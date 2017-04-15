package ejb.sessions;

import ejb.entites.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;


@Stateless
public class ServiceGestionEncheresBean implements ServiceGestionEncheresLocal, ServiceGestionEncheresRemote {

    @PersistenceContext(unitName = "gestionEncheres")
    private EntityManager em;

    /**
     * Ajoute un article dans la base de données. L'enchère correspondant à cet article sera automatiquement
     * mis à l'état "NON COMMENCEE"
     *
     * @param code        le code unique de l'article
     * @param nom         le nom attribué à l'article
     * @param prixInitial le prix initial minimum de l'article
     * @throws ArticleDejaExistantException Erreur si un article de même code existe déjà dans la base de données
     * @throws PrixInitialException         Erreur si le prix initial de l'article est inférieur à 0 ou non renseigné
     */
    @Override
    public void addArticle(String code, String nom, Double prixInitial) throws ArticleDejaExistantException, PrixInitialException {
        try {
            getArticle(code);
            throw new ArticleDejaExistantException();
        } catch (ArticleInexistantException e) {
            Article article = new Article();
            article.setCode(code);
            article.setEtatEnchere(EtatEnchere.NON_COMMENCEE);
            if (prixInitial != null && prixInitial >= 0) {
                article.setPrixInitial(prixInitial);
            } else {
                throw new PrixInitialException();
            }
            article.setPrixMeilleureOffre(0);
            article.setNom(nom);
            article.setAcheteur(new HashSet<Acheteur>());
            article.setOffres(new HashSet<Offre>());
            em.persist(article);
        }
    }

    /**
     *
     * Ajoute un client du type typeCliendt dans la base de données
     *
     * @param email L'email est l'identifiant unique du client dans le base de données
     * @param typeClient Le type de client permet de définir le type de client qui veut être créé
     * @param pourcentage Le pourcentage ne doit être donné que pour la création d'un client Systematique
     * @throws ClientDejaExistantException Erreur si un client du même nom est déjà dans la base de donnée
     * @throws PourcentageIncorrectException Erreur si un pourcentage inférieur à 0 ou non renseigné est fourni pour la création d'un client Systematique
     */
    @Override
    public void addClient(String email, TypeClient typeClient, Double pourcentage) throws ClientDejaExistantException, PourcentageIncorrectException {
        try {
            getClient(email);
            throw new ClientDejaExistantException();
        } catch (ClientInexistantException e) {
            //on ne fait rien
        }
        Client client;
        if (typeClient == TypeClient.CLASSIQUE)
            client = new Classique();
        else if (typeClient == TypeClient.AGRESSIF) {
            client = new Agressif();
            ((Agressif) client).setPourcentage(50);
        } else if (typeClient == TypeClient.ALEATOIRE)
            client = new Aleatoire();
        else {
            client = new Systematique();
            if (pourcentage == null || pourcentage < 0) {
                throw new PourcentageIncorrectException();
            }
            ((Systematique)client).setPourcentage(pourcentage);
        }
        client.setAdresseMail(email);
        em.persist(client);
    }

    /**
     *
     * @return la liste des articles dans la base de données. null s'il n'y a aucun article dans la base de données.
     */
    @Override
    public Collection<Article> getArticles() {
        return (Collection<Article>) em.createQuery("SELECT a from Article a").getResultList();
    }

    /**
     *
     * @return la liste des clients dans la base de données. null s'il n'y a aucun client dans la base de données.
     */
    @Override
    public Collection<Client> getClients() {
        return (Collection<Client>) em.createQuery("SELECT c from Client c").getResultList();

    }

    /**
     * Permet de recuperer de manière sécurisée un article ayant un code donné.
     *
     * @param code le code de l'article voulu
     * @return L'article correspondant au code dans la base de données
     * @throws ArticleInexistantException Erreur si il n'existe aucun article ayant ce code dans la base de données
     */
    @Override
    public Article getArticle(String code) throws ArticleInexistantException {
        Article article = em.find(Article.class, code);
        if (article == null)
            throw new ArticleInexistantException();
        return article;
    }

    /**
     * Démarre l'enchère d'un article donné
     *
     * @param code le code de l'article voulu
     * @throws ArticleInexistantException Erreur si l'article n'existe pas
     * @throws EnchereDejaTerminneeException Erreur si l'article est déjà terminee (et donc dont l'enchere ne peut demarrer)
     * @throws EnchereDejaEnCoursException Erreur si l'article est déjà en cours (ne peut demarrer deux fois)
     */
    @Override
    public void demarrerEnchere(String code) throws ArticleInexistantException, EnchereDejaEnCoursException, EnchereDejaTerminneeException {
        Article article = getArticle(code);
        if (article.getEtatEnchere() == EtatEnchere.EN_COURS) {
            throw new EnchereDejaEnCoursException();
        } else if (article.getEtatEnchere() == EtatEnchere.TERMINEE) {
            throw new EnchereDejaTerminneeException();
        }
        article.setEtatEnchere(EtatEnchere.EN_COURS);
    }

    /**
     * Cloture l'enchere qui a le code correspondant
     *
     * @param code le code de l'article dont on clore l'enchere
     * @throws ArticleInexistantException Erreur si l'article n'exite pas
     * @throws EnchereDejaTerminneeException Erreur si l'article est déjà terminee (et donc dont l'enchere ne peut se cloturer deux fois)
     * @throws EnchereNonDemarreeException Erreur si l'article est n'est pas dans l'etat EN COURS (et ne peut donc pas se cloturer avant)
     */
    @Override
    public void clotureEnchere(String code) throws ArticleInexistantException, EnchereDejaTerminneeException, EnchereNonDemarreeException {
        Article article = getArticle(code);
        if (article.getEtatEnchere() == EtatEnchere.TERMINEE) {
            throw new EnchereDejaTerminneeException();
        } else if (article.getEtatEnchere() == EtatEnchere.NON_COMMENCEE) {
            throw new EnchereNonDemarreeException();
        }
        article.setEtatEnchere(EtatEnchere.TERMINEE);
    }

    /**
     * Permet d'inscrire un client aux enchere d'un article
     *
     * @param email l'email du client que l'on souhaite inscrire
     * @param code le code de l'article concerné
     * @param prixMax le prix maximum que le client souhaite mettre pour cette session d'enchere
     * @throws InscriptionImpossibleException Erreur si la date d'inscription est passé (que l'enchere est deja terminee)
     * @throws ArticleInexistantException Erreur si l'article concerné n'existe pas
     */
    @Override
    public void inscription(String email, String code, double prixMax) throws InscriptionImpossibleException, ArticleInexistantException, ClientInexistantException {
        Article article = getArticle(code);
        if (article.getEtatEnchere() == EtatEnchere.TERMINEE) {
            throw new InscriptionImpossibleException();
        }

        Client client = getClient(email);
        for (Acheteur a : article.getAcheteur()){
            if (a.getClient().getAdresseMail() == email)
                throw new InscriptionImpossibleException();
        }
        Acheteur acheteur = new Acheteur();
        acheteur.setClient(client);
        acheteur.setArticle(article);
        acheteur.setPlafond(prixMax);
        article.getAcheteur().add(acheteur);
        em.persist(acheteur);

    }

    /**
     * permet d'ajouter l'offre d'une personne et execute l'ensemble des actions associés.
     *
     * @param email l'email de l'acheteur proposant une offre
     * @param code le code de l'article concerné
     * @param proposition la somme proposé par la personne
     * @throws EnchereCloseException Erreur si l'enchere est déjà terminee
     * @throws EnchereNonDemarreeException Erreur si l'enchere n'a pas encore demarree
     * @throws NonInscritException Erreur si le client n'est pas inscrit pour l'enchère de cet article
     * @throws ArticleInexistantException Erreur si l'article renseigné n'existe pas
     */
    @Override
    public void proposeOffre(String email, String code, double proposition) throws EnchereCloseException, EnchereNonDemarreeException, NonInscritException, ArticleInexistantException, ClientInexistantException {
        // On recupere les objets que l'on va utiliser et on vérifie qu'une offre est possible
        Article article = getArticle(code);
        if (article.getEtatEnchere() == EtatEnchere.TERMINEE){
            throw new EnchereCloseException();
        } else if (article.getEtatEnchere() == EtatEnchere.NON_COMMENCEE) {
            throw new EnchereNonDemarreeException();
        }
        Client client = getClient(email);
        Acheteur acheteur = new Acheteur();
        acheteur.setArticle(article);
        acheteur.setClient(client);
        acheteur = em.find(Acheteur.class, acheteur);
        if (acheteur == null){
            throw new NonInscritException();
        }

        // Si l'offre est possible mais pas interressante
        if (proposition <= article.getPrixMeilleureOffre() || proposition <= article.getPrixInitial() || proposition > acheteur.getPlafond()){
            // On ne fait rien
        } else {
            //Sinon on ajoute l'offre
            Offre offre = new Offre();
            offre.setDate(new Timestamp((Calendar.getInstance().getTime().getTime())));
            offre.setPrix(proposition);
            offre.setArticle(article);
            offre.setAcheteur(acheteur);
            article.getOffres().add(offre);
            article.setPrixMeilleureOffre(offre.getPrix());
            em.persist(offre);
            //Demarrage du mécanisme d'offre automatique
            gestionOffre(article);
        }
    }

    /**
     * Permet de recuperer un client de manière de sécurisé
     *
     * @param email l'identifiant unique du client dans la base de données
     * @return le client de la base de données ayant l'email founi en parametre
     * @throws ClientInexistantException Erreur si le client n'existe pas dans la base de données
     */
    private Client getClient(String email) throws ClientInexistantException {
        Client client = em.find(Client.class, email);
        if (client == null)
            throw new ClientInexistantException();
        return client;
    }

    /**
     * Permet l'excution interne de la montée des surenchère
     * @param article l'article pour lequel on va faire la montée des enchères
     */
    private void gestionOffre(Article article){
        boolean fini = false;
        while (!fini) {
            fini = true;
            for (Acheteur a : article.getAcheteur()) {
                if (a != getMeilleursAcheteur(article)) {
                    double proposition = a.getClient().surenchere(article.getPrixMeilleureOffre());
                    if (proposition < a.getPlafond() && proposition > article.getPrixMeilleureOffre()) {
                        Offre offre = new Offre();
                        offre.setDate(new Timestamp((Calendar.getInstance().getTime().getTime())));
                        offre.setPrix(proposition);
                        offre.setArticle(article);
                        offre.setAcheteur(a);
                        article.getOffres().add(offre);
                        article.setPrixMeilleureOffre(offre.getPrix());
                        em.persist(offre);
                        fini = false;
                    }
                }
            }
        }
    }

    /**
     * Retourne le meilleur acheteur d'un article donné.
     *
     * @param article l'article dont on veut le meilleur acheteur
     * @return l'acheteur ayant fait la meilleur offre
     */
    private Acheteur getMeilleursAcheteur(Article article){
        for (Offre offre : article.getOffres()){
            if (offre.getPrix() == article.getPrixMeilleureOffre()) {
                return offre.getAcheteur();
            }
        }
        return null;
    }
}

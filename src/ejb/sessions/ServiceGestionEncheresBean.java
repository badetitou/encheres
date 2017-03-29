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

    @Override
    public void addArticle(String code, String nom, double prixInitial) throws ArticleDejaExistantException, PrixInitialException {
        try {
            getArticle(code);
            throw new ArticleDejaExistantException();
        } catch (ArticleInexistantException e) {
            Article article = new Article();
            article.setCode(code);
            article.setEtatEnchere(EtatEnchere.NON_COMMENCE);
            if(prixInitial >= 0) {
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

    @Override
    public void addClient(String email, TypeClient typeClient, double pourcentage) throws ClientDejaExistantException, PourcentageIncorrectException {
        if(pourcentage < 0){
            throw new PourcentageIncorrectException();
        }
        if (em.find(Client.class, email) != null){
            throw new ClientDejaExistantException();
        }
        Client client;
        if (typeClient == TypeClient.CLASSIQUE)
            client = new Classique();
        else if (typeClient == TypeClient.AGRESSIF) {
            client = new Agressif();
            ((Agressif) client).setPourcentage(50);
        }
        else if (typeClient == TypeClient.ALEATOIRE)
            client = new Aleatoire();
        else {
            client = new Systematique();
            ((Systematique)client).setPourcentage(pourcentage);
        }
        client.setAdresseMail(email);
        em.persist(client);
    }

    @Override
    public Collection<Article> getArticles() {
        return (Collection<Article>) em.createQuery("SELECT a from Article a").getResultList();
    }

    @Override
    public Collection<Client> getClients() {
        return (Collection<Client>) em.createQuery("SELECT c from Client c").getResultList();

    }

    @Override
    public Article getArticle(String code) throws ArticleInexistantException {
        Article article = em.find(Article.class, code);
        if (article == null)
            throw new ArticleInexistantException();
        return article;
    }

    @Override
    public void demarrerEnchere(String code) throws ArticleInexistantException{
        Article article = getArticle(code);
        article.setEtatEnchere(EtatEnchere.EN_COURS);
    }

    @Override
    public void clotureEnchere(String code) throws ArticleInexistantException{
        Article article = getArticle(code);
        article.setEtatEnchere(EtatEnchere.TERMINEE);
    }

    @Override
    public void inscription(String email, String code, double prixMax) throws InscriptionImpossibleException, ArticleInexistantException {
        Article article = getArticle(code);
        if (article.getEtatEnchere() != EtatEnchere.NON_COMMENCE){
            throw new InscriptionImpossibleException();
        }

        Client client = em.find(Client.class, email);
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

    @Override
    public void proposeOffre(String email, String code, double proposition) throws EnchereCloseException, EnchereNonDemarreeException, NonInscritException, ArticleInexistantException{
        Article article = getArticle(code);
        if (article.getEtatEnchere() == EtatEnchere.TERMINEE){
            throw new EnchereCloseException();
        } else if (article.getEtatEnchere() == EtatEnchere.NON_COMMENCE){
            throw new EnchereNonDemarreeException();
        }
        Client client = em.find(Client.class, email);
        Acheteur acheteur = new Acheteur();
        acheteur.setArticle(article);
        acheteur.setClient(client);
        acheteur = em.find(Acheteur.class, acheteur);
        if (acheteur == null){
            throw new NonInscritException();
        }

        if (proposition <= article.getPrixMeilleureOffre() || proposition <= article.getPrixInitial() || proposition > acheteur.getPlafond()){
            // On ne fait rien
        } else {
            Offre offre = new Offre();
            offre.setDate(new Timestamp((Calendar.getInstance().getTime().getTime())));
            offre.setPrix(proposition);
            offre.setArticle(article);
            offre.setAcheteur(acheteur);
            article.getOffres().add(offre);
            article.setPrixMeilleureOffre(offre.getPrix());
            em.persist(offre);

            gestionOffre(article);
        }
    }

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

    private Acheteur getMeilleursAcheteur(Article article){
        for (Offre offre : article.getOffres()){
            if (offre.getPrix() == article.getPrixMeilleureOffre()) {
                return offre.getAcheteur();
            }
        }
        return null;
    }
}

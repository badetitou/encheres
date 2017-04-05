package ejb.sessions;


import ejb.entites.Article;
import ejb.entites.Client;
import ejb.entites.TypeClient;

import java.util.Collection;

/**
 * Created by badetitou on 19/03/17.
 */
public interface ServiceGestionEncheres {
    void addArticle(String code, String nom, Double prixInitial)
            throws ArticleDejaExistantException, PrixInitialException;

    void addClient(String email, TypeClient typeClient, Double pourcentage) throws ClientDejaExistantException, PourcentageIncorrectException;

    Collection<Article> getArticles();

    Collection<Client> getClients();

    Article getArticle(String code) throws ArticleInexistantException;

    void demarrerEnchere(String code) throws ArticleInexistantException, EnchereDejaEnCoursException, EnchereDejaTerminneeException;

    void clotureEnchere(String code) throws ArticleInexistantException, EnchereNonDemarreeException, EnchereDejaTerminneeException;

    void inscription(String email, String code, double prixMax) throws InscriptionImpossibleException, ArticleInexistantException, ClientInexistantException;

    void proposeOffre(String email, String code, double proposition) throws NonInscritException, EnchereCloseException, EnchereNonDemarreeException, ArticleInexistantException, ClientInexistantException;

}

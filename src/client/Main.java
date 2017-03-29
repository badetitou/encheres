package client;

import ejb.entites.TypeClient;
import ejb.sessions.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {


    public static void main(String[] args) {
         String address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        try {
            InitialContext ic = new InitialContext();
            Object obj = ic.lookup(address);
            ServiceGestionEncheres sge = (ServiceGestionEncheres) obj;

            System.out.println("Creation d'utilisateur");

            try {
                sge.addClient("john.smith@gmail.com", TypeClient.AGRESSIF, 0);
            } catch (ClientDejaExistantException e) {
                System.err.println("Client deja existant 1");
            } catch (PourcentageIncorrectException e) {
                e.printStackTrace();
            }

            try {
                sge.addClient("paul.dupont@free.fr", TypeClient.ALEATOIRE, 0);
            } catch (ClientDejaExistantException e) {
                System.err.println("Client deja existant 2");
            } catch (PourcentageIncorrectException e) {
                e.printStackTrace();
            }

            try {
                sge.addClient("moi@free.fr", TypeClient.CLASSIQUE, 0);
            } catch (ClientDejaExistantException e) {
                System.err.println("Client deja existant 3");
            } catch (PourcentageIncorrectException e) {
                e.printStackTrace();
            }

            try {
                sge.addClient("alain.dubois@yahoo.fr", TypeClient.SYSTEMATIQUE, 0.1);
            } catch (ClientDejaExistantException e) {
                System.err.println("Client deja existant 4");
            } catch (PourcentageIncorrectException e) {
                e.printStackTrace();
            }
            // Creation d'article
            System.out.println("*** Creation d'articles ***");
            try {
                sge.addArticle("re12", "cactus qui pique", 12.5);
                sge.addArticle("re17", "tablette 10 pouces", 260);
            } catch (ArticleDejaExistantException e) {
                e.printStackTrace();
            } catch (PrixInitialException e) {
                e.printStackTrace();
            }
            // Gestion des inscriptions
            try {
                sge.inscription("john.smith@gmail.com", "re12", 800);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }
            try {
                sge.inscription("paul.dupont@free.fr", "re12", 52);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }
            try {
                sge.inscription("moi@free.fr", "re12", 800);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }
            try {
                sge.inscription("alain.dubois@yahoo.fr", "re12", 45);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }
            try {
                sge.inscription("paul.dupont@free.fr", "re17", 300);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }
            try {
                sge.inscription("moi@free.fr", "re17", 300);
            } catch (InscriptionImpossibleException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }

            // Debut des encheres
            try {
                sge.demarrerEnchere("re12");
                sge.demarrerEnchere("re17");
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }

            System.out.println("OK");

            try {
                sge.proposeOffre("moi@free.fr", "re12",17);
            } catch (NonInscritException e) {
                e.printStackTrace();
            } catch (EnchereCloseException e) {
                e.printStackTrace();
            } catch (EnchereNonDemarreeException e) {
                e.printStackTrace();
            } catch (ArticleInexistantException e) {
                e.printStackTrace();
            }

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}


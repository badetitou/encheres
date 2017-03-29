<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.entites.Article" %>
<%@ page import="ejb.entites.Offre" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="ejb.entites.Acheteur" %>
<%@ page import="ejb.sessions.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des encheres</title>
    <!--Import Google Icon Font-->
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="/enchere/css/materialize.css"  media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/enchere/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/enchere/js/materialize.min.js"></script>
    <script>
        $(document).ready(function() {
            $('select').material_select();
            $(".button-collapse").sideNav();
        });
    </script>

    <%!String address;%>
    <%!InitialContext ic;%>
    <%!Object obj;%>
    <%!ServiceGestionEncheres sge;%>
    <%!Article a;%>
    <%!String email;%>
    <%!String prix;%>
    <%!List<Offre> offres;%>
    <%!NumberFormat formatter;%>
    <%!boolean article_ok;%>


    <%
        article_ok = true;
        address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        ic = new InitialContext();
        obj = ic.lookup(address);
        sge = (ServiceGestionEncheres) obj;
        if (request.getParameter("refArticle") == null) {
            article_ok = false;
    %>
    <meta http-equiv="refresh" content="0; url=index.html?ok=ko&description=Article%20Inexistant" />

    <%
    } else {
        email = request.getParameter("email");
        prix = request.getParameter("prix");
        if (email != null && prix != null) {
            try {
                sge.proposeOffre(email, request.getParameter("refArticle"), Double.parseDouble(prix));
            %>
            <script>
                var ok = 'ok';
                var message = 'Offre bien proposé !!';
            </script>
            <%
            } catch (NonInscritException e) {
            %>
            <script>
                var ok = 'ko';
                var message = 'Oups... vous n\'etes pas inscrit';
            </script>
            <%
            } catch (EnchereCloseException e) {
            %>
            <script>
                var ok = 'ko';
                var message = 'Oups... l\'enchère est close';
            </script>
            <%
            } catch (EnchereNonDemarreeException e) {
            %>
            <script>
                var ok = 'ko';
                var message = 'Oups... l\'enchère n\'a pas commencée';
            </script>
            <%    } catch (ArticleInexistantException e) {
                article_ok = false;
            %>
            <meta http-equiv="refresh" content="0; url=index.html?ok=ko&description=<%=e.getMessage()%>" />

            <%    }
            catch (NumberFormatException e) {
            %>
            <script>
                var ok = 'ko';
                var message = 'Oups... Prix incorect';
            </script>
            <%
                }
            %>

            <%
                }
                try {
                    a = sge.getArticle(request.getParameter("refArticle"));
                } catch (ArticleInexistantException e) {
                    article_ok = false;

            %>
            <meta http-equiv="refresh" content="0; url=index.html?ok=ko&description=<%=e.getMessage()%>" />
            <%
                }

                formatter = new DecimalFormat("#0.00");
            %>


</head>
        <%
            // si l'article existe on affiche la page
            if (article_ok){
        %>
<body>
<header>
    <nav class="indigo">
        <div class="nav-wrapper">
            <a href="index.html" class="brand-logo">Encheres BI</a>
            <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
                <li><a href="ListeEnchere">Listes Des Encheres</a></li>
                <li><a href="EtatEnchere">Etat Des Encheres</a></li>

                <li><a href="EnregistrementClient.html">Inscription</a></li>
                <li><a href="EnregistrementArticle.html">Vendre</a></li>
            </ul>
            <ul class="side-nav" id="mobile-demo">
                <li><a href="ListeEnchere">Listes Des Encheres</a></li>
                <li><a href="EtatEnchere">Etat Des Encheres</a></li>

                <li><a href="EnregistrementClient.html">Inscription</a></li>
                <li><a href="EnregistrementArticle.html">Vendre</a></li>
            </ul>
        </div>
    </nav>
</header>
<main>
    <div class="container">
        <div class="row">
            <h1><%= a.getNom()%></h1>
            <h5>Prix initial : <%=a.getPrixInitial()%>  &euro;</h5>
            <p>(ref:<%=a.getCode()%>)</p>
            <div>
                Prix en cours :
                <%=formatter.format(a.getPrixMeilleureOffre())%> &euro;
                <table class="bordered highlight responsive-table">
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>Valeur</th>
                        <th>Client</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        offres = new ArrayList<Offre>();
                        offres.addAll(a.getOffres());
                        Collections.sort(offres);
                        for(Offre offre : offres){

                    %>
                    <tr>
                        <td><%= offre.getDate()%></td>
                        <td><%=formatter.format(offre.getPrix())%> &euro;</td>
                        <td><%=offre.getAcheteur().getClient().getAdresseMail()%></td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <h1>Faire une offre :</h1>
            <form action="GestionEnchere" method="get" class="col s12">
                <input type="hidden" name="refArticle" value="<%=a.getCode()%>">
                <div class="row">
                    <div class="input-field col s12">
                        <select name="email">
                            <%     for(Acheteur a : a.getAcheteur()){
                            %>
                            <option value="<%= a.getClient().getAdresseMail()%>"><%=a.getClient().getAdresseMail()%></option>
                            <%
                                }
                            %>
                        </select>
                        <label>Email</label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="number" type="number" step="any" name="prix" class="validate">
                        <label for="number">Proposition</label>
                    </div>
                </div>
                <button class="btn waves-effect waves-light" type="submit" name="action">Valider
                    <i class="material-icons right">send</i>
                </button>
            </form>
        </div>

    </div>
</main>
<footer class="page-footer indigo">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
                <h5 class="white-text">Informations</h5>
                <p class="grey-text text-lighten-4">Ce site correspond à un projet de polytech Lille</p>
            </div>
            <div class="col l4 offset-l2 s12">
                <h5 class="white-text">Liens</h5>
                <ul>
                    <li><a class="grey-text text-lighten-3" href="http://badetitou.github.io">Badetitou</a></li>
                    <li><a class="grey-text text-lighten-3" href="https://www.linkedin.com/in/irenecampagne/">Irène Campagne</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2017 WTFPL
            <a class="grey-text text-lighten-4 right" href="https://fr.wikipedia.org/wiki/WTFPL">Licence link</a>
        </div>
    </div>
</footer>
<script>
    if(ok === 'ko') {
        if (message != null) {
            Materialize.toast(message, 10000, 'rounded')
        } else {
            Materialize.toast('Erreur non repertoriée... Désolé ^^', 10000, 'rounded')
        }
    }
</script>
</body>
<%
            // si l'article existe on affiche la page
        }
    }
%>
</html>
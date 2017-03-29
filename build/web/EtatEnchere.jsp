<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="ejb.entites.Article" %>
<%@ page import="ejb.entites.EtatEnchere" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Etat Enchere</title>
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
            $(".button-collapse").sideNav();
        });
    </script>
</head>
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
<%!String address;%>
<%!InitialContext ic;%>
<%!Object obj;%>
<%!ServiceGestionEncheres sge;%>
<main>
    <div class="container">

        <table>
            <thead>
            <tr>
                <th>Nom</th>
                <th>Prix Initial</th>
                <th>Etat Enchere</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
    <%
        address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        ic = new InitialContext();
        obj = ic.lookup(address);
        sge = (ServiceGestionEncheres) obj;
        for(Article a : sge.getArticles()){
    %>
    <tr>
        <td><%=a.getNom()%></td>
        <td><%=a.getPrixInitial()%> €</td>
        <td><%=a.getEtatEnchere().toString()%></td>
        <td>
                <%
            if (a.getEtatEnchere() == EtatEnchere.NON_COMMENCE){
            %>
            <a class="waves-effect waves-circle waves-light" href="DemarrerEnchere?refArticle=<%=a.getCode()%>"><i class="material-icons">lock_open</i></a>
                <%
            } else if (a.getEtatEnchere() == EtatEnchere.EN_COURS){
        %>
            <a class="waves-effect waves-circle waves-light" href="TerminerEnchere?refArticle=<%=a.getCode()%>"><i class="material-icons">lock</i></a>
                <%
            }
            }
            %>
            </tbody>
        </table>
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
<script type="text/javascript" src="/enchere/js/notification.js"></script>
</body>
</html>

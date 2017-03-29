<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.sessions.ArticleDejaExistantException" %>
<%@ page import="ejb.sessions.PrixInitialException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/enchere/css/materialize.css"  media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/enchere/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/enchere/js/materialize.min.js"></script>

    <%!String address;%>
    <%!InitialContext ic;%>
    <%!Object obj;%>
    <%!ServiceGestionEncheres sge;%>

    <%
        address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        ic = new InitialContext();
        obj = ic.lookup(address);
        sge = (ServiceGestionEncheres) obj;
        if (request.getParameter("code").isEmpty() ||
                request.getParameter("nom").isEmpty() ||
                request.getParameter("prixInitial").isEmpty()){
            %>
    <meta http-equiv="refresh" content="0; url=Enregistrement.html?ok=ko&description=Données%20Manquantes" />

    <%
        } else {
            try {
                sge.addArticle(request.getParameter("code"), request.getParameter("nom"),
                        Double.parseDouble(request.getParameter("prixInitial")));
                %>
            <meta http-equiv="refresh" content="0; url=index.html?ok=ok" />
            <%
            } catch (ArticleDejaExistantException e) {
            %>
                <meta http-equiv="refresh" content="0; url=EnregistrementArticle.html?ok=ko&description=<%=e.getMessage()%>" />
            <%
            } catch (PrixInitialException e) {
            %>
                <meta http-equiv="refresh" content="0; url=EnregistrementArticle.html?ok=ko&description=<%=e.getMessage()%>" />
            <%
            }
        }
    %>
</head>
<body>
<div class="preloader-wrapper big active">
    <div class="spinner-layer spinner-blue">
        <div class="circle-clipper left">
            <div class="circle"></div>
        </div><div class="gap-patch">
        <div class="circle"></div>
    </div><div class="circle-clipper right">
        <div class="circle"></div>
    </div>
    </div>
</div>
</body>
</html>

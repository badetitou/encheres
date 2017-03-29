<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.entites.TypeClient" %>
<%@ page import="ejb.sessions.ClientDejaExistantException" %>
<%@ page import="ejb.sessions.PourcentageIncorrectException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/enchere/css/materialize.css"  media="screen,projection"/>

    <!--Let browser know website is optimized for mobile-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="/enchere/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/enchere/js/materialize.min.js"></script>

    <%!String address;%>
    <%!String typeClient;%>
    <%!InitialContext ic;%>
    <%!Object obj;%>
    <%!ServiceGestionEncheres sge;%>

    <%
        address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        ic = new InitialContext();
        obj = ic.lookup(address);
        sge = (ServiceGestionEncheres) obj;
        typeClient = request.getParameter("type");
        if (typeClient == null || request.getParameter("pourcentage").isEmpty() || request.getParameter("email").isEmpty()){
        %>
    <meta http-equiv="refresh" content="0; url=EnregistrementClient.html?ok=ko&description=Données%20Manquantes" />

    <%
        } else {
            try {
                if (typeClient.equals("agressif")) {
                    sge.addClient(request.getParameter("email"), TypeClient.AGRESSIF,
                            Double.parseDouble(request.getParameter("pourcentage")));
                } else if (typeClient.equals("classique")) {
                    sge.addClient(request.getParameter("email"), TypeClient.CLASSIQUE,
                            Double.parseDouble(request.getParameter("pourcentage")));
                } else if (typeClient.equals("systematique")) {
                    sge.addClient(request.getParameter("email"), TypeClient.SYSTEMATIQUE,
                            Double.parseDouble(request.getParameter("pourcentage")));
                } else {
                    sge.addClient(request.getParameter("email"), TypeClient.ALEATOIRE,
                            Double.parseDouble(request.getParameter("pourcentage")));
                }
            %>
                <meta http-equiv="refresh" content="0; url=index.html?ok=ok" />
            <%
            } catch (ClientDejaExistantException e) {
            %>
                <meta http-equiv="refresh" content="0; url=EnregistrementClient.html?ok=ko&description=<%=e.getMessage()%>" />
            <%
            } catch (PourcentageIncorrectException e){
            %>
                <meta http-equiv="refresh" content="0; url=EnregistrementClient.html?ok=ko&description=<%=e.getMessage()%>" />

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

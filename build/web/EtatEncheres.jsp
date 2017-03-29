<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="ejb.entites.Article" %>
<%@ page import="ejb.entites.EtatEnchere" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%!String address;%>
<%!InitialContext ic;%>
<%!Object obj;%>
<%!ServiceGestionEncheres sge;%>

<ul>
    <%
        address = "ejb:gestionEncheres/gestionEncheresSessions/"
                + "ServiceGestionEncheresBean!ejb.sessions.ServiceGestionEncheresRemote";
        ic = new InitialContext();
        obj = ic.lookup(address);
        sge = (ServiceGestionEncheres) obj;
        for(Article a : sge.getArticles()){
    %>
    <li>Nom : <a href="DetailArticle?refArticle=<%=a.getCode()%>"> <%=a.getNom()%></a>, prix initial : <%=a.getPrixInitial()%>
        EtatEnchere : <%=a.getEtatEnchere().toString()%>
        <%
            if (a.getEtatEnchere() == EtatEnchere.NON_COMMENCE)
        %>
            Demarrer enchere : <a href="DemarrerEnchere?refArticle=<%=a.getCode()%>"/>
        <%
            else if (a.getEtatEnchere() == EtatEnchere.EN_COURS)
        %>
            Terminer enchere : <a href="TerminerEnchere?refArticle=<%=a.getCode()%>"/>
    <%
        }
    %>
</ul>



</body>
</html>

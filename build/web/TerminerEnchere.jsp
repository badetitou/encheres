<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.sessions.ArticleInexistantException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

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
        try {
            sge.clotureEnchere(request.getParameter("refArticle"));
    %>
    <meta http-equiv="refresh" content="0; url=EtatEnchere?ok=ok&description=Cloture%20des%20encheres" />
    <%
    } catch (ArticleInexistantException e) {%>
    <meta http-equiv="refresh" content="0; url=EtatEnchere?ok=ko&description=<%=e.getMessage()%>" />
    <%}
    %>
</head>
<body>


<h1>Error</h1>

</body>
</html>

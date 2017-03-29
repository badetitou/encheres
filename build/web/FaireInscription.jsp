<%@ page import="javax.naming.InitialContext" %>
<%@ page import="ejb.sessions.ServiceGestionEncheres" %>
<%@ page import="ejb.sessions.InscriptionImpossibleException" %>
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
            sge.inscription(request.getParameter("client"), request.getParameter("article"),
                    Double.parseDouble(request.getParameter("plafond"))); %>
    <meta http-equiv="refresh" content="0; url=index.html?ok=ok" />

    <%
    } catch (InscriptionImpossibleException e){
    %>
</head>
<body>
<h1>OUPS... Une erreur ^^</h1>

</body>
</html>

<%
    }
%>
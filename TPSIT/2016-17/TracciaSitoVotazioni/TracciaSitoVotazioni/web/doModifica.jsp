

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="persona" class="dati.Persona"  scope="session" />
<!DOCTYPE html>
<html>
    <head>
            <title> Esecuzione modifica</title>
    </head>
    <body>
        <h1>Esecuzione Modifica</h1>
        <% Connection conn = dbutil.UtilDB.getConnection();
        %>
        <p> Da fare  !</p>
        <a href="index.jsp"> index </a>
    </body>
</html>

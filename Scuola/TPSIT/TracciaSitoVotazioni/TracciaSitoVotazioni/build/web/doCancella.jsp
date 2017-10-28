
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Esecuzione cancellazione</title>
    </head>
    <body>
        <h1>Esecuzione Cancellazione</h1>
        <% Connection conn = dbutil.UtilDB.getConnection();
        String query = "DELETE FROM PERSONA WHERE IdPersona=?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1,request.getParameter("id"));
        st.executeUpdate();
        %>
        <p>Fatto !</p>
        <a href="index.jsp"> index </a>
    </body>
</html>

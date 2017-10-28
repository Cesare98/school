

<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Pagine d'errore</title>
    </head>
    <body>

        <div class="message">
            Siamo spiacenti, si è verificato un errore
            durante l'esecuzione:<br /><br />
            <%= exception.getMessage()%>
      

        </div>
    </body>
</html>




<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage = "true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Pagina che ccttura eventuali eccezioni provenienti dalle altre pagine</title>
    </head>
    <body>
        <h1> Errore </h1>
        <div class="message">
            Siamo spiacenti, si è verificato un errore
            durante l'esecuzione:<br /><br />
            <%= exception.getClass().getName()%>
            <%= exception.getMessage()%>
      

        </div>
    </body>
</html>


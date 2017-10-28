<%-- 
    Document   : index
    Created on : 24-mag-2017, 8.32.02
   --%>

<jsp:useBean id="mioContatore" class="modelli.Contatore"  scope="session" />  <%--- oggetto creato automaticamente e condiviso con il controlllore --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>   Demo JSP con Modello MVC </h1>
        <form method="get" action='controlloContatore.jsp'>  
            <input type="submit"  value='Incrementa' name="btnIncr" />
            <input type="submit"  value='Decrementa' name="btnDecr"  />
            <input type='text' name='txtNumber' value="${mioContatore.get()}"/>
        </form>
    </body>
</html>

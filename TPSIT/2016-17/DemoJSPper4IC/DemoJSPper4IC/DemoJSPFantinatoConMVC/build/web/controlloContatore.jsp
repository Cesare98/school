<%-- 
    Document   : doModficaContatore
    Created on : 24-mag-2017, 17.13.04
    Author     : gc
--%>

<jsp:useBean id="mioContatore" class="modelli.Contatore"  scope="session" />  <%--  oggetto condiviso comune tra le pagine  --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage="PaginaErrore.jsp" %> <%--  pagina che  cattura eventuali eccezioni  --%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>  Controllore VISTA Contatore</title>
    </head>
    <body>
        <%
            int num = Integer.parseInt(request.getParameter("txtNumber")); 
            if (num != mioContatore.get()) {  // noodificato il numero nella casella di Input. 
                mioContatore.set(num);
            }
            if (request.getParameter("btnIncr") != null) { //premuto Incremento
                mioContatore.incr();
            }
            if (request.getParameter("btnDecr") != null) { //premuto Decremento
                mioContatore.decr();
            }
          
            response.sendRedirect("VistaContatore.jsp"); //mostra la pagina della vista

        %>
    </body>
</html>

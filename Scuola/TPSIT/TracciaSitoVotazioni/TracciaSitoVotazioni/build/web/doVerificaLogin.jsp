<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbutil.UtilDB"%>
<%@page errorPage = "PaginaErrore.jsp" %>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>

<jsp:useBean id="login"
             class="beans.Credenziali"
             scope="session" />
<jsp:useBean id="erroriLogin"
             class="beans.ErroriLogin"
             scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>  Verifica Credenziali </title>
    </head>
    <body>
   

        <%  login.setUserName(request.getParameter("userName"));
            login.setPassword(request.getParameter("password"));
    
            login.setIdPersona(UtilDB.verificaCredenziali(login.getUserName(),login.getPassword()));
            boolean accreditato = login.getIdPersona()>0;
            if(!accreditato) {
                erroriLogin.setMessaggi("Credenziali non valide");
                response.sendRedirect("index.jsp");
            }
            else  {
                login.setAccreditato(true);
                

                response.sendRedirect("listaVotazioniIscritto.jsp");
            }

        %>

    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbutil.UtilDB"%>
<%@page errorPage = "PaginaErrore.jsp" %>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>

<jsp:useBean id="reg"
             class="beans.Registrazione"
             scope="session" />
<jsp:useBean id="erroriReg"
             class="beans.ErroriRegistrazione"
             scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>  Inserimento </title>
    </head>
    <body>
        <h1>Esecuzione Inserimento</h1>

        <%  reg.setCognomeNome(request.getParameter("cognomeNome"));
            reg.setUserName(request.getParameter("userName"));
            reg.setIndirizzo(request.getParameter("indirizzo"));
            reg.setPassword1(request.getParameter("password1"));
            reg.setPassword2(request.getParameter("password2"));

            String errori = reg.validate();
            erroriReg.setMessaggi(errori);
            if (errori.isEmpty()) {
                UtilDB.inserimentoPersona(reg);
                response.sendRedirect("index.jsp");
            } else {
                erroriReg.setMessaggi(errori);
                response.sendRedirect("inserimentoNuovaPersona.jsp");
            }

        %>

    </body>
</html>

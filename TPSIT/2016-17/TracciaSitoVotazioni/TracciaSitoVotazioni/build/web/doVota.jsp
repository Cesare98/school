
<%@page import="dbutil.UtilDB"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="login" class="beans.Credenziali"  scope="session" />
<jsp:useBean id="datiVoto"
             class="beans.DatiVoto"
             scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Esecuzione Voto</title>
    </head>
    <body>
        <h1>Esecuzione Voto</h1>
        <% if (login.isAccreditato()) {
                String voto = request.getParameter("getVoto");
                if (voto != null) {
                    UtilDB.inserisciVoto(login.getIdPersona(), datiVoto.getIdCosaVotare(), voto);
                } else {
                    response.sendRedirect("vota.jsp");
                }
            }
            %>
        <p>Fatto !</p>
        <a href="index.jsp"> index </a>
    </body>
</html>

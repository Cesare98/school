
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="login" class="beans.Credenziali"  scope="session" />
<jsp:useBean id="erroriLogin"
             class="beans.ErroriLogin"
             scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="stile/stile.css" title="style" />
        <title>Lista Votazioni a cui sei iscritto </title>
    </head>
    <body>
        <script src="js/Data.js"></script>
        <h1> Lista delle Votazioni a cui sei iscritto </h1>

        <% if (login.isAccreditato()) {
                Connection conn = dbutil.UtilDB.getConnection();
                PreparedStatement s = conn.prepareStatement("SELECT VOTAZIONE.IdVotazione AS IdV,TestoVotazione "
                                                          + "FROM VOTAZIONE,ISCRIZIONE "
                                                          + "WHERE ISCRIZIONE.IdVotazione=VOTAZIONE.IdVotazione AND IdPersona=?");
                s.setInt(1, login.getIdPersona());
                ResultSet rs = s.executeQuery();
                while (rs.next()) {
                    int idv = rs.getInt("IdV");
                    out.print("<p>"
                            + rs.getString("TestoVotazione")
                            + " <a href=listaCoseDaVotare.jsp?id=" + idv +"> vai alle cose da votare </a>");
                }

                rs.close();
                //conn.close();
            }
        else {
            erroriLogin.setMessaggi("Accesso non autorizzato: necessario login");
            response.sendRedirect("index.jsp");
        }
        
        %>
    </body>
</html>

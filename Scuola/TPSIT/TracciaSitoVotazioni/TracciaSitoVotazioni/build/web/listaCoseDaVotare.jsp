<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="login" class="beans.Credenziali"  scope="session" />
<jsp:useBean id="erroriLogin"
             class="beans.ErroriLogin"
             scope="session" />
<jsp:useBean id="datiVoto"
             class="beans.DatiVoto"
             scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="stile/stile.css" title="style" />
        <title>Lista cose votare </title>
    </head>
    <body>
        <script src="js/Data.js"></script>
        <h1> Lista delle cose da votare </h1>

        <% if (login.isAccreditato()) {
                Connection conn = dbutil.UtilDB.getConnection();
                out.println("<br>"+login.getUserName());
                PreparedStatement s = conn.prepareStatement("SELECT IdCosaVotare,TestoCosaVotare "
                                                          + "FROM COSAVOTARE "
                                                           + "WHERE IdVotazione=? AND "
                                                           + "NOT EXISTS(SELECT * "
                                                                      + "FROM VOTO "
                                                                      + "WHERE VOTO.IdCosaVotare=COSAVOTARE.IdCosaVotare AND VOTO.IdPersona=?)"
                                                          );
                s.setInt(1, Integer.parseInt(request.getParameter("id")));
                s.setInt(2, login.getIdPersona());
                ResultSet rs = s.executeQuery();
                while (rs.next()) {
                    int idc = rs.getInt("IdCosaVotare");
                    String testoCosaDaVotare=rs.getString("TestoCosaVotare");
                    out.print("<p>"
                            + testoCosaDaVotare
                            + " <a href=vota.jsp?id=" + idc + "&testo="+testoCosaDaVotare+"> vota </a>");
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

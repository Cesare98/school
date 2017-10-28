

<%@page import="static dbutil.UtilDB.creaListaTelefoni"%>
<%@page import="dati.Telefono"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="persona" class="dati.Persona"  scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="stile/stile.css" title="style" />
        <title>Lista</title>
    </head>
    <body>
        <script src="js/Data.js"></script>
        <h1> Votazioni </h1>

        <a href="inserimentoNuovaPersona.jsp"> Inserimento di una nuova Persona</a>
        <%
            Connection conn = dbutil.UtilDB.getConnection();
            if (conn == null) {
                response.sendRedirect("ConnessioneNonRiuscita");
            } else {
                PreparedStatement s = conn.prepareStatement("SELECT * FROM PERSONA");
                ResultSet rs = s.executeQuery();
                while (rs.next()) {
                    int idp = rs.getInt("IdPersona");
                    persona.setCognomeNome(rs.getString("CognomeNome"));
                    persona.setIndirizzo(rs.getString("Indirizzo"));
                    persona.setUserName(rs.getString("UserName"));
                    persona.setPassword(rs.getString("Password"));
                    out.print("<p>" + persona
                            + " <a href=\"doModifica.jsp?id=" + idp + "\"> modifica </a> ----"
                            + "<a href=\"doCancella.jsp?id=" + idp + "\"> cancella </a>" + "</p>");
                    //tecnica alternativa, basata sulla programmazione ad oggetti
					out.print("<img href=\"img\\"+persona.getUserName()+".jpg\" />"); 
					for (Telefono tel : creaListaTelefoni(idp)) {
                        out.print("<p> " + tel);
                    }

                }
                rs.close();
                //conn.close();
            }
        %>
    </body>
</html>

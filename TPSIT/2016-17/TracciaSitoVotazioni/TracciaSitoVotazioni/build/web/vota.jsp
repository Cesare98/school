<%@page import="dbutil.UtilDB"%>
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
        <%= login.getUserName()%> <br>
        <h1> Voti su: <%=request.getParameter("testo")%> </h1>
         <% if (login.isAccreditato()) {
             datiVoto.setIdCosaVotare(Integer.parseInt(request.getParameter("id")));
            } else {
                erroriLogin.setMessaggi("Accesso non autorizzato: necessario login");
                response.sendRedirect("index.jsp");
            }
        %>
        <form action="doVota.jsp" >
            <input  type = "radio" name = "getVoto" value = "S" > SI <br>
            <input  type = "radio" name = "getVoto" value = "N" > NO <br>
            <input  type = "radio" name = "getVoto" value = "A" > ASTENUTO <br>
            <br>
            <input  type="submit" value="VOTA"/>
        </form>


       
    </body>
</html>



<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        <link rel="stylesheet" type="text/css" href="stile/stile.css" title="style" />
        <title> Autenticazione votante</title>
    </head>
    <body>
        <h1>  Votazioni - autenticazione votante </h1>
        <div class="errorMessage"> ${erroriLogin.messaggi} </div>
        <form action="doVerificaLogin.jsp" >
            <p> Username  <input type="text"  name="userName" value="${login.userName}"> </p>
            <p> Password  <input type="password"  name="password" value="${login.password}"> </p>
            <p> <input type="submit" value="LOGIN" > </p>
        </form>


    </body>
</html>

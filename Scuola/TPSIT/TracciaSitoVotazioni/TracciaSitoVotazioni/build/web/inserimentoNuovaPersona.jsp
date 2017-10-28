
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" type="text/css" href="stile/stile.css" title="style" />
        <title> Inserimento nuova persona</title>
    </head>
    <body>
        <div class="errorMessage"> ${erroriReg.messaggi} </div>
        <form action="doInserimentoNuovaPersona.jsp" >
            <p> Cognome e Nome * <input type="text"  name="cognomeNome" value="${reg.cognomeNome}"> </p>
            <p> indirizzo<input type="text"  name="indirizzo" value="${reg.indirizzo}" > </p>
            <p>  username* <input type="text"  name="userName" value="${reg.userName}"> </p>
            <p> password * <input type="password"  name="password1" value="${reg.password1}"> </p>
            <p> ripeti password*<input type="text"  name="password2" value="${reg.password2}"> </p>
            <p> <input type="submit" value="Inserisci" > </p>
        </form>
            * = campi obbligatori

    </body>
</html>

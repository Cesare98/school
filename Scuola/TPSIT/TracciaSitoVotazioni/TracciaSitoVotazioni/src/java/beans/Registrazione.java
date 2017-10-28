
package beans;

import dbutil.UtilDB;

public class Registrazione {

    protected String cognomeNome, indirizzo, userName, password1, password2;

    final static String NAME_PATTERN = "^[A-Z]{1}[a-z,A-Z' ] +$";
    final static String USERNAME_PATTERN = "^[A-Za-z0-9_.]+$";
    final static String PASSWORD_PATTERN = "^.*(?=.{4,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";

    public Registrazione() {
        cognomeNome=indirizzo=userName=password1=password2="";
    }

    public String getCognomeNome() {
        return cognomeNome;
    }

    public void setCognomeNome(String cognomeNome) {
        this.cognomeNome = cognomeNome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password) {
        this.password2 = password;
    }

    public String validate() {
        String errReport = "";
        // controllo cognome nome
        if (cognomeNome == null || cognomeNome.isEmpty()) {
            errReport += "Cognome Nome è obbligatorio. ";
        } else if (!cognomeNome.matches(USERNAME_PATTERN)) {
            errReport += "cognomeNome non valido \n";
        } else if (UtilDB.esisteCognomeNome(cognomeNome)) {
            errReport += "cognomeNome  già  esistente. ";
        }
        // controllo username
        if (userName == null || userName.isEmpty()) {
            errReport += "Lo username è obbligatorio. ";
        } else if (!userName.matches(USERNAME_PATTERN)) {
            errReport += "username non valido \n";
        } else if (UtilDB.esisteUserName(userName)) {
            errReport += "username  già  usato. ";
        }
        // controllo password           
        if (password1 == null || password1.isEmpty()) {
            errReport += "La password è obbligatoria. ";

        } else if (!password1.matches(PASSWORD_PATTERN)) {
            errReport += "La password deve contenere almeno una cifra, una lettera maiuscola e minuscola e deve essere lunga almeno 8 caratteri. ";
        } else if (!password2.equals(password1)) {
            errReport += "Le due password non coincidono. ";
        }
        return errReport;

    }


}

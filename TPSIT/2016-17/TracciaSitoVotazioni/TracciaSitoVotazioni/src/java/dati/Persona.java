
package dati;


public class Persona {
 
    protected String cognomeNome,indirizzo,userName,password;

    public Persona() {
        this.cognomeNome = "";
        this.indirizzo = "";
        this.userName = "";
        this.password = "";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    @Override
    public String toString() {
        return "Persona{  cognomeNome=" + cognomeNome+
                ", indirizzo=" + indirizzo + ", username=" + userName + ",password=" + password + '}';
    }
}

package beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



public class Credenziali {
    
    protected String userName,password;
    protected int idPersona;
    protected boolean accreditato;

    public boolean isAccreditato() {
        return accreditato;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public void setAccreditato(boolean accreditato) {
        this.accreditato = accreditato;
    }

      
    public Credenziali(){
        accreditato=false;
        idPersona=-1;
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
}
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author bc
 */
public class DatiVoto {
    protected String cosaDaVotare, Votazione, Voto;
    protected int idCosaVotare, idPersona;

    public String getVotazione() {
        return Votazione;
    }

    public void setVotazione(String Votazione) {
        this.Votazione = Votazione;
    }

    public String getVoto() {
        return Voto;
    }

    public void setVoto(String Voto) {
        this.Voto = Voto;
    }

    public int getIdCosaVotare() {
        return idCosaVotare;
    }

    public void setIdCosaVotare(int idCosaVotare) {
        this.idCosaVotare = idCosaVotare;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getCosaDaVotare() {
        return cosaDaVotare;
    }

    public void setCosaDaVotare(String cosaDaVotare) {
        this.cosaDaVotare = cosaDaVotare;
    }
    
    
}

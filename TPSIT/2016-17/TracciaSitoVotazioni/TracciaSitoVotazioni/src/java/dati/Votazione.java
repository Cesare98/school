/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dati;

/**
 *
 * @author bc
 */
public class Votazione {
    
    protected String testoVotazione,dataOraInizioVotazione,dataOraFineVotazione;

    public String getTestoVotazione() {
        return testoVotazione;
    }

    public void setTestoVotazione(String testoVotazione) {
        this.testoVotazione = testoVotazione;
    }

    public String getDataOraInizioVotazione() {
        return dataOraInizioVotazione;
    }

    public void setDataOraInizioVotazione(String dataOraInizioVotazione) {
        this.dataOraInizioVotazione = dataOraInizioVotazione;
    }

    public String getDataOraFineVotazione() {
        return dataOraFineVotazione;
    }

    public void setDataOraFineVotazione(String DataOraFineVotazione) {
        this.dataOraFineVotazione = DataOraFineVotazione;
    }

    public Votazione(String testoVotazione, String dataOraInizioVotazione, String DataOraFineVotazione) {
        this.testoVotazione = testoVotazione;
        this.dataOraInizioVotazione = dataOraInizioVotazione;
        this.dataOraFineVotazione = DataOraFineVotazione;
    }
    
     @Override
    public String toString() {
        return "Votazione{  su =" + testoVotazione+
                ", inizio=" + dataOraInizioVotazione + ", fine=" + dataOraFineVotazione + '}';
    }
    

    
}

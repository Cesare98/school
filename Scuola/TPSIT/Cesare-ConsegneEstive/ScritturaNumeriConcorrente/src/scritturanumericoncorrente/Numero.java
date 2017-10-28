/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scritturanumericoncorrente;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Numero {

    private int n;
    private boolean trovato;
    ScritturaInizio inizio;
    ScritturaFine fine;

    public Numero(int n, ScritturaInizio inizio, ScritturaFine fine) {
        this.n = n;
        this.trovato = false;
        this.inizio = inizio;
        this.fine = fine;
    }

    public synchronized void run() {
        while (!this.trovato ) {

            if (inizio.getN() == fine.getN() || !inizio.isAlive() || !fine.isAlive()) {
                        fine.stopThis();
               inizio.stopThis();
               this.stopThis();
                
               if(!inizio.isAlive() && !fine.isAlive())
                    System.out.println("Operazione di ricerca fallita");
               
               if(inizio.getN()==fine.getN())
                System.out.println("Ci siamo incontrati al numero " + inizio.getN());
            
            }
            
        }
    }
    
    public void stopThis(){trovato=true;}
    
    public boolean isRunning(){return trovato;}
}

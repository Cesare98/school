package scritturanumericoncorrente;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ScritturaNumeriConcorrente {

    public static boolean trovato = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int n;
        System.out.println("Inserire il numero ");
        Scanner input = new Scanner(System.in);
        n = input.nextInt();

        ScritturaInizio inizio = new ScritturaInizio(n);
        ScritturaFine fine = new ScritturaFine(n);
        Numero num = new Numero(n, inizio, fine);
        inizio.start();
        fine.start();
        num.run();
        
        
        if (num.isRunning()) {
            try {
                inizio.join();
                fine.join();
                num = null;
                System.out.println("Programma terminato");
            } catch (InterruptedException ex) {
                Logger.getLogger(ScritturaNumeriConcorrente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

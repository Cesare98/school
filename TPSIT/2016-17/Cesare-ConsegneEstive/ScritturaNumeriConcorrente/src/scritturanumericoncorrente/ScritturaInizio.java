package scritturanumericoncorrente;

import static java.lang.Thread.sleep;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author david
 */
public class ScritturaInizio extends Thread {

    private int n;
    private int d;
    private boolean running;

    public ScritturaInizio(int d) {
        this.n = 1;
        this.d=d;
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {
            if(this.n > d)
            {
            this.stopThis();
            }
            System.out.println("sono il thread uno " + n);
            n++;
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ScritturaFine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stopThis() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int getN() {
        return n;
    }

}

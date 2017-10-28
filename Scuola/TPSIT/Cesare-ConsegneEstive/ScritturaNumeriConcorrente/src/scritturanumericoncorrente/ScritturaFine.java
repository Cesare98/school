package scritturanumericoncorrente;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ScritturaFine extends Thread{
    
   private int n;
   private boolean running;
   
   public ScritturaFine(int n)
    {
        this.n=n;
        this.running = true;
    }
    
    @Override
      public  void  run()
    {
        while(running)
        {   
            if(n < 1)
            {
                this.stopThis();
            }
            try {

               System.out.println("sono il thread due "+n);
                n--;
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
      
    public int getN(){return n;}
    
}

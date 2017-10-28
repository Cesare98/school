
 
package modelli;

/**
 *
 * @author gc
 */
public class Contatore {
    
    private int cont;
    
    public Contatore(){
        cont=0;
    }
    
    public void incr(){
        cont++;
    }
    
    public void decr(){
        cont--;
    }
    
    
    public void set(int x){
        cont=x;
    }
    public int get(){
        return cont;
    }
    
}

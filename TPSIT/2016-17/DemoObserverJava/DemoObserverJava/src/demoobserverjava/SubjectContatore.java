/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoobserverjava;

import java.util.Observable;


public class SubjectContatore extends Observable {
    
    private int cont;
    SubjectContatore(){
        cont=0;
    }
    
    void incr(){
        cont++;
        refresh(); //aggiorna le viste

    }
    
    void decr(){
        cont--;
        refresh();//aggiorna le viste

    }

    public int getCont() {
        return cont;
    }
    
    public void refresh(){  //aggiorna le viste
        setChanged();
        notifyObservers();
    }
    
    
    
    
    
}

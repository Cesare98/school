/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giostra;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class Bambino extends Thread{
    
    private String name;
   private Carosello car;
   private boolean seat= false;
   private Random rnd;
    
   public Bambino(){}
   
    public Bambino(String name,Carosello car)
    {
        this.name=name;
        this.car=car;
    }
    
    @Override
    public void run()
    {
            while(true)
            {
                if(car.isSeatFree() && !seat)
                {
                    System.out.println("Sono " + name +" sto salendo sulla giostra");
                    car.setSeatNumber(car.getSeatNumber()-1);
                    seat=true;
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Bambino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(seat && car.getSeatNumber()<=car.getRealSeatNumber())
                    {
                        seat = false;
                        car.setSeatNumber(car.getSeatNumber()+1);
                    }
                }
                    
                if(!car.isSeatFree() )
                {
                    System.out.println("Sono "+ name + " sto dormendo");
                }try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bambino.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giostra;

/**
 *
 * @author david
 */
public class Carosello {
    
    private int seatNumber;
    private int realSeatNumber;
    private boolean isSetFree; 
    
    Carosello(int seatNumber)
    {
        realSeatNumber = seatNumber;
       setSeatNumber(seatNumber);
       setIsSeatFree(true);
    }
    
    public synchronized int getSeatNumber()
    {
        return this.seatNumber;
    }
    
    public synchronized void setSeatNumber(int seatNumber)
    {
     this.seatNumber = seatNumber;
    }
    
    public synchronized boolean isSeatFree()
    {
        return getSeatNumber()>=1;
    }
    
    public synchronized void setIsSeatFree(boolean b)
    {
        this.isSetFree=b;
    }
    
    public synchronized int getRealSeatNumber(){return realSeatNumber;}
}

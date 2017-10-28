/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoobserverjava;

public class DemoObserverJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        SubjectContatore sc = new SubjectContatore();
        System.out.println("Creato oggetto da osservare");      
        FrameVistaContatore frVista1 = new  FrameVistaContatore(100,100);
         System.out.println("Creata una prima vista");   
        sc.addObserver(frVista1);
        System.out.println("Aggiunta all'elenco degli osservatori");
        FrameVistaContatore frVista2 = new  FrameVistaContatore(200,200);
        System.out.println("Creata una seconda vista");   
        sc.addObserver(frVista2);
        System.out.println("Aggiunta all'elenco degli osservatori");        
        sc.refresh();
        Thread.sleep(5000);
        for (int i=0;i<5;i++){
            sc.incr();
            System.out.println("incrementato il contatore");
            System.out.println("Notare l'aggiornamento contemporaneo delle viste ");
            Thread.sleep(2000);
        }     
         sc.decr();
        System.out.println("decrementato il contatore");
        System.out.println("Notare l'aggiornamento contemporaneo delle viste ");
        Thread.sleep(5000);
        System.exit(0);
        
        
    }
    
}

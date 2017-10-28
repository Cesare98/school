/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giostra;

import java.util.Scanner;

/**
 *
 * @author david
 */
public class Giostra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numBambini = 0;
        int numPosti = 0;
        System.out.println("inserire il numero di posti : ");
        Scanner input = new Scanner(System.in);
        numPosti = input.nextInt();
        Carosello car = new Carosello(numPosti);
        System.out.println("Inserire quanti bambini ci sono : ");
        numBambini = input.nextInt();
        String name;
        Bambino[] bambini = new Bambino[numBambini];
        for(int i = 0 ; i < numBambini;i++)
        {
            System.out.println("Inserire il nome del bambino ");
           name  = input.nextLine();
            bambini [i] = new Bambino(name,car);
        }
        for(Bambino bamb:bambini)
        {
        bamb.start();
        }
    }
    
}

// scrive e legge un tipo int alla volta in modo sequenziale da un file binario
// con le classi   FileOutputStream/FileInputStream per aprire i file
// DataOutputStream/DataInputStream permettono di scrivere/leggere dati tipizzati


// memorizza su file binari

import java.io.*;


public class AccessoCasuale{


public static void main(String args[]) throws IOException{
  System.out.println("prova file sequenziali contenenti un tipo int");

      RandomAccessFile dout= new RandomAccessFile("int.dat","rw");
     		//apre un file in scrittura
			// esiste l'apertura in append
     // DataOutputStream dout= new DataOutputStream(dout);

      dout.writeInt(11000); 	dout.writeInt(22200);	dout.writeInt(33330);
      dout.writeInt(44444); 	dout.writeInt(55555); 	dout.writeInt(66666);
      dout.close();

      RandomAccessFile din= new RandomAccessFile("int.dat","r");
      					// apre un file in lettura e si posiziona sul primo byte

      double c= din.readInt();	// legge un intero di 4 byte dal file
      while( c!= -1){				// se vale -1 è il fine file
		  System.out.println("\n  dato: "+ c);
	      c=din.readInt();
       } // while

      din.close();

}// main

}// class
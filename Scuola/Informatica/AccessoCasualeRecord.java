// scrive e legge un array di byte alla volta in modo sequenziale
// usa le classi   FileOutputStream/FileInputStream per aprire i file
// DataOutputStream/DataInputStream permettono di scrivere/leggere dati tipizzati


import java.io.*;

public class AccessoCasualeRecord{

public static void main(String args[]) throws IOException{
  System.out.println("prova file sequenziali contenenti un record");

      RandomAccessFile dout= new RandomAccessFile("record.dat","rw");
     		//apre un file in lettura/scrittura

      byte [] b1= {'1','2','3','4','5'};
      byte [] b2= {'A','B','C','D','E'};
      byte [] b3= {'a','b','c','d','e'};

      dout.write(b1); 	dout.write(b2);	dout.write(b3);

      dout.close();


      RandomAccessFile din= new RandomAccessFile("record.dat","r");
      					// apre un file in lettura e si posiziona sul primo byte

     // DataInputStream din= new DataInputStream(fin);

      byte [] b= new byte[5];
      int s;
      do{							// con do-while legge 2 volte l'ultimo elemento
	      s=din.read(b);
	      String str= new String(b);
	      System.out.println("\n  dato: "+ str);
       }  while (s>=0); // se vale -1 è il fine file


       din.seek(5);    // si posiziona sul secondo record
	    s=din.read(b);  // se s== -1 fine file
	    String str= new String(b);
	    System.out.println("\n  2° dato: "+ str);

		 long pos= din.getFilePointer();   // posizione corrente del puntatore
		 System.out.println("\n  posizione: "+ pos);

		 long lun= din.length();   // posizione corrente del puntatore
		 System.out.println("\n  dimensione file: "+ lun);

      din.close();

}// main

}// class
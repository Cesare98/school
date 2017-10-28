// scrive e legge una linea anche sequenzialmente
// usa le classi   FileOutputStream/FileInputStream per aprire i file
// DataOutputStream/DataInputStream permettono di scrivere/leggere dati tipizzati


import java.io.*;

public class AccessoCasualeLinea{

public static void main(String args[]) throws IOException{
  System.out.println("prova file sequenziali contenenti un record");

      RandomAccessFile dout= new RandomAccessFile("record.dat","rw");
     		//apre un file in lettura/scrittura

      byte [] b1= {'1','2','3','4','\n'};
      byte [] b2= {'A','B','C','D','\n'};
      byte [] b3= {'a','b','c','d','\n'};

      dout.write(b1); 	dout.write(b2);	dout.write(b3);

      dout.close();


      RandomAccessFile din= new RandomAccessFile("record.dat","r");
      					// apre un file in lettura e si posiziona sul primo byte

		String str;
      do{							// con do-while legge 2 volte l'ultimo elemento
	      str= din.readLine();
	      System.out.println("\n  dato: "+ str);
       }  while (str!=null); // se vale null è  fine file

	   din.seek(5);
		str= din.readLine();
	   System.out.println("\n\n\n si posiziona sul 2 dato: "+ str);



      din.close();

}// main

}// class
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eserciziohash;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class EsercizioHash
{

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        String name = "2";
        Scanner input = new Scanner(System.in);

        int scelta = 0;
        while (scelta != 3)
        {
            System.out.println("selezionare l'opzione:");
            System.out.println("1) Ricerca file hash");
            System.out.println("2) ricerca nome");
            System.out.println("3) chiudere programma");
            scelta = input.nextInt();
            if (scelta == 1)
            {
                RandomAccessFile output = new RandomAccessFile("SecondaProva.txt", "rw");
                String sb = new String();
                for (int i = 0; i < 99; i++)
                {
                    sb = sb + '*';
                }

                sb = sb + '\n';
                for (int i = 0; i < 500; i++)
                {
                    try
                    {
                        output.write(sb.getBytes());
                    }
                    catch (IOException ex)
                    {
                    }
                }
                Scanner scan = new Scanner(new FileReader("agenda_telefonica.txt"));
                try
                {

                    while (scan.hasNextLine())
                    {
                        name = scan.nextLine();
                        int pos1 = name.indexOf('\t');
                        String cognome = name.substring(0, pos1);
                        String resto = name.substring(pos1 + 1);
                        int pos2 = name.indexOf('\t');
                        String nome = resto.substring(0, pos2);
                        String cognome_nome = cognome + " " + nome;
                        long posRiga = Math.abs(cognome_nome.hashCode()) % 100;
                        output.seek(posRiga * 100);
                        output.write(name.getBytes());
                    }

                }
                catch (IOException ex)

                {
                    Logger.getLogger(EsercizioHash.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (scelta == 2)
            {
                try
                {
                    RandomAccessFile file = new RandomAccessFile("SecondaProva.txt", "r");
                    System.out.println("Inserire il cognome");
                    String cognome = input.next();
                    System.out.println("Inserire il nome");
                    String nome = input.next();
                    String cognome_nome = cognome.toUpperCase() + " " + nome.toUpperCase();
                    long posRiga = Math.abs(cognome_nome.hashCode()) % 100;
                    file.seek(posRiga * 100);
                    System.out.println(file.readLine());
                }
                catch (IOException ex)
                {
                    Logger.getLogger(EsercizioHash.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    
}

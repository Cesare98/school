/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author david
 */
public class ClientStr {

String nomeServer = "localhost";
int portaServer = 8080;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
  String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    
    public Socket connetti()
    {
        System.out.println("2 ... CLIENT partito in esecuzione ...");
        try
        {
        //per l'input da tastiera
            tastiera = new BufferedReader (new InputStreamReader(System.in));
            //creo in socket 
            mioSocket = new Socket(nomeServer,portaServer);
            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer= new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            
        }catch(UnknownHostException e)
        {
        System.err.println("Host sconosciuto");
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
        System.exit(1);
        }
        return mioSocket;
    }
    
    public void comunica ()
    {
    try
    {
        System.out.println("4 ... inserici la stringa da trasmettere al server "+'\n');
        stringaUtente = tastiera.readLine();
        //la spedisco al server
        System.out.println("5 ... invio la stringa al server e attendo ...");
        outVersoServer.writeBytes(stringaUtente+'\n');
        //legge la risposta dal server
        stringaRicevutaDalServer = inDalServer.readLine();
        System.out.println("8 ... risposta dal server "+'\n'+stringaRicevutaDalServer);
        //chiudo la connessione
        System.out.println("9 CLIENT: termina elaberazione col server");
        mioSocket.close();
    }
    catch(Exception e)
    {
        System.out.println(e.getMessage());
        System.out.println("Errore durante la comunicazione col server");
        System.exit(1);
    }
    }
    
     public static void main(String[] args) throws IOException {
    ClientStr cliente = new ClientStr();
    cliente.connetti();
    cliente.comunica();
     }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author david
 */
public class ServerStr {

    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalCliente;
    DataOutputStream outVersoIlCliente;

    public Socket attendi() {
        try {
            System.out.println("1 SERVER partito in esecuzione ...");
            //crea un server sulla porta 8080
            server = new ServerSocket(8080);
            //rimane in attesa di un client
            client = server.accept();
            //chiudo il server per inibire altri client
            server.close();
            //associo due oggetti al socket per effettuare lettura e scrittura nel client
            inDalCliente = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoIlCliente = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server");
            System.exit(1);
        }
        return client;
    }

    public void comunica() throws IOException
    {
     //rimango in attesa della riga scritta dal client
        System.out.println("3 Benvenuto client, scrivi una frase e la trasformo in maiuscolo. Attendo ...");
        stringaRicevuta = inDalCliente.readLine();
        System.out.println("6 rcevuta la stringa da cliente ! "+stringaRicevuta);
        
        //la modifica e rispedisce al mittente
        stringaModificata = stringaRicevuta.toUpperCase();
        System.out.println("7 invio la stringa modificata al client ...");
        outVersoIlCliente.writeBytes(stringaRicevuta+'\n');
        
        System.out.println("9 SERVER: fine elaborazione ... buona notte!");
        client.close();
    }
    
     public static void main(String[] args) throws IOException {
        ServerStr servente = new ServerStr();
        servente.attendi();
        servente.comunica();
    }
}

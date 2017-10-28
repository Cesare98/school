/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensernumeri;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Client
{

    String nomeServer = "localhost";
    int portaServer = 8080;
    Socket mySocket;
    BufferedReader inDalServer;

    public Socket connetti()
    {
	try
	{
	    System.out.println("Vorrei un numero per la coda");
	    mySocket = new Socket(nomeServer, portaServer);
	    inDalServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	return mySocket;
    }

    public void ottieniNumero()
    {
	try
	{
	   while(true){
		String numero =inDalServer.readLine();
		System.out.println("Serve il numero " + numero);
	   }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void main(String[] args)
    {
	Client cliente = new Client();
	cliente.connetti();
	cliente.ottieniNumero();

    }
}

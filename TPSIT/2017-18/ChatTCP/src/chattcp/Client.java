/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class Client implements Runnable
{

    private BufferedReader fromKeyboard;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private Socket mySocket;
    String nameServer = "localhost";
    int portServer = 8080;
    private String username;
    private char[] password;
    private boolean quit = false;
    private static Thread listener;//thread che ascolta il server se arriva un messaggio lo mette a video

    public static void main(String[] args)
    {
	Client client = new Client();
    }

    public Client()
    {
	this.connect();
	this.sendCredentials();
	Chat.setAuthentication(false);
	listener = new Thread(this);
	listener.start();
	this.sendMessage();
    }

    //initizialize the socket
    synchronized private Socket connect()
    {
	try
	{
	    fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
	    mySocket = new Socket(nameServer, portServer);
	    toServer = new PrintWriter(mySocket.getOutputStream(), true);
	    fromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	return mySocket;
    }

    @Override
    public void run()
    {
	try
	{
	    while (true)
	    {
		String message = fromServer.readLine();
		System.out.println(message);

	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	finally
	{
	    try
	    {
		toServer.close();
		fromServer.close();
		listener = null;
	    }
	    catch (IOException ex)
	    {
		Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    //check if the user is regitred
    synchronized private void sendCredentials()
    {
	try
	{
	    do
	    {

		System.out.println("Insert the username:");
		username = fromKeyboard.readLine();
		System.out.println("Insert the password:");
		password = new char[18];
		password = fromKeyboard.readLine().toCharArray();
	    }
	    while (!Chat.checkCredentials(username, password));
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    synchronized private void sendMessage()
    {
	String message;
	try
	{
	    while (true)
	    {
		message = fromKeyboard.readLine();
		if (message != null && username != null)
		{
		    toServer.println(username);
		    toServer.println(message);
		}

	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	}
	finally
	{
	    try
	    {
		username = null;
		fromServer.close();
		toServer.close();
		fromKeyboard.close();
		mySocket.close();
	    }
	    catch (IOException ex)
	    {
		Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
}

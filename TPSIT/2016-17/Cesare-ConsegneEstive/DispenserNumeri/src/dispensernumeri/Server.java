/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensernumeri;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Server
{

    ServerSocket server = null;
    Socket client = null;
    PrintWriter outToClient;

    public Socket attendi()
    {
	try
	{
	    System.out.println("Server partito...");
	    server = new ServerSocket(8080);
	    client = server.accept();
	    server.close();
	    outToClient = new PrintWriter(client.getOutputStream(),true);
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	}
	return client;
    }

    public  void comunica()
    {
	int num = 1;
	while (true)
	{
	    System.out.println("invio del numero al cliente " + num);
	    outToClient.println(String.valueOf(num));
	    num++;
	}

    }

    public static void main(String[] args)
    {
	Server server = new Server();
	server.attendi();
	server.comunica();

    }
}

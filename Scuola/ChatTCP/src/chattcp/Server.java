/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chattcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide.cesare
 */
public class Server
{

    private static SQLHelper helper = new SQLHelper();

    public static void main(String[] args)
    {
	try
	{
	    ServerSocket server = new ServerSocket(8080);
	    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	    helper.createDatabase();
	    helper.createNewTable();
	    while (true)
	    {
		executor.execute(new Chat(server.accept()));
	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

}

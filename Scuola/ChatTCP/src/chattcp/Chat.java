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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Chat implements Runnable
{

    private Socket s;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    protected static Vector clients = new Vector();//client connessi al server statico perché esiste una sola istanza she tutti condividono
    private static boolean authentication = false;
    private static SQLHelper helper = new SQLHelper();
    private boolean quit = false;

    public Chat(Socket s)
    {
	try
	{
	    this.s = s;
	    fromClient = new BufferedReader(new InputStreamReader(s.getInputStream()));
	    toClient = new PrintWriter(s.getOutputStream(), true);
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void run()
    {
	try
	{
	    clients.add(this);
	    while (true)
	    {
		String message = fromClient.readLine();
		broadcast(message);
	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);

	}
	finally
	{
	    try
	    {
		clients.remove(this);
		s.close();
	    }
	    catch (IOException ex)
	    {
		Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    synchronized void broadcast(String message)
    {
	Enumeration e = clients.elements();//iteratore 
	while (e.hasMoreElements())//finché l'iteratore non ha completato la lista 
	{
	    Chat c = (Chat) e.nextElement();//prende il prossimo socket della lista
	    c.toClient.println(message);
	    c.toClient.flush();
	}
    }

    /*
	@param username user username
    @param password user passord
    check if the the user is register he log in otherwise the program register the 
    new account
     */
    public static boolean checkCredentials(String username, char[] password)
    {
	try
	{
	    Connection conn = helper.connect();
	    ResultSet rs = null;
	    PreparedStatement st = null;
	    String sql = ("SELECT username,password FROM dati WHERE username=? AND password=?");
	    st = conn.prepareStatement(sql);
	    st.setString(1, username);
	    st.setString(2, Arrays.toString(password));
	    rs = st.executeQuery();
	    if (rs.next())
	    {
		authentication = true;
		System.out.println(username + " has joined the chat");
	    }
	    else
	    {
		if (validatePassword(password))
		{
		    helper.addNewUser(username, Arrays.toString(password));
		}
		System.out.println("The password doesn't encounter the minum requirements");
	    }
	    st.close();
	    conn.close();
	    rs.close();
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
	}

	return authentication;
    }

    //check if the password follow the minum requirements
    public static boolean validatePassword(char[] password)
    {
	boolean alright = false;
	if (password == null || password.length > 18)
	{
	    return alright;
	}
	boolean upper = false;
	boolean lower = false;
	boolean digit = false;
	boolean symbol = false;
	for (char c : password)
	{
	    if (Character.isUpperCase(c))
	    {
		upper = true;
	    }
	    else if (Character.isLowerCase(c))
	    {
		lower = true;
	    }
	    else if (Character.isDigit(c))
	    {
		digit = true;
	    }
	    else
	    {
		symbol = true;
	    }
	    if (upper && lower && digit && symbol)
	    {
		alright = true;
	    }
	}
	return alright;
    }

    public static void setAuthentication(boolean b)
    {
	authentication = b;
    }
}

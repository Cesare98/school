package contocorrenteweb;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiServer
{

    protected static String fileName = "dati.db";

    protected static boolean authentication = false;

    private static ArrayList<ServerThread> servers = new ArrayList<ServerThread>();

    public void start()
    {
	try
	{
	    int numServer = 0;
	    ContoCorrente cc = new ContoCorrente(10000);
	    ServerSocket serverSocket = new ServerSocket(8080);
	    for (;;)
	    {
		System.out.println("Server in attesa...");
		Socket socket = serverSocket.accept();
		System.out.println("Server socket " + socket);
		ServerThread serverThread = new ServerThread(socket, cc);
		serverThread.start();
		servers.add(numServer, serverThread);
		numServer++;
	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static ArrayList<ServerThread> getServers()
    {
	return MultiServer.servers;
    }

    public static void createUser(String txtServer, int port, String username)
    {
	Cliente client = new Cliente(txtServer, port, username);
	client.connetti();
    }

    public static void createNewDatabase()
    {
	File files = new File("C:\\sqlite2\\database");
	if (!files.exists())
	{
	    if (files.mkdirs())
	    {
		System.out.println("Multiple directories are created!");
	    }
	    else
	    {
		System.out.println("Failed to create multiple directories!");
	    }
	}
	String url = "jdbc:sqlite:C:\\sqlite2\\database\\" + fileName;
	try (Connection conn = DriverManager.getConnection(url))
	{
	    if (conn != null)
	    {
		DatabaseMetaData meta = conn.getMetaData();
		System.out.println("The driver name is " + meta.getDriverName());
		System.out.println("A new database has been created.");
		createNewTable();
		System.out.println("Table created");
	    }
	    conn.close();
	}
	catch (SQLException e)
	{
	    System.out.println(e.getMessage());
	}
    }

    public static void createNewTable()
    {
	Connection conn = null;
	Statement stmt = null;
	try
	{
	    Class.forName("org.sqlite.JDBC");
	    conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite2\\database\\" + fileName);
	    System.out.println("Opened database successfully");
	    stmt = conn.createStatement();
	    String sql = "CREATE TABLE IF NOT EXISTS dati(\n " + "id INTEGER  PRIMARY KEY,\n" + "username      TEXT        NOT NULL,\n" + "password      TEXT        NOT NULL\n" + ");";
	    stmt.executeUpdate(sql);
	    stmt.close();
	}
	catch (ClassNotFoundException ex)
	{
	    Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	}
	System.out.println("Table created successufully");
	try
	{
	    conn.close();
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static void addNewUser(String username, String password)
    {
	String credentials = "INSERT INTO dati VALUES(?,?,?);";
	try (Connection conn = MultiServer.connect();
		PreparedStatement pstmt = conn.prepareStatement(credentials))
	{
	    pstmt.setString(2, username);
	    pstmt.setString(3, password);
	    pstmt.executeUpdate();
	    pstmt.close();
	    conn.close();
	}
	catch (SQLException ex)
	{
	}
    }

    public static void checkCredentials(String username, char[] password)
    {
	try
	{
	    Connection conn = MultiServer.connect();
	    ResultSet rs = null;
	    PreparedStatement pst = null;
	    try
	    {
		String sql = "SELECT * FROM dati WHERE username=? AND password=?";
		pst = conn.prepareStatement(sql);
		pst.setString(1, username);
		pst.setString(2, Arrays.toString(password));
		rs = pst.executeQuery();
		if (rs.next())
		{
		    authentication = true;
		}
	    }
	    catch (SQLException ex)
	    {
		Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    pst.close();
	    conn.close();
	    rs.close();
	}
	catch (SQLException ex)
	{
	    Logger.getLogger(MultiServer.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    private static Connection connect()
    {
	String url = "jdbc:sqlite:C:\\sqlite2\\database\\" + fileName;
	Connection conn = null;
	try
	{
	    conn = DriverManager.getConnection(url);
	}
	catch (SQLException ex)
	{
	}
	return conn;
    }

    public static boolean getAuthentication()
    {
	return authentication;
    }

    public static void setAuthentication(boolean b)
    {
	authentication = b;
    }

    public static void main(String[] args)
    {
	ServerFrame frame = new ServerFrame();
    }
}

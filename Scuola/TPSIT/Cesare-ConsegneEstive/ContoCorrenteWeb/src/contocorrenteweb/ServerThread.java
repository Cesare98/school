package contocorrenteweb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread
{

    private Socket client = null;

    private DataInputStream inDalClient;

    private DataOutputStream outVersoClient;

    private ContoCorrente cc;

    public ServerThread(Socket socket, ContoCorrente cc)
    {
	this.client = socket;
	this.cc = cc;
    }

    @Override
    public void run()
    {
	comunica();
    }

    public synchronized void comunica()
    {
	String importo;
	try
	{
	    outVersoClient = new DataOutputStream(client.getOutputStream());
	    inDalClient = new DataInputStream(client.getInputStream());
	    for (;;)
	    {
		switch (inDalClient.readByte())
		{
		    case 1:
			importo = inDalClient.readUTF();
			if (Integer.parseInt(importo) <= cc.getSoldiConto())
			{
			    cc.bankWithdrawal(importo);
			    outVersoClient.writeUTF("ok");
			    outVersoClient.writeUTF(String.valueOf(cc.getSoldiConto()));
			}
			else
			{
			    outVersoClient.writeUTF("no");
			}
			break;
		    case 2:
			importo = inDalClient.readUTF();
			cc.bankDeposit(importo);
			outVersoClient.writeUTF("ok");
			outVersoClient.writeUTF(String.valueOf(cc.getSoldiConto()));
			break;
		}
	    }
	}
	catch (IOException ex)
	{
	    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public ContoCorrente getConto()
    {
	return cc;
    }

    public int getSoldiConto()
    {
	return cc.getSoldiConto();
    }
}

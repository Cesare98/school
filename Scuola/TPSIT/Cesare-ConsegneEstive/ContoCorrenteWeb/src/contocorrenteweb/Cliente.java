package contocorrenteweb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.YES_NO_OPTION;

public class Cliente extends JFrame implements Observer, ActionListener
{

    private JLabel lblUsername, lblShowUsername, lblFondi, lblShowFondi, lblSoldiConto, lblShowSoldiConto;

    private JButton btnDeposita, btnPreleva;

    private Socket client;

    private String txtServer;

    private DataInputStream inFromServer;

    private DataOutputStream outToServer;

    private String strFromServer;

    private ContoCorrente cc;

    private int money;

    private String userName;

    public Cliente(String txtServer, int port, String userName)
    {
	setTitle("Conto comune");
	setSize(800, 400);
	setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	lblUsername = new JLabel();
	lblUsername.setBounds(80, 70, 200, 30);
	lblUsername.setText("username : ");
	lblShowUsername = new JLabel();
	lblShowUsername.setBounds(300, 70, 200, 30);
	lblShowUsername.setText(userName);
	lblFondi = new JLabel();
	lblFondi.setBounds(80, 110, 200, 30);
	lblFondi.setText("I tuoi fondi :");
	lblShowFondi = new JLabel();
	lblShowFondi.setBounds(300, 110, 200, 30);
	lblSoldiConto = new JLabel();
	lblSoldiConto.setBounds(80, 150, 200, 30);
	lblSoldiConto.setText("I soldi nel conto sono :");
	lblShowSoldiConto = new JLabel();
	lblShowSoldiConto.setBounds(300, 150, 200, 30);
	btnDeposita = new JButton("Deposita");
	btnDeposita.setBounds(100, 200, 100, 30);
	btnDeposita.addActionListener(this);
	btnPreleva = new JButton("Preleva");
	btnPreleva.setBounds(400, 200, 100, 30);
	btnPreleva.addActionListener(this);
	add(lblUsername);
	add(lblShowUsername);
	add(lblFondi);
	add(lblShowFondi);
	add(lblSoldiConto);
	add(lblShowSoldiConto);
	add(btnDeposita);
	add(btnPreleva);
	setVisible(true);
	this.money = 100;
	this.txtServer = txtServer;
	this.userName = userName;
    }

    public synchronized Socket connetti()
    {
	System.out.println("2 CLIENT partito in esecuzione ...");
	try
	{
	    client = new Socket(txtServer, 8080);
	    outToServer = new DataOutputStream(client.getOutputStream());
	    inFromServer = new DataInputStream(client.getInputStream());
	    System.out.println("Sono " + userName + " sono connesso");
	    lblShowFondi.setText(String.valueOf(money));
	}
	catch (IOException ex)
	{
	    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
	}
	return client;
    }

    public synchronized void withdrawalOperation(String value)
    {

	strFromServer = null;

	if (value != null && !value.equals(""))
	{
	    try
	    {
		outToServer.writeByte(1);
		outToServer.writeUTF(value);
		outToServer.flush();
		strFromServer = inFromServer.readUTF();
		if (strFromServer.equals("ok"))
		{
		    strFromServer = inFromServer.readUTF();
		    money += Integer.parseInt(value);
		    lblShowSoldiConto.setText(strFromServer);
		    lblShowFondi.setText(String.valueOf(money));
		}
		if (strFromServer.equals("no"))
		{
		    JOptionPane.showConfirmDialog(this, "I soldi sul conto comune sono insufficienti");
		}
	    }
	    catch (IOException ex)
	    {
		Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    public synchronized void depositOperation(String value)
    {
	if (money > Integer.parseInt(value) && value != null && !value.equals(""))
	{
	    try
	    {
		outToServer.writeByte(2);
		outToServer.writeUTF(value);
		outToServer.flush();
		strFromServer = inFromServer.readUTF();
		if (strFromServer.equals("ok"))
		{
		    strFromServer = inFromServer.readUTF();
		    money -= Integer.parseInt(value);
		    lblShowSoldiConto.setText(strFromServer);
		    lblShowFondi.setText(String.valueOf(money));
		}
	    }
	    catch (IOException ex)
	    {
		Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	else
	{
	    JOptionPane.showConfirmDialog(this, "Soldi insufficienti", "Errore", OK_OPTION);
	}
    }

    public int getMoney()
    {
	return money;
    }

    public ContoCorrente getConto()
    {
	return this.cc;
    }

    public void setConto(ContoCorrente cc)
    {
	this.cc = cc;
    }

    @Override
    public void update(Observable o, Object arg)
    {
	ContoCorrente c = (ContoCorrente) (o);
	lblShowSoldiConto.setText(String.valueOf(c.getSoldiConto()));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	String btnPremuto = e.getActionCommand();
	if (btnPremuto.equals(btnDeposita.getText()))
	{
	    String deposito;
	    deposito = JOptionPane.showInputDialog(this, "Quanti soldi vuoi depositare ?(massimo 999)", "deposito", YES_NO_OPTION);
	    JOptionPane.showInputDialog(this, "Inserire la causale", "Perché deposita?", OK_OPTION);
	    depositOperation(deposito);
	}
	if (btnPremuto.equals(btnPreleva.getText()))
	{
	    String prelievo;
	    prelievo = JOptionPane.showInputDialog(this, "Quanti soldi vuoi prelevare ?(massimo 999)", "prelievo", YES_NO_OPTION);
	    JOptionPane.showInputDialog(this, "Inserire la causale", "Perché deposita?", OK_OPTION);
	    withdrawalOperation(prelievo);
	}
    }
}

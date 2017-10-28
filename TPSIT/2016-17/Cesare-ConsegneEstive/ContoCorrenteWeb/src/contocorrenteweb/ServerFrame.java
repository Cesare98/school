package contocorrenteweb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ServerFrame extends JFrame implements ActionListener
{

    private JButton btnStartServer;

    public ServerFrame()
    {
	setTitle("Server");
	setSize(800, 400);
	setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	btnStartServer = new JButton("Avvia Server");
	btnStartServer.setBounds(400, 200, 100, 30);
	btnStartServer.addActionListener(this);
	add(btnStartServer);
	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	String btnPremuto = e.getActionCommand();
	if (btnPremuto.equals(btnStartServer.getText()))
	{
	    MultiServer server = new MultiServer();
	    server.start();
	}
    }
}

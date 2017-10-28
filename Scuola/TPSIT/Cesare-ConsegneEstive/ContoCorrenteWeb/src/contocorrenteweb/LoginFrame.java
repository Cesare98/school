package contocorrenteweb;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener
{

    JLabel lblUsername, lblPassword;

    JTextField txtUsername;

    JPasswordField jPassword;

    JButton btnLogin, btnRegistrati;

    public LoginFrame()
    {
	setTitle("Autenticazione");
	setSize(800, 400);
	setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	lblUsername = new JLabel("inserire lo username: ");
	lblPassword = new JLabel("Inserire la password");
	txtUsername = new JTextField();
	jPassword = new JPasswordField();
	btnLogin = new JButton("Accedi");
	btnRegistrati = new JButton("Registrati");
	lblUsername.setBounds(80, 70, 200, 30);
	lblPassword.setBounds(80, 110, 200, 30);
	txtUsername.setBounds(300, 70, 200, 30);
	jPassword.setBounds(300, 110, 200, 30);
	btnLogin.setBounds(150, 160, 100, 30);
	btnRegistrati.setBounds(300, 160, 100, 30);
	add(lblUsername);
	add(lblPassword);
	add(txtUsername);
	add(jPassword);
	add(btnLogin);
	add(btnRegistrati);
	btnLogin.addActionListener(this);
	btnRegistrati.addActionListener(this);
	setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
	String btnPremuto = e.getActionCommand();
	String username = null;
	char[] password = new char[18];
	username = txtUsername.getText();
	password = jPassword.getPassword();
	if (btnPremuto.equals(btnRegistrati.getText()))
	{
	    File files = new File("C:\\sqlite2\\database");
	    if (!files.exists())
	    {
		MultiServer.createNewDatabase();
	    }
	    MultiServer.addNewUser(username, Arrays.toString(password));
	}
	if (btnPremuto.equals(btnLogin.getText()))
	{
	    boolean authentication = false;
	    if (Arrays.toString(password).equals("") || Arrays.toString(password) == null || username == null || username.equals(""))
	    {
		JOptionPane.showMessageDialog(rootPane, "Credeziali non valide");
		authentication = false;
	    }
	    if (validatePassword(password))
	    {
		MultiServer.checkCredentials(username, password);
		authentication = MultiServer.getAuthentication();
	    }
	    else
	    {
		JOptionPane.showMessageDialog(rootPane, "La password deve :\n -essere lunga meno di 18 caratteri\n" + "- avere una lettera maiuscola e una minuscola\n" + "- avere una cifra \n" + "- e un carattere speciale");
	    }
	    if (authentication)
	    {
		MultiServer.setAuthentication(false);
		JOptionPane.showMessageDialog(rootPane, "Benvenuto " + username);
		MultiServer.createUser("localhost", 8080, username);
		txtUsername.setText("");
		jPassword.setText("");
		this.dispose();
	    }
	}
    }

    boolean validatePassword(char[] password)
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
}

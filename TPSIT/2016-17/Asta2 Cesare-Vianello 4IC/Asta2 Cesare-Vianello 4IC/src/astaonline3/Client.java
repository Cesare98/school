package astaonline3;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    static int PORTA = 3333;

    public static void main(String[] args) throws IOException {
        String nome = JOptionPane.showInputDialog("Inserire nome:");
        String budget = JOptionPane.showInputDialog("Inserire budget iniziale:");
        String indIP = JOptionPane.showInputDialog("Inserire indirizzo IP del Server:");
        Socket socket = new Socket(indIP, PORTA);
        AstaOnline3 x = new AstaOnline3(nome, budget, socket);
    }
}

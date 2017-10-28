package astaonline3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class AstaOnline3 extends JFrame implements ActionListener {

    private   int N_PORTA = 3333;
    private  String INDIRIZZO_SERVER, nomeCliente;
    private JPanel p, p1;
    private JTextField valOffertaMAX, budget;
    private JButton rilancio;
    private JLabel offertaMAX, budgetTXT, nomeClienteMAX;
    private boolean cambioOgg = false;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter out;
    public static OsservatoreClient sc;
    Integer offertaBase;

    AstaOnline3(String nome, String val_budget, Socket socket) throws IOException {

        setTitle("Asta");
        setLayout(new GridLayout(3, 1));
        sc = new OsservatoreClient();

        this.socket = socket;

        input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        nomeCliente = nome;
        out.println(nomeCliente);

        valOffertaMAX = new JTextField();
        budget = new JTextField();
        valOffertaMAX.setEditable(false);
        budget.setText(val_budget);
        budget.setEditable(false);

        p = new JPanel();
        p1 = new JPanel();
        p.setLayout(new GridLayout(1, 3));
        p1.setLayout(new GridLayout(1, 3));

        offertaMAX = new JLabel("Migliore offerta");
        budgetTXT = new JLabel("Budget:");
        nomeClienteMAX = new JLabel();

        valOffertaMAX.setText(input.readLine());
        offertaBase = Integer.parseInt(valOffertaMAX.getText());

        rilancio = new JButton();
        rilancio.setText("RILANCIA");
        rilancio.addActionListener(this);

        p.add(offertaMAX);
        p.add(valOffertaMAX);
        p.add(nomeClienteMAX);
        p1.add(rilancio);
        p1.add(budgetTXT);
        p1.add(budget);

        pack();
        add(sc);
        add(p);
        add(p1);
        setVisible(true);
        setSize(500, 200);
        setLocation(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void restart() throws IOException {
        valOffertaMAX.setText(offertaBase.toString());
        cambioOgg = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();
        String rilancia, isFinito;
        Integer valore, valore2;
        Integer valoreBudget = Integer.parseInt(budget.getText());

        try {

            if (s.equals(rilancio.getText())) {
                rilancia = JOptionPane.showInputDialog("Inserire valore:");
                valore = Integer.parseInt(rilancia);
                while (valore > valoreBudget) {
                    rilancia = JOptionPane.showInputDialog("Valore supera il budget, rinserire valore:");
                    valore = Integer.parseInt(rilancia);
                }
                valoreBudget = valoreBudget - valore;
                budget.setText(valoreBudget.toString());
                out.println(valore);
            }

            if (!cambioOgg) {
                rilancia = input.readLine();
                valore2 = Integer.parseInt(rilancia);
                rilancia = valOffertaMAX.getText();
                valore = Integer.parseInt(rilancia);
                if (valore < valore2) {
                    valOffertaMAX.setText(valore2.toString());
                    nomeClienteMAX.setText(input.readLine());
                    isFinito = input.readLine();
                    if (isFinito.equals("finito")) {
                        cambioOgg = true;
                    }
                }
            }

            if (cambioOgg) {
                restart();
            }

        } catch (Exception i) {
            i.printStackTrace();
        }
    }

}

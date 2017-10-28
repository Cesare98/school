package astaonline3;

import java.util.*;
import java.net.*;

public class Server {

    private String stringaInput;//stringa ricevuta dal client
    private static int ultimaOfferta;
    private static Scanner input = new Scanner(System.in);
    private final static int PORTA = 3333;
    private static String oggetto;//nome oggetto
    public static String[] oggetti = new String[6];//pila di oggetti in vendita
    //static ArrayList<String> clienti = new ArrayList<>(12);

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            System.out.println("Inserire gl'articoli");
            oggetto = input.nextLine();
            oggetti[i] = oggetto;
        }
        try {
            ServerSocket server = new ServerSocket(PORTA);
            while (true) {
                System.out.println("Sono in attesa su: " + PORTA);
                Socket client = server.accept();
                Connect nuovaConnessione = new Connect(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

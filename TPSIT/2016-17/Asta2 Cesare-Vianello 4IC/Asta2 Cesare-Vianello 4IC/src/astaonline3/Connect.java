package astaonline3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect extends Thread {

    private int offertaBase = 50;
    private int rilancio;//rilancio ottenuto dal cliente 
    private int miglioreOfferta;
    private static int ultimaOfferta;
    private int tempo;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private int cont;
    //Cooldown cooldown;
    private boolean aggiudicato = false;
    private String oggettoInVendita, isFinito, nome;
    private Osservato osservato;

    public Connect(Socket client) throws IOException {
        this.client = client;
        this.start();
        cont = 0;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
        osservato = new Osservato();
        osservato.addObserver(AstaOnline3.sc);
        osservato.refresh();
        oggettoInVendita = osservato.getItem();
        tempo = osservato.getSec();
        nome=in.readLine();
    }

    @Override
    public void run() {
        try {
            System.out.println("Sono connesso a : " + client.getInetAddress());
            while (cont < 6) {
                out.println(offertaBase);
                aggiudicato();
            }
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cont == 6) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.close();
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void aggiudicato() throws IOException, InterruptedException {
        
        while (!aggiudicato && tempo != 0) {
            controllaOfferta(rilancio);

        }
    }

    private void controllaOfferta(int rilancio) throws InterruptedException, IOException {

        if (miglioreOfferta == ultimaOfferta && tempo == 0) {
            aggiudicato = true;
            System.out.println(client.getInetAddress() + " si è aggiudicato il lotto");
            out.println("finito");
            cont++;
            osservato.prossimoItem();
            oggettoInVendita = osservato.getItem();
        }
        while (tempo != 0) {
            osservato.decremento();
            this.rilancio = Integer.parseInt(in.readLine());
            tempo = osservato.getSec();

            if (rilancio > ultimaOfferta) {
                miglioreOfferta = rilancio;
                ultimaOfferta = rilancio;
                System.out.println("L'offerta di " + client.getInetAddress() + " è stata accettata ");
                out.println(miglioreOfferta);
                out.println(nome);
                out.println("non finito");
                osservato.reset();
            }
        }

    }
}

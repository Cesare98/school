package astaonline3;

import java.util.Observable;

public class Osservato extends Observable {

    private Integer secondi;
    private String oggetto;
    int cont;

    public Osservato() {
        secondi = 5;
        cont = 0;
        oggetto = Server.oggetti[cont];
    }

    public void reset() {
        secondi = 5;
        refresh();
    }

    public void decremento() throws InterruptedException {
        secondi--;
        Thread.sleep(1000);
        refresh();
    }

    public void prossimoItem() {
        cont++;
        oggetto = Server.oggetti[cont];
    }

    public int getSec() {
        return secondi;
    }

    public String getItem() {
        return oggetto;
    }

    public void refresh() {
        setChanged();
        notifyObservers();
    }
}

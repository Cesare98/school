package astaonline3;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

public class OsservatoreClient extends JLabel implements Observer {

    JLabel oggetto = new JLabel();

    public OsservatoreClient() {
        add(oggetto);
        this.setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        Osservato x = (Osservato) (o);
        oggetto.setText("Oggetto: " + x.getItem());
    }

}

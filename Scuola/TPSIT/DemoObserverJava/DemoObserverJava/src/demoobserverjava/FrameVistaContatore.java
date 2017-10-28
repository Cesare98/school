/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demoobserverjava;


import java.awt.Label;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;



public class FrameVistaContatore extends JFrame implements Observer{
    Label vistaCont = new Label();
    FrameVistaContatore(int x0, int y0 ){
  
        add(vistaCont);
        setLocation(x0,y0);
        pack();
        this.setVisible(true);
        
        
    }
  
    @Override
    public void update(Observable o, Object arg) { // non usato arg
        SubjectContatore sc = (SubjectContatore)(o); // downcasting
        vistaCont.setText(""+sc.getCont());
    }
    
}

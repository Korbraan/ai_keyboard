package gui;

import models.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Steak on 09/01/2017.
 */
public class Window  extends JFrame implements Observer {

    public Window(Keyboard k){

        this.setTitle("Intelligence artificielle");
        this.setSize(600,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));

        this.setVisible(true);

        KeyboardView kv = new KeyboardView(k);
        MenuView mv = new MenuView(k);
        this.add(kv);
        this.add(mv);
        this.pack();
    }
    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
    }
}

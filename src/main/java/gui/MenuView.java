package gui;

import algorithms.SimulatedAnnealing;
import models.Keyboard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Steak on 09/01/2017.
 */
public class MenuView extends JPanel{

    private SimulatedAnnealing simulatedAnnealing;

    public MenuView(Keyboard k){
        this.setLayout(new GridLayout(4,4));
        JButton rec = new JButton("Simulated annealing");
        rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulatedAnnealing = new SimulatedAnnealing(k);
                simulatedAnnealing.optimizeKeyboard(k);
                System.out.println(k.toString());
                k.updateGUI();
            }
        });
        JButton random = new JButton("Randomize keyboard");
        random.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                k.createRandomKeyboard();
                System.out.println(k.toString());
                k.updateGUI();
            }
        });
        JButton gen = new JButton("Genetic");
        this.add(random);
        this.add(rec);
        this.add(gen);
    }
}

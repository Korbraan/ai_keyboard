package gui;

import algorithms.SimulatedAnnealing;
import models.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Steak on 09/01/2017.
 */
public class MenuView extends JPanel implements Observer{

    private SimulatedAnnealing simulatedAnnealing;

    private JPanel panel_buttons;

    private JPanel panel_text;

    public MenuView(Keyboard k){

        k.addObserver(this);

        this.panel_buttons=new JPanel();
        panel_buttons.setLayout(new GridLayout(1,3));

        this.panel_text=new JPanel();
        panel_text.setLayout(new GridLayout(3,2));

        this.setLayout(new GridLayout(3,1));

        JLabel cost = new JLabel();
        cost.setText(Double.toString(k.getGain()));

        JButton rec = new JButton("Simulated annealing");
        rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulatedAnnealing = new SimulatedAnnealing(k);
                simulatedAnnealing.optimizeKeyboard(k);
                k.computeGain();
                System.out.println(k.getGain());
                cost.setText(Double.toString(k.getGain()));
                k.updateGUI();
            }
        });
        JButton random = new JButton("Randomize keyboard");
        random.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                k.createRandomKeyboard();
                System.out.println(k.getGain());
                cost.setText(Double.toString(k.getGain()));
                k.updateGUI();
            }
        });
        JButton gen = new JButton("Genetic");
        gen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("LOL TA CRU");
            }
        });
        JLabel temp_lab = new JLabel("Temp :");
        JTextField temp = new JTextField("");

        this.panel_buttons.add(random);
        this.panel_buttons.add(rec);
        this.panel_buttons.add(gen);

        this.panel_text.add(temp_lab);
        this.panel_text.add(temp);
        this.panel_text.add(new JLabel("Cost"));
        this.panel_text.add(cost);

        this.add(panel_buttons);
        this.add(panel_text);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
    }
}

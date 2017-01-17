package gui;

import algorithms.Genetic;
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

    private Genetic genetic;

    private JPanel panel_buttons;

    private JPanel panel_text;

    public MenuView(Keyboard k){

        k.addObserver(this);

        this.panel_buttons=new JPanel();
        panel_buttons.setLayout(new GridLayout(2,2));

        this.panel_text=new JPanel();
        panel_text.setLayout(new GridLayout(2,4));

        this.setLayout(new GridLayout(1,2));

        JLabel costLabel = new JLabel();
        costLabel.setText("Fitness: "+Double.toString(k.getGain()));


        JLabel population_size = new JLabel("Population");
        JTextField population_sizefield = new JTextField("1000");
        JLabel generations = new JLabel("Generations");
        JTextField generationsfield = new JTextField("1200");
        JLabel selectionSize  = new JLabel("Selection");
        JTextField selectionSizefield = new JTextField("200");
        JLabel mutateProb = new JLabel ("Mutation prob");
        JTextField mutateProbField = new JTextField("0.2");

        JButton rec = new JButton("Simulated annealing");
        rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                k.createRandomKeyboard();
                k.updateGUI();
                simulatedAnnealing = new SimulatedAnnealing(k);
                simulatedAnnealing.optimizeKeyboard(k);
                k.computeGain();
                System.out.println(k.getGain());
                costLabel.setText(Double.toString(k.getGain()));
                k.updateGUI();
            }
        });
        JButton random = new JButton("Randomize keyboard");
        random.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                k.createRandomKeyboard();
                System.out.println(k.getGain());
                costLabel.setText(Double.toString(k.getGain()));
                k.computeGain();
                System.out.println(k.getGain());
                costLabel.setText("Fitness: "+Double.toString(k.getGain()));
                k.updateGUI();
            }
        });
        JButton gen = new JButton("Genetic");
        gen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                k.setKeys(null);
                k.createRandomKeyboard();
                costLabel.setText(Double.toString(k.getGain()));
                genetic = new Genetic(Integer.parseInt(population_sizefield.getText()), Integer.parseInt(generationsfield.getText()), Integer.parseInt(selectionSizefield.getText()), Double.parseDouble(mutateProbField.getText()));
                genetic.optimizePopulation();
                k.setKeys(genetic.optimizePopulation().getKeys());
                k.computeGain();
                costLabel.setText("Fitness: "+Double.toString(k.getGain()));
                k.updateGUI();
            }
        });

        JTextField temp = new JTextField("");

        this.panel_buttons.add(costLabel);
        this.panel_buttons.add(rec);
        this.panel_buttons.add(gen);

        this.panel_text.add(population_size);
        this.panel_text.add(population_sizefield);
        this.panel_text.add(generations);
        this.panel_text.add(generationsfield);
        this.panel_text.add(selectionSize);
        this.panel_text.add(selectionSizefield);
        this.panel_text.add(mutateProb);
        this.panel_text.add(mutateProbField);

        this.add(panel_buttons);
        this.add(panel_text);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
    }
}

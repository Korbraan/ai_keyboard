package gui;

import algorithms.Genetic;
import algorithms.SimulatedAnnealing;
import models.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Steak on 09/01/2017.
 */
public class MenuView extends JPanel implements Observer{

    private SimulatedAnnealing simulatedAnnealing;

    private Genetic genetic;

    public MenuView(Keyboard k){

        k.addObserver(this);

        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new GridLayout(2,2));

        JPanel panel_field = new JPanel();
        panel_field.setLayout(new GridLayout(2,1));

        JPanel panel_field_gen = new JPanel();
        panel_field_gen.setLayout(new GridLayout(1, 8));


        String[] algorithmsNames = new String[]
                {
                        "Simulated annealing",
                        "Genetic"
                };
        JComboBox algorithmsList = new JComboBox(algorithmsNames);


        this.setLayout(new BorderLayout());

        JLabel costLabel = new JLabel();
        String fit = "Fitness : "+(int)k.getGain();
        costLabel.setText(fit);

        JLabel population_size = new JLabel("Population");
        JTextField population_sizefield = new JTextField("1000");
        JLabel generations = new JLabel("Generations");
        JTextField generationsfield = new JTextField("1200");
        JLabel selectionSize  = new JLabel("Selection");
        JTextField selectionSizefield = new JTextField("200");
        JLabel mutateProb = new JLabel ("Mutation");
        JTextField mutateProbField = new JTextField("0.2");

        JButton rec = new JButton("Simulated annealing");
        rec.addActionListener(e -> {
            k.createRandomKeyboard();
            k.updateGUI();
            simulatedAnnealing = new SimulatedAnnealing();
            simulatedAnnealing.optimizeKeyboard(k);
            k.setKeys(simulatedAnnealing.getBestKeyboard().getKeys());
            k.setGain(simulatedAnnealing.getBestKeyboard().getGain());
//                System.out.println(k.getGain());
            String fit1 = "Fitness : "+(int)k.getGain();
            costLabel.setText(fit1);
            k.updateGUI();
        });
        JButton random = new JButton("Randomize keyboard");
        random.addActionListener( e -> {
            k.createRandomKeyboard();
//                System.out.println(k.getGain());
            costLabel.setText(Double.toString(k.getGain()));
            k.computeGain();
//                System.out.println(k.getGain());
            String fit12 = "Fitness : "+(int)k.getGain();
            costLabel.setText(fit12);
            k.updateGUI();
        });
        JButton gen = new JButton("Genetic");
        gen.addActionListener( e -> {
            k.setKeys(null);
            k.createRandomKeyboard();
            costLabel.setText(Double.toString(k.getGain()));
            genetic = new Genetic(Integer.parseInt(population_sizefield.getText()), Integer.parseInt(generationsfield.getText()), Integer.parseInt(selectionSizefield.getText()), Double.parseDouble(mutateProbField.getText()));
            genetic.optimizePopulation();
            k.setKeys(genetic.optimizePopulation().getKeys());
            k.setGain(genetic.optimizePopulation().fitness());
            String fit13 = "Fitness : "+(int)k.getGain();
            costLabel.setText(fit13);
            k.updateGUI();
        });

        panel_buttons.add(costLabel);
        panel_buttons.add(gen);
        panel_buttons.add(new JLabel());
        panel_buttons.add(rec);

        panel_field_gen.add(population_size);
        panel_field_gen.add(population_sizefield);
        panel_field_gen.add(generations);
        panel_field_gen.add(generationsfield);
        panel_field_gen.add(selectionSize);
        panel_field_gen.add(selectionSizefield);
        panel_field_gen.add(mutateProb);
        panel_field_gen.add(mutateProbField);

        this.add(panel_buttons);
        panel_field.add(panel_field_gen);
        this.add(panel_field);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
    }
}

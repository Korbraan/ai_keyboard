package gui;

import algorithms.Genetic;
import algorithms.SimulatedAnnealing;
import models.Genetic.Candidate;
import models.Keyboard;
import tools.DataParser;

import javax.swing.*;
import java.util.HashMap;

/**
 * Created by cremond on 17/01/17.
 */
public class testForm {
    private JPanel mainPanel;
    private JTextField populationField;
    private JTextField generationsField;
    private JTextField selectionField;
    private JTextField mutationField;
    private JComboBox algorithmChoice;
    private JPanel geneticFields;
    private JButton goButton;
    private JPanel simulatedAnnealingFields;
    private JTextField initialTemperatureField;
    private JTextField coolingRateField;
    private JLabel populationLabel;
    private JLabel generationsLabel;
    private JLabel selectionLabel;
    private JLabel mutationLabel;
    private JLabel initialTemperatureLabel;
    private JLabel coolingRateLabel;
    private JLabel minimalTemperature;
    private JTextField minimalTemperatureField;
    private KeyboardView keyboardView;
    private JPanel topPanel;
    private JTextField gainField;
    private JLabel gainLabel;
    private JTextField azertyKeyboardGainField;
    private JTextField qwertyKeyboardGainField;
    private JLabel azertyKeyboardGainPanel;

    public testForm() {

        DataParser dp = new DataParser("./datafiles/bigramFreqEng-Occurrence.dat");
        dp.parseData();

        azertyKeyboardGainField.setText("113");
        qwertyKeyboardGainField.setText("115");

        algorithmChoice.addActionListener(actionEvent -> {
            JComboBox algorithmChoice = (JComboBox) actionEvent.getSource();
            String selectedItem = (String) algorithmChoice.getSelectedItem();

            switch (selectedItem) {
                case "Simulated Annealing":
                    simulatedAnnealingFields.setVisible(true);
                    geneticFields.setVisible(false);
                    break;
                case "Genetic":
                    simulatedAnnealingFields.setVisible(false);
                    geneticFields.setVisible(true);
                    break;
                default:
                    System.out.println("Error in item selection");
                    break;
            }
        });
        goButton.addActionListener(actionEvent -> {
            String selectedItem = (String) algorithmChoice.getSelectedItem();
            Keyboard k = new Keyboard();
            switch (selectedItem) {
                case "Simulated Annealing":

                    k.createRandomKeyboard();
                    double initialTemperature = Double.parseDouble(initialTemperatureField.getText());
                    double coolingRate = Double.parseDouble(coolingRateField.getText());
                    double temperatureLimit = Double.parseDouble(minimalTemperatureField.getText());

                    SimulatedAnnealing sa = new SimulatedAnnealing(initialTemperature, coolingRate, temperatureLimit);
                    sa.optimizeKeyboard(k);
                    keyboardView.setKeyboard(sa.getBestKeyboard());

                    break;

                case "Genetic":
                    Genetic genetic = new Genetic(Integer.parseInt(populationField.getText()),
                            Integer.parseInt(generationsField.getText()), Integer.parseInt(selectionField.getText()),
                            Double.parseDouble(mutationField.getText()));
                    Candidate op = genetic.optimizePopulation();

                    k.setKeys(op.getKeys());
                    k.setGain(op.fitness());
                    keyboardView.setKeyboard(k);
                    gainField.setText(Double.toString(k.getGain()));
                    keyboardView.revalidate();
                    keyboardView.repaint();
                    break;
                default:
                    System.out.println("Error in item selection");
                    break;
            }
            gainField.setText(Integer.toString((int) keyboardView.getKeyboard().getGain()));
            keyboardView.revalidate();
            keyboardView.repaint();
        });
    }

    private void createUIComponents() {
        simulatedAnnealingFields = new JPanel();
        simulatedAnnealingFields.setVisible(false);

        Keyboard k = new Keyboard();
        keyboardView = new KeyboardView(k);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Optimized keyboard generator");
        frame.setContentPane(new testForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

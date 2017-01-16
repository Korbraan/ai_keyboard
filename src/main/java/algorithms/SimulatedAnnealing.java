package algorithms;


import models.Keyboard;
import models.Letter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    private int n;
    private double temperature;
    private double energy;
    private Keyboard keyboard;

    public SimulatedAnnealing(Keyboard keyboard) {
        this.keyboard = keyboard;
        this.energy = keyboard.getCost();
        this.n = 1;
        this.temperature = temperature(n);
    }

    public void optimizeKeyboard() {
        while (temperature >= 1) {
            Keyboard nextKeyboard = new Keyboard(keyboard);
//            Letter letter = keyboard.getWorstLetter();

            // Plutôt que de prendre la pire lettre, on prend une lettre au hasard --> sortir des extrema locaux
            Letter[] alphabet = Letter.values();
            Letter letter = alphabet[ThreadLocalRandom.current().nextInt(alphabet.length)];
            boolean optimized = false;
            while (!optimized) {
                nextKeyboard.moveLetter(letter);
                double nextEnergy = nextKeyboard.getCost();
                if (nextKeyboard.getCost() < keyboard.getCost() || isLucky(nextEnergy)) {
                    keyboard = nextKeyboard;
                    energy = nextEnergy;
                    n++;
                    temperature = temperature(n);
                    optimized = true;
                    System.out.println("Letter " + letter + " moved");
                }
            }
        }

    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public boolean isLucky(double energy) {
            Random r = new Random();
        double p = r.nextDouble();
        return p <= Math.exp(-(energy-this.energy)/temperature);
    }

    public double temperature(int n) {
        //TODO : Implement a temperature function
        return 100/n;
    }



}

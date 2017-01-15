package algorithms;


import models.Keyboard;
import models.Letter;
import models.OccurencesData;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    private double temperature;
    private double cost;
    private Keyboard keyboard;
    private double coolingRate;


    public SimulatedAnnealing(Keyboard keyboard) {
        this.keyboard = keyboard;
        this.cost = keyboard.getCost();
        this.temperature = OccurencesData.getMaxOccurence();
        coolingRate = 0.003;
    }

    public void optimizeKeyboard() {
        int i = 0;
        while (temperature > 1) {
            Keyboard nextKeyboard = new Keyboard(keyboard);

            // Plutôt que de prendre la pire lettre, on prend une lettre au hasard --> sortir des extrema locaux
            Letter[] alphabet = Letter.values();
            Letter letter = alphabet[ThreadLocalRandom.current().nextInt(alphabet.length)];
//                nextKeyboard.moveLetterInEmpty(letter);

            // De même il peut être intéressant de donner la possibilité d'intervertir les lettres entre elles
            nextKeyboard.moveLetter(letter);
            double newCost = nextKeyboard.getCost();
            if (acceptanceProbability(cost, newCost, temperature) >= ThreadLocalRandom.current().nextDouble(0, 1)) {
                keyboard = nextKeyboard;
                cost = newCost;
//                System.out.println("Letter " + letter + " moved");
            }
            temperature *= 1 - coolingRate;
            i++;
        }
        System.out.println("i : " + i);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public double acceptanceProbability(double old_cost, double new_cost, double temperature) {
        if (old_cost > new_cost) {
            return 1;
        }
//        System.out.println("proba : " +Math.exp((old_cost - new_cost)/(OccurencesData.getMaxOccurence()*temperature)));
        return Math.exp((old_cost - new_cost)/(temperature));
    }
}

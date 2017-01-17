package algorithms;


import models.Keyboard;
import models.Letter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    private double temperature;
    private double temperatureLimit;
    private double coolingRate;
    private double energy;

    public SimulatedAnnealing(Keyboard keyboard) {
        this.energy = keyboard.getGain();
        this.temperature = 1000;
        this.temperatureLimit = 0.01;
        this.coolingRate = 0.99;
    }

    public void optimizeKeyboard(Keyboard k) {
        while (temperature > temperatureLimit) {
            Keyboard nextKeyboard = new Keyboard(k);

            // Plutôt que de prendre la pire lettre, on prend une lettre au hasard --> sortir des extrema locaux
            Letter[] alphabet = Letter.values();
            Letter letter = alphabet[ThreadLocalRandom.current().nextInt(alphabet.length)];

            nextKeyboard.moveLetter(letter);
            double newEnergy = nextKeyboard.getGain();

            if (acceptanceProbability(energy, newEnergy) > ThreadLocalRandom.current().nextDouble(1)) {
                k = nextKeyboard;

//                System.out.println("Letter " + letter + " moved");
            }
            temperature *= coolingRate;
        }
    }

    public double acceptanceProbability(double old_energy, double new_energy) {
        if (old_energy < new_energy) {
            return 1;
        }
        double proba = Math.exp((new_energy - old_energy) / temperature);

        System.out.println("proba : " + proba);

        return proba;
    }
}

package algorithms;


import models.Keyboard;
import models.Letter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private double temperatureLimit;
    private Keyboard bestKeyboard;

    public SimulatedAnnealing() {
        this.temperature = 10;
        this.coolingRate = 0.9999;
        this.temperatureLimit = 0.01;
        this.bestKeyboard = new Keyboard();
    }

    public void optimizeKeyboard(Keyboard keyboard) {
        Keyboard neighbourKeyboard;

        while (temperature > temperatureLimit) {

            neighbourKeyboard = keyboard.getNeighbour();

            if (acceptanceProbability(keyboard.getGain(), neighbourKeyboard.getGain()) > ThreadLocalRandom.current().nextDouble(1)) {
                keyboard = new Keyboard(neighbourKeyboard);
                keyboard.getGain();
            }
            if (keyboard.getGain() > bestKeyboard.getGain()) {
                bestKeyboard = new Keyboard(keyboard);
            }
            temperature *= coolingRate;
        }
    }

    public double acceptanceProbability(double old_energy, double new_energy) {
        if (new_energy > old_energy) {
            return 1;
        }
        double probability = Math.exp((new_energy - old_energy)/temperature);
//        System.out.println("proba : " + probability);
        return probability;
    }

    public Keyboard getBestKeyboard() {
        return bestKeyboard;
    }
}

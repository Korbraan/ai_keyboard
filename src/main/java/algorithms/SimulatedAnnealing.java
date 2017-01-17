package algorithms;


import models.Keyboard;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    private double temperature;
    private double coolingRate;
    private double temperatureLimit;
    private Keyboard bestKeyboard;

    public SimulatedAnnealing(double temperature, double coolingRate, double temperatureLimit) {
        this.temperature = temperature;
        this.coolingRate = coolingRate;
        this.temperatureLimit = temperatureLimit;
        this.bestKeyboard = new Keyboard();
    }

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

        return probability;
    }

    public Keyboard getBestKeyboard() {
        return bestKeyboard;
    }
}

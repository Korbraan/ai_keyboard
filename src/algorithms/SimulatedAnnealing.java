package algorithms;

import models.Keyboard;

/**
 * Created by cremond on 27/12/16.
 */
public class SimulatedAnnealing {
    int temperature;
    int energy;
    Keyboard keyboard;

    public SimulatedAnnealing(Keyboard keyboard, int temperature, int energy) {
        this.keyboard = keyboard;
        this.temperature = temperature;
        this.energy = energy;
    }
}

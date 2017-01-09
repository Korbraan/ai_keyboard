package algorithms;


import models.Keyboard;
import models.Letter;
import models.OccurencesData;
import models.Position;

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

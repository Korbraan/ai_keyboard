package algorithms;


import models.Keyboard;
import models.Letter;

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

    public long twoLettersCost(Letter a, Letter b) {

        return 42;
    }

    public long letterCost(Letter letter) {

        return 42;
    }

    public long keyboardCost() {

        return 42;
    }


}

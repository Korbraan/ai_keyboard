package algorithms;

import models.Keyboard;

import java.util.ArrayList;

/**
 * Created by cremond on 27/12/16.
 */
public class TabuSearch {
    private ArrayList<Keyboard> memory;
    private int neighbourhoodSize;
    private int iterationsNumber;
    private Keyboard bestKeyboard;

    public TabuSearch() {

        this.memory = new ArrayList<>();
        this.bestKeyboard = new Keyboard();
        this.neighbourhoodSize = 10;
        this.iterationsNumber = 1000;
    }

    public void optimizeKeyboard(Keyboard keyboard) {

        bestKeyboard = new Keyboard(keyboard);
        // While the stop criteria has not been encountered
        int iteration = 0;

        while (iteration < iterationsNumber) {
//            System.out.println("iteration : " + iteration);
            // We create a set of neighbours not already seen before
            Keyboard[] neighbours = new Keyboard[neighbourhoodSize];
            for (int i = 0; i < neighbourhoodSize; i++) {

                Keyboard neighbour = bestKeyboard.getNeighbour();

                while(memory.contains(neighbour)) {
                    neighbour = bestKeyboard.getNeighbour();
                }

                neighbours[i] = neighbour;
            }
            // We choose the best neighbour
            Keyboard bestNeighboor = neighbours[0];

            for (int i = 1; i < neighbourhoodSize; i++) {
                if (neighbours[i].getGain() > bestNeighboor.getGain()) {
                    bestNeighboor = neighbours[i];
                }
            }
            bestKeyboard = new Keyboard(bestNeighboor);
            memory.add(keyboard);
            iteration++;
        }
    }

    public Keyboard getBestKeyboard() {
        return bestKeyboard;
    }
}

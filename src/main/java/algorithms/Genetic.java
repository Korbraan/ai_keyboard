package algorithms;

import models.Genetic.*;

/**
 * Created by cremond on 27/12/16.
 */
public class Genetic {
    private int selectionSize;
    private int generations;
    private float mutationProbability;
    private Population currentPopulation;

    public Genetic(int populationSize, int generations, int selectionSize, float mutationProbability) {
        this.generations = generations;
        this.selectionSize = selectionSize;
        this.mutationProbability = mutationProbability;
        /* Initialize population */
        this.currentPopulation = new Population(populationSize);
    }

    public void optimizePopulation() {
        for (int i=0;i<generations;i++) {
        /* Selection */
            Population selectedPopulation = currentPopulation.selectByRank(selectionSize);
        /* Crossing */
            Population childPopulation = selectedPopulation.makeBabies();
        /* Mutations */
            for (Candidate child : childPopulation.getCandidates())
                child.mutate(mutationProbability);
        /* Insertion */
            currentPopulation = currentPopulation.newGeneration(childPopulation);
        }
    }
}

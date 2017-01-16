package algorithms;

import models.Genetic.*;

/**
 * Created by cremond on 27/12/16.
 */
public class Genetic {
    private int selectionSize;
    private int generations;
    private double mutationProbability;
    private Population currentPopulation;

    public Genetic(int populationSize, int generations, int selectionSize, double mutationProbability) {
        this.generations = generations;
        this.selectionSize = selectionSize;
        this.mutationProbability = mutationProbability;
        /* Initialize population */
        this.currentPopulation = new Population(populationSize);
    }

    public void optimizePopulation() {
        for (int i=0;i<generations;i++) {
            System.out.println("generation " + i);
        /* Selection */
            System.out.println("starting selection ...");
            Population selectedPopulation = currentPopulation.selectByRank(selectionSize);
        /* Crossing */
            System.out.println("making babies ...");
            Population childPopulation = selectedPopulation.makeBabies();
        /* Mutations */
            System.out.println("mutate babies ...");
            for (Candidate child : childPopulation.getCandidates())
                child.mutate(mutationProbability);
        /* Insertion */
            System.out.println("Inserting population ...");
            currentPopulation = currentPopulation.newGeneration(childPopulation);
        }
        System.out.println("Done !");
        System.out.println(currentPopulation);
    }
}

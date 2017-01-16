package algorithms;

import models.Genetic.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cremond on 27/12/16.
 */
public class Genetic {
    private int selectionSize;
    private int generations;
    private double mutationProbability;
    private Population currentPopulation;
    public static List<Double> fitnessList = new ArrayList<>();
    public static List<Double> oldFitnessList = new ArrayList<>();
    public static List<Candidate> keyboardList = new ArrayList<>();

    public Genetic(int populationSize, int generations, int selectionSize, double mutationProbability) {
        this.generations = generations;
        this.selectionSize = selectionSize;
        this.mutationProbability = mutationProbability;
        /* Initialize population */
        this.currentPopulation = new Population(populationSize);
        oldFitnessList.add((double) currentPopulation.getCandidates().get(currentPopulation.getCandidates().size() - 1).fitness());
    }

    public void optimizePopulation() {
        int[] fitnessvar = new int[generations];
        System.out.println(currentPopulation.getCandidates().get(currentPopulation.getCandidates().size() - 1));
        for (int i = 0; i < generations; i++) {
        /* Selection */
//            System.out.println("starting selection ...");
            Population selectedPopulation = currentPopulation.selectByRank(selectionSize);
        /* Crossing */
//            System.out.println("making babies ...");
            Population childPopulation = selectedPopulation.makeBabies();
        /* Mutations */
//            System.out.println("mutate babies ...");
            for (Candidate child : childPopulation.getCandidates())
                child.mutate(mutationProbability);
        /* Insertion */
//            System.out.println("Inserting population ...");
            int oldFitness = currentPopulation.meanFitness();
            currentPopulation = currentPopulation.newGeneration(childPopulation);
            int fitnessVariation = currentPopulation.meanFitness() - oldFitness;
            fitnessvar[i] = fitnessVariation;

        }
        List<Integer> occurences = new ArrayList<>();
        List<Double> fitnesses = new ArrayList<>();
        Collections.sort(currentPopulation.getCandidates());
        for (Candidate candidate : currentPopulation.getCandidates()) {
            fitnesses.add((double) candidate.fitness());
            occurences.add(Collections.frequency(currentPopulation.getCandidates(), candidate));
        }
        System.out.println("Done !");
        Collections.sort(currentPopulation.getCandidates());
        Collections.reverse(currentPopulation.getCandidates());
        fitnessList.add((double) currentPopulation.getCandidates().get(currentPopulation.getCandidates().size() - 1).fitness());
        keyboardList.add(currentPopulation.getCandidates().get(currentPopulation.getCandidates().size() - 1));
    }
}

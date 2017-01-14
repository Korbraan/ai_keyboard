package models.Genetic;

import models.Letter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static models.OccurencesData.getMaxOccurence;
import static models.OccurencesData.occurences;

/**
 * Created by tom on 14/01/17.
 */
public class Candidate implements Comparable<Candidate> {

    private Gene[] genes;
    private List<Gene> availableGenes;
    private double cost;

    public Candidate() {
        this.genes = new Gene[26];
        getAvailableGenes();
        createRandomGenotype();
        computeCost();
    }

    public Candidate(Gene[] genes) {
        if (genes.length != 26) {
            System.out.println("Error : Every candidate should have 26 genes");
            return;
        }
        this.genes = genes;
        this.availableGenes = getAvailableGenes();
        computeCost();
    }

    public void createRandomGenotype() {
        Random random = new Random();
        for (int i = 0; i<26 ; i++) {
            int randomPos = random.nextInt(availableGenes.size());
            genes[i] = availableGenes.get(randomPos);
            availableGenes.remove(randomPos);
        }
        if (availableGenes.size() != 14)
            System.out.println("Error : availableGenes size should be 14");
        if (genes.length != 26)
            System.out.println("Error : genotype size should be 26");
    }

    public Candidate[] cross(Candidate other) {
        Random random = new Random();
        Gene[] genes1 = new Gene[26];
        Gene[] genes2 = new Gene[26];
        /* 2 childs per couple */
        for (int i = 0;i<genes.length ; i ++) {
            /* 50% chance */
            if (random.nextBoolean()) {
                genes1[i] = this.genes[i];
            } else {
                genes1[i] = other.getGenes()[i];
            }
            if (random.nextBoolean()) {
                genes2[i] = this.genes[i];
            } else {
                genes2[i] = other.getGenes()[i];
            }
        }
        Candidate child1 = new Candidate(genes1);
        Candidate child2 = new Candidate(genes2);
        return new Candidate[]{child1,child2};
    }

    public void mutate(float p) {
        Random random = new Random();
        if (random.nextFloat() < p) {
            /* Randomly mutate one gene */
            genes[random.nextInt(26)] = availableGenes.get(random.nextInt(14));
        }
    }

    public void computeCost() {
        double cost = 0;

        for (int i=0; i<genes.length ; i++) {
            cost += computeGeneCost(genes[i],i);
        }

        this.cost = cost;
    }

    public double computeGeneCost(Gene gene, int index) {
        double cost = 0;
        for (int i =0 ; i<genes.length ; i++) {
            if (genes[i] != gene) {
                cost += computeCoupleCost(gene, index, genes[i], i);
            }
        }
        return cost;
    }

    public double computeCoupleCost(Gene g1, int index1, Gene g2, int index2) {

        double distance = g1.euclideanDistance(g2);
        long occurence = occurences[index1][index2];

        return distance * getCoeff() + occurence;
    }

    public double getCoeff() {
        //TODO : Move that to the proper class
        Gene g1 = new Gene(0,0);
        Gene g2 = new Gene(9,3);
        return getMaxOccurence()/g1.euclideanDistance(g2);
    }


    public List<Gene> getAvailableGenes() {
        List<Gene> availableGenes = new ArrayList<>();
        for (int x=0;x<10;x++) {
            for (int y = 0; y <4; y++) {
                if (!Arrays.asList(genes).contains(new Gene(x,y)))
                    availableGenes.add(new Gene(x,y));
            }
        }
        if (availableGenes.size() != 14)
            System.out.println("ERROR : availableGenes size should be 14");
        return availableGenes;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(Candidate o) {
        return Double.compare(cost, o.getCost());
    }
}

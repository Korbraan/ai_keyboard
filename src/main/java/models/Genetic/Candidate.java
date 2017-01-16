package models.Genetic;


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
        computeAvailableGenes();
        createRandomGenotype();
        computeCost();
    }

    public Candidate(Gene[] genes) {
        if (genes.length != 26) {
            System.out.println("Error : Every candidate should have 26 genes");
            return;
        }
        this.genes = genes;
        computeAvailableGenes();
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
            System.out.println("Error : availableGenes size should be 14 but is " + availableGenes.size());
        if (genes.length != 26)
            System.out.println("Error : genotype size should be 26");
    }

    public Candidate[] cross(Candidate other) {
        Random random = new Random();
        Gene[] genes1 = new Gene[26];
        Gene[] genes2 = new Gene[26];
        /* 2 childs per couple */
        for (int i = 0;i<genes.length ; i ++) {
            /* 80% chance per gene */
            if (random.nextFloat()<0.8) {
                genes1[i] = this.genes[i];
            } else {
                genes1[i] = other.getGenes()[i];
            }
            if (random.nextFloat()<0.8) {
                genes2[i] = this.genes[i];
            } else {
                genes2[i] = other.getGenes()[i];
            }
        }
        /* we have to mutate the duplications*/
        for (int x=0;x<10;x++) {
            for (int y=0;y<4;y++) {
                List<Integer> occIndex = getOccurence(new Gene(x,y), genes1);
                while (occIndex.size()>1) {
                    int oldAllele = random.nextInt(occIndex.size());
                    int newAllele = random.nextInt(14);
                    genes[occIndex.get(oldAllele)] = availableGenes.get(newAllele);
                    occIndex.remove(oldAllele);
                    computeAvailableGenes();
                }
            }
        }
        Candidate child1 = new Candidate(genes1);
        Candidate child2 = new Candidate(genes2);
        return new Candidate[]{child1,child2};
    }

    public void mutate(double p) {
        Random random = new Random();
        if (random.nextDouble() < p) {
            /* Randomly mutate one gene */
            int oldAllele = random.nextInt(26);
            int newAllele = random.nextInt(14);
            genes[oldAllele] = availableGenes.get(newAllele);
            /* Update available genes */
            computeAvailableGenes();
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


    public void computeAvailableGenes() {
        List<Gene> availableGenes = new ArrayList<>();
        for (int x=0;x<10;x++) {
            for (int y = 0; y <4; y++) {
                if (!Arrays.asList(genes).contains(new Gene(x,y)))
                    availableGenes.add(new Gene(x,y));
            }
        }
        this.availableGenes = availableGenes;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public double getCost() {
        return cost;
    }

    public List<Gene> getAvailableGenes() {
        return availableGenes;
    }

    public List<Integer> getOccurence(Gene gene, Gene[] genes) {
        List<Integer> occIndexes = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
            if (gene.equals(genes[i]))
                occIndexes.add(i);
        }
        return occIndexes;
    }

    public String toString() {
        String res = "{";
        for (Gene gene : genes) {
            res+="["+gene.getX()+","+gene.getY()+"]";
            res+=",";
        }
        res += "}";
        return res;
    }

    @Override
    public int compareTo(Candidate o) {
        return Double.compare(cost, o.getCost());
    }
}

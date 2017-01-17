package models.Genetic;

import java.util.*;

/**
 * Created by tom on 14/01/17.
 */
public class Population {

    private List<Candidate> candidates;

    public Population(int size) {
        candidates = new ArrayList<>();
        for (int i=0 ; i<size;i++) {
            candidates.add(new Candidate());
        }
    }

    public Population(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public Population selectByRank(int candidateNumber) {
        List<Candidate> selectedCandidates = new ArrayList<>();
        List<Candidate> candidates = new ArrayList<>(this.candidates);
        Random random = new Random();
        Collections.sort(candidates);
//        Collections.reverse(candidates);
        int size = candidates.size();
        for (int i=0;i<candidateNumber-1;i++) {
            int pDomain = size*(size+1)/2;
            int p = random.nextInt(pDomain);
            int bornSup = 1;
            for (int j=1;j<=size;j++) {
                if (p<bornSup) {
                    selectedCandidates.add(candidates.get(j-1));
                    break;
                }
                bornSup += j+1;
            }
        }
        /* ensure we keep selecting the best one */
        selectedCandidates.add(candidates.get(size-1));
        return new Population(selectedCandidates);
    }

    public Population makeBabies() {
        List<Candidate> children = new ArrayList<>();
        List<Candidate[]> couples = generateCouples();
        for (Candidate[] couple : couples) {
            Candidate[] coupleChildren = couple[0].cross(couple[1]);
            for (Candidate child : coupleChildren) {
                if (child.getAvailableGenes().size() != 14)
                    throw new RuntimeException(String.valueOf(child.getAvailableGenes().size()));
                children.add(child);
            }
        }
        return new Population(children);
    }

    public List<Candidate[]> generateCouples() {
        List<Candidate[]> couples = new ArrayList<>();
        List<Candidate> candidates = new ArrayList<>(this.candidates);
        Collections.shuffle(candidates);

        for (int i=0; i<candidates.size()-1 ; i+=2)
            couples.add(new Candidate[]{candidates.get(i),candidates.get(i+1)});
        return couples;
    }

    public Population newGeneration(Population created) {
        int wantedSize = this.candidates.size();
        List<Candidate> candidates = new ArrayList<>(this.candidates);
        candidates.addAll(created.getCandidates());
        Collections.sort(candidates);
//        Collections.reverse(candidates);
        int totalSize = candidates.size();
        candidates = candidates.subList(totalSize-wantedSize, totalSize);

        return new Population(candidates);
    }

    public int meanFitness() {
        int fitness = 0;
        for (Candidate candidate : candidates) {
            fitness += candidate.fitness();
        }
        return fitness;
    }

    public String toString() {
        String res = "";
        for (Candidate candidate : candidates) {
            res += candidate.toString();
            res += "\n";
            res += "---";
            res += "\n";
        }
        return res;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }
}

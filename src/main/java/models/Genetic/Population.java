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
        int size = candidates.size();
        for (int i=0;i<candidateNumber;i++) {
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
        return new Population(selectedCandidates);
    }

    public Population makeBabies() {
        List<Candidate> children = new ArrayList<>();
        List<Candidate[]> couples = generateCouples();
        for (Candidate[] couple : couples) {
            Candidate[] coupleChildren = couple[0].cross(couple[1]);
            for (Candidate child : coupleChildren) {
                children.add(child);
            }
        }
        return new Population(children);
    }

    public List<Candidate[]> generateCouples() {
        List<Candidate[]> couples = new ArrayList<>();
        List<Candidate> mens = new ArrayList<>(candidates);
        List<Candidate> womens = new ArrayList<>(candidates);
        for (Candidate male : candidates) {
            for (Candidate female : candidates) {
                if (male != female) {
                    couples.add(new Candidate[]{male,female});
                    mens.remove(male);
                    womens.remove(female);
                }
            }
        }
        return couples;
    }

    public Population newGeneration(Population created) {
        int wantedSize = this.candidates.size();
        List<Candidate> candidates = new ArrayList<>(this.candidates);
        candidates.addAll(created.getCandidates());
        Collections.sort(candidates);
        int totalSize = candidates.size();
        candidates = candidates.subList(totalSize-wantedSize, totalSize);
        return new Population(candidates);
    }

    public String toString() {
        String res = "";
        for (Candidate candidate : candidates) {
//            res += candidate.toString();
            System.out.println(candidate.getCost());
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

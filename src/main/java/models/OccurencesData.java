package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cremond on 27/12/16.
 */
public class OccurencesData {
    public static long[][] occurences;

    public OccurencesData(long[][] occurences) {
        this.occurences = occurences;
    }

    public List<Long> sortedOccurences() {
        List<Long> occurenceList = new ArrayList<>();
        for (int i = 0; i < occurences.length; i++) {
            for (int j = 0; j < occurences[i].length; j++) {
                occurenceList.add(occurences[i][j]);
            }
        }
        occurenceList.sort(Long::compareTo);
        return occurenceList;
    }

    public void replaceOccurenceByRank() {
        List<Long> sortedOcc = sortedOccurences();
        System.out.println("sortedOcc = " + sortedOcc);

        for (int i = 0; i < occurences.length; i++) {
            for (int j = 0; j < occurences[i].length; j++) {
                if (occurences[i][j] == 0)
                    System.out.println("[" + i + "," + j + "]");
            }
        }


        for (int k = sortedOcc.size()-1 ; k>=0; k--) {
            for (int i = 0; i < occurences.length; i++) {
                for (int j = 0; j < occurences[i].length; j++) {
                    if (occurences[i][j] == sortedOcc.get(k))
                        occurences[i][j] = k+1;
                }
            }
        }
    }

    public static long getMaxOccurence() {
        long max = 0;
        for (int i = 0; i < occurences.length; i++) {
           for (int j = 0; j < occurences[i].length; j++) {
               long local_max = 0;
               if ((local_max = occurences[i][j]) > max) {
                   max = local_max;
               }
           }
        }
        return max;
    }

    public String toString() {
        String res ="";
        for(int i=0; i < occurences.length; i++) {
            res+="\n";
            for(int j=0; j < occurences[i].length; j++) {
                long current_occurence = occurences[i][j];
                if (current_occurence < 10) {
                    res += current_occurence + " ";
                } else {
                    res += current_occurence;
                }
                res+="|";
            }
        }
        return res;
    }

    public long[][] getOccurences() {
        return occurences;
    }
}

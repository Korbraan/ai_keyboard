package models;

/**
 * Created by cremond on 27/12/16.
 */
public class OccurencesData {
    public static long[][] occurences;

    public OccurencesData(long[][] occurences) {
        this.occurences = occurences;
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
}

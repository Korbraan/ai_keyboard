package models;

import java.util.ArrayList;

/**
 * Created by cremond on 27/12/16.
 */
public class OccurencesData {
    public static long[][] occurences;

    private ArrayList<Position >max_to_min_positions;

    public OccurencesData(long[][] occurences) {
        this.occurences = occurences;
        this.max_to_min_positions = new ArrayList<Position>();
//        this.sortOccurences();

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

    private void sortOccurences() {
        Position max_pos = new Position(0, 0);
        // We search the position of the minimum to init the max
        Position min_pos = new Position(0,0);
        for (int i = 0; i < occurences.length; i++) {
            for (int j = 0; j < occurences[i].length; j++) {
                if (occurences[i][j] <= occurences[min_pos.getX()][min_pos.getY()]) {
                    min_pos.setXandY(i, j);
                }
            }
        }
        max_pos.setXandY(min_pos.getX(), min_pos.getY());

        // For each couple of letter
        for (int k = 0; k < occurences.length * occurences[0].length; k++) {
            max_pos.setXandY(min_pos.getX(), min_pos.getY());
            System.out.println("Start");
            // We search the next max and add it to the array
            for (int i = 0; i < occurences.length; i++) {
                for (int j = 0; j < occurences[i].length; j++) {
                    Position current_pos = new Position(i,j);
                    // If is higher and not already in the array
                    //System.out.println("is in array : " + max_to_min_positions.contains(current_pos));

                    if (occurences[i][j] >= occurences[max_pos.getX()][max_pos.getY()] &&
                            !max_to_min_positions.contains(current_pos)) {
                        max_pos.setXandY(i, j);
                        //System.out.println(k + ", " + i + ", " + j + "| max_pos : " + max_pos + ", value : " + occurences[max_pos.getX()][max_pos.getY()]);
                    }
                }
            }
//            System.out.println("max_pos : " + max_pos + ", value : " + occurences[max_pos.getX()][max_pos.getY()]);
            max_to_min_positions.add(max_pos);
            for (int l = 0; l < max_to_min_positions.size(); l++) {
                //System.out.println("x : " + max_to_min_positions.get(l).getX() + ", y : " + max_to_min_positions.get(l).getY());
            }
        }
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

    public ArrayList<Position> getMax_to_min_positions() {
        return max_to_min_positions;
    }

    public long[][] getOccurences() {
        return occurences;
    }
}

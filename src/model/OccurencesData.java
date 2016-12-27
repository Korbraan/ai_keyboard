package model;

import java.util.Arrays;

/**
 * Created by cremond on 27/12/16.
 */
public class OccurencesData {
    long[][] occurences;
    Position[] max_to_min_positions;

    public OccurencesData(long[][] occurences) {
        this.occurences = occurences;
        this.max_to_min_positions = new Position[(int) Math.pow(occurences.length, 2)];
    }

    private void sortOccurences() {
        Position max_pos = new Position(0,0);
        // For each couple of letter
        for (int k = 0; k < max_to_min_positions.length; k++) {
            max_pos.setXandY(0, 0);
            // We search the next max and add it to the array
            for (int i = 0; i < occurences.length; i++) {
                for (int j = 0; j < occurences[i].length; j++) {
                    // If is higher and not already in the array
                    if (occurences[i][j] > occurences[max_pos.getX()][max_pos.getY()] &&
                            !Arrays.asList(max_to_min_positions).contains(new Position(i, j))) {
                        max_pos.setXandY(i, j);
                    }
                }
            }
            max_to_min_positions[k] = max_pos;
        }
    }
}

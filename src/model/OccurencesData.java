package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by cremond on 27/12/16.
 */
public class OccurencesData {
    long[][] occurences;
    int[] max_to_min_positions;

    public OccurencesData(long[][] occurences) {
        this.occurences = occurences;
        this.max_to_min_positions = new int[(int) Math.pow(occurences.length, 2)];
    }

    private void sortOccurences() {
        List occurences_list = new ArrayList<List<Long>>();
        for (int i = 0; i < occurences.length; i++) {
            List line = Arrays.asList(this.occurences[i]);
            occurences_list.add(line);
        }
        long max = 0;
        for (int i = 0; i < occurences_list.size(); i++) {
            long local_max = (long) Collections.max((List) occurences_list.get(i));
            if (local_max > max) {
                max = local_max;
            }
        }
    }
}

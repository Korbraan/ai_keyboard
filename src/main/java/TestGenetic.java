import algorithms.Genetic;
import models.Genetic.Gene;
import models.OccurencesData;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import tools.DataParser;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by tom on 15/01/17.
 */
public class TestGenetic {

    public static void main(String[] args) {
        String data_file_path = "./datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.ParseData();

        OccurencesData occurences_data = data_parser.getOccurencesData();
//        occurences_data.replaceOccurenceByRank();

        for (int i = 0; i<10 ; i++) {
            System.out.println("i = " + i);
            Genetic genetic = new Genetic(1000, 1000, 200, 0.1);
            genetic.optimizePopulation();
        }

    }
}

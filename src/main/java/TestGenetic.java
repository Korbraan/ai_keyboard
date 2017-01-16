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
        occurences_data.replaceOccurenceByRank();

        for (int i = 0; i<50 ; i++) {
            System.out.println("i = " + i);
            Genetic genetic = new Genetic(400, 400, 40, 0.1);
            genetic.optimizePopulation();
        }

        double[] oldFitnessTab = ArrayUtils.toPrimitive(Genetic.oldFitnessList.toArray(new Double[0]));
        double[] fitnessTab = ArrayUtils.toPrimitive(Genetic.fitnessList.toArray(new Double[0]));
        StandardDeviation standardDeviation = new StandardDeviation();
        Mean meanC = new Mean();
        double deviation = standardDeviation.evaluate(oldFitnessTab);
        double mean = meanC.evaluate(oldFitnessTab);
        System.out.println("lower : " + Collections.min(Genetic.oldFitnessList));
        System.out.println("deviation : " + deviation + " | mean : " + mean);
        System.out.println("deviation ~ " + 100*deviation/mean +"%");
        deviation = standardDeviation.evaluate(fitnessTab);
        mean = meanC.evaluate(fitnessTab);
        System.out.println("***** NEW *****");
        System.out.println("lower : " + Collections.min(Genetic.fitnessList) + " | higher : " + Collections.max(Genetic.fitnessList));
        System.out.println("deviation : " + deviation + " | mean : " + mean);
        System.out.println("deviation ~ " + 100*deviation/mean +"%");

        Collections.sort(Genetic.keyboardList);

        System.out.println("Best one (" + Genetic.keyboardList.get(0).fitness() +") :");
        System.out.println(Genetic.keyboardList.get(0));
    }
}

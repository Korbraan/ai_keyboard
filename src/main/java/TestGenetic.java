import algorithms.Genetic;
import models.OccurencesData;
import tools.DataParser;

/**
 * Created by tom on 15/01/17.
 */
public class TestGenetic {

    public static void main(String[] args) {
        String data_file_path = "./datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.ParseData();

        Genetic genetic = new Genetic(100, 100, 20, 0.1);
//        genetic.optimizePopulation();
        OccurencesData occurences_data = data_parser.getOccurencesData();
        occurences_data.replaceOccurenceByRank();
        System.out.println(occurences_data);

    }
}

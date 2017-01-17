import algorithms.Genetic;
import tools.DataParser;

/**
 * Created by tom on 15/01/17.
 */
public class TestGenetic {

    public static void main(String[] args) {
        String data_file_path = "src/datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.parseData();

        for (int i = 0; i<10 ; i++) {
//            System.out.println("i = " + i);
            Genetic genetic = new Genetic(1000, 1000, 200, 0.1);
            genetic.optimizePopulation();
        }

    }
}

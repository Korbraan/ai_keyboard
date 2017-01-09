import algorithms.SimulatedAnnealing;
import gui.Window;
import models.Keyboard;
import models.OccurencesData;
import models.Position;
import tools.DataParser;

import java.util.ArrayList;

/**
 * Created by cremond on 17/12/16.
 */
public class Main {

    public static void main (String [] args){

        System.out.println("Wesh je me lance\n");

        String data_file_path = "./datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.ParseData();

        OccurencesData occurences_data = data_parser.getOccurencesData();

        System.out.println("max : " + occurences_data.getMaxOccurence());

        Keyboard k = new Keyboard();

        k.createRandomKeyboard();
        System.out.println("Tableau de lettres random");
        System.out.println(k.toString());

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(k);
        simulatedAnnealing.optimizeKeyboard();

        System.out.println(simulatedAnnealing.getKeyboard().toString());

        Window window = new Window(k);
    }

}

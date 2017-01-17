import algorithms.SimulatedAnnealing;
import gui.Window;
import models.Keyboard;
import models.Letter;
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

//        Window window = new Window(k);

//        k.createRandomKeyboard();
//        System.out.println("Tableau de lettres random");
//        System.out.println(k.toString());
//
//        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
//        simulatedAnnealing.optimizeKeyboard(k);
//        System.out.println(simulatedAnnealing.getBestKeyboard().toString() + "\nGain : "+ simulatedAnnealing.getBestKeyboard().getGain());

        Keyboard k2000 = new Keyboard();


        Letter[] azerty = new Letter[]
        {
                Letter.A,
                Letter.Z,
                Letter.E,
                Letter.R,
                Letter.T,
                Letter.Y,
                Letter.U,
                Letter.I,
                Letter.O,
                Letter.P,
                Letter.Q,
                Letter.S,
                Letter.D,
                Letter.F,
                Letter.G,
                Letter.H,
                Letter.J,
                Letter.K,
                Letter.L,
                Letter.M,
                Letter.W,
                Letter.X,
                Letter.C,
                Letter.V,
                Letter.B,
                Letter.N
        };
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                if (index < 26) {
                    Letter letter = azerty[index];
                    index++;
                    Position position = new Position(i, j);

                    k2000.getKeys()[i][j] = letter;
                    k2000.getKeyPos().put(letter, position);
                    k2000.getPosKey().put(position, letter);
                    k2000.getEmptyPos().remove(position);
                }
            }
        }
        k2000.computeGain();
        System.out.println(k2000.toString() + "\n " +  k2000.getGain());
    }

}

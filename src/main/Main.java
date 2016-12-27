package main;

import model.Keyboard;
import model.OccurencesData;
import tools.DataParser;

/**
 * Created by cremond on 17/12/16.
 */
public class Main {

    public static void main (String [] args){

        System.out.println("Wesh je me lance\n");

        String data_file_path = "./datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.ParseData();

        OccurencesData occurences = data_parser.getOccurencesData();

        /*for(int i = 0; i<occurences.length;i++){
            System.out.println("Tableau d'occurences sauvegardé");
            System.out.println("---------------------------------------");
            for(int j = 0; j<occurences.length;j++){
                System.out.print(occurences[i][j]+ " ");
            }
        }*/

        Keyboard k = new Keyboard();

        k.createRandomKeyboard();

        System.out.println("Tableau de lettres random");
        System.out.println(k.toString());
    }

}

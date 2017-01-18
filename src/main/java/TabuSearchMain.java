import algorithms.TabuSearch;
import gui.Window;
import models.Keyboard;
import models.OccurencesData;
import tools.DataParser;

/**
 * Created by cremond on 18/01/17.
 */
public class TabuSearchMain {
    public static void main (String [] args){

        String data_file_path = "/datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.parseData();

        data_parser.getOccurencesData();

        Keyboard k = new Keyboard();

        System.out.println("Random keyboard");
        k.createRandomKeyboard();

        TabuSearch tabuSearch = new TabuSearch();
        System.out.println(k.toString());
        System.out.println("Gain : " + k.getGain());

        System.out.println("Optimizing...");
        tabuSearch.optimizeKeyboard(k);

        System.out.println(tabuSearch.getBestKeyboard().toString());
        System.out.println("Gain : " + tabuSearch.getBestKeyboard().getGain());
    }
}

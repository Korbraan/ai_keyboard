import gui.Window;
import models.Keyboard;
import models.OccurencesData;
import tools.DataParser;

/**
 * Created by cremond on 17/12/16.
 */
public class Main {

    public static void main (String [] args){

        String data_file_path = "/datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.parseData();

        OccurencesData occurences_data = data_parser.getOccurencesData();

//        System.out.println("max : " + occurences_data.getMaxOccurence());

        Keyboard k = new Keyboard();

        new Window(k);
    }

}

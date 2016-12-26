/**
 * Created by cremond on 17/12/16.
 */
public class Main {

    public static void main (String [] args){

        System.out.println("Wesh je me lance");

        String data_file_path = "./datafiles/bigramFreqEng-Occurrence.dat";

        DataParser data_parser = new DataParser(data_file_path);

        data_parser.ParseData();

        long[][] occurences = data_parser.getOccurences();

        for(int i = 0; i<occurences.length;i++){
            System.out.println("Tableau d'occurences sauvegardé");
            System.out.println("---------------------------------------");
            for(int j = 0; j<occurences.length;j++){
                System.out.print(occurences[i][j]+ " ");
            }
        }
    }

}

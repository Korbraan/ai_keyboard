import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by cremond on 17/12/16.
 */
public class DataParser {
    private File data_file;
    private int[][] occurences;

    public DataParser(String data_file_path) {
        this.data_file = new File(data_file_path);
        this.occurences = new int[26][26];
    }

    public void ParseData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(data_file));
            String line = "";
            // For each line in the file, we process the data
            int j = 0;
            while ((line = br.readLine()) != null) {
                // If the ligne is not a comment
                if (line.charAt(0) != '#') {
                    String[] string_array_line = line.split(" ");
                    for(int i = 0; i < string_array_line.length; i++) {
                        occurences[j][i] = Integer.parseInt(string_array_line[i]);
                    }
                }
                j++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getOccurences() {
        return occurences;
    }

}

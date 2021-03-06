package tools;



import com.sun.org.apache.bcel.internal.util.ClassPath;
import models.OccurencesData;

import java.io.*;

/**
 * Created by cremond on 17/12/16.
 */
public class DataParser {
    private String data_file_path;
    private OccurencesData occurences_data;

    public DataParser(String data_file_path) {
        this.data_file_path = data_file_path;
    }

    public void parseData() {
        try {
            long[][] occurences = new long[26][26];
            InputStream is = DataParser.class.getResourceAsStream(data_file_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            // For each line in the file, we process the data
            int j = 0;
            while ((line = br.readLine()) != null) {
                // If line is not a comment
                if (line.charAt(0) != '#') {
                    String[] string_array_line = line.split(" ");
                    for(int i = 0; i < string_array_line.length; i++) {
                        occurences[j][i] = Long.parseLong(string_array_line[i]);
                    }
                    j++;
                }
            }
            this.occurences_data = new OccurencesData(occurences);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OccurencesData getOccurencesData() {
        return occurences_data;
    }

}

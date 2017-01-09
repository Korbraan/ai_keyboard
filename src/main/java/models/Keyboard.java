package models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static models.OccurencesData.occurences;

/**
 * Created by Steak on 27/12/2016.
 */
public class Keyboard {

    private Letter[][] keys;
    private HashMap<Letter, Position> keyPos;
    private HashMap<Letter, Double> lettersCost;
    private double cost;

    public Keyboard() {
        this.keys= new Letter[4][10];
        this.keyPos= new HashMap<Letter, Position>();
        this.lettersCost = new HashMap<Letter, Double>();
    }

    public void createRandomKeyboard() {

        Letter[] possibleValues = Letter.class.getEnumConstants();

        for(int i = 0; i < 26; i++) {
            boolean placed = false;

            while (!placed) {
                int line = ThreadLocalRandom.current().nextInt(4);
                int column = ThreadLocalRandom.current().nextInt(10);

                if(this.keys[line][column] == null) {
                    Letter letter = possibleValues[i];
                    //Add the letter to the keyboard
                    this.setKey(letter, line, column);
                    placed = true;
                }
            }
        }
    }

    public Letter[][] getKeyboard(){
        return this.keys;
    }
    public Position getPosByKey(Letter al){
        return keyPos.get(al);
    }

    /**
     * Set a letter on keyboard
     * @param al
     * @param x
     * @param y
     */
    public void setKey(Letter al, int x, int y) {
        keys[x][y]=al;
        //System.out.println(keys[x][y].getValue());
        keyPos.put(al, new Position(x,y));
    }

    public Position getLetterPosition(Letter letter) {
        return keyPos.get(letter);
    }

    public String toString() {
        String res ="";
        for(int i=0; i < keys.length; i++) {
            res+="\n";
            for(int j=0; j < keys[i].length; j++) {
                Letter current_key = keys[i][j];
                if(current_key == null) {
                    res+="  ";
                } else if (current_key.getValue() < 10) {
                    res += current_key.getValue() + " ";
                } else {
                    res += current_key.getValue();
                }
                res+="|";
            }
        }
        return res;
    }

    public double twoLettersCost(Letter l1, Letter l2) {
        Position p1 = this.getLetterPosition(l1);
        Position p2 = this.getLetterPosition(l2);

        double distance = p1.euclideanDistance(p2);
        long occurence = occurences[l1.getValue()][l2.getValue()];

        double result = distance * occurence;

        return result;
    }

    public void computeLetterCost(Letter letter) {
        double letterCost = 0;

        for (Letter other : Letter.values()) {
            if (!other.equals(letter)) {
                letterCost += twoLettersCost(letter, other);
            }
        }

        this.lettersCost.put(letter, letterCost);
    }

    public void computeCost() {
        double cost = 0;

        for (Map.Entry<Letter, Double> entry : lettersCost.entrySet()) {
            cost += entry.getValue();
        }

        this.cost = cost;
    }

    public HashMap<Letter, Double> getLettersCost() {
        return lettersCost;
    }

    public double getCost() {
        return cost;
    }

}

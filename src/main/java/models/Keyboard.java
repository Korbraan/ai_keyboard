package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static models.OccurencesData.occurences;

/**
 * Created by Steak on 27/12/2016.
 */
public class Keyboard extends java.util.Observable {

    private Letter[][] keys;
    private HashMap<Letter, Position> keyPos;
    private ArrayList<Position> emptyPos;
    private HashMap<Letter, Double> lettersCost;
    private double cost;

    public Keyboard() {
        this.keys= new Letter[4][10];
        this.keyPos= new HashMap<Letter, Position>();
        this.lettersCost = new HashMap<>();
        this.emptyPos = new ArrayList<>();
        initEmptyPos();
    }

    public Keyboard(Keyboard k) {
        this.keys = k.keys;
        this.keyPos = k.keyPos;
        this.emptyPos = k.emptyPos;
        this.lettersCost = k.lettersCost;
        this.cost = k.cost;
    }

    private void initEmptyPos() {
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                emptyPos.add(new Position(i, j));
            }
        }
    }

    public void createRandomKeyboard() {

        this.keys = new Letter[4][10];

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
        computeCost();
        this.setChanged();
        this.notifyObservers();
    }

    public Letter[][] getKeyboard(){
        return this.keys;
    }
    public Position getPosByKey(Letter al){
        return keyPos.get(al);
    }

    /**
     * Set a letter on keyboard
     * @param letter
     * @param x
     * @param y
     */
    public void setKey(Letter letter, int x, int y) {
        keys[x][y]=letter;
        //System.out.println(keys[x][y].getValue());
        Position pos = new Position(x, y);
        emptyPos.remove(pos);
        keyPos.put(letter, pos);
    }

    public Position getLetterPosition(Letter letter) {
        return keyPos.get(letter);
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < keys.length; i++) {
            res += "\n";
            for (int j = 0; j < keys[i].length; j++) {
                Letter current_key = keys[i][j];
                if (current_key == null) {
                    res += " ";
                } else {
                    res += current_key.toString();
                }
                res += "|";
            }
        }
        return res;
    }

    public double twoLettersCost(Letter l1, Letter l2) {
        Position p1 = this.getLetterPosition(l1);
        Position p2 = this.getLetterPosition(l2);

        double distance = p1.euclideanDistance(p2);
        long occurence = occurences[l1.getValue()-1][l2.getValue()-1];

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

        for (Letter letter : Letter.values()) {
            computeLetterCost(letter);
        }

        for (Map.Entry<Letter, Double> entry : lettersCost.entrySet()) {
            cost += entry.getValue();
        }

        this.cost = cost;
    }

    public Letter getWorstLetter() {
        Map.Entry<Letter, Double> maxEntry = null;

        for (Map.Entry<Letter, Double> entry : lettersCost.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public void moveLetter(Letter letter) {
        Position current_position = this.getLetterPosition(letter);
        int index = ThreadLocalRandom.current().nextInt(emptyPos.size());

        Position new_position = emptyPos.remove(index);
        emptyPos.add(current_position);
        keyPos.put(letter, new_position);
        keys[current_position.getX()][current_position.getY()] = null;
        keys[new_position.getX()][new_position.getY()] = letter;

        computeCost();
    }

    public HashMap<Letter, Double> getLettersCost() {
        return lettersCost;
    }

    public double getCost() {
        return cost;
    }

}

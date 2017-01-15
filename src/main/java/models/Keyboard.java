package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static models.OccurencesData.getMaxOccurence;
import static models.OccurencesData.occurences;

/**
 * Created by Steak on 27/12/2016.
 */
public class Keyboard extends java.util.Observable {

    private Letter[][] keys;
    private HashMap<Letter, Position> keyPos;
    private HashMap<Position, Letter> posKey;
    private ArrayList<Position> emptyPos;
    private HashMap<Letter, Double> lettersCost;
    private double cost;

    public Keyboard() {
        this.keys= new Letter[4][10];
        this.keyPos = new HashMap<>();
        this.posKey = new HashMap<>();
        this.lettersCost = new HashMap<>();
        this.emptyPos = new ArrayList<>();
        initEmptyPos();
    }

    public Keyboard(Keyboard k) {
        this.keys = k.keys;
        this.keyPos = k.keyPos;
        this.posKey = k.posKey;
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
        posKey.put(pos, letter);
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
        Position p1 = this.keyPos.get(l1);
        Position p2 = this.keyPos.get(l2);

        double distance = p1.euclideanDistance(p2);
        double occurence = occurences[l1.getValue()-1][l2.getValue()-1];
        double result = distance * occurence;

        return result;
    }

    public double getCoeff() {
        //TODO : Move that to the proper class
        Position p1 = new Position(0,0);
        Position p2 = new Position (keys.length,keys[0].length);
        return getMaxOccurence()/p1.euclideanDistance(p2);
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

    public void moveLetterInEmpty(Letter letter) {
        Position current_position = this.getLetterPosition(letter);
        int index = ThreadLocalRandom.current().nextInt(emptyPos.size());

        Position new_position = emptyPos.remove(index);
        emptyPos.add(current_position);
        keyPos.put(letter, new_position);
        posKey.put(new_position, letter);
        keys[current_position.getX()][current_position.getY()] = null;
        keys[new_position.getX()][new_position.getY()] = letter;

        computeCost();
    }

    public void moveLetter(Letter letter) {
        // TODO en fait ce serait mieux de pouvoir swap deux lettres car le résultat sera un paquet condensé de lettres
        Position initial_position = this.getLetterPosition(letter);

        int x = ThreadLocalRandom.current().nextInt(4);
        int y = ThreadLocalRandom.current().nextInt(10);
        //int index = ThreadLocalRandom.current().nextInt(empty_positions.size());

        //Position new_position = empty_positions.remove(index);
        //empty_positions.add(initial_position);

        Position new_position = new Position(x, y);

        if (emptyPos.contains(new_position)) {
            emptyPos.remove(new_position);

            posKey.remove(initial_position);
            keyPos.remove(letter);

            posKey.put(new_position, letter);
            keyPos.put(letter, new_position);

            keys[initial_position.getX()][initial_position.getY()] = null;
            keys[new_position.getX()][new_position.getY()] = letter;

        } else {
            Letter letter_to_swap = posKey.get(new_position);

            posKey.put(initial_position, letter_to_swap);
            keyPos.put(letter_to_swap, initial_position);

            posKey.put(new_position, letter);
            keyPos.put(letter, new_position);

            keys[initial_position.getX()][initial_position.getY()] = letter_to_swap;
            keys[new_position.getX()][new_position.getY()] = letter;
        }

        computeCost();
    }

    public HashMap<Letter, Double> getLettersCost() {
        return lettersCost;
    }

    public double getCost() {
        return cost;
    }

}

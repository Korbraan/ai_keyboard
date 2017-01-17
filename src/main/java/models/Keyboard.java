package models;

import java.util.ArrayList;
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
    private HashMap<Letter, Double> lettersGain;
    private double gain;

    public Keyboard() {
        this.keys= new Letter[4][10];
        this.keyPos= new HashMap<Letter, Position>();
        this.lettersGain = new HashMap<>();
        this.emptyPos = new ArrayList<>();
        initEmptyPos();
    }

    public Keyboard(Keyboard k) {
        this.keys = k.keys;
        this.keyPos = k.keyPos;
        this.emptyPos = k.emptyPos;
        this.lettersGain = k.lettersGain;
        this.gain = k.gain;
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
        computeGain();
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

    public double twoLettersGain(Letter l1, Letter l2) {
        Position p1 = this.getLetterPosition(l1);
        Position p2 = this.getLetterPosition(l2);

        double distance = p1.euclideanDistance(p2);
        long occurence = occurences[l1.getValue()-1][l2.getValue()-1];

        double result = occurence/(Math.pow(10,10) * distance);

        return result;
    }

    public void computeLetterGain(Letter letter) {
        double lettersGain = 0;

        for (Letter other : Letter.values()) {
            if (!other.equals(letter)) {
                lettersGain += twoLettersGain(letter, other);
            }
        }

        this.lettersGain.put(letter, lettersGain);
    }

    public void computeGain() {
        double gain = 0;

        for (Letter letter : Letter.values()) {
            computeLetterGain(letter);
        }

        for (Map.Entry<Letter, Double> entry : lettersGain.entrySet()) {
            gain += entry.getValue();
        }

        this.gain = gain;
        updateGUI();
    }

    public void moveLetter(Letter letter) {
        Position current_position = this.getLetterPosition(letter);
        int index = ThreadLocalRandom.current().nextInt(emptyPos.size());

        Position new_position = emptyPos.remove(index);
        emptyPos.add(current_position);
        keyPos.put(letter, new_position);
        keys[current_position.getX()][current_position.getY()] = null;
        keys[new_position.getX()][new_position.getY()] = letter;

        updateGUI();

        computeGain();
    }

    public void updateGUI(){
        setChanged();
        notifyObservers();
    }

    public double getGain() {
        return gain;
    }

    public void setKeys(Letter[][] letters){
        this.keys=letters;
    }

}

package models;

import java.util.ArrayList;
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
    // On a besoin des deux map pour aller chercher dans les deux sens
    private Map<Letter, Position> letter_to_position;
    private Map<Position, Letter> position_to_letter;
    private ArrayList<Position> empty_positions;
    private Map<Letter, Double> letters_cost;
    private double cost;

    public Keyboard() {
        this.keys= new Letter[4][10];
        this.letter_to_position = new HashMap<>();
        this.position_to_letter = new HashMap<>();
        this.letters_cost = new HashMap<>();
        this.empty_positions = new ArrayList<>();
        initEmptyPos();
    }

    public Keyboard(Keyboard k) {
        this.keys = k.keys;
        this.letter_to_position = k.letter_to_position;
        this.position_to_letter = k.position_to_letter;
        this.empty_positions = k.empty_positions;
        this.letters_cost = k.letters_cost;
        this.cost = k.cost;
    }

    private void initEmptyPos() {
        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                empty_positions.add(new Position(i, j));
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
        return letter_to_position.get(al);
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
        empty_positions.remove(pos);
        letter_to_position.put(letter, pos);
        position_to_letter.put(pos, letter);
    }

    public void setKey(Letter letter, Position p) {
        int x = p.getX();
        int y = p.getY();

        keys[x][y]=letter;
        //System.out.println(keys[x][y].getValue());
        Position pos = new Position(x, y);
        empty_positions.remove(pos);
        letter_to_position.put(letter, pos);
        position_to_letter.put(pos, letter);
    }

    public Position getLetterPosition(Letter letter) {
        return letter_to_position.get(letter);
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

        double result = distance * getCoeff() + occurence;

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

        this.letters_cost.put(letter, letterCost);
    }

    public void computeCost() {
        double cost = 0;

        for (Letter letter : Letter.values()) {
            computeLetterCost(letter);
        }

        for (Map.Entry<Letter, Double> entry : letters_cost.entrySet()) {
            cost += entry.getValue();
        }

        this.cost = cost;
    }

    public Letter getWorstLetter() {
        Map.Entry<Letter, Double> maxEntry = null;

        for (Map.Entry<Letter, Double> entry : letters_cost.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
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

        if (empty_positions.contains(new_position)) {
            empty_positions.remove(new_position);

            position_to_letter.remove(initial_position);
            letter_to_position.remove(letter);

            position_to_letter.put(new_position, letter);
            letter_to_position.put(letter, new_position);

            keys[initial_position.getX()][initial_position.getY()] = null;
            keys[new_position.getX()][new_position.getY()] = letter;

        } else {
            Letter letter_to_swap = position_to_letter.get(new_position);

            position_to_letter.put(initial_position, letter_to_swap);
            letter_to_position.put(letter_to_swap, initial_position);

            position_to_letter.put(new_position, letter);
            letter_to_position.put(letter, new_position);

            keys[initial_position.getX()][initial_position.getY()] = letter_to_swap;
            keys[new_position.getX()][new_position.getY()] = letter;
        }

        computeCost();
    }

    public Map<Letter, Double> getLetters_cost() {
        return letters_cost;
    }

    public double getCost() {
        return cost;
    }

}

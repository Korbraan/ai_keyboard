package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Steak on 27/12/2016.
 */
public class Keyboard {

    private Letter[][] keys;
    private HashMap<Letter, Position> keyPos;

    public Keyboard(){
        this.keys= new Letter[4][10];
        this.keyPos= new HashMap<Letter, Position>();
    }

    public void createRandomKeyboard(){

        Letter[] possibleValues = Letter.class.getEnumConstants();
        ArrayList<Letter> possibleValuesList = new ArrayList<Letter>();
        for(int i = 0; i<possibleValues.length; i++){
            possibleValuesList.add(possibleValues[i]);
        }

        for(int i=0; i<26; i++){
            Random r1 = new Random();
            int line = r1.nextInt(4);
            Random r2 = new Random();
            int column = r2.nextInt(10);

            if(this.keys[line][column] == null){
                Random r3 = new Random();
                int index = r3.nextInt(possibleValuesList.size());
                Letter letter = possibleValuesList.get(index);
                //Add the letter to the keyboard
                this.setKey(letter,line,column);
                possibleValuesList.remove(index);
            }else{
                i--;
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
    public void setKey(Letter al, int x, int y){
        keys[x][y]=al;
        //System.out.println(keys[x][y].getValue());
        keyPos.put(al, new Position(x,y));
    }

    public String toString(){
        String res ="";
        for(int i=0; i<keys.length; i++){
            res+="\n";
            for(int j=0; j<keys[i].length; j++){
                if(keys[i][j]==null){
                    res+="  ";
                }else {
                    res+=keys[i][j].getValue();
                }
                res+="|";
            }
        }
        return res;
    }


}

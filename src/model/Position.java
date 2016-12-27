package model;

/**
 * Created by Steak on 27/12/2016.
 */
public class Position {
    private int x;
    private int y;

    public Position(int i , int j ){
        this.x = i;
        this.y = j;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public void setX(int i){
        x = i;
    }
    public void setY(int j){
        y = j;
    }
    public void setXandY(int i,int j){
        x = i;
        y = j;
    }

    public String toString() {
        return this.x + " " + this.y;
    }
}

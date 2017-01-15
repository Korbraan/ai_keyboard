package models;


import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Steak on 27/12/2016.
 */
public class Position {
    private int x;
    private int y;

    public Position(int i , int j){
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

    @Override
    public boolean equals(Object other) {
        Position other_position = (Position) other;
        if (other_position.getX() == this.getX() && other_position.getY() == this.getY()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,31).
                append(x).
                append(y).
                toHashCode();
    }

    public double euclideanDistance(Position other) {
        double result = 0;
        result = Math.pow((double) other.getX() - this.getX(), 2);
        result = result + Math.pow((double) other.getY() - this.getY(), 2);
        result = Math.sqrt(result);

        return result;
    }

}

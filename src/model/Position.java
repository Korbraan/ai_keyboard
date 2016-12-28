package model;


import org.apache.commons.lang3.builder.EqualsBuilder;
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
    public int hashCode() {
        return new HashCodeBuilder(17,31).
                append(x).
                append(y).
                toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;
        }
        if (other == this) {
        return true;
        }

        Position other_pos = (Position) other;
        return new EqualsBuilder().
                append(x, other_pos.getX()).
                append(y, other_pos.getY()).
                isEquals();
    }
}

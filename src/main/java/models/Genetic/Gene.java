package models.Genetic;

/**
 * Created by tom on 14/01/17.
 */
public class Gene {
    private int x;
    private int y;

    public Gene(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double euclideanDistance(Gene other) {
        double result = 0;
        result = Math.pow((double) other.getX() - this.getX(), 2);
        result = result + Math.pow((double) other.getY() - this.getY(), 2);
        result = Math.sqrt((double) result);

        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gene gene = (Gene) o;

        if (x != gene.x) return false;
        return y == gene.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Gene{" + x +
                "," + y +
                '}';
    }
}

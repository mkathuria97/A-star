import java.util.HashSet;
import java.util.Set;

public class Position implements AstarableNode {
    //stores the x coordinate
    private int x;
    //stores the y coordinate
    private int y;

    //creates a new position object
    Position(){
        this(0, 0);
    }

    Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    //returns the x coordinate
    public int getX(){
        return x;
    }

    //returns the y coordinate
    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) {
            return false;
        }
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    //returns the string in the specified format when printed to the console
    @Override
    public String toString(){
        return "x=" + x + ", " + "y=" + y;
    }

}

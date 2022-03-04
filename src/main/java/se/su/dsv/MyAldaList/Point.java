/**
 * @author Vera Sol Nygren
 * klny8594
 */
package se.su.dsv.MyAldaList;

public class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point o) {
        //Pythagoras:
        return Math.hypot((double)x-o.x, (double)y-o.y);
    }

    public double xDistance(Point o) {
        return Math.abs(x - o.x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double yDistance(Point o) {
        return Math.abs(y-o.y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return x * 10000 + y;
    }


    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            "}";
    }


}
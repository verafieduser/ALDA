package se.su.dsv.MyAldaList;

public class Point implements Comparable<Point> {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point o) {
        double distance;
        if(this.compareTo(o) > 0){
            distance = Math.hypot(x-o.x, y-o.y);
        } else {
            distance = Math.hypot(o.x-x, o.y-y);
        }

        //Pythagoras:
        //double distance = Math.hypot(Math.max(x, o.x) - Math.min(x,o.x), Math.max(y, o.y) - Math.min(y, o.y));

        

        //Avrundar till 2 decimaler för att standardisera värdena:
        return distance;//Math.round(distance * 10d) / 10d;
    }

    public double xDistance(Point o) {
        return Integer.max(x, o.x) - Integer.min(x, o.x);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double yDistance(Point o) {
        return Integer.max(y, o.y) - Integer.min(y, o.y);
    }

    @Override
    public int compareTo(Point o) {
        return Integer.compare(x, o.x);
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

}
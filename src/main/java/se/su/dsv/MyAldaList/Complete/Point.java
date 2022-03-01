package se.su.dsv.MyAldaList.Complete;

public class Point implements Comparable<Point> {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point o) {
        //Pythagoras:
        double distance = Math.sqrt((   Math.pow((double)x - o.x,2) 
                                +       Math.pow((double)y - o.y,2)));
        //Avrundar till 2 decimaler p.g.a. sqrt inte pålitlig:
        return Math.round(distance * 100d) / 100d;
    }

    public double xDistance(Point o) {
        return Integer.max(x, o.x) - Integer.min(x, o.x);

    }

    public double yDistance(Point o) {
        return Integer.max(y, o.y) - Integer.min(y, o.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
package se.su.dsv.MyAldaList;

public class Point implements Comparable<Point> {
    private int x, y;
    
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    public double distanceTo(Point o){
        double distance = Integer.max(x, o.x) - Integer.min(x, o.x);
        distance += Integer.max(y, o.y) - Integer.min(y, o.y);
        return distance;
    }

    public double xDistance(Point o){
        return Integer.max(x, o.x) - Integer.min(x, o.x);

    }
    public double yDistance(Point o){
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
        return x*10000+y;
    }

}
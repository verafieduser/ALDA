package se.su.dsv.MyAldaList.ProjectTwo;

public class Node implements Comparable<Node>{

    private final SL_Stop stop;
    private final int currentRouteScore;
    private final int distanceToGoalScore;
    private final Node previous;

    //private final List<Edge> edges = new LinkedList<>();

    public Node(SL_Stop stop, int currentRouteScore, int distanceToGoalScore, Node previous) {
        this.stop = stop;
        this.currentRouteScore = currentRouteScore;
        this.distanceToGoalScore = distanceToGoalScore;
        this.previous = previous;
    }

    public SL_Stop getStop() {
        return stop;
    }

    public int getCurrentRouteScore() {
        return this.currentRouteScore;
    }

    public int getDistanceToGoalScore() {
        return this.distanceToGoalScore;
    }

    public Node getPrevious() {
        return this.previous;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        return stop.equals(node.stop);
    }

    @Override
    public int hashCode() {
        return stop.hashCode();
    }


    @Override
    public int compareTo(Node o) {
        int value = 0;
        if(distanceToGoalScore > o.distanceToGoalScore){
            value = 1;
        } else if(distanceToGoalScore < o.distanceToGoalScore){
            value = -1;
        }
        return value;
    }

}
/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.LinkedList;
import java.util.List;

public class Node implements Comparable<Node>{

    private final Station stop;
    private final List<Edge> edges = new LinkedList<>();
    private Time currentRouteScore;
    private double distanceToGoalScore;
    private Station previous;


    public Node(int id, Station stop) {
        this.stop = stop;
        this.currentRouteScore = new Time("99:00:00");
    }

    public Station getStop() {
        return stop;
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
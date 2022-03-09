package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph {

    private final int pathsToFind = 3;
//List of list of paths!!!
    private final Set<SL_Stop> nodes;
    private final Set<Edge> edges;  


    public Graph(Set<SL_Stop> nodes, Set<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

//https://www.baeldung.com/java-a-star-pathfinding
public List<Edge> aStar(SL_Stop start, SL_Stop goal, Time earliestTime){
    Queue<SL_Stop> foundNodes = new PriorityQueue<>();
    start.setCurrentRouteScore(new Time("0:0:0"));
    start.setDistanceToGoalScore(calculateDistance(start, goal));
    foundNodes.add(start);
    return aStarRecursivePart(foundNodes, goal, 0, earliestTime);
}

private List<Edge> aStarRecursivePart(Queue<SL_Stop> foundNodes, SL_Stop goal, int noOfPathsFound, Time earliestTime){
    SL_Stop current = foundNodes.poll(); //chooses the one with lowest estimated score
    if(current.equals(goal)){
        System.out.println("Found goal!");
        return collectingPath(goal, earliestTime);
    }

    Time t = Time.plus(earliestTime, current.getCurrentRouteScore());
    for(Edge edge : current.edgesAtSpecificTime(t)){
        //System.out.println(edge);
        SL_Stop stop = edge.getTo().getStop();
        Time nextStopRouteCost = Time.plus(current.getCurrentRouteScore(), edge.getCost());
        //if the new connection takes less time than the old one, add it (prohibits visited nodes to be visited over n over)
        if(nextStopRouteCost.compareTo(stop.getCurrentRouteScore())<0){
            stop.setCurrentRouteScore(nextStopRouteCost);
            stop.setDistanceToGoalScore(calculateDistance(stop, goal));
            stop.setPrevious(current);
            foundNodes.add(stop);
        }
    }
    return foundNodes.isEmpty() ? null : aStarRecursivePart(foundNodes, goal, noOfPathsFound, earliestTime);
}

private List<Edge> collectingPath(SL_Stop goal, Time earliestTime){
    List<Edge> path = new LinkedList<>(); 
    while(goal.getPrevious() != null){
        //currently time is arrival
        path.add(goal.getPrevious().edgeAtSpecificTime(earliestTime, goal));
        goal = goal.getPrevious();
    }
    return path;   
}

/**
 * Taken from https://www.baeldung.com/java-a-star-pathfinding
 * @param from
 * @param to
 * @return
 */
public double calculateDistance(SL_Stop from, SL_Stop to) {
    double R = 6372.8; // Earth's Radius, in kilometers
    double[] fromCoords = from.getLatlon();
    double[] toCoords = to.getLatlon();

    double dLat = Math.toRadians(toCoords[0] - fromCoords[0]);
    double dLon = Math.toRadians(toCoords[1] - fromCoords[1]);
    double lat1 = Math.toRadians(fromCoords[1]);
    double lat2 = Math.toRadians(toCoords[1]);

    double a = Math.pow(Math.sin(dLat / 2),2)
      + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return R * c;
}


    public Set<SL_Stop> getNodes() {
        return this.nodes;
    }


    public Set<Edge> getEdges() {
        return this.edges;
    }


    public Set<Edge> getEdges(Node node) {
        Set<Edge> nodeEdges = new HashSet<>();
        //TODO: choose implementation, edges in nodes, or just the list here
        for(Edge edge : edges){
            
        }
        return null;
    }

    public String printPath(List<Edge> path){
        if(path==null || path.isEmpty()){
            return "No path found!";
        }
        StringBuilder sb = new StringBuilder();
        Time cost = new Time("0:0:0");
        int shifts = 0;
        for(int i = path.size()-1; i >= 0; i--){
            Edge edge = path.get(i);
            if(i>0 && !edge.getType().equals(path.get(i-1).getType())){
                shifts++;
            }
            sb.append(  "\nFrom:\t" + edge.getFrom().getStop().getName() + "\n\t   departs at\t" + edge.getFrom().getDepartureTime() + 
                        "\nTo:\t" + edge.getTo().getStop().getName() + "\n\t   arrives at\t" + edge.getTo().getDepartureTime() + 
                        "\tThis step took:" + edge.getCost() + " and used the " + edge.getType());
            cost = Time.plus(cost, edge.getCost());
        }
        sb.append("\nTotal time taken: " + cost + " and changed mode of transport " + shifts + " times");

        return sb.toString();
    }

}
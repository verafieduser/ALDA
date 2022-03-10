package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;


public class Graph {

    boolean tripsByArrivalTime;
    Set<SL_Stop> nodes;

    public Graph(List<SL_Stop> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    public Graph(Set<SL_Stop> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    public List<Edge> minimumShifts(SL_Stop start, SL_Stop goal, Time earliestTime, boolean timeIsArrivalAtGoal){
        List<Edge> result = new LinkedList<>();
        List<SL_Route> connectingRoutes = findConnectingRoutes(start, goal);
        //edgecase when there is just one trip!
        for(int i = 0; i < connectingRoutes.size(); i++){
            if(i<connectingRoutes.size()-2){
                Set<SL_Stop> stops = connectingRoutes.get(i).intersectingStops(connectingRoutes.get(i+1));
                //find trip moving right direction at right time
                //getNext on the trip until you have reached one of the stops
                //add each step in between.
            }
        }
        return null;
    }

    private List<SL_Route> findConnectingRoutes(SL_Stop start, SL_Stop goal) {
        Map<SL_Route, Set<SL_Route>> routesToSearchThroughN2 = new HashMap<>();
        Map<SL_Route, Map<SL_Route, Set<SL_Route>>> routesToSearchThroughN3 = new HashMap<>();
        //N
        for(SL_Route route : start.getRoutes()){
            if(goal.getRoutes().contains(route)){
                return new LinkedList<>(Arrays.asList(route));
            }
            routesToSearchThroughN2.put(route, route.intersectingRoutes());
        } 
        List<SL_Route> result = new LinkedList<>();
        //N^2
        for(Map.Entry<SL_Route, Set<SL_Route>> entry : routesToSearchThroughN2.entrySet()){
            for(SL_Route route : entry.getValue()){
                if(goal.getRoutes().contains(route)){
                    result.add(entry.getKey());
                    result.add(route);
                    return result;
                }
                
                Map<SL_Route, Set<SL_Route>> routesN3Inner = new HashMap<>();
                routesN3Inner.put(route, route.intersectingRoutes());
                routesToSearchThroughN3.put(entry.getKey(), routesN3Inner);

            }
        }
        //N^3
        for(Map.Entry<SL_Route, Map<SL_Route, Set<SL_Route>>> entry : routesToSearchThroughN3.entrySet()){
            for(Map.Entry<SL_Route, Set<SL_Route>> entry2 : entry.getValue().entrySet()){
                for(SL_Route route : entry2.getValue()){
                    if(goal.getRoutes().contains(route)){
                        result.add(entry.getKey());
                        result.add(entry2.getKey());
                        result.add(route);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    public List<Edge> aStar(SL_Stop start, SL_Stop goal, Time earliestTime, boolean timeIsArrivalAtGoal) {
        Queue<SL_Stop> foundNodes = new PriorityQueue<>();
        start.setCurrentRouteScore(new Time("0:0:0"));
        start.setDistanceToGoalScore(calculateDistance(start, goal));
        foundNodes.add(start);
        return aStarRecursivePart(foundNodes, goal, earliestTime, timeIsArrivalAtGoal);
    }

    private List<Edge> aStarRecursivePart(Queue<SL_Stop> foundNodes, SL_Stop goal, Time earliestTime, boolean timeIsArrivalAtGoal) {
        SL_Stop current = foundNodes.poll(); // chooses the one with lowest estimated score
        if (current.equals(goal)) {
            System.out.println("Found path!");
            return pathCollection(goal, earliestTime, timeIsArrivalAtGoal);
        }
        foundNodes.addAll(addNeighbouringNodes(current, goal, earliestTime));
        return foundNodes.isEmpty() ? null : aStarRecursivePart(foundNodes, goal, earliestTime, timeIsArrivalAtGoal);
    }

    private List<SL_Stop> addNeighbouringNodes(SL_Stop current, SL_Stop goal, Time earliestTime) {
        List<SL_Stop> neighbours = new LinkedList<>();
        for (Edge edge : current.getUniqueEdges()) {
            SL_Stop stop = edge.getTo().getStop();
            Time nextStopRouteCost = Time.plus(current.getCurrentRouteScore(), edge.getCost());
            // if the new connection takes less time than the old one, add it (prohibits
            // visited nodes to be visited over n over)
            if (nextStopRouteCost.compareTo(stop.getCurrentRouteScore()) < 0) {
                stop.setCurrentRouteScore(nextStopRouteCost);
                stop.setDistanceToGoalScore(calculateDistance(stop, goal));
                stop.setPrevious(current);
                neighbours.add(stop);

            }
        }
        return neighbours;
    }

    private List<Edge> pathCollection(SL_Stop goal, Time earliestTime, boolean timeIsArrivalAtGoal) {
        LinkedList<Edge> path = new LinkedList<>();
        while (!timeIsArrivalAtGoal && goal.getPrevious() != null) {
            // currently time is arrival
            SL_Stop previous = goal.getPrevious();

            Time time = Time.plus(earliestTime, previous.getCurrentRouteScore());
            path.add(previous.edgeAtEarliestTime(time, goal));

            goal = previous;
        }
        if(timeIsArrivalAtGoal && goal.getPrevious() != null){
            SL_Stop previous = goal.getPrevious();
            path.add(previous.edgeAtLatestTime(earliestTime, goal));
            goal = previous;
            while(goal.getPrevious() != null){
                previous = goal.getPrevious();
                earliestTime = Time.minus(earliestTime, path.peekLast().getCost());
                path.add(previous.edgeAtLatestTime(earliestTime, goal));
                goal = previous;
            }
            
        }

        return path;
    }

    /**
     * Taken from https://www.baeldung.com/java-a-star-pathfinding
     * 
     * @param from
     * @param to
     * @return
     */
    //public double calculateDistance(SL_Stop from, SL_Stop to) {
        // double R = 6372.8; // Earth's Radius, in kilometers
        // double[] fromCoords = from.getLatlon();
        // double[] toCoords = to.getLatlon();

        // double dLat = Math.toRadians(toCoords[0] - fromCoords[0]);
        // double dLon = Math.toRadians(toCoords[1] - fromCoords[1]);
        // double lat1 = Math.toRadians(fromCoords[1]);
        // double lat2 = Math.toRadians(toCoords[1]);

        // double a = Math.pow(Math.sin(dLat / 2), 2)
        //         + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        // double c = 2 * Math.asin(Math.sqrt(a));
        // return R * c;}

    /**
     * http://www.movable-type.co.uk/scripts/latlong.html
     * @param from
     * @param to
     * @return
     */
    public double calculateDistance(SL_Stop from, SL_Stop to) {
        double[] latlot1 = from.getLatlon();
        double[] latlot2 = to.getLatlon();
        double lon1 = latlot1[1];
        double lat1 = latlot1[0];
        double lon2 = latlot2[1];
        double lat2 = latlot2[0];

        double R = 6372.8; //radius of the earth in km
        double dLat = Math.toRadians(lat2-lat1); 
        double dLon = Math.toRadians(lon2-lon1); 
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double angle = 
          Math.pow(Math.sin(dLat/2), 2) + 
          Math.pow(Math.sin(dLon/2), 2) *
          Math.cos(lat1Rad) * Math.cos(lat2Rad); 
        double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1-angle)); 
        return R * c; // Distance in km
    }

    public String printPath(List<Edge> path) {
        if (path == null || path.isEmpty()) {
            return "No path found!";
        }
        StringBuilder sb = new StringBuilder();
        Time cost = new Time("0:0:0");
        int shifts = 0;
        for (int i = path.size() - 1; i >= 0; i--) {
            Edge edge = path.get(i);
            edge.getTrip();
            if (i >= 1 && !edge.getTrip().equals(path.get(i - 1).getTrip())) {
                shifts++;
            }
            sb.append("\nFrom:\t"   
                        + edge.getFrom().getStop().getName() 
                        + "\n\t   departs at\t"
                        + edge.getFrom().getDepartureTime());
            sb.append("\nTo:\t"     
                        + edge.getTo().getStop().getName() 
                        + "\n\t   arrives at\t"
                        + edge.getTo().getDepartureTime());
            sb.append("\tThis step took:" 
                        + edge.getCost() 
                        + " and used the " 
                        + edge.getType());
            cost = Time.plus(cost, edge.getCost());
        }
        sb.append("\nTotal time taken: " + cost);
        sb.append(" and changed mode of transport " + shifts + " times");
        sb.append("\nPassed a total of " + (path.size()+1) + " stations!");

        return sb.toString();
    }

    public SL_Stop findNode(String query) {
        for(SL_Stop stop : nodes){
            if(stop.getName().toLowerCase().equals(query.toLowerCase())){
                return stop;
            }
        }
        return null;
    }


    // public Path findPath(SL_Stop from, SL_Stop to, Time earliestTime) {
    //     List<Path> paths = new LinkedList<>();
    //     List<SL_Stop_Time> firstPartOfPath = new LinkedList<>();

    //     paths.addAll(findCommmonRoutes(new Path(null, to), from, to, earliestTime));

    //     // TODO: find best path out of paths!

    //     return null;
    // }

    // public List<Path> findCommmonRoutes(Path path, SL_Stop from, SL_Stop to, Time earliestTime) {
    //     List<Path> paths = new LinkedList<>();
    //     path.addStop(from);

    //     Set<SL_Route> fromRoutes = from.getRoutes();
    //     Set<SL_Route> routesToGoal = to.getRoutes();

    //     List<SL_Route> sharedRoutes = new ArrayList<>();
    //     for (SL_Route route : fromRoutes) {
    //         if (routesToGoal.contains(route)) {
    //             sharedRoutes.add(route);
    //             paths.add(new Path(route, to, path.getFirstPartOfPath()));
    //         }
    //     }
    //     if (sharedRoutes.isEmpty()) {
    //         // sort transport options in from
    //         // visited nodes so far are in the path.
    //         // take a node in the direction of to according to longlat.
    //         // recur on this method.

    //         // NOTE: need to make sure if you come to subway intersection,
    //         // you take the best subway direction, even if it implies shifting? have a
    //         // boolean determine if this is active?

    //         // NOTE:
    //     }

    //     sharedRoutes.sort(null); // sorted by best transport option. should be a more hard core sorting though?
    //     for (int i = 0; i < sharedRoutes.size() && i < 3; i++) {

    //     }

    //     return paths;
    // }

    // public List<Edge> inefficientPath(SL_Stop start, SL_Stop goal, int depth) {
    //     List<SL_Route> path = new LinkedList<>();
    //     Set<SL_Route> result = new HashSet<>();
    //     for (SL_Route route : start.getRoutes()) {
    //         if (depth < 2 && goal.getRoutes().contains(route)) {
    //             path.add(route);
    //             // found goal! return it immedietly
    //         }
    //         result.addAll(route.intersectingRoutes());
    //     }

    //     for (SL_Route route : result) {
    //         if (depth < 3 && goal.getRoutes().contains(route)) {
                
    //         }
    //         result.addAll(route.intersectingRoutes());
    //     }

    //     for (SL_Route route : result) {
    //         if (depth < 4 && goal.getRoutes().contains(route)) {

    //         }
    //         result.addAll(route.intersectingRoutes());
    //     }

    //     return null;
    // }



}
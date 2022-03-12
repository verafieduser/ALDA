/**
 * @author Vera Nygren, klny8594
 */

package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class PathFinder {

    /**
     * The graph object that is responsible for calculating distances, methods for
     * finding nodes that arent
     * inside the nodes and edges already.
     */
    private Graph graph;

    /**
     * 
     * @param graph
     */
    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    /**
     * A* algorithm for finding a path between two stations. Uses geo-distance as
     * heuristic, to determine which stop is the best to
     * go to next. This can find a path efficiently - but that path is usually not
     * optimal. For example it can propose paths that require
     * many changes of modes of transport, instead of just following a line straight
     * - especially if that path is especially winding.
     * 
     * I have implemented it so that it first finds a "generic path" between
     * start and goal, using just stations, and disregarding specific departures
     * and arrivals. When such a generic path is found, it is then concretized,
     * converted into a path with departures and arrivals. This solution may have
     * cons, since you cannot change path depending on how long it is taking while
     * traversing it. However, you could get around this by returning several
     * solutions
     * whose cost are calculated and compared - returning the one that takes the
     * least
     * amount of time. However, this would drastically increase the time complexity,
     * with the worst case of traversing the entire graph becoming much, much more
     * likely.
     * I chose to just pick the first path found to make it run faster.
     * 
     * Another con I can imagine is that if this was used for an actual pathfinder
     * for public transportation, it would have a hard time adjusting for cases in
     * which there are delays on certain trips.
     * 
     * Another is if there is a specific trip that only goes once a day that is perfect
     * for the heuristic, it will prioritize that one, even if it means waiting an entire
     * day to take that one trip.
     * 
     * @param start               SL station to start the path at
     * @param goal                SL station to end the path at
     * @param time                the time you want to either depart at, or arrive
     *                            at.
     * @param timeIsArrivalAtGoal determines if time is when you arrive at
     *                            goal(true),
     *                            or whether it is when you depart from
     *                            start(false).
     * @return a list of edges that connect start and goal at times specified.
     */ // TODO: if you have time, implement "if same route, collectpath"! might lessen
        // shifts
    public List<Edge> aStar(Station start, Station goal, Time time) {
        if (start.equals(goal)) {
            System.out.println("No trip required because you are already at destination!");
            return null;
        }
        Queue<Station> foundNodes = new PriorityQueue<>();
        double startLengthOfTraveling = 0.0;
        start.setDistanceOfPath(startLengthOfTraveling);
        start.setDistanceToGoal(graph.calculateDistance(start, goal));
        foundNodes.add(start);
        return aStarRecursivePart(foundNodes, goal, time, new HashSet<>());
    }

    /**
     * Recursive part of the A* algorithm. Could be done iteratively, but I felt
     * a recursive solution is better since it reflects the fact that A* /
     * djikstra's
     * are recursive algorithms. I.e., make it more understandable.
     * 
     * @param foundNodes a priorityqueue of stations, where the station with the
     *                   lowest
     *                   distance to goal is the one pushed to the front, providing
     *                   the heuristic function
     *                   necessary for A*.
     * @param goal       the station the algorithm is striving towards.
     * @param time       the time the next departure must be earliest at.
     * @return a list of edges constituting a path from the start to the goal. the
     *         result
     *         of the A* algorithm.
     */
    private List<Edge> aStarRecursivePart(Queue<Station> foundNodes, Station goal, Time time, Set<Station> visited) {
        // chooses the station with the shortest distance to goal:
        Station current = foundNodes.poll();
        // for(Route route : current.getRoutes()){
        //     if(goal.getRoutes().contains(route)){
        //         List<Edge> result = new LinkedList<>();
        //         System.out.println("Found path!");
        //         Trip trip = route.connectingTrip(current, goal, time);
        //         result.addAll(trip.getPath(current, goal));
        //         result.addAll(pathCollection(current, time));
        //         return result;
        //     }
        // }
        visited.add(current);
        if (current.equals(goal)) {
            System.out.println("Found path!");
            return pathCollection(goal, time);
        }
        //Adds neighbouring nodes to current station to the queue to decide where to go next.
        foundNodes.addAll(addNeighbouringNodes(current, goal, visited));
        // If no more nodes were found, and foundNodes are empty,
        // that means there are no more places the algorithm could traverse.
        // This means no path was found!
        if (foundNodes.isEmpty()) {
            graph.clear();
            return null;
        }
        return aStarRecursivePart(foundNodes, goal, time, visited);
    }

    /**
     * Helper-method for A*, for finding neighbouring nodes from a stop, and then
     * assign
     * a cost to them according to the heuristic, which is geo-distance from current
     * to goal.
     * 
     * @param current stop that you want to find neighbours to.
     * @param goal    station to calculate distance to for the A* algorithm.
     * @return a list of stops that neighbours param current, with costs
     *         added for the path taken so far.
     */ // TODO: Currently error must happen in here.
    private List<Station> addNeighbouringNodes(Station current, Station goal, Set<Station> visited) {
        List<Station> neighbours = new LinkedList<>();
        for (Edge edge : current.getEdges()) {
            Station neighbour = edge.getTo().getStation();

            // the cost the path to the next stop would be
            double nextStopPathCost = current.getDistanceOfPath() + graph.calculateDistance(current, neighbour);
            // if the new connection is shorter than the old one, add it (prohibits
            // visited nodes to be visited over and over, and allows longer paths to be replaced by shorter ones)
            if (nextStopPathCost < neighbour.getDistanceOfPath()) {
                neighbour.setDistanceOfPath(nextStopPathCost);
                neighbour.setDistanceToGoal(nextStopPathCost + graph.calculateDistance(neighbour, goal));
                neighbour.setPrevious(current);
                neighbours.add(neighbour);
            }

        }
        return neighbours;
    }

    /**
     * Helper method for A* algorithm. Reconstructs the path walked in the
     * algorithm, and returns it as
     * a list of edges connecting all of them. This is the method that
     * converts the more "generic" stations into concrete departures and arrivals.
     * 
     * @param goal the end node to start the reconstruction from.
     * @param time the time you want to either depart at, or arrive
     *             at.
     * @return a list of edges that connect start and goal at time specified.
     */
    private List<Edge> pathCollection(Station goal, Time time) {
        LinkedList<Edge> path = new LinkedList<>();
        Station previous = goal.getPrevious();
        Edge currentSection = previous.edgeAtLatestTime(time, goal);
        path.add(currentSection);        
        goal=previous;         
        while (goal.getPrevious() != null) {
            previous = goal.getPrevious();
            time = path.getLast().getFrom().getTime();
            currentSection = previous.edgeAtLatestTime(time, goal); 

            path.add(currentSection);


            goal = previous;
            if (goal.getPrevious() == null) {
                return path;
            }
        }

        return null;
    }

    /**
     * A worst case N^3 method for finding a path between two stations.
     * It is constructed to return the path with the least changes between lines.
     * N^3 is proven by method SLlight.testMaxShifts() that gives 3 to be the max
     * amount of changes required for our dataset.
     * 
     * @param start the station to start from
     * @param goal  the station to arrive at
     * @param time  the time you want to find a path for
     * @return a list of edges connecting the two stations
     */
    public List<Edge> minimumShifts(Station start, Station goal, Time time) {
        List<Edge> result = new LinkedList<>();
        List<Route> connectingRoutes = findConnectingRoutes(start, goal);

        if (connectingRoutes == null || connectingRoutes.isEmpty()) {
            System.out.println("Oops! Something went wrong");
            return null;
        }

        // edge case for when there is only one route:
        if (connectingRoutes.size() == 1) {
            Trip trip = connectingRoutes.get(0).connectingTrip(start, goal, time);
            return trip.getPath(start, goal);
        }

        for (int i = 0; i < connectingRoutes.size() - 1; i++) {

            // finds the stops that intersect two routes:
            Set<Station> intersectingStops = connectingRoutes.get(i).intersectingStops(connectingRoutes.get(i + 1));
            // chooses one of those stops arbitrarily:
            Station arbitraryStop = intersectingStops.iterator().next();
            // Finds a trip at a relevant time that connects from where
            // we currently are, to the intersecting stop:
            Trip trip = connectingRoutes.get(i).connectingTrip(start, arbitraryStop, time); // trip can be null from
                                                                                            // connectingTrip()
            // TODO: clock is currently only earliest departure! never latest arrival!
            time = trip.getStopTime(arbitraryStop).getTime();
            // turn that trip into a path of edges:
            result.addAll(0, trip.getPath(start, arbitraryStop));

            // preparation for the next stop of the iteration:
            start = arbitraryStop;

        }
        Trip trip = connectingRoutes.get(connectingRoutes.size() - 1).connectingTrip(start, goal, time);
        result.addAll(0, trip.getPath(start, goal));
        return result;
    }

    /**
     * N^3 method for finding the series of routes that connect start with goal.
     * 
     * @param start the station to start at
     * @param goal  the station to end at
     * @return a list of routes that connect the two stations, min size=1, max
     *         size=3.
     */
    private List<Route> findConnectingRoutes(Station start, Station goal) {
        List<List<Route>> candidates = findConnectingRoutesRecursive(start.getRoutes(), goal, new LinkedList<>(), 0);
        List<Route> bestCandidate = new LinkedList<>();
        for (List<Route> candidate : candidates) {
            if (bestCandidate.isEmpty() || candidate.size() < bestCandidate.size()) {
                bestCandidate = candidate;
            }
        }
        return bestCandidate;
    }

    private List<List<Route>> findConnectingRoutesRecursive(Set<Route> routes, Station goal, List<Route> previousRoutes,
            int steps) {
        List<List<Route>> result = new LinkedList<>();
        for (Route route : routes) {
            if (goal.getRoutes().contains(route)) {
                List<Route> candidate = new LinkedList<>();
                candidate.addAll(previousRoutes);
                candidate.add(route);
                result.add(candidate);
            }
            List<Route> previous = new LinkedList<>(previousRoutes);
            previous.add(route);
            if (steps < 8) {
                steps += 1;
                result.addAll(findConnectingRoutesRecursive(route.intersectingRoutes(), goal, previous, steps));
            }

        }

        return result.isEmpty() ? Collections.emptyList() : result;
    }

    public String printPath(List<Edge> path) {
        if (path == null || path.isEmpty()) {
            return "No path found!";
        }
        StringBuilder sb = new StringBuilder();
        Time cost = new Time("0:0:0");
        int shifts = 0;
        Time timeOnShift = new Time("00:00:00");
        for (int i = path.size() - 1; i >= 0; i--) {
            Edge edge = path.get(i);
            if (i == path.size() - 1) {
                sb.append("\nStart with getting on the " + edge.getType() + " towards " + edge.getTrip().getHeadsign());
            }
            if (i >= 1 && i < path.size() - 1 && !edge.getTrip().equals(path.get(i + 1).getTrip())) {
                shifts++;
                String str = "\nChanged line from one going towards " + path.get(i).getTrip().getHeadsign();
                str += ", to one going towards " + path.get(i - 1).getTrip().getHeadsign();
                str += ".\nTime spent on previous line: " + timeOnShift;
                sb.append(str);
                timeOnShift = new Time("00:00:00");
            }
            sb.append("\n\tFrom:\t"
                    + edge.getFrom().getStation().getName()
                    + "\n\t\t   departs at\t"
                    + edge.getFrom().getTime());
            sb.append("\n\tTo:\t"
                    + edge.getTo().getStation().getName()
                    + "\n\t\t   arrives at\t"
                    + edge.getTo().getTime());
            timeOnShift = Time.plus(timeOnShift, edge.getCost());
            cost = Time.plus(cost, edge.getCost());

        }
        sb.append("\nTotal time taken: " + cost);
        sb.append(" and changed mode of transport " + shifts + " times");
        sb.append("\nPassed a total of " + (path.size() + 1) + " stations!");

        return sb.toString();
    }

}
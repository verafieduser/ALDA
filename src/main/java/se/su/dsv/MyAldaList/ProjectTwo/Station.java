/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for SL stations, functions as nodes in the graph. However, you can't
 * build a path directly between these,
 * since you require a specific stop-time for that, i.e., a departure from a
 * stop at a specific time - which is handled by
 * the class SLStopTime. The reason to use this class as the node is because not
 * all SLStopTimes are connected to all other
 * stations on the trip they are on - it is better to use this to first find a
 * "generic route" between start and end, and then
 * based on that find a specific path using actual SLStopTimes based on that
 * generic route.
 */
public class Station implements Comparable<Station> {
    /**
     * Unique station identifier
     */
    private final int id;
    /**
     * The name of the station, is used to locate it by search
     */
    private final String name;
    /**
     * All trips that depart or arrive at this station.
     */
    private final List<Trip> trips = new LinkedList<>();
    /**
     * All routes that in some way intersect with this stop
     */
    private final Set<Route> routes = new HashSet<>();
    /**
     * All the edges originating at this station
     */
    private final List<Edge> edges = new LinkedList<>();
    /**
     * The longitude and latitude coordinates of this station, where i0=latitude,
     * and i1=longitude.
     */
    private final double[] latlon;
    /**
     * Used to calculate how long the current path has taken to get here. Used by
     * the A* algorithm.
     * Default value is 99 hours, since that will be far above any other values that
     * will occur while pathfinding.
     */
    private Time currentRouteScore;
    /**
     * Calculates a score based on how far it is to the goal from this station. Used
     * by the A* algorithm.
     */
    private double distanceToGoalScore;
    /**
     * The previous node on the path the A* algorithm has taken so far.
     */
    private Station previous;

    /**
     * Creates a station to use in a graph. However, to create the graph, it is also
     * necessary to addConnections() and addEdges(),
     * which the SLlight-class is responsible to.
     * 
     * @param stopId   a unique identifier for the station. Used to connect with the
     *                 SLStopTimes.
     * @param stopName a name identifying the station, is used to locate the station
     *                 in searches - and allow for informative prints.
     * @param stopLat  the latitude of the station.
     * @param stopLon  the longitude of the station.
     */
    public Station(int stopId, String stopName, double stopLat, double stopLon) {
        this.id = stopId;
        this.name = stopName;
        latlon = new double[] { stopLat, stopLon };
        currentRouteScore = new Time("99:00:00");
    }

    /**
     * Adds connectivity to the station, making them from individual nodes into a
     * graph.
     * 
     * @param trip the trip the station is on, to be added to the station.
     * @return a boolean value whether the trip was successfully added or not.
     */
    public boolean addConnection(Trip trip) {
        Edge edge = trip.getNext(this);
        routes.add(trip.getRoute());
        if (edge != null) {
            edges.add(edge);
        }
        return trips.add(trip);
    }

    /**
     * Sets the previous node, used by A* to retrack the path taken when it reaches
     * goal.
     * 
     * @param previous node on the path A* took to get to this node.
     */
    public void setPrevious(Station previous) {
        this.previous = previous;
    }

    /**
     * @returns previous node on the path the A* algorithm has taken so far.
     */
    public Station getPrevious() {
        return previous;
    }

    /**
     * Returns edge to a stop after, but as close as possible, to time specified.
     * 
     * @param earliestTime time that return value needs to be after, but as close to
     *                     as possible.
     * @param stop         stop that is neighbouring to this SLStop.
     * @return an edge between this and param stop, at specified time.
     */
    public Edge edgeAtEarliestTime(Time earliestTime, Station stop) {
        Edge[] edgesAtTime = edgesAtEarliestTime(earliestTime);
        for (Edge edge : edgesAtTime) {
            if (edge.getTo().getStation().equals(stop)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Returns edges to all neighbouring stops after, but as close as possible, to
     * time specified.
     * 
     * @param earliestTime time that return value needs to be after, but as close to
     *                     as possible.
     * @return edges between this and all neighbours, at specified time.
     */
    public Edge[] edgesAtEarliestTime(Time earliestTime) {
        Map<Station, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            Station to = edge.getTo().getStation();
            StopTime from = edge.getFrom();
            // we are not interested in already departed times
            if (from.getDepartureTime().compareTo(earliestTime) >= 0) {
                Edge old = map.get(to);
                // if new is earlier than old one, if there is one, add new:
                if (old == null || from.getDepartureTime().compareTo(old.getFrom().getDepartureTime()) < 0) {
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]);
    }

    /**
     * Returns edge to a stop before, but as close as possible, to time specified.
     * 
     * @param earliestTime time that return value needs to be before, but as close
     *                     to as possible.
     * @param stop         stop that is neighbouring to this SLStop.
     * @return an edge between this and param stop, at specified time.
     */
    public Edge edgeAtLatestTime(Time latestTime, Station stop) {
        Edge[] edgesAtTime = edgesAtLatestTime(latestTime);
        for (Edge edge : edgesAtTime) {
            if (edge.getTo().getStation().equals(stop)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Returns edges to all neighbouring stops before, but as close as possible, to
     * time specified.
     * 
     * @param earliestTime time that return value needs to be before, but as close
     *                     to as possible.
     * @return edges between this and all neighbours, at specified time.
     */
    public Edge[] edgesAtLatestTime(Time latestTime) {
        Map<Station, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            Station to = edge.getTo().getStation();
            StopTime from = edge.getFrom();
            // we are not interested in already departed times
            if (from.getDepartureTime().compareTo(latestTime) <= 0) {
                Edge old = map.get(to);
                // if new is earlier than old one, if there is one, add new:
                if (old == null || from.getDepartureTime().compareTo(old.getFrom().getDepartureTime()) > 0) {
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]);
    }

    /**
     * 
     * @return all edges to unique stations neighbouring this one.
     */
    public Set<Edge> getUniqueEdges() {
        return new HashSet<>(edges);
    }

    /**
     * @return name of the station, is used to locate it by search
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return result of calculation of how long the current path has taken to get
     *         here. Used by the A* algorithm.
     *         Default value is 99 hours, since that will be far above any other
     *         values that will occur while pathfinding.
     */
    public Time getCurrentRouteScore() {
        return currentRouteScore;
    }

    /**
     * @return a calculated score based on how far it is to the goal from this
     *         station. Used by the A* algorithm.
     */
    public double getDistanceToGoalScore() {
        return distanceToGoalScore;
    }

    /**
     * @param currentRouteScore set how long it has taken to get to this station, in
     *                          A* algorithm.
     */
    public void setCurrentRouteScore(Time currentRouteScore) {
        this.currentRouteScore = currentRouteScore;
    }

    /**
     * @param distanceToGoalScore set how far it is to the goal station, according
     *                            to A* algorithm. distance
     *                            should be based on distance between two map
     *                            coordinates. method to calculate this is found in
     *                            the Graph-class.
     */
    public void setDistanceToGoalScore(double distanceToGoalScore) {
        this.distanceToGoalScore = distanceToGoalScore;
    }

    /**
     * @return a list of trips intersecting with this stop
     */
    public List<Trip> getConnections() {
        return trips;
    }

    /**
     * @return all edges originating at this station.
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * @return the name of this station
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return all routes intersecting with this stop
     */
    public Set<Route> getRoutes() {
        return routes;
    }

    /**
     * clears this stop from information used by the A* algorithm to determine paths
     * to take.
     */
    public void clear() {
        currentRouteScore = new Time("99:00:00");
        distanceToGoalScore = Double.POSITIVE_INFINITY;
        previous = null;
    }

    /**
     * @return an array with a length of 2, where i0=the latitude of this station,
     *         and i1=the longitude of this station.
     */
    public double[] getLatlon() {
        return latlon;
    }

    @Override
    public String toString() {
        return "{" +
                " \n\tstop_id='" + id + "'" +
                ", \n\tstop_name='" + name + "'" +
                ", \n\tstop_latlon='" + latlon[0] + "|" + latlon[1] + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Station)) {
            return false;
        }
        Station oStop = (Station) o;
        return id == oStop.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Station o) {
        int value = 0;
        if (distanceToGoalScore > o.distanceToGoalScore) {
            value = 1;
        } else if (distanceToGoalScore < o.distanceToGoalScore) {
            value = -1;
        }
        return value;
    }

}
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SL_Stop implements Comparable<SL_Stop> {
    private final int id;
    private final String name;
    private final List<SL_Trip> trips = new LinkedList<>();
    private final Set<SL_Route> routes = new HashSet<>();
    private final List<Edge> edges = new LinkedList<>();
    private final double[] latlon;
    private Time currentRouteScore;
    private double distanceToGoalScore;
    private SL_Stop previous;

    public SL_Stop(int stopId, String stopName, double stopLat, double stopLon) {
        this.id = stopId;
        this.name = stopName;
        latlon = new double[] { stopLat, stopLon };
        currentRouteScore = new Time();
    }

    public boolean addConnection(SL_Trip trip) {
        Edge edge = trip.getNext(this);
        routes.add(trip.getRoute());
        if (edge != null) {
            edges.add(edge);
        }
        return trips.add(trip);
    }

    public void addEdges() {
        for (SL_Trip trip : trips) {
            Edge edge = trip.getNext(this);
            if (edge != null) {
                edges.add(edge);
            }
        }
    }

    public void setPrevious(SL_Stop previous) {
        this.previous = previous;
    }

    public SL_Stop getPrevious() {
        return previous;
    }

    public Edge[] edgesAtSpecificTime(Time earliestTime) {
        Map<SL_Stop, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            SL_Stop to = edge.getTo().getStop();
            SL_Stop_Time from = edge.getFrom();
            if (from.getDepartureTime().compareTo(earliestTime) >= 0) {
                Edge edge2 = map.get(to);
                if (edge2 == null || edge2.getFrom().getDepartureTime().compareTo(from.getDepartureTime()) < 0) {
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]);
    }

    public int getId() {
        return this.id;
    }

    public Time getCurrentRouteScore() {
        return currentRouteScore;
    }

    public double getDistanceToGoalScore() {
        return distanceToGoalScore;
    }

    public void setCurrentRouteScore(Time currentRouteScore) {
        this.currentRouteScore = currentRouteScore;
    }

    public void setDistanceToGoalScore(double distanceToGoalScore) {
        this.distanceToGoalScore = distanceToGoalScore;
    }

    public List<SL_Trip> getConnections() {
        return trips;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public String getName() {
        return this.name;
    }

    public Set<SL_Route> getRoutes() {
        return routes;
    }

    private String printRoutes() {
        StringBuilder sb = new StringBuilder();
        SL_Route lastRoute = null;
        for (SL_Trip trip : trips) {
            SL_Route thisRoute = trip.getRoute();
            if (lastRoute != thisRoute) {
                sb.append("\n\t" + thisRoute);
                lastRoute = thisRoute;
            }

        }
        return sb.toString();
    }

    public double[] getLatlon() {
        return latlon;
    }

    @Override
    public String toString() {
        return "{" +
                " \n\tstop_id='" + id + "'" +
                ", \n\tstop_name='" + name + "'" +
                ", \n\tconnections='" + printRoutes() + "'" +
                ", \n\tstop_latlon='" + latlon[0] + "|" + latlon[1] + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SL_Stop)) {
            return false;
        }
        SL_Stop oStop = (SL_Stop) o;
        return id == oStop.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(SL_Stop o) {
        int value = 0;
        if (distanceToGoalScore > o.distanceToGoalScore) {
            value = 1;
        } else if (distanceToGoalScore < o.distanceToGoalScore) {
            value = -1;
        }
        return value;
    }

}
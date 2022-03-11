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

public class SLStop implements Comparable<SLStop> {
    private final int id;
    private final String name;
    private final List<SLTrip> trips = new LinkedList<>();
    private final Set<SLRoute> routes = new HashSet<>();
    private final List<Edge> edges = new LinkedList<>();
    private final double[] latlon;
    private Time currentRouteScore;
    private double distanceToGoalScore;
    private SLStop previous;

    public SLStop(int stopId, String stopName, double stopLat, double stopLon) {
        this.id = stopId;
        this.name = stopName;
        latlon = new double[] { stopLat, stopLon };
        currentRouteScore = new Time("99:00:00");
    }

    public boolean addConnection(SLTrip trip) {
        Edge edge = trip.getNext(this);
        routes.add(trip.getRoute());
        if (edge != null) {
            edges.add(edge);
        }
        return trips.add(trip);
    }

    public void addEdges() {
        for (SLTrip trip : trips) {
            Edge edge = trip.getNext(this);
            if (edge != null) {
                edges.add(edge);
            }
        }
    }

    public void setPrevious(SLStop previous) {
        this.previous = previous;
    }

    public SLStop getPrevious() {
        return previous;
    }

    public Edge edgeAtEarliestTime(Time earliestTime, SLStop stop){
        Edge[] edgesAtTime = edgesAtEarliestTime(earliestTime);
        for(Edge edge : edgesAtTime){
            if(edge.getTo().getStop().equals(stop)){
                return edge;
            }
        }
        return null;
    }

    public Edge[] edgesAtEarliestTime(Time earliestTime) {
        Map<SLStop, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            SLStop to = edge.getTo().getStop();
            SLStopTime from = edge.getFrom();
            //we are not interested in already departed times
            if (from.getDepartureTime().compareTo(earliestTime) >= 0) {
                Edge old = map.get(to);
                //if new is earlier than old one, if there is one, add new:
                if (old == null || from.getDepartureTime().compareTo(old.getFrom().getDepartureTime()) < 0) {
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]);
    }

    public Edge edgeAtLatestTime(Time latestTime, SLStop stop){
        Edge[] edgesAtTime = edgesAtLatestTime(latestTime);
        for(Edge edge : edgesAtTime){
            if(edge.getTo().getStop().equals(stop)){
                return edge;
            }
        }
        return null;
    }

    public Edge[] edgesAtLatestTime(Time latestTime) {
        Map<SLStop, Edge> map = new HashMap<>();
        for (Edge edge : edges) {
            SLStop to = edge.getTo().getStop();
            SLStopTime from = edge.getFrom();
            //we are not interested in already departed times
            if (from.getDepartureTime().compareTo(latestTime) <= 0) {
                Edge old = map.get(to);
                //if new is earlier than old one, if there is one, add new:
                if (old == null || from.getDepartureTime().compareTo(old.getFrom().getDepartureTime()) > 0) {
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]);
    }

    public Set<Edge> getUniqueEdges(){
        return new HashSet<>(edges);
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

    public List<SLTrip> getConnections() {
        return trips;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public String getName() {
        return this.name;
    }

    public Set<SLRoute> getRoutes() {
        return routes;
    }

    private String printRoutes() {
        StringBuilder sb = new StringBuilder();
        SLRoute lastRoute = null;
        for (SLTrip trip : trips) {
            SLRoute thisRoute = trip.getRoute();
            if (lastRoute != thisRoute) {
                sb.append("\n\t" + thisRoute);
                lastRoute = thisRoute;
            }

        }
        return sb.toString();
    }

    public void clear(){
        currentRouteScore = new Time("99:00:00");
        distanceToGoalScore = Double.POSITIVE_INFINITY;
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
        if (!(o instanceof SLStop)) {
            return false;
        }
        SLStop oStop = (SLStop) o;
        return id == oStop.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(SLStop o) {
        int value = 0;
        if (distanceToGoalScore > o.distanceToGoalScore) {
            value = 1;
        } else if (distanceToGoalScore < o.distanceToGoalScore) {
            value = -1;
        }
        return value;
    }

}
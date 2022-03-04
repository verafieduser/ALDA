package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SL_Stop{
    private int id;
    private String name;
    List<SL_Trip> trips = new LinkedList<>();
    Set<SL_Route> routes = new HashSet<>();
    List<Edge> edges = new LinkedList<>();
    private double[] latlon;

    public SL_Stop(int stop_id, String stop_name, double stop_lat, double stop_lon) {
        this.id = stop_id;
        this.name = stop_name;
        latlon = new double[] {stop_lat, stop_lon};
    }

    public boolean addConnection(SL_Trip trip){
        Edge edge = trip.getNext(this);
        routes.add(trip.getRoute());
        if(edge != null){
            edges.add(edge);
        }
        return trips.add(trip);
    }

    public void addEdges(){
        for(SL_Trip trip : trips){
            Edge edge = trip.getNext(this);
            if(edge!=null){
                edges.add(edge);
            }
        }
    }

    public Edge[] edgesAtSpecificTime(Time earliestTime){
        Map<SL_Stop, Edge> map = new HashMap<>();
        for(Edge edge : edges){
            SL_Stop to = edge.getTo().getStop();
            SL_Stop_Time from = edge.getFrom();
            if(from.getDepartureTime().compareTo(earliestTime) >= 0){
                Edge edge2 = map.get(to);
                if(edge2 == null || edge2.getFrom().getDepartureTime().compareTo(from.getDepartureTime()) < 0){
                    map.put(to, edge);
                }
            }
        }
        return map.values().toArray(new Edge[map.size()]); 
    }

    public int getId() {
        return this.id;
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

    private String printRoutes(){
        StringBuilder sb = new StringBuilder();
        SL_Route lastRoute = null;
        for(SL_Trip trip : trips){
            SL_Route thisRoute = trip.getRoute(); 
            if(lastRoute != thisRoute){
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
            ", \n\tstop_latlon='" + latlon[0] +"|" + latlon[1]  + "'" +
            "}";
    }


}
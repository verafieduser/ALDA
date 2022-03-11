/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Trip {
    /**
     * The route this trip is on
     */
    private Route route;
    /**
     * Unique identifier for trip:
     */
    private long id;
    /**
     * Terminal station name:
     */
    private String headsign;

    private List<StopTime> stopTimes = new ArrayList<>();
    private List<Station> stops = new LinkedList<>();

    public Trip(Route route, long id, String headsign) {
        this.route = route;
        this.id = id;
        this.headsign = headsign;
    }

    public StopTime getStopTime(int i) {
        return stopTimes.get(i);
    }

    public Edge getNext(Station currentStop) {
        StopTime from = null;
        StopTime to = null; 

        for(StopTime stopTime : stopTimes){
            if(stopTime.getStation().equals(currentStop)){
                from = stopTime;
            }
        }

        if(from == null){
            return null;
        }
        
        to=getNext(from);

        if(to == null){
            return null;
        }

        return new Edge(from, to, route.getType());
    }


    public StopTime getNext(StopTime from) {
        int currentStopSeq = from.getSequence();

        if(currentStopSeq >= stopTimes.size()-1){
            return null;
        }

        StopTime candidate1 = stopTimes.get(currentStopSeq);
        if(candidate1.getSequence()-2 <= 0){
            return candidate1;
        }
        StopTime candidate2 = stopTimes.get(currentStopSeq-2);
        
        return  candidate1.getDepartureTime().compareTo(candidate2.getDepartureTime()) < 0 ? 
                candidate2 : candidate1;
    }

    public List<Edge> getPath(Station from, Station to) {
        return getPath(getStopTime(from), getStopTime(to));
    }

    public List<Edge> getPath(StopTime from, StopTime to){
        List<Edge> path = new LinkedList<>();

        StopTime next = getNext(from);
        while(from != null && next != null && next != to){
            path.add(new Edge(from, next, route.getType()));
            from = next;
            next = getNext(from);
        }
        if(from == null){
            throw new IllegalArgumentException("From was null! ");
        }
        path.add(new Edge(from, to, route.getType()));
        Collections.reverse(path);
        return path;
    }

    /**
     * Determines whether it is possible to go from a station to another on this trip.
     * @param from is a station
     * @param to is another station
     * @return true if both are on the same trip, and to is later than from.
     */
    public boolean traversable(Station from, Station to){
        StopTime fromTime = getStopTime(from);
        StopTime toTime = getStopTime(to);
        if(fromTime == null || toTime == null){
            return false;
        }
        return fromTime.getDepartureTime().compareTo(toTime.getDepartureTime()) < 0;

    }

    
    public StopTime getStopTime(Station stop){
        for(StopTime stopTime : stopTimes){
            if(stopTime.getStation().equals(stop)){
                return stopTime;
            }
        }
        return null;
    }

    public List<StopTime> getStopTimes() {
        return this.stopTimes;
    }

    public List<Station> getStops() {
        return this.stops;
    }

    public boolean addStopTime(StopTime stopTime) {
        stops.add(stopTime.getStation());
        return stopTimes.add(stopTime);
    }

    public void sortStopTimes() {
        stopTimes.sort(null);
    }

    public Route getRoute() {
        return this.route;
    }

    public long getId() {
        return this.id;
    }

    public String getHeadsign() {
        return this.headsign;
    }

    @Override
    public String toString() {
        return "{" +
                " route='" + getRoute() + "'" +
                ", id='" + getId() + "'" +
                ", headsign='" + getHeadsign() + "'" +
                ", stopTimes='" + getStopTimes() + "'" +
                // ", stops='" + getStops() + "'" +
                "}";
    }

}
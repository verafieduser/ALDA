/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class SLTrip {
    private SLRoute route;
    /**
     * Unique identifier for trip:
     */
    private long id;
    /**
     * Terminal station name:
     */
    private String headsign;

    private List<SLStopTime> stopTimes = new ArrayList<>();
    private List<SLStop> stops = new LinkedList<>();

    public SLTrip(SLRoute route, long id, String headsign) {
        this.route = route;
        this.id = id;
        this.headsign = headsign;
    }

    public SLStopTime getStopTime(int i) {
        return stopTimes.get(i);
    }

    public Edge getNext(SLStop currentStop) {
        SLStopTime from = null;
        SLStopTime to = null; 

        for(SLStopTime stopTime : stopTimes){
            if(stopTime.getStop().equals(currentStop)){
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


    public SLStopTime getNext(SLStopTime from) {
        int currentStopSeq = from.getSequence();

        if(currentStopSeq >= stopTimes.size()-1){
            return null;
        }

        SLStopTime candidate1 = stopTimes.get(currentStopSeq);
        if(candidate1.getSequence()-2 <= 0){
            return candidate1;
        }
        SLStopTime candidate2 = stopTimes.get(currentStopSeq-2);
        
        return  candidate1.getDepartureTime().compareTo(candidate2.getDepartureTime()) < 0 ? 
                candidate2 : candidate1;
    }

    public List<Edge> getPath(SLStop from, SLStop to) {
        return getPath(getStopTime(from), getStopTime(to));
    }

    public List<Edge> getPath(SLStopTime from, SLStopTime to){
        List<Edge> path = new LinkedList<>();

        SLStopTime next = getNext(from);
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
    public boolean traversable(SLStop from, SLStop to){
        SLStopTime fromTime = getStopTime(from);
        SLStopTime toTime = getStopTime(to);
        if(fromTime == null || toTime == null){
            return false;
        }
        return fromTime.getDepartureTime().compareTo(toTime.getDepartureTime()) < 0;

    }

    
    public SLStopTime getStopTime(SLStop stop){
        for(SLStopTime stopTime : stopTimes){
            if(stopTime.getStop().equals(stop)){
                return stopTime;
            }
        }
        return null;
    }

    public List<SLStopTime> getStopTimes() {
        return this.stopTimes;
    }

    public List<SLStop> getStops() {
        return this.stops;
    }

    public boolean addStopTime(SLStopTime stopTime) {
        stops.add(stopTime.getStop());
        return stopTimes.add(stopTime);
    }

    public void sortStopTimes() {
        stopTimes.sort(null);
    }

    public SLRoute getRoute() {
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
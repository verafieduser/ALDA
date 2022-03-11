package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class SL_Trip {
    private SL_Route route;
    /**
     * Unique identifier for trip:
     */
    private long id;
    /**
     * Terminal station name:
     */
    private String headsign;

    private List<SL_Stop_Time> stopTimes = new ArrayList<>();
    private List<SL_Stop> stops = new LinkedList<>();

    public SL_Trip(SL_Route route, long id, String headsign) {
        this.route = route;
        this.id = id;
        this.headsign = headsign;
    }

    public SL_Stop_Time getStopTime(int i) {
        return stopTimes.get(i);
    }

    public Edge getNext(SL_Stop currentStop) {
        SL_Stop_Time from = null;
        SL_Stop_Time to = null; 

        for(SL_Stop_Time stopTime : stopTimes){
            if(stopTime.getStop().equals(currentStop)){
                from = stopTime;
            }
        }

        if(from == null){
            return null;
        }
        
        to=getNext(from);

        return new Edge(from, to, route.getType());
    }


    public SL_Stop_Time getNext(SL_Stop_Time from) {
        int currentStopSeq = from.getSequence();

        if(currentStopSeq >= stopTimes.size()-1){
            return null;
        }

        SL_Stop_Time candidate1 = stopTimes.get(currentStopSeq);
        SL_Stop_Time candidate2 = stopTimes.get(currentStopSeq-2);
        
        return  candidate1.getDepartureTime().compareTo(candidate2.getDepartureTime()) < 0 ? 
                candidate2 : candidate1;
    }

    public List<Edge> getPath(SL_Stop from, SL_Stop to) {
        return getPath(getStopTime(from), getStopTime(to));
    }

    public List<Edge> getPath(SL_Stop_Time from, SL_Stop_Time to){
        List<Edge> path = new LinkedList<>();

        SL_Stop_Time next = getNext(from);
        while(next != to){
            path.add(new Edge(from, next, route.getType()));
            from = next;
            next = getNext(from);
        }
        path.add(new Edge(from, next, route.getType()));
        return path;
    }

    /**
     * Determines whether it is possible to go from a station to another on this trip.
     * @param from is a station
     * @param to is another station
     * @return true if both are on the same trip, and to is later than from.
     */
    public boolean traversable(SL_Stop from, SL_Stop to){
        SL_Stop_Time fromTime = getStopTime(from);
        SL_Stop_Time toTime = getStopTime(to);
        if(fromTime == null || toTime == null){
            return false;
        }
        return fromTime.getDepartureTime().compareTo(toTime.getDepartureTime()) < 0;

    }

    
    public SL_Stop_Time getStopTime(SL_Stop stop){
        for(SL_Stop_Time stopTime : stopTimes){
            if(stopTime.getStop().equals(stop)){
                return stopTime;
            }
        }
        return null;
    }

    public List<SL_Stop_Time> getStopTimes() {
        return this.stopTimes;
    }

    public List<SL_Stop> getStops() {
        return this.stops;
    }

    public boolean addStopTime(SL_Stop_Time stopTime) {
        stops.add(stopTime.getStop());
        return stopTimes.add(stopTime);
    }

    public void sortStopTimes() {
        stopTimes.sort(null);
    }

    public SL_Route getRoute() {
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
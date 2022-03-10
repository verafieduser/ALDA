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
        int currentStopSeq = from.getSequence();

        if(currentStopSeq >= stopTimes.size()-1){
            return null;
        }

        to = stopTimes.get(currentStopSeq);
        return new Edge(from, to, route.getType());
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
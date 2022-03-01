package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class SL_Trip {
    private SL_Route route;
    /**
     * Unique identifier for trip:
     */
    private long trip_id;
    /**
     * Terminal station name:
     */
    private String trip_headsign;

    private List<SL_Stop_Time> stopTimes = new ArrayList<>();
    private List<SL_Stop> stops = new LinkedList<>();

    public SL_Trip(SL_Route route, long trip_id, String trip_headsign) {
        this.route = route;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
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
        int currentStopSeq = from.getStop_sequence();

        if(currentStopSeq >= stopTimes.size()-1){
            return null;
        }

        to = stopTimes.get(currentStopSeq);
        return new Edge(from, to);
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

    public long getTrip_id() {
        return this.trip_id;
    }

    public String getTrip_headsign() {
        return this.trip_headsign;
    }

    @Override
    public String toString() {
        return "{" +
                " route='" + getRoute() + "'" +
                ", trip_id='" + getTrip_id() + "'" +
                ", trip_headsign='" + getTrip_headsign() + "'" +
                ", stopTimes='" + getStopTimes() + "'" +
                // ", stops='" + getStops() + "'" +
                "}";
    }

}
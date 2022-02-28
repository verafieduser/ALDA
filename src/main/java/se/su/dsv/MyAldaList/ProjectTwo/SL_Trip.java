package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
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

    private List<SL_Stop_Time> stopTimes = new LinkedList<>();
    private List<SL_Stop> stops = new LinkedList<>();

    public SL_Stop_Time getNextStops(SL_Stop currentStop, short[] departureTime){
        return null;
    }

    public List<SL_Stop_Time> getStopTimes() {
        return this.stopTimes;
    }

    public List<SL_Stop> getStops() {
        return this.stops;
    }

    public SL_Trip(SL_Route route, long trip_id, String trip_headsign) {
        this.route = route;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
    }

    public boolean addStopTime(SL_Stop_Time stopTime) {
        stops.add(stopTime.getStop());
        boolean returnValue = stopTimes.add(stopTime);
        return returnValue;
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
        return "\nSL_TRIP: {" +
                "\n\t route='" + getRoute() + "'" +
                ",\n\t trip_id='" + getTrip_id() + "'" +
                ",\n\t trip_headsign='" + getTrip_headsign() + "'" +
                "}";
    }

}
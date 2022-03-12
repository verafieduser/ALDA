/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * A trip of stoptimes. Is to Routes, what stopTimes is to Stations.
 */
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

    /**
     * The StopTimes on this trip
     */
    private List<StopTime> stopTimes = new ArrayList<>();
    /**
     * the stations that is on this trip
     */
    private List<Station> stops = new LinkedList<>();

    /**
     * constructs a trip
     * 
     * @param route    the route this trip is on
     * @param id       unique identifier for this trip
     * @param headsign the destination for the trip
     */
    public Trip(Route route, long id, String headsign) {
        this.route = route;
        this.id = id;
        this.headsign = headsign;
    }

    /**
     * @return specific stoptime on this trip
     */
    public StopTime getStopTime(int i) {
        return stopTimes.get(i);
    }

    /**
     * finds the edge originating at the station provided.
     * 
     * @param currentStop a station that is on this trip
     * @return an edge originating at the station provided, null if none was found
     */
    public Edge getNext(Station currentStop) {
        StopTime from = null;
        StopTime to = null;

        for (StopTime stopTime : stopTimes) {
            if (stopTime.getStation().equals(currentStop)) {
                from = stopTime;
            }
        }

        if (from == null) {
            return null;
        }

        to = getNext(from);

        if (to == null) {
            return null;
        }

        return new Edge(from, to, route.getType());
    }

    /**
     * returns the next stoptime on the trip, to the one provided
     * 
     * @param from the stoptime you want the successor for
     * @return the stoptime which is after param from, on this trip
     */
    public StopTime getNext(StopTime from) {
        int currentStopSeq = from.getSequence();

        if (currentStopSeq > stopTimes.size() - 1) {
            return null;
        }

        StopTime candidate1 = stopTimes.get(currentStopSeq);
        if (candidate1.getSequence() - 2 <= 0) {
            return candidate1;
        }
        StopTime candidate2 = stopTimes.get(currentStopSeq - 2);

        return candidate1.getTime().compareTo(candidate2.getTime()) < 0 ? candidate2 : candidate1;
    }

    /**
     * gets you a path between two stations on this trip
     * 
     * @param from a station that must be on this trip
     * @param to   another station that must be on this trip, and later than from.
     * @return a list of edges constituting a path between the two specified
     *         stations provided
     */
    public List<Edge> getPath(Station from, Station to) {
        return getPath(getStopTime(from), getStopTime(to));
    }

    /**
     * gets you a path between two stoptimes on this trip
     * 
     * @param from a stoptime on this trip
     * @param to   another stoptime on this trip, that must be later than param
     *             from.
     * @return a list of edges constituting a path between the two specified
     *         stations provided.
     */
    public List<Edge> getPath(StopTime from, StopTime to) {
        List<Edge> path = new LinkedList<>();

        StopTime next = getNext(from);
        while (from != null && next != null && next != to) {
            path.add(new Edge(from, next, route.getType()));
            from = next;
            next = getNext(from);
        }
        if (from == null) {
            throw new IllegalArgumentException("From was null! ");
        }
        path.add(new Edge(from, to, route.getType()));
        Collections.reverse(path);
        return path;
    }

    /**
     * Determines whether it is possible to go from a station to another on this
     * trip.
     * 
     * @param from is a station
     * @param to   is another station
     * @return true if both are on the same trip, and to is later than from.
     */
    public boolean traversable(Station from, Station to) {
        StopTime fromTime = getStopTime(from);
        StopTime toTime = getStopTime(to);
        if (fromTime == null || toTime == null) {
            return false;
        }
        return fromTime.getTime().compareTo(toTime.getTime()) < 0;

    }

    /**
     * gets you the stoptime which is when the trip is at the station specified.
     * 
     * @param stop station that must be on this line.
     * @return the stoptime of the station provided, on this trip. null if not
     *         found.
     */
    public StopTime getStopTime(Station stop) {
        for (StopTime stopTime : stopTimes) {
            if (stopTime.getStation().equals(stop)) {
                return stopTime;
            }
        }
        return null;
    }

    /**
     * @return a list of all the stoptimes on this trip.
     */
    public List<StopTime> getStopTimes() {
        return this.stopTimes;
    }

    /**
     * @return a list of stops on this trip.
     */
    public List<Station> getStops() {
        return this.stops;
    }

    /**
     * Method used to add the stoptimes that constitute this trip. This occurs while
     * linking data
     * in the class SLLight.
     * 
     * @param stopTime the stoptime to be added
     * @return a boolean value representing if the addition was successful or not.
     */
    public boolean addStopTime(StopTime stopTime) {
        stops.add(stopTime.getStation());
        return stopTimes.add(stopTime);
    }

    /**
     * @return the route this trip is a part of
     */
    public Route getRoute() {
        return this.route;
    }

    /**
     * @return the unique identifier for this trip
     */
    public long getId() {
        return this.id;
    }

    /**
     * @return the destination for this trip
     */
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
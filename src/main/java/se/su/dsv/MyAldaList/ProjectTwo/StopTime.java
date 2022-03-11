/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

/**
 * Class for departures from a station, on a specific route. Is what is
 * traversed in edges for
 * pathfinding.
 */
public class StopTime implements Comparable<StopTime> {
    /**
     * The trip this station time is on
     */
    private Trip trip;
    /**
     * The time the trip is here
     */
    private Time time;
    /**
     * The station this station occurs at
     */
    private Station station;
    /**
     * The number it is at on the trip
     */
    private short sequence;

    /**
     * Constructs a station time
     * 
     * @param trip           the trip the station time is on
     * @param departure_time the time the trip is on this station
     * @param station        the station this station occurs at
     * @param stop_sequence  the number it is at on the trip
     */
    public StopTime(Trip trip, String departure_time, Station station, short stop_sequence) {
        this.trip = trip;
        this.station = station;
        this.sequence = stop_sequence;
        time = new Time(departure_time);
    }

    /**
     * @return the trip this station time is a part of
     */
    public Trip getTrip() {
        return this.trip;
    }

    /**
     * @return the time this station occurs
     */
    public Time getTime() {
        return time;
    }

    /**
     * @return the station this station is on
     */
    public Station getStation() {
        return this.station;
    }

    /**
     * @return which stop this stop time is on the trip
     */
    public short getSequence() {
        return this.sequence;
    }

    @Override
    public int compareTo(StopTime o) {
        Short own = sequence;
        Short other = o.getSequence();
        return own.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StopTime)) {
            return false;
        }
        StopTime other = (StopTime) o;
        return station.equals(other.station) && time.equals(other.time);
    }

    @Override
    public int hashCode() {
        return station.hashCode() + time.hashCode();
    }

    @Override
    public String toString() {
        return "\nSL_STOP_TIME: {" +
                "\n\t trip='" + getTrip().getHeadsign() + "'" +
                ",\n\t departure_time='" + getTime() + "'" +
                ",\n\t station='" + getStation().getName() + "'" +
                ",\n\t stop_sequence='" + getSequence() + "'" +
                "}";
    }

}
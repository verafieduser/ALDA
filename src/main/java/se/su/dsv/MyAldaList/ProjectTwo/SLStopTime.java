/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

public class SLStopTime implements Comparable<SLStopTime> {
    private SLTrip trip;
    private Time time;
    private SLStop stop;
    private short sequence;

    public SLStopTime(SLTrip trip, String departure_time, SLStop stop, short stop_sequence) {
        this.trip = trip;
        this.stop = stop;
        this.sequence = stop_sequence;
        time = new Time(departure_time);
    }

    public SLTrip getTrip() {
        return this.trip;
    }

    public Time getDepartureTime() {
        return time;
    }

    public SLStop getStop() {
        return this.stop;
    }

    public short getSequence() {
        return this.sequence;
    }

    @Override
    public int compareTo(SLStopTime o) {
        Short own = sequence;
        Short other = o.getSequence();
        return own.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SLStopTime)) {
            return false;
        }
        SLStopTime other = (SLStopTime) o;
        return stop.equals(other.stop) && time.equals(other.time);
    }

    @Override
    public int hashCode() {
        return stop.hashCode() + time.hashCode();
    }

    @Override
    public String toString() {
        return "\nSL_STOP_TIME: {" +
                "\n\t trip='" + getTrip().getHeadsign() + "'" +
                ",\n\t departure_time='" + getDepartureTime() + "'" +
                ",\n\t stop='" + getStop().getName() + "'" +
                ",\n\t stop_sequence='" + getSequence() + "'" +
                "}";
    }

}
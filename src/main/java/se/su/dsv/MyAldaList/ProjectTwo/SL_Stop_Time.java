package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop_Time implements Comparable<SL_Stop_Time>{
    private SL_Trip trip;
    private Time departureTime;
    private SL_Stop_Time next; 
    private SL_Stop stop;
    private short sequence;


    public SL_Stop_Time(SL_Trip trip, String departure_time, SL_Stop stop, short stop_sequence) {
        this.trip = trip;
        this.stop = stop;
        this.sequence = stop_sequence;
        departureTime = new Time(departure_time);
    }

    public SL_Trip getTrip() {
        return this.trip;
    }

    public SL_Stop_Time getNext(){
        return next;
    }

    public void setNext(SL_Stop_Time next) {
        this.next = next;
    }

    public Time getDepartureTime() {
        return departureTime;
    }


    public SL_Stop getStop() {
        return this.stop;
    }

    public short getSequence() {
        return this.sequence;
    }

    @Override
    public int compareTo(SL_Stop_Time o) {
        Short own = sequence;
        Short other = o.getSequence();
        return own.compareTo(other);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SL_Stop_Time)) {
            return false;
        }
        SL_Stop_Time other = (SL_Stop_Time) o;
        return stop.equals(other.stop) && departureTime.equals(other.departureTime);
    }

    @Override
    public int hashCode() {
        return stop.hashCode()+departureTime.hashCode();
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
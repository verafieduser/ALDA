package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop_Time implements Comparable<SL_Stop_Time>{
    private SL_Trip trip;
    private String departure_time;
    private SL_Stop stop;
    private short stop_sequence;


    public SL_Stop_Time(SL_Trip trip, String departure_time, SL_Stop stop, short stop_sequence) {
        this.trip = trip;
        this.departure_time = departure_time;
        this.stop = stop;
        this.stop_sequence = stop_sequence;
    }


    public SL_Trip getTrip() {
        return this.trip;
    }


    public String getDeparture_time() {
        return this.departure_time;
    }

    public SL_Stop getStop() {
        return this.stop;
    }

    public short getStop_sequence() {
        return this.stop_sequence;
    }

    @Override
    public int compareTo(SL_Stop_Time o) {
        Short own = stop_sequence;
        Short other = o.getStop_sequence();
        return own.compareTo(other);
    }

    @Override
    public String toString() {
        return "\nSL_STOP_TIME: {" +
            "\n\t trip='" + getTrip() + "'" +
            ",\n\t departure_time='" + getDeparture_time() + "'" +
            ",\n\t stop='" + getStop() + "'" +
            ",\n\t stop_sequence='" + getStop_sequence() + "'" +
            "}";
    }





}
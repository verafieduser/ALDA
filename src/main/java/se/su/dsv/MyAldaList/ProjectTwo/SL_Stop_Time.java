package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop_Time{
    private long trip_id;
    private String departure_time;
    private int stop_id;
    private short stop_sequence;


    public SL_Stop_Time(long trip_id, String departure_time, int stop_id, short stop_sequence) {
        this.trip_id = trip_id;
        this.departure_time = departure_time;
        this.stop_id = stop_id;
        this.stop_sequence = stop_sequence;
    }


    public long getTrip_id() {
        return this.trip_id;
    }


    public String getDeparture_time() {
        return this.departure_time;
    }

    public int getStop_id() {
        return this.stop_id;
    }

    public short getStop_sequence() {
        return this.stop_sequence;
    }


    @Override
    public String toString() {
        return "{" +
            "\t trip_id='" + getTrip_id() + "'" +
            ",\n\t departure_time='" + getDeparture_time() + "'" +
            ",\n\t stop_id='" + getStop_id() + "'" +
            ",\n\t stop_sequence='" + getStop_sequence() + "'" +
            "}\n\t";
    }

}
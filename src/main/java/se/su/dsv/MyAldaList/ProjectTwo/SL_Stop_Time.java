package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop_Time{
    private int trip_id;
    private String departure_time;
    private int stop_id;
    private short stop_sequence;


    public SL_Stop_Time(int trip_id, String departure_time, int stop_id, short stop_sequence) {
        this.trip_id = trip_id;
        this.departure_time = departure_time;
        this.stop_id = stop_id;
        this.stop_sequence = stop_sequence;
    }

}
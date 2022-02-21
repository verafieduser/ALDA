package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop{
    private int stop_id;
    private String stop_name;
    private Double stop_lat;
    private Double stop_lon;


    public SL_Stop(int stop_id, String stop_name, double stop_lat, double stop_lon) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
    }

}
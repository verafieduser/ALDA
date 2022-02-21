package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.LinkedList;
import java.util.List;

public class SL_Stop{
    private int stop_id;
    private String stop_name;
    List<SL_Route> connections = new LinkedList<>();
    // private Double stop_lat;
    // private Double stop_lon;


    public SL_Stop(int stop_id, String stop_name/*, double stop_lat, double stop_lon*/) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        // this.stop_lat = stop_lat;
        // this.stop_lon = stop_lon;
    }

    public boolean addConnection(SL_Route connection){
        return connections.add(connection);
    }
    
    public Map<SL_Stop_Time, > findEdgesAtTime(String time){

    }

    public int getStop_id() {
        return this.stop_id;
    }

    public void setStop_id(int stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_name() {
        return this.stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }


    @Override
    public String toString() {
        return "\nSL_STOP: {" +
            "\n\t stop_id='" + getStop_id() + "'" +
            ",\n\t stop_name='" + getStop_name() + "'" +
            "}";
    }


}
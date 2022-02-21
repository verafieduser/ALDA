package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Trips{
    private int route_id;
    /**
     * Unique identifier for trip:
     */
    private int trip_id;
    /**
     * Terminal station name:
     */
    private String trip_headsign; 


    public SL_Trips(int route_id, int trip_id, String trip_headsign) {
        this.route_id = route_id;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
    }


}
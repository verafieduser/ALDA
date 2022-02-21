package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Trip{
    private long route_id;
    /**
     * Unique identifier for trip:
     */
    private long trip_id;
    /**
     * Terminal station name:
     */
    private String trip_headsign; 


    public SL_Trip(long route_id, long trip_id, String trip_headsign) {
        this.route_id = route_id;
        this.trip_id = trip_id;
        this.trip_headsign = trip_headsign;
    }



    public long getRoute_id() {
        return this.route_id;
    }

    public long getTrip_id() {
        return this.trip_id;
    }

    public String getTrip_headsign() {
        return this.trip_headsign;
    }


    @Override
    public String toString() {
        return "{" +
            "\n\t route_id='" + getRoute_id() + "'" +
            ",\n\t trip_id='" + getTrip_id() + "'" +
            ",\n\t trip_headsign='" + getTrip_headsign() + "'" +
            "}\n";
    }

}
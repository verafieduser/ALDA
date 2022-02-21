package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.LinkedList;
import java.util.List;

public class SL_Route{
    private long route_id;
    private short route_short_name;
    private short route_type;
    private List<SL_Trip> trips = new LinkedList<>();


    public SL_Route(long route_id, short route_short_name, short route_type) {
        this.route_id = route_id;
        this.route_short_name = route_short_name;
        this.route_type = route_type;
    }

    public boolean addTrip(SL_Trip trip){
        return trips.add(trip);
    }


    public long getRoute_id() {
        return this.route_id;
    }

    public short getRoute_short_name() {
        return this.route_short_name;
    }

    public short getRoute_type() {
        return this.route_type;
    }



    @Override
    public String toString() {
        return "\nSL_ROUTE: {" +
            "\n\t route_id='" + getRoute_id() + "'" +
            ",\n\t route_short_name='" + getRoute_short_name() + "'" +
            ",\n\t route_type='" + getRoute_type() + "'" +
            "}";
    }


}
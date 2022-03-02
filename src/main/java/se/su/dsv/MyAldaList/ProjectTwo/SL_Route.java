package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.LinkedList;
import java.util.List;

public class SL_Route{
    private long id;
    private short shortName;
    private short type;
    private List<SL_Trip> trips = new LinkedList<>();

    public List<SL_Trip> getTrips() {
        return this.trips;
    }

    public SL_Route(long route_id, short route_short_name, short route_type) {
        this.id = route_id;
        this.shortName = route_short_name;
        this.type = route_type;
    }

    public boolean addTrip(SL_Trip trip){
        return trips.add(trip);
    }


    public long getId() {
        return this.id;
    }

    public short getShortName() {
        return this.shortName;
    }

    public short getType() {
        return this.type;
    }



    @Override
    public String toString() {
        return "\nSL_ROUTE: {" +
            "\n\t route_id='" + getId() + "'" +
            ",\n\t route_short_name='" + getShortName() + "'" +
            ",\n\t route_type='" + getType() + "'" +
            "}";
    }


}
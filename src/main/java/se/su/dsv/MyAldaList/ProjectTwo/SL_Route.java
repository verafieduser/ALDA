package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SL_Route{
    private long id;
    private short shortName;
    private String type;
    private List<SL_Trip> trips = new LinkedList<>();
    private Set<SL_Stop> stops = new HashSet<>();

    public List<SL_Trip> getTrips() {
        return this.trips;
    }

    public SL_Route(long id, short shortName, short type) {
        this.id = id;
        this.shortName = shortName;
        switch (type) {
            case 700:
                this.type = "bus";
                break;
        
            case 401: 
                this.type = "metro";
                break;

            case 900:
                this.type = "tramway";
                break;

            default:
                this.type = "unknown";
                break;
        }
    }

    public boolean addTrip(SL_Trip trip){
        return trips.add(trip);
    }

    public boolean addStop(SL_Stop stop){
        return stops.add(stop);
    }


    public long getId() {
        return this.id;
    }

    public short getShortName() {
        return this.shortName;
    }

    public String getType() {
        return this.type;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SL_Route)) {
            return false;
        }
        SL_Route route = (SL_Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return shortName;
    }



    @Override
    public String toString() {
        return "\nSL_ROUTE: {" +
            "\n\t id='" + getId() + "'" +
            ",\n\t shortName='" + getShortName() + "'" +
            ",\n\t type='" + getType() + "'" +
            "}";
    }


}
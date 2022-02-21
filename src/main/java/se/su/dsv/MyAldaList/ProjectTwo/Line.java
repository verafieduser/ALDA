package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Line{
    private SL_Route route;
    private Set<SL_Stop> stops = new HashSet<>();
    private List<SL_Trip> trips = new LinkedList<>();

    public Line(SL_Route route) {
        this.route = route;
    }

    public boolean contains(SL_Stop stop){
        for(SL_Stop s : stops){
            if(stop.equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean contains(SL_Trip trip){
        for(SL_Trip t : trips){
            if(trip.equals(t)){
                return true;
            }
        }
        return false;
    }

    public boolean addStop(SL_Stop stop){
        return stops.add(stop);
    }

    public boolean addTrip(SL_Trip trip){
        return trips.add(trip);
    }


    public SL_Route getRoute() {
        return this.route;
    }

    public Set<SL_Stop> getStops() {
        return this.stops;
    }

    public List<SL_Trip> getTrips() {
        return this.trips;
    }
    

    @Override
    public String toString() {
        return "LINE: {" +
            "\t route='{" + getRoute() + "}'" +
            ",\n\t stops='{" + getStops() + "}'" +
           // ",\n\t trips='{" + getTrips() + "}'" +
            "}\n";
    }

}
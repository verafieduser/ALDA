package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SL_Route implements Comparable<SL_Route>{
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

    public Set<SL_Stop> intersectingStops(SL_Route otherRoute){
        Set<SL_Stop> intersection = stops;
        intersection.retainAll(otherRoute.getStops());
        return intersection;
    }


    public Set<SL_Route> intersectingRoutes(){
        Set<SL_Route> intersection = new HashSet<>();
        for(SL_Stop stop : stops){
            intersection.addAll(stop.getRoutes());
        }

        return intersection;
    }

    //TODO: currently returns trip in the wrong direction!
    public SL_Trip connectingTrip(SL_Stop from, SL_Stop to, Time earliestTime, boolean timeIsArrivalAtGoal){
        SL_Trip currentBestTrip = null;
        Time currentBestStopTime = timeIsArrivalAtGoal ? new Time("24:00:00") : new Time("00:00:00");
        for(SL_Trip trip : trips){

            List<SL_Stop> tripStops = trip.getStops();

            if(tripStops.contains(from) && tripStops.contains(to) && trip.traversable(from, to)){
                SL_Stop_Time stopTime = trip.getStopTime(from);
                Time departureTime = stopTime.getDepartureTime();
                if  (arrivalByTime(timeIsArrivalAtGoal, earliestTime, departureTime, currentBestStopTime)) {
                    currentBestStopTime = departureTime;
                    currentBestTrip = trip;
                } 
            }
        }
        return currentBestTrip;
    }

    private boolean arrivalByTime(boolean timeIsArrivalAtGoal, Time earliestTime, Time departureTime, Time currentBestStopTime){
        if(timeIsArrivalAtGoal){
            return departureTime.compareTo(earliestTime) >= 0
            && departureTime.compareTo(currentBestStopTime) < 0;
        }
        return departureTime.compareTo(earliestTime) <= 0
        && departureTime.compareTo(currentBestStopTime) > 0;
    }

    public boolean addTrip(SL_Trip trip){
        return trips.add(trip);
    }

    public boolean addStop(SL_Stop stop){
        return stops.add(stop);
    }

    public Set<SL_Stop> getStops() {
        return stops;
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
    public int compareTo(SL_Route o) {
        int value = 0;
        if(type.equals("metro") && (o.type.equals("bus") || o.type.equals("tramway"))){
            value = 1;
        } else if((type.equals("bus") || type.equals("tramway")) && o.type.equals("metro")){
            value = -1;
        } 
        return value;
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
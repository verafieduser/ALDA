/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SLRoute implements Comparable<SLRoute>{
    private long id;
    private short shortName;
    private String type;
    private List<SLTrip> trips = new LinkedList<>();
    private Set<SLStop> stops = new HashSet<>();

    public List<SLTrip> getTrips() {
        return this.trips;
    }

    public SLRoute(long id, short shortName, short type) {
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

    public Set<SLStop> intersectingStops(SLRoute otherRoute){
        Set<SLStop> intersection = stops;
        intersection.retainAll(otherRoute.getStops());
        return intersection;
    }


    public Set<SLRoute> intersectingRoutes(){
        Set<SLRoute> intersection = new HashSet<>();
        for(SLStop stop : stops){
            intersection.addAll(stop.getRoutes());
        }

        return intersection;
    }

    //TODO: currently returns trip in the wrong direction!
    public SLTrip connectingTrip(SLStop from, SLStop to, Time earliestTime, boolean timeIsArrivalAtGoal){
        SLTrip currentBestTrip = null;
        Time currentBestStopTime = timeIsArrivalAtGoal ? new Time("24:00:00") : new Time("00:00:00");
        for(SLTrip trip : trips){

            List<SLStop> tripStops = trip.getStops();

            if(tripStops.contains(from) && tripStops.contains(to) && trip.traversable(from, to)){
                SLStopTime stopTime = trip.getStopTime(from);
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

    public boolean addTrip(SLTrip trip){
        return trips.add(trip);
    }

    public boolean addStop(SLStop stop){
        return stops.add(stop);
    }

    public Set<SLStop> getStops() {
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
        if (!(o instanceof SLRoute)) {
            return false;
        }
        SLRoute route = (SLRoute) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return shortName;
    }


    @Override
    public int compareTo(SLRoute o) {
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
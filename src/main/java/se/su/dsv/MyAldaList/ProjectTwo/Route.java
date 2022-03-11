/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The routes are to trips, what stations are to stoptimes. That is, the
 * "generic" route, that all specific trips happen under.
 * While a trip would be "metro towards Alvik", the route would be "green line".
 */
public class Route implements Comparable<Route> {
    /**
     * Unique identifier
     */
    private int id;
    /**
     * Name of this line, i.e., what the trains say on them. For example, 21 for
     * Lidingötåget.
     */
    private short shortName;
    /**
     * The type of transport that occurs on this line. Metro, bus or tramway.
     */
    private String type;
    /**
     * The individual trips that occur on this route throughout the day
     */
    private List<Trip> trips = new LinkedList<>();
    /**
     * The stops that exist on this stop.
     */
    private Set<Station> stops = new HashSet<>();

    /**
     * Constructs a route
     * 
     * @param id        unique identified for this route. Used to link with other
     *                  data.
     * @param shortName the name of the route, in a number.
     * @param type      the type of transportation that occurs on this route -
     *                  metro, bus or tramway.
     */
    public Route(int id, short shortName, short type) {
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

    /**
     * @return the individual trips that occur on this route throughout the day
     */
    public List<Trip> getTrips() {
        return this.trips;
    }

    /**
     * Method for finding what stops two routes intersect
     * 
     * @param otherRoute the route you want to find intersection with this one for
     * @return the list of stops in which the two routes intersect
     */
    public Set<Station> intersectingStops(Route otherRoute) {
        Set<Station> intersection = stops;
        intersection.retainAll(otherRoute.getStops());
        return intersection;
    }

    /**
     * @return all routes that intersect with this one along all its stops
     */
    public Set<Route> intersectingRoutes() {
        Set<Route> intersection = new HashSet<>();
        for (Station stop : stops) {
            intersection.addAll(stop.getRoutes());
        }

        return intersection;
    }

    /**
     * The trip that connects two station at specified time that both are on this
     * route. //TODO: Current timing is incorrect
     * 
     * @param from                the station that you want the trip to include that
     *                            you start at.
     * @param to                  the station you want the trip to include that you
     *                            end at. Must be on the same route as param from.
     * @param earliestTime        the time you want the trip to be determined by.
     * @param timeIsArrivalAtGoal if you want the time to be when you arrive, or
     *                            when you depart.
     * @return a trip connecting "from" with "to"
     */
    public Trip connectingTrip(Station from, Station to, Time earliestTime, boolean timeIsArrivalAtGoal) {
        Trip currentBestTrip = null;
        Time currentBestStopTime = timeIsArrivalAtGoal ? new Time("24:00:00") : new Time("00:00:00");
        for (Trip trip : trips) {

            List<Station> tripStops = trip.getStops();

            if (tripStops.contains(from) && tripStops.contains(to) && trip.traversable(from, to)) {
                StopTime stopTime = trip.getStopTime(from);
                Time departureTime = stopTime.getDepartureTime();
                if (arrivalByTime(timeIsArrivalAtGoal, earliestTime, departureTime, currentBestStopTime)) {
                    currentBestStopTime = departureTime;
                    currentBestTrip = trip;
                }
            }
        }
        return currentBestTrip;
    }

    /**
     * helper method for connectingTrip() that decides whether time is decided by
     * when you arrive at goal, or when you depart from start.
     * 
     * @param timeIsArrivalAtGoal value that decides how the result is to be
     *                            calculated.
     * @param earliestTime
     * @param departureTime
     * @param currentBestStopTime
     * @return a boolean value, constructed differently depending on if
     *         timeIsArrivalAtGoal is true or false.
     */
    private boolean arrivalByTime(boolean timeIsArrivalAtGoal, Time earliestTime, Time departureTime,
            Time currentBestStopTime) {
        if (timeIsArrivalAtGoal) {
            return departureTime.compareTo(earliestTime) >= 0
                    && departureTime.compareTo(currentBestStopTime) < 0;
        }
        return departureTime.compareTo(earliestTime) <= 0
                && departureTime.compareTo(currentBestStopTime) > 0;
    }

    /**
     * adds a trip to this route, that is on this route.
     * 
     * @param trip a trip a part of this route.
     * @return true if it was added successfully.
     */
    public boolean addTrip(Trip trip) {
        return trips.add(trip);
    }

    /**
     * adds a stop to this route, that is on this route.
     * 
     * @param stop a stop that is a part of this route.
     * @return true if add was successful.
     */
    public boolean addStop(Station stop) {
        return stops.add(stop);
    }

    /**
     * @return stops that are on this route.
     */
    public Set<Station> getStops() {
        return stops;
    }

    /**
     * @return unique identifier for this route.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the name of this route
     */
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
        if (!(o instanceof Route)) {
            return false;
        }
        Route route = (Route) o;
        return id == route.id;
    }

    @Override
    public int hashCode() {
        return shortName;
    }

    @Override
    public int compareTo(Route o) {
        int value = 0;
        if (type.equals("metro") && (o.type.equals("bus") || o.type.equals("tramway"))) {
            value = 1;
        } else if ((type.equals("bus") || type.equals("tramway")) && o.type.equals("metro")) {
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
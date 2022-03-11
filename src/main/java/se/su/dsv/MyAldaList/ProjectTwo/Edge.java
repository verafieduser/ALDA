/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

/**
 * Edge between two StopTimes. Used to traverse the graph.
 */
public class Edge {
    private final StopTime from;
    private final StopTime to;
    /**
     * the cost to traverse from "from", to "to", in time.
     */
    private final Time cost;
    /**
     * the type of travel that occurs on this edge, i.e. metro, bus or tramway.
     */
    private final String type;

    /**
     * 
     * @param from the stop at which this edge originates
     * @param to   the stop at which this edge terminates
     * @param type the type of travel that occurs on this edge, i.e., metro, bus, or
     *             tramway
     */
    public Edge(StopTime from, StopTime to, String type) {
        this.from = from;
        this.to = to;
        if (from == null || to == null) {
            String message = from == null ? " from!" : " to!";
            throw new IllegalArgumentException("An edge was created with null" + message);
        }
        cost = Time.timeDifference(from, to);
        this.type = type;
    }

    /**
     * @return the stop at which this edge originates
     */
    public StopTime getFrom() {
        return from;
    }

    /**
     * @return the stop at which this edge terminates
     */
    public StopTime getTo() {
        return to;
    }

    /**
     * the trip this edge is a part of
     */
    public Trip getTrip() {
        return from.getTrip();
    }

    /**
     * @return the route this edge is on
     */
    public Route getRoute() {
        return from.getTrip().getRoute();
    }

    /**
     * @return the time it takes to go from the origination of this edge, to its
     *         termination.
     */
    public Time getCost() {
        return cost;
    }

    /**
     * @return the type of travel that occurs on this edge, i.e., metro, bus, or
     *         tramway
     */
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) o;
        return from.equals(edge.from) && to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                " from='" + getFrom().getStation().getName() + "'" +
                ", to='" + getTo().getStation().getName() + "'" +
                ", on line=' " + getRoute().getShortName() + " towards " + getTrip().getHeadsign() + "'" +
                ", \ntotal travel time='" + getCost() + "'" +

                "}";
    }

}
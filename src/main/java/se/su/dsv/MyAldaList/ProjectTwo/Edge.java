package se.su.dsv.MyAldaList.ProjectTwo;

public class Edge {
    private final SLStopTime from;
    private final SLStopTime to;
    private final Time cost;
    private final String type;

    public Edge(SLStopTime from, SLStopTime to, String type) {
        this.from = from;
        this.to = to;
        if (from == null || to == null) {
            String message = from == null ? " from!" : " to!";
            throw new IllegalArgumentException("An edge was created with null" + message);
        }
        cost = Time.timeDifference(from, to); // calculateTravelCost(from, to);
        this.type = type;
    }

    public SLStopTime getFrom() {
        return from;
    }

    public SLStopTime getTo() {
        return to;
    }

    public SLTrip getTrip() {
        return from.getTrip();
    }

    public SLRoute getRoute() {
        return from.getTrip().getRoute();
    }

    public Time getCost() {
        return cost;
    }

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
                " from='" + getFrom().getStop().getName() + "'" +
                ", to='" + getTo().getStop().getName() + "'" +
                ", on line=' " + getRoute().getShortName() + " towards " + getTrip().getHeadsign() + "'" +
                ", \ntotal travel time='" + getCost() + "'" +

                "}";
    }

}
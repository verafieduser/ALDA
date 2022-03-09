package se.su.dsv.MyAldaList.ProjectTwo;

public class Edge{
    private final SL_Stop_Time from;
    private final SL_Stop_Time to;
    private final Time cost;
    private final String type;

    public Edge(SL_Stop_Time from, SL_Stop_Time to, String type){
        this.from = from;
        this.to = to;
        cost = Time.timeDifference(from, to); //calculateTravelCost(from, to);
        this.type = type;
    }

    public SL_Stop_Time getFrom() {
        return from;
    }

    public SL_Stop_Time getTo() {
        return to;
    }

    public SL_Trip getTrip(){
        return from.getTrip();
    }

    public SL_Route getRoute(){
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
        return from.hashCode()+to.hashCode();
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
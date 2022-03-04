package se.su.dsv.MyAldaList.ProjectTwo;

public class Edge{
    private SL_Stop_Time from;
    private SL_Stop_Time to;
    private Time departureTime;
    private Time arrivalTime;
    private Time travelTime;

    public Edge(SL_Stop_Time from, SL_Stop_Time to){
        this.from = from;
        this.to = to;
        departureTime = from.getDepartureTime();
        arrivalTime = to.getDepartureTime();
        travelTime = Time.timeDifference(from, to); //calculateTravelCost(from, to);
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

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Time getTravelTime() {
        return travelTime;
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
            ", \ndeparture='" + getDepartureTime() + "'" +
            ", arrival='" + getArrivalTime() + "'" +
            ", total travel time='" + getTravelTime() + "'" +

            "}";
    }


}
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

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Time getTravelTime() {
        return travelTime;
    }

}
package se.su.dsv.MyAldaList.ProjectTwo;

public class Edge{
    private SL_Stop_Time from;
    private SL_Stop_Time to;
    private short[] travelTime;

    public Edge(SL_Stop_Time from, SL_Stop_Time to){
        this.from = from;
        this.to = to;
        travelTime = calculateCost(from, to);
    }

    private short[] calculateCost(SL_Stop_Time from, SL_Stop_Time to){
        short[] fromArrivalTime = from.getDepartureTime();
        short[] toArrivalTime = to.getDepartureTime();

        short[] travelTime = new short[toArrivalTime.length];

        

        return travelTime;
    }
}
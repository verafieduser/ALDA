package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SL_Stop{
    private int stop_id;
    private String stop_name;
    List<SL_Trip> connections = new LinkedList<>();
    // private Double stop_lat;
    // private Double stop_lon;


    public SL_Stop(int stop_id, String stop_name/*, double stop_lat, double stop_lon*/) {
        this.stop_id = stop_id;
        this.stop_name = stop_name;
        // this.stop_lat = stop_lat;
        // this.stop_lon = stop_lon;
    }

    public boolean addConnection(SL_Trip connection){
        return connections.add(connection);
    }
    
    public Map<SL_Stop_Time, Integer> findEdgesAtTime(short[] departureTime){
        HashMap<SL_Stop_Time, short[]> stopsAndCosts = new HashMap<>();
        for(SL_Trip connection : connections){
            SL_Stop_Time nextStop = connection.getNextStops(this, departureTime); 
            short[] travelTime = calculateTravelCost(departureTime, nextStop);
        }
        return null;
    }

    private short[] calculateTravelCost(short[] departureTime, SL_Stop_Time nextStop){
        short[] arrivalTime = nextStop.getDepartureTime();
        short[] travelCost = new short[arrivalTime.length];
        for(int i = 0; i<arrivalTime.length;i++){
            travelCost[i] = (short) (arrivalTime[i] - departureTime[i]);
        }

        travelCost = checkIfTravelCostNegative(travelCost);
        return travelCost;
    }

    private short[] checkIfTravelCostNegative(short[] travelCost){
        for(int i = travelCost.length; i>=0; i--){
            if(Short.compare(travelCost[i], (short)0) < 0){
                if(i==travelCost.length){ //if it is the highest value in time, you cannot subtract from higher unit - therefore... 
                    for(int j=0; i<travelCost.length; j++){
                        if(j==travelCost.length-1){
                            travelCost[j]+=60;   
                        } else {
                            travelCost[j]=0;
                        }
                    }
                    break;
                } else if(travelCost[i-1]>(short)0) { //if the value in the higher unit is larger than 0, you can subtract from it
                    travelCost[i-1] -= 1;
                    travelCost[i] += 60;
                } else {
                    //TODO: if value in higher unit is not larger than 0, but current is still negative? can this happen?
                }
            } 
        }
        return travelCost;
    }

    public int getStop_id() {
        return this.stop_id;
    }

    public void setStop_id(int stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_name() {
        return this.stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }


    @Override
    public String toString() {
        return "\nSL_STOP: {" +
            "\n\t stop_id='" + getStop_id() + "'" +
            ",\n\t stop_name='" + getStop_name() + "'" +
            "}";
    }


}
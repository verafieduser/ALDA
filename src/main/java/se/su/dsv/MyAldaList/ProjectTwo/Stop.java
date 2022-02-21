package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stop{
    SL_Stop stop;

    Map<Line, SL_Stop_Time[]> departures = new HashMap<>();
    List<Line> lines = new ArrayList<>();
    
    public Stop(SL_Stop stop){
        this.stop = stop;
    }

    /**
     * 
     * @param line the Lines that use this station
     * @param stopTimes all the departures from each line from this station.
     * @return returns false if duplicate key.
     */
    public boolean addDeparture(Line line, SL_Stop_Time... stopTimes){
        return departures.put(line, stopTimes)!=null;
    }

    public boolean addStopTime(SL_Stop_Time... stopTimes){
        for(SL_Stop_Time stopTime : stopTimes){
            for(Line line : lines){
                
            }
        }

        return true;
    }

}
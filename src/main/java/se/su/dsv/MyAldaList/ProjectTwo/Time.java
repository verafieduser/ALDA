package se.su.dsv.MyAldaList.ProjectTwo;

import java.time.LocalTime;

public class Time implements Comparable<Time> {

    short[] time;



    public Time() {
        LocalTime currentTime = java.time.LocalTime.now();
        time = new short[3];
        time[0] = (short)currentTime.getHour();
        time[1] = (short)currentTime.getMinute();
        time[2] = (short)currentTime.getSecond();
    }

    public Time(String timeString){
        String[] time = timeString.split(":");
        this.time = new short[time.length];
        for(int i = 0; i < time.length; i++){
            this.time[i] = Short.parseShort(time[i]);
        }
    }

    public Time(short[] time){
        this.time = time;
    }

    public int getAmountOfTimeUnits(){
        return time.length;
    }

    public short[] getTime(){
        return time;
    }

    public static Time timeDifference(SL_Stop_Time from, SL_Stop_Time to){
        return timeDifference(from.getDepartureTime(), to.getDepartureTime(), false);
    }


    public static Time timeDifference(SL_Stop_Time from, SL_Stop_Time to, boolean withNegative){
        return timeDifference(from.getDepartureTime(), to.getDepartureTime(), withNegative);
    }

    public static Time timeDifference(Time from, Time to){
        return timeDifference(from, to, false);
    }

    public static Time timeDifference(Time from, Time to, boolean withNegative){
        short[] fromArrivalTime = from.getTime();
        short[] toArrivalTime = to.getTime();
     
        return timeDifference(fromArrivalTime, toArrivalTime, withNegative);
    }

    private static Time timeDifference(short[] from, short[] to, boolean withNegative){

        short[] travelCost = new short[to.length];
        for(int i = 0; i<to.length;i++){
            travelCost[i] = (short) (to[i] - from[i]);
        }      

        if(withNegative){
            return new Time(travelCost);
        } 
        return checkIfTravelCostNegative(travelCost);
    } 

    public static Time plus(Time t1, Time t2) {
        short[] newTime = new short[3];
        for(int i = 0; i < newTime.length; i++){
            newTime[i] = (short)(t1.getTime()[i] + t2.getTime()[i]);
            if(newTime[i]>60){
                newTime[i-1]+=1;
                newTime[i]-=60;
            }
        }
        return new Time(newTime);
    }

    public static Time minus(Time t1, Time t2) {
        short[] newTime = new short[3];
        for(int i = 0; i < newTime.length; i++){
            newTime[i] = (short)(t1.getTime()[i] - t2.getTime()[i]);
        }
        return checkIfTravelCostNegative(newTime);
    }


    private static Time checkIfTravelCostNegative(short[] travelCost){
        if(travelCost[0] < (short)0){
            travelCost[0] += 24;          
        }

        //TODO: implement general solution:
        if(travelCost[1] < (short)0){
            if(travelCost[0] > (short)0){
                travelCost[0] -= 1;
                travelCost[1] += 60;
            } else {
                System.out.println("this shouldnt happen");
            }
        }

        return new Time(travelCost);
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Time)) {
            return false;
        }
        Time time = (Time) o;
        return this.time==time.time;
    }

    @Override
    public int hashCode() {
        return 100*time[0]+time[1];
    }


    @Override
    public int compareTo(Time o) {
        for(int i = 0; i < time.length; i++){
            if(time[i] > o.time[i]){
                return 1;
            } else if (time[i] < o.time[i]){
                return -1;
            }
        }
        return 0;
    }


    @Override
    public String toString() {
        String[] timeAsString = new String[time.length];
        for(int i = 0; i < time.length; i++){
            timeAsString[i] = String.valueOf(time[i]);
            if(timeAsString[i].length()<2){
                timeAsString[i] = "0" + timeAsString[i];
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i = 0; i < timeAsString.length; i++){
            sb.append(timeAsString[i] + ":");
        }
        return (sb.substring(0, sb.length()-1)+"}").toString();
    }

    
}
/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.time.LocalTime;
/**
 * Time class implementing time in the format of hh:mm:ss. Currently "loops" so that
 * 23:59:00 + 00:05:00 = 00:04:00. Started work on a more generic solution
 * that could handle more than 3 units of time, but decided to not continue
 * that thread since the data we are using only uses 3 units of time, in
 * which the seconds are already always 00. 
 */
public class Time implements Comparable<Time> {

    /**
     * The core object that the class functions as a wrapper for - 
     * An array 3 in length, where 0=hours, 1=minutes, and 2=seconds. 
     */
    private short[] time;

    /**
     * Creates a new Time object based on the current time.
     * Uses java.time.LocalTime to get the current time.
     */
    public Time() {
        LocalTime currentTime = java.time.LocalTime.now();
        time = new short[3];
        time[0] = (short)currentTime.getHour();
        time[1] = (short)currentTime.getMinute();
        time[2] = (short)currentTime.getSecond();
    }

    /**
     * Constructor that creates a time object based on the string provided.
     * Important to follow specifications since there are not many controls.
     * @param time A string in the format: "hh:mm:ss", 
     * String must follow rules for each time unit. Hours must be
     * 00-23, and minutes and seconds must be 00-59. You cannot add more 
     * time units!
     */
    public Time(String time){
        String[] timeString = time.split(":");
        if(timeString.length < 3){
            throw new IllegalArgumentException("You need three units when selecting time");
        }
        this.time = new short[timeString.length];
        for(int i = 0; i < timeString.length; i++){
            this.time[i] = Short.parseShort(timeString[i]);
        }
    }

    /**
     * A constructor that creates a time object based on the parameter.
     * Has no controls, therefore important that one follows the specifications!
     * @param time a 3 length array where i0=hours, i1=minutes, and i2=seconds.
     * All shorts in the array follow rules for each time unit. Hours must be
     * 00-23, and minutes and seconds must be 00-59.
     */
    public Time(short[] time){
        this.time = time;
    }

    /**
     * @return an 3 length array representation of the time object, where
     * i0=hours, i1=minutes, and i2=seconds. 
     */
    public short[] getTime(){
        return time;
    }

    /**
     * Method that calculates the time difference between two times. If you enter the times
     * 15:00:00, and 15:05:00 - it returns 00:05:00. If you want to be able to get a negative result,
     * add boolean flag "true" as a parameter.
     * @param from a stop at a specific time at a station, with the method .getDepartureTime which
     * returns a time object. 
     * @param to a stop at a specific time at a station, with the method .getDepartureTime which
     * returns a time object. 
     * @return a time object with the difference between the time objects in the parameters. 
     */
    public static Time timeDifference(StopTime from, StopTime to){
        return timeDifference(from.getTime(), to.getTime(), false);
    }

        /**
     * Method that calculates the time difference between two times. If you enter the times
     * 15:00:00, and 15:05:00 - it returns 00:05:00. Also uses flag withNegative, which determines
     * if a negative result is allowed. 
     * @param from a stop at a specific time at a station, with the method .getDepartureTime which
     * returns a time object. 
     * @param to a stop at a specific time at a station, with the method .getDepartureTime which
     * returns a time object. 
     * @param withNegative Decides if you want to be able to get a negative result. If true,
     * the result can be negative if parameter t1 is later than parameter t2. 
     * @return a time object with the difference between the time objects in the parameters. 
     */
    public static Time timeDifference(StopTime from, StopTime to, boolean withNegative){
        return timeDifference(from.getTime(), to.getTime(), withNegative);
    }

    /**
     * Method that calculates the time difference between two times. If you enter the times
     * 15:00:00, and 15:05:00 - it returns 00:05:00. If you want to be able to get a negative result,
     * add boolean flag "true" as a parameter.
     * @param t1 time in the format of hh:mm:ss 
     * @param t2 time in the format of hh:mm:ss
     * @return a time object with the difference between the time objects in the parameters. 
     */
    public static Time timeDifference(Time t1, Time t2){
        return timeDifference(t1, t2, false);
    }

    /**
     * Method that calculates the time difference between two times. If you enter the times
     * 15:00:00, and 15:05:00 - it returns 00:05:00. Also uses flag withNegative, which determines
     * if a negative result is allowed. 
     * @param t1 time in the format of hh:mm:ss 
     * @param t2 time in the format of hh:mm:ss
     * @param withNegative Decides if you want to be able to get a negative result. If true,
     * the result can be negative if parameter t1 is later than parameter t2. 
     * @return a time object with the difference between the time objects in the parameters. 
     */
    public static Time timeDifference(Time t1, Time t2, boolean withNegative){
        short[] fromArrivalTime = t1.getTime();
        short[] toArrivalTime = t2.getTime();
     
        return timeDifference(fromArrivalTime, toArrivalTime, withNegative);
    }

    /**
     * Helper method for calculating the difference between two time objects. 
     * @param t1 time represented in an array of shorts, where index 0 is hour, 1 is minute
     * and 2 is seconds.
     * @param t2 time represented in an array of shorts, where index 0 is hour, 1 is minute
     * and 2 is seconds.
     * @param withNegative determines if a negative result is possible.
     * @return a Time-object based on the resulting difference between the short arrays.
     * Can return a negative result if withNegative is true and t1 is later than t2. 
     */
    private static Time timeDifference(short[] t1, short[] t2, boolean withNegative){
        short[] travelCost = new short[t2.length];
        for(int i = 0; i<t2.length;i++){
            travelCost[i] = (short) (t2[i] - t1[i]);
        }      

        if(withNegative){
            return new Time(travelCost);
        } 
        return checkIfTravelCostNegative(travelCost);
    } 

    /**
     * Method for adding together two time objects and get a time later than both. 
     * For example, if parameters are 15:05:00 and 00:05:00, the result is 15:10:00.
     * 
     * If the resulting seconds is above 60, +1 is added to minutes, and 60 is subtracted from
     * seconds. Same applies for minutes and hours. If hours reach 24, it all wraps around to 00.
     * @param t1 One time object in the format of hh:mm:ss.
     * @param t2 Another time object in the format of hh:mm:ss
     * @return A time object based on the result of the addition of the two time objects in the parameters
     */
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

    /**
     * Subtracts t1 with t2. Returns result. If time reaches beneath 00,
     * it borrows from higher units - if it cannot, the whole time object 
     * wraps around. 
     * @param t1 A time object in the format hh:mm:ss, 
     * @param t2 A time object in the format hh:mm:ss. 
     * @return a time object of the resulting subtraction of t1 with t2. 
     * Can not return a negative result no matter the order of t1 and t2. 
     */
    public static Time minus(Time t1, Time t2) {
        short[] newTime = new short[3];
        for(int i = 0; i < newTime.length; i++){
            newTime[i] = (short)(t1.getTime()[i] - t2.getTime()[i]);
        }
        return checkIfTravelCostNegative(newTime);
    }

    /**
     * Helper method for addition and subtraction methods, to 
     * guarantee there are no negative time units. Borrows from higher units
     * to guarantee correct result. If hours reach 24, it wraps around.
     * @param time time represented in an array 3 in length, where i0=hours,
     * i1=minutes, i2:seconds.  
     * @return a new time object with only positive time units.
     */
    private static Time checkIfTravelCostNegative(short[] time){
        if(time[0] < (short)0){
            time[0] += 24;          
        }

        if(time[1] < (short)0){
            if(time[0] > (short)0){
                time[0] -= 1;
                time[1] += 60;
            } else {
                System.out.println("this shouldnt happen");
            }
        }
        return new Time(time);
    }

    /**
     * Bases equality if hours, minutes and seconds are equal. 
     */
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

    /**
     * Hashcode calculated by multiplying hours with 100, and then 
     * adding minutes onto that. The resulting hashcode is therefore
     * always 3-4 digits.
     */
    @Override
    public int hashCode() {
        return 100*time[0]+time[1];
    }

    /**
     * Compares using each time unit, where hours weigh the heaviest
     * then minutes, and lastly, seconds. 
     */
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

    /**
     * Returns a string representation of the time object in
     * this format: "{xx:xx:xx}"
     */
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
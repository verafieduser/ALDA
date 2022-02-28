package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Stop_Time implements Comparable<SL_Stop_Time>{
    private SL_Trip trip;
    private short[] departureTime;
    private SL_Stop stop;
    private short stop_sequence;


    public SL_Stop_Time(SL_Trip trip, String departure_time, SL_Stop stop, short stop_sequence) {
        this.trip = trip;
        this.stop = stop;
        this.stop_sequence = stop_sequence;
        String[] time = departure_time.split(":");
        departureTime = new short[time.length];
        for(int i = 0; i < time.length; i++){
            departureTime[i] = Short.parseShort(time[i]);
        }
    }


    public SL_Trip getTrip() {
        return this.trip;
    }

    public short[] getDepartureTime() {
        return departureTime;
    }


    public SL_Stop getStop() {
        return this.stop;
    }

    public short getStop_sequence() {
        return this.stop_sequence;
    }

    @Override
    public int compareTo(SL_Stop_Time o) {
        Short own = stop_sequence;
        Short other = o.getStop_sequence();
        return own.compareTo(other);
    }

    @Override
    public String toString() {
        return "\nSL_STOP_TIME: {" +
            "\n\t trip='" + getTrip() + "'" +
            ",\n\t departure_time='" + getDepartureTime() + "'" +
            ",\n\t stop='" + getStop() + "'" +
            ",\n\t stop_sequence='" + getStop_sequence() + "'" +
            "}";
    }





}
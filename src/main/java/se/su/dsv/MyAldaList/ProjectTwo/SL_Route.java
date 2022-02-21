package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Route{
    private long route_id;
    private short route_short_name;
    private short route_type;


    public SL_Route(long route_id, short route_short_name, short route_type) {
        this.route_id = route_id;
        this.route_short_name = route_short_name;
        this.route_type = route_type;
    }


    public long getRoute_id() {
        return this.route_id;
    }

    public short getRoute_short_name() {
        return this.route_short_name;
    }

    public short getRoute_type() {
        return this.route_type;
    }



    @Override
    public String toString() {
        return "{" +
            " route_id='" + getRoute_id() + "'" +
            ",\n route_short_name='" + getRoute_short_name() + "'" +
            ",\n route_type='" + getRoute_type() + "'" +
            "}\n";
    }


}
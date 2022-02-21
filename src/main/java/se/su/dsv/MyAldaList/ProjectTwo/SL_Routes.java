package se.su.dsv.MyAldaList.ProjectTwo;

public class SL_Routes{
    private int route_id;
    private short route_short_name;
    private String route_long_name = "";
    private short route_type;


    public SL_Routes(int route_id, short route_short_name, String route_long_name, short route_type) {
        this.route_id = route_id;
        this.route_short_name = route_short_name;
        this.route_long_name = route_long_name;
        this.route_type = route_type;
    }


}
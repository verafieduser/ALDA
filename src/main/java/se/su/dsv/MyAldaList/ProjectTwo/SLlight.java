package se.su.dsv.MyAldaList.ProjectTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class SLlight {
    LinkedList<Line> lines = new LinkedList<>();
    LinkedList<SL_Stop> stops = new LinkedList<>();
    LinkedList<SL_Stop_Time> stopTimes = new LinkedList<>();
    LinkedList<SL_Route> routes = new LinkedList<>();
    LinkedList<SL_Trip> trips = new LinkedList<>();

    public static void main(String[] args) {
        SLlight sl = new SLlight();
        sl.importCSV("sl_stops");
        sl.importCSV("sl_stop_times");
        sl.importCSV("sl_routes");
        sl.importCSV("sl_trips");
        sl.dataIntoGraph();
    }


    /**
     * Imports one of for possible CSV files (sl_stops, sl_stop_times, sl_routes, or sl_trips.)
     * @param fileName is the name of the file, which needs to be in src/test/resources/ (the path is added inside the method). 
     * The method can only handle .txt files therefore it is added inside the method. That is - when calling this method write
     * "fileName", and not "fileName.txt" or "x/y/fileName.txt".
     */
    public void importCSV(String fileName){
        try (
            FileReader fileReader = new FileReader("src\\test\\resources\\sl_gtfs_data\\" + fileName + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                String[] lines = line.split(",");

                switch (fileName) {
                    case "sl_stop_times":
                        addStopTime(lines);
                        break;
                    
                    case "sl_trips":
                        addTrip(lines);
                        break;
                    case "sl_stops":
                        addStop(lines);
                        break;
                    case "sl_routes":
                        addRoute(lines);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }  catch (Exception e) {
            System.out.println(e);
        }
    }

    public void dataIntoGraph(){
        //en lista på alla linjer, varje linje har stops.
        SL_Route currentRoute = null;
        Line currentLine = null;

        //adds all lines:
        for(SL_Trip trip : trips){
            if(currentRoute == null || currentRoute.getRoute_id() != trip.getRoute_id()){
                for(SL_Route route : routes){
                    if(route.getRoute_id()==trip.getRoute_id()){
                        currentRoute = route;
                        currentLine = new Line(currentRoute);
                        lines.add(currentLine);
                    }
                }
            }
            //do stuff...
            currentLine.addTrip(trip);
        }

        System.out.println("Added all trips to each line!");

        for(Line line : lines){
            for(SL_Trip trip : line.getTrips()){
                
                for(SL_Stop_Time stopTime : stopTimes){
                    if(stopTime.getTrip_id()==trip.getTrip_id()){

                        for(SL_Stop stop : stops){
                            if(stop.getStop_id()==stopTime.getStop_id()){
                                line.addStop(stop);
                            }
                        }
                    }    
                }
            }
        }
        System.out.println("Added all stops to each line");

        System.out.println(lines);
    }

    private void addStop(String[] lines) {
        int stop_id = Integer.parseInt(lines[0]);
        String stop_name = lines[1];
        // double stop_lat = Double.parseDouble(lines[2]);
        // double stop_lon = Double.parseDouble(lines[3]);
        stops.add(new SL_Stop(stop_id, stop_name/*, stop_lat, stop_lon*/));
    }

    private void addRoute(String[] lines) {
        long route_id = Long.parseLong(lines[0]);
        short route_short_name = Short.parseShort(lines[2]); 
        //String route_long_name = lines[3];
        short route_type = Short.parseShort(lines[3]);
        routes.add(new SL_Route(route_id, route_short_name, route_type));
    }

    private void addTrip(String[] lines) {
        Long route_id = Long.parseLong(lines[0]);
        Long trip_id = Long.parseLong(lines[2]);
        String trip_headsign = lines[3];
        trips.add(new SL_Trip(route_id, trip_id, trip_headsign));
    }

    private void addStopTime(String[] lines) {
        Long trip_id = Long.parseLong(lines[0]);
        String departure_time = lines[2];
        int stop_id = Integer.parseInt(lines[3]);
        short stop_sequence = Short.parseShort(lines[4]);
        stopTimes.add(new SL_Stop_Time(trip_id, departure_time, stop_id, stop_sequence));
    }
}
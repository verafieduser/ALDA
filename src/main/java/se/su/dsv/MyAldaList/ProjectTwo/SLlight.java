package se.su.dsv.MyAldaList.ProjectTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SLlight {
    List<Line> lines = new LinkedList<>();
    List<SL_Stop> stops = new ArrayList<>();
    List<SL_Stop_Time> stopTimes = new ArrayList<>();
    List<SL_Route> routes = new LinkedList<>();
    List<SL_Trip> trips = new LinkedList<>();

    public static void main(String[] args) {
        SLlight sl = new SLlight();
        sl.initialize();

    }

    public void initialize() {
            long startTimeOne = System.currentTimeMillis();
        importCSV("sl_routes");
            long endTimeOne = System.currentTimeMillis();
            long timeOne = endTimeOne - startTimeOne;
            System.out.println("Import of Routes took: " + timeOne);

            long startTimeTwo = System.currentTimeMillis();
        importCSV("sl_stops");
            long endTimeTwo = System.currentTimeMillis();
            long timeTwo = endTimeTwo - startTimeTwo;
            System.out.println("Import of Stops took: " + timeTwo);

            long startTimeThree = System.currentTimeMillis();
        importCSV("sl_trips");
            long endTimeThree = System.currentTimeMillis();
            long timeThree = endTimeThree - startTimeThree;
            System.out.println("Import of Trips took: " + timeThree);

            long startTimeFour = System.currentTimeMillis();
        importCSV("sl_stop_times");
            long endTimeFour = System.currentTimeMillis();
            long timeFour = endTimeFour - startTimeFour;
            System.out.println("Import of Stop Times took: " + timeFour);

            long startTimeFive = System.currentTimeMillis();
        addTripsToRoutes();
            long endTimeFive = System.currentTimeMillis();
            long timeFive = endTimeFive - startTimeFive;
            System.out.println("Linking of Trips to Routes took: " + timeFive);

            long startTimeSix = System.currentTimeMillis();
        addRoutesToStops();
            long endTimeSix = System.currentTimeMillis();
            long timeSix = endTimeSix - startTimeSix;
            System.out.println("Linking of Routes to Stops took: " + timeSix);

            System.out.println("Total Import took: " + (timeOne + timeTwo + timeThree + timeFour + timeFive + timeSix));
    }

    public void graphify() {
        for (SL_Trip trip : trips) {
            for (int i = 0; i < trip.getStops().size(); i++) {

            }
        }
        // SL_Stop is node class!
        // create edges along trips, and add them to nodes. determine cost by time? use
        // departure time to determine if path is
        // traversable!
    }

    public LinkedList<SL_Stop_Time> djikstras() {

        return null;
    }

    /**
     * Imports one of for possible CSV files (sl_stops, sl_stop_times, sl_routes, or
     * sl_trips.)
     * 
     * @param fileName is the name of the file, which needs to be in
     *                 src/test/resources/ (the path is added inside the method).
     *                 The method can only handle .txt files therefore it is added
     *                 inside the method. That is - when calling this method write
     *                 "fileName", and not "fileName.txt" or "x/y/fileName.txt".
     */
    private void importCSV(String fileName) {
        try (
                FileReader fileReader = new FileReader("src\\test\\resources\\sl_gtfs_data\\" + fileName + ".txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void dataIntoGraph() {

        // //en lista på alla linjer, varje linje har stops.
        // SL_Route currentRoute = null;
        // Line currentLine = null;

        // //create all lines:
        // for(SL_Trip trip : trips){
        // if(currentRoute == null || currentRoute.getRoute_id() != trip.getRoute_id()){
        // for(SL_Route route : routes){
        // if(route.getRoute_id()==trip.getRoute_id()){
        // currentRoute = route;
        // currentLine = new Line(currentRoute);
        // lines.add(currentLine);
        // break; //break to make sure that loop doesn't keep iterating once it is done
        // }
        // }
        // }
        // //do stuff...
        // currentLine.addTrip(trip);
        // }

        // System.out.println("Added all trips to each line!");

        // for(Line line : lines){
        // for(SL_Trip trip : line.getTrips()){

        // for(int i = 0; i<stopTimes.size();i++){

        // if(stopTimes.get(i).getTrip_id()==trip.getTrip_id()){
        // for(int j = 0; j<stops.size();j++){
        // boolean foundEm =stops.get(j).getStop_id()==stopTimes.get(i).getStop_id();
        // // int stopStopId = stops.get(j).getStop_id();
        // // int timeStopId = stopTimes.get(i).getStop_id();
        // while(foundEm){
        // foundEm = true;
        // line.addStop(stops.get(j));
        // j++;
        // if(j==stops.size() ||
        // stops.get(j).getStop_id()!=stopTimes.get(i).getStop_id()){
        // break;
        // }

        // }
        // if(foundEm){
        // break; //to make sure iteration stops once all in a row have been found.
        // //This works bc data is presorted.
        // }
        // }

        // }
        // }
        // }
        // }
        // System.out.println("Added all stops to each line");

        // System.out.println(lines);
    }

    private void addStop(String[] lines) {
        int stop_id = Integer.parseInt(lines[0]);
        String stop_name = lines[1];
        // double stop_lat = Double.parseDouble(lines[2]);
        // double stop_lon = Double.parseDouble(lines[3]);
        stops.add(new SL_Stop(stop_id, stop_name/* , stop_lat, stop_lon */));
    }

    private void addRoute(String[] lines) {
        long route_id = Long.parseLong(lines[0]);
        short route_short_name = Short.parseShort(lines[2]);
        // String route_long_name = lines[3];
        short route_type = Short.parseShort(lines[3]);
        routes.add(new SL_Route(route_id, route_short_name, route_type));
    }

    private void addTrip(String[] lines) {
        Long routeId = Long.parseLong(lines[0]);
        SL_Route line = null;
        for (SL_Route route : routes) {
            if (route.getRoute_id() == routeId) {
                line = route;
            }
        }

        Long trip_id = Long.parseLong(lines[2]);
        String trip_headsign = lines[3];
        trips.add(new SL_Trip(line, trip_id, trip_headsign)); // add dependency!!!
    }

    private void addStopTime(String[] lines) {
        String departureTime = lines[2];
        int stopId = Integer.parseInt(lines[3]);
        SL_Stop location = null;
        for (SL_Stop stop : stops) {
            if (stop.getStop_id() == stopId) {
                location = stop;
                break;
            }
        }
        short stop_sequence = Short.parseShort(lines[4]);

        Long tripId = Long.parseLong(lines[0]);
        SL_Trip tripStop = null;
        for (SL_Trip trip : trips) {
            if (trip.getTrip_id() == tripId) {
                tripStop = trip;
                SL_Stop_Time stopTime = new SL_Stop_Time(tripStop, departureTime, location, stop_sequence);
                trip.addStopTime(stopTime);
                stopTimes.add(stopTime);
                break;
            }
        }
    }

    private void addTripsToRoutes() {
        for (SL_Route route : routes) {
            for (SL_Trip trip : trips) {
                if (trip.getRoute().equals(route)) {
                    route.addTrip(trip);
                }
            }
        }
    }

    public void addRoutesToStops() {
        for (SL_Stop stop : stops) {
            for (SL_Route route : routes) {
                List<SL_Trip> routeTrips = route.getTrips();
                for (SL_Trip trip : routeTrips) {
                    if (trip.getStops().contains(stop)) {
                        stop.addConnection(route);
                    }
                }
            }
        }
    }
}
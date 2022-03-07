package se.su.dsv.MyAldaList.ProjectTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//TODO: Djikstras baserat på avgångstid
//TODO: A* för att hitta rätt väg.
//TODO: Implementera gångavstånd via long/lat-avstånd? 
//TODO: Effektivisera via att kolla ifall de befinner sig på samma linje? Eller t.o.m. linjerna som går från linjen?
//TODO: Kartkoordinater!

//TODO: bedöm vilka linjer som är vilka färdmedel. 
//TODO: prioritera linjer som finns i targets connections. 

/**
 * Heuristic:
 *      1: Kolla att någon av de routes i start finns i routes i end. Spara alla dom routes:
 *          För varje sådan Route, ge den ett värde baserat på färdmedel - metro>tramway>bus
 *              Kolla när den med högst värde går. om det är långt till avgångstid, jämför med den med näst högst värde, e.t.c.
 *              Returnera bästa värdet du fick, och vilken väg det var
 *                  
 *      2: Om de inte delar någon route...: 
 *          Ta en station i taget åt vardera håll (om bägge finns) med bästa färdmedel. Utför steg 1 på den. 
 *          - Steg 1 på nuvarande station. 
 *              Gör ovan steg 3 gånger, om fortfarande inget - kolla en station på näst bästa färdmedel om något finns.
 *      
 * Kör till man hittat tre vägar, föreslå alla dom i snabbhetsordning?
 * 
 * Använd koordinater för att lista ut riktning? Är det vad man gör om det finns flera av samma färdmedel kanske?
 * 
 */     

public class SLlight {
    List<SL_Stop> stops = new ArrayList<>();
    List<SL_Stop_Time> stopTimes = new ArrayList<>();
    List<SL_Route> routes = new LinkedList<>();
    List<SL_Trip> trips = new LinkedList<>();

    public static void main(String[] args) {
        SLlight sl = new SLlight();
        sl.initialize();
    }



    /*
    function reconstruct_path(cameFrom, current)
    total_path := {current}
    while current in cameFrom.Keys:
        current := cameFrom[current]
        total_path.prepend(current)
    return total_path

// A* finds a path from start to goal.
// h is the heuristic function. h(n) estimates the cost to reach goal from node n.
function A_Star(start, goal, h)
    // The set of discovered nodes that may need to be (re-)expanded.
    // Initially, only the start node is known.
    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
    openSet := {start}

    // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start
    // to n currently known.
    cameFrom := an empty map

    // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
    gScore := map with default value of Infinity
    gScore[start] := 0

    // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
    // how short a path from start to finish can be if it goes through n.
    fScore := map with default value of Infinity
    fScore[start] := h(start)

    while openSet is not empty
        // This operation can occur in O(Log(N)) time if openSet is a min-heap or a priority queue
        current := the node in openSet having the lowest fScore[] value
        if current = goal
            return reconstruct_path(cameFrom, current)

        openSet.Remove(current)
        for each neighbor of current
            // d(current,neighbor) is the weight of the edge from current to neighbor
            // tentative_gScore is the distance from start to the neighbor through current
            tentative_gScore := gScore[current] + d(current, neighbor)
            if tentative_gScore < gScore[neighbor]
                // This path to neighbor is better than any previous one. Record it!
                cameFrom[neighbor] := current
                gScore[neighbor] := tentative_gScore
                fScore[neighbor] := tentative_gScore + h(neighbor)
                if neighbor not in openSet
                    openSet.add(neighbor)

    // Open set is empty but goal was never reached
    return failure



    
    */

    public Path findPath(SL_Stop from, SL_Stop to, Time earliestTime){
        List<Path> paths = new LinkedList<>();
        List<SL_Stop_Time> firstPartOfPath = new LinkedList<>();


        paths.addAll(findCommmonRoutes(new Path(null, to), from, to, earliestTime));

        //TODO: find best path out of paths!

        return null;
    }

    public List<Path> findCommmonRoutes(Path path, SL_Stop from, SL_Stop to, Time earliestTime){
        List<Path> paths = new LinkedList<>();
        path.addStop(from);
        
        Set<SL_Route> fromRoutes = from.getRoutes();
        Set<SL_Route> toRoutes = to.getRoutes();
        
        List<SL_Route> routesInCommon = new ArrayList<>();
        for(SL_Route route : fromRoutes){
            if(toRoutes.contains(route)){
                routesInCommon.add(route);
                paths.add(new Path(route, to, path.getFirstPartOfPath()));
            }
        }
        if(routesInCommon.isEmpty()){
            //sort transport options in from
            //visited nodes so far are in the path. 
            //take a node in the direction of to according to longlat. 
            //recur on this method. 

            //NOTE: need to make sure if you come to subway intersection, 
            //you take the best subway direction, even if it implies shifting? have a boolean determine if this is active?

            //NOTE: 
        }

        routesInCommon.sort(null); //sorted by best transport option. should be a more hard core sorting though?
        for(int i = 0; i < routesInCommon.size() && i<3; i++){

        }

        return paths;
    }

    

    private void tests(){
        // Edge[] edges=stops.get(50).edgesAtSpecificTime(new Time("16:00:00"));
        // for(Edge edge : edges){
        //     System.out.println(edge);
        // }

        System.out.println(stops.get(20).getEdges().get(0));

        // System.out.println(trips.get(0));
        // Time time1 = new Time("23:30:00");
        // Time time2 = new Time("07:40:00");
        // System.out.println(Time.timeDifference(time1, time2));
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
        addTripsToStops();
            long endTimeSix = System.currentTimeMillis();
            long timeSix = endTimeSix - startTimeSix;
            System.out.println("Linking of Routes to Stops took: " + timeSix);

            long startTimeSeven = System.currentTimeMillis();
        addEdgesToStops();
            long endTimeSeven = System.currentTimeMillis();
            long timeSeven = endTimeSeven - startTimeSeven;
            System.out.println("Adding edges to stops took: " + timeSix);

            System.out.println("Total Import took: " + (timeOne + timeTwo + timeThree + timeFour + timeFive + timeSix + timeSeven));

        tests();

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
        double stop_lat = Double.parseDouble(lines[2]);
        double stop_lon = Double.parseDouble(lines[3]);
        stops.add(new SL_Stop(stop_id, stop_name, stop_lat, stop_lon));
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
            if (route.getId() == routeId) {
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
            if (stop.getId() == stopId) {
                location = stop;
                break;
            }
        }
        short stop_sequence = Short.parseShort(lines[4]);

        Long tripId = Long.parseLong(lines[0]);
        SL_Trip tripStop = null;
        for (SL_Trip trip : trips) {
            if (trip.getId() == tripId) {
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
                    for(SL_Stop stop : trip.getStops()){
                        route.addStop(stop);
                    }
                }
            }
        }
    }

    public void addTripsToStops() {
        for (SL_Stop stop : stops) {
            for (SL_Trip trip : trips) {
                if (trip.getStops().contains(stop)) {
                    stop.addConnection(trip);
                }
            }
        }
    }

    private void addEdgesToStops() {
        for(SL_Stop stop : stops){
            stop.addEdges();
        }

    }
}
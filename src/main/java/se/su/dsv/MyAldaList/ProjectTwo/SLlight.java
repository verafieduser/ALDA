/**
 * @author Vera Nygren, klny8594
 */

package se.su.dsv.MyAldaList.ProjectTwo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

//TODO: FOCUS ON DATASTRUCTURE > ALGORITHM! 
// bc G=Datastructure VG=Algorithm 
//TODO: metod för att hitta noder baserat på namn?
//TODO: A* för att hitta rätt väg.
//TODO: OOP - separera node från stop?
//TODO: några stopTimes saknar trips? t.ex. jarlaberg?
//TODO: tror det har att göra med slutstationer!

/**
 * Heuristic:
 * 1: Kolla att någon av de routes i start finns i routes i end. Spara alla dom
 * routes:
 * För varje sådan Route, ge den ett värde baserat på färdmedel -
 * metro>tramway>bus
 * Kolla när den med högst värde går. om det är långt till avgångstid, jämför
 * med den med näst högst värde, e.t.c.
 * Returnera bästa värdet du fick, och vilken väg det var
 * 
 * 2: Om de inte delar någon route...:
 * Ta en station i taget åt vardera håll (om bägge finns) med bästa färdmedel.
 * Utför steg 1 på den.
 * - Steg 1 på nuvarande station.
 * Gör ovan steg 3 gånger, om fortfarande inget - kolla en station på näst bästa
 * färdmedel om något finns.
 * 
 * Kör till man hittat tre vägar, föreslå alla dom i snabbhetsordning?
 * 
 * Använd koordinater för att lista ut riktning? Är det vad man gör om det finns
 * flera av samma färdmedel kanske?
 * 
 */
/**class that central to this project. Is used to initialize 
 * and linking data from the CSV provided, and then modifier futher by me. 
 * It also handles user interfacing.
 * 
 * It has containers for the data from the CSV, but are mostly used to
 * build the data into the graph it represents. All access to the graph
 * goes through the Graph class, which is constructed from SLlight with 
 * the SL_Stops from the CSV as backbone. 
 * 
 */
public class SLlight {

    Graph graph;
    PathFinder pathfinder;
    Scanner in;
    //PrintStream out = new PrintStream(System.out, true, "UTF-8");

    Set<SLStop> stops = new HashSet<>();
    List<SLStopTime> stopTimes = new ArrayList<>();
    List<SLRoute> routes = new LinkedList<>();
    List<SLTrip> trips = new LinkedList<>();

    public static void main(String[] args) {
        SLlight sl = new SLlight();
        sl.initializeData(true);
        //sl.tests();
        sl.queryUser();
        sl.close();
    }

    public void close(){
        in.close();
    }

    private void queryUser() {
        do {
            SLStop[] fromAndTo = queryUserAboutTrip();
            SLStop from = fromAndTo[0];
            SLStop to = fromAndTo[1];
            boolean resultByArrival = queryUserAboutTime();
            Time t = queryUserAboutWhen();
            System.out.println("Going from: " + from.getName() + " to: " + to.getName());
            System.out.print("Do you want to find a trip with a minimum amount of shifts? y/n: ");
            List<Edge> path;
            if(in.nextLine().equalsIgnoreCase("y")){
                path = pathfinder.minimumShifts(from, to, t, resultByArrival);
            } else {
                path = pathfinder.aStar(from, to, t, resultByArrival);
            }
            System.out.println(pathfinder.printPath(path));
            System.out.print("Press enter for another trip: ");
        } while ((in.nextLine().equalsIgnoreCase("")));

        //in.close();
    }

    private SLStop[] queryUserAboutTrip() {
        String errorMessage = "Station could not be found. Try again";
        SLStop from = findNode("Going from : ", errorMessage);
        SLStop to = findNode("Going to: ", errorMessage);
        return new SLStop[] { from, to };
    }

    private boolean queryUserAboutTime() {
        System.out.print("Do you want to have an answer by departure time, or arrival time?");
        String answer = in.nextLine();
        return answer.equalsIgnoreCase("arrival") ? true : false;
    }

    private Time queryUserAboutWhen() {
        System.out.print("Enter time in format \"xx:xx:xx\": ");
        String answer = in.nextLine();
        return new Time(answer);
    }

    private SLStop findNode(String information, String errorMessage) {
        System.out.print(information);
        String query = in.nextLine(); 
        System.out.println(query);
        SLStop result = findNode(query);
        while (result == null) {
            System.out.println(errorMessage);
            System.out.print(information);
            result = findNode(in.nextLine());
        }
        return result;
    }

    public SLStop findNode(String name){
        return graph.findNode(name);
    }
    
    public List<Edge> findPath(SLStop from, SLStop to, Time time, boolean timeIsArrivalAtGoal, boolean astar){
        if(astar){
            return pathfinder.aStar(from, to, time, timeIsArrivalAtGoal);
        }
        return pathfinder.minimumShifts(from, to, time, timeIsArrivalAtGoal);
       
    }

    public void initializeData(boolean withTimePrints) {
        long startTimeOne = System.currentTimeMillis();
        importCSV("sl_routes");

        importCSV("sl_stops");
        importCSV("sl_trips");
        importCSV("sl_stop_times");
        long endTimeOne = System.currentTimeMillis();
        long timeOne = endTimeOne - startTimeOne;
        if (withTimePrints) {
            System.out.println("Importing from CSV took: " + timeOne);
        }

        long startTimeTwo = System.currentTimeMillis();
        addTripsToRoutes();
        addTripsToStops();
        addEdgesToStops();
        long endTimeTwo = System.currentTimeMillis();
        long timeTwo = endTimeTwo - startTimeTwo;
        if (withTimePrints) {
            System.out.println("Linking graph together took: " + timeTwo);
            System.out.println("Total Import took: " + (timeOne + timeTwo));
        }


        graph = new Graph(stops);
        pathfinder = new PathFinder(graph);
        in = new Scanner(System.in);
    }

    public Graph getGraph() {
        return graph;
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
                FileReader fileReader = new FileReader("src\\test\\resources\\sl_gtfs_data\\" + fileName + ".txt", StandardCharsets.UTF_8);
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

    private void addStop(String[] lines) {
        int stopId = Integer.parseInt(lines[0].substring(4));
        String stopName = lines[1];
        double stopLat = Double.parseDouble(lines[2]);
        double stopLon = Double.parseDouble(lines[3]);
        stops.add(new SLStop(stopId, stopName, stopLat, stopLon));
    }

    private void addRoute(String[] lines) {
        int routeId = Integer.parseInt(lines[0].substring(6));
        short routeShortName = Short.parseShort(lines[2]);
        // String route_long_name = lines[3];
        short routeType = Short.parseShort(lines[3]);
        routes.add(new SLRoute(routeId, routeShortName, routeType));
    }

    private void addTrip(String[] lines) {
        int routeId = Integer.parseInt(lines[0].substring(6));
        SLRoute line = null;
        for (SLRoute route : routes) {
            if (route.getId() == routeId) {
                line = route;
            }
        }

        int tripId = Integer.parseInt(lines[2].substring(6));
        String tripHeadsign = lines[3];
        trips.add(new SLTrip(line, tripId, tripHeadsign)); // add dependency!!!
    }

    private void addStopTime(String[] lines) {
        String departureTime = lines[2];
        if(departureTime.isBlank()){
            departureTime = lines[1];
        }
        int stopId = Integer.parseInt(lines[3].substring(4));
        SLStop location = null;
        for (SLStop stop : stops) {
            if (stop.getId() == stopId) {
                location = stop;
                break;
            }
        }
        short stopSequence = Short.parseShort(lines[4]);

        int tripId = Integer.parseInt(lines[0].substring(6));
        SLTrip tripStop = null;
        for (SLTrip trip : trips) {
            if (trip.getId() == tripId) {
                tripStop = trip;
                SLStopTime stopTime = new SLStopTime(tripStop, departureTime, location, stopSequence);
                trip.addStopTime(stopTime);
                stopTimes.add(stopTime);
                break;
            }
        }
    }

    private void addTripsToRoutes() {
        for (SLRoute route : routes) {
            for (SLTrip trip : trips) {
                if (trip.getRoute().equals(route)) {
                    route.addTrip(trip);
                    for (SLStop stop : trip.getStops()) {
                        route.addStop(stop);
                    }
                }
            }
        }
    }

    public void addTripsToStops() {
        for (SLStop stop : stops) {
            for (SLTrip trip : trips) {
                if (trip.getStops().contains(stop)) {
                    stop.addConnection(trip);
                }
            }
        }
    }

    private void addEdgesToStops() {
        for (SLStop stop : stops) {
            stop.addEdges();
        }

    }

    public void tests() {
        testMaxShifts();
        graph = new Graph(stops);
        SLStop from = graph.findNode("Jarlaberg");
        SLStop to = graph.findNode("Stockholm Sickla Kaj");
        String result = pathfinder.printPath(pathfinder.aStar(from, to, new Time("14:21:00"), false));
        System.out.println(result);
    }

    private void testMaxShifts() {
        List<SLStop> stopsAsList = new ArrayList<>(stops);
        int maxShifts = 0;
        for (int i = 0; i < stops.size(); i++) {
            for (int j = i + 1; j < stops.size(); j++) {
                int candidate = routesInCommon(stopsAsList.get(i), stopsAsList.get(j));
                maxShifts = maxShifts < candidate ? candidate : maxShifts;
            }
        }
        System.out.println("Max amounts of shifts in SL is: " + maxShifts);
    }

    private int routesInCommon(SLStop i, SLStop j) {
        int steps = 1;
        List<SLRoute> routes = new ArrayList<>();
        for (SLRoute route : i.getRoutes()) {
            if (j.getRoutes().contains(route)) {
                return steps;
            }
            routes.add(route);
        }
        while (true) {
            List<SLRoute> nextRoutes = new ArrayList<>();
            for (SLRoute route1 : routes) {
                for (SLRoute route2 : route1.intersectingRoutes()) {
                    if (j.getRoutes().contains(route2)) {
                        return steps;
                    }
                    nextRoutes.add(route2);
                }
            }
            steps++;
            routes = nextRoutes;
        }
    }
}
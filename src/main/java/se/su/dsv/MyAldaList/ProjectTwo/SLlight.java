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
 * class that central to this project. Is used to initialize
 * and linking data from the CSV provided, and then modifier futher by me.
 * It also handles user interfacing.
 * 
 * It has containers for the data from the CSV, but are mostly used to
 * build the data into the graph it represents. All access to the graph
 * goes through the Graph class, which is constructed from SLlight with
 * the SL_Stops from the CSV as backbone.
 * 
 */
public class SLLight {

    Graph graph;
    PathFinder pathfinder;
    Scanner in;

    Set<Station> stops = new HashSet<>();
    List<StopTime> stopTimes = new ArrayList<>();
    List<Route> routes = new LinkedList<>();
    List<Trip> trips = new LinkedList<>();

    public static void main(String[] args) {
        SLLight sl = new SLLight();
        sl.initializeData(true, false);
        sl.queryUser();
        sl.close();
    }

    /**
     * Closes the scanner, to be called before program completion.
     */
    public void close() {
        in.close();
    }

    /**
     * Method for interfacing with pathfinding, by locating stations for start/end
     * of path,
     * if user wants result by arrive at time, or depart at time, and which time
     * user wants that to be at.
     * lastly, asks user if they want to use A* algorithm, or the algorithm for
     * finding a path with a minimum
     * amount of changes of modes of transport.
     */
    public void queryUser() {
        do {
            Station[] fromAndTo = queryUserAboutTrip();
            Station from = fromAndTo[0];
            Station to = fromAndTo[1];
            boolean resultByArrival = queryUserAboutTime();
            Time t = queryUserAboutWhen();
            System.out.println("Going from: " + from.getName() + " to: " + to.getName());
            System.out.print("Do you want to find a trip with a minimum amount of shifts? y/n: ");
            List<Edge> path;
            if (in.nextLine().equalsIgnoreCase("y")) {
                path = pathfinder.minimumShifts(from, to, t, resultByArrival);
            } else {
                path = pathfinder.aStar(from, to, t, resultByArrival);
            }
            System.out.println(pathfinder.printPath(path));
            System.out.print("Press enter for another trip: ");
        } while ((in.nextLine().equalsIgnoreCase("")));
    }

    /**
     * Helper method for queryUser(). Locates stations based on inputted string.
     * 
     * @return an array with the length of 2, containing i0=the station the user
     *         wants to depart from,
     *         and i1= the station the user wants to arrive at.
     */
    private Station[] queryUserAboutTrip() {
        String errorMessage = "Station could not be found. Try again";
        Station from = findNode("Going from : ", errorMessage);
        Station to = findNode("Going to: ", errorMessage);
        return new Station[] { from, to };
    }

    /**
     * Helper method for queryUser().
     * 
     * @return a boolean representing if the user wants a result where they arrive
     *         at specified time, or one in which
     *         they depart at specified time.
     */
    private boolean queryUserAboutTime() {
        System.out.print("Enter which you prefer: An answer in which time determines arrival at goal(1), "
                + "or an answer in which time determines departure from start(2)");
        String answer = in.nextLine();
        return answer.equalsIgnoreCase("1");
    }

    /**
     * Helper method for queryUser()
     * 
     * @return a time the user wants their trip based upon
     */
    private Time queryUserAboutWhen() {
        System.out.print("Enter time in format \"xx:xx:xx\": ");
        String answer = in.nextLine();
        return new Time(answer);
    }

    /**
     * Helper method for queryUser().
     * 
     * @param information  information to be displayed before user query.
     * @param errorMessage error message to be displayed if no station was found.
     * @return a station with name matching with string the user inputed.
     */
    private Station findNode(String information, String errorMessage) {
        System.out.print(information);
        String query = in.nextLine();
        System.out.println(query);
        Station result = findNode(query);
        while (result == null) {
            System.out.println(errorMessage);
            System.out.print(information);
            result = findNode(in.nextLine());
        }
        return result;
    }

    /**
     * Method to make node finding possible to do from outside of this class, if
     * you'd want to do that.
     * 
     * @param name the name of the station you want to find
     * @return a station with the same name as the param name. If none was found,
     *         null is returned.
     */
    public Station findNode(String name) {
        return graph.findNode(name);
    }

    /**
     * Method to initialize pathfinding.
     * 
     * @param from                the station you want to depart from.
     * @param to                  the station you want to arrive at.
     * @param time                the time you want the trip to be at.
     * @param timeIsArrivalAtGoal whether the time specifies arrival at "to", or
     *                            departure from "from".
     * @param astar               determines whether it uses the A*algorithm, or the
     *                            minimum shifts one.
     * @return a list of edges connecting "from" with "to". Returns null if none was
     *         found.
     */
    public List<Edge> findPath(Station from, Station to, Time time, boolean timeIsArrivalAtGoal, boolean astar) {
        if (astar) {
            return pathfinder.aStar(from, to, time, timeIsArrivalAtGoal);
        }
        return pathfinder.minimumShifts(from, to, time, timeIsArrivalAtGoal);

    }

    /**
     * Initializes data by importing it from the CSV, and then linking it together,
     * and then using
     * this data to create the graph class, and the pathfinder-class.
     * 
     * @param withTimePrints true if you want terminal prints that specifies the
     *                       time taken for importing
     *                       from the csv, and the linking of data.
     * @param withTests      true if you want tests specified in method "tests()" to
     *                       be run.
     */
    public void initializeData(boolean withTimePrints, boolean withTests) {
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
        long endTimeTwo = System.currentTimeMillis();
        long timeTwo = endTimeTwo - startTimeTwo;
        if (withTimePrints) {
            System.out.println("Linking graph together took: " + timeTwo);
            System.out.println("Total Import took: " + (timeOne + timeTwo));
        }

        graph = new Graph(stops);
        pathfinder = new PathFinder(graph);
        in = new Scanner(System.in);
        if (withTests) {
            tests();
        }
    }

    /**
     * @return the graph object within this class.
     */
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
                FileReader fileReader = new FileReader("src\\test\\resources\\sl_gtfs_data\\" + fileName + ".txt",
                        StandardCharsets.UTF_8);
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

    /**
     * Adds stop to list of stops.
     * 
     * @param lines a CSV-line containing the data for a stop, where i0=stopId,
     *              i1=latitude, i3=longitude.
     */
    private void addStop(String[] lines) {
        int stopId = Integer.parseInt(lines[0].substring(4));
        String stopName = lines[1];
        double stopLat = Double.parseDouble(lines[2]);
        double stopLon = Double.parseDouble(lines[3]);
        stops.add(new Station(stopId, stopName, stopLat, stopLon));
    }

    /**
     * Adds a route to list of routes.
     * 
     * @param lines a CSV-line containing the data for a route, where i0=routeId,
     *              and unique identifier,
     *              i2=the routes number, i3=the type the route is (ie, bus, metro,
     *              tramway)
     */
    private void addRoute(String[] lines) {
        int routeId = Integer.parseInt(lines[0].substring(6));
        short routeShortName = Short.parseShort(lines[2]);
        // String route_long_name = lines[3];
        short routeType = Short.parseShort(lines[3]);
        routes.add(new Route(routeId, routeShortName, routeType));
    }

    /**
     * Adds a trip to list of trips.
     * 
     * @param lines a CSV-line containing the data for a trip, where i0=routeId, for
     *              linking the trip to the route it is on,
     *              i2=tripId, the unique identifier for the trip. i3=the headsign
     *              of the trip, that is, the end station it is going towards.
     */
    private void addTrip(String[] lines) {
        int routeId = Integer.parseInt(lines[0].substring(6));
        Route line = null;
        for (Route route : routes) {
            if (route.getId() == routeId) {
                line = route;
            }
        }

        int tripId = Integer.parseInt(lines[2].substring(6));
        String tripHeadsign = lines[3];
        trips.add(new Trip(line, tripId, tripHeadsign)); // add dependency!!!
    }

    /**
     * Adds a stoptime to the list of stoptimes. A stop time is a specific departure
     * on a trip, from a stop.
     * 
     * @param lines i0=the id of the trip it is on, i1 and i2 are
     *              departure/arrivaltimes, which are always
     *              identical in this system. But because some final stops dont have
     *              a departure time, but only an arrival time -
     *              I add both to the same variable, "departureTime". i3=stopId, the
     *              id of the stop it is on. i4=stopSequence, the
     *              number of which stop it is on the trip.
     */
    private void addStopTime(String[] lines) {
        String departureTime = lines[2];
        if (departureTime.isBlank()) {
            departureTime = lines[1];
        }
        int stopId = Integer.parseInt(lines[3].substring(4));
        Station location = null;
        for (Station stop : stops) {
            if (stop.getId() == stopId) {
                location = stop;
                break;
            }
        }
        short stopSequence = Short.parseShort(lines[4]);

        int tripId = Integer.parseInt(lines[0].substring(6));
        Trip tripStop = null;
        for (Trip trip : trips) {
            if (trip.getId() == tripId) {
                tripStop = trip;
                StopTime stopTime = new StopTime(tripStop, departureTime, location, stopSequence);
                trip.addStopTime(stopTime);
                stopTimes.add(stopTime);
                break;
            }
        }
    }

    /**
     * Links together trips and routes, by adding each trip that is on a specific
     * route, onto that route.
     */
    private void addTripsToRoutes() {
        for (Route route : routes) {
            for (Trip trip : trips) {
                if (trip.getRoute().equals(route)) {
                    route.addTrip(trip);
                    for (Station stop : trip.getStops()) {
                        route.addStop(stop);
                    }
                }
            }
        }
    }

    /**
     * Adds the trips which goes past a stop, to that stop.
     */
    public void addTripsToStops() {
        for (Station stop : stops) {
            for (Trip trip : trips) {
                if (trip.getStops().contains(stop)) {
                    stop.addConnection(trip);
                }
            }
        }
    }

    /**
     * Method in which you can put tests for the app.
     */
    public void tests() {
        testMaxShifts();
    }

    /**
     * Method that finds the max amount of shifts necessary to get from any station
     * to another within the graph.
     * Having run this, the max amount seems to be 3. This is helpful for for
     * example the minimum shifts method in
     * the pathfinder class, because it guarantees the result is at worst O(n^3)
     * time complexity.
     */
    private void testMaxShifts() {
        List<Station> stopsAsList = new ArrayList<>(stops);
        int maxShifts = 0;
        for (int i = 0; i < stops.size(); i++) {
            for (int j = i + 1; j < stops.size(); j++) {
                int candidate = routesInCommon(stopsAsList.get(i), stopsAsList.get(j));
                maxShifts = maxShifts < candidate ? candidate : maxShifts;
            }
        }
        System.out.println("Max amounts of shifts in SL is: " + maxShifts);
    }

    /**
     * Helper method for the testMaxShifts method.
     * 
     * @param i a station
     * @param j another station
     * @return the amount of routes necessary to cross station i to station j
     */
    private int routesInCommon(Station i, Station j) {
        int steps = 1;
        List<Route> routesInCommon = new ArrayList<>();
        for (Route route : i.getRoutes()) {
            if (j.getRoutes().contains(route)) {
                return steps;
            }
            routes.add(route);
        }
        while (true) {
            List<Route> nextRoutes = new ArrayList<>();
            for (Route route1 : routesInCommon) {
                for (Route route2 : route1.intersectingRoutes()) {
                    if (j.getRoutes().contains(route2)) {
                        return steps;
                    }
                    nextRoutes.add(route2);
                }
            }
            steps++;
            routesInCommon = nextRoutes;
        }
    }
}
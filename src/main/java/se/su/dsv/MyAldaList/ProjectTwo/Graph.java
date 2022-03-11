/**
 * @author Vera Nygren, klny8594
 */

package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class that stores nodes, and methods for helping out working with them,
 * such as finding neighbouring nodes for the A* algorithm, calculating
 * geo-distance between stations, and finding nodes based on station names.
 * 
 * Only stores nodes, and then nodes are responsible for
 * storing their own edges.
 * 
 */
public class Graph {

    /**
     * Collection of nodes that make up the graph. The nodes store the edges.
     * Is used to locate start and end nodes for the path finding algorithms.
     */
    private Set<Station> nodes;

    /**
     * Creates a graph with the nodes specified.
     * 
     * @param nodes for full functionality, the data already needs to be fully
     *              connected by the SLlight-class.
     */
    public Graph(List<Station> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    /**
     * Creates a graph with the nodes specified.
     * 
     * @param nodes for full functionality, the data already needs to be fully
     *              connected by the SLlight-class.
     */
    public Graph(Set<Station> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    /**
     * Method for finding neighbouring nodes from a stop, and then assign
     * a cost to them according to the A* algorithm.
     * 
     * @param current stop that you want to find neighbours to.
     * @param goal    station to calculate distance to for the A* algorithm.
     * @return a list of stops that neighbours param current, with costs
     *         added for the path taken so far.
     */
    public List<Station> addNeighbouringNodes(Station current, Station goal) {
        List<Station> neighbours = new LinkedList<>();
        for (Edge edge : current.getUniqueEdges()) {
            Station stop = edge.getTo().getStation();
            Time nextStopRouteCost = Time.plus(current.getCurrentRouteScore(), edge.getCost());
            // if the new connection takes less time than the old one, add it (prohibits
            // visited nodes to be visited over n over)
            if (nextStopRouteCost.compareTo(stop.getCurrentRouteScore()) < 0) {
                stop.setCurrentRouteScore(nextStopRouteCost);
                stop.setDistanceToGoalScore(calculateDistance(stop, goal));
                stop.setPrevious(current);
                neighbours.add(stop);

            }
        }
        return neighbours;
    }

    /**
     * Algorithm for calculating the distance between two map coordinates,
     * that is, two points with longitude and latitude.
     * 
     * Works by finding the angle between the points, and projects
     * it upon the sphere with a radius the same as earth.
     * 
     * Based on algorithm found at:
     * http://www.movable-type.co.uk/scripts/latlong.html
     * 
     * @param from
     * @param to
     * @return
     */
    public double calculateDistance(Station from, Station to) {
        double[] latlot1 = from.getLatlon();
        double[] latlot2 = to.getLatlon();
        double lon1 = latlot1[1];
        double lat1 = latlot1[0];
        double lon2 = latlot2[1];
        double lat2 = latlot2[0];

        double R = 6372.8; // radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double angle = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1Rad) * Math.cos(lat2Rad);
        double c = 2 * Math.atan2(Math.sqrt(angle), Math.sqrt(1 - angle));
        return R * c; // Distance in km
    }

    /**
     * Searches for a station in the list of nodes based on
     * text string acquired.
     * 
     * @param query containing the name of the station you are searching for.
     *              case insensitive.
     * @return the node found with the same name as the param query string content.
     *         Returns null if none was found.
     */
    public Station findNode(String query) {
        for (Station stop : nodes) {
            if (stop.getName().toLowerCase().equals(query.toLowerCase())) {
                return stop;
            }
        }
        return null;
    }

    /**
     * clears all the nodes from information generated by the
     * A* algorithm.
     */
    public void clear() {
        for (Station node : nodes) {
            node.clear();
        }
    }

}
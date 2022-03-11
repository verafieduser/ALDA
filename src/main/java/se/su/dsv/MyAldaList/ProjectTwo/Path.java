/**
 * @author Vera Nygren, klny8594
 */
package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private List<Station> firstPartOfPath;
    private Route restOfPath;
    private Station to;

    public Path(Route route, Station to){
        firstPartOfPath = new LinkedList<Station>();
        restOfPath=route;
        this.to=to;
    }

    public Path(Route route, Station to, Station... stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(Arrays.asList(stops));
        restOfPath=route;
        this.to=to;
    }

    public Path(Route route, Station to, List<Station> stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(stops);
        restOfPath=route;
        this.to=to;
    }

    public Path(List<Station> firstPartOfPath, Route restOfPath, Station to){
        this.firstPartOfPath = firstPartOfPath;
        this.restOfPath = restOfPath;
        this.to = to;
    }    

    public boolean addStop(Station stop){
        return firstPartOfPath.add(stop);
    }

    public List<Station> getFirstPartOfPath() {
        return firstPartOfPath;
    }

}
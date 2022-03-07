package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private List<SL_Stop> firstPartOfPath;
    private SL_Route restOfPath;
    private SL_Stop to;

    public Path(SL_Route route, SL_Stop to){
        firstPartOfPath = new LinkedList<SL_Stop>();
        restOfPath=route;
        this.to=to;
    }

    public Path(SL_Route route, SL_Stop to, SL_Stop... stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(Arrays.asList(stops));
        restOfPath=route;
        this.to=to;
    }

    public Path(SL_Route route, SL_Stop to, List<SL_Stop> stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(stops);
        restOfPath=route;
        this.to=to;
    }

    public Path(List<SL_Stop> firstPartOfPath, SL_Route restOfPath, SL_Stop to){
        this.firstPartOfPath = firstPartOfPath;
        this.restOfPath = restOfPath;
        this.to = to;
    }    

    public boolean addStop(SL_Stop stop){
        return firstPartOfPath.add(stop);
    }

    public List<SL_Stop> getFirstPartOfPath() {
        return firstPartOfPath;
    }

}
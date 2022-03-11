package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private List<SLStop> firstPartOfPath;
    private SLRoute restOfPath;
    private SLStop to;

    public Path(SLRoute route, SLStop to){
        firstPartOfPath = new LinkedList<SLStop>();
        restOfPath=route;
        this.to=to;
    }

    public Path(SLRoute route, SLStop to, SLStop... stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(Arrays.asList(stops));
        restOfPath=route;
        this.to=to;
    }

    public Path(SLRoute route, SLStop to, List<SLStop> stops){
        firstPartOfPath = new LinkedList<>();
        firstPartOfPath.addAll(stops);
        restOfPath=route;
        this.to=to;
    }

    public Path(List<SLStop> firstPartOfPath, SLRoute restOfPath, SLStop to){
        this.firstPartOfPath = firstPartOfPath;
        this.restOfPath = restOfPath;
        this.to = to;
    }    

    public boolean addStop(SLStop stop){
        return firstPartOfPath.add(stop);
    }

    public List<SLStop> getFirstPartOfPath() {
        return firstPartOfPath;
    }

}
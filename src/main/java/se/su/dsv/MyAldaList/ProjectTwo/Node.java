package se.su.dsv.MyAldaList.ProjectTwo;

import java.util.LinkedList;
import java.util.List;

public class Node {
    final SL_Stop stop;
    List<SL_Stop> neighbours = new LinkedList<>();


    public Node(SL_Stop stop) {
        this.stop = stop;
    }

    public boolean addNeigbour(SL_Stop neighbour){
        return neighbours.add(neighbour);
    }

}
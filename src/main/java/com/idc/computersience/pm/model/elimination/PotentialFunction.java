package com.idc.computersience.pm.model.elimination;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a potential function
 *
 * @author eladcohen
 */
@Getter
public class PotentialFunction {
    private List<Integer> nodesId = new ArrayList<>();

    public void addNode(int nodeId){
        nodesId.add(nodeId);
    }

    public boolean isPart(int nodeId){
        return nodesId.contains(nodeId);
    }
}

package com.idc.computersience.pm.model.elimination;

import com.idc.computersience.pm.model.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a potential function
 *
 * @author eladcohen
 */
@Getter
public class PotentialFunction {
    private int id;

    private List<Integer> nodesId = new ArrayList<>();
    @Setter
    private Node node;

    public PotentialFunction(int id){
        this.id = id;
    }

    public PotentialFunction(int id, List<Integer> nodes){
        this.id = id;
        this.nodesId = nodes;
    }

    public void addNode(int nodeId){
        nodesId.add(nodeId);
    }

    public boolean isPart(int nodeId){
        return nodesId.contains(nodeId);
    }

    public void multiple(PotentialFunction potentialFunction){
        node.getConditionalTable();
    }

}

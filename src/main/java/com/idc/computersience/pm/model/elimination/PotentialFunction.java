package com.idc.computersience.pm.model.elimination;

import com.idc.computersience.pm.model.Node;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represent a potential function
 *
 * @author eladcohen
 */
@Getter
public class PotentialFunction {
    private String id;

    /** Nodes part of this potential function*/
    private Set<String> nodesId = new HashSet<>();
    @Setter
    private Node node;

    public PotentialFunction(String id){
        this.id = id;
    }

    public PotentialFunction(String id, Set<String> nodes){
        this.id = id;
        this.nodesId = nodes;
    }

    public void addNode(String nodeId){
        nodesId.add(nodeId);
    }

    public boolean isPart(int nodeId){
        return nodesId.contains(nodeId);
    }

    public void multiple(PotentialFunction potentialFunction){
        val domain1 = node.getDomain();
        val domain2 = potentialFunction.getNode().getDomain();


        node.getConditionalTable();
        potentialFunction.getNode().getConditionalTable();
    }

}

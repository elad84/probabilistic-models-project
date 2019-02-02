package com.idc.computersience.pm.model.condition;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author eladcohen
 */
@EqualsAndHashCode(of = "nodeId")
@Getter
public class PotentialFunction {

    private String nodeId;
    private Set<String> participatingNodes = new HashSet<>();

    public PotentialFunction(String nodeId) {
        this.nodeId = nodeId;
    }

    public void addNode(String participatingNode) {
        participatingNodes.add(participatingNode);
    }
}

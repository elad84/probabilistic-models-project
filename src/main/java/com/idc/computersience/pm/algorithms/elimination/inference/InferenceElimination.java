package com.idc.computersience.pm.algorithms.elimination.inference;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.condition.BinaryMembership;
import com.idc.computersience.pm.model.condition.PotentialFunction;

import java.util.List;

/**
 * @author eladcohen
 */
public class InferenceElimination {
    private BinaryMembership binaryMembership = new BinaryMembership();
    private List<String> eliminationOrder;

    public InferenceElimination(BayesianNetwork bayesianNetwork, List<String> eliminationOrder) {
        this.eliminationOrder = eliminationOrder;

        //initiate th binary membership
        bayesianNetwork.getNodes().stream().forEach(node -> {
            PotentialFunction potentialFunction = new PotentialFunction(node.getNodeId());

            //add parents to nodes in the potential function
            if(node.getParentList() != null && !node.getParentList().isEmpty()){
                node.getParentList().forEach(potentialFunction::addNode);
            }

            binaryMembership.addMembership(node.getNodeId(), potentialFunction);
        });
    }
}

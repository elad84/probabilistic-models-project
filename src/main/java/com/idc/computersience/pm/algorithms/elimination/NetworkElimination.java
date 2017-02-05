package com.idc.computersience.pm.algorithms.elimination;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.elimination.PotentialFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author eladcohen
 */
public class NetworkElimination {
    private Map<Integer, List<PotentialFunction>> nodePotentials;
    private List<Integer> eliminationOrder;

    public void init(BayesianNetwork bayesianNetwork, List<Integer> eliminationOrder){
        List<PotentialFunction> potentialFunctions;

        //iterate over all the nodes and create potential functions
        for(Node node : bayesianNetwork.getNodes()){
            PotentialFunction potentialFunction = new PotentialFunction();
            potentialFunction.addNode(node.getNodeId());
            if(node.getDependencyList() != null && !node.getDependencyList().isEmpty()){
                node.getDependencyList().forEach(potentialFunction::addNode);
            }

            potentialFunctions = this.nodePotentials.get(node.getNodeId());
            if(potentialFunctions == null){
                potentialFunctions = new ArrayList<>();
                this.nodePotentials.put(node.getNodeId(), potentialFunctions);
            }
            potentialFunctions.add(potentialFunction);

            potentialFunctions.add(potentialFunction);
        }

        this.eliminationOrder = eliminationOrder;
    }

}

package com.idc.computersience.pm.algorithms.elimination;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.elimination.PotentialFunction;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author eladcohen
 */
@Slf4j
public class NetworkElimination {
    private Map<String, List<PotentialFunction>> nodePotentials;
    private List<String> eliminationOrder;

    public void init(BayesianNetwork bayesianNetwork, List<String> eliminationOrder){
        List<PotentialFunction> potentialFunctions;

        //iterate over all the nodes and create potential functions
        for(Node node : bayesianNetwork.getNodes()){
            PotentialFunction potentialFunction = new PotentialFunction(node.getNodeId());

            potentialFunction.setNode(node);
            //potential function points to all the nodes that are part of the conditional table
            //excluding the current node
//            potentialFunction.addNode(node.getNodeId());
            if(node.getParentList() != null && !node.getParentList().isEmpty()){
                node.getParentList().forEach(potentialFunction::addNode);
            }

            //map nodes to potential function
            potentialFunctions = this.nodePotentials.computeIfAbsent(node.getNodeId(), k -> new ArrayList<>());
            potentialFunctions.add(potentialFunction);

            if(node.getParentList() != null && !node.getParentList().isEmpty()){
                node.getParentList().forEach(nodeId ->{
                    List<PotentialFunction> nodeFunctions = this.nodePotentials.computeIfAbsent(nodeId, k -> new ArrayList<>());
                    nodeFunctions.add(potentialFunction);
                });
            }

        }

        this.eliminationOrder = eliminationOrder;
    }

    public void eliminateNext(){
        if(eliminationOrder == null || eliminationOrder.isEmpty()){
            log.info("no more nodes to eliminate");
        }

        String nextNode = eliminationOrder.remove(0);
        List<PotentialFunction> nodePotentialFunctions = nodePotentials.get(nextNode);

        Set<String> nodeNeighbors = nodePotentialFunctions.stream().flatMap(node -> node.getNodesId().stream()).collect(Collectors.toSet());

        val newPotential = new PotentialFunction(nextNode, nodeNeighbors);

        nodePotentialFunctions.sort(new PotentialFunctionComparator(nextNode));

        //take the first one
        val potentialFunction = nodePotentialFunctions.remove(0);

        nodePotentialFunctions.forEach(otherFunction -> {

        });



    }


    private static class PotentialFunctionComparator implements Comparator<PotentialFunction>{

        private String primaryId;

        PotentialFunctionComparator(String primaryId){
            this.primaryId = primaryId;
        }

        @Override
        public int compare(PotentialFunction o1, PotentialFunction o2) {
            if(o1.getId().equals(primaryId) ){
                return -1;
            }else if(o2.getId().equals(primaryId)){
                return 1;
            }
            return 0;
        }
    }



}

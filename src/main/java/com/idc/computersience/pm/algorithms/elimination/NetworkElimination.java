package com.idc.computersience.pm.algorithms.elimination;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.elimination.PotentialFunction;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author eladcohen
 */
@Slf4j
public class NetworkElimination {
    private Map<Integer, List<PotentialFunction>> nodePotentials;
    private List<Integer> eliminationOrder;

    public void init(BayesianNetwork bayesianNetwork, List<Integer> eliminationOrder){
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

        int nextNode = eliminationOrder.remove(0);
        List<PotentialFunction> nodePotentialFunctions = nodePotentials.get(nextNode);

        List<Integer> nodeNeighbors = nodePotentialFunctions.stream().flatMap(node -> node.getNodesId().stream()).distinct().collect(Collectors.toList());

        val newPotential = new PotentialFunction(nextNode, nodeNeighbors);

        nodePotentialFunctions.sort(new PotentialFunctionComparator(nextNode));

        //take the first one
        val potentialFunction = nodePotentialFunctions.remove(0);

        nodePotentialFunctions.forEach(otherFunction -> {

        });



    }


    private static class PotentialFunctionComparator implements Comparator<PotentialFunction>{

        private int primaryId;

        PotentialFunctionComparator(int primaryId){
            this.primaryId = primaryId;
        }

        @Override
        public int compare(PotentialFunction o1, PotentialFunction o2) {
            if(o1.getId() == primaryId){
                return -1;
            }else if(o2.getId() == primaryId){
                return 1;
            }
            return 0;
        }
    }



}

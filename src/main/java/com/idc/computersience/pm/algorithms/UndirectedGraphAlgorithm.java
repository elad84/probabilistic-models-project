package com.idc.computersience.pm.algorithms;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.idc.computersience.pm.model.BayesianNetwork;
import lombok.val;
import org.jgrapht.GraphTests;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author eladcohen
 */
@Component
public class UndirectedGraphAlgorithm {


    public MutableNetwork<String, String> toMorilized(BayesianNetwork bayesianNetwork){
        MutableNetwork<String, String> network = NetworkBuilder.directed()
                .allowsParallelEdges(false)
                .build();

        //convert to different model
        bayesianNetwork.getNodes().stream().forEach(node-> network.addNode(node.getNodeId()));

        //populate nodes parents
        bayesianNetwork.getEdges().stream().forEach(edge -> {
            val head = edge.getNodeId1().equals(edge.getNodeArrowHead()) ? edge.getNodeId1() : edge.getNodeId2();
            val node = edge.getNodeId1().equals(edge.getNodeArrowHead()) ? edge.getNodeId2() : edge.getNodeId1();
            network.addEdge(head, node, head + "_" + node);
//            Set<String> parentNodes = nodeParents.get(node);
//            if(parentNodes == null){
//                parentNodes = new HashSet<>();
//                nodeParents.put(node, parentNodes);
//            }
//            parentNodes.add(head);
        });


        network.nodes().stream().forEach(node -> {
            Set<String> predecessors = network.predecessors(node);
            final Set<String> leftPredecessors = new HashSet<>(predecessors);
            predecessors.stream().forEach(predecessor -> {
                leftPredecessors.stream().forEach(leftPredecessor ->{
                    if(!predecessor.equals(leftPredecessor)){
                        network.addEdge(predecessor, leftPredecessor, predecessor +"_" + leftPredecessor);
                    }
                });
                leftPredecessors.remove(predecessor);
            });
        });

        MutableNetwork<String, String> undirected = NetworkBuilder.undirected().allowsParallelEdges(false).build();
        network.nodes().stream().forEach(node-> {
            undirected.addNode(node);
            network.successors(node).stream().forEach(successor -> {
                undirected.addEdge(node, successor, node + "_" + successor);
            });
        });

        return undirected;

//        System.out.println(undirected);
    }

    public boolean isCordal(BayesianNetwork bayesianNetwork){
        val undirectedGraph = toMorilized(bayesianNetwork);
        SimpleDirectedGraph<String, String> simpleGraph = new SimpleDirectedGraph<>(String.class);
        undirectedGraph.nodes().stream().forEach(vertex -> simpleGraph.addVertex(vertex));
        undirectedGraph.edges().stream().forEach(edge -> {
            String[] edges = edge.split("_");
            simpleGraph.addEdge(edges[0], edges[1]);
        });

        System.out.println(simpleGraph);
        AsUndirectedGraph<String, String> simpleundirected = new AsUndirectedGraph<>(simpleGraph);
        return GraphTests.isChordal(simpleundirected);
    }



}

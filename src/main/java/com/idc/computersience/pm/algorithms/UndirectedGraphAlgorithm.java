package com.idc.computersience.pm.algorithms;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.idc.computersience.pm.controller.model.undirected.LeanGraph;
import com.idc.computersience.pm.controller.model.undirected.PerfectElimination;
import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Edge;
import lombok.val;
import org.jgrapht.GraphTests;
import org.jgrapht.Graphs;
import org.jgrapht.alg.clique.ChordalGraphMaxCliqueFinder;
import org.jgrapht.alg.clique.DegeneracyBronKerboschCliqueFinder;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.stereotype.Component;
import sun.security.validator.SimpleValidator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author eladcohen
 */
@Component
public class UndirectedGraphAlgorithm {


    public MutableNetwork<String, String> getMoralized(BayesianNetwork bayesianNetwork) {
        MutableNetwork<String, String> network = NetworkBuilder.directed()
                .allowsParallelEdges(false)
                .build();

        //convert to different model
        bayesianNetwork.getNodes().stream().forEach(node -> network.addNode(node.getNodeId()));

        //populate nodes parents
        bayesianNetwork.getEdges().stream().forEach(edge -> {
            val head = edge.getNodeId1().equals(edge.getNodeArrowHead()) ? edge.getNodeId1() : edge.getNodeId2();
            val node = edge.getNodeId1().equals(edge.getNodeArrowHead()) ? edge.getNodeId2() : edge.getNodeId1();
            network.addEdge(head, node, head + "_" + node);
        });

        Set<String> newEdges = new HashSet<>();

        network.nodes().stream().forEach(node -> {
            Set<String> predecessors = network.predecessors(node);
            final Set<String> leftPredecessors = new HashSet<>(predecessors);
            predecessors.stream().forEach(predecessor -> {
                leftPredecessors.stream().forEach(leftPredecessor -> {
                    if (!predecessor.equals(leftPredecessor)) {
                        network.addEdge(predecessor, leftPredecessor, predecessor + "_" + leftPredecessor);
                        newEdges.add(predecessor + "_" + leftPredecessor);
                    }
                });
                leftPredecessors.remove(predecessor);
            });
        });

        MutableNetwork<String, String> undirected = NetworkBuilder.undirected().allowsParallelEdges(false).build();
        network.nodes().stream().forEach(node -> {
            undirected.addNode(node);
            network.successors(node).stream().forEach(successor -> {
                String label = node + "_" + successor;
                undirected.addEdge(node, successor, node + "_" + successor
                        + (newEdges.contains(label) || newEdges.contains(successor + "_" + node) ? "_new" : ""));
            });
        });

        return undirected;
    }

    public boolean isChordal(BayesianNetwork bayesianNetwork) {
        val undirectedGraph = getMoralized(bayesianNetwork);
        SimpleDirectedGraph<String, String> simpleGraph = new SimpleDirectedGraph<>(String.class);
        undirectedGraph.nodes().stream().forEach(vertex -> simpleGraph.addVertex(vertex));
        undirectedGraph.edges().stream().forEach(edge -> {
            String[] edges = edge.split("_");
            simpleGraph.addEdge(edges[0], edges[1]);
        });

        AsUndirectedGraph<String, String> simpleUndirected = new AsUndirectedGraph<>(simpleGraph);
        val maxCliqueIterator = new DegeneracyBronKerboschCliqueFinder(simpleUndirected).iterator();
        while (maxCliqueIterator.hasNext())
            System.out.println(maxCliqueIterator.next());
//        maxCliqueIterator.forEachRemaining(System.out::println);
        return GraphTests.isChordal(simpleUndirected);
    }

    public LeanGraph eliminateNode(LeanGraph graph, String node2Eliminate) {
        //create undirected graph
        MutableNetwork<String, String> undirected = NetworkBuilder.undirected().allowsParallelEdges(false).build();
        graph.getNodeEdge().entrySet().stream().forEach(entry -> {
            undirected.addNode(entry.getKey());
            entry.getValue().stream().forEach(edge -> undirected.addEdge(edge.getNodeId1(), edge.getNodeId2(), edge.getNodeId1() + "_" + edge.getNodeId2()));
        });

        //eliminate the node
        Set<String> adjacentNodes = undirected.adjacentNodes(node2Eliminate);
        final Set<String> leftNodes = new HashSet<>(adjacentNodes);
        adjacentNodes.stream().forEach(node -> {
            leftNodes.stream().forEach(leftNode -> {
                if (!node.equals(leftNode)) {
                    if (undirected.edgeConnectingOrNull(node, leftNode) == null) {
                        undirected.addEdge(node, leftNode, node + "_" + leftNode);
                    }
                }
            });
            leftNodes.remove(node);
        });

        undirected.removeNode(node2Eliminate);

        LeanGraph afterElimination = new LeanGraph();
        undirected.nodes().stream().forEach(node -> {
            val nodeEdges = undirected.incidentEdges(node);
            afterElimination.getNodeEdge().put(node, nodeEdges.stream().map(edge -> {
                val edgeNodes = edge.split("_");
                return Edge.newBuilder().setNode1(edgeNodes[0]).setNode2(edgeNodes[1]).build();
            }).collect(Collectors.toList()));
        });

        return afterElimination;
    }

    public PerfectElimination findPerfectElimination(LeanGraph leanGraph) {
        //convert to simple graph
        SimpleDirectedGraph<String, String> simpleGraph = new SimpleDirectedGraph<>(String.class);
        leanGraph.getNodeEdge().entrySet().stream().forEach(entry -> {
            simpleGraph.addVertex(entry.getKey());
        });

        leanGraph.getNodeEdge().values().stream().forEach(edgeList -> {
            edgeList.forEach(edge -> simpleGraph.addEdge(edge.getNodeId1(), edge.getNodeId2()));
        });

        //try to find simplical node
        Optional<String> simplicalVertex = simpleGraph.vertexSet().stream().filter(node -> {
            Set<String> nodeNeighbors = Graphs.neighborSetOf(simpleGraph, node);
            SimpleGraph<String, String> newGraph = new SimpleGraph<>(String.class);
            nodeNeighbors.stream().forEach(neighborNode -> {
                newGraph.addVertex(neighborNode);
                nodeNeighbors.forEach(maybe -> {
                    if (simpleGraph.containsEdge(neighborNode, maybe)) {
                        newGraph.addEdge(neighborNode, maybe);
                    } else if (simpleGraph.containsEdge(maybe, neighborNode)) {
                        newGraph.addEdge(maybe, neighborNode);
                    }
                });
            });

            return new ChordalGraphMaxCliqueFinder(newGraph).getClique().size() == newGraph.vertexSet().size();
        }).findFirst();

        simplicalVertex.ifPresent(simpleGraph::removeVertex);

        LeanGraph afterElimination;

        if (simplicalVertex.isPresent()) {
            afterElimination = new LeanGraph();
            simpleGraph.vertexSet().forEach(node -> {
                afterElimination.getNodeEdge().put(node, new ArrayList<>());
                simpleGraph.vertexSet().forEach(neighbor -> {
                    if (simpleGraph.containsEdge(node, neighbor)) {
                        afterElimination.getNodeEdge().get(node).add(Edge.newBuilder().setNode1(node).setNode2(neighbor).build());
                    } else if (simpleGraph.containsEdge(neighbor, node)) {
                        afterElimination.getNodeEdge().get(node).add(Edge.newBuilder().setNode1(neighbor).setNode2(node).build());
                    }
                });
            });
        }else{
            afterElimination = leanGraph;
        }

        PerfectElimination.PerfectEliminationBuilder perfectElimination = PerfectElimination
                .builder()
                .leanGraph(afterElimination);

        simplicalVertex.ifPresent(perfectElimination::simplicalNode);

        return perfectElimination.build();
    }
}

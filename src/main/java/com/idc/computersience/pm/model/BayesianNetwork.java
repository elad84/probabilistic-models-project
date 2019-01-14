package com.idc.computersience.pm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author eladcohen
 */
@Data
@EqualsAndHashCode(exclude={"nodes", "edges"})
public class BayesianNetwork {
    private NetworkType type;
    private String name;
    private List<Node> nodes;
    private List<Edge> edges;
    private String fileName;

    public Collection<DepedencyEdge> findDependent(Node root, Set<String> observedNodes){
        val ancestorZ = new HashSet<Node>();
        val nodeEdgeMap = new HashMap<String, DepedencyEdge>();
        val nodesMap = nodes.stream().map(node -> {
            node.setVisited(false);
            return node;
        }).collect(Collectors.toMap(node -> node.getNodeId(), node -> node));

        val observedCopy = new ArrayList<String>();
        if(observedNodes != null){
            observedCopy.addAll(observedNodes);
        }
        //construct ancestor list of Z
        while(!observedCopy.isEmpty()){
            val nextObservedNode = nodesMap.get(observedCopy.remove(0));

            if(!ancestorZ.contains(nextObservedNode) && nextObservedNode.getParentList() != null){
                observedCopy.addAll(nextObservedNode.getParentList());
            }
            ancestorZ.add(nextObservedNode);
        }

        //create parent child list for each node to help in iteration
        edges.stream().forEach((Edge edge) -> {
            Node nodeParent = nodesMap.get(edge.getNodeArrowHead());
            String nodeChildId = edge.getNodeId1().equals(nodeParent.getNodeId()) ? edge.getNodeId2() : edge.getNodeId1();
            Node nodeChild = nodesMap.get(nodeChildId);
            if(nodeParent.getParentList() == null){
                nodeParent.setParentList(new ArrayList<>());
            }
            if(nodeParent.getChildrenList() == null){
                nodeParent.setChildrenList(new ArrayList<>());
            }

            nodeParent.getChildrenList().add(nodeChildId);
            nodeChild.getParentList().add(nodeParent.getNodeId());
            nodeEdgeMap.put(nodeParent.getNodeId() + "_" + nodeChildId, new DepedencyEdge(edge));
        });

        dfsVisit(root, Direction.START, ancestorZ, nodesMap, nodeEdgeMap);

        return nodeEdgeMap.values();
    }

    private void dfsVisit(Node node, Direction direction, Set<Node> ancestorZ, Map<String, Node> nodesMap, Map<String, DepedencyEdge> nodeEdgeMap){
        if(node.isVisited()){
            return;
        }

        node.setVisited(true);

        boolean shouldVisitChildern = false;
        boolean shouldVisitParents = false;

        //only check non collides
        // x -> w -> y
        // x <- w <- y
        // x <- w -> y
        if(Direction.START.equals(direction) || ancestorZ == null || !ancestorZ.contains(node)){
            shouldVisitChildern = true;
        }

        if(node.getChildrenList() != null){
            for (String s : node.getChildrenList()) {
                if(shouldVisitChildern){
                    val edge = nodeEdgeMap.get(s + "_" + node.getNodeId());
                    if(edge != null){
                        edge.setCategory(DconnectivityCategory.CONNECTED);
                    }
                    dfsVisit(nodesMap.get(s), Direction.IN, ancestorZ, nodesMap, nodeEdgeMap);
                }else{
                    nodeEdgeMap.get(node.getNodeId() + "_" + s).setCategory(DconnectivityCategory.BLOCKED_NON_COLLIDER);
                }
            }
        }

        if(Direction.START.equals(direction) ||
                (Direction.IN.equals(direction) && (ancestorZ == null || ancestorZ.contains(node))) || //collider
                (Direction.OUT.equals(direction) && (ancestorZ == null || !ancestorZ.contains(node)))){ //non collider
            shouldVisitParents = true;

        }

        if(node.getParentList() != null){
            for (String s : node.getParentList()) {
                if(shouldVisitParents){
                    nodeEdgeMap.get(s + "_" + node.getNodeId()).setCategory(DconnectivityCategory.CONNECTED);
                    dfsVisit(nodesMap.get(s), Direction.OUT, ancestorZ, nodesMap, nodeEdgeMap);
                }else{
                    if(Direction.IN.equals(direction)){
                        nodeEdgeMap.get(s + "_" + node.getNodeId()).setCategory(DconnectivityCategory.BLOCKED_COLLIDER);
                    }else {
                        nodeEdgeMap.get(s + "_" + node.getNodeId()).setCategory(DconnectivityCategory.BLOCKED_NON_COLLIDER);
                    }
                }
            }
        }
    }


}

package com.idc.computersience.pm.model.reader;

import com.google.common.collect.ImmutableSet;
import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.DepedencyEdge;
import com.idc.computersience.pm.model.Node;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author eladcohen
 */
@Ignore
@Slf4j
public class BayesianNetworkTest {


    private NetworkReader networkReader = new JsonNetworkReader();

    @Test
    public void testDependency() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/network.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, null));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("2")).findFirst();

        Assert.assertTrue("dependent node should be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testDependencyParentObserved() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/network.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        observedNodes.add("2");

        Collection<DepedencyEdge> edgeCollection = null;
        if(root.isPresent()){
            edgeCollection = bayesianNetwork.findDependent(root.get(), observedNodes);
        }
//        val edgeClassification = root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        System.out.println(edgeCollection);

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("2")).findFirst();

        Assert.assertFalse("dependent node should not be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testDependencyNoObserved() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/conditional_independent.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, null));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertTrue("dependent node should be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testConditionalIndependence() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/conditional_independent.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        observedNodes.add("2");
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertFalse("dependent node should not be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }


    @Test
    public void testCollider() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/collider.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        observedNodes.add("2");
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertTrue("dependent node should be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testColliderNoObserved() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/collider.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertFalse("dependent node should not be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testCollideObservedDescendant() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/collider_descendant.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        observedNodes.add("4");
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertTrue("dependent node should be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testCollideDescendantNotObserved() throws IOException {
        File file = new File("/Users/eladcohen/Documents/idc/study/probabilistic models/networks/collider_descendant.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("3")).findFirst();
        val observedNodes = new HashSet<String>();
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();

        Assert.assertFalse("dependent node should not be visited but was: " + dependentNode.get().isVisited(), dependentNode.get().isVisited());
    }

    @Test
    public void testCondition() throws IOException {
        File file = new File("src/test/resource/Transport.txt");
        BayesianNetwork bayesianNetwork = networkReader.read(file.getAbsolutePath());
        Optional<Node> root = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("1")).findFirst();
        val observedNodes = ImmutableSet.of("2");
        root.ifPresent(rootNode -> bayesianNetwork.findDependent(rootNode, observedNodes));

        val dependentNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals("5")).findFirst();
        val nodeMap = bayesianNetwork.getNodes().stream().collect(Collectors.toMap(Node::getNodeId, Function.identity()));

        Assert.assertTrue("dependent node should be visited but was not", nodeMap.get("2").isVisited());
        Assert.assertTrue("dependent node should be visited but was not", nodeMap.get("3").isVisited());
        Assert.assertTrue("dependent node should be visited but was not", nodeMap.get("4").isVisited());
        Assert.assertTrue("dependent node should be visited but was not", nodeMap.get("6").isVisited());
        Assert.assertFalse("dependent node should not be visited but was", nodeMap.get("5").isVisited());
    }
}

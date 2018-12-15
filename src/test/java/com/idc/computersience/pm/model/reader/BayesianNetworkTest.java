package com.idc.computersience.pm.model.reader;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.DepedencyEdge;
import com.idc.computersience.pm.model.Node;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author eladcohen
 */
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
}

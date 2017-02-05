package com.idc.computersience.pm.network;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Edge;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.reader.NodeReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author eladcohen
 */
@Slf4j
@Component
public class NetworkGenerator {

    private NodeReader nodeReader;
    private NetworkCache networkCache;

    @Autowired
    public NetworkGenerator(NodeReader nodeReader, NetworkCache networkCache){
        this.nodeReader = nodeReader;
        this.networkCache = networkCache;
    }


    public List<Node> readNetwork(String networkName, String networkPath) throws IOException {
        try {
            List<Node> nodes = nodeReader.readNodes(networkPath);
            Set<Edge> edges = new HashSet<>();

            //create edges
            for(Node node : nodes){
                if(node.getDependencyList() != null && !node.getDependencyList().isEmpty()){
                    for(int dependency : node.getDependencyList()){
                        Edge edge = Edge.newBuilder()
                                .setNode1(node.getNodeId())
                                .setNode2(dependency)
                                .setNodeArrowHead(node.getNodeId())
                                .build();
                        edges.add(edge);
                    }
                }
            }

            BayesianNetwork bayesianNetwork = new BayesianNetwork();
            bayesianNetwork.setNetworkName(networkName);
            bayesianNetwork.setNodes(nodes);
            bayesianNetwork.setEdges(new ArrayList<>(edges));

            String networkId = networkCache.addNetwork(bayesianNetwork);

            return nodes;
        }catch (Exception e){
            log.error("failed to read network " + networkName + " from path " +networkPath, e);
            throw e;
        }
    }

}

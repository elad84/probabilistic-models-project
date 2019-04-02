package com.idc.computersience.pm.helper;

import com.idc.computersience.pm.cache.PathChooser;
import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.condition.BinaryMembership;
import com.idc.computersience.pm.model.condition.PotentialFunction;
import com.idc.computersience.pm.model.reader.NetworkReader;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author eladcohen
 */
@Component
public class BayesianNetworkHelper {

    private final NetworkReader networkReader;
    private final PathChooser pathChooser;

    public BayesianNetworkHelper(NetworkReader networkReader, PathChooser pathChooser) {
        this.networkReader = networkReader;
        this.pathChooser = pathChooser;
    }

    public void updateProbabilityTables(String networkName, String tablesFileName) throws IOException {
        BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + networkName);

    }


    public BinaryMembership toBinaryMembership(String networkName) throws IOException {
        BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + networkName);
        BinaryMembership binaryMembership = new BinaryMembership();
        //initiate th binary membership
        bayesianNetwork.getNodes().stream().forEach(node -> {
            PotentialFunction potentialFunction = new PotentialFunction(node.getNodeId());

            //add parents to nodes in the potential function
            if(node.getParentList() != null && !node.getParentList().isEmpty()){
                node.getParentList().forEach(potentialFunction::addNode);
            }

            binaryMembership.addMembership(node.getNodeId(), potentialFunction);
        });

        return binaryMembership;
    }
}

package com.idc.computersience.pm.helper;

import com.idc.computersience.pm.cache.PathChooser;
import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.reader.NetworkReader;

import java.io.IOException;
import java.util.List;

/**
 * @author eladcohen
 */
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
}

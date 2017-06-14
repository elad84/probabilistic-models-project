package com.idc.computersience.pm.model.writer;

import com.idc.computersience.pm.model.BayesianNetwork;

import java.io.IOException;

/**
 * @author eladcohen
 */
public interface NetworkWriter {

    void writeNetwork(String path, BayesianNetwork bayesianNetwork) throws IOException;
}

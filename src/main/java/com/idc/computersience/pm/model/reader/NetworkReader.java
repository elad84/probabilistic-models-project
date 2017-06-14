package com.idc.computersience.pm.model.reader;

import com.idc.computersience.pm.model.BayesianNetwork;

import java.io.IOException;

/**
 * @author eladcohen
 */
public interface NetworkReader {

    BayesianNetwork read(String path) throws IOException;
}

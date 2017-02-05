package com.idc.computersience.pm.model.reader;

import com.idc.computersience.pm.model.Node;

import java.io.IOException;
import java.util.List;

/**
 * Reader to create {@link Node} list from a file
 *
 * @author eladcohen
 */
public interface NodeReader {

    /**
     * Reads the given file and converts it to a list of {@link Node}
     *
     * @param path path to a file contains the list of nodes
     *
     * @return a list of nodes
     * @throws IOException when file cannot be accessed
     */
    List<Node> readNodes(String path) throws IOException;

}

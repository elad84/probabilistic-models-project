package com.idc.computersience.pm.controller.model.undirected;

import com.idc.computersience.pm.model.Edge;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eladcohen
 */
@Getter
public class LeanGraph {
    private Map<String, List<Edge>> nodeEdge = new HashMap<>();
}

package com.idc.computersience.pm.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author eladcohen
 */
@Data
@EqualsAndHashCode(exclude={"nodes", "edges"})
public class BayesianNetwork {
    private String networkName;
    private List<Node> nodes;
    private List<Edge> edges;
}

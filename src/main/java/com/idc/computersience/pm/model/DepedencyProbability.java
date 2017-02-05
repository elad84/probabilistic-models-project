package com.idc.computersience.pm.model;

import lombok.Data;

import java.util.List;

/**
 * Represent a single probability of dependency
 *
 * @author eladcohen
 */
@Data
public class DepedencyProbability {
    private List<DependencyNode> dependencyNodes;
    private double probability;
}

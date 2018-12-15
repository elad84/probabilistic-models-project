package com.idc.computersience.pm.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author eladcohen
 */
@Setter
@Getter
public class BayesianDependency {
    private String fileName;
    private String rootNodeId;
    private List<String> observedNodes;
}

package com.idc.computersience.pm.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idc.computersience.pm.model.BayesianNetwork;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author eladcohen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class BayesianDependency {
    private String fileName;
    private String rootNodeId;
    private List<String> observedNodes;
    private BayesianNetwork network;
}

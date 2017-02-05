package com.idc.computersience.pm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Represent a dependecy for one value of a node
 *
 * @author eladcohen
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NodeValueConditionalProb {
    private int nodeValue;
    private double probability;
    private List<DepedencyProbability> dependency;
}

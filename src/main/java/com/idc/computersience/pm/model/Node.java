package com.idc.computersience.pm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author eladcohen
 */
@Getter
@Setter
public class Node {
    private Integer nodeId;
    private String displayName;
    private List<Double> domain;
    private List<Integer> parentList;
    private List<NodeValueConditionalProb> conditionalTable;
}

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
    private boolean controlNode = false;
    private String displayName;
    private List<NodeDomain> domain;
    private List<Integer> parentList;
    private List<NodeValueConditionalProb> conditionalTable;
}

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
    private String nodeId;
    private boolean controlNode = false;
    private String displayName;
    private List<NodeDomain> domain;
    private List<String> parentList;
    private List<NodeValueConditionalProb> conditionalTable;
}

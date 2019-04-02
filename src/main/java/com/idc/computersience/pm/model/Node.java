package com.idc.computersience.pm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eladcohen
 */
@ToString
@EqualsAndHashCode(of = {"nodeId"})
@Getter
@Setter
public class Node {
    private String nodeId;
    private boolean controlNode = false;
    private String displayName;
    private List<NodeDomain> domain;
    private List<String> parentList = new ArrayList<>();
    @JsonIgnore
    private List<String> childrenList;
    @JsonIgnore
    private boolean visited;
    private List<NodeValueConditionalProb> conditionalTable;
}

package com.idc.computersience.pm.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idc.computersience.pm.model.NodeDomain;
import com.idc.computersience.pm.model.NodeValueConditionalProb;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author eladcohen
 */
@ToString
@Getter
@Setter
public class NodeDto {
    private String nodeId;
    private boolean controlNode = false;
    private String displayName;
    private List<NodeDomain> domain;
    private boolean visited;
    private List<NodeValueConditionalProb> conditionalTable;
}

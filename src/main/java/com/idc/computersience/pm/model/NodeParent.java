package com.idc.computersience.pm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author eladcohen
 */
@ToString
@Getter
@Setter
public class NodeParent {

    private ParentType type;
    private int nodeId;
}

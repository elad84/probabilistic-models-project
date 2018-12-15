package com.idc.computersience.pm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author eladcohen
 */
@ToString
@Setter
@Getter
public class DepedencyEdge {
    private Edge edge;
    private DconnectivityCategory category;

    public DepedencyEdge(Edge edge) {
        this.edge = edge;
    }
}

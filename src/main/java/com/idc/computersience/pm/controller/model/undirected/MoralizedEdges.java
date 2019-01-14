package com.idc.computersience.pm.controller.model.undirected;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/**
 * @author eladcohen
 */
@Getter
@Builder
public class MoralizedEdges {

    private Set<String> edges;
}

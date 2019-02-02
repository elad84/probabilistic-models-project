package com.idc.computersience.pm.controller.model.undirected;

import lombok.Builder;
import lombok.Getter;

/**
 * @author eladcohen
 */
@Getter
@Builder(toBuilder = true)
public class PerfectElimination {

    private String simplicalNode;
    private LeanGraph leanGraph;
}

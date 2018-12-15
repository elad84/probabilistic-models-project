package com.idc.computersience.pm.controller.model;

import com.idc.computersience.pm.model.NetworkType;
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
public class BayesianNetworkDto {
    private NetworkType type;
    private String name;
    private List<NodeDto> nodes;
    private List<EdgeDto> edges;
}

package com.idc.computersience.pm.controller;

import com.idc.computersience.pm.model.BayesianNetwork;
import lombok.Getter;
import lombok.Setter;

/**
 * @author eladcohen
 */
@Getter
@Setter
public class BayesianCreateRequest {

    private String filePath;
    private BayesianNetwork network;
}

package com.idc.computersience.pm.model;

import com.idc.computersience.pm.model.condition.Probability;
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
public class NodeDomain {
    private Double value;
    private String name;
    private List<Probability> probabilities;
}

package com.idc.computersience.pm.controller.model;

import com.idc.computersience.pm.model.DconnectivityCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author eladcohen
 */
@ToString
@Setter
@Getter
public class EdgeDto {
    private String nodeId1;
    private String nodeId2;
    private String nodeArrowHead;
    private DconnectivityCategory category;
}

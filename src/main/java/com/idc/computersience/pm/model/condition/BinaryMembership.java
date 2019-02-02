package com.idc.computersience.pm.model.condition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author eladcohen
 */
public class BinaryMembership {

    private Map<String, Set<PotentialFunction>> membership = new HashMap<>();

    public void addMembership(String nodeId, PotentialFunction potentialFunction) {
        Set<PotentialFunction> existingMembership = membership.computeIfAbsent(nodeId, k -> new HashSet<>());
        existingMembership.add(potentialFunction);
    }
}

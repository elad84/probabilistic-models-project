package com.idc.computersience.pm.network;

import com.idc.computersience.pm.cache.ManualCache;
import com.idc.computersience.pm.model.BayesianNetwork;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Local cache holding network
 *
 * @author eladcohen
 */
@Component
public class NetworkCache {

    private ManualCache<String, BayesianNetwork> networkCache = new ManualCache<>();


    public String addNetwork(BayesianNetwork bayesianNetwork){
        String networkId = UUID.randomUUID().toString();
        networkCache.put(networkId, bayesianNetwork);
        return networkId;
    }

    public BayesianNetwork getNetwork(String networkId){
        return networkCache.get(networkId);
    }
}

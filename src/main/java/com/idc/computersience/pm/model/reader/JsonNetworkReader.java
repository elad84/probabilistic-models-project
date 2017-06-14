package com.idc.computersience.pm.model.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idc.computersience.pm.model.BayesianNetwork;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author eladcohen
 */
@Component
public class JsonNetworkReader implements NetworkReader{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public BayesianNetwork read(String path) throws IOException {
        return mapper.readValue(new File(path), BayesianNetwork.class);
    }
}

package com.idc.computersience.pm.model.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idc.computersience.pm.model.BayesianNetwork;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author eladcohen
 */
@Component
public class JsonNetworkWriter implements NetworkWriter{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void writeNetwork(String path, BayesianNetwork bayesianNetwork) throws IOException {
        mapper.writeValue(new File(path), bayesianNetwork);
    }
}

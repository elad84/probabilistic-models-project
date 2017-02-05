package com.idc.computersience.pm.model.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.idc.computersience.pm.model.Node;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Reader to read data out of YAML files
 *
 * @author eladcohen
 */
public class YamlNodeReader implements NodeReader {

    public ObjectMapper mapper = new ObjectMapper(new YAMLFactory());


    @Override
    public List<Node> readNodes(String path) throws IOException {
        return mapper.readValue(new File(path), new TypeReference<List<Node>>(){});
    }
}

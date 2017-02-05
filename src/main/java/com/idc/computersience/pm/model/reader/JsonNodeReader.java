package com.idc.computersience.pm.model.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idc.computersience.pm.model.Node;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author eladcohen
 */
@Component
public class JsonNodeReader implements NodeReader {

    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<Node> readNodes(String path) throws IOException {
        return mapper.readValue(new File(path),  new TypeReference<List<Node>>(){});
    }
}

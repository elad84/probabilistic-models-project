package com.idc.computersience.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.reader.NodeReader;

import java.io.IOException;
import java.util.List;

/**
 * @author eladcohen
 */
@Controller
public class BayesianNetworkService {

    @Autowired
    private NodeReader nodeReader;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST, value = "/bayesian/network")
    public @ResponseBody List<Node> createBayesianNetwork(@RequestBody BayesianCreateRequest createRequest){
        List<Node> nodes = null;
        try {
            nodes = nodeReader.readNodes(createRequest.getFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }


}

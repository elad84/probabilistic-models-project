package com.idc.computersience.pm.service;

import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.reader.NetworkReader;
import com.idc.computersience.pm.model.writer.NetworkWriter;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private NetworkWriter networkWriter;

    @Autowired
    private NetworkReader networkReader;

    @Value("${app.base.network}")
    private String baseDir;

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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/bayesian/network/load")
    public @ResponseBody ResponseEntity<BayesianNetwork> loadNetwork(@RequestParam(value = "name") String path){
        ResponseEntity<BayesianNetwork> response;
        try{
            BayesianNetwork bayesianNetwork = networkReader.read(baseDir + path);
            response = new ResponseEntity<>(bayesianNetwork, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/bayesian/network/create")
    public @ResponseBody ResponseEntity<Boolean> saveNetwork(@RequestBody BayesianNetwork bayesianNetwork){
        try {
            networkWriter.writeNetwork(baseDir + bayesianNetwork.getNetworkName(), bayesianNetwork);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

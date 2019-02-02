package com.idc.computersience.pm.controller;

import com.idc.computersience.pm.algorithms.UndirectedGraphAlgorithm;
import com.idc.computersience.pm.cache.PathChooser;
import com.idc.computersience.pm.controller.model.BayesianNetworkDto;
import com.idc.computersience.pm.controller.model.EdgeDto;
import com.idc.computersience.pm.controller.model.NodeDto;
import com.idc.computersience.pm.controller.model.undirected.LeanGraph;
import com.idc.computersience.pm.controller.model.undirected.MoralizedEdges;
import com.idc.computersience.pm.model.BayesianNetwork;
import com.idc.computersience.pm.model.Node;
import com.idc.computersience.pm.model.reader.NetworkReader;
import com.idc.computersience.pm.model.reader.NodeReader;
import com.idc.computersience.pm.model.writer.NetworkWriter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author eladcohen
 */
@Slf4j
@CrossOrigin
@Controller
public class BayesianNetworkController {

    private final NodeReader nodeReader;

    private final NetworkWriter networkWriter;

    private final NetworkReader networkReader;

    private final PathChooser pathChooser;

    private final UndirectedGraphAlgorithm undirectedGraphAlgorithm;

    public BayesianNetworkController(NodeReader nodeReader, NetworkWriter networkWriter, NetworkReader networkReader, PathChooser pathChooser, UndirectedGraphAlgorithm undirectedGraphAlgorithm) {
        this.nodeReader = nodeReader;
        this.networkWriter = networkWriter;
        this.networkReader = networkReader;
        this.pathChooser = pathChooser;
        this.undirectedGraphAlgorithm = undirectedGraphAlgorithm;
    }
    //    @Value("${app.base.network}")
//    private String baseDir;

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
            BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + path);
            bayesianNetwork.setFileName(path);
            response = new ResponseEntity<>(bayesianNetwork, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/bayesian/network/create")
    public @ResponseBody ResponseEntity<Boolean> saveNetwork(@RequestBody BayesianCreateRequest bayesianCreateRequest){
        try {
                networkWriter.writeNetwork(pathChooser.getNetworkHomeDirectory() + bayesianCreateRequest.getFilePath(), bayesianCreateRequest.getNetwork());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/bayesian/choose")
    public @ResponseBody ResponseEntity<Boolean> openFileChooser(){
        pathChooser.choseFile();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/bayesian/dependency/check")
    public @ResponseBody ResponseEntity<BayesianNetworkDto> checkDependency(@RequestBody BayesianDependency bayesianDependency) throws IOException {
        BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + bayesianDependency.getFileName());
        Optional<Node> rootNode = bayesianNetwork.getNodes().stream().filter(node -> node.getNodeId().equals(bayesianDependency.getRootNodeId())).findFirst();
        if(!rootNode.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        val edges = bayesianNetwork.findDependent(rootNode.get(), bayesianDependency.getObservedNodes() != null ? new HashSet<>(bayesianDependency.getObservedNodes()) : null);
        val bayesianNetworkDto = new BayesianNetworkDto();
        val edgesDto = edges.stream().map(edge -> {
            EdgeDto edgeDto = new EdgeDto();
            edgeDto.setNodeId1(edge.getEdge().getNodeId1());
            edgeDto.setNodeId2(edge.getEdge().getNodeId2());
            edgeDto.setNodeArrowHead(edge.getEdge().getNodeArrowHead());
            edgeDto.setCategory(edge.getCategory());
            return edgeDto;
        }).collect(Collectors.toList());

        val nodes = bayesianNetwork.getNodes().stream().map(node -> {
            NodeDto nodeDto = new NodeDto();
            nodeDto.setNodeId(node.getNodeId());
            nodeDto.setDisplayName(node.getDisplayName());
            nodeDto.setControlNode(node.isControlNode());
            nodeDto.setConditionalTable(node.getConditionalTable());
            nodeDto.setDomain(node.getDomain());
            nodeDto.setVisited(node.isVisited());
            return nodeDto;
        }).collect(Collectors.toList());

        bayesianNetworkDto.setName(bayesianNetwork.getName());
        bayesianNetworkDto.setType(bayesianNetwork.getType());
        bayesianNetworkDto.setNodes(nodes);
        bayesianNetworkDto.setEdges(edgesDto);
        return new ResponseEntity<>(bayesianNetworkDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bayesian/moralized")
    public @ResponseBody ResponseEntity<MoralizedEdges> toMoralized(@RequestParam(value = "name") String path){
        try{
            BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + path.toLowerCase());
            val grpah = undirectedGraphAlgorithm.getMoralized(bayesianNetwork);
            return new ResponseEntity<>(MoralizedEdges.builder().edges(grpah.edges()).build(), HttpStatus.OK);
        }catch(Exception e){
            log.error("failed to convert bayesian network to moralized", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/bayesian/chordal")
    public @ResponseBody ResponseEntity<Boolean> isChordal(@RequestParam(value = "name") String path){
        try{
            BayesianNetwork bayesianNetwork = networkReader.read(pathChooser.getNetworkHomeDirectory() + path);
            return new ResponseEntity<>(undirectedGraphAlgorithm.isChordal(bayesianNetwork), HttpStatus.OK);
        }catch(Exception e){
            log.error("failed to convert bayesian network to moralized", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/undirected/eliminate/{node2Eliminate}")
    public @ResponseBody ResponseEntity<LeanGraph> eliminateNode(@RequestBody LeanGraph graph, @PathVariable("node2Eliminate") String node2Eliminate) {
        try{
            return new ResponseEntity<>(undirectedGraphAlgorithm.eliminateNode(graph, node2Eliminate), HttpStatus.OK);
        }catch (Exception e){
            log.error("failed to eliminate node", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

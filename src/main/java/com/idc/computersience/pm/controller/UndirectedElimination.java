package com.idc.computersience.pm.controller;

import com.idc.computersience.pm.algorithms.UndirectedGraphAlgorithm;
import com.idc.computersience.pm.controller.model.undirected.LeanGraph;
import com.idc.computersience.pm.controller.model.undirected.PerfectElimination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author eladcohen
 */
@Slf4j
@CrossOrigin
@Controller
public class UndirectedElimination {

    private final UndirectedGraphAlgorithm undirectedGraphAlgorithm;

    public UndirectedElimination(UndirectedGraphAlgorithm undirectedGraphAlgorithm) {
        this.undirectedGraphAlgorithm = undirectedGraphAlgorithm;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/undirected/elimination/prefect")
    public @ResponseBody
    ResponseEntity<PerfectElimination> findPerfectElimination(@RequestBody LeanGraph graph) {
        try{
            return new ResponseEntity<>(undirectedGraphAlgorithm.findPerfectElimination(graph), HttpStatus.OK);
        }catch (Exception e) {
            log.error("failed to find perfect elimination", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

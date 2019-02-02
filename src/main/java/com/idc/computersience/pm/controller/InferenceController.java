package com.idc.computersience.pm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author eladcohen
 */
@Slf4j
@Controller
public class InferenceController {

    public @ResponseBody ResponseEntity<Boolean> loadProbalityTables(@RequestParam String networkName, @RequestParam String fileName) {
        try{


            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            log.error("failed to update network with probability tables", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

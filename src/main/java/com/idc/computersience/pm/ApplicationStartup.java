package com.idc.computersience.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author eladcohen
 */
@SpringBootApplication
public class ApplicationStartup {

    public static void main(String[] args) throws Exception{
        ApplicationContext ctx = SpringApplication.run(ApplicationStartup.class, args);
    }
}

package com.idc.computersience.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

/**
 * @author eladcohen
 */
@SpringBootApplication
public class ApplicationStartup {

    public static void main(String[] args) throws Exception{
        ApplicationContext ctx = SpringApplication.run(ApplicationStartup.class, args);

        String url = "http://localhost:8888";
        if(Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(url));
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                String Command="open "+url;
                Process Child=Runtime.getRuntime().exec(Command);
            }
        }
    }
}

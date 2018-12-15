package com.idc.computersience.pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

/**
 * @author eladcohen
 */
@SpringBootApplication
public class ApplicationStartup {

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationStartup.class).headless(false).run(args);
//        ApplicationContext ctx = SpringApplication.run(ApplicationStartup.class, args);

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Choose the network folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }

        String url = "http://localhost:8091";
//        String url = "http://localhost:3002";
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

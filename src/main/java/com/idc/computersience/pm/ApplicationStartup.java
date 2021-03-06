package com.idc.computersience.pm;

import com.idc.computersience.pm.cache.PathChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * @author eladcohen
 */
@Slf4j
@SpringBootApplication
public class ApplicationStartup {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ApplicationStartup.class, args);
        } catch (Exception e) {
            log.error("Spring application exited with an error");
            System.exit(15);
        }
//        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationStartup.class).headless(false).run(args);

//        JFileChooser chooser = new JFileChooser();
//        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
//        chooser.setDialogTitle("Choose the network folder");
//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        chooser.setAcceptAllFileFilterUsed(false);
//
//        JFrame frame = new JFrame("JFrame Example");
//
//        JOptionPane.showMessageDialog(frame, "Please choose the root folder for save/load networks");
//
//        int returnVal = chooser.showOpenDialog(frame);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            System.out.println("getCurrentDirectory(): "
//                    +  chooser.getCurrentDirectory());
//            System.out.println("getSelectedFile() : "
//                    +  chooser.getSelectedFile());
//            PathChooser chooserBean = context.getBean(PathChooser.class);
//            chooserBean.setNetworkHomeDirectory(chooser.getSelectedFile().getAbsolutePath() + File.separator);
//            System.setProperty("network.path", chooser.getSelectedFile().getAbsolutePath() + File.separator);
//        }

//        String url = "http://localhost:8080";
////        String url = "http://localhost:3002";
//        if(Desktop.isDesktopSupported()) {
//            Desktop.getDesktop().browse(new URI(url));
//        }else{
//            Runtime runtime = Runtime.getRuntime();
//            try {
//                runtime.exec("xdg-open " + url);
//            } catch (IOException e) {
//                String Command="open "+url;
//                Process Child=Runtime.getRuntime().exec(Command);
//            }
//        }
    }
}

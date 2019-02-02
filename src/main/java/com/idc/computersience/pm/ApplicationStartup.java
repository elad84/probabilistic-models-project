package com.idc.computersience.pm;

import com.idc.computersience.pm.cache.PathChooser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
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
@SpringBootApplication
public class ApplicationStartup {

    public static void main(String[] args) throws Exception{
        ConfigurableApplicationContext context = new SpringApplicationBuilder(ApplicationStartup.class).headless(false).run(args);

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
            PathChooser chooserBean = context.getBean(PathChooser.class);
            chooserBean.setNetworkHomeDirectory(chooser.getSelectedFile().getAbsolutePath() + File.separator);
            System.setProperty("network.path", chooser.getSelectedFile().getAbsolutePath() + File.separator);
        }

        String url = "http://localhost:8080";
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

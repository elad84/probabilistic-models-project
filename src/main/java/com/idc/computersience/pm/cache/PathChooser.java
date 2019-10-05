package com.idc.computersience.pm.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 * @author eladcohen
 */
@Component
public class PathChooser {

//    private JFrame frame = new JFrame("JFrame Example");

//    private JFileChooser chooser = new JFileChooser();

    @Getter
    @Setter
    private String networkHomeDirectory;

    @PostConstruct
    public void post(){
//        chooser.setCurrentDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
//        chooser.setDialogTitle("Choose the network folder");
//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        chooser.setAcceptAllFileFilterUsed(false);
    }


    public void choseFile(){
//        frame.toFront();
//        frame.repaint();
//        int returnVal = chooser.showOpenDialog(frame);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            System.out.println("getCurrentDirectory(): "
//                    +  chooser.getCurrentDirectory());
//            System.out.println("getSelectedFile() : "
//                    +  chooser.getSelectedFile());
//        }
    }

}

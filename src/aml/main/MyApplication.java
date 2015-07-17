/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.global.Config;
import aml.graph.Network;
import aml.jade.MyPlatformManager;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author ddefalco
 */
public class MyApplication {
    private int counter = 0;
    private double step = 0.1;
    
    public MyApplication(int index){       
        Network graph = new Network("AML Synthetic DB");
        if (Config.instance().isGuiEnabled()) {
            enableGUI(graph);
        }
        graph.build();
        double p1 = Config.instance().getParentProbability() + (step * index);
        Config.instance().setParentProbability(p1);
        MyPlatformManager f = new MyPlatformManager(graph);
        f.exec();
    }
        
    /**
     * if GUI is enabled then graph and system.output are rendered in two frame
     */
    private void enableGUI(Network graph) {
        graph.display(true);

        JFrame myFrame = new JFrame("SystemMessages");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(700, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        PrintStream printStream = new PrintStream(new MyOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        myFrame.getContentPane().add(scroll);
        myFrame.setVisible(true);
    }    
}

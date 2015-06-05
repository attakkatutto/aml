/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.agent.JadeManager;
import aml.global.Config;
import aml.graph.Network;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import org.graphstream.graph.Graph;

/**
 * Main class for the network
 *
 * @author ddefalco
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Network("AML Test");

        guiInit(graph);

        JadeManager f = new JadeManager(graph);
        f.exec();
    }

    /*
    * if GUI is enabled then graph and system.output are rendered
    * in a frame
    */
    private static void guiInit(Graph graph) {
        if (Config.instance().isGuiEnabled()) {
            graph.display(true);

            JFrame myFrame = new JFrame("SystemMessages");
            myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myFrame.setSize(700, 300);

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
}

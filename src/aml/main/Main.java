/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.agent.JadeManager;
import aml.graph.Network;
import org.graphstream.graph.Graph;

/**
 * Test class for the network
 *
 * @author ddefalco
 */
public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Network("AML Test");
        graph.display(true);
        JadeManager f = new JadeManager(graph);               
        f.exec();
    }
}

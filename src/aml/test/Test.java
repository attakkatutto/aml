/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.test;

import static aml.global.Constant.*;
import aml.graph.Network;
import org.graphstream.graph.*;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;

/**
 * Test class for the network
 *
 * @author ddefalco
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Graph graph = new Network("AML Test");

        graph.addAttribute("ui.stylesheet",
                "node {fill-color: red; size-mode: dyn-size;} edge {fill-color:lightgrey;}");
        graph.display(true);

        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(MAX_TRANSACTIONS_PER_ENTITIES, false);
        b.setDirectedEdges(true, true);
        b.addSink(graph);
        b.begin();       

        while (graph.getNodeCount() < MAX_NUMBER_ENTITIES) {
            b.nextEvents();    
            for (Node node : graph) {
                node.addAttribute("ui.label", String.format("%s", node.getId()));  
            }
            Thread.sleep(100);
        }

        b.end();
    }
}

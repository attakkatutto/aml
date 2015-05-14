/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.test;

import aml.global.Config;
import aml.agent.Jade;
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
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new Network("AML Test");
        graph.setAttribute("stylesheet", "node { "                
                + "     padding: 6px; "
                + "     fill-color: white; "
                + "     stroke-mode: plain; "
                + "     size-mode: fit; "
                + "} "
                + "     node.person {  "
                + "     shape:rounded-box; } "
                + "     node.company {   "
                + "     shape:circle; }  "
                + "edge { "
                + "     shape: freeplane; fill-color:lightgrey; "
                + "}");        
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        graph.display(true);

        Jade f = new Jade(graph);

        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(Config.getInstance().getMaxTransactionsPerEntity(),
                false);
        b.setDirectedEdges(true, true);
        b.addSink(graph);
        b.begin();

        while (graph.getNodeCount() < Config.getInstance().getMaxNumberOfEntity()) {
            b.nextEvents();
            for (Node node : graph) {
                node.addAttribute("ui.label", String.format("%s", node.getId()));
            }
            Thread.sleep(100);
        }

        f.startAgents();
        b.end();
    }
}

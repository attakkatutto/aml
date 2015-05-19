/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.test;

import aml.global.Config;
import aml.agent.Jade;
import static aml.global.Enums.NodeType.EMPLOYEE;
import static aml.global.Enums.NodeType.FREELANCE;
import aml.graph.MyNode;
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

        graph.display(true);

        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(Config.getInstance().getMaxEdgesPerEntity(),
                false);
        b.setDirectedEdges(true, true);
        b.addSink(graph);
        b.begin();

        while (graph.getNodeCount() < Config.getInstance().getMaxNumberOfEntity()) {
            b.nextEvents();
            for (Node node : graph) {
                node.addAttribute("ui.label", String.format("%s", node.getId()));
                if (((MyNode) node).getType() == EMPLOYEE
                        || ((MyNode) node).getType() == FREELANCE) {
                    node.addAttribute("ui.class", "person");
                } else {
                    node.addAttribute("ui.class", "company");
                }
            }
            Thread.sleep(500);
        }

        b.end();
        
        Jade f = new Jade(graph);
        f.startAgents();
    }
}

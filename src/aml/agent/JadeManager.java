/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.global.Config;
import static aml.global.Enums.NodeType.EMPLOYEE;
import static aml.global.Enums.NodeType.FREELANCE;
import aml.graph.Network;
import aml.graph.MyNode;
import aml.graph.PageRank;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.PlatformEvent;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public class JadeManager {

    private final AgentContainer mainContainer;
    private final Graph graph;
    private final PageRank pageRank;

    public JadeManager() {
        this.graph = new Network("AML Test");
        this.pageRank = new PageRank();
        this.pageRank.init(graph);
        this.graph.display(true);
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        mainContainer = Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        //agentContainer = jade.core.Runtime.instance().createAgentContainer(p);     
        agentsHandler();
    }

    public void exec() {
        generateBarabasiGraph();
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n);
            try {
                mainContainer.acceptNewAgent(a.getId(), a).start();
            } catch (StaleProxyException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void stop() {
        try {            
            mainContainer.getPlatformController().kill();            
        } catch (ControllerException ex) {
            Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agentsHandler() {
        try {
            mainContainer.addPlatformListener(new PlatformController.Listener() {
                List<String> agents = new ArrayList<>();

                @Override
                public void deadAgent(PlatformEvent anEvent) {
                    // WORKS 
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " dead ");
                    agents.remove(name);
                    if (agents.isEmpty()) {
                        System.out.println(" - "
                                + " JADE end! ");
                        stop();
                        calculatePageRank();
                    }
                }

                @Override
                public void bornAgent(PlatformEvent anEvent) {
                    // WORKS
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " born ");
                    agents.add(name);
                }

                @Override
                public void startedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void suspendedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void resumedPlatform(PlatformEvent pe) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void killedPlatform(PlatformEvent pe) {
                    System.out.println(" - "
                            + pe.getPlatformName()
                            + " killed ");
                }
            });
        } catch (ControllerException ex) {
            Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculatePageRank() {
        for (Node node : graph) {
            double rank = pageRank.getRank(node);
            double rankperc = 5 + Math.sqrt(graph.getNodeCount() * rank * 20);
            node.addAttribute("ui.style",
                    "padding:" + rankperc + "px;");
            if (rankperc > 12) {
                node.addAttribute("ui.style", "fill-color: rgb(0,255,0);");
            }
        }
    }

    private void generateBarabasiGraph() {
        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(Config.getInstance().getMaxEdgesPerEntity(),
                false);
        b.setDirectedEdges(true, true);
        b.addSink(graph);
        b.begin();
        while (graph.getNodeCount() < Config.getInstance().getNumberOfEntity()) {
            try {
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
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        b.end();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.entity.SynthDB;
import aml.entity.Transaction;
import aml.global.Config;
import static aml.global.Enums.NodeType.EMPLOYEE;
import static aml.global.Enums.NodeType.FREELANCE;
import aml.global.Enums.PersistenceMode;
import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
//    private final PageRank pageRank;

    public JadeManager(Graph graph) {
        this.graph = graph;
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
        setLaunderersAndHonests();
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n);
            try {
                mainContainer.acceptNewAgent(a.getId(), a).start();
            } catch (StaleProxyException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stop() {
        try {
            mainContainer.getPlatformController().kill();
        } catch (ControllerException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agentsHandler() {
        try {
            mainContainer.addPlatformListener(new JadeListener(this));
        } catch (ControllerException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exit() {
        System.out.println(" - Exit..... ");
        JOptionPane.showMessageDialog(null, "Simulation finished!", "AML Ranking", JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    }

    public void writeData() {

        try {
            System.out.println(" - Start writing DB..... ");
            SynthDB db = new SynthDB(PersistenceMode.FILE);
            for (Node node : graph) {
                MyNode mynode = (MyNode) node;
                for (Transaction trans : mynode.getReceived()) {
                    db.write(trans);
                }
            }
            db.close();
            System.out.println(" - End writing DB..... ");
            exit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error simulation!", "AML Ranking", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    private void calculatePageRank() {
//        for (Node node : graph) {
//            double rank = pageRank.getRank(node);
//            double rankperc = 5 + Math.sqrt(graph.getNodeCount() * rank * 20);
//            node.addAttribute("ui.style",
//                    "padding:" + rankperc + "px;");
//            if (rankperc > 12) {
//                node.addAttribute("ui.style", "fill-color: rgb(0,255,0);");
//            }
//        }
//    }

    private void generateBarabasiGraph() {
        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(Config.instance().getMaxEdgesPerEntity(),
                false);
        b.setDirectedEdges(true, true);
        b.addSink(graph);
        b.begin();
        while (graph.getNodeCount() < Config.instance().getNumberOfEntity()) {
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
                Thread.currentThread().sleep(200);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(JadeManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        b.end();
    }

    private void setLaunderersAndHonests() {
        int numberLaunderer = (Config.instance().getNumberOfEntity() * Config.instance().getLaundererPercentage()) / 100;
        List<MyNode> nodes = new ArrayList<>(graph.getNodeSet());
        Collections.sort(nodes);
        for (int index = 0; index < nodes.size(); index++) {
            MyNode n = nodes.get(index);
            n.setHonest(index >= numberLaunderer);
            if (index >= numberLaunderer) {
                n.addAttribute("ui.style", "fill-color: rgb(0,255,0);");
            }
        }
    }
}

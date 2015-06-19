/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.agent.MyAgent;
import aml.agent.Receiver;
import aml.agent.Sender;
import aml.entity.SynthDB;
import aml.global.Config;
import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 * Concrete subject of the JADE platform
 *
 * @author DAVIDE
 */
public class MyPlatformManager {

    protected AgentContainer mainContainer;
    protected Graph graph;
    protected long start;
    protected long end;
    protected SynthDB writer;
//    private final PageRank pageRank;

    public MyPlatformManager(Graph graph) {
        this.graph = graph;
        this.writer = new SynthDB();
        this.start = System.currentTimeMillis();
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099)      
        mainContainer = Runtime.instance().createMainContainer(p);
        //initialize the platform handler
        initHandler();
    }

    /**
     * Execute the JADE containers and starts all agents of the network
     */
    public void exec() {       
        /*
         * List of the agents of the JADE container
         */
        List<MyAgent> agents = new ArrayList<>();
        /*
         * Create an agent for each node of the network and start it       
         */
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n,writer);
            try {
                mainContainer.acceptNewAgent(a.getId(), a).start();
                agents.add(a);
            } catch (StaleProxyException ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*
         * After all agents of the network starts in the JADE container
         * an agent can send a message to their neighbour nodes
         */
        for (MyAgent a : agents) {
            a.addBehaviour(new Sender(a, (MyNode) graph.getNode(a.getLocalName())));
            a.addBehaviour(new Receiver(a, (MyNode) graph.getNode(a.getLocalName())));
        }
    }

    public void halt() {
        try {
            mainContainer.getPlatformController().kill();
            this.writer.close();
            exit();
        } catch (ControllerException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MyPlatformManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Custom listener of the platform for handle agents life
     */
    private void initHandler() {
        try {
            mainContainer.addPlatformListener(new MyPlatformListener(this));
        } catch (ControllerException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MyPlatformManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Exit from prototype
     */
    private void exit() {
        System.out.println(" - Exit..... ");
        this.end = System.currentTimeMillis();
        System.out.println(" - time elapsed (msec): " + (end - start));
        if (Config.instance().isGuiEnabled()) {
            JOptionPane.showMessageDialog(null, "Simulation finished!", "AML Ranking", JOptionPane.INFORMATION_MESSAGE);
        }
        System.exit(1);
    }    
}
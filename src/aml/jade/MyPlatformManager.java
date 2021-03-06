/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.jade;

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

    /**
     * Create new instance of MyPlatformManager
     *
     * @param graph instance of Network to manage
     */
    public MyPlatformManager(Graph graph) {
        this.graph = graph;
        this.writer = new SynthDB();
        this.start = System.currentTimeMillis();
        Runtime rt = Runtime.instance();
        rt.setCloseVM(true);
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099)      
        rt.createMainContainer(p);
        mainContainer = rt.createAgentContainer(p);
        //initialize the platform handler
        initHandler();
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
            MyAgent a = new MyAgent((MyNode) n, writer);
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

    /**
     * Stop the execution of the JADE platform
     */
    public void halt() {

        try {
            mainContainer.getPlatformController().kill();
        } catch (ControllerException ex) {
            Logger.getLogger(MyPlatformManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.writer.close();
        System.out.println(" - Exit..... ");
        this.end = System.currentTimeMillis();
        System.out.println(" - time elapsed (msec): " + (end - start));
        if (Config.instance().isGuiEnabled()) {
            JOptionPane.showMessageDialog(null, "Simulation finished!", "AML Ranking", JOptionPane.INFORMATION_MESSAGE);
        }
        //TODO Compute the network scores
        System.exit(0);
    }
    
//    /**
//     * Compute the score of each node in the network
//     * using defined formalism
//     */
//    private void computeScores(){
//        //for each node v in the graph
//            //for each node x in the graph
//                //if x in partner(v)
//                    //inc deficitScore(v)
//                //else 
//                    //for each node v2 in partner(x)
//                        //if v2 in dummies(v) || v2 in parents(v)
//                            //inc deficitScore(v)
//    }
}

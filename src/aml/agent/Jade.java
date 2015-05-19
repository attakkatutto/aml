/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public class Jade  {

    private AgentContainer agentContainer;
    private final Graph graph;

    public Jade(Graph graph) {
        this.graph = graph;
        initJade();
    }

    private void initJade() {
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        agentContainer = jade.core.Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        //agentContainer = jade.core.Runtime.instance().createAgentContainer(p);
    }

    public void startAgents() {
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n);
            try {
                agentContainer.acceptNewAgent(a.getId(), a).start();
            } catch (StaleProxyException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

}

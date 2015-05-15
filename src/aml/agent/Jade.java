/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.global.Writer;
import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public final class Jade {

    private AgentContainer agentContainer;
    private final HashMap<String, MyAgent> map;
    private final Graph graph;
    private Writer writer;

    public Jade(Graph graph) {
        this.map = new HashMap<>();
        this.graph = graph;
        try {
            this.writer = new Writer();
        } catch (IOException ex) {
            Logger.getLogger(Jade.class.getName()).log(Level.SEVERE, null, ex);
        }
        initJade();
    }

    private void initJade() {
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        jade.core.Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        agentContainer = jade.core.Runtime.instance().createAgentContainer(p);
    }

    private void createAgent(MyAgent agent, String id) {
        try {
            agentContainer.acceptNewAgent(id, agent).start();
            map.put(id, agent);
        } catch (StaleProxyException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void removeAgent(MyAgent agent) {
         map.remove(agent);        
    }

    public void startAgents() {
        for (Node n : graph.getEachNode()) {
            MyNode v = (MyNode) n;
            MyAgent a = new MyAgent(v.getType(), n,writer);
            createAgent(a, n.getId());
        }
    }

}

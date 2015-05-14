/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.MyAgent;
import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;
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

    public Jade(Graph graph) {
        this.map = new HashMap<>();
        this.graph = graph;
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

    public void startAgents() {
        for (Node n : graph.getEachNode()) {
            MyNode v = (MyNode) n;
            MyAgent a = new MyAgent(v.getType(), n);            
            createAgent(a, n.getId());
        }
    }

}

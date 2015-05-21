/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.global.Config;
import aml.graph.Network;
import aml.graph.MyNode;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public class Jade  {

    private AgentContainer mainContainer;
    private final Graph graph;
    private final ArrayList<MyAgent> activeAgents;

    public Jade(Graph graph) {
        this.graph = graph;
        this.activeAgents = new ArrayList<>();
        initJade();
    }

    private void initJade() {
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        mainContainer = jade.core.Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        //agentContainer = jade.core.Runtime.instance().createAgentContainer(p);
    }

    public void startAgents() {
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n,this);
            try {
                mainContainer.acceptNewAgent(a.getId(), a).start();
                activeAgents.add(a);
            } catch (StaleProxyException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public void addKill(MyAgent agent){
        this.activeAgents.remove(agent);
        if (activeAgents.isEmpty())
            notifyAllAgent();
    }

    private void notifyAllAgent() {
        
    }

}

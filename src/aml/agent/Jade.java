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
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public class Jade {

    private AgentContainer mainContainer;
    private final Graph graph;
    //private final List<String> agents;

    public Jade(Graph graph) {
        this.graph = graph;
        initJade();
        handleAgent();
        //agents = new ArrayList<>();
    }

    private void initJade() {
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        mainContainer = Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        //agentContainer = jade.core.Runtime.instance().createAgentContainer(p);        
    }

    public void startAgents() {
        for (Node n : graph.getEachNode()) {
            MyAgent a = new MyAgent((MyNode) n);
            try {
                mainContainer.acceptNewAgent(a.getId(), a).start();
            } catch (StaleProxyException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleAgent() {
        try {
            mainContainer.addPlatformListener(new PlatformController.Listener() {
                @Override
                public void deadAgent(PlatformEvent anEvent) {
                    // WORKS 
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " dead ");
                    //agents.remove(name);
//                    if (agents.isEmpty()) {
//                        System.out.println(" - "
//                                + " JADE end! ");                        
//                    }
                }

                @Override
                public void bornAgent(PlatformEvent anEvent) {
                    // WORKS
                    String name = anEvent.getAgentGUID();
                    System.out.println(" - "
                            + name
                            + " born ");
                    //agents.add(name);
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
            Logger.getLogger(Jade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.graph.MyNode;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Node;

/**
 *
 * @author Davide
 */
public class Receiver extends CyclicBehaviour {

    Node n;
    
    public Receiver(Node n){
       this.n=n;
    }
    
    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        ACLMessage msg = myAgent.receive();
        MyNode v = (MyNode) n;
        if (msg != null) {
            switch (msg.getPerformative()) {
                case ACLMessage.INFORM:
                    try {
                        Transaction t = (Transaction) msg.getContentObject();
                        v.setRevenues(t.getAmount(), t.getMonth());
                        System.out.println(" - "
                                + myAgent.getLocalName() + " receive  -> "
                                + t.getAmount() + " month: " + (t.getMonth() + 1) + " costs: " + v.getCosts(t.getMonth()) + " revenues: " + v.getRevenues(t.getMonth()));                        
                    } catch (UnreadableException ex) { 
                        Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case ACLMessage.CANCEL:
                    System.out.println(" - "
                                + myAgent.getLocalName() + " receive  -> " + msg.getContent());
                    base.addBehaviour(new Stop(msg.getSender().getLocalName()));
                    break;
            }
        }
    }

}

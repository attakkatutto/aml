/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide
 */
public class Receiver extends CyclicBehaviour {

    @Override
    public void action() {
        AgentBase base = (AgentBase) myAgent;
        ACLMessage msg = myAgent.receive();
        if (msg != null) {            
            try {
                Transaction t = (Transaction) msg.getContentObject();
                base.setRevenues(t.getAmount());
                System.out.println(" - "
                        + myAgent.getLocalName() + " receive  -> "            
                        + t.getAmount() + " costs: " + base.getCosts() + " revenues: " + base.getRevenues());                
            } catch (UnreadableException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.entity.Transaction;
import aml.base.AgentBase;
import aml.graph.MyNode;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide
 */
public class Receiver extends CyclicBehaviour {

    MyNode n;
    Random random = new Random();
    boolean finished = false;

    public Receiver(AgentBase agent) {
        super(agent);
        this.n = agent.getNode();
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        ACLMessage msg = base.receive();
        if (msg != null) {
            switch (msg.getPerformative()) {
                case ACLMessage.INFORM:
                    try {
                        Transaction t = (Transaction) msg.getContentObject();
                        n.setRevenues(t.getAmount(), t.getMonth());
                        System.out.println(" - "
                                + t.getIdTarget()
                                + " receive from "
                                + t.getIdSource() + " -> "
                                + t.getAmount()
                                + " month: "
                                + (t.getMonth() + 1)
                                + " revenues: " + n.getRevenues(t.getMonth())
                                + " costs: " + n.getCosts(t.getMonth())
                                + " budget: " + n.getBudget(t.getMonth()));
                    } catch (UnreadableException ex) {
                        Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case ACLMessage.PROPAGATE:
                    base.addEND();
                    System.out.println(" - "
                            + base.getLocalName()
                            + " receive finish message from "
                            + msg.getSender().getLocalName()
                            + " node degree: "
                            + n.getDegree()
                            + " END count: "
                            + base.getEND());
                    if (base.getEND() == n.getDegree()) {
                        System.out.println(" - KILL " + base.getId());
                        base.doDelete();
                    }
                    break;
            }
        } else {
            this.block();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import static aml.global.Constant.MAX_WAITING;
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
    boolean finished;
    Random random = new Random();

    public Receiver(MyNode n) {
        this.n = n;
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
                        n.addReceived(t);
//                        ACLMessage reply = msg.createReply();
//                        reply.setPerformative(ACLMessage.AGREE);
//                        reply.setContent(myAgent.getLocalName());
                        System.out.println(" - "
                                + t.getIdTargetAgent()
                                + " receive from "
                                + t.getIdSourceAgent() + " -> "
                                + t.getAmount()
                                + " month: "
                                + (t.getMonth() + 1)
                                + " budget: " + n.getBudget(t.getMonth()));
//                        base.send(reply);
                    } catch (UnreadableException ex) {
                        Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
//                case ACLMessage.AGREE:
//                    System.out.println(" - "
//                            + base.getLocalName()
//                            + " receive transaction ack from "
//                            + msg.getContent());
//                    break;
                case ACLMessage.PROPAGATE:
                    base.addFIN();
                    System.out.println(" - "
                            + base.getLocalName()
                            + " receive finish message from "
                            + msg.getSender().getLocalName() 
                            + " node degree: " 
                            + n.getDegree()
                            + " FIN count: "
                            + base.getFIN());
                    if (base.getFIN()==n.getDegree()){
                        System.out.println(" - KILL " + base.getId());                        
                    }
                    break;
            }
        }
        block(random.nextInt(MAX_WAITING));
    }

}

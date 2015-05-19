/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.graph.MyNode;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

/**
 *
 * @author Davide
 */
public class Receiver extends SimpleBehaviour {

    Node n;
    boolean finished;

    public Receiver(Node n) {
        this.n = n;
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        MyAgent base = (MyAgent) myAgent;
        MyNode v = (MyNode) n;
        if (msg != null) {
            switch (msg.getPerformative()) {
                case ACLMessage.INFORM:
                    try {
                        Transaction t = (Transaction) msg.getContentObject();
                        v.setRevenues(t.getAmount(), t.getMonth());
                        v.addReceived(t);
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent(myAgent.getLocalName());
                        myAgent.send(reply);
                        System.out.println(" - "
                                + t.getIdTargetAgent() + " receive from " + t.getIdSourceAgent() + " -> "
                                + t.getAmount() + " month: " + (t.getMonth() + 1) + " costs: " + v.getCosts(t.getMonth()) + " revenues: " + v.getRevenues(t.getMonth()));
                    } catch (UnreadableException ex) {
                        Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case ACLMessage.AGREE:
                    if (!base.dequeueMessage(msg.getContent())) {
                        for (Edge e : v.getLeavingEdgeSet()) {
                            MyNode vc = e.getTargetNode();
                            ACLMessage msgCanc = new ACLMessage(ACLMessage.CANCEL);
                            msgCanc.addReceiver(new AID(vc.getId(), AID.ISLOCALNAME));
                            msgCanc.setContent(" finished! ");
                            myAgent.send(msgCanc);
                            System.out.println(" - "
                                    + myAgent.getLocalName()
                                    + " send to " + vc.getId() + " ->  " + msgCanc.getContent());
                        }
                    }
                    System.out.println(" - "
                            + myAgent.getLocalName() + " receive transaction ack from " + msg.getContent());
                    break;
                case ACLMessage.CANCEL:
                    System.out.println(" - "
                            + myAgent.getLocalName() + " receive from " + msg.getSender().getLocalName() + " -> " + msg.getContent());
                    myAgent.addBehaviour(new Stop());
                    finished = true;
                    break;
            }
        }
    }

    @Override
    public boolean done() {
        return finished;
    }

}

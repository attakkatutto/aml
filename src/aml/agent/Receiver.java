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
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This behavoiur handle message from other agents
 * 
 * @author Davide
 */
public class Receiver extends CyclicBehaviour {

    MyNode n;
    Random random = new Random();
    boolean finished = false;

    public Receiver(AgentBase agent, MyNode n) {
        super(agent);
        this.n = n;
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        ACLMessage msg = base.receive();
        if (msg != null) {
            switch (msg.getPerformative()) {
                case ACLMessage.REQUEST:
                    base.addBehaviour(new HandleTransactionReceived(base, n, msg));
                    break;
                case ACLMessage.PROPAGATE:
                    base.addBehaviour(new HandleFinished(base, n, msg));
                    break;
            }
        } else {
            this.block();
        }
    }
}

/*
* Class to handle a received transaction from another node
*/
class HandleTransactionReceived extends OneShotBehaviour {

    private final ACLMessage msg;
    private final MyNode n;

    HandleTransactionReceived(MyAgent a, MyNode n, ACLMessage msg) {
        super(a);
        this.n = n;
        this.msg = msg;
    }

    @Override
    public void action() {
        try {
            Transaction t = (Transaction) msg.getContentObject();
            n.setRevenues(t.getAmount(), t.getMonth());
            n.addReceived(t);
            System.out.println(" - "
                    + t.getIdTarget()
                    + " receive from "
                    + t.getIdSource());
//                    + " -> "
//                    + t.getAmount()
//                    + " month: "
//                    + (t.getMonth() + 1)
//                    + " revenues: " + n.getRevenues(t.getMonth())
//                    + " costs: " + n.getCosts(t.getMonth())
//                    + " budget: " + n.getBudget(t.getMonth()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(HandleTransactionReceived.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
* Class to handle a received finish message from another node
*/
class HandleFinished extends OneShotBehaviour {

    private final ACLMessage msg;
    private final MyNode n;

    HandleFinished(MyAgent a, MyNode n, ACLMessage msg) {
        super(a);
        this.n = n;
        this.msg = msg;
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        base.addEND();
        System.out.println(" - "
                + base.getLocalName()
                + " receive finish message from "
                + msg.getSender().getLocalName());
//                + " node degree: "
//                + n.getDegree()
//                + " END count: "
//                + base.getEND());
        if (base.getEND() == n.getDegree()) {
            System.out.println(" - KILL " + base.getId());
            base.doDelete();
        }
    }
}

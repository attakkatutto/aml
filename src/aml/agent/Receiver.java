/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.entity.Transaction;
import aml.base.AgentBase;
import aml.global.Enums.*;
import aml.graph.MyNode;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This behavoiur handle message from other agents
 *
 * @author Davide
 */
public class Receiver extends SimpleBehaviour {

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
                    handleTransactionReceived(msg);
                    break;
                case ACLMessage.PROPAGATE:
                    handleFinished(msg);
                    break;
            }
        } else {
            this.block();
        }
    }
    
    public void handleTransactionReceived(ACLMessage msg) {
        try {
            MyAgent base = (MyAgent) myAgent;
            Transaction t = (Transaction) msg.getContentObject();
            n.setRevenues(t.getAmount(), t.getMonth(), t.getYear());
            base.writeReceived(t);
            System.out.println(" - "
                    + t.getIdTarget()
                    + " receive from "
                    + t.getIdSource());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleFinished(ACLMessage msg) {
        MyAgent base = (MyAgent) myAgent;
        base.addEND();
        System.out.println(" - "
                + base.getLocalName()
                + " receive finish message from "
                + msg.getSender().getLocalName());
        if (base.getEND() == n.getDegree()) {
            finished = true;
            if (base.getCurrentState() == MyAgentState.SEND_FINISH) {
                System.out.println(" - KILL " + base.getId());
                base.doDelete();
            } else {
                base.setState(MyAgentState.RECEIVE_FINISH);
            }
        }
    }

    @Override
    public boolean done() {
        return finished;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.agent.Receiver;
import aml.agent.Sender;
import aml.agent.Transaction;
import jade.core.Agent;
import java.util.ArrayList;
import java.util.Random;
import org.graphstream.graph.Node;

/**
 * Base class of custom JADE Agent
 * 
 * @author Davide
 */
public abstract class AgentBase extends Agent {

    private Node n;
    protected final Random random = new Random();
    protected ArrayList<Transaction> sent;
    protected ArrayList<Transaction> received;

    public AgentBase(Node n) {
        super();    
        this.n = n;
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();
    }

    @Override
    public void setup() {
        addBehaviour(new Sender(n));
        addBehaviour(new Receiver(n));
    }

    public ArrayList<Transaction> getSent() {
        return sent;
    }

    public void setSent(ArrayList<Transaction> sent) {
        this.sent = sent;
    }

    public ArrayList<Transaction> getReceived() {
        return received;
    }

    public void setReceived(ArrayList<Transaction> received) {
        this.received = received;
    }
    
}

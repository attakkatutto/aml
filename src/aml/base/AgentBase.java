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
    protected Sender beh1;
    protected Receiver beh2;
    protected ArrayList<Transaction> sent;
    protected ArrayList<Transaction> received;

    public AgentBase(Node n) {
        super();
        this.n = n;
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();
        this.beh1 = new Sender(n);
        this.beh2 = new Receiver(n);
    }

    @Override
    public void setup() {
        addBehaviour(beh1);
        addBehaviour(beh2);
    }

    public void addSent(Transaction sent) {
        this.sent.add(sent);
    }

    public void addReceived(Transaction received) {
        this.received.add(received);
    }

}

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
 *
 * @author Davide
 */
public abstract class AgentBase extends Agent implements IAgentBase {

    private Node n;
    protected double[] revenues, costs;
    protected final Random random = new Random();
    protected ArrayList<String> neighbour;
    protected ArrayList<Transaction> sent;
    protected ArrayList<Transaction> received;

    public AgentBase(Node n) {
        super();    
        this.n = n;
        this.revenues = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.costs = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.neighbour = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.received = new ArrayList<>();
    }

    @Override
    public void setup() {
        addBehaviour(new Sender(n));
        addBehaviour(new Receiver(n));
    }

    /**
     * Get revenues of the EntityBase
     *
     * @param month
     * @return revenues
     */
    @Override
    public double getRevenues(int month) {
        return revenues[month];
    }

    /**
     * Set revenue of the EntityBase and increment total revenues
     *
     * @param revenue of the EntityBase
     * @param month
     */
    @Override
    public void setRevenues(double revenue, int month) {
        revenues[month] += revenue;
    }

    /**
     * Set cost of the EntityBase and increment total costs
     *
     * @param cost of the EntityBase
     * @param month
     */
    @Override
    public void setCosts(double cost, int month) {
        costs[month] += cost;
    }

    /**
     * Get costs of the EntityBase
     *
     * @param month
     * @return costs
     */
    @Override
    public double getCosts(int month) {
        return costs[month];
    }

    /**
     * Get budget of the EntityBase
     *
     * @param month
     * @return revenues - costs
     */
    @Override
    public double getBudget(int month) {
        return revenues[month] - costs[month];
    }

    @Override
    public double getGlobalCosts() {
        double _global = 0;
        for (double d : costs) {
            _global += d;
        }
        return _global;
    }

    @Override
    public double getGlobalRevenues() {
        double _global = 0;
        for (double d : revenues) {
            _global += d;
        }
        return _global;
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

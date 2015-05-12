/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent.base;

import aml.agent.Receiver;
import aml.agent.Sender;
import jade.core.Agent;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Davide
 */
public abstract class AgentBase extends Agent implements IAgentBase {

    protected double[] revenues, costs;
    protected double[] fraudScore, suspectedScore, deficitScore;
    protected final Random random = new Random();
    protected ArrayList<String> neighbour;

    public AgentBase() {
        super();
        this.deficitScore = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.fraudScore = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.suspectedScore = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.revenues = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.costs = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.neighbour = new ArrayList<>();
    }

    public void addNeighbour(String id) {
        this.neighbour.add(id);
    }

    public String getNeighbour(int index) {
        return this.neighbour.get(index);
    }

    public int sizeNeighbour() {
        return this.neighbour.size();
    }

    public boolean isEmptyNeighbour() {
        return this.neighbour.isEmpty();
    }

    @Override
    public void setup() {
        addBehaviour(new Sender());
        addBehaviour(new Receiver());
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

    /**
     * Get fraudScore of the EntityBase
     *
     * @param month
     * @return fraudScore
     */
    @Override
    public double getFraudScore(int month) {
        return fraudScore[month];
    }

    /**
     * Get suspectedScore of the EntityBase
     *
     * @param month
     * @return suspectedScore
     */
    @Override
    public double getSuspectedScore(int month) {
        return suspectedScore[month];
    }

    /**
     * Are you honest?
     *
     * @return true/false
     */
    public boolean IsHonest() {
        return true;
    }

    @Override
    public abstract double getDeficitScore(int month);

    @Override
    public abstract void initPartners();

    @Override
    public double getGlobalCosts() {
        double _global = 0;
        for(double d: costs) {_global+=d;}
        return _global;
    }
    
    @Override
    public double getGlobalRevenues() {
        double _global = 0;
        for(double d: revenues) {_global+=d;}
        return _global;
    }

}

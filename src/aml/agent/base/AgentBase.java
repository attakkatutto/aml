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

    protected double revenues, costs;
    protected double fraudScore, suspectedScore, deficitScore;
    protected final Random random = new Random();
    protected ArrayList<String> neighbour;

    public AgentBase() {
        super();
        this.deficitScore = 0;
        this.fraudScore = 0;
        this.suspectedScore = 0;
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
     * @return revenues
     */
    @Override
    public double getRevenues() {
        return revenues;
    }

    /**
     * Set revenue of the EntityBase and increment total revenues
     *
     * @param revenue of the EntityBase
     */
    @Override
    public void setRevenues(double revenue) {
        revenues += revenue;
    }

    /**
     * Set cost of the EntityBase and increment total costs
     *
     * @param cost of the EntityBase
     */
    @Override
    public void setCosts(double cost) {
        costs += cost;
    }

    /**
     * Get costs of the EntityBase
     *
     * @return costs
     */
    @Override
    public double getCosts() {
        return costs;
    }

    /**
     * Get budget of the EntityBase
     *
     * @return revenues - costs
     */
    @Override
    public double getBudget() {
        return revenues - costs;
    }

    /**
     * Get fraudScore of the EntityBase
     *
     * @return fraudScore
     */
    @Override
    public double getFraudScore() {
        return fraudScore;
    }

    /**
     * Get suspectedScore of the EntityBase
     *
     * @return suspectedScore
     */
    @Override
    public double getSuspectedScore() {
        return suspectedScore;
    }
    
    /**
     * Are you honest?
     *
     * @return true/false
     */
    public boolean IsHonest() {
        return this.getDeficitScore() >= 0;
    }

    @Override
    public abstract double getDeficitScore();

    @Override
    public abstract void initPartners();

}

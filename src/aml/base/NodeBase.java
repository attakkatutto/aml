/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.global.Config;
import aml.global.Enums.*;
import java.util.ArrayList;
import java.util.Random;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.AdjacencyListNode;

/**
 * Abstract base class of EntityBase
 *
 * @author ddefalco
 */
public abstract class NodeBase extends AdjacencyListNode implements INode {

    protected AgentBase agent;
    protected NodeType type;

    //Random List of busness partners 
    protected ArrayList<String> partners;
    //Random List of parents
    protected ArrayList<String> parents;
    //Random List of dummies
    protected ArrayList<String> dummies;

    protected Random random;
    protected double[] revenues, costs;
    protected double[] fraudScore, suspectedScore, deficitScore;

    // *** Constructor ***
    public NodeBase(AbstractGraph graph, String id, NodeType type) {
        super(graph, id);
        this.revenues = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.costs = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.type = type;
        this.random = new Random();
        this.partners = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.dummies = new ArrayList<>();
        initScore();
    }

    public ArrayList<String> getPartners() {
        return partners;
    }

    public void setPartners(ArrayList<String> partners) {
        this.partners = partners;
    }

    public ArrayList<String> getParents() {
        return parents;
    }

    public void setParents(ArrayList<String> parents) {
        this.parents = parents;
    }

    @Override
    public void setIndex(int index) {
        super.setIndex(index);
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    /**
     * Get fraudScore of the EntityBase
     *
     * @return fraudScore
     */
    @Override
    public double getFraudScore() {
        return 0;
    }

    /**
     * Get suspectedScore of the EntityBase
     *
     * @return suspectedScore
     */
    @Override
    public double getSuspectedScore() {
        return 0;
    }

    @Override
    public double getDeficitScore(int index) {
        return deficitScore[index];
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

    /**
     * Are you honest?
     *
     * @return true/false
     */
    public boolean isHonest() {
        return true;
    }

    /**
     * Are you dummy?
     *
     * @param id
     * @return true/false
     */
    public boolean isDummy(String id) {
        return dummies.contains(id);
    }

    /**
     * Set color node in the network
     */
    @Override
    public abstract void setColor();

    /**
     * Initialize the parents of the current node PERSON
     */
    @Override
    public abstract void initParents();

    /**
     * Initialize the partners of the current node
     */
    @Override
    public abstract void initPartners();

    private void initScore() {
        switch (Config.getInstance().getScoreWindowType()) {
            case THREEMONTHS:
                this.fraudScore = new double[3];
                this.suspectedScore = new double[3];
                this.deficitScore = new double[3];
            case FOURMONTHS:
                this.fraudScore = new double[4];
                this.suspectedScore = new double[4];
                this.deficitScore = new double[4];
            case SIXMONTHS:
                this.fraudScore = new double[6];
                this.suspectedScore = new double[6];
                this.deficitScore = new double[6];
        }
    }
}

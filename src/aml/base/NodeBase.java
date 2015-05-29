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
public abstract class NodeBase extends AdjacencyListNode implements INode, Comparable {

    protected AgentBase agent;
    protected NodeType type;
    protected boolean honest;

    //Random List of busness partners 
    protected ArrayList<String> partners;
    //Random List of parents
    protected ArrayList<String> parents;
    //Random List of dummies
    protected ArrayList<String> dummies;

    protected Random random = new Random();
    protected double[] revenues, costs;
    //, fraudScore, suspectedScore, deficitScore

    // *** Constructor ***
    public NodeBase(AbstractGraph graph, String id, NodeType type) {
        super(graph, id);
        this.type = type;
        costs = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        revenues = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 
        this.partners = new ArrayList<>();
        this.parents = new ArrayList<>();
        this.dummies = new ArrayList<>();
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
     * Are you honest?
     *
     * @return true/false calcolo onesto o disonesto sulla base degli archi
     * uscenti in modo da ottenere disonesti per il 5% (xml) del totale dei
     * nodi. media e deviazione std per disonesti (quella che è config nel xml
     * rimane così ed è quella degli onesti)
     *
     */
    public boolean isHonest() {
        return honest;
    }

    public void setHonest(boolean honest) {
        this.honest = honest;
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

    /**
     * Initialize the dummies of the current node PERSON
     */
    @Override
    public abstract void initDummies();


    @Override
    public int compareTo(Object o) {
        if (this.getOutDegree() < ((NodeBase) o).getOutDegree()) {
            return 1;
        }
        if (this.getOutDegree() > ((NodeBase) o).getOutDegree()) {
            return -1;
        }
        return 0;
    }
}

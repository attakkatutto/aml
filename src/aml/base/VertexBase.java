/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.base.IVertexBase;
import aml.agent.Transaction;
import aml.base.AgentBase;
import aml.global.Config;
import static aml.global.Constant.MONTHS;
import aml.global.Enums.*;
import java.util.ArrayList;
import java.util.Random;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.AdjacencyListNode;

/**
 * Abstract base class of EntityBase
 *
 * @author ddefalco
 */
public abstract class VertexBase extends AdjacencyListNode implements IVertexBase {

    protected AgentBase agent;
    protected VertexType type;

    //Random List of busness partners 
    protected ArrayList<String> partners;
    //Random List of parents
    protected ArrayList<String> parents;

    protected Random random;
    protected double[] fraudScore, suspectedScore, deficitScore;

    // *** Constructor ***
    public VertexBase(AbstractGraph graph, String id, VertexType type) {
        super(graph, id);
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
        this.type = type;
        this.random = new Random();
        this.partners = new ArrayList<>(Config.getInstance().getMaxNumberPartners());
        this.parents = new ArrayList<>(Config.getInstance().getMaxNumberParents());
        initVertexRelationship();
    }

    private void initVertexRelationship() {
        if (this.graph.getNodeCount() > 0) {
            initPartners();
            if (type == VertexType.PERSON) {
                initParents();
            }
        }
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

    public VertexType getType() {
        return type;
    }

    public void setType(VertexType type) {
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
        updateDeficitScore();
        return deficitScore[index];
    }

    private void updateDeficitScore() {
        for (int month = 0; month < MONTHS; month++) {
            deficitScore[month / deficitScore.length] = 0;
            for (String u : partners) {
                VertexBase v = (VertexBase) graph.getNode(u);
                deficitScore[month / deficitScore.length] += v.getAgent().getBudget(month) - v.getAgent().getRevenues(month);
                if (v.getType() == VertexType.PERSON) 
                    for (String u1 : v.getParents()) {
                        VertexBase v2 = (VertexBase) graph.getNode(u1);
                        for (String u2 : v2.getPartners()) {
                            VertexBase v3 = (VertexBase) graph.getNode(u2);
                            deficitScore[month / deficitScore.length] += v3.getAgent().getBudget(month);
                            for (Edge e : v3.getEdgeSet()) 
                                if (e.getNode0() == v3 && e.getNode1() == v) 
                                    for (Transaction t :v3.getAgent().getSent()) 
                                        if (t.getIdTargetAgent().equals(v.getId())) deficitScore[month / deficitScore.length] -= t.getAmount();                                                              
                        }
                    }                
            }
        }
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
     * @return true/false
     */
    public boolean isDummy() {
        return true;
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
}

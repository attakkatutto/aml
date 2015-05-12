/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph.base;

import aml.agent.base.AgentBase;
import aml.global.Config;
import aml.global.VertexType;
import java.util.ArrayList;
import java.util.Random;
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

    // *** Constructor ***
    public VertexBase(AbstractGraph graph, String id, VertexType type) {
        super(graph, id);
        this.type = type;
        this.random = new Random();
        this.partners = new ArrayList<>(Config.getInstance().getMaxNumberPartners());
        this.parents = new ArrayList<>(Config.getInstance().getMaxNumberParents());
        initVertexRelationship();
    }       

    private void initVertexRelationship(){
        if (this.graph.getNodeCount() > 0) {
            initPartners();
            if (type == VertexType.PERSON) {
                initParents();
            }
        }
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

    public double getScore(int month) {
        return agent.getFraudScore(month);
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

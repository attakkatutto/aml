/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph.base;

import aml.agent.base.AgentBase;
import aml.global.VertexType;
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

    // *** Constructor ***
    public VertexBase(AbstractGraph graph, String id, VertexType type) {
        super(graph, id);
        this.type = type;
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

    public double getScore() {
        return agent.getDeficitScore();
    }

    /**
     * Set color node in the network
     */
    @Override
    public abstract void setColor();
}

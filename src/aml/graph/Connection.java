/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.base.VertexBase;
import org.graphstream.graph.implementations.AbstractEdge;

/**
 * Connection of the network between two Vertex
 *
 * @author DAVIDE
 */
public final class Connection extends AbstractEdge {

    /**
     * New ConnectionBase
     * 
     * @param id id transaction
     * @param sourceAgent EntityBase transaction source
     * @param targetAgent EntityBase transaction target
     */
    public Connection(String id, VertexBase sourceAgent, VertexBase targetAgent) {
        super(id, sourceAgent, targetAgent, true);
        this.directed = true;
    }


    @Override
    public void setIndex(int index) {
        super.setIndex(index);
    }
}

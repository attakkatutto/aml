/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.AgentBase;
import aml.global.Enums.VertexType;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected VertexType type;    

    public MyAgent(VertexType type,Node n) {
        super(n);
        this.type = type;
    }

    public VertexType getType() {
        return type;
    }

}

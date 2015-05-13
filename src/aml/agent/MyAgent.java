/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;
import aml.global.Enums.VertexType;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected VertexType type;

    public MyAgent(VertexType type) {
        super();
        this.type = type;
    }

    public VertexType getType() {
        return type;
    }

}

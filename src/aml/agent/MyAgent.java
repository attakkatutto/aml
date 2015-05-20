/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.AgentBase;
import aml.global.Enums.NodeType;
import aml.graph.MyNode;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected NodeType type;
    protected String id;

    public MyAgent(MyNode n) {
        super(n);
        this.type = n.getType();
        this.id = n.getId();
    }

    public NodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}

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
    protected int END;

    public MyAgent(MyNode node) {
        super(node);
        this.type = node.getType();
        this.id = node.getId();
    }
    
    public NodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
   
    public int getEND() {
        return END;
    }
    
    public int addEND() {
        return END++;
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.AgentBase;
import aml.global.Enums.*;
import aml.graph.MyNode;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected NodeType type;
    protected String id;
    protected int END;
    protected MyAgentState state;

    public MyAgent(MyNode node) {
        super(node);
        this.type = node.getType();
        this.id = node.getId();
        this.state = MyAgentState.START;
    }
    
    public NodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
   
    public synchronized int getEND() {
        return END;
    }
    
    public synchronized int addEND() {
        return END++;
    }

    public synchronized MyAgentState getCurrentState() {
        return state;
    }

    public synchronized void setState(MyAgentState state) {
        this.state = state;
    }       
}

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
 * Custom JADE Agent 
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected int END;
    protected MyAgentState state;
    
    /**
     * Constructor of the agent
     * @param node every agent is related with a node in the network 
     */
    public MyAgent(MyNode node) {
        super(node);
        this.state = MyAgentState.START;
    }    
   
    /**
     * Count the finish message received from this agent
     * @return END: number of finish message received
     */
    public synchronized int getEND() {
        return END;
    }
    
    public synchronized int addEND() {
        return END++;
    }

    /**
     * Rapresent the current state of the agent:
     * - START the agent is started
     * - SEND_FINISH the agent has been sent all messages
     * - RECEIVE_FINISH the agent has been received all messages
     * @return MyAgentState current state of the agent
     */
    public synchronized MyAgentState getCurrentState() {
        return state;
    }

    public synchronized void setState(MyAgentState state) {
        this.state = state;
    }       
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.AgentBase;
import aml.global.Enums.NodeType;
import aml.graph.MyNode;
import java.util.ArrayList;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected NodeType type;
    protected String id;
    protected ArrayList<String> inqueue;
    protected ArrayList<String> outqueue;

    public MyAgent(MyNode n) {
        super(n);
        this.type = n.getType();
        this.id = n.getId();
        this.inqueue = new ArrayList<>();
        this.outqueue = new ArrayList<>();
    }

    public NodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public synchronized void enqueueInMessage(String idAgent) {

        if (!inqueue.contains(idAgent)) {
            inqueue.add(idAgent);
        }

    }
    
    public synchronized void enqueueOutMessage(String idAgent) {

        if (!outqueue.contains(idAgent)) {
            outqueue.add(idAgent);
        }

    }

    public synchronized boolean dequeueInMessage(String idAgent) {
        return inqueue.remove(idAgent);
    }
    
     public synchronized boolean dequeueOutMessage(String idAgent) {
        return outqueue.remove(idAgent);
    }
}

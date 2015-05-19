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
    protected ArrayList<String> queue;

    public MyAgent(MyNode n) {
        super(n);
        this.type = n.getType();
        this.id = n.getId();
        this.queue = new ArrayList<>();
    }

    public NodeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public synchronized void enqueueMessage(String idAgent) {

        if (!queue.contains(idAgent)) {
            queue.add(idAgent);
        }

    }

    public synchronized boolean dequeueMessage(String idAgent) {
        return queue.remove(idAgent);
    }
}

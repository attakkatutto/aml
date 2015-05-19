/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.AgentBase;
import aml.global.Enums.NodeType;
import java.util.ArrayList;
import org.graphstream.graph.Node;

/**
 *
 * @author DAVIDE
 */
public final class MyAgent extends AgentBase {

    protected NodeType type;
    protected String id;
    protected ArrayList<String> queue;

    public MyAgent(NodeType type, Node n) {
        super(n);
        this.type = type;
        this.id = n.getId();
        this.queue = new ArrayList<>();            
    }

    public NodeType getType() {
        return type;
    }    

    public String getId() {
        return id;
    }
      
    public void enqueueMessage(String idAgent){
        if (!queue.contains(idAgent))
        queue.add(idAgent);
    }
    
    public boolean dequeueMessage(String idAgent){
        return queue.remove(idAgent);
    }
}

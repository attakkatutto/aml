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
public final class MyAgent extends AgentBase{

    protected NodeType type;
    private ArrayList<String> activeLink;

    public MyAgent(NodeType type, Node n) {
        super(n);
        this.type = type;
        this.activeLink = new ArrayList<>();
    }

    public NodeType getType() {
        return type;
    }

    public void addLink(String idAgent) {
        if (!activeLink.contains(idAgent)) activeLink.add(idAgent);
    }
    
    public boolean removeLink(String idAgent) {
        return activeLink.remove(idAgent);
    }
    
    public boolean containsLink(String idAgent) {
        return activeLink.contains(idAgent);
    }

    public boolean isEmptyLink() {
        return activeLink.isEmpty();
    }
    
}

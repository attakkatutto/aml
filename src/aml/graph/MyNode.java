/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.base.AgentBase;
import aml.global.Config;
import aml.global.Enums.*;
import aml.base.NodeBase;
import org.graphstream.graph.implementations.*;

/**
 * Vertex of the random graph
 *
 * @author ddefalco
 */
public final class MyNode extends NodeBase {

    boolean honest;
    boolean dummy;

    /**
     * @param graph Network
     * @param id identifier of the Person
     * @param type type of the vertex
     */
    public MyNode(AbstractGraph graph, String id, NodeType type) {
        super(graph, id, type);
        this.dummy = random.nextBoolean();
    }

    @Override
    public void setColor() {
        if (isHonest()) {
            this.addAttribute("ui.style", "fill-color: rgb(0,204,0);");
        } else {
            this.addAttribute("ui.style", "fill-color: rgb(255,0,0);");
        }
    }

    @Override
    public AgentBase getAgent() {
        return agent;
    }

    @Override
    public void setAgent(AgentBase a) {
        agent = a;
    }

    @Override
    public void initPartners() {
        int count = 0;
        while (count < Config.getInstance().getMaxNumberPartners()) {
            String _id = String.valueOf(random.nextInt(graph.getNodeCount()));
            partners.add(_id);
            count++;
        }
    }

    @Override
    public void initParents() {
        int count = 0;
        while (count < Config.getInstance().getMaxNumberParents()) {
            MyNode v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (v.type == NodeType.PERSON) {
                parents.add(v.getId());
            }
            count++;
        }
    }
    
    /**
     * Are you a dummy or not?
     *
     * @return true/false
     */
    @Override
    public boolean isDummy() {
        return dummy;
    }  
}

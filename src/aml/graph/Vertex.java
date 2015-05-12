/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.agent.base.AgentBase;
import aml.global.Config;
import aml.global.VertexType;
import aml.graph.base.VertexBase;
import org.graphstream.graph.implementations.*;

/**
 * Vertex of the random graph
 *
 * @author ddefalco
 */
public final class Vertex extends VertexBase {    

    /**
     * @param graph Network
     * @param id identifier of the Person
     * @param type type of the vertex
     */
    public Vertex(AbstractGraph graph, String id, VertexType type) {
        super(graph, id, type);        
    }

    @Override
    public void setColor() {
        if (agent.IsHonest()) {
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
            Vertex v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (v.type == VertexType.PERSON) {
                parents.add(v.getId());
            }
            count++;
        }
    }
}

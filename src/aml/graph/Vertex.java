/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.agent.base.AgentBase;
import static aml.global.Constant.MAX_NUMBER_PARENTS;
import static aml.global.Constant.MAX_NUMBER_PARTNERS;
import aml.global.VertexType;
import aml.graph.base.VertexBase;
import java.util.ArrayList;
import java.util.Random;
import org.graphstream.graph.implementations.*;

/**
 * Vertex of the random graph
 *
 * @author ddefalco
 */
public final class Vertex extends VertexBase {

    //Random List of busness partners 
    protected ArrayList<String> partners;
    //Random List of parents
    protected ArrayList<String> parents;

    Random random;

    /**
     * @param graph Network
     * @param id identifier of the Person
     * @param type type of the vertex
     */
    public Vertex(AbstractGraph graph, String id, VertexType type) {
        super(graph, id, type);
        this.random = new Random();
        this.partners = new ArrayList<>(MAX_NUMBER_PARTNERS);
        this.parents = new ArrayList<>(MAX_NUMBER_PARENTS);
        if (this.graph.getNodeCount() > 0) {
            initPartners();
            if (type == VertexType.PERSON) {
                initParents();
            }
        }
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

    public void initPartners() {
        int count = 0;
        while (count < MAX_NUMBER_PARTNERS) {
            String _id = String.valueOf(random.nextInt(graph.getNodeCount()));
            partners.add(_id);
            count++;
        }
    }

    public void initParents() {
        int count = 0;
        while (count < MAX_NUMBER_PARENTS) {
            Vertex v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (v.type == VertexType.PERSON) {
                parents.add(v.getId());
            }
            count++;
        }
    }
}

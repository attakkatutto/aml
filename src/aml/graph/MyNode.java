/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.global.Config;
import aml.global.Enums.*;
import aml.base.NodeBase;
import aml.entity.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.graphstream.graph.implementations.*;

/**
 * Node of the graph
 *
 * @author ddefalco
 */
public final class MyNode extends NodeBase {

    protected List<Transaction> received;
    /**/
    protected double fraudPotential;

    /**
     * Node of the network
     * 
     * @param graph Network
     * @param id identifier of the Person
     * @param type type of the vertex
     */
    public MyNode(AbstractGraph graph, String id, NodeType type) {
        super(graph, id, type);
        this.received = Collections.synchronizedList(new ArrayList<Transaction>());
        this.fraudPotential = (Config.instance().getFraudPotential() == 0) ? 1 : Math.random();
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
    public void initPartners() {
        int count = 0;
        while (count < Config.instance().getNumberPartners()) {
            String _id = String.valueOf(random.nextInt(graph.getNodeCount()));
            partners.add(_id);
            count++;
        }
    }

    @Override
    public void initParents() {
        int count = 0;
        while (count < Config.instance().getNumberParents()) {
            MyNode v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (type == NodeType.EMPLOYEE || type == NodeType.FREELANCE) {
                parents.add(v.getId());
            }
            count++;
        }
    }
    
    public void addReceived(Transaction received) {
        this.received.add(received);
    }

    public List<Transaction> getReceived() {
        return received;
    }       

    public double getFraudPotential() {
        return (isHonest()) ? 0 : fraudPotential;
    }     
    
    @Override
    public void initDummies() {
        int count = 0;
        while (count < Config.instance().getMaxNumberDummies()) {
            MyNode v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (type == NodeType.EMPLOYEE || type == NodeType.FREELANCE) {
                dummies.add(v.getId());
            }
            count++;
        }
    }    
}

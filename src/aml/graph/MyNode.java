/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.global.Config;
import aml.global.Enums.*;
import aml.base.NodeBase;
import org.graphstream.graph.implementations.*;

/**
 * Node of the graph
 *
 * @author ddefalco
 */
public final class MyNode extends NodeBase {

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
        this.fraudPotential = (Config.instance().getFraudPotential() == 0) ? 1 : Math.random();
    }

    @Override
    public void initPartners() {
        int _count = 0;
        while (_count < Config.instance().getNumberPartners()) {
            String _id = String.valueOf(random.nextInt(graph.getNodeCount()));
            partners.add(_id);
            _count++;
        }
    }

    @Override
    public void initParents() {
        int _count = 0;
        while (_count < Config.instance().getNumberParents()) {
            MyNode v = graph.getNode(random.nextInt(graph.getNodeCount()));
            if (type == NodeType.EMPLOYEE || type == NodeType.FREELANCE) {
                parents.add(v.getId());
            }
            _count++;
        }
    }

    public double getFraudPotential() {
        return (isHonest()) ? 0 : fraudPotential;
    }

    @Override
    public void initDummies() {
        int _count = 0;
        while (_count < Config.instance().getMaxNumberDummies()) {
            MyNode v = graph.getNode(random.nextInt(graph.getNodeCount()));
            dummies.add(v.getId());
            _count++;
        }
    }
}

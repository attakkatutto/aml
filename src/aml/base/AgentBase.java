/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.graph.MyNode;
import jade.core.Agent;
import java.util.Random;

/**
 * Base class of custom JADE Agent
 *
 * @author Davide
 */
public abstract class AgentBase extends Agent {

    protected MyNode n;
    protected final Random random = new Random();

    public AgentBase(MyNode n) {
        super();
        this.n = n;
    }     
}

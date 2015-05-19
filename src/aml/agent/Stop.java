/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import jade.core.behaviours.SimpleBehaviour;

/**
 * 
 * 
 * @author ddefalco
 */
public class Stop extends SimpleBehaviour {

    protected boolean finished = false;
    
    @Override
    public void action() {
        myAgent.doDelete();
        finished = true;
    }

    @Override
    public boolean done() {
        return finished;
    }
    
}

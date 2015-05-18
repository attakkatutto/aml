/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author ddefalco
 */
public class Stop extends SimpleBehaviour {

    boolean finished;
    String id;

    public Stop(String id) {
        this.id = id;
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        if (!base.removeLink(id)) {
            base.doDelete();
            finished = true;
        }
    }

    @Override
    public boolean done() {
        return finished == true;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;

/**
 *
 * @author DAVIDE
 */
public final class Person extends AgentBase {

    boolean honest;
    boolean dummy;

    public Person(){
        super();      
        this.dummy = random.nextBoolean();
    }
    
    /**
     * Are you a dummy or not?
     *
     * @return true/false
     */
    public boolean IsDummy() {
        return dummy;
    }       
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;
import java.util.ArrayList;

/**
 *
 * @author DAVIDE
 */
public final class Person extends AgentBase {

    boolean honest;
    boolean dummy;
    //Random List of busness partners 
    protected ArrayList<String> partners;
    //Random List of parents
    protected ArrayList<String> parents;

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
    
    @Override
    public double getDeficitScore(int month) {
        return deficitScore[month-1];
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;
import static aml.global.Constant.MAX_NUMBER_PARTNERS;
import java.util.ArrayList;

/**
 * Company of the network
 *
 * @author DAVIDE
 */
public final class Company extends AgentBase {

    //identifier of the company owner
    private String idOwner;
    //Random List of the company partners
    protected ArrayList<String> partners;

    public Company(String idOwner) {
        super();
        this.revenues = 0;
        this.costs = 0;
        this.idOwner = idOwner;
        this.partners = new ArrayList<>(random.nextInt(MAX_NUMBER_PARTNERS));
        initPartners();
    }

    /**
     * Get company owner
     *
     * @return id owner
     */
    public String getIdOwner() {
        return idOwner;
    }

    /**
     * Set the company owner
     *
     * @param idOwner identifier owner
     */
    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    @Override
    public void initPartners() {       
        
    }

    @Override
    public double getDeficitScore() {
        return deficitScore;
    }
    
}

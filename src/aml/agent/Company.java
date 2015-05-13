/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;

/**
 * Company of the network
 *
 * @author DAVIDE
 */
public final class Company extends AgentBase {

    //identifier of the company owner
    private String idOwner;

    public Company(String idOwner) {
        super();
        this.idOwner = idOwner;
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
}

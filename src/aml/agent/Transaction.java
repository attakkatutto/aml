/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.base.ITransaction;

/**
 * Output Transaction Class
 *
 * @author ddefalco
 */
public class Transaction implements java.io.Serializable, ITransaction {

    String id;

    double amount;

    String idSourceAgent;
    
    String idTargetAgent;
    
    int month;

    /**
     * New TransactionBase
     * 
     * @param id id transaction
     * @param idSourceAgent Agent transaction source
     * @param idTargetAgent Agent transaction target
     * @param month time of transaction
     * @param amount amount of transaction
     */
    public Transaction(String id, double amount, String idSourceAgent, String idTargetAgent, int month) {
        this.id = id;
        this.amount = amount;
        this.idSourceAgent = idSourceAgent;
        this.idTargetAgent = idTargetAgent;
        this.month = month;
    }
   
    @Override
    public double getAmount() {
        return amount;
    }
    
    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getIdSourceAgent() {
        return idSourceAgent;
    }

    @Override
    public void setIdSourceAgent(String idSourceAgent) {
        this.idSourceAgent = idSourceAgent;
    }

    @Override
    public String getIdTargetAgent() {
        return idTargetAgent;
    }

    @Override
    public void setIdTargetAgent(String idTargetAgent) {
        this.idTargetAgent = idTargetAgent;
    }

    @Override
    public int getMonth() {
        return month;
    } 
    
    @Override
    public String toString(){
        return "(person\n\t:id " + getId() 
                + "\n\t:amount " + getAmount() 
                + "\n\t:month " + getMonth() 
                + "\n\t:source " + getIdSourceAgent() 
                + "\n\t:target " + getIdTargetAgent()                 
                + ")";
    }
}

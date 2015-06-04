/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

/**
 * Interface Transaction
 *
 * @author ddefalco
 */
public interface ITransaction {

    public double getAmount();
    
    public void setAmount(double amount);

    public String getId();

    public void setId(String id);

    public String getIdSourceAgent();

    public void setIdSourceAgent(String idSourceAgent);

    public String getIdTargetAgent();

    public void setIdTargetAgent(String idTargetAgent);

    public int getMonth();
}

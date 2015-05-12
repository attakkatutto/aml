/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent.base;

/**
 * Interface EntityBase
 *
 * @author ddefalco
 */
public interface IAgentBase {

    double getRevenues(int month);

    void setRevenues(double revenue,int month);

    void setCosts(double cost,int month);

    double getCosts(int month);

    double getBudget(int month);

    double getDeficitScore(int month);

    double getFraudScore(int month);

    double getSuspectedScore(int month);

    void initPartners();
    
}

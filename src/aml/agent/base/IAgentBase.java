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

    double getRevenues();

    void setRevenues(double revenue);

    void setCosts(double cost);

    double getCosts();

    double getBudget();

    double getDeficitScore();

    double getFraudScore();

    double getSuspectedScore();

    void initPartners();
    
}

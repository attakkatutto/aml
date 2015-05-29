/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;


/**
 * Interface EntityBase
 *
 * @author ddefalco
 */
public interface INode {

    void setColor();

    AgentBase getAgent();

    void setAgent(AgentBase a);

    void initPartners();

    void initParents();
    
    void initDummies();
    
    double getRevenues(int month);

    void setRevenues(double revenue, int month);

    void setCosts(double cost, int month);

    double getCosts(int month);

    double getBudget(int month);

//    double getGlobalCosts();
//
//    double getGlobalRevenues();    
}

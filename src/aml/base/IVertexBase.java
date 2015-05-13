/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.base.AgentBase;

/**
 * Interface EntityBase
 *
 * @author ddefalco
 */
public interface IVertexBase {

    void setColor();

    AgentBase getAgent();

    void setAgent(AgentBase a);

    void initPartners();

    void initParents();

    double getDeficitScore(int index);

    double getFraudScore();

    double getSuspectedScore();
}

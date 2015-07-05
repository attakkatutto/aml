/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.global;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Enumerations
 *
 * @author ddefalco
 */
public class Enums {

    /**
     * Node types
     */
    public enum NodeType {

        EMPLOYEE,
        FREELANCE,
        SMALLCOMPANY,
        BIGCOMPANY
    };

    /**
     * Persistence mode of the writer
     */
    public enum PersistenceMode {

        @XmlEnumValue("1")
        FILE,
        @XmlEnumValue("2")
        DATABASE,
        @XmlEnumValue("3")
        ALL
    };

    /**
     * State of the agent
     */
    public enum MyAgentState {

        START,
        SEND_FINISH,
        RECEIVE_FINISH
    };

}

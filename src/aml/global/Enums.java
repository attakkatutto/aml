/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.global;

import javax.xml.bind.annotation.XmlEnumValue;


public class Enums{
/**
 *
 * @author ddefalco
 */
public enum NodeType {
    PERSON,
    COMPANY
};


public enum WindowType {
    @XmlEnumValue("1")
    THREEMONTHS,
    @XmlEnumValue("2")
    FOURMONTHS,   
    @XmlEnumValue("3")
    SIXMONTHS
};

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.global;

/**
 * Constants of the Network
 *
 * @author DAVIDE
 */
public final class Constant {    
    
    public static final int MONTHS = 12;   

    /**
     * Default damping factor
     */
    public static final double DEFAULT_DAMPING_FACTOR = 0.85;

    /**
     * Default precision
     */
    public static final double DEFAULT_PRECISION = 1.0e-5;

    /**
     * Default rank attribute
     */
    public static final String DEFAULT_RANK_ATTRIBUTE = "PageRank";

    /**
     * Growth Factor
     */
    public static final double GROW_FACTOR = 1.1;

    /**
     * Node Capacity
     */
    public static final int DEFAULT_NODE_CAPACITY = 128;

    /**
     * Edge Capacity
     */
    public static final int DEFAULT_EDGE_CAPACITY = 1024;
}

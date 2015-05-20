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
    
    /*    
    * Tempo massimo di attesa in ms   
    */
    public static final int MAX_WAITING = 5000;
    
    /*    
    * Numero di mesi    
    */
    public static final int MONTHS = 12;   
    
    
    
    public static final String WRITER_FILENAME = "C:/dbsynthetic_%s.csv";

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

}

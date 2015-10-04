/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.jade.MyPlatformManager;
import aml.global.Config;
import aml.graph.Network;

/**
 * Main class for the network
 *
 * @author ddefalco
 */
public class Main {

    /**
     * Entry point of the AML Synthetic data generator
     *
     * @param args
     */
    public static void main(String[] args) {
        Network graph = new Network("AML Synthetic DB");
        if (Config.instance().isGuiEnabled()) {
            graph.enableGUI();
        }
        graph.build();
        MyPlatformManager f = new MyPlatformManager(graph);
        f.exec();
    }    
}

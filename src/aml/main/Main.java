/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.main;

import aml.graph.Network;

/**
 * Main class for the network
 *
 * @author ddefalco
 */
public class Main {

    public static void main(String[] args) {
        Network graph = new Network("AML Synthetic DB");
        graph.generateBarabasiNetwork();
        graph.setLaunderersAndHonests();
        MyPlatformManager f = new MyPlatformManager(graph);
        f.exec();
    }            
}


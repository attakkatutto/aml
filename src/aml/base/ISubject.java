/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.base;

import aml.agent.MyAgent;

/**
 *
 * @author DAVIDE
 */
public interface ISubject {

    //methods to register and unregister observers
    void registerAgent(IObserver agent, String id);

    public void unregisterAgent(IObserver obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from subject
    public Object getUpdate(IObserver obj);

}

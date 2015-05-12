/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.agent.base.AgentBase;
import static aml.global.Constant.MONTHS;
import static aml.global.Constant.MAX_NUMBER_AGENT_MESSAGES;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davide
 */
public class Sender extends SimpleBehaviour {

    Random random = new Random();
    int count = 0;

    private double getRandomAmount(String name) {
        switch (name.toLowerCase()) {
            case "person":
                return 1000 * random.nextDouble() + 30;
            case "company":
                return 5000 * random.nextDouble() + 20;
            default:
                return 1000 * random.nextDouble() + 30;
        }
    }

    @Override
    public void action() {
        AgentBase base = (AgentBase) myAgent;
        if (!base.isEmptyNeighbour()) {
            for (int i = 0; i < base.sizeNeighbour(); i++) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                int _time = random.nextInt(MONTHS);
                try {
                    msg.addReceiver(new AID(base.getNeighbour(i), AID.ISLOCALNAME));
                    double _amount = getRandomAmount(base.getClass().getName());                    
                    msg.setContentObject(new Transaction(myAgent.getLocalName() + "_" + i + "_" + Instant.now(), _amount, myAgent.getLocalName(), String.valueOf(i), _time));//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));
                    myAgent.send(msg);
                    base.setCosts(_amount,_time);
                    System.out.println(" - "
                            + myAgent.getLocalName()
                            + " send to " + base.getNeighbour(i) + " ->  month " + _time
                            + " amount " + _amount);                    
                } catch (IOException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        count++;
    }

    @Override
    public boolean done() {
        return count == MAX_NUMBER_AGENT_MESSAGES;
    }

}

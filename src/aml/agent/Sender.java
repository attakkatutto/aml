/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.global.Config;
import static aml.global.Constant.MONTHS;
import aml.graph.MyNode;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

/**
 *
 * @author Davide
 */
public class Sender extends SimpleBehaviour {

    Node n;
    Random random = new Random();
    int count = 0;

    public Sender(Node n) {
        this.n = n;
    }

    private double getRandomAmount(String name) {
        switch (name.toLowerCase()) {
            case "person":
                return Config.getInstance().getPersonMean() * random.nextDouble()
                        + Config.getInstance().getPersonStdDev();
            case "company":
                return Config.getInstance().getCompanyMean() * random.nextDouble()
                        + Config.getInstance().getCompanyStdDev();
            default:
                return Config.getInstance().getPersonMean() * random.nextDouble()
                        + Config.getInstance().getPersonStdDev();
        }
    }

    @Override
    public void action() {
        if (n.getOutDegree() > 0) {
            Edge e = n.getLeavingEdge(random.nextInt(n.getOutDegree()));
            MyAgent base = (MyAgent) myAgent;
            MyNode v = e.getTargetNode();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            int _time = random.nextInt(MONTHS);
            try {
                msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
                double _amount = getRandomAmount(base.getClass().getName());
                Transaction t = new Transaction(myAgent.getLocalName() + "_" + v.getId() + "_" + Instant.now(), _amount, myAgent.getLocalName(), v.getId(), _time);
                msg.setContentObject(t);//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));
                myAgent.send(msg);
                v.setCosts(_amount, _time);
                base.getSent().add(t);
                System.out.println(" - "
                        + myAgent.getLocalName()
                        + " send to " + v.getId() + " ->  month " + _time
                        + " amount " + _amount);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            count++;
        }
    }

    @Override
    public boolean done() {
        return count == Config.getInstance().getMaxAgentMessage();
    }

}

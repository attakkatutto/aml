/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.global.Config;
import static aml.global.Constant.MONTHS;
import static aml.global.Constant.MAX_WAITING;
import aml.global.Enums.NodeType;
import aml.graph.MyNode;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;

/**
 *
 * @author Davide
 */
public class Sender extends SimpleBehaviour {
    
    MyNode n;
    Random random = new Random();
    boolean finished = false;
    int count = 0;
    
    public Sender(MyNode n) {
        this.n = n;
    }
    
    private double getRandomAmount(NodeType type) {
        switch (type) {
            case EMPLOYEE:
                return Config.getInstance().getEmployeeMean() * random.nextDouble()
                        + Config.getInstance().getEmployeeStdDev();
            case FREELANCE:
                return Config.getInstance().getFreelanceMean() * random.nextDouble()
                        + Config.getInstance().getFreelanceStdDev();
            case BIGCOMPANY:
                return Config.getInstance().getBigCompanyMean() * random.nextDouble()
                        + Config.getInstance().getBigCompanyStdDev();
            case SMALLCOMPANY:
                return Config.getInstance().getSmallCompanyMean() * random.nextDouble()
                        + Config.getInstance().getSmallCompanyStdDev();
            default:
                return Config.getInstance().getEmployeeMean() * random.nextDouble()
                        + Config.getInstance().getEmployeeStdDev();
        }
    }
    
    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        if (n.getOutDegree() > 0) {
            Edge e = n.getLeavingEdge(random.nextInt(n.getOutDegree()));
            MyNode v = e.getTargetNode();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            int _time = random.nextInt(MONTHS);
            try {
                msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
                double _amount = getRandomAmount(base.getType());
                Transaction t = new Transaction(base.getLocalName() + "_" + v.getId() + "_" + System.currentTimeMillis(), _amount, base.getLocalName(), v.getId(), _time);
                msg.setContentObject(t);//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));
                v.setCosts(_amount, _time);
                v.addSent(t);                
                System.out.println(" - "
                        + base.getLocalName()
                        + " send to " 
                        + v.getId() 
                        + " ->  month " 
                        + _time
                        + " amount " 
                        + _amount);
                base.send(msg);                                                
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            count++;
        }
        block(random.nextInt(MAX_WAITING));
    }
    
    @Override
    public boolean done() {
        return count == Config.getInstance().getMaxAgentMessage();
    }
    
}

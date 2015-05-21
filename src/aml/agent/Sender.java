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
    int count = 0;
    boolean finished = false;

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
        if (n.getOutDegree() > 0 && count < Config.getInstance().getMaxAgentMessage()) {
            //this agent send messages
            Edge e = n.getLeavingEdge(random.nextInt(n.getOutDegree()));
            MyNode v = e.getTargetNode();
            int _time = random.nextInt(MONTHS);
            double _amount = getRandomAmount(base.getType());
            v.setCosts(_amount, _time);
            Transaction t = new Transaction(base.getLocalName() + "_" + v.getId() + "_" + System.currentTimeMillis(),
                    _amount, base.getLocalName(), v.getId(), _time);
            v.addSent(t);
            System.out.println(" - "
                    + base.getLocalName()
                    + " send to "
                    + v.getId()
                    + " ->  month "
                    + _time
                    + " amount "
                    + _amount);
            ACLMessage msg = createSendMessage(t, v.getId());
            base.send(msg);
            count++;
        } //this agent declares FINISH
        else {
            System.out.println(" - "
                    + base.getLocalName()
                    + " declare FINISH ");
            ACLMessage msg = createFinishMessage();
            base.send(msg);
            finished = true;
        }
        block(random.nextInt(MAX_WAITING));
    }

    @Override
    public boolean done() {
        return finished;
    }

    private ACLMessage createFinishMessage() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
        msg.setContent("FINISH");
        for (Edge e : n.getEachLeavingEdge()) {
            MyNode v = e.getTargetNode();
            msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
        }
        for (Edge e : n.getEachEnteringEdge()) {
            MyNode v = e.getSourceNode();
            msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
        }
        return msg;
    }

    private ACLMessage createSendMessage(Transaction t, String idTarget) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        try {
            msg.addReceiver(new AID(idTarget, AID.ISLOCALNAME));
            msg.setContentObject(t);//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));            
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

}

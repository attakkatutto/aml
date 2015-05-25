/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.entity.Transaction;
import aml.base.AgentBase;
import aml.global.Config;
import static aml.global.Constant.MONTHS;
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

    public Sender(AgentBase agent) {
        super(agent);
        this.n = agent.getNode();
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
        if (n.getOutDegree() > 0 && count < Config.getInstance().getNumberAgentMessage()) {
            try {
                //this agent send messages
                MyNode v = n.getLeavingEdge(random.nextInt(n.getOutDegree())).getTargetNode();
                short _time = (short)random.nextInt(MONTHS);
                double _amount = getRandomAmount(base.getType());
                n.setCosts(_amount, _time);
                Transaction t = new Transaction(n.getId() + "_" + v.getId() + "_" + System.currentTimeMillis(),
                        n.getId(), v.getId(),_amount, _time);
                System.out.println(" - "
                        + n.getId()
                        + " send to "
                        + v.getId()
                        + " ->  month "
                        + (_time + 1)
                        + " amount "
                        + _amount);
                ACLMessage msg = createSendMessage(t, v.getId());
                base.send(msg);
                count++;
            } //this agent wait MAX_WAITING ms and then declares FINISH
            catch (Exception ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            System.out.println(" - "
                    + base.getLocalName()
                    + " declare FINISH ");
            ACLMessage msg = createFinishMessage();
            base.send(msg);
            finished = true;
        }        
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

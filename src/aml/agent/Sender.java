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
 * This behaviour handle message for other agents
 * 
 * @author Davide
 */
public class Sender extends SimpleBehaviour {

    MyNode n;
    Random random = new Random();
    int count = 0;
    boolean finished = false;

    public Sender(AgentBase agent, MyNode n) {
        super(agent);
        this.n = n;
    }

    private double getRandomAmount(NodeType type) {
        switch (type) {
            case EMPLOYEE:
                return (n.isHonest()) ? Config.instance().getEmployeeMeanHonest() * random.nextDouble()
                        + Config.instance().getEmployeeStdDevHonest()
                        : Config.instance().getEmployeeMeanLaunderer() * random.nextDouble()
                        + Config.instance().getEmployeeStdDevLaunderer();
            case FREELANCE:
                return (n.isHonest()) ? Config.instance().getFreelanceMeanHonest() * random.nextDouble()
                        + Config.instance().getFreelanceStdDevHonest()
                        : Config.instance().getFreelanceMeanLaunderer() * random.nextDouble()
                        + Config.instance().getFreelanceStdDevLaunderer();
            case BIGCOMPANY:
                return (n.isHonest()) ? Config.instance().getBigCompanyMeanHonest() * random.nextDouble()
                        + Config.instance().getBigCompanyStdDevHonest()
                        : Config.instance().getBigCompanyMeanLaunderer() * random.nextDouble()
                        + Config.instance().getBigCompanyStdDevLaunderer();
            case SMALLCOMPANY:
                return (n.isHonest()) ? Config.instance().getSmallCompanyMeanHonest() * random.nextDouble()
                        + Config.instance().getSmallCompanyStdDevHonest()
                        : Config.instance().getSmallCompanyMeanLaunderer() * random.nextDouble()
                        + Config.instance().getSmallCompanyStdDevLaunderer();
            default:
                return (n.isHonest()) ? Config.instance().getEmployeeMeanHonest() * random.nextDouble()
                        + Config.instance().getEmployeeStdDevHonest()
                        : Config.instance().getEmployeeMeanLaunderer() * random.nextDouble()
                        + Config.instance().getEmployeeStdDevLaunderer();
        }
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        if (n.getOutDegree() > 0 && count < Config.instance().getNumberAgentMessage()) {
            try {
                ACLMessage msg = createSendMessage(n);
                base.send(msg);
                count++;
                block(100);
            } //this agent wait MAX_WAITING ms and then declares FINISH
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {            
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

    private ACLMessage createSendMessage(MyNode n) {
        //this agent send messages
        MyNode v = n.getLeavingEdge(random.nextInt(n.getOutDegree())).getTargetNode();
        short _time = (short) random.nextInt(MONTHS);
        double _amount = getRandomAmount(n.getType());
        n.setCosts(_amount, _time);
        Transaction t = new Transaction(n.getId() + "_" + v.getId() + "_" + System.currentTimeMillis(),
                n.getId(), v.getId(), _amount, _time);
        t.setSourceType(n.getType().name());
        t.setTargetType(v.getType().name());
        System.out.println(" - "
                + n.getId()
                + " send to "
                + v.getId());
//                + " ->  month "
//                + (_time + 1)
//                + " amount "
//                + _amount);
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        try {
            msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
            msg.setContentObject(t);//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

}

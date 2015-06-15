/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.agent;

import aml.entity.Transaction;
import aml.base.AgentBase;
import aml.global.Config;
import static aml.global.Constant.*;
import aml.global.Enums.*;
import aml.graph.MyNode;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
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

    MyNode n; /*Rapresent the node of the netowrk*/

    Random random = new Random(); /*Random class generate number*/

    int count = 0; /*Number of sent messages*/

    boolean finished = false; /*Have you finish?*/


    public Sender(AgentBase agent, MyNode n) {
        super(agent);
        this.n = n;
    }

    /*
     * Get the next Gaussian amount for this type of node
     */
    private double getRandomAmount(NodeType type) {
        switch (type) {
            case EMPLOYEE:
                return (n.isHonest()) ? Config.instance().getEmployeeMeanHonest() + random.nextGaussian()
                        * Config.instance().getEmployeeStdDevHonest()
                        : Config.instance().getEmployeeMeanLaunderer() + random.nextGaussian()
                        * Config.instance().getEmployeeStdDevLaunderer();
            case FREELANCE:
                return (n.isHonest()) ? Config.instance().getFreelanceMeanHonest() + random.nextGaussian()
                        * Config.instance().getFreelanceStdDevHonest()
                        : Config.instance().getFreelanceMeanLaunderer() + random.nextGaussian()
                        * Config.instance().getFreelanceStdDevLaunderer();
            case BIGCOMPANY:
                return (n.isHonest()) ? Config.instance().getBigCompanyMeanHonest() + random.nextGaussian()
                        * Config.instance().getBigCompanyStdDevHonest()
                        : Config.instance().getBigCompanyMeanLaunderer() + random.nextGaussian()
                        * Config.instance().getBigCompanyStdDevLaunderer();
            case SMALLCOMPANY:
                return (n.isHonest()) ? Config.instance().getSmallCompanyMeanHonest() + random.nextGaussian()
                        * Config.instance().getSmallCompanyStdDevHonest()
                        : Config.instance().getSmallCompanyMeanLaunderer() + random.nextGaussian()
                        * Config.instance().getSmallCompanyStdDevLaunderer();
            default:
                return (n.isHonest()) ? Config.instance().getEmployeeMeanHonest() + random.nextGaussian()
                        * Config.instance().getEmployeeStdDevHonest()
                        : Config.instance().getEmployeeMeanLaunderer() + random.nextGaussian()
                        * Config.instance().getEmployeeStdDevLaunderer();
        }
    }

    @Override
    public void action() {
        MyAgent base = (MyAgent) myAgent;
        if (n.getOutDegree() > 0 && count < base.getMESSAGE_NUMBER()) {
            try {
                ACLMessage msg = createSendMessage(n);
                base.send(msg);
                count++;
                block(50);
            } //this agent wait 50 ms before send a new message
            catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            ACLMessage msg = createFinishMessage();
            base.send(msg);
            finished = true;
            if (base.getCurrentState() == MyAgentState.RECEIVE_FINISH) {
                base.doDelete();
            } else {
                base.setState(MyAgentState.SEND_FINISH);
            }
        }
    }

    @Override
    public boolean done() {
        return finished;
    }

    /*
     * Create finish message for neighbours nodes 
     * connected by entering or leaving edges
     */
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

    /*
     * Create a new send message - target node n connected by leaving edge
     */
    private ACLMessage createSendMessage(MyNode n) {
        //this agent send messages
        MyNode v = n.getLeavingEdge(random.nextInt(n.getOutDegree())).getTargetNode();
        short _month = (short) random.nextInt(MONTHS);
        short _year = (short) (random.nextInt(Config.instance().getYearsNumber()) + START_YEAR);
        double _amount = getRandomAmount(n.getType());
        n.setCosts(_amount, _month, _year);
        String _fraud = (n.isHonest()) ? "YES" : "NO";
        Transaction t = new Transaction(n.getId() + "_" + v.getId() + "_" + System.currentTimeMillis(),
                n.getId(), v.getId(), _amount, _month, _year, _fraud);
        t.setSourceType(n.getType().name());
        t.setTargetType(v.getType().name());
        System.out.println(" - "
                + n.getId()
                + " send to "
                + v.getId());
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        try {
            msg.addReceiver(new AID(v.getId(), AID.ISLOCALNAME));
            msg.setContentObject(t);//Content(" message from " + base.getLocalName() + " to " + base.getNeighbour(i));            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }

}

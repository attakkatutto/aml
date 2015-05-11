/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.agent.Company;
import aml.agent.Person;
import aml.agent.base.AgentBase;
import static aml.global.Constant.*;
import aml.global.VertexType;
import aml.graph.base.VertexBase;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractEdge;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.AbstractNode;

/**
 * Network of Vertex connected by Connection
 *
 * @author DAVIDE
 */
public final class Network extends AbstractGraph {

    private final Random random;

    protected HashMap<String, VertexBase> nodeMap;
    protected HashMap<String, Connection> edgeMap;

    public ArrayList<String> persons;
    public ArrayList<String> companies;

    protected VertexBase[] nodeArray;
    protected Connection[] edgeArray;

    protected int nodeCount;
    protected int edgeCount;
    
    protected AgentContainer agentContainer;

    //**** Constructor
    /**
     * Create new instance of Network
     *
     * @param id identifier of the net
     * @param strictChecking
     * @param autoCreate auto create the network
     */
    public Network(String id, boolean strictChecking, boolean autoCreate) {
        super(id, strictChecking, autoCreate);

        random = new Random();

        persons = new ArrayList<>();
        companies = new ArrayList<>();

        nodeMap = new HashMap<>(
                4 * DEFAULT_NODE_CAPACITY / 3 + 1);
        edgeMap = new HashMap<>(
                4 * DEFAULT_EDGE_CAPACITY / 3 + 1);
        nodeArray = new VertexBase[DEFAULT_NODE_CAPACITY];
        edgeArray = new Connection[DEFAULT_EDGE_CAPACITY];
        nodeCount = edgeCount = 0;

        initFactories();
        startJade();
    }

    public Network(String id) {
        this(id, true, false);
    }

    protected void startJade() {
        // Get a hold on JADE runtime
        // Create a default profile
        Profile p = new ProfileImpl();
        // Create a new main container (i.e. on this host, port 1099) 
        Runtime.instance().createMainContainer(p);
        // Create a new non-main container, connecting to the default
        // main container (i.e. on this host, port 1099) 
        agentContainer = Runtime.instance().createAgentContainer(p);        
    }

    /**
     * Initialize the factories of EntityBase and TransactionBase
     */
    private void initFactories() {
        setNodeFactory((String id1, Graph graph) -> {
            if (persons.size() <= 0) {
                persons.add(id1);
                return (VertexBase) new Vertex((AbstractGraph) graph, id1, VertexType.PERSON);
            }
            switch (random.nextInt(3)) {
                case 0:
                    persons.add(id1);
                    return (VertexBase) new Vertex((AbstractGraph) graph, id1, VertexType.PERSON);
                case 1:
                    companies.add(id1);
                    return (VertexBase) new Vertex((AbstractGraph) graph, id1, VertexType.COMPANY);
                default:
                    persons.add(id1);
                    return (VertexBase) new Vertex((AbstractGraph) graph, id1, VertexType.PERSON);
            }
        });

        setEdgeFactory((String id1, Node src, Node dst, boolean directed) -> new Connection(id1, (VertexBase) src, (VertexBase) dst));
    }    

     private void addAgent(VertexBase node) {
        AgentBase agent = null;
        try {          
            switch (node.getType()) {
                case PERSON:
                    agent = new Person();
                    break;
                case COMPANY:
                    agent = new Company("");
                    break;
                default:
                    agent = new Person();
                    break;
            }        
            node.setAgent(agent);
            agentContainer.acceptNewAgent(node.getId(), agent).start();              
        } catch (StaleProxyException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
    private void addNewNeighbour(Connection edge){
        VertexBase _source = edge.getSourceNode();
        VertexBase _target = edge.getTargetNode();
        try {                       
            _source.getAgent().addNeighbour(_target.getId());
        } catch (Exception ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    // *** Callbacks ***
    @Override
    protected void addEdgeCallback(AbstractEdge absedge) {     
        Connection edge = (Connection) absedge;
        edgeMap.put(edge.getId(), edge);
        if (edgeCount == edgeArray.length) {
            Connection[] tmp = new Connection[(int) (edgeArray.length * GROW_FACTOR) + 1];
            System.arraycopy(edgeArray, 0, tmp, 0, edgeArray.length);
            Arrays.fill(edgeArray, null);
            edgeArray = tmp;
        }
        edgeArray[edgeCount] = edge;
        edge.setIndex(edgeCount++);        
        addNewNeighbour(edge);
    }

    @Override
    protected void addNodeCallback(AbstractNode absnode) {
        VertexBase node = (VertexBase) absnode;
        nodeMap.put(node.getId(), node);
        if (nodeCount == nodeArray.length) {
            VertexBase[] tmp = new VertexBase[(int) (nodeArray.length * GROW_FACTOR) + 1];
            System.arraycopy(nodeArray, 0, tmp, 0, nodeArray.length);
            Arrays.fill(nodeArray, null);
            nodeArray = tmp;
        }
        nodeArray[nodeCount] = node;
        node.setIndex(nodeCount++);
        addAgent(node);
    }

    @Override
    protected void removeEdgeCallback(AbstractEdge edge) {
        edgeMap.remove(edge.getId());
        int i = edge.getIndex();
        edgeArray[i] = edgeArray[--edgeCount];
        edgeArray[i].setIndex(i);
        edgeArray[edgeCount] = null;
    }

    @Override
    protected void removeNodeCallback(AbstractNode node) {
        nodeMap.remove(node.getId());
        int i = node.getIndex();
        nodeArray[i] = nodeArray[--nodeCount];
        nodeArray[i].setIndex(i);
        nodeArray[nodeCount] = null;
    }

    @Override
    protected void clearCallback() {
        nodeMap.clear();
        edgeMap.clear();
        Arrays.fill(nodeArray, 0, nodeCount, null);
        Arrays.fill(edgeArray, 0, edgeCount, null);
        nodeCount = edgeCount = 0;
    }

    @Override
    public Connection getEdge(String id) {
        return (Connection) edgeMap.get(id);
    }

    @Override
    public Connection getEdge(int index) {
        if (index < 0 || index >= edgeCount) {
            throw new IndexOutOfBoundsException("Edge " + index
                    + " does not exist");
        }
        return (Connection) edgeArray[index];
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public VertexBase getNode(String id) {
        return (VertexBase) nodeMap.get(id);
    }

    @Override
    public VertexBase getNode(int index) {
        if (index < 0 || index > nodeCount) {
            throw new IndexOutOfBoundsException("Node " + index
                    + " does not exist");
        }
        return (VertexBase) nodeArray[index];
    }

    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    // *** Iterators ***
    /**
     * Iterate the EdgeBase of the Network
     *
     * @param <EdgeBase>
     */
    protected class EdgeIterator<EdgeBase> implements Iterator<EdgeBase> {

        int iNext = 0;
        int iPrev = -1;

        @Override
        public boolean hasNext() {
            return iNext < edgeCount;
        }

        @Override
        public EdgeBase next() {
            if (iNext >= edgeCount) {
                throw new NoSuchElementException();
            }
            iPrev = iNext++;
            return (EdgeBase) edgeArray[iPrev];
        }

        @Override
        public void remove() {
            if (iPrev == -1) {
                throw new IllegalStateException();
            }
            removeEdge(edgeArray[iPrev], true, true, true);
            iNext = iPrev;
            iPrev = -1;
        }
    }

    /**
     * Iterate the NodeBase of the Network
     *
     * @param <NodeBase>
     */
    protected class NodeIterator<NodeBase> implements Iterator<NodeBase> {

        int iNext = 0;
        int iPrev = -1;

        @Override
        public boolean hasNext() {
            return iNext < nodeCount;
        }

        @Override
        public NodeBase next() {
            if (iNext >= nodeCount) {
                throw new NoSuchElementException();
            }
            iPrev = iNext++;
            return (NodeBase) nodeArray[iPrev];
        }

        @Override
        public void remove() {
            if (iPrev == -1) {
                throw new IllegalStateException();
            }
            removeNode(nodeArray[iPrev], true);
            iNext = iPrev;
            iPrev = -1;
        }
    }

    @Override
    public Iterator<Connection> getEdgeIterator() {
        return new EdgeIterator<>();
    }

    @Override
    public Iterator<VertexBase> getNodeIterator() {
        return new NodeIterator<>();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.global.Enums.*;
import aml.base.NodeBase;
import java.util.ArrayList;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.AbstractGraph;

/**
 * Network of Vertex connected by Connection
 *
 * @author DAVIDE
 */
public final class Network extends SingleGraph {

    private final Random random;
   
    public ArrayList<String> persons;
    public ArrayList<String> companies;

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

        initFactories();
    }

    public Network(String id) {
        this(id, true, false);
    }

    /**
     * Initialize the factories of EntityBase and TransactionBase
     */
    private void initFactories() {
        setNodeFactory((String id1, Graph graph) -> {
            if (persons.size() <= 0) {
                persons.add(id1);
                NodeBase base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                base.addAttribute("ui.class", "person");
                return base;
            } else {
                NodeBase base;
                switch (random.nextInt(3)) {
                    case 0:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        base.addAttribute("ui.class", "person");
                        return base;
                    case 1:
                        companies.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.SMALLCOMPANY);
                        base.addAttribute("ui.class", "company");
                        return base;
                    case 2:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.FREELANCE);
                        base.addAttribute("ui.class", "person");
                        return base;
                    case 3:
                        companies.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.BIGCOMPANY);
                        base.addAttribute("ui.style", "company");
                        return base;
                    default:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        base.addAttribute("ui.style", "person");
                        return base;
                }
            }
        });

        setEdgeFactory((String id1, Node src, Node dst, boolean directed)
                -> new Connection(id1, (NodeBase) src, (NodeBase) dst));
    }
}

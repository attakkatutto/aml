/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.global.Enums.*;
import aml.base.NodeBase;
import static aml.global.Enums.NodeType.EMPLOYEE;
import static aml.global.Enums.NodeType.FREELANCE;
import aml.test.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.AbstractNode;
import static org.graphstream.graph.implementations.AdjacencyListGraph.GROW_FACTOR;

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
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        random = new Random();

        persons = new ArrayList<>();
        companies = new ArrayList<>();

        initStyle();
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
                //base.addAttribute("ui.class", "person");
                return base;
            } else {
                NodeBase base;
                switch (random.nextInt(3)) {
                    case 0:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        return base;
                    case 1:
                        companies.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.SMALLCOMPANY);
                        return base;
                    case 2:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.FREELANCE);
                        return base;
                    case 3:
                        companies.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.BIGCOMPANY);
                        return base;
                    default:
                        persons.add(id1);
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        return base;
                }
            }
        });

        setEdgeFactory((String id1, Node src, Node dst, boolean directed)
                -> new Connection(id1, (NodeBase) src, (NodeBase) dst));
    }

    public String readStylesheet() throws IOException {
        File file = new File("res\\MyStyle.css");
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");
        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    private void initStyle() {
        String ss;
        try {
            ss = readStylesheet();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            ss = "";
        }
        setAttribute("stylesheet", ss);
        addAttribute("ui.quality");
        addAttribute("ui.antialias");
    }
}

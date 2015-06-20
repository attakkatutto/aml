/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.graph;

import aml.global.Enums.*;
import aml.global.Config;
import aml.global.Enums;
import aml.main.MyPlatformManager;
import aml.main.MyOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.EdgeFactory;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.implementations.AbstractGraph;

/**
 * Network of Nodes connected by Edges
 *
 * @author DAVIDE
 */
public final class Network extends SingleGraph {

    private final Random random;

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
        if (Config.instance().isGuiEnabled()) {
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            initStyle();
            guiInit();
        }
        initFactories();
    }

    public Network(String id) {
        this(id, true, false);
    }

    /**
     * Initialize the factories of EntityBase and TransactionBase
     */
    private void initFactories() {
        setNodeFactory(new NodeFactory() {
            @Override
            public MyNode newInstance(String id1, Graph graph) {
                MyNode base;
                switch (random.nextInt(3)) {
                    case 0:
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        return base;
                    case 1:
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.SMALLCOMPANY);
                        return base;
                    case 2:
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.FREELANCE);
                        return base;
                    case 3:
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.BIGCOMPANY);
                        return base;
                    default:
                        base = new MyNode((AbstractGraph) graph, id1, NodeType.EMPLOYEE);
                        return base;
                }
            }
        });

        setEdgeFactory(new EdgeFactory() {
            @Override
            public MyEdge newInstance(String id1, Node src, Node dst, boolean directed) {
                return new MyEdge(id1, (MyNode) src, (MyNode) dst);
            }
        });

    }

    public String readStylesheet() throws IOException {
        File file = new File("." + File.separator + "res" + File.separator + "MyStyle.css");
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");
        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine()).append(lineSeparator);
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
            System.out.println(ex.getMessage());
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            ss = "";
        }
        setAttribute("stylesheet", ss);
        addAttribute("ui.quality");
        addAttribute("ui.antialias");
    }
    
    /**
     * Generate random Barabasi Network for the prototype
     */
    public void generateBarabasiNetwork() {
        BarabasiAlbertGenerator b = new BarabasiAlbertGenerator(Config.instance().getMaxEdgesNode(),false);
        b.setDirectedEdges(true, true);
        b.addSink(this);
        b.begin();
        while (getNodeCount() < Config.instance().getNumberOfNode()) {
            try {
                b.nextEvents();
                if (Config.instance().isGuiEnabled()) {
                    for (Node node : getNodeSet()) {
                        node.addAttribute("ui.label", String.format("%s", node.getId()));
                        if (((MyNode) node).getType() == Enums.NodeType.EMPLOYEE
                                || ((MyNode) node).getType() == Enums.NodeType.FREELANCE) {
                            node.addAttribute("ui.class", "person");
                        } else {
                            node.addAttribute("ui.class", "company");
                        }
                    }
                    Thread.sleep(50);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Logger.getLogger(MyPlatformManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        b.end();
    }

    /**
     * Set the number of honests and launderers agents in the network
     */
    public void setLaunderersAndHonests() {
        int numberLaunderer = (Config.instance().getNumberOfNode() * Config.instance().getLaundererPercentage()) / 100;
        List<MyNode> nodes = new ArrayList(getNodeSet());
        Collections.sort(nodes);
        for (int index = 0; index < nodes.size(); index++) {
            MyNode n = nodes.get(index);
            n.setHonest(index >= numberLaunderer);
            if (index >= numberLaunderer && Config.instance().isGuiEnabled()) {
                n.addAttribute("ui.style", "fill-color: rgb(0,255,0);");
            }
        }
    }
    
    /*
     * if GUI is enabled then graph and system.output are rendered
     * in a frame
     */
    private void guiInit() {

        display(true);

        JFrame myFrame = new JFrame("SystemMessages");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(700, 400);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        PrintStream printStream = new PrintStream(new MyOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
        myFrame.getContentPane().add(scroll);
        myFrame.setVisible(true);
    }
}

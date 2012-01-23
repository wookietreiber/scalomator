package main.java.scalax.automata.gui;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphSelectionModel;


public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6921400848602068156L;

	public mxGraph graph;
	Object root;
	
	public GUI (String name) {
		super(name);
	}
	
	public void initGUI(JFrame gui) {
		JMenu menu;
		JMenuItem menuItem;
		JMenuBar menuBar = new JMenuBar();
		JPanel sidePanel, statusBar, subPanel, statesPanel, transitionsPanel, infoPanel, buttonPanel;
		JButton runButton, quitButton;
		JTextField alphabetField, testField;

		// menus
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		// TODO: create image for icon
		menuItem = new JMenuItem("New", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_N);
		menu.add(menuItem);
		// TODO: create image for icon
		menuItem = new JMenuItem("Open", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_O);
		menu.add(menuItem);
		// TODO: create image for icon
		menuItem = new JMenuItem("Save", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_S);
		menu.add(menuItem);
		menu.addSeparator();
		// TODO: create image for icon
		menuItem = new JMenuItem("Quit", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menu.add(menuItem);
		
		menu = new JMenu("Simulation");
		menu.setMnemonic(KeyEvent.VK_S);
		menuBar.add(menu);
		// TODO: create image for icon
		menuItem = new JMenuItem("Run", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_R);
		menu.add(menuItem);
		
		menu = new JMenu("Info");
		menu.setMnemonic(KeyEvent.VK_I);
		menuBar.add(menu);
		// TODO: create image for icon
		menuItem = new JMenuItem("About", new ImageIcon());
		menuItem.setMnemonic(KeyEvent.VK_A);
		menu.add(menuItem);
		
		statusBar = new JPanel();
		sidePanel = new JPanel();
		subPanel = new JPanel();
		buttonPanel = new JPanel();
		statesPanel = new JPanel();
		transitionsPanel = new JPanel();
		infoPanel = new JPanel();
		
		sidePanel.setLayout(new BorderLayout());
		sidePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		statesPanel.setLayout(new BorderLayout());
		statesPanel.setBorder(BorderFactory.createTitledBorder("States:"));
		transitionsPanel.setLayout(new BorderLayout());
		transitionsPanel.setBorder(BorderFactory.createTitledBorder("Transitions:"));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		alphabetField = new JTextField();
		alphabetField.setEditable(false);
		testField = new JTextField();
		infoPanel.add(new JLabel("Alphabet:"));
		infoPanel.add(alphabetField);
		infoPanel.add(new JLabel("Word to test:"));
		infoPanel.add(testField);
		
		runButton = new JButton("Run");
		quitButton = new JButton("Quit");
		buttonPanel.add(runButton);
		buttonPanel.add(quitButton);
		
		sidePanel.add(subPanel, BorderLayout.NORTH);
		sidePanel.add(buttonPanel, BorderLayout.SOUTH);
		subPanel.add(statesPanel);
		subPanel.add(transitionsPanel);
		subPanel.add(infoPanel);
		
		// TODO: remove status stub
		JLabel status = new JLabel("StatusBar");
		statusBar.add(status);	
		
		gui.setJMenuBar(menuBar);
		gui.setLayout(new BorderLayout());
		gui.add(statusBar, BorderLayout.SOUTH);
		gui.add(sidePanel, BorderLayout.EAST);
		gui.add(initGraph(), BorderLayout.CENTER);
	}
	
	public JComponent initGraph() {
		graph = new mxGraph();
		final mxGraphComponent graphComponent = new mxGraphComponent(graph);
		
		// dangling edges are bad and result in all kinds of nasty things
		graph.setAllowDanglingEdges(false);
		root = graph.getDefaultParent();
		
		// a movement listener for any amount of selected cells
	    graph.addListener(mxEvent.CELLS_MOVED, new mxIEventListener() {
	        @Override
	        public void invoke(Object sender, mxEventObject evt) {
	            if (sender instanceof mxGraph) {
	            	System.out.println("Movement happened for:");
	                for (Object cell : ((mxGraph)sender).getSelectionCells()) {
	                	// TODO: update cell geometry attributes in lists 
	                    System.out.println("cell=" + graph.getLabel(cell));
	                }
	            }
	        }
	    });

	    // handle mouse right-click events for adding cells or changing cells
	    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
	    			Object cell = graphComponent.getCellAt(e.getX(), e.getY());
	    			System.out.println("Right mouse click in:");
	    			if (cell != null) {
	    				// TODO: offer right-click menu for changing attributes
	    				System.out.println("cell=" + graph.getLabel(cell));
	    			}
	    			else {
	    				// TODO: offer right-click menu for new cells
	    				System.out.println("in empty space");
	    			}
	    		}
	    	}
	    });
		
		graph.getModel().beginUpdate();
		try
		{
			Object v1 = graph.insertVertex(root, null, "Hello", 20, 20, 80,
					30, "shape=ellipse;perimeter=ellipsePerimeter");
			Object v2 = graph.insertVertex(root, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(root, null, "Edge", v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		
		return graphComponent;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI gui = new GUI("Scalomator - Simulate finite-state machines");
		gui.initGUI(gui);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(640, 480);
		gui.setVisible(true);
		
		// some test for vertex and edge grabbing
//		System.out.println(Arrays.toString(gui.graph.getChildCells(gui.root, true, false)));
//		System.out.println(Arrays.toString(gui.graph.getChildCells(gui.root, false, true)));
	}

}

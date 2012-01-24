package main.java.scalax.automata.gui;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

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
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.MenuElement;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	/**
	 * 
	 */
	private final static int INITIAL_STATE = 0;
	private final static int NORMAL_STATE = 1;
	private final static int END_STATE = 2;

	public mxGraph graph;
	public Object root;
	public JPopupMenu popup;
	
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

	    //Create the popup menu.
	    popup = new JPopupMenu();
	    menuItem = new JMenuItem("Add state");
	    popup.add(menuItem);
	    menuItem = new JMenuItem("Remove");
	    popup.add(menuItem);
	    menuItem = new JMenuItem("Change state");
	    popup.add(menuItem);
		
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
		sidePanel.setBorder(BorderFactory.createEtchedBorder());
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
		// edge source and target are the same
		graph.setAllowLoops(true);
		// don't need this
		graph.setCellsResizable(false);
		// dragging edge to empty space creates a new shape
		graphComponent.getConnectionHandler().setCreateTarget(true);

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
	    
	    // a cell add listener
	    graph.addListener(mxEvent.CELLS_ADDED, new mxIEventListener() {
	        @Override
	        public void invoke(Object sender, mxEventObject evt) {
	            if (sender instanceof mxGraph) {
	            	Object[] cells=(Object[]) evt.getProperty("cells");
					for (Object cell : cells) {
						if (cell instanceof mxCell) {
							Object shape = graph.getCellStyle(cell).get("shape");
//							if (shape.toString().equals("initialShape")) {
//								System.out.println("init");
//							}
//							else if (shape.toString().equals("ellipse")) {
//								System.out.println("normal");
//							}
//							else if (shape.toString().equals("doubleEllipse")) {
//								System.out.println("end");
//							}
//							else if (shape.toString().equals("connector")) {
//								System.out.println("connector");
//							}
						}
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
	    			if (cell != null) {
	    				Object shape = graph.getCellStyle(cell).get("shape");
	    				
	    				if (shape.toString().equals("connector")) {
	    					popup.getComponent(0).setEnabled(false);
	    					popup.getComponent(1).setEnabled(true);
	    					popup.getComponent(2).setEnabled(false);
	    				}
	    				else {
	    					popup.getComponent(0).setEnabled(false);
	    					popup.getComponent(1).setEnabled(true);
	    					popup.getComponent(2).setEnabled(true);
	    				}
	    			}
	    			else {
	    				popup.getComponent(0).setEnabled(true);
	    				popup.getComponent(1).setEnabled(false);
	    				popup.getComponent(2).setEnabled(false);
	    			}
    				popup.show(e.getComponent(), e.getX(), e.getY());
	    		}
	    	}
	    });
	    
	    // add customized shape to list of available shapes
	    mxGraphics2DCanvas.putShape("initialShape", new initialStateShape());

		return graphComponent;
	}
	
	Object addState(String name, int x, int y, int radius, int type) {
		Object state = null;
		graph.getModel().beginUpdate();
		try
		{
			switch (type) {
			case INITIAL_STATE:
				state = graph.insertVertex(root, null, name, x, y, radius,
						radius, "shape=initialShape;perimeter=ellipsePerimeter");
				break;
			case NORMAL_STATE:
				state = graph.insertVertex(root, null, name, x, y, radius,
						radius, "shape=ellipse;perimeter=ellipsePerimeter");
				break;
			case END_STATE:
				state = graph.insertVertex(root, null, name, x, y, radius,
						radius, "shape=doubleEllipse;perimeter=ellipsePerimeter");
				break;
			}
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		// TODO: add state to appropriate list
		return state;
	}
	
	public void addTransition(String name, Object source, Object target) {
		graph.getModel().beginUpdate();
		try
		{
			graph.insertEdge(root, null, name, source, target);
		}
		finally
		{
			graph.getModel().endUpdate();
		}
		// TODO: add transition to appropriate list
	}
	
	public void loadAutomata() {
		// TODO: loading from file ore scala here
		
		// FIXME: remove test automata
		Object v1 = addState("first", 50, 50, 80, INITIAL_STATE);
		Object v2 = addState("second", 350, 50, 80, NORMAL_STATE);
		Object v3 = addState("third", 200, 200, 80, END_STATE);
		addTransition("E1", v1, v2);
		addTransition("E1", v2, v3);
		addTransition("E1", v3, v3);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI gui = new GUI("Scalomator - Simulate finite-state machines");
		gui.initGUI(gui);
		// FIXME: remove test load
		gui.loadAutomata();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(640, 480);
		gui.setVisible(true);
		
		// some test for vertex and edge grabbing
//		System.out.println(Arrays.toString(gui.graph.getChildCells(gui.root, true, false)));
//		System.out.println(Arrays.toString(gui.graph.getChildCells(gui.root, false, true)));
	}

}

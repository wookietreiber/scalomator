/* **************************************************************************
 *                                                                          *
 *  Copyright (C)  2011-2012  Peter Kossek, Nils Foken, Christian Krause    *
 *                                                                          *
 *  Peter Kossek     <peter.kossek@it2009.ba-leipzig.de>                    *
 *  Nils Foken       <nils.foken@it2009.ba-leipzig.de>                      *
 *  Christian Krause <christian.krause@it2009.ba-leipzig.de>                *
 *                                                                          *
 ****************************************************************************
 *                                                                          *
 *  This file is part of 'scalomator'.                                      *
 *                                                                          *
 *  This project is free software: you can redistribute it and/or modify    *
 *  it under the terms of the GNU General Public License as published by    *
 *  the Free Software Foundation, either version 3 of the License, or       *
 *  any later version.                                                      *
 *                                                                          *
 *  This project is distributed in the hope that it will be useful,         *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 *  GNU General Public License for more details.                            *
 *                                                                          *
 *  You should have received a copy of the GNU General Public License       *
 *  along with this project. If not, see <http://www.gnu.org/licenses/>.    *
 *                                                                          *
 ****************************************************************************/


package scalax.automata.gui;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


public class GUI extends JFrame {

	private static final long serialVersionUID = 6921400848602068156L;

	public GUI (String name) {
		super(name);

		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try
		{
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI frame = new GUI("Test GUI Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}

}

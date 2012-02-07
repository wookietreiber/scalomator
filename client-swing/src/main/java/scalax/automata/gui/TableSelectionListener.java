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

package main.java.scalax.automata.gui;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.mxgraph.model.mxCell;

public class TableSelectionListener implements ListSelectionListener {

	JTable table;
	GUI gui;
	
	public TableSelectionListener(JTable table, GUI gui) {
		super();
		this.table = table;
		this.gui = gui;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		if (e.getValueIsAdjusting()) {

		}
		// row selected
		if (source.equals(table.getSelectionModel())
				&& table.getRowSelectionAllowed()) {
			TableModel tableModel = table.getModel();
			int[] selectedRows = table.getSelectedRows();
			gui.graph.clearSelection();
			if (tableModel instanceof StateTableModel) {
				StateTableModel stateTableModel = (StateTableModel) tableModel;
				for (int i : selectedRows) {
					mxCell cell = stateTableModel.getCellAt(i);
					gui.graph.getSelectionModel().addCell(cell);
				}
			}
			if (tableModel instanceof TransitionTableModel) {
				gui.graph.clearSelection();
				TransitionTableModel transitionTableModel = (TransitionTableModel) tableModel;
				for (int i : selectedRows) {
					mxCell cell = transitionTableModel.getCellAt(i);
					gui.graph.getSelectionModel().addCell(cell);
				}
			}
		}
		// column selected
		else if (source.equals(table.getColumnModel())
				&& table.getColumnSelectionAllowed()) {

		}
	}
}

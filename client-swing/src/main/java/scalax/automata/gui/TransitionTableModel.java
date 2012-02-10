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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.table.AbstractTableModel;
import com.mxgraph.model.mxCell;

/**
 * A table model that contains the data of the transitions
 */
@SuppressWarnings("serial")
public class TransitionTableModel extends AbstractTableModel {

	private ArrayList<mxCell> edge = new ArrayList<mxCell>();
	private GUI gui;

	/**
	 * Creates a new TransitionTableModel.
	 * @param gui The GUI where this TableModel is used.
	 */
	public TransitionTableModel(GUI gui) {
		this.gui = gui;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return edge.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return ((mxCell) edge.get(rowIndex).getSource()).getValue();
		} else if (columnIndex == 1) {
			return edge.get(rowIndex).getValue();
		} else {
			return ((mxCell) edge.get(rowIndex).getTarget()).getValue();
		}

	}

	/**
	 * Appends a transition to the model.
	 * @param edge The transtition to add.
	 */
	public void appendValue(mxCell edge) {
		this.edge.add(edge);
		fireTableDataChanged();
	}

	/**
	 * Removes a transition from the model.
	 * @param edge The transition to remove.
	 */
	public void removeValue(mxCell edge) {
		this.edge.remove(edge);
		fireTableDataChanged();
	}
	
	/**
	 * Creates a set of the labels of all transitions in this model (automaton's alphabet).
	 * @return Set of labels of all transitions.
	 */
	public String getTransitionValues() {
		HashSet<String> transitionValues = new HashSet<String>();
		for (mxCell cell : edge) {
			transitionValues.add(cell.getValue().toString());
		}
		ArrayList<String> stringValues = new ArrayList<String>(transitionValues);
		Collections.sort(stringValues);
		String[] strings = new String[stringValues.size()];
		stringValues.toArray(strings);
		String oneString = Arrays.toString(strings); 
		return oneString.substring(1, oneString.length() - 1);
	}

	/**
	 * Returns transition at specific index in model.
	 * @param index Position of the transition to return.
	 * @return Transition at the index.
	 */
	public mxCell getCellAt(int index) {
		return edge.get(index);
	}

	/**
	 * Returns the index of the transition.
	 * @param cell Transition whose index shall be returned.
	 * @return Index of the transition inside the model.
	 *         <code>-1</code> if the transition is not element of this model.
	 */
	public int getIndexOf(mxCell cell) {
		return edge.indexOf(cell);
	}
	
	/**
	 * If the cell is editable.
	 * @param rowIndex Row in the table.
	 * @param columnIndex Column in the table.
	 * @return <code>true</code> if the cell is editable, <code>false</code> otherwise.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Replaces the label of the transition.
	 * @param aValue The new label.
	 * @param rowIndex Row in the table.
	 * @param columnIndex Column in the table.
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		mxCell cell = edge.get(rowIndex);
		if (columnIndex == 1) {
			gui.graphComponent.labelChanged(cell, aValue, null);
		}
	}

}

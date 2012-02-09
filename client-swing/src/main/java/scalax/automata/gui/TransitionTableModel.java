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
 * A table model that contains the names of the source and target states as well
 * as the name of the transition
 */
@SuppressWarnings("serial")
public class TransitionTableModel extends AbstractTableModel {

	private ArrayList<mxCell> edge = new ArrayList<mxCell>();
	private GUI gui;

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

	public void appendValue(mxCell edge) {
		this.edge.add(edge);
		fireTableDataChanged();
	}

	public void removeValue(mxCell edge) {
		this.edge.remove(edge);
		fireTableDataChanged();
	}
	
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

	public mxCell getCellAt(int index) {
		return edge.get(index);
	}

	public int getIndexOf(mxCell cell) {
		return edge.indexOf(cell);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		mxCell cell = edge.get(rowIndex);
		if (columnIndex == 1) {
			gui.graphComponent.labelChanged(cell, aValue, null);
		}
	}

}

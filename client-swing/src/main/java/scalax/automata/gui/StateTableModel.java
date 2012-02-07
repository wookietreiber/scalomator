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

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.mxgraph.model.mxCell;

/**
 * A table model that simply contains the names of the states in
 * a single column
 */
@SuppressWarnings("serial")
public class StateTableModel extends AbstractTableModel {

//	private static final String SHAPE_STYLE = SHAPE;
	private ArrayList<mxCell> data = new ArrayList<mxCell>();
	private GUI gui;
	
	public StateTableModel(GUI gui) {
		this.gui = gui;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			mxCell cell = data.get(rowIndex);
			String[] style = cell.getStyle().split(";|=");
			for (int i=0; i<style.length; i++) {
				if (style[i].equals(GUI.SHAPE)) {
					if (style[i+1].equals(GUI.INITIAL_STATE)) {
						return "inital state";
					} else if (style[i+1].equals(GUI.END_STATE)) {
						return "end state";
					} else if (style[i+1].equals(GUI.MULTI_STATE)) {
						return "initial + end state";
					} else {
						return "";
					}
					
				}
			}
			return "";
			
		}
		else {
			return data.get(rowIndex).getValue();
		}
	}
	
	public void appendValue(mxCell value) {
		data.add(value);
		fireTableDataChanged();
	}
	
	public boolean removeValue(mxCell value) {
		boolean removed = data.remove(value);
		fireTableDataChanged();
		return removed;
	}
	
	public mxCell getCellAt(int index) {
		return data.get(index);
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
		mxCell cell = data.get(rowIndex);
		if (columnIndex == 1) {
			gui.graphComponent.labelChanged(cell, aValue, null);
		}
	}
}

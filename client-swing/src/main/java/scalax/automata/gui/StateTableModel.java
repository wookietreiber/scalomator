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
import javax.swing.table.AbstractTableModel;
import com.mxgraph.model.mxCell;

/**
 * A table model that simply contains states
 */
@SuppressWarnings("serial")
public class StateTableModel extends AbstractTableModel {

//	private static final String SHAPE_STYLE = SHAPE;
	private ArrayList<mxCell> data = new ArrayList<mxCell>();
	private GUI gui;
	
	/**
	 * Creates new TableModel.
	 * @param gui The GUI this TableModel is used in.
	 */
	public StateTableModel(GUI gui) {
		this.gui = gui;
	}

	/**
	 * Returns number of columns
	 * @return Number of columns
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}

	/**
	 * Returns number of rows
	 * @return Number of rows
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Returns object for cell
	 * @param rowIndex row of the cell
	 * @param columnIndex column of the cell
	 * @return Object to be displayed in the cell
	 */
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
	
	/**
	 * Appends state to model.
	 * @param value State to append.
	 */
	public void appendValue(mxCell value) {
		data.add(value);
		fireTableDataChanged();
	}
	
	/**
	 * Removes state from model.
	 * @param value State to remove.
	 * @return <code>true</code> if state was removed successfully, otherwise <code>false</code>.
	 */
	public boolean removeValue(mxCell value) {
		boolean removed = data.remove(value);
		fireTableDataChanged();
		return removed;
	}
	
	/**
	 * Returns state at specified index.
	 * @param index Position of state inside model.
	 * @return State at given position.
	 */
	public mxCell getCellAt(int index) {
		return data.get(index);
	}
	
	/**
	 * Returns if cell can be edited by user.
	 * @param rowIndex Row of the cell
	 * @param columnIndex Column of the cell
	 * @return <code>true</code> if cell is editable, otherwise <code>false</code>.
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
	 * Replaces label of the state displaye in the row.
	 * @param aValue New label for the state.
	 * @param rowIndex Row inside the table.
	 * @param columnIndex Column inside the table.
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		mxCell cell = data.get(rowIndex);
		if (columnIndex == 1) {
			gui.graphComponent.labelChanged(cell, aValue, null);
		}
	}
}

package main.java.scalax.automata.gui;

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

}

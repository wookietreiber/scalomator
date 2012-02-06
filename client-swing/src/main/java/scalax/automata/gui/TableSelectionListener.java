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
		// row selected
		if (source.equals(table.getSelectionModel()) &&
				table.getRowSelectionAllowed()) {
			TableModel tableModel = table.getModel();
			int[] selectedRows = table.getSelectedRows();
			gui.graph.clearSelection();
			if (tableModel instanceof StateTableModel) {
				StateTableModel stateTableModel = (StateTableModel) tableModel;
				for (int i:selectedRows) {
					mxCell cell = stateTableModel.getCellAt(i);
					gui.graph.getSelectionModel().addCell(cell);
				}
			}
			if (tableModel instanceof TransitionTableModel) {
				gui.graph.clearSelection();
				TransitionTableModel transitionTableModel = (TransitionTableModel) tableModel;
				for (int i:selectedRows) {
					mxCell cell = transitionTableModel.getCellAt(i);
					gui.graph.getSelectionModel().addCell(cell);
				}
			}
		} 
		// column selected
		else if (source.equals(table.getColumnModel()) &&
				table.getColumnSelectionAllowed()) {
			
		}
	}
}

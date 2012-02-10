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


package scalax.automata
package gui

import javax.swing.table.AbstractTableModel
import com.mxgraph.model.mxCell
import scala.collection.immutable.TreeSet

/** A table model that contains the names of the source and target states as
  * well as the name of the transition
  */
class TransitionTableModel(gui: GUI) extends AbstractTableModel {

  private val edges = collection.mutable.ArrayBuffer[mxCell]()

  override def getColumnCount = 3
  override def getRowCount    = edges.size

  override def getValueAt(row: Int, col: Int) = col match {
    case 0 => edges(row).getSource.asInstanceOf[mxCell].getValue
    case 1 => edges(row).getValue
    case _ => edges(row).getTarget.asInstanceOf[mxCell].getValue
  }

  def appendValue(edge: mxCell) {
    edges += edge
    fireTableDataChanged()
  }

  def removeValue(edge: mxCell) {
    edges -= edge
    fireTableDataChanged()
  }

  def getTransitionValues =
    TreeSet(edges.map(_.getValue.toString): _*) mkString " "

  def getCellAt(i: Int) = edges(i)

  def getIndexOf(c: mxCell) = edges indexOf c

  override def isCellEditable(row: Int, col: Int) =
    if (col == 1) true else false

  override def setValueAt(aValue: Object, row: Int, col: Int) = if (col == 1)
    gui.graphComponent.labelChanged(edges(row), aValue, null)

}

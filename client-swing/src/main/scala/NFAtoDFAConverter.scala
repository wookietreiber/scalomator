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

import java.util.{HashMap, ArrayList}
import java.util.concurrent.ExecutionException
import javax.swing._
import scala.collection.JavaConversions._
import scala.xml._
import GUI._

class NFAtoDFAConverter(
    init: HashMap[String,String],
    fs: ArrayList[HashMap[String,String]],
    ts: ArrayList[HashMap[String,String]],
    gui: GUI
  ) extends SwingWorker[DeterministicFiniteAutomaton[String,Set[String]],Unit] {

  override def doInBackground() = {
    val tx = ts map { t =>
      t.get("source") -> t.get("input") -> t.get("target")
    }
    val sis = tx map { _._1 } toSet
    val tfs = sis map { x =>
      x -> (tx filter { _._1 == x } map { _._2 } toSet)
    } toMap

    if (tfs.values.forall { _.size == 1 })
      sys.error("This is already a deterministic finite automaton!")

    NFA[String,String](
      init.get("name"),
      fs map { _.get("name") } toSet,
      tfs
    ).toDFA
  }

  override protected def done() = try {
    val dfa = get();

    gui.removeAllCells();

    var nodes = Map[String,Object]();

    dfa.states foreach { s =>
      val node = if (dfa.initialState == s && dfa.finalStates.contains(s))
        gui.addState(s.toString, -1, -1, CELL_RADIUS, MULTI_STATE)
      else if (dfa.initialState == s)
        gui.addState(s.toString, -1, -1, CELL_RADIUS, INITIAL_STATE)
      else if (dfa.finalStates.contains(s))
        gui.addState(s.toString, -1, -1, CELL_RADIUS, END_STATE)
      else
        gui.addState(s.toString, -1, -1, CELL_RADIUS, NORMAL_STATE)

      nodes += (s.toString -> node)
    }

    dfa.transitions foreach { t =>
      gui.addTransition(t._1._2, nodes(t._1._1.toString), nodes(t._2.toString))
    }
  } catch {
    case e: ExecutionException => gui.setStatusMessage(e.getCause.getMessage)
    case e                     => gui.setStatusMessage(e.getMessage)
    case e => gui.setStatusMessage(e.getMessage)
  }

}

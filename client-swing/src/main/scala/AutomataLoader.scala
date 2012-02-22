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

import java.io._
import java.util.concurrent.ExecutionException
import javax.swing._
import scala.collection.JavaConversions._
import scala.xml._
import GUI._

class AutomataLoader(file: String, gui: GUI)
  extends SwingWorker[FiniteStateMachine[String,String,Set[String]],Unit] {

  override def doInBackground() =
    FiniteStateMachine(XML.loadFile(file))

  override protected def done() = try {
    val fsm = get()

    var nodes = Map[String,Object]()

    fsm.states foreach { s =>
      val node = if (fsm.initialState == s && fsm.finalStates.contains(s))
        gui.addState(s, -1, -1, CELL_RADIUS, MULTI_STATE)
      else if (fsm.initialState == s)
        gui.addState(s, -1, -1, CELL_RADIUS, INITIAL_STATE)
      else if (fsm.finalStates.contains(s))
        gui.addState(s, -1, -1, CELL_RADIUS, END_STATE)
      else
        gui.addState(s, -1, -1, CELL_RADIUS, NORMAL_STATE)

      nodes += (s -> node)
    }

    fsm.transitions foreach { t =>
      t._2 foreach { end =>
        gui.addTransition(t._1._2, nodes(t._1._1), nodes(end))
      }
    }
    
    gui.clearStatus()
    gui.graphComponent.refresh()
  } catch {
    case e: ExecutionException => gui.setStatusMessage(e.getCause.getMessage)
    case e                     => gui.setStatusMessage(e.getMessage)
  }

}

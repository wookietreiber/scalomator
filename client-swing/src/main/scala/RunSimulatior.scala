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

import java.util._
import javax.swing._
import scala.collection.JavaConversions._

class RunSimulator(
    init: HashMap[String,String],
    fs: ArrayList[HashMap[String,String]],
    ts: ArrayList[HashMap[String,String]],
    word: String,
    gui: GUI
  ) extends SwingWorker[Boolean,Unit] {

  override def doInBackground() = {
    val tx = ts map { t =>
      t.get("source") -> t.get("input") -> t.get("target")
    }
    val sis = tx map { _._1 } toSet
    val tfs = sis map { x =>
      x -> (tx filter { _._1 == x } map { _._2 } toSet)
    } toMap

    FSM[String,String](
      init.get("name"),
      fs map { _.get("name") } toSet,
      tfs
    ).accepts(word map { _ toString }: _*)
  }

  override protected def done() {
    gui.setStatusMessage(
      if (get) "Word is accepted." else "Word is not accepted."
    )
  }

}

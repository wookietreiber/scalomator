/* **************************************************************************
 *                                                                          *
 *  Copyright (C)  2011  Peter Kossek, Nils Foken, Christian Krause         *
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

package scalax.automata.gui

import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame
import java.awt.Toolkit
import java.awt.Dimension

/**
 * Main class for scalomator's GUI.
 */
object AutomataGUI extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scalomator"
    minimumSize = new Dimension(640,480)
    centerOnScreen()
  }
}
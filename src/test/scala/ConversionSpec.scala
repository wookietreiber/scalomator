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

import org.specs2._

class ConversionSpec extends Specification { def is =

  // -----------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------

  "Conversion Specification"                                                  ^
                                                                             p^
  "NFA to DFA examples"                                                       ^
    "1st example"                 ! nfa2dfa1                                  ^
    "2nd example"                 ! nfa2dfa2                                  ^
                                                                            end
  // -----------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------

  def nfa2dfa1 = NFA(0, Set(3), Map(
    0 -> 0 -> Set(0,1),
    0 -> 1 -> Set(0),
    1 -> 0 -> Set(2),
    1 -> 1 -> Set(2),
    2 -> 0 -> Set(3),
    2 -> 1 -> Set(3)
  )).toDFA must_== DFA(Set(0), Set(Set(0,3), Set(0,1,3), Set(0,2,3), Set(0,1,2,3)), Map(
    Set(0)       -> 0 -> Set(0,1),
    Set(0)       -> 1 -> Set(0),
    Set(0,1)     -> 0 -> Set(0,1,2),
    Set(0,1)     -> 1 -> Set(0,2),
    Set(0,2)     -> 0 -> Set(0,1,3),
    Set(0,2)     -> 1 -> Set(0,3),
    Set(0,3)     -> 0 -> Set(0,1),
    Set(0,3)     -> 1 -> Set(0),
    Set(0,1,2)   -> 0 -> Set(0,1,2,3),
    Set(0,1,2)   -> 1 -> Set(0,2,3),
    Set(0,1,3)   -> 0 -> Set(0,1,2),
    Set(0,1,3)   -> 1 -> Set(0,2),
    Set(0,2,3)   -> 0 -> Set(0,1,3),
    Set(0,2,3)   -> 1 -> Set(0,3),
    Set(0,1,2,3) -> 0 -> Set(0,1,2,3),
    Set(0,1,2,3) -> 1 -> Set(0,2,3)
  ))

  def nfa2dfa2 = NFA(0, Set(3), Map(
    0 -> "a" -> Set(0,1),
    0 -> "b" -> Set(0),
    1 -> "b" -> Set(2),
    2 -> "a" -> Set(3)
  )).toDFA must_== DFA(Set(0), Set(Set(0,1,3)), Map(
    Set(0) -> "a" -> Set(0,1),
    Set(0) -> "b" -> Set(0),
    Set(0,1) -> "a" -> Set(0,1),
    Set(0,1) -> "b" -> Set(0,2),
    Set(0,2) -> "a" -> Set(0,1,3),
    Set(0,2) -> "b" -> Set(0),
    Set(0,1,3) -> "a" -> Set(0,1),
    Set(0,1,3) -> "b" -> Set(0,2)
  ))

}

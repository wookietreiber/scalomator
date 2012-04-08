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

class AcceptanceSpec extends Specification { def is =

  // -----------------------------------------------------------------------
  // fragments
  // -----------------------------------------------------------------------

  "Acceptance Specification"                                                  ^
                                                                             p^
  "DFA examples"                                                              ^
    "1st example"                 ! dfaAccepts(0,0,0,1)                       ^
    "2nd example"                 ! dfaRejects(0,0,0)                         ^
                                                                             p^
  "NFA examples"                                                              ^
    "1st example"                 ! nfaAccepts(0,0,0)                         ^
    "2nd example"                 ! nfaRejects(0,0)                           ^
                                                                            end
  // -----------------------------------------------------------------------
  // tests
  // -----------------------------------------------------------------------

  def dfaAccepts(word: Int*) = dfa.accepts(word: _*) must_== true
  def dfaRejects(word: Int*) = dfa.accepts(word: _*) must_== false

  def nfaAccepts(word: Int*) = nfa.accepts(word: _*) must_== true
  def nfaRejects(word: Int*) = nfa.accepts(word: _*) must_== false

  // -----------------------------------------------------------------------
  // sample automatons
  // -----------------------------------------------------------------------

  def dfa = DFA(0, Set(3,4), Map(
    0 -> 0 -> 1,            0 -> 1 -> 2,
    1 -> 0 -> 1,            1 -> 1 -> 3,
    2 -> 0 -> 2,            2 -> 1 -> 4,
    3 -> 0 -> 2,            3 -> 1 -> 4,
    4 -> 0 -> 1,            4 -> 1 -> 3
  ))

  def nfa = NFA(0, Set(3), Map(
    0 -> 0 -> Set(0,1),     0 -> 1 -> Set(0),
    1 -> 0 -> Set(2),       1 -> 1 -> Set(2),
    2 -> 0 -> Set(3),       2 -> 1 -> Set(3)
  ))

}

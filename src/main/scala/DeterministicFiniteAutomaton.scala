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


package scalax.automata

/** Represents a deterministic finite automaton (DFA). For ease of use you may
  * use it as `DFA` as well by importing the whole `scalax.automata` package:
  *
  * {{{
  *   scala> import scalax.automata._
  *   import scalax.automata._
  *
  *   scala> new DFA(...)
  * }}}
  *
  * @param states   states of this automaton, may not be empty
  * @param alphabet input alphabet, may not be empty
  * @param delta    state-transition function
  * @param init     initial state, must be an element of `states`
  * @param finals   final states, must be a subset of `states`
  */
case class DeterministicFiniteAutomaton(
    states: Set[String],
    alphabet: Set[String],
    delta: (String, String) => String,
    init: String,
    finals: Set[String]) {

  require(states nonEmpty, "There are no states.")

  require(alphabet nonEmpty, "There is no input alphabet.")

  require(states contains init,
    "Start state (%s) not contained in states (%s)." format (init, states))

  require(finals forall { states contains _ },
    "One of the final states is not a state.")

  /** Returns the equivalent minimum DFA. */
  def minimize: DeterministicFiniteAutomaton = ???

}

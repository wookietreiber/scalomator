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

/** Factory for [[scalax.automata.DeterministicFiniteAutomaton]] instances. */
object DeterministicFiniteAutomaton {

  /** Returns a new deterministic finite automaton.
    *
    * @tparam A alphabet type
    * @tparam S state type
    *
    * @param states       states
    * @param alphabet     input alphabet
    * @param initialState initial state
    * @param finalStates  final states
    * @param transitions  state-transition function
    */
  def apply[A,S](states: Set[S], alphabet: Set[A], initialState: S, finalStates: Set[S])
                (transitions: PartialFunction[(S,A),S]) =
    new DeterministicFiniteAutomaton(states, alphabet, initialState, finalStates, transitions)

}

/** Represents a deterministic finite automaton (DFA). For ease of use you may
  * import the whole [[scalax.automata]] package and just need to type in the
  * shorter alias `DFA`:
  *
  * {{{
  *   scala> import scalax.automata._
  *   import scalax.automata._
  *
  *   scala> DFA(Set(1,2), Set("a"), 1, Set(2)) {
  *        |   case (1,"a") => 2
  *        |   case (2,"a") => 2
  *        | }
  *   res0: scalax.automata.DeterministicFiniteAutomaton[java.lang.String,Int] = ...
  * }}}
  *
  * @tparam A alphabet type
  * @tparam S state type
  *
  * @param states       Returns the states of this automaton.
  * @param alphabet     Returns the input alphabet of this automaton.
  * @param initialState Returns the initial state of this automaton.
  * @param finalStates  Returns the final states of this automaton.
  * @param transitions  Returns the transition function of this automaton.
  */
class DeterministicFiniteAutomaton[A,S] private (
    val states: Set[S],
    val alphabet: Set[A],
    val initialState: S,
    val finalStates: Set[S],
    val transitions: PartialFunction[(S,A),S]) {

  require(states nonEmpty, "There are no states.")

  require(alphabet nonEmpty, "There is no input alphabet.")

  require(states contains initialState,
    "Start state (%s) not contained in states (%s)." format (initialState, states))

  require(finalStates forall { states contains _ },
    "One of the final states is not a state.")

  /** Returns the equivalent [[http://en.wikipedia.org/wiki/DFA_minimization minimum DFA]]. */
  def minimize: DeterministicFiniteAutomaton[A,S] = ???

}

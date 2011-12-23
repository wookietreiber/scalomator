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

import scala.xml._

/** Factory for finite-state machines. */
object FiniteStateMachine {

  /** Returns a new finite-state machine.
    *
    * @tparam A alphabet type
    * @tparam S state type
    *
    * @param initialState initial state
    * @param finalStates  final states
    * @param transitions  state-transition function
    *
    * @todo improve, looks ugly
    */
  def apply[A,S](initialState: S, finalStates: Set[S], transitions: Map[(S,A),Set[S]]) =
    if (isDeterministic(transitions)) {
      val deterministicTransitions = transitions map { t =>
        (t._1, t._2.head)
      }
      DeterministicFiniteAutomaton(initialState, finalStates, deterministicTransitions)
    } else
      NondeterministicFiniteAutomaton(initialState, finalStates, transitions)

  /** Returns a new finite-state machine.
    *
    * @param xml the XML representation of an automaton
    *
    * @todo scaladoc contains information about the XML structure
    */
  def apply(xml: Elem) = {
    ???
  }

  private def isDeterministic[A,S](transitions: Map[(S,A),Set[S]]) =
    transitions.values.forall { _.size == 1 }

}

/** Represents finite-state machines (FSM). For ease of use you may import the
  * whole [[scalax.automata]] package and just need to type in the shorter alias
  * `FSM`:
  *
  * {{{
  *   scala> import scalax.automata._
  *   import scalax.automata._
  *
  *   scala> FSM(1, Set(2), Map(
  *        |   1 -> "a" -> Set(2),
  *        |   2 -> "a" -> Set(2)
  *        | ))
  *   res0: scalax.automata.FiniteStateMachine[java.lang.String,Int] = ...
  * }}}
  *
  * @tparam A alphabet type
  * @tparam S state type
  */
abstract class FiniteStateMachine[A,S] {

  /** Returns the input alphabet of this automaton. */
  def alphabet: Set[A] = transitions.keySet map { _._2 }

  /** Returns the states of this automaton. */
  def states: Set[S] = finalStates ++ transitions.values.flatten.toSet + initialState

  /** Returns the initial state of this automaton. */
  def initialState: S

  /** Returns the final states of this automaton. */
  def finalStates: Set[S]

  /** Returns the state-transition function of this automaton. */
  def transitions: Map[(S,A),Set[S]]

  /** Returns the equivalent [[scalax.automata.DeterministicFiniteAutomaton]]. */
  def toDFA: DeterministicFiniteAutomaton[A,S]

  /** Returns the equivalent [[http://en.wikipedia.org/wiki/DFA_minimization minimum DFA]]. */
  def minimize: DeterministicFiniteAutomaton[A,S]

  // -----------------------------------------------------------------------
  // serialization
  // -----------------------------------------------------------------------

  /** Returns the XML representation of this automaton. */
  def toXML: Elem = ???

}

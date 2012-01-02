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

import scala.annotation.tailrec

/** Factory for nondeterministic finite automata. */
object NondeterministicFiniteAutomaton {

  /** Returns a new nondeterministic finite automaton.
    *
    * @tparam A alphabet type
    * @tparam S state type
    *
    * @param initialState initial state
    * @param finalStates  final states
    * @param transitions  state-transition function
    */
  def apply[A,S](initialState: S, finalStates: Set[S], transitions: Map[(S,A),Set[S]]) =
    new NondeterministicFiniteAutomaton(initialState, finalStates, transitions)

}

/** Represents nondeterministic finite automata (NFA). For ease of use you may
  * import the whole [[scalax.automata]] package and just need to type in the
  * shorter alias `NFA`:
  *
  * {{{
  *   scala> import scalax.automata._
  *   import scalax.automata._
  *
  *   scala> NFA(1, Set(2), Map(
  *        |   1 -> "a" -> Set(2),
  *        |   2 -> "a" -> Set(2)
  *        | ))
  *   res0: scalax.automata.NondeterministicFiniteAutomaton[java.lang.String,Int] = ...
  * }}}
  *
  * @tparam A alphabet type
  * @tparam S state type
  */
class NondeterministicFiniteAutomaton[A,S] private (
    val initialState: S,
    val finalStates: Set[S],
    val transitions: Map[(S,A),Set[S]])
  extends FiniteStateMachine[A,S,Set[S]] {

  override def states: Set[S] = finalStates ++ transitions.values.flatten.toSet + initialState

  // -----------------------------------------------------------------------
  // conversion within the domain
  // -----------------------------------------------------------------------

  override def toDFA = {
    /** Returns the ends of the combined state. */
    def endsOf(ss: Set[S]) = for {
      a <- alphabet
      t <- Map(ss -> a -> ss.flatMap { s => transitions.getOrElse((s,a),Set()) })
    } yield t

    /** Returns the transitions of the powerset construction. */
    @tailrec
    def psc(ss: Set[Set[S]], ts: Map[(Set[S],A),Set[S]]): Map[(Set[S],A),Set[S]] = ss match {
      case set if set.isEmpty => ts
      case _ =>
        val newts = endsOf(ss.head) toMap
        val sumts = ts ++ newts
        val newss = (newts.values.toSet) diff (sumts.keySet map { _._1 })
        psc(ss.tail ++ newss, sumts)
    }

    val init = Set(initialState)
    val ts   = psc(Set(init), Map())
    val fs   = ts.values.toSet filter { _ exists { finalStates contains } }

    DeterministicFiniteAutomaton(init, fs, ts)
  }

}

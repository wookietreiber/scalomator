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
import scala.collection.immutable.Queue

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
    override val initialState: S,
    override val finalStates: Set[S],
    override val transitions: Map[(S,A),Set[S]])
  extends FiniteStateMachine[A,S,Set[S]] {

  override def states: Set[S] = finalStates ++ transitions.values.flatten.toSet + initialState

  override def accepts(word: Seq[A]) = {
    def traverse(word: Seq[A], state: S): Boolean = {
      if ( word isEmpty )
        if ( finalStates contains state ) true else false
      else {
        val move = state -> word.head
        if ( transitions isDefinedAt move )
          transitions(move).map(traverse(word.tail,_)).foldLeft(false)(_ || _)
        else
          false
      }
    }

    traverse(word, initialState)
  }

  // -----------------------------------------------------------------------
  // conversion within the domain
  // -----------------------------------------------------------------------

  override def toDFA = {
    val init = Set(initialState)

    @tailrec // recursive powerset construction
    def psc(ts: Map[(Set[S],A),Set[S]], q: Queue[Set[S]]): DFA[A,Set[S]] = q match {
      case Queue() => { // nothing more to do but to set up the DFA
        val fs = ts.values.toSet filter { _ exists { finalStates contains } }

        DeterministicFiniteAutomaton(init, fs, ts)
      }

      case Dequeue(dfaState,q) => {
        val newts = (
          for {
            a <- alphabet

            end = for {
              nfaState <- dfaState
              end      <- transitions.getOrElse((nfaState,a),Set())
            } yield end
          } yield ( dfaState -> a -> end )
        ) toMap

        val sumts = ts ++ newts

        val unhandledStates = newts.values.toSet diff {
          sumts.keySet map { _._1 }
        }

        psc(sumts, q enqueue unhandledStates)
      }
    }

    psc(Map(), Queue(init))
  }

}

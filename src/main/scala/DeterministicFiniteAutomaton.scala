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

/** Factory for deterministic finite automata. */
object DeterministicFiniteAutomaton {

  /** Returns a new deterministic finite automaton.
    *
    * @tparam A alphabet type
    * @tparam S state type
    *
    * @param initialState initial state
    * @param finalStates  final states
    * @param transitions  state-transition function
    */
  def apply[A,S](initialState: S, finalStates: Set[S], transitions: Map[(S,A),S]) =
    new DeterministicFiniteAutomaton(initialState, finalStates, transitions)

  object setsize extends Ordering[Set[_]] {
    def compare(x: Set[_], y: Set[_]) = x.size compare y.size
  }

}

import DeterministicFiniteAutomaton._

/** Represents deterministic finite automata (DFA). For ease of use you may
  * import the whole [[scalax.automata]] package and just need to type in the
  * shorter alias `DFA`:
  *
  * {{{
  *   scala> import scalax.automata._
  *   import scalax.automata._
  *
  *   scala> DFA(1, Set(2), Map(
  *        |   1 -> "a" -> 2,
  *        |   2 -> "a" -> 2
  *        | ))
  *   res0: scalax.automata.DeterministicFiniteAutomaton[java.lang.String,Int] = ...
  * }}}
  *
  * @tparam A alphabet type
  * @tparam S state type
  */
class DeterministicFiniteAutomaton[A,S] private (
    override val initialState: S,
    override val finalStates: Set[S],
    override val transitions: Map[(S,A),S])
  extends FiniteStateMachine[A,S,S] {

  override def states: Set[S] = finalStates ++ transitions.values.toSet + initialState

  // -----------------------------------------------------------------------
  // conversion within the domain
  // -----------------------------------------------------------------------

  override def toDFA = this

  /** Returns the equivalent [[http://en.wikipedia.org/wiki/DFA_minimization minimum DFA]]. */
  def minimize: DFA[A,Set[S]] = {
    @tailrec
    def refine(partition: Set[Set[S]], q: Queue[Set[S]]): DFA[A,Set[S]] = q match {
      case Queue() => { // nothing more to do but to set up the minimized DFA
        val init = partition filter { _ contains initialState } head
        val fs   = partition filter { _ exists { finalStates contains _ } }
        val ts   = for {
          start <- partition
          input <- alphabet

          end = partition filter {
            _ contains transitions ( start.head -> input )
          } head
        } yield ( start -> input -> end )

        DeterministicFiniteAutomaton(init, fs, ts.toMap)
      }

      case Dequeue(current,q) => {
        val (removals,additions) = (
          for {
            start <- for {
              input <- alphabet

              starts = for { // ? -> input -> end
                end       <- current
                ((s,i),e) <- transitions if ( e == end ) && ( i == input )
              } yield s

              if starts nonEmpty
            } yield starts

            changes <- for {
              part  <- partition
              isect =  part & start if ( isect nonEmpty ) && ! ( partition contains isect )
            } yield part -> List(isect, part diff isect)
          } yield changes
        ) unzip

        val newparts = for {
          add  <- additions
          part =  add min setsize if ! ( part.size <= 1 )
        } yield part

        refine(partition -- removals ++ additions.flatten, q enqueue newparts)
      }
    }

    val partition = Set(finalStates, states diff finalStates)

    refine(partition, Queue(finalStates))
  }

}

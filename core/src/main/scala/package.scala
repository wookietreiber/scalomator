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


package scalax

import scala.collection.immutable.Queue

/** A scala API for automata simulation. */
package object automata {

  object Dequeue {
    def unapply[A](q: Queue[A]) = q match {
      case Queue() => None
      case q       => Some(q dequeue)
    }
  }

  // -----------------------------------------------------------------------
  // aliases
  // -----------------------------------------------------------------------

  type FSM[A,S,R] = scalax.automata.FiniteStateMachine[A,S,R]
  val  FSM        = scalax.automata.FiniteStateMachine

  type NFA[A,S] = scalax.automata.NondeterministicFiniteAutomaton[A,S]
  val  NFA      = scalax.automata.NondeterministicFiniteAutomaton

  type DFA[A,S] = scalax.automata.DeterministicFiniteAutomaton[A,S]
  val  DFA      = scalax.automata.DeterministicFiniteAutomaton

  // -----------------------------------------------------------------------
  // implicits
  // -----------------------------------------------------------------------

  implicit def any2input[A](a: A) = Input(a)

  // -----------------------------------------------------------------------
  // implicits
  // -----------------------------------------------------------------------

  object does {
    def the[A](fsm: FiniteStateMachine[A,_,_]) = new Pimp(fsm)
  }

  class Pimp[A](fsm: FiniteStateMachine[A,_,_]) {
    def accept(word: A*) = fsm.accepts(word: _*)
  }

}

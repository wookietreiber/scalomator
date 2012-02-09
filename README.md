Automata Simulator
==================

This project provides an API and a simulator for automatas like finite-state
machines which is intended to be a helpful learning tool for computer science
students.


How to Run
----------

Because, unfortunately, jgraph can not be downloaded from any repository you
have to create a directory called "lib" directly in the base project directory,
download this version of [jgraph][1], extract it and put the `jgraphx.jar` in
the "lib" directory.

To simply build and run the GUI [install sbt][2] go to the base project
directory and execute the following commands:

```
$ cd /path/to/scalomator
$ sbt "; project scalomator-client-swing ; run"
```

You may also start the GUI with the sbt console and execute the following
commands:

```
$ cd /path/to/scalomator
$ sbt
> project scalomator-client-swing
> run
```

If you like to work with the scala interpreter:

```
$ cd /path/to/scalomator
$ sbt
> console
[info] Starting scala interpreter...
[info]
import scalax.automata._
Welcome to Scala version 2.9.1.final (OpenJDK 64-Bit Server VM, Java 1.7.0_147-icedtea).
Type in expressions to have them evaluated.
Type :help for more information.

scala> val nfa = NFA(0, Set(3), Map(
     |   0 -> 0 -> Set(0,1), 0 -> 1 -> Set(0),
     |   1 -> 0 -> Set(2),   1 -> 1 -> Set(2),
     |   2 -> 0 -> Set(3),   2 -> 1 -> Set(3)
     | ))
nfa: scalax.automata.NondeterministicFiniteAutomaton[Int,Int] = ...

scala> nfa accepts (0,0,1)
res0: Boolean = true

scala> val dfa = nfa toDFA
dfa: scalax.automata.package.DFA[Int,Set[Int]] = ...

scala> dfa accepts (0,0,1)
res1: Boolean = true

scala>
```


Acknowledgements
----------------

This project directly (nontransitively) includes and/or uses the following
projects:

- the general-purpose programming language [Scala][3] ([Scala License][4])
- building the project is done with [sbt][5] ([New BSD][6])
- the [jgraph][7] library ([BSD-style][8])


[1]: http://downloads.jgraph.com/downloads/jgraphx/archive/jgraphx-1_9_0_2.zip
[2]: https://github.com/harrah/xsbt/wiki/Getting-Started-Setup
[3]: http://www.scala-lang.org/
[4]: http://www.scala-lang.org/print/146
[5]: https://github.com/harrah/xsbt
[6]: http://www.opensource.org/licenses/BSD-3-Clause
[7]: http://www.jgraph.com/
[8]: http://en.wikipedia.org/wiki/Bsd_licence


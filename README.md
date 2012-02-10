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

To build the project's documentation, execute the following commands:

```
$ cd /path/to/scalomator
$ sbt
> project scalomator-client-swing
> doc
> project scalomator
> doc
```
The respective documentation can be found at:
/path/to/scalomator/client-swing/target/scala-2.9.1/api/index.html
and
/path/to/scalomator/target/scala-2.9.1/api/index.html

If you prefer to build the Javadoc for the GUI instead, navigate to the subproject's base directory

```
$ cd /path/to/scalomator/client-swing
$ javadoc -d target/javadoc src/main/java/scalax/automatot/gui/*.java
```

All warnings concerning com.mxgraph can be ignored. For the jgraph documentation consult its own [website][7].
The javadoc can be found at:
/path/to/scalomator/client-swing/target/javadoc

To simply build and run the GUI [install sbt][2], navigate to the base project
directory and execute the following commands:

```
$ cd /path/to/scalomator
$ sbt "; project scalomator-client-swing ; run"
```

You may also start the GUI with the sbt console and execute the following
commands individually:

```
$ cd /path/to/scalomator
$ sbt
> project scalomator-client-swing
> run
```

If you rather like to work with the scala interpreter instead:

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

scala>
```

And then paste the following into the console:

```
val nfa = NFA(0, Set(3), Map(
  0 -> 0 -> Set(0,1), 0 -> 1 -> Set(0),
  1 -> 0 -> Set(2),   1 -> 1 -> Set(2),
  2 -> 0 -> Set(3),   2 -> 1 -> Set(3)
))
```
This will create a nondeterministic finite automata (NFA) object 'nfa'
with which you can do everything you'd expect from an automaton:
(The syntax can be found in the project's documentation at:
/path/to/scalomator/target/scala-2.9.1/api/index.html#scalax.automata.NondeterministicFiniteAutomaton
and 
/path/to/scalomator/target/scala-2.9.1/api/index.html#scalax.automata.NondeterministicFiniteAutomaton$)

Acceptance test for sequence 001:
```
nfa.accepts(0,0,1)
```

Convert the NFA into a deterministic finite automata (DFA) object 'dfa'.
```
val dfa = nfa.toDFA
```

Acceptance test for sequence 001:
```
dfa.accepts(0,0,1)
```

Minimize the DFA:
```
val mindfa = dfa.minimize
```

Acceptance test for sequence 001:
```
mindfa.accepts(0,0,1)
```

Create an XML structure:
```
nfa.toXML
```

Save the xml (root is the current project path):
```
xml.XML.save("/path/to/somewhere/foo.xml",nfa.toXML)
```

To exit the scala console when done press CTRL+D

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


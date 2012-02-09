Automata Simulator
==================

This project provides an API and a simulator for automatas like finite-state
machines which is intended to be a helpful learning tool for computer science
students.


How to Run
----------

Because, unfortunately, jgraph can not be downloaded from any repository you
have to create a directory called "lib" directly in the base project directory
and download this version of [jgraph][1].

To simply build and run the project install [sbt][2] go to the base project
directory and execute the following command:

`sbt "; project scalomator-client-swing ; run"`

You may also start an sbt console and run the following commands:

```
$ cd /path/to/project/base/dir
$ sbt
> project scalomator-client-swing
> run
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


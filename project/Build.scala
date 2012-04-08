import sbt._
import Keys._

import Resolvers._
import Dependencies._
import BuildSettings._

object BuildSettings {
  val buildOrganization = "com.github.scalomator"
  val buildVersion      = "0.2.0-SNAPSHOT"
  val buildScalaVersion = "2.9.2-RC2"

  val baseSettings = Defaults.defaultSettings ++ Seq (
    crossScalaVersions := Seq ( "2.9.1", "2.9.1-1", "2.9.2-RC2" )
  )

  val buildSettings = baseSettings ++ Seq (
    organization     := buildOrganization,
    version          := buildVersion,
    scalaVersion     := buildScalaVersion,
    resolvers       ++= Seq ( sonatype )
  )
}

object ScalomatorBuild extends Build {

  lazy val root = Project (
    id        = "scalomator",
    base      = file ("."),
    settings  = baseSettings
  ) aggregate ( core, clientSwing )

  lazy val core = Project (
    id        = "scalomator-core",
    base      = file ("core"),
    settings  = buildSettings ++ Seq (
      libraryDependencies ++= Seq ( specs2 ),
      initialCommands in Compile in console += """
        import scalax.automata._
      """
    )
  )

  lazy val clientSwing = Project (
    id        = "scalomator-client-swing",
    base      = file ("client-swing"),
    settings  = buildSettings ++ Seq (
      libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
        deps :+ specs2 :+ ("org.scala-lang" % "scala-swing" % sv)
      },
      mainClass := Some("scalax.automata.gui.AutomataGUI")
    )
  ) dependsOn ( core )

}

object Dependencies {
  lazy val swing  = "org.scala-lang" %  "scala-swing" % buildScalaVersion
  lazy val specs2 = "org.specs2"     %% "specs2"      % "1.9" % "test"
}

object Resolvers {
  lazy val sonatype = "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
}

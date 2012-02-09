import sbt._
import Keys._

import Dependencies._
import BuildSettings._

object BuildSettings {
  val buildOrganization = "com.github.scalomator"
  val buildVersion      = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization    := buildOrganization,
    version         := buildVersion,
    scalaVersion    := buildScalaVersion,
    initialCommands := "import scalax.automata._"
  )
}

object ScalomatorBuild extends Build {

  // -----------------------------------------------------------------------
  // project definition
  // -----------------------------------------------------------------------

  lazy val root = Project ( "scalomator", file ("."),
    settings = buildSettings ++ Seq (
      libraryDependencies ++= Seq ( specs2 )
    )
  )

  lazy val clientSwing = Project ( "scalomator-client-swing", file ("client-swing"),
    settings = buildSettings ++ Seq (
      libraryDependencies ++= Seq ( swing, specs2 ),
      mainClass := Some("scalax.automata.gui.AutomataGUI")
    )
  ) dependsOn ( root )

}

object Dependencies {
  lazy val swing  = "org.scala-lang" %  "scala-swing" % buildScalaVersion
  lazy val specs2 = "org.specs2"     %% "specs2"      % "1.7.1" % "test"
}

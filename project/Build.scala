import sbt._
import Keys._

import Dependencies._
import BuildSettings._

object BuildSettings {
  val buildOrganization = "com.github.scalomator"
  val buildVersion      = "0.1-SNAPSHOT"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion
  )
}

object ScalomatorBuild extends Build {

  // -----------------------------------------------------------------------
  // project definition
  // -----------------------------------------------------------------------

  lazy val root = Project ( "scalomator", file ("."),
    settings = buildSettings ++ Seq (
      libraryDependencies ++= Seq ( specs2 ),
      initialCommands in Compile in console := "import scalax.automata._"
    )
  )

}

object Dependencies {
  val specs2 = "org.specs2" %% "specs2" % "1.6.1" % "test"
}

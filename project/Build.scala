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

  lazy val clientSwing = Project ( "scalomator-client-swing", file ("client-swing"),
    settings = buildSettings ++ Seq (
      libraryDependencies ++= Seq ( swing, specs2 )
    )
  ) dependsOn ( root )

}

object Dependencies {
  lazy val swing  = "org.scala-lang" %  "scala-swing" % buildScalaVersion
  lazy val specs2 = "org.specs2"     %% "specs2"      % "1.7.1" % "test"
}

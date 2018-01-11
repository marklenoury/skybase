import Dependencies._
lazy val akkaVersion = "2.5.3"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.marklenoury",
      scalaVersion := "2.12.2",
      version      := "0.0.1-SNAPSHOT"
    )),
    name := "skybase-client",
    libraryDependencies ++= Seq(
		"com.marklenoury" %% "skybase-common" % "0.0.1-SNAPSHOT",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
		  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
	)
  )

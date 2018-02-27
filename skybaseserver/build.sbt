lazy val akkaVersion = "2.5.3"

lazy val root = (project in file("."))
  .settings(
  name := "skybase",
  organization := "com.marklenoury",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.2",
  libraryDependencies ++= Seq(
	"com.marklenoury" %% "skybase-common" % "0.0.1-SNAPSHOT",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "com.typesafe" % "config" % "1.3.2",
    "com.github.scopt" % "scopt_2.11" % "3.7.0"
  )
)
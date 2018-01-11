lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.marklenoury",
      scalaVersion := "2.12.2",
      version      := "0.0.1-SNAPSHOT"
    )),
    name := "skybase-common"
  )

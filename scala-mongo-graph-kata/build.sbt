import play.sbt.routes.RoutesKeys

name := """scala-mongo-graph-kata"""
organization := "de.butterworks"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.12" % "0.12.7-play26"

routesGenerator := InjectedRoutesGenerator

RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
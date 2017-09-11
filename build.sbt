name := """table-tennis-team-service"""
organization := "com.ruchij"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4"
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"
libraryDependencies += "com.h2database" % "h2" % "1.4.196"
libraryDependencies += "com.typesafe.play" % "play-slick_2.12" % "3.0.1"
libraryDependencies += "com.typesafe.play" % "play-slick-evolutions_2.12" % "3.0.1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test
libraryDependencies += "org.pegdown" % "pegdown" % "1.6.0" % Test

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/html-test-reports")

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ruchij.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ruchij.binders._"

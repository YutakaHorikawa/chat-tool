name := """chat"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalikejdbc"      %% "scalikejdbc-play-plugin"   % "[1.7,)",
  "mysql"                %  "mysql-connector-java"      % "[5.1,)",
  anorm,
  cache,
  ws
)

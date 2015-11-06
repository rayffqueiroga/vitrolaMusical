name := """si1-lab2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  javaCore,
  cache,
  javaWs,
  "org.apache.directory.api" % "api-all" % "1.0.0-M14",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.hibernate" % "hibernate-core" % "4.2.3.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.2.3.Final",
  "net.sourceforge.htmlunit" % "htmlunit" % "2.14" % "test",
  "org.apache.httpcomponents" % "httpclient" % "4.3.1" % "test",
  "org.apache.httpcomponents" % "httpcore" % "4.3.1" % "test"
)


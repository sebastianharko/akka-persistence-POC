name := """akka-persistence-poc"""

version := "2.4.4"

scalaVersion := "2.11.8"

mainClass in Compile := Some("poc.persistence.write.WriteApp")

enablePlugins (Cinnamon)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.10",
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.4",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.4",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.4",
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.4",
  "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.21",
  "com.lightbend.akka" %% "akka-management-cluster-http" % "0.1-RC1",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.4",
  "org.json4s" % "json4s-jackson_2.11" % "3.5.0",
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.4",
  "de.heikoseeberger" % "akka-http-json4s_2.11" % "1.11.0",
  "org.scalactic" %% "scalactic" % "3.0.1",
  Cinnamon.library.cinnamonAkka,
  Cinnamon.library.cinnamonCHMetrics,
  Cinnamon.library.cinnamonCHMetricsStatsDReporter,
  Cinnamon.library.cinnamonCHMetricsJvmMetrics,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.lightbend.akka" % "akka-split-brain-resolver_2.11" % "1.0.0",
  "com.lightbend.akka" % "akka-diagnostics_2.11" % "1.0.0"

)

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))

fork in run := true

cancelable in Global := true

enablePlugins(JavaServerAppPackaging)

// Add the Monitoring Agent for run and test
cinnamon in run := true
cinnamon in test := true

// Set the Monitoring Agent log level
cinnamonLogLevel := "DEBUG"




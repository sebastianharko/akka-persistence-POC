name := """akka-persistence-poc"""

version := "2.4.4"

scalaVersion := "2.11.8"

mainClass in Compile := Some("poc.persistence.write.WriteApp")

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
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

licenses := Seq(("CC0", url("http://creativecommons.org/publicdomain/zero/1.0")))

fork in run := true

cancelable in Global := true

enablePlugins(JavaServerAppPackaging)


// for the split brain resolver
credentials += Credentials(Path.userHome / ".lightbend" / "commercial.credentials")

resolvers += "com-mvn" at "https://repo.lightbend.com/commercial-releases/"

resolvers += Resolver.url("com-ivy", url("https://repo.lightbend.com/commercial-releases/"))(Resolver.ivyStylePatterns)

libraryDependencies += "com.lightbend.akka" % "akka-split-brain-resolver_2.11" % "1.0.0"

libraryDependencies += "com.lightbend.akka" % "akka-diagnostics_2.11" % "1.0.0"
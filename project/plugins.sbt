addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M6")

addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.0.0")

credentials += Credentials(Path.userHome / ".lightbend" / "commercial.credentials")

resolvers += "com-mvn" at "https://repo.lightbend.com/commercial-releases/"

resolvers += Resolver.url("com-ivy", url("https://repo.lightbend.com/commercial-releases/"))(Resolver.ivyStylePatterns)

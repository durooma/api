logLevel := Level.Warn

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.1.2")
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

resolvers += "Flyway" at "https://flywaydb.org/repo"
import sbt.Keys.{sourceGenerators, version}

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, unmanagedResources in Compile, runner in Compile, streams) map { (dir, cp, res, r, s) =>
  val outputDir = (dir / "main").getPath
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files ++ res, Array("#data", outputDir), s.log))
  val fname = outputDir + "/com/durooma/db/Tables.scala"
  Seq(file(fname))
}

lazy val db = (project in file("db"))
  .settings(Seq(
    name := "durooma-db",
    version := "0.1.0",
    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
      "com.typesafe" % "config" % "1.3.1",
      "mysql" % "mysql-connector-java" % "5.1.41"
    ),
    slick <<= slickCodeGenTask, // register manual sbt command
    sourceGenerators in Compile <+= slickCodeGenTask, // register automatic code generation on every compile, remove for only manual use
    flywayUrl := "jdbc:mysql://localhost/durooma?useSSL=false", // TODO: remove this redundancy
    flywayLocations := Seq("db/migration"),
    flywayUser := "durooma"
  ))

lazy val api = (project in file("."))
  .settings(Seq(
    name := "durooma-api",
    version := "0.1.0",
    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.4",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4",
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
      "com.typesafe" % "config" % "1.3.1",
      "mysql" % "mysql-connector-java" % "5.1.41"
    )
  ))
  .dependsOn(db)




organization in ThisBuild := "com.iheart"

resolvers +=  Resolver.bintrayRepo("scalaz", "releases")

lazy val root = project.in(file("."))
  .aggregate(playSwagger, sbtPlaySwagger)
  .settings(
    publishArtifact := false,
    sourcesInBase := false
  )

lazy val playSwagger = project.in(file("core"))
  .settings(Publish.coreSettings ++ Format.settings ++ Testing.settings)
  .settings(
    name := "play-swagger",
    libraryDependencies ++= Dependencies.playTest ++
      Dependencies.playRoutesCompiler ++
      Dependencies.playJson ++
      Dependencies.test ++
      Dependencies.yaml,
    scalaVersion := "2.11.7"
  )

lazy val sbtPlaySwagger = project.in(file("sbtPlugin"))
  .settings(Publish.sbtPluginSettings ++ Format.settings ++ ScriptedTesting.settings)
  .settings(addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.6" % Provided))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version),
    buildInfoPackage := "com.iheart.playSwagger",
    name := "sbt-play-swagger",
    description := "sbt plugin for play swagger spec generation",
    sbtPlugin := true,
    scalaVersion := "2.10.6",
    scripted <<= scripted.dependsOn(publishLocal in playSwagger)
  )

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "aps-cool"
  )

lazy val transformed = project
  .settings(
    Compile / unmanagedSourceDirectories += (Compile / sourceManaged).value
  )

val transform = taskKey[Unit]("Transform sources")

transform := {
  val inputDir  = (root        / Compile / scalaSource  ).value
  val outputDir = (transformed / Compile / sourceManaged).value
  Generator.gen(
    inputDir,
    outputDir,
    filesToTransform = Seq("cool-semant.scala"),
    emptyLineIndices = Map("cool-semant.scala" -> Seq(960)),
    className        = "M_SEMANT",
    methodName       = "visit_1_1_0"
  )
}


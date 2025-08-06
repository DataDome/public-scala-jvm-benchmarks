organization := "co.datadome.pub"
name := "scala-jvm-benchmarks"
description := "Benchmarks for Scala code on various JVMs"

version := "0.0.0"

scalaVersion := "3.7.2"

javacOptions ++= Seq(
  "-source", "21", "-target", "21",
  "--enable-preview",
  "--add-modules=jdk.incubator.vector",
  "--add-exports=java.base/jdk.internal.vm.annotation=ALL-UNNAMED",
  "--add-exports=java.management/sun.management=ALL-UNNAMED"
)

scalacOptions ++= Seq(
  "-no-indent", // Remove the significant-indentation syntax

  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-explain", // Explain type errors in more detail.

  "-language:implicitConversions",
  "-language:higherKinds",

  "-Wsafe-init", // Wrap field accessors to throw an exception on uninitialized access.
  "-Wunused:explicits", // Warn if an explicit parameter is unused.
  "-Wunused:implicits", // Warn if an implicit parameter is unused.
  "-Wunused:imports", // Warn when imports are unused.
  "-Wunused:locals", // Warn if a local definition is unused.
  "-Wunused:params", // Warn if a value parameter is unused.
  "-Wunused:privates", // Warn if a private member is unused.
  "-Wvalue-discard" // Warn when non-Unit expression results are unused.
)

libraryDependencies ++= Seq(

)

assembly / mainClass := None
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}

Test / fork := true
Test / testOptions += Tests.Argument("-oD") // show test duration

/* Testing configuration  */
Test / parallelExecution := false

/* Makes processes is SBT cancelable without closing SBT */
Global / cancelable := true

enablePlugins(JmhPlugin)

organization := "co.datadome.pub"
name := "scala-jvm-benchmarks"
description := "Benchmarks for Scala code on various JVMs"

version := "0.0.0"

scalaVersion := "3.7.2"

javacOptions ++= Seq(
  "-source", "21", "-target", "21",
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
  "org.scalatest" %% "scalatest" % "3.2.19" % Test
)

assembly / mainClass := Some("org.openjdk.jmh.Main")
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.rename
  case PathList("LICENSE") => MergeStrategy.rename
  case _ => MergeStrategy.deduplicate // default
}

// rewire tasks, so that 'Jmh/assembly' automatically invokes 'Jmh/compile' (otherwise a clean 'Jmh/assembly' would miss content in the META-INF)
Jmh / assembly := (Jmh / assembly).dependsOn(Jmh / compile).value


Test / fork := true
Test / testOptions += Tests.Argument("-oD") // show test duration

/* Testing configuration  */
Test / parallelExecution := false

/* Makes processes is SBT cancelable without closing SBT */
Global / cancelable := true

enablePlugins(JmhPlugin)

// Adds a pure Scala artifact fetcher `coursier`
// https://github.com/coursier/coursier
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.3")

// Adds a `scalafmt` task for automatic source code formatting
// https://github.com/lucidsoftware/neo-sbt-scalafmt
addSbtPlugin("com.lucidchart" % "sbt-scalafmt-coursier" % "1.16")

// Used to get updates for plugins
// see https://github.com/rtimush/sbt-updates/issues/10
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.0")

// Adds a pure Scala artifact fetcher `coursier`
// https://github.com/coursier/coursier
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.3")

// Adds a `wartremover` a flexible Scala code linting tool
// http://github.com/puffnfresh/wartremover
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.3.7")

// Adds Contrib Warts
// http://github.com/wartremover/wartremover-contrib/
addSbtPlugin("org.wartremover" % "sbt-wartremover-contrib" % "1.2.3")

// Adds Extra Warts
// http://github.com/danielnixon/extrawarts
addSbtPlugin("org.danielnixon" % "sbt-extrawarts" % "1.0.3")

// Adds a `assembly` task to create a fat JAR with all of its dependencies
// https://github.com/sbt/sbt-assembly
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.7")

// Adds a `BuildInfo` tasks
// https://github.com/sbt/sbt-buildinfo
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.9.0")

// Adds most common doc api mappings
// https://github.com/ThoughtWorksInc/sbt-api-mappings
addSbtPlugin("com.thoughtworks.sbt-api-mappings" % "sbt-api-mappings" % "2.1.0")

// Adds Scala Code Coverage (Scoverage) used during unit tests
// http://github.com/scoverage/sbt-scoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

// Adds a `dependencyUpdates` task to check Maven repositories for dependency updates
// http://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")

// Adds a `scalafmt` task for automatic source code formatting
// https://github.com/lucidsoftware/neo-sbt-scalafmt
addSbtPlugin("com.lucidchart" % "sbt-scalafmt-coursier" % "1.15")

// Adds `scalastyle` a coding style checker and enforcer
// https://github.com/scalastyle/scalastyle-sbt-plugin
addSbtPlugin("org.scalastyle" % "scalastyle-sbt-plugin" % "1.0.0")

// Adds a `dependencyUpdates` task to check for dependency updates
// http://github.com/rtimush/sbt-updates
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.1")

// Adds a `release` plugin
// https://github.com/sbt/sbt-release
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.9")

// Adds a `gnu-pgp` plugin
// https://github.com/sbt/sbt-pgp
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.2")

// Setup this and project/project/plugins.sbt for formatting project/*.scala files with scalafmt
inThisBuild(
  Seq(
    scalafmtOnCompile := true,
    // Use the scalafmt config in the root directory
    scalafmtConfig := baseDirectory(_.getParentFile / ".scalafmt.conf").value
  )
)

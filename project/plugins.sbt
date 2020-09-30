// Adds a pure Scala artifact fetcher `coursier`
// https://github.com/coursier/coursier
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.3")

// Adds a `wartremover` a flexible Scala code linting tool
// http://github.com/puffnfresh/wartremover
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.10")

// Adds Contrib Warts
// http://github.com/wartremover/wartremover-contrib/
addSbtPlugin("org.wartremover" % "sbt-wartremover-contrib" % "1.3.8")

// Adds a `assembly` task to create a fat JAR with all of its dependencies
// https://github.com/sbt/sbt-assembly
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.15.0")

// Adds most common doc api mappings
// https://github.com/ThoughtWorksInc/sbt-api-mappings
addSbtPlugin("com.thoughtworks.sbt-api-mappings" % "sbt-api-mappings" % "3.0.0")

// Adds Scala Code Coverage (Scoverage) used during unit tests
// http://github.com/scoverage/sbt-scoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")

// Adds SBT Coveralls plugin for uploading Scala code coverage to
// https://coveralls.io
// https://github.com/scoverage/sbt-coveralls
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")

// Adds a `dependencyUpdates` task to check Maven repositories for dependency updates
// http://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.1")

// Adds a `scalafmt` task for automatic source code formatting
// https://github.com/lucidsoftware/neo-sbt-scalafmt
addSbtPlugin("com.lucidchart" % "sbt-scalafmt-coursier" % "1.16")

// Adds `scalastyle` a coding style checker and enforcer
// https://github.com/scalastyle/scalastyle-sbt-plugin
addSbtPlugin("org.scalastyle" % "scalastyle-sbt-plugin" % "1.0.0")

// Adds a `dependencyUpdates` task to check for dependency updates
// http://github.com/rtimush/sbt-updates
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

// Adds a `sonatype` release tasks
// https://github.com/xerial/sbt-sonatype
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.4")

// Adds a `gnu-pgp` plugin
// https://github.com/sbt/sbt-pgp
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")

// Adds a `git` plugin
// https://github.com/sbt/sbt-git
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0")

// Setup this and project/project/plugins.sbt for formatting project/*.scala files with scalafmt
inThisBuild(
  Seq(
    scalafmtOnCompile := true,
    // Use the scalafmt config in the root directory
    scalafmtConfig := baseDirectory(_.getParentFile / ".scalafmt.conf").value
  )
)

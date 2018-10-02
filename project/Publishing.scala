package com.exasol.spark.sbt

import sbt._
import sbt.Keys._
import com.typesafe.sbt.pgp.PgpKeys._
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer

object Publishing {

  // Useful tasks to show what versions would be used if a release was performed
  private val showReleaseVersion = taskKey[String]("Show current release version")
  private val showNextVersion = taskKey[String]("Show next release version")

  def publishSettings(): Seq[Setting[_]] = Seq(
    homepage := Some(url("https://github.com/EXASOL/spark-exasol-connector")),
    licenses := Seq(
      "Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")
    ),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := Function.const(false),
    publishTo := {
      val realm = "https://maven.exasol.com/artifactory/"
      if (isSnapshot.value) {
        val timestamp = new java.util.Date().getTime
        Some("Artifactory Realm" at s"$realm/exasol-snapshots;build.timestamp=$timestamp")
      } else {
        Some("Artifactory Realm" at s"$realm/exasol-releases")
      }
    },
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/EXASOL/spark-exasol-connector"),
        "scm:git:git@github.com:EXASOL/spark-exasol-connector.git"
      )
    ),
    developers := List(
      Developer(
        id = "morazow",
        name = "Muhammet Orazov",
        email = "muhammet.orazov@exasol.com",
        url = url("https://github.com/morazow/")
      )
    ),
    // Do not include scoverage & wartremover plugins as a dependency in the pom
    // https://github.com/scoverage/sbt-scoverage/issues/153
    // This code was copied from https://github.com/http4s/http4s
    pomPostProcess := { (node: xml.Node) =>
      val depsToRemove = Set("org.scoverage", "org.wartremover", "org.danielnixon")
      new RuleTransformer(new RewriteRule {
        override def transform(node: xml.Node): Seq[xml.Node] = node match {
          case e: xml.Elem
              if e.label == "dependency" && e.child
                .exists(child => child.label == "groupId" && depsToRemove.contains(child.text)) =>
            Nil
          case _ => Seq(node)
        }
      }).transform(node).head
    },
    // Do not include scala-library as dependency since it provided with Spark runtime
    autoScalaLibrary := false,
    // Gnupg related settings
    // Global scope somehow needed here; otherwise publishLocalSigned looks for these credentials
    // in default path of ~/.sbt/gpg/
    useGpg in Global := false,
    pgpPublicRing in Global := baseDirectory.value / "project" / ".gnupg" / "local.pubring.asc",
    pgpSecretRing in Global := baseDirectory.value / "project" / ".gnupg" / "local.secring.asc",
    pgpPassphrase in Global := sys.env.get("PGP_PASSPHRASE").map(_.toArray),
    credentials ++= (for {
      username <- sys.env.get("ARTIFACTORY_USERNAME")
      password <- sys.env.get("ARTIFACTORY_PASSWORD")
    } yield Credentials("Artifactory Realm", "maven.exasol.com", username, password)).toSeq,
    releaseCrossBuild := false,
    releaseCommitMessage := {
      if (isSnapshot.value) {
        s"Setting version to ${version.value} for next development iteration\n\n[skip ci]"
      } else {
        s"Setting version to ${version.value} for release\n\n[skip ci]"
      }
    },
    releaseTagName := s"v${version.value}",
    releaseTagComment := s"Releasing ${version.value} of module: ${name.value}",
    releaseVersionBump := sbtrelease.Version.Bump.Minor,
    releasePublishArtifactsAction := publishSigned.value,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      releaseStepCommand(sbtrelease.ExtraReleaseCommands.initialVcsChecksCommand),
      inquireVersions,
      setReleaseVersion,
      runClean,
      runTest,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      pushChanges,
      releaseStepCommand("git checkout develop"),
      releaseStepCommand("git merge master"),
      setNextVersion,
      commitNextVersion,
      pushChanges
    ),
    showReleaseVersion := { val rV = releaseVersion.value.apply(version.value); println(rV); rV },
    showNextVersion := { val nV = releaseNextVersion.value.apply(version.value); println(nV); nV }
  )

  def noPublishSettings: Seq[Setting[_]] = Seq(
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )

}

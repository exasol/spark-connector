package com.exasol.spark.sbt

import sbt._
import sbt.Keys._
import com.typesafe.sbt.pgp.PgpKeys._
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer

object Publishing {

  val VersionRegex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r

  def publishSettings(): Seq[Setting[_]] = Seq(
    homepage := Some(url("https://github.com/EXASOL/spark-exasol-connector")),
    licenses := Seq(
      "Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")
    ),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := Function.const(false),
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
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
      username <- sys.env.get("SONATYPE_USERNAME")
      password <- sys.env.get("SONATYPE_PASSWORD")
    } yield
      Credentials(
        "Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        username,
        password
      )).toSeq
  )

  def noPublishSettings: Seq[Setting[_]] = Seq(
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )

}

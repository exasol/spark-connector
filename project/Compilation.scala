package com.exasol.spark.sbt

import sbt._
import wartremover.Wart
import wartremover.Warts
import org.danielnixon.extrawarts.ExtraWart

/** Compiler related settings (flags, warts, lints) */
object Compilation {

  def compilerFlagsFn(scalaVersion: String): Seq[String] =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 12)) => CompilerFlags ++ Scala12CompilerFlags
      case Some((2, 11)) => CompilerFlags
      case _ => CompilerFlags
    }

  def consoleFlagsFn(scalaVersion: String): Seq[String] =
    compilerFlagsFn(scalaVersion).filterNot(Set(
      "-Xfatal-warnings",
      "-Ywarn-unused-import",
      "-Ywarn-unused:imports"
    ))

  /** Compiler flags specific to Scala version 2.12.x */
  private val Scala12CompilerFlags: Seq[String] = Seq(
    "-Xlint:constant",                  // Evaluation of a constant arithmetic expression results in an error.
    "-Ywarn-extra-implicit",            // Warn when more than one implicit parameter section is defined.
    "-Ywarn-unused:implicits",          // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",            // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",             // Warn if a local definition is unused.
    "-Ywarn-unused:params",             // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",            // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates"            // Warn if a private member is unused.
  )

  /**
   * Compiler flags specific to Scala versions 2.10.x and 2.11.x
   *
   * From tpolecat, https://tpolecat.github.io/2017/04/25/scalac-flags.html
   */
  private val CompilerFlags: Seq[String] = Seq(
    "-encoding", "utf-8",               // Specify character encoding used by source files.
    "-deprecation",                     // Emit warning and location for usages of deprecated APIs.
    "-explaintypes",                    // Explain type errors in more detail.
    "-feature",                         // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",           // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",    // Allow macro definition (besides implementation and application)
    "-language:higherKinds",            // Allow higher-kinded types
    "-language:implicitConversions",    // Allow definition of implicit functions called views
    "-unchecked",                       // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                      // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                 // Fail the compilation if there are any warnings.
    "-Xfuture",                         // Turn on future language features.
    "-Xlint:adapted-args",              // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
    "-Xlint:delayedinit-select",        // Selecting member of DelayedInit.
    "-Xlint:doc-detached",              // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",              // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                 // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",      // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",          // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",              // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",           // Option.apply used implicit view.
    "-Xlint:package-object-classes",    // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",    // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",            // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",               // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",     // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",             // Pattern match may not be typesafe.
    "-Yno-adapted-args",                // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",            // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                 // Warn when dead code is identified.
    "-Ywarn-inaccessible",              // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                 // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",          // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",              // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",             // Warn when numerics are widened.
    "-Ywarn-value-discard"              // Warn when non-Unit expression results are unused.
  )

  val JavacCompilerFlags: Seq[String] = Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-parameters",
    "-Xlint:all"
  )

  private def contribWart(name: String) = {
    Wart.custom(s"org.wartremover.contrib.warts.$name")
  }

  private val ExtraWartremoverFlags = Seq(
    contribWart("Apply"),
    contribWart("ExposedTuples"),
    contribWart("MissingOverride"),
    contribWart("NoNeedForMonad"),
    contribWart("OldTime"),
    contribWart("SealedCaseClass"),
    contribWart("SomeApply"),
    contribWart("SymbolicName"),
    // contribWart("UnsafeInheritance"), // Not possible with Spark classes / traits

    ExtraWart.EnumerationPartial,
    ExtraWart.FutureObject,
    ExtraWart.GenMapLikePartial,
    ExtraWart.GenTraversableLikeOps,
    ExtraWart.GenTraversableOnceOps,
    ExtraWart.ScalaGlobalExecutionContext,
    ExtraWart.StringOpsPartial,
    ExtraWart.ThrowablePartial,
    ExtraWart.TraversableOnceOps,
    ExtraWart.UnsafeContains
  )

  val WartremoverFlags: Seq[Wart] = ExtraWartremoverFlags ++ Warts.allBut(
    Wart.Any,
    Wart.Equals,
    Wart.Null,
    Wart.Throw,
    Wart.Var,
    Wart.While
  )

  val WartremoverTestFlags: Seq[Wart] = ExtraWartremoverFlags ++ Warts.allBut(
    Wart.AsInstanceOf,
    Wart.Equals,
    Wart.IsInstanceOf,
    Wart.NonUnitStatements
  )

}

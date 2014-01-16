import sbt._
import Keys._
import ScalaMockPlugin._
 
object TestingScala extends Build {
 
  override lazy val settings = super.settings ++ Seq(
    organization := "com.oreilly.testingscala",
    version := "1.0",
    scalaVersion := "2.9.2",
 
    resolvers += ScalaToolsSnapshots,
    resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                     "releases"  at "http://oss.sonatype.org/content/repositories/releases"),
    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "1.8" % "test" withSources() withJavadoc(),
                                "joda-time" % "joda-time" % "1.6.2" withSources() withJavadoc(),
                                "junit" % "junit" % "4.10" withSources() withJavadoc(),
                                "org.testng" % "testng" % "6.1.1" % "test" withSources() withJavadoc(),
                                "org.specs2" %% "specs2" % "1.12.3" % "test" withSources() withJavadoc(),
                                "org.easymock" % "easymock" % "3.1" % "test" withSources() withJavadoc(),
                                "org.mockito" % "mockito-core" % "1.9.0" % "test" withSources() withJavadoc(),
                                "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc(),
                                "org.scalamock" %% "scalamock-scalatest-support" % "2.4"),
    autoCompilerPlugins := true,
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    addCompilerPlugin("org.scalamock" %% "scalamock-compiler-plugin" % "2.4"))
 
  lazy val myproject = Project("Testing Scala", file(".")) settings(generateMocksSettings: _*) configs(Mock)
}

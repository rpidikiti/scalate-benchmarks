name := "scalate-benchmarks"

version := "1.0"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Scalate Repository" at "http://repo.fusesource.com/nexus/content/repositories/public/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/releases/"

libraryDependencies ++= Seq(
"org.scalatest" % "scalatest_2.9.0" % "1.8" % "test",
"org.fusesource.scalate" % "scalate-wikitext_2.9" % "1.6.1",
"org.fusesource.scalate" % "scalate-page_2.9" % "1.6.1",
"org.fusesource.scalate" % "scalate-test_2.9" % "1.6.1",
"com.typesafe.akka" % "akka-testkit" % "2.0.4",
"com.typesafe.akka" % "akka-actor" % "2.0.4"
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.3.0-SNAPSHOT")

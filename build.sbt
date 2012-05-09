name := "spring-paperclip"

organization := "com.gs"

version := "1.0.0"

credentials  += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo   <<= (version) { version: String =>
  val nexus = "http://nexus.generalsensing.com/content/repositories/"
  if (version.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "snapshots/")
  else                                   Some("releases"  at nexus + "releases/")
}

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.java.dev.inflector" % "inflector" % "0.7.0",
  "org.springframework" % "spring-core" % "3.1.1.RELEASE",
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "junit" % "junit" % "4.9" % "test",
  "com.novocode" % "junit-interface" % "0.7" % "test"
)

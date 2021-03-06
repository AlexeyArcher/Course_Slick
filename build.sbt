name := "Course_Slick"

version := "0.1"

scalaVersion := "2.13.6"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.scalafx" %% "scalafx" % "16.0.0-R22",
  "mysql" % "mysql-connector-java" % "8.0.25",
  "org.slf4j" % "slf4j-nop" % "1.7.30",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "org.scalactic" % "scalactic_2.13" % "3.2.9",
  "org.scalatest" % "scalatest_2.13" % "3.2.9" % "test",
  "com.typesafe.slick" % "slick-testkit_2.13" % "3.3.3" % "test"

)

val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
fork := true
logBuffered in Test := false
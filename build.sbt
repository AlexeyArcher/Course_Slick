name := "Course_Slick"

version := "0.1"

scalaVersion := "2.13.6"
scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.scalafx" %% "scalafx" % "16.0.0-R22",
  "mysql" % "mysql-connector-java" % "8.0.23",
  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.github.nscala-time" %% "nscala-time" % "2.28.0"
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
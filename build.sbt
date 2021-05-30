name := "Course_Slick"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.scalafx" %% "scalafx" % "15.0.1-R21",
  "mysql" % "mysql-connector-java" % "8.0.23",
  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.github.nscala-time" %% "nscala-time" % "2.28.0"
)
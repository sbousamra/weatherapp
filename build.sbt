name := "weatherapp-scala"
version := "0.0.1"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8", "-Xfatal-warnings", "-language:postfixOps")
scalaVersion := "2.11.8"
mainClass := Some("sbousamra.weatherapp.WeatherApp")
libraryDependencies ++= Seq(
  "org.scalatest"               %% "scalatest"                      % "3.0.0"  % "test",
  "org.scalacheck"              %% "scalacheck"                     % "1.12.1" % "test"
)

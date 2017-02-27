name := "weatherapp-scala"
version := "0.0.1"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8", "-Xfatal-warnings", "-language:postfixOps")
scalaVersion := "2.11.8"
mainClass := Some("sbousamra.weatherapp.WeatherApp")

libraryDependencies ++= Seq(
  "org.scalatest"               %% "scalatest"                      % "3.0.0"  % "test",
  "org.scalacheck"              %% "scalacheck"                     % "1.12.1" % "test",
  "org.http4s"                  %% "http4s-dsl"                     % "0.15.0",
  "org.http4s"                  %% "http4s-blaze-server"            % "0.15.0",
  "org.http4s"                  %% "http4s-blaze-client"            % "0.15.0",
  "org.http4s"                  %% "http4s-argonaut"                % "0.15.0",
  "org.http4s"                  %% "http4s-json4s-native"           % "0.15.0",
  "io.argonaut"                 %% "argonaut"                       % "6.1"
)

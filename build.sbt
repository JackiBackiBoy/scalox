scalaVersion := "3.1.0"
run / fork := true
run / javaOptions ++= Seq("-XstartOnFirstThread",
                          "-Djava.awt.headless=true")
Compile / scalaSource := baseDirectory.value / "src"
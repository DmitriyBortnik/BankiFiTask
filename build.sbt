name := "BankiFiTask"

version := "0.1"

scalaVersion := "2.13.5"

val akkaVersion = "2.6.12"
val akkaHttpVersion = "10.1.10"
val json4s = "3.6.6"
val scalatestVersion = "3.1.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
  "nu.validator.htmlparser" % "htmlparser" % "1.2.1",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.0",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "org.json4s" %% "json4s-ext" % json4s,
  "org.json4s" %% "json4s-native" % json4s,
  "org.scalatest" %% "scalatest" % scalatestVersion)

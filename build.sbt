enablePlugins(GitVersioning)

Compile / scalafmtOnCompile := true

organization := "com.github.simplylizz"
name := "spark-fast-tests"

version := "2.0.0"

val versionRegex      = """^(.*)\.(.*)\.(.*)$""".r

val sparkVersion = settingKey[String]("Spark version")

val scala2_13= "2.13.8"
val scala2_12= "2.12.15"
val scala2_11= "2.11.12"

sparkVersion := System.getProperty("spark.testVersion", "3.2.1")
crossScalaVersions := {sparkVersion.value match {
  case versionRegex("3", m, _) if m.toInt >= 2 => Seq(scala2_12, scala2_13)
  case versionRegex("3", _ , _) => Seq(scala2_12)
}
}

scalaVersion := crossScalaVersions.value.head

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion.value % "provided"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"

credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")

Test / fork := true
javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled", "-Duser.timezone=GMT")

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/simplylizz/spark-fast-tests"))
scmInfo := Some(ScmInfo(url("https://github.com/simplylizz/spark-fast-tests"), "git@github.com:simplylizz/spark-fast-tests.git"))

updateOptions := updateOptions.value.withLatestSnapshots(false)

publishMavenStyle := true

publishTo := sonatypePublishToBundle.value

Global / useGpgPinentry := true

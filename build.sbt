import xerial.sbt.Sonatype._

lazy val snapshot: Boolean = false
lazy val v: String = {
  val vv = "0.3.0"
  if (!snapshot) vv
  else vv + "-SNAPSHOT"
}

lazy val scalaReflect = Def.setting {
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
}

organization in ThisBuild := "ua.pp.itkpi"

val `scala-2-12` = "2.12.8"
val `scala-2-11` = "2.11.12"

def sonatypeProject(id: String, base: File) =
  Project(id, base)
    .settings(
      name := id,
      isSnapshot := snapshot,
      version := v,
      scalaVersion := `scala-2-12`,
      crossScalaVersions := Seq(`scala-2-11`, `scala-2-12`),
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      scalacOptions ++= Seq("-Ypartial-unification", "-feature"),
      resolvers += Resolver.sonatypeRepo("releases"),
      addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
    )

lazy val laws4s = sonatypeProject(id = "law4s", base = file("./kernel"))
  .settings(
    libraryDependencies += scalaReflect.value
  )
lazy val laws4cats = sonatypeProject(id = "law4cats", base = file("./cats"))
  .dependsOn(laws4s)
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"
  )

lazy val examples = project
  .in(file("./examples"))
  .dependsOn(laws4s, laws4cats)
  .settings(
    name := "laws4s-examples",
    isSnapshot := snapshot,
    version := v,
    scalaVersion := `scala-2-12`,
    crossScalaVersions := Seq(`scala-2-11`, `scala-2-12`),
    publish := {},
    scalacOptions ++= Seq("-Ypartial-unification", "-feature"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
  )

lazy val root = project
  .in(file("."))
  .aggregate(laws4s, laws4cats)
  .settings(
    name := "laws4s-root",
    isSnapshot := snapshot,
    version := v,
    scalaVersion := `scala-2-12`,
    crossScalaVersions := Seq(`scala-2-11`, `scala-2-12`),
    publish := {},
    scalacOptions ++= Seq("-Ypartial-unification", "-feature"),
    resolvers += Resolver.sonatypeRepo("releases"),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
  )

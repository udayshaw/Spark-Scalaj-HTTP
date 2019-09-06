name := "scalaj_http_spark"
version := "1.0"
resolvers += "Local Maven Repository" at "file:///opt/kafka-streaming/.m2/repository"
scalaVersion := "2.11.0"

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.3.1" from "file:///opt/kafka-streaming/spark-core_2.11-2.3.1.jar"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.3.1" from "file:///opt/kafka-streaming/spark-sql_2.11-2.3.1.jar"
libraryDependencies += "org.apache.kafka" % "scalaj-http_2.11" % "2.3.0" from "file:///opt/kafka-streaming/scalaj-http_2.11-2.3.0.jar"

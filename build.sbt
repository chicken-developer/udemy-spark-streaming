name := "udemy-spark-streaming"

version := "0.1"

scalaVersion := "2.12.10"

val vegasVersion = "0.3.11"
val sparkVersion = "3.0.0"
val postgresVersion = "42.2.2"
val cassandraConnectorVersion = "3.0.0"
val akkaVersion = "2.6.9"
val akkaHttpVersion = "10.2.0"
val twitter4jVersion = "4.0.7"
val kafkaVersion = "2.6.0"
val log4jVersion = "2.13.3"
val nlpLibVersion = "3.5.1"

resolvers ++= Seq(
  "bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven",
  "Typesafe Simple Repository" at "https://repo.typesafe.com/typesafe/simple/maven-releases",
  "MavenRepository" at "https://mvnrepository.com"
)

/*
  Beware that if you're working on this repository from a work computer,
  corporate firewalls might block the IDE from downloading the libraries and/or the Docker images in this project.
 */
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,

  // streaming
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  // streaming-kafka
  "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion,

  // low-level integrations
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion,

  "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnectorVersion,

  // postgres
  "org.postgresql" % "postgresql" % postgresVersion,

  // logging
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,

  // kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion
)
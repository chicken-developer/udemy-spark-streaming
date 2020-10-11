package StructureStreaming

import org.apache.spark.sql.{DataFrame, SparkSession}

object StreamingDataFrames {

  val spark = SparkSession.builder()
    .appName("FirstStream")
    .master("local[2]")
    .getOrCreate()

  def readFromSocket(): Unit = {
    val lines: DataFrame = spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port",12345)
      .load()

    val query = lines.writeStream
      .format("console")
      .outputMode("append")
      .start()

    query.awaitTermination()
  }
  def main(args: Array[String]): Unit = {
    println("Start program")
    readFromSocket()
  }
}

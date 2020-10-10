package Recap

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._
object SparkRecap{
  val spark = SparkSession.builder()
    .appName("SparkRecap")
    .master("local[2]")
    .getOrCreate()

  //read a dataframe
  val cars = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/resources/data/cars")

  import spark.implicits._ // for using $


  val usefulCarData: DataFrame = cars.select(
    col("Name"), // column object
    $"Year", // Another column object - use in practical
    ($"Weight_in_lbs" / 2.2).as("Weight_in_kg"),
    expr("Weight_in_lbs / 2.2").as("Weight_in_kg_2")
  )

  val carWeights: DataFrame = cars.selectExpr("Weight_in_lbs / 2.2")

  //Filter
  val europeanCar = cars.filter($"Origin" =!= "USA") // can use cars.where($"Origin" =!= "USA")

  //Aggregations
  val averageHP = cars.select(avg($"Horsepower").as("average_hp"))
  //Another math: sum, meam, stddev, min, max

  //Grouping
  val groupByOrigin = cars
    .groupBy($"Origin")
    .count()

  //Joining
  val guitarPlayers = spark.read
    .option("inferSchema", "true")
    .json("src/main/resources/data/guitarPlayers")
  val bands = spark.read
    .option("inferSchema","true")
    .json("src/main/resources/data/bands")

  val guitaristsBands = guitarPlayers.join(bands, guitarPlayers.col("band") === bands.col("id"))
  /* Some join type:
      - inner: only the matching rows are kept
      - left/ right/ full outer join
      - semi/ anti
   */


  //datasets
  case class GuitarPlayer(id: Long, name: String, guitars: Seq[Long], band: Long)
  val guitarPlayerDS = guitarPlayers.as[GuitarPlayer] //need import spark.implicits
  //Now can handle this dataset by using fold, map, filter
  guitarPlayerDS.map(_.name)

  //spark SQL
  cars.createOrReplaceTempView("cars")
  val americanCar = spark.sql(
    """
      |select Name from cars when Origin = 'USA'
      |""".stripMargin
  )

  //low-level API: RDDs
  val sparkContext = spark.sparkContext
  val numbersRDD: RDD[Int] = sparkContext.parallelize(1 to 100000000)
  //So now can do some functional operators
  numbersRDD.map(_ * 2)

  //RDD -> DF
  val numbersDF = numbersRDD.toDF("number") // lose type info and get SQL capability

  //RDD -> DS
  val numberDS = spark.createDataset(numbersRDD)

  //DS -> RDD
  val guitarPlayerRDD = guitarPlayerDS.rdd

  //DF -> RDD
  val carRDD: RDD[Row] = cars.rdd



  def main(args: Array[String]): Unit = {
    //Show dataframe to console
    cars.show()
    cars.printSchema()
    usefulCarData.show()
    carWeights.show()
    europeanCar.show()
    averageHP.show()
    groupByOrigin.show()

  }

}

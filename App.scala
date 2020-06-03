import scalaj.http._
import org.apache.spark.sql.functions._
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object scalaj_http_spark {
  def main(args: Array[String]): Unit = {
val conf = new SparkConf().setAppName("scalaj_http_spark")
val spark = SparkSession.builder().config(conf).getOrCreate()
import spark.implicits._
val sc = new SparkContext(conf)
var driver = "oracle.jdbc.driver.OracleDriver"
var orc = "jdbc:oracle:thin:@xx.xx.xx.xx:1521/service_name";
var username = "username`";
var password = "password";
var url="";
val topic=spark.read.format("jdbc").option("url", orc).option("dbtable", "schema.table_name").option("user", username).option("password", password).option("driver", driver).load
//topic dataframe contains list of URL(that give JSON as output in response body) under url column
val idx=topic.select($"id",$"kpi",$"url",$"remark").collect() 
for(i <- idx) {
  var ipcount=Http(i(2).asInstanceOf[String]).header("Content-Type", "application/json").param("q", "smith").auth("username", "password").asString
  var rdd = sc.parallelize(Seq(ipcount.body))
  var jsonDF = spark.read.json(rdd.map(x => x.toString())).toDF //creating a DF out of json from response body
  //jsonDF.select(explode($"data.result.value").as("value")).printSchema
  println(i);
  jsonDF.select(explode($"data.result.value").as("value")).select($"value"(0).as("String").as("date"),$"value"(1).as("value"),lit(i(1).asInstanceOf[String]).as("kpi"),lit(i(3).asInstanceOf[String]).as("remark")).show(truncate=false)
}
  }
}



package challenge.basic
import challenge.parsers.CSV
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.Map
import collection.JavaConversions._



object RDDConvert {
  def main(args: Array[String]){
    //Path
    val csvParser = new CSV("TestFiles/HumanitarianData/DataForWorldFoodProgram/CSV/example.csv")
    val myMap = asScalaMap(csvParser.getMap())   
    
    //Set Up Spark
    val conf = new SparkConf().setAppName("SparkProject").setMaster("local")
    val sc = new SparkContext(conf)
    
    //Set Up RDD
    val csv_rdd = sc.parallelize(myMap.toSeq)
    println(csv_rdd.getClass())
    

     
  }
}
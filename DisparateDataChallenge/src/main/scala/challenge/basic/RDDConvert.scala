package challenge.basic
import challenge.parsers._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import collection.JavaConversions._



object RDDConvert {
  def main(args: Array[String]){
    val conf = new SparkConf()
        .setAppName("SparkProject")
        .setMaster("local")
    val sc = new SparkContext(conf)
    val csvParser = new CSV("TestFiles/HumanitarianData/DataForWorldFoodProgram/CSV/example.csv")
    val mymap = asScalaMap(csvParser.getMap())
    println(mymap.get("adm0_name"))

     
  }
}
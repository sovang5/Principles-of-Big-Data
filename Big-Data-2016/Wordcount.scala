import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
object WordCount {
  def main(args: Array[String]) = {

    val conf = new SparkConf()
      .setAppName("WordCount")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val test = sc.textFile("hdfs://cloud0:9001/input/a.txt")

    test.flatMap { line => 
      line.split(" ") 
    }
      .map { word => 
        (word, 1) 
      }
      .reduceByKey(_ + _) 
      .saveAsTextFile("hdfs://cloud0:9001/output/output_result") 

    sc.stop
  }
}

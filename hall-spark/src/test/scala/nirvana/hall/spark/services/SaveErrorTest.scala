package nirvana.hall.spark.services

import kafka.serializer.StringDecoder
import nirvana.hall.spark.config.NirvanaSparkConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit.Test

/**
 * Created by wangjue on 2016/2/22.
 */
class SaveErrorTest {

  @Test
  def streamTest (){
    val array = Array("src/test/resources/test_spark.xml")
    SaveErrorStream.main(array)
  }

}

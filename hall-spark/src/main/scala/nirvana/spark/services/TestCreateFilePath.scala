package nirvana.spark.services

import java.io.File
import java.util.Properties

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}
import org.apache.commons.io.FileUtils


/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-03
 */
object TestCreateFilePath {
  def main(args:Array[String]): Unit ={
    val props = new Properties()
    props.put("metadata.broker.list", "10.1.7.140:9092")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)

    //
    val files  = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\DBDATA\\personids"),Array[String]("txt"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val fptFile = itt.next()
      val lines = FileUtils.readLines(fptFile).iterator()
      while(lines.hasNext){
        ///data/fpt-files/2/error/R3402725324222225427244.fpt
        //0 until 1000000 foreach { i =>
        val fptPath = lines.next()
        if (!fptPath.isEmpty) {
          val message: KeyedMessage[String, String] = new KeyedMessage("DBDATA", fptPath, fptPath)
          println(message)
          producer.send(message)
        }
      }
    }

    //
    /*val it = FileUtils.readLines(new File("E:\\fpt.txt")).iterator()

    while(it.hasNext){
      ///data/fpt-files/2/error/R3402725324222225427244.fpt
    //0 until 1000000 foreach { i =>
        val fptPath = it.next().replaceAll("/data/fpt-files", "")
        if (!fptPath.isEmpty) {
          val message: KeyedMessage[String, String] = new KeyedMessage("FPT", fptPath, fptPath)
          println(message)
          producer.send(message)
        }
    }*/
    producer.close
  }
}

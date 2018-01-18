package nirvana.hall.spark.services

import java.io.File
import java.net.URI

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.spark.config.NirvanaSparkConfig
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by wangjue on 2017/5/24.
  */
object BigDataForFileStream {

  def startProcess(parameter : NirvanaSparkConfig,listFile : String) : Unit = {
    /*val hadoopConf = new Configuration()
    val hdfs = FileSystem.get(new URI(parameter.hdfsServer),hadoopConf)
    var hasListFile = true
    try {
      hasListFile = hdfs.exists(new Path(parameter.hdfsServer+listFile))
    } catch {
      case e:IllegalArgumentException =>  System.err.append(e.getMessage);hasListFile = false
    }
    if (!hasListFile) System.exit(0)
    try {
      hdfs.exists(new Path(parameter.hdfsServer+listFile+".pid"))
    } catch {
      case e:IllegalArgumentException =>  hdfs.create(new Path(parameter.hdfsServer+listFile+".pid"))
    }
    val pidRDD = sc.textFile(parameter.hdfsServer+listFile+".pid") //record accum to file
    */
    val conf = new SparkConf().setAppName(parameter.appName)
      .setMaster(parameter.masterServer)
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    conf.set("spark.driver.host",parameter.host)
    val sc = new SparkContext(conf)
    var accum = sc.accumulator(0)
    val listRDD =
      if (parameter.hdfsServer == "None")
        sc.textFile(listFile,parameter.partitionsNum)
      else
        sc.textFile(parameter.hdfsServer+listFile,parameter.partitionsNum)

      listRDD.foreachPartition{ partitionRdd =>
        SysProperties.setConfig(parameter)
        partitionRdd.foreach{ rdd =>
          {
            //SysProperties.setConfig(parameter)
            ImageProviderService.requestRemoteFile(parameter,rdd.toString)
          }.flatMap{x=>
            //SysProperties.setConfig(parameter)
            DecompressImageService.requestDecompress(parameter,x._1,x._2)
          }.flatMap{x=>
            //SysProperties.setConfig(parameter)
            ExtractFeatureService.requestExtract(parameter,x._1,x._2,x._3)
          }.foreach{ x=>
            //SysProperties.setConfig(parameter)
            PartitionRecordsSaverService.savePartitionRecords(parameter)(Iterator(x))
          }
          accum += 1
        }
      }

      /*listRDD.foreach{ rdd =>
        {
          SysProperties.setConfig(parameter)
          ImageProviderService.requestRemoteFile(parameter,rdd.toString)
        }.flatMap{x=>
          SysProperties.setConfig(parameter)
          DecompressImageService.requestDecompress(parameter,x._1,x._2)
        }.flatMap{x=>
          SysProperties.setConfig(parameter)
          ExtractFeatureService.requestExtract(parameter,x._1,x._2,x._3)
        }.foreach{ x=>
          SysProperties.setConfig(parameter)
          PartitionRecordsSaverService.savePartitionRecords(parameter)(Iterator(x))
        }
        accum += 1
      }*/

  }

  def main(args: Array[String]): Unit = {
    if(args.length != 2){
      println("please add parameter and id list: <configuration file path>,<the listed files for fpt name>")
      return
    }
    val content = Source.fromFile(new File(args(0)),MonadSupportConstants.UTF8_ENCODING).mkString
    val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
    val pidFile = args(1)
    startProcess(config,pidFile)
  }

}

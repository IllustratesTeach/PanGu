package nirvana.hall.spark.services

import java.io.{File, ByteArrayInputStream}

import monad.support.MonadSupportConstants
import monad.support.services.XmlLoader
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.BigDataStream._
import org.apache.commons.io.FileUtils
import org.junit.Test

import scala.io.Source
import scala.util.control.NonFatal

/**
 * Created by wangjue on 2016/5/28.
 */
class DownLoadFPTFromFtpServerTest {
  @Test
  def downLoadTest(): Unit ={
    def fetch(times : Int) {
      try {
        /*for (i <- 1 to 100000) {
          println("times:" + i + "-start|" + System.currentTimeMillis())
          var path = "D:\\ftp_server\\20160528\\R2512126324000923823243.fpt"
          if (i%2==0) path = "D:\\ftp_server\\20160528\\3.fpt"
          val data = SparkFTPClient.downloadFPTFromFTPServer(null, path)
          val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)
          println("data length:" + data.length)
          println("times:" + i + "-ended|" + System.currentTimeMillis())
        }*/
        val content = Source.fromFile(new File("src/test/resources/test_spark_file.xml"),MonadSupportConstants.UTF8_ENCODING).mkString
        val config = XmlLoader.parseXML[NirvanaSparkConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/spark/nirvana-spark.xsd")))
        SysProperties.setConfig(config)
        val lines = FileUtils.readLines(new File("D:\\fpt_path\\1465188450594.txt")).iterator()
        while(lines.hasNext){
          val fptPath = lines.next()
          if (!fptPath.isEmpty) {
            println(fptPath)
            val data = SparkFTPClient.downloadFPTFromFTPServer(null, fptPath)
            val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)
            println("data length:" + data.length)
          }
        }

      } catch{
        case e:CallRpcException=>
          if(times == 4)  reportException(e)  else fetch(times+1)
        case NonFatal(e)=>
          reportException(e)
      }
    }
    def reportException(e: Throwable) = {
      e.printStackTrace(System.err)
      println("-----------"+e.getMessage)
      Nil
    }
    fetch(1)
  }
}

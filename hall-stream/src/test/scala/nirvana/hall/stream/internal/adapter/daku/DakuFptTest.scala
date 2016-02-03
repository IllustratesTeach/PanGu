package nirvana.hall.stream.internal.adapter.daku

import java.io.{BufferedReader, InputStreamReader, File}
import java.net.{HttpURLConnection, URL}
import java.util

import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.stream.internal.adapter.bianjian.BianjianTestSymobls
import nirvana.hall.stream.internal.adapter.daku.util.FPTObject
import nirvana.hall.stream.services.StreamService
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.junit.{Assert, Test}
import org.mockito.Mockito
import org.slf4j.LoggerFactory

/**
 * Created by wangjue on 2015/12/29.
 */
class DakuFptTest {
  private val logger = LoggerFactory getLogger getClass
  @Test
  def parseFpt : Unit = {
    //val fpt = FPTObject.parseOfFile(new File("D:\\fpt\\P5200000000002015129985.fpt"))
    /*val fpt = FPTObject.parseOfFile(new File("D:\\fpt\\R0000123324111626522353.fpt"))//R0000123324111626522353.fpt
    val list = fpt.getTpDataList
    Assert.assertTrue(list.size()>0)*/
  }

  @Test
  def recordFptPath : Unit = {
    val fpt_dir = "D:\\backup\\"
    val files  = FileUtils.listFiles(new File(fpt_dir),Array[String]("fpt"),true)
    val total = files.size()
    println("start stream,total files:{"+total+"}")
    val it = files.iterator()
    var j = 0
    var txtIndex = 0
    var fptPathFile = new File(fpt_dir+"\\fpt.txt")

    while (it.hasNext) {
      val fptFile = it.next()
      j += 1
      //读取文件，记录文件名和文件路径
      if (fptPathFile.length() <= 50485) {
        //限制文件大小为10M
        FileUtils.writeStringToFile(fptPathFile, "Line "+j+":"+fptFile.getName+";path:"+fptFile.getAbsolutePath+"\r\n",true)
      } else {
        txtIndex += 1
        fptPathFile = new File(fpt_dir + "\\fpt" + txtIndex + ".txt")
      }
    }

  }

  @Test
  def getFpt : Unit = {
    val fpt_dir = "D:\\backup\\"
    val files  = FileUtils.listFiles(new File(fpt_dir),Array[String]("txt"),true)
    val it = files.iterator()
    while (it.hasNext) {
      val fptFile = it.next()
      val lines  = FileUtils.readLines(fptFile)

      for (i <- 0 to lines.size()-1) {
        val line = lines.get(i)
        val path = line.mkString
        val remoteFptPath = path.substring(path.indexOf("backup")+7)
        println(remoteFptPath)

        if (path.indexOf("test") != -1) {
          val remoteFpt = "http://127.0.0.1/"+remoteFptPath
          val url = new URL(remoteFpt)
          val connection = url.openConnection()
          connection.setRequestProperty("accept", "0/0")
          connection.setRequestProperty("connection", "Keep-Alive")
          connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
          connection.connect();
          val in = new BufferedReader(new InputStreamReader(connection.getInputStream()))
          var l = ""
          while ((l = in.readLine())!=null && l != null) {
            println("test.fpt data - "+ l)
          }
          in.close()
        }
      }
    }

  }


  @Test
  def decodeFptData : Unit = {
    val streamService = Mockito.mock(classOf[StreamService])
    /*val registry = Mockito.mock(classOf[RegistryShutdownHub])
    System.setProperty(DakuSymobls.MNT_JDBC_URL, "jdbc:oracle:thin:@127.0.0.1:1521:xe")
    System.setProperty(DakuSymobls.MNT_JDBC_USER, "pcsys_cs")
    System.setProperty(DakuSymobls.MNT_JDBC_PASS, "11")

    val datasource = DakuModule.buildMntDataSource(registry,logger)*/
    val dakuStream = new DakuStream(null,streamService)
    dakuStream.startStream()

  }

}

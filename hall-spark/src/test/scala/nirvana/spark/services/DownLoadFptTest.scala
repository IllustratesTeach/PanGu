package nirvana.spark.services

import java.io.File
import java.net.{URL, URLConnection}

import kafka.producer.KeyedMessage
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
 * Created by wangjue on 2016/2/24.
 */
class DownLoadFptTest {

  @Test
  def downLoadTest(): Unit ={
    val it = FileUtils.readLines(new File("D:\\2900\\2900.txt"),"GBK").iterator()

    while(it.hasNext){
        val fptPath = it.next()
        if (!fptPath.isEmpty) {
          println(fptPath)
          val fileName = fptPath.substring(fptPath.lastIndexOf("/"))
          val remoteFptPath = fptPath
          println(remoteFptPath)
          val remoteFpt = "http://10.1.7.212/fpt" + remoteFptPath
          var connection: URLConnection = null
          try {
            val url = new URL(remoteFpt)
            connection = url.openConnection()
            connection.setRequestProperty("accept", "0/0")
            connection.setRequestProperty("connection", "Keep-Alive")
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
            connection.setRequestProperty("Content-type", "text/html")
            connection.setRequestProperty("Accept-Charset", "GB2312")
            connection.setRequestProperty("contentType", "GB2312")
            connection.connect()
            FileUtils.copyInputStreamToFile(connection.getInputStream(), new File("D:\\2900\\"+fileName))
          }
          catch {
            case e: Throwable =>
              println(e.getMessage)
          }


        }
    }
  }

}

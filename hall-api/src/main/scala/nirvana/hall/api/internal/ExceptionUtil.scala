package nirvana.hall.api.internal

import java.io.{PrintWriter, StringWriter}

/**
  * Created by yuchen on 2017/6/10.
  */
object ExceptionUtil {

  /**
    * 获取异常信息
    *
    * @param e 异常
    */
  def getStackTraceInfo(e: Exception): String = {
    var info = ""
    val writer = new StringWriter
    val printWriter = new PrintWriter(writer)
      try{
        info = writer.toString
      }
    catch {
      case exception: Exception => {
        exception.printStackTrace()
      }
    } finally {
      if (printWriter != null) printWriter.close()
      if (writer != null) writer.close()
    }
    info
  }
}

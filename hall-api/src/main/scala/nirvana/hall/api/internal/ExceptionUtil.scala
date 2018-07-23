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
  def getStackTraceInfo[A<:Throwable](e: A): String = {
    var info = ""
    val writer = new StringWriter
    val printWriter = new PrintWriter(writer,true)
      try{
        e.printStackTrace(printWriter)
        info = writer.toString
        printWriter.flush
        writer.flush
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

package nirvana.hall.v70.internal

import java.text.SimpleDateFormat
import java.util.Date

import com.google.protobuf.GeneratedMessage.GeneratedExtension
import com.google.protobuf.Message
import nirvana.hall.api.internal.WebHttpClientUtils
import nirvana.hall.protocol.sys.CommonProto.BaseRequest

/**
 * some implicit converter
 * Created by songpeng on 15/12/7.
 */
package object sync {
  implicit def string2Int(string: String): Int ={
    if(isNonBlank(string))
      Integer.parseInt(string)
    else
      0
  }
  implicit def date2String(date: Date): String = {
    if (date != null)
      new SimpleDateFormat("yyyyMMdd").format(date)
    else ""
  }
  implicit def string2Date(date: String): Date= {
    if (date != null && date.length == 8)
      new SimpleDateFormat("yyyyMMdd").parse(date)
    else null
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0
  def magicSet(value:String,fun:String=>Any){
    if(isNonBlank(value)){ fun(value)}
  }


  def httpCall[T](ip: String, port: String, extension: GeneratedExtension[BaseRequest, T], request: T, responseBuilder: Message.Builder): Unit ={
    WebHttpClientUtils.call("http://"+ip+":"+port, extension, request, responseBuilder)
  }

}

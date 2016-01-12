package nirvana.hall.api.internal

import java.text.SimpleDateFormat
import java.util.Date

/**
 * some implicit converter
 * Created by songpeng on 15/12/7.
 */
package object sync {
  implicit def string2Int(string: String): Int ={
    if(string != null && string.length > 0)
      Integer.parseInt(string)
    else
      0
  }
  implicit def date2String(date: Date): String = {
//    date.toString("yyyyMMdd")
    if (date != null)
      new SimpleDateFormat("yyyyMMdd").format(date)
    else ""
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0
  def magicSet(value:String,fun:String=>Any){
    if(isNonBlank(value)){ fun(value)}
  }
}

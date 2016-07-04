package nirvana.hall.api.internal

import java.text.{ParsePosition, SimpleDateFormat}
import java.util.Date

/**
 * Created by songpeng on 16/7/2.
 */
object DateConverter {

  /**
   * 将字符串转为Date
   * @param str
   * @param format
   * @return
   */
  def convertString2Date(str: String, format: String): Date ={
    var date:Date = null
    if(str != null && format != null){
      try{
        val formatter = new SimpleDateFormat(format)
        date = formatter.parse(str,new ParsePosition(0))
      }catch {
        case e: Exception=>
          e.printStackTrace()
      }
    }
    date
  }
}

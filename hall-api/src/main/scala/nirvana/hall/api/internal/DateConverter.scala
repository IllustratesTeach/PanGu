package nirvana.hall.api.internal

import java.nio.ByteBuffer
import java.text.{ParsePosition, SimpleDateFormat}
import java.util.Date

import nirvana.hall.c.services.gbaselib.gbasedef.GAFIS_DATETIME


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
  def convertString2Date(str: String, format: String): Date = {
    var date: Date = null
    if (str != null && format != null) {
      try {
        val formatter = new SimpleDateFormat(format)
        date = formatter.parse(str, new ParsePosition(0))
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
    date
  }

  /**
   * 将gafis日期转换为字符串yyyyMMddHHmmss
   * @param dateTime
   * @return
   */
  def convertAFISDateTime2String(dateTime: GAFIS_DATETIME): String = {
    val year = shortConvert(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth + 1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = Math.abs(shortConvert(dateTime.tTime.tMilliSec) / 1000)

    "%04d%02d%02d%02d%02d%02d".format(year, month, day, hour, min, sec)
  }

  /**
   * 将字符yyyyMMddHHmmss 转为gafis日期
   * @param str
   * @return
   */
  def convertString2AFISDateTime(str: String): GAFIS_DATETIME ={
    if(str.length == 14){
      val date = convertString2Date(str, "yyyyMMddHHmmss")
      val dateTime = new GAFIS_DATETIME
      dateTime.tDate.setJavaYear(date.getYear)
      dateTime.tDate.tMonth = date.getMonth.toByte
      dateTime.tDate.tDay = date.getDate.toByte
      dateTime.tTime.tHour = date.getHours.toByte
      dateTime.tTime.tMin = date.getMinutes.toByte
      dateTime.tTime.setJavaSecs(date.getSeconds)

      dateTime
    }else{
      return null
    }
  }

  /**
   * short高地位转换
   * @param short
   * @return
   */
  private def shortConvert(short: Short): Short = {
    val arr = ByteBuffer.allocate(2).putShort(short).array()
    val t = arr(0)
    arr(0) = arr(1)
    arr(1) = t
    ByteBuffer.wrap(arr, 0, 2).getShort()
  }

  /**
    * 获得系统当前时间
    * @return
    */
  def getNowDate():Date={
    val now:Date = new Date()
    val formatter = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm")
    val date = formatter.parse(formatter.format(now), new ParsePosition(0))
    date
  }
}

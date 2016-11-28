package nirvana.hall.api.internal

import java.text.{ParsePosition, SimpleDateFormat}
import java.util.Date

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.gbasedef.GAFIS_DATETIME


/**
 * Created by songpeng on 16/7/2.
 */
object DateConverter extends LoggerSupport{

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
          error(e.getMessage, e)
      }
    }
    date
  }

  /**
    * 日期格式化转换
    * @param date
    * @param format (default YYYYMMDD)
    * @return
    */
  def convertDate2String(date: Date, format: String = "YYYYMMDD"): String = {
    if(date != null){
      new SimpleDateFormat(format).format(date)
    } else {
      ""
    }
  }

  /**
   * 将gafis日期转换为字符串yyyyMMddHHmmss
   * @param dateTime
   * @return
   */
  def convertAFISDateTime2String(dateTime: GAFIS_DATETIME): String = {
    val year = switchShortEndian(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth + 1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = Math.abs(switchShortEndian(dateTime.tTime.tMilliSec) / 1000)

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
  private def switchShortEndian(short: Short): Short = {
    (((short >>> 8) & 0xff) | (short << 8)).toShort
  }
}

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
    val year = DataConverter.switchShortEndian(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth + 1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = dateTime.tTime.convertAsJavaSecs()

    val date = "%04d%02d%02d%02d%02d%02d".format(year, month, day, hour, min, sec)
    if(date.equals("00000100000000")) ""    //00000100000000 时间为空 String 转Date时会发生转换错误
    else date
  }
  /**
    * 将gafis日期转换为字符串 yyyy-MM-dd HH:mm:ss
    * @param dateTime
    * @return
    */
  def convertAFISDateTime2String2(dateTime: GAFIS_DATETIME): String = {
    val year = DataConverter.switchShortEndian(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth + 1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = dateTime.tTime.convertAsJavaSecs()

    "%04d-%02d-%02d %02d:%02d:%02d".format(year, month, day, hour, min, sec)
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
      dateTime.tDate.setJavaYear(date.getYear + 1900)
      dateTime.tDate.tMonth = date.getMonth.toByte
      dateTime.tDate.tDay = date.getDate.toByte
      dateTime.tTime.tHour = date.getHours.toByte
      dateTime.tTime.tMin = date.getMinutes.toByte
      dateTime.tTime.setJavaSecs(date.getSeconds)

      dateTime
    }else if(str.length == 8){
      val date = convertString2Date(str, "yyyyMMdd")
      val dateTime = new GAFIS_DATETIME
      dateTime.tDate.setJavaYear(date.getYear + 1900)
      dateTime.tDate.tMonth = date.getMonth.toByte
      dateTime.tDate.tDay = date.getDate.toByte

      dateTime
    }else{
      return null
    }
  }

  /**
    * 将java.util.Date转换为GAFIS_DATETIME
    * @param date
    * @return
    */
  def convertDate2AFISDateTime(date: Date): GAFIS_DATETIME ={
    val dateTime = new GAFIS_DATETIME
    dateTime.tDate.setJavaYear(date.getYear + 1900)
    dateTime.tDate.tMonth = date.getMonth.toByte
    dateTime.tDate.tDay = date.getDate.toByte
    dateTime.tTime.tHour = date.getHours.toByte
    dateTime.tTime.tMin = date.getMinutes.toByte
    dateTime.tTime.setJavaSecs(date.getSeconds)

    dateTime
  }
}

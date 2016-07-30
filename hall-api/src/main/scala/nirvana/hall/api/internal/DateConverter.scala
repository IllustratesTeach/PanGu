package nirvana.hall.api.internal

import java.nio.ByteBuffer
import java.text.{ParsePosition, SimpleDateFormat}
import java.util.Date

import nirvana.hall.c.services.ghpcbase.ghpcdef._

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
  def convertAFISDateTime2String(dateTime: AFISDateTime): String = {
    val year = shortConvert(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth + 1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = Math.abs(shortConvert(dateTime.tTime.tMilliSec) / 1000)

    "%04d%02d%02d%02d%02d%02d".format(year, month, day, hour, min, sec)
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
}

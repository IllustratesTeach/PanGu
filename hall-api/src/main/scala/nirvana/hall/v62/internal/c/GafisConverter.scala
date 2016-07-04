package nirvana.hall.v62.internal.c

import java.nio.ByteBuffer

import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime

/**
 * gafis数据转换类
 */
object GafisConverter {

  /**
   * 将gafis日期转换为字符串yyyyMMddHHmmss
   * @param dateTime
   * @return
   */
  def convertAFISDateTime2String(dateTime: AFISDateTime): String ={
    val year = shortConvert(dateTime.tDate.tYear)
    val month = dateTime.tDate.tMonth +1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
    val sec = shortConvert(dateTime.tTime.tMilliSec)/1000

    "%04d%02d%02d%02d%02d%02d".format(year, month, day, hour, min, sec)
  }

  /**
   * short高地位转换
   * @param short
   * @return
   */
  private def shortConvert(short: Short):Short = {
    val arr = ByteBuffer.allocate(2).putShort(short).array()
    val t = arr(0)
    arr(0) = arr(1)
    arr(1) = t
    ByteBuffer.wrap(arr,0,2).getShort()
  }

}

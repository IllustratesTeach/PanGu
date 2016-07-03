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
    val arr = ByteBuffer.allocate(2).putShort(dateTime.tDate.tYear).array()
    val t = arr(0)
    arr(0) = arr(1)
    arr(1) = t
    val year = ByteBuffer.wrap(arr,0,2).getShort()
    val month = dateTime.tDate.tMonth +1
    val day = dateTime.tDate.tDay
    val hour = dateTime.tTime.tHour
    val min = dateTime.tTime.tMin
//    val sec = dateTime.tTime.tMilliSec

    "%04d%02d%02d%02d%02d00".format(year, month, day, hour, min)
  }

}

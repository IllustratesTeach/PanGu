package nirvana.hall.api.internal

import java.nio.ByteBuffer

/**
 * 数据转换类
 */
object DataConverter {

  /**
    * 转换6个字节成long
    * @param bytes 待转换的六个字节
    * @return 转换后的long数字
    */
  def convertSixByteArrayToLong(bytes: Array[Byte]): Long = {
    var l = 0L
    l |= (0xff & bytes(0)) << 16
    l |= (0xff & bytes(1)) << 8
    l |= (0xff & bytes(2))
    l <<= 24

    l |= (0xff & bytes(3)) << 16
    l |= (0xff & bytes(4)) << 8
    l |= (0xff & bytes(5))

    l
  }
  def convertLongAsSixByteArray(sid: Long): Array[Byte]=
    ByteBuffer.allocate(8).putLong(sid).array().slice(2,8)

  /**
    * short高地位转换
    * @param short
    * @return
    */
  def switchShortEndian(short: Short): Short = {
    (((short >>> 8) & 0xff) | (short << 8)).toShort
  }
}

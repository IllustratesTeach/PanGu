package nirvana.hall.matcher.internal

import java.nio.ByteBuffer

import com.google.protobuf.ByteString
import nirvana.hall.matcher.HallMatcherConstants

/**
 * Created by songpeng on 16/3/21.
 */
object DataConverter {

  def int2Bytes(num: Int): Array[Byte] = {
    return ByteBuffer.allocate(4).putInt(num).array()
  }

  def long2Bytes(num: Long): Array[Byte] = {
    ByteBuffer.allocate(8).putLong(num).array();
  }
  /**
   * 将int转为byte数组
   * @param num
   * @param len [1-4]
   * @return
   */
  def int2Bytes(num: Int, len: Int): Array[Byte] = {
    val result: Array[Byte] = new Array[Byte](len)
    len match {
      case 1 =>
        result(0) = (num & 0xFF).toByte
      case 2 =>
        result(0) = (num >> 8 & 0xFF).toByte
        result(1) = (num & 0xFF).toByte
      case 3 =>
        result(0) = (num >> 16 & 0xFF).toByte
        result(1) = (num >> 8 & 0xFF).toByte
        result(2) = (num & 0xFF).toByte
      case 4 =>
        result(0) = (num >> 24 & 0xFF).toByte
        result(1) = (num >> 16 & 0xFF).toByte
        result(2) = (num >> 8 & 0xFF).toByte
        result(3) = (num & 0xFF).toByte
      case _ =>
        if (len > 4) {
          result(len - 4) = (num >> 24 & 0xFF).toByte
          result(len - 3) = (num >> 16 & 0xFF).toByte
          result(len - 2) = (num >> 8 & 0xFF).toByte
          result(len - 1) = (num & 0xFF).toByte
        }
    }
    return result
  }

  /**
   * 获取指位6.2转8.0,如:右手拇指是 1 << 0 ,左手小指是 1 << 9
   * 平指11-20
   * @param fgp
   */
  def fingerPos6to8(fgp: Int): Int = {
    return 1 << (fgp - 1)
  }

  /**
   * 获取指位8.0转6.2
   * @param fgp
   */
  def fingerPos8to6(fgp: Int): Int = {
    return (Math.log(fgp) / Math.log(2) + 1).toInt
  }

  /**
   * 掌纹指纹转换11->1,12->2
   * @param fgp
   * @return
   */
  def palmPos6to8(fgp: Int): Int = {
    if (fgp == 11) return 1
    if (fgp == 12) return 2
    return 1 << (fgp - 1)
  }

  def palmPos8to6(fgp: Int): Int = {
    if (fgp == 1) {
      return 11
    }
    else if (fgp == 2) {
      return 12
    }
    return (Math.log(fgp) / Math.log(2) + 1).toInt
  }

  def readGAFISIMAGESTRUCTDataLength(data: ByteString): Int = {
    if (data.size > HallMatcherConstants.HEADER_LENGTH) {
      val bytes: Array[Byte] = new Array[Byte](4)
      data.copyTo(bytes, 12, 0, 4)
      return ByteBuffer.wrap(bytes).getInt
    }
    return 0
  }
}

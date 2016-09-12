package nirvana.hall.api.internal

import java.nio.ByteBuffer

/**
 * 数据转换类
 */
object DataConverter {

  /**
   * sid long 转换为Array[Byte](6)
   * @param sid
   * @return
   */
  def longToSidArray(sid: Long): Array[Byte] ={
    ByteBuffer.allocate(8).putLong(sid).array().drop(2)
  }

}

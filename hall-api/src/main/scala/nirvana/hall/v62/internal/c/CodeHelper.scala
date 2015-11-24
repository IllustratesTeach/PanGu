package nirvana.hall.v62.internal.c

import java.nio.ByteBuffer

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
object CodeHelper {
  def convertAsInt(data:Array[Byte],offset:Int=0): Int ={
    ByteBuffer.wrap(data,offset,4).getInt
  }
}

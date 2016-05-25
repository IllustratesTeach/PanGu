package nirvana.hall.c

import java.nio.ByteBuffer

import nirvana.hall.c.annotations.Length

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
package object services {

  class SID_TYPE extends AncientData{
    var sidType:Long= _
  }
  implicit final val SID_SIZE = 6
  // if we call SaveSelRes, we need to know each row result.
  class GADB_RETVAL extends AncientData{
    @Length(SID_SIZE)
    var nSID:Array[Byte] = _
    var nRetVal:Byte = _  // -1 error, 1 success.
    var nFlag:Byte = _    // GADB_RETVALFLAG_XXX
  } //GADB_RETVAL;  // size is 8 bytes long.
  final val GADB_RETVALFLAG_HASOP = 0x1 //·
  final val GADB_RETVALFLAG_FAILED= 0x2
  def SIDArrayToLong(sid:Array[Byte],offset:Int=0): Long ={
    val buffer = ByteBuffer.allocate(8);
    buffer.mark()
    buffer.position(2)
    buffer.put(sid,offset,SID_SIZE)
    buffer.reset()
    buffer.getLong
  }
  def longToSidArray(sid:Long):Array[Byte]={
    ByteBuffer.allocate(8).putLong(sid).array().drop(2)
  }
}

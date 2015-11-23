package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
package object c {

  class SID_TYPE extends AncientData{
    var sidType:Long= _
  }
  final val SID_SIZE = 6
  // if we call SaveSelRes, we need to know each row result.
  class GADB_RETVAL extends AncientData{
    @Length(SID_SIZE)
    var nSID:Array[Byte] = _
    var nRetVal:Byte = _  // -1 error, 1 success.
    var nFlag:Byte = _    // GADB_RETVALFLAG_XXX
  } //GADB_RETVAL;  // size is 8 bytes long.
  final val GADB_RETVALFLAG_HASOP = 0x1 //·
  final val GADB_RETVALFLAG_FAILED= 0x2
}

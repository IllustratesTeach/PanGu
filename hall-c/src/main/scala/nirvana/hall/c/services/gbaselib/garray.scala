package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object garray {
  class GARRAY_STRUCT extends AncientData
  {
    var nItemSize:Int = _;
    var nItemCnt:Int = _;
    var nItemBufCnt:Int = _;
    var nIncStep:Int = _;
    var pItem_Ptr:Int = _;
    @Length(4)
    var bnRes_Pointer:Array[Byte]= _;
    @Length(8)
    var bnRes:Array[Byte] = _;
  } // size 32 bytes long
}

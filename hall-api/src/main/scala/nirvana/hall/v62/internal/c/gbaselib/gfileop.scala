package nirvana.hall.v62.internal.c.gbaselib

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gfileop {


  class GFILEHADLEOBJECT extends AncientData
  {
    var nFD:Int = _;	// file handle
  @Length(28)
  var bnRes:Array[Byte] = _ ;
  } // GFILEHANDLEOBJECT;	// size of this structure is 32 bytes
  type GFILEHANDLEOBJECT = GFILEHADLEOBJECT

  class GFILEOBJECT extends AncientData
  {
    @Length(256)
    var szFileName:String = _ ;	// file name, can not exceed 255 bytes
  var stHandle = new GFILEHANDLEOBJECT;	// file handle, 32 bytes
  @Length(96)
  var bnRes:Array[Byte] = _ ;	// 96 bytes reserved
  } // GFILEOBJECT;	// 384 bytes




}

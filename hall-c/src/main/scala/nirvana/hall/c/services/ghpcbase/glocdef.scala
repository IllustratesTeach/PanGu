package nirvana.hall.c.services.ghpcbase

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object glocdef {

  final val GA_TRUE = 1
  final val GA_FALSE = 0


  // structure for finger print transfer for GA.
  class GAFIS_FPX_STATUS extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    var nFPXState:Byte = _ ;			// TPLP_FPX_STATUS_
  var bFPXIsForeign:Byte = _ ;		// 0 or 1
  var nFPXPurpose:Byte = _ ;		// TPLP_FPX_PURPOSE_
  @Length(5)
  var bnFPXRes:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var nItemFlag:Byte = _ ;	// FPX_ITEMFLAG_XXX, indicates which item has valid values.
  @Length(7)
  var bnRes1:Array[Byte] = _ ;
    @Length(16)
    var szFPXForeignUnitCode:String = _ ;
    @Length(24)
    var bnResx:Array[Byte] = _ ;
    @Length(64)
    var bnResy:Array[Byte] = _ ;
  } // GAFIS_FPX_STATUS;	// 128 bytes long.

  // GAFIS_FPX_STATUS::nItemFlag
  final val FPX_ITEMFLAG_STATE = 0x1
  final val FPX_ITEMFLAG_ISFOREIGN = 0x2
  final val FPX_ITEMFLAG_PURPOSE = 0x4
  final val FPX_ITEMFLAG_FOREIGNUNITCODE = 0x8


  // if nItemType==GTPIO_ITEMTYPE_FACE, then nItemIndex can have the following values
  final val	GTPIO_ITEMINDEX_FACEFRONT			= 0x1
  final val	GTPIO_ITEMINDEX_FACENOSELEFT		= 0x2
  final val	GTPIO_ITEMINDEX_FACENOSERIGHT		= 0x3
  // 3 other items on June 1st, we want to add face and voice recognition into afis system.
  final val	GTPIO_ITEMINDEX_FACE1				= 0x4
  final val	GTPIO_ITEMINDEX_FACE2				= 0x5
  final val	GTPIO_ITEMINDEX_FACE3				= 0x6

  // if nItemType==GTPIO_ITEMTYPE_PALM then nItemIndex can be the following values
  // nItemIndex-1 is the index in name array g_stCN.stTPm
  final val	GTPIO_ITEMINDEX_PALM_RIGHT		= 0x1
  final val	GTPIO_ITEMINDEX_PALM_LEFT		= 0x2
  final val	GTPIO_ITEMINDEX_PALM_RFINGER	= 0x3
  final val	GTPIO_ITEMINDEX_PALM_LFINGER	= 0x4
  final val	GTPIO_ITEMINDEX_PALM_RTHUMBLOW	= 0x5
  final val	GTPIO_ITEMINDEX_PALM_RTHUMBUP	= 0x6
  final val	GTPIO_ITEMINDEX_PALM_LTHUMBLOW	= 0x7
  final val	GTPIO_ITEMINDEX_PALM_LTHUMBUP	= 0x8
  final val	GTPIO_ITEMINDEX_PALM_WHOLERIGHT	= 0x9	// 9		// not stored.
  final val	GTPIO_ITEMINDEX_PALM_WHOLELEFT	= 0xa	// 10		// not stored.

  final val GTPIO_ITEMINDEX_PALM_RFOURFINGER = 0x15	//21 //右手四联指
  final val GTPIO_ITEMINDEX_PALM_LFOURFINGER = 0x16 //22 //左手四联指
}



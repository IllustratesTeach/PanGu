package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services._
import nirvana.hall.c.services.gbaselib.gathrdop.GBASE_CRIT

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object gacodetb {

  // we using code table to store code's of items, such as sex, address
  // crime class, etc.
  class GAFIS_CODE_ENTRYSTRUCT extends AncientData
  {
    @Length(SID_SIZE)
    var nSID:String = _ ;		// sid of the entry(only used when update a code, read only)
  var nItemFlag:Byte = _ ;		// indicate which item is used, GAFISCDT_ITEM_XXXX
  @Length(1)
  var bnRes:Array[Byte] = _ ;		// reserved
  @Length(24)
  var szCode:String = _ ;			// code(key) can be digit or alpha
  @Length(24)
  var szInputCode:String = _ ;	// abbreviate form of szName. can be digit or alpha.
  @Length(136)
  var szName:Array[Byte] = _ ;	// name, about 68 hanzi, two lines long enough
  } // GAFIS_CODE_ENTRYSTRUCT;	// length is 192 bytes

  class GAFIS_CODETABLE extends AncientData
  {
    @Length(32)
    var szTableName:String = _ ;	// code table name.
  var nCodeCnt:Int = _ ;
    var nCodeBufCnt:Int = _ ;
    var pstCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCode_Data:Array[GAFIS_CODE_ENTRYSTRUCT] = _ // for pstCode pointer ,struct:GAFIS_CODE_ENTRYSTRUCT;	// pointer to code entries.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_CODETABLE;	// code table. 64 bytes long.

  final val GAFISCDT_ITEM_SID = 0x1
  final val GAFISCDT_ITEM_CODE = 0x2
  final val GAFISCDT_ITEM_INPUTCODE = 0x4
  final val GAFISCDT_ITEM_NAME = 0x8

  // multiadd option
  final val GAFISCDT_ADDOPT_DUPFAIL = 0
  final val GAFISCDT_ADDOPT_DUPDISCARD = 1
  final val GAFISCDT_ADDOPT_DUPUPDATE = 2

  /////////// we will cache code table 2006.04.03
  class GAFIS_CODETB_CACHE extends AncientData
  {
    var nDBID:Short = _;
    var nTID:Short = _;
    var nEntryCount:Int = _;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
    var pstEntry_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstEntry_Data:Array[GAFIS_CODE_ENTRYSTRUCT] = _ // for pstEntry pointer ,struct:GAFIS_CODE_ENTRYSTRUCT;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;	// reserved for pointer reason.
  } // GAFIS_CODETB_CACHE;	// size is 32 bytes long

  class GAFIS_CODETB_CACHEMGR extends AncientData
  {
    var stCrit = new GBASE_CRIT;	// critical section.	96 bytes long.
  var pstCache_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCache_Data:Array[GAFIS_CODETB_CACHE] = _ // for pstCache pointer ,struct:GAFIS_CODETB_CACHE;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nCacheCnt:Int = _;
    var nCacheBufCnt:Int = _;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_CODETB_CACHEMGR;	// 128 bytes long.

  ///////////////////////////////////////////////////////////////

}

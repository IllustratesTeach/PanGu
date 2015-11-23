package nirvana.hall.v62.internal.c.ganumia

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.c._
import nirvana.hall.v62.internal.c.ganumia.bmfop.GADB_BLOCKHEADSTRUCT
import nirvana.hall.v62.internal.c.ganumia.bmrfop.GADB_BMRFILEHEAD
import nirvana.hall.v62.internal.c.ganumia.gadbdef.GADB_SRID
import nirvana.hall.v62.internal.c.ganumia.gadbrec.GADB_ROWHEADSCHEMA
import nirvana.hall.v62.internal.c.ganumia.gaflobmg.GADB_LOBPT
import nirvana.hall.v62.internal.c.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object isafile {

  // value for GADB_ISAROWSCHEMA::nQueFlag, two states
  final val GADB_FQFLAG_READY = 0
  final val GADB_FQFLAG_WORKING = 1

  // the following value is the child table id of isa file
  // it's just a fake id that is different from any other ctid.
  final val GADB_ISACTID = 31273

  // does not like other table column schemes,
  // the structure of a ISA row is predefined
  class GADB_ISAROWSCHEMA extends AncientData
  {
    var stRh = new GADB_ROWHEADSCHEMA;	// row head, 16 bytes long, including FileID, and RID.
  @Length(16)
  var guidUUID:String = _ ;	// uuid structure(to here is 32 bytes)
  var tCreateTime = new AFISDateTime;	// create time, 8 bytes
  var tUpdateTime = new AFISDateTime;	// last update time, 8 bytes
  // to here is 48 bytes long
  @Length(SID_SIZE)
  var nPrevSID:String = _ ;	// used only for fifo que
  @Length(SID_SIZE)
  var nNextSID:String = _ ;	// used only for fifo que
  var nQueFlag:Byte = _ ;		// can be two status, READY, WORKING, GADB_FQFLAG_XXXX
  var nUserAccMode:Byte = _ ;	// user access mode, GADB_ACCMODE_XX
  var nGrpAccMode:Byte = _ ;	// group access mode, GADB_ACCMODE_XX
  var nOtherAccMode:Byte = _ ;	// other access mode, GADB_ACCMODE_XX
  // to here is 64 bytes long
  @Length(16)
  var szCreator:String = _ ;	// creator
  @Length(16)
  var szUpdator:String = _ ;	// updater, the one who modifies the record last time
  // may be inner user
  // to here is 96  bytes long. on 2003.nov.18, added 32 bytes.
  var stLobPt = new GADB_LOBPT;		// pointer to blob	// size is 16 bytes long
  var nOwnerID:Short = _ ;	// owner id.
  var nGroupID:Short = _ ;	// group id
  var nItemFlag:Byte = _ ;		// copy used dec. 18, 2006	GADB_ISAITEMFLAG_XXX
  var nItemFlag2:Byte = _ ;		// copy used dec. 18, 2006	GADB_ISAITEMFLAG2_XXX
  @Length(10)
  var bnRes2:Array[Byte] = _ ;		// reserved 10 bytes.
  } // GADB_ISAROWSCHEMA;	// size of this structure is 128 bytes

  // GADB_ISAROWSCHEMA::nItemFlag
  final val GADB_ISAITEMFLAG_GUID = 0x1
  final val GADB_ISAITEMFLAG_CRTTIME = 0x2
  final val GADB_ISAITEMFLAG_UPDTIME = 0x4
  final val GADB_ISAITEMFLAG_PREVSID = 0x8
  final val GADB_ISAITEMFLAG_NEXTSID = 0x10
  final val GADB_ISAITEMFLAG_QUEFLAG = 0x20
  final val GADB_ISAITEMFLAG_USRACCMODE = 0x40
  final val GADB_ISAITEMFLAG_GRPACCMODE = 0x80

  // GADB_ISAROWSCHEMA::nItemFlag2
  final val GADB_ISAITEMFLAG2_OTHACCMODE = 0x1
  final val GADB_ISAITEMFLAG2_CREATOR = 0x2
  final val GADB_ISAITEMFLAG2_UPDATOR = 0x4
  final val GADB_ISAITEMFLAG2_LOBPT = 0x8
  final val GADB_ISAITEMFLAG2_OWNERID = 0x10
  final val GADB_ISAITEMFLAG2_GROUPID = 0x20


  // simple isa row schema.
  class GADB_SIMPISAROW extends AncientData
  {
    var bIsUsed:Byte = _ ;	// whether the record is used
  @Length(1)
  var bnRes:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;	// sequence id
  @Length(16)
  var guidUUID:String = _ ;	// uuid structure(to here is 32 bytes)
  var tCreateTime = new AFISDateTime;	// create time, 8 bytes
  var tUpdateTime = new AFISDateTime;	// last update time, 8 bytes
  @Length(8)
  var bnRes2:Array[Byte] = _ ;
  } // GADB_SIMPISAROW;	// 48 bytes long.

  // stRh.bIsUsed is three value items:
  // 0 not used, 1 used, 2 reserved
  // for a reserved slot it has temporary values, so when getting data
  // by a key we check this flag. if it is ROWUSEDFLAG_RESERVED
  // then do not get value from it.
  // value for GADB_ROWHEADSCHEMA::bIsUsed.
  final val ROWUSEDFLAG_NOTUSED = 0x0
  final val ROWUSEDFLAG_USED = 0x1
  final val ROWUSEDFLAG_RESERVED = 0x2

  // the following structure are used for ISA data file
  // it can store some informations
  class GADB_ISAPROPSTRUCT extends AncientData
  {
    @Length(64)
    var bnRes:Array[Byte] = _ ;
  } // GADB_ISAPROPSTRUCT;	// size is 64 bytes

  class GADB_ISAFILEHEADSTRUCT extends AncientData
  {

    var stBMRFh = new GADB_BMRFILEHEAD;	// size is 192 bytes
  var stIsaProp = new GADB_ISAPROPSTRUCT;	// properties, 64 bytes
  @Length(768)
  var bnRes:Array[Byte] = _ ;	// reserved
  } // GADB_ISAFILEHEADSTRUCT;	// size is 1024 bytes

  class GADB_ISAPAGESTRUCT extends AncientData
  {
    var stHead = new GADB_BLOCKHEADSTRUCT;
    @Length(1)
    var stRow:Array[GADB_ISAROWSCHEMA] = _;
  } // GADB_ISAPAGESTRUCT;	// size of this structure depends on the size of block

  class GADB_ISAGETBLOCKSTRUCT extends AncientData
  {
    var pBlock_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBlock_Data:Array[GADB_ISAPAGESTRUCT] = _ // for pBlock pointer ,struct:GADB_ISAPAGESTRUCT;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var bIsCached:Byte = _ ;	// is cached
  @Length(3)
  var bnRes:Array[Byte] = _ ;
    var nBkSID:Int = _;		// block sid.
  } // GADB_ISAGETBLOCKSTRUCT;	// size is 16 bytes

  // the following structure is used for non-congiguous SID tables only
  class GADB_ISAPAGEINFO extends AncientData
  {
    var nMinSID = new SID_TYPE;	// min SID stored in block
  var nMaxSID = new SID_TYPE;	// max SID stored in block
  var strid = new GADB_SRID;
    var nUsedEntCnt:Int = _;	// how many entries used
  var nMaxEntCnt:Int = _;		// up to this # of entries can be hold
  } // GADB_ISAPAGEINFO;

  // name of the isa table, it must be different from any user defined table
  // names. so the following name is reserved
  final val GADB_ISATABLENAME	= "$$ISATBL"

  // strictly speaking isa table is not a trivial table. It's schema is predefined and
  // can directly mapping to C/C++ structure.
  // isa table's operations is different from other child table's
  // it has a unique property : it's used to locate the deleted records position
  // in a block. so we cache it's block. Child table does not have any caching
  // for isa table we only use blk buf. in order to cache file block, we need
  // SID FILEID AND BLOCK ID. in GADB_MMTREEFORID structure where ID is SID and
  // pItem is a pointer to each isatable block
  class GADB_ISAFILEINMEM extends AncientData
  {
    /*
    var stTable = new GADB_CHILDTABLEOBJECT;		// isa table, 2KB.
  var nMemLimit:Int = _;		//  update to this memory can be used, in byte, that it's max is 4GB
  var nMaxRowPerPage:Int = _;	// max row per block/page.
  var bCritInited:Int = _;
    var bSIDIsNotContiguous:Int = _;	// sid is not contiguous
  @Length(48)
  var bnRes:Array[Byte] = _ ;
    var stBlkBuf = new GADB_MMTREEFORID;		// block buffer., 128 +64 bytes
  var pstHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstHead_Data:Array[GADB_ISAFILEHEADSTRUCT] = _ // for pstHead pointer ,struct:GADB_ISAFILEHEADSTRUCT;		// isa file head
  var tagGADB_TABLEOBJECT = new struct	*p  `    32esxtTable;	// is belong to this table.
  @Length(4*2)
  var bnRes_Pointer:Array[Byte] = _ ;
    var bHeadCanBeFreed:Byte = _ ;
    @Length(143)
    var bnRes2:Array[Byte] = _ ;
    var stCrit = new GAFIS_CRITSECT;
    var bnRes0:Byte = _ [96-sizeof(GAFIS_CRITSECT)];			// reserved
    */
    @Length(2560)
    var bnRes:Array[Byte] = _
  } // GADB_ISAFILEINMEM;	// size is 2.5KB=2048 + 512 = 2560 bytes

}

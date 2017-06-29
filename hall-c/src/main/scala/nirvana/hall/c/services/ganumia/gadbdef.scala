package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-09
 */
object gadbdef {

  final val ERR_CODE_GENERAL = -1
  final val ERR_CODE_FATAL = -9999

  final val GNUMINA_MAXPATHFILELEN = 256
  final val GNUMINA_MAXCOLNAMELEN = 32

  class GADB_SID extends AncientData {
    @Length(SID_SIZE)
    var nSID:String = _ ;
  } // GADB_SID;	// structure is 6 bytes long.

  // OID : Offset ID in a block
  class GADB_RID extends AncientData
  {
    var nFileID:Short = _ ;	// file id
  var nOID:Short = _ ;	// Offset ID
  var nBKID:Int = _ ;	// block id
  } // GADB_RID;	// size is 8 bytes

  class GADB_SRID extends AncientData
  {
    var nFileID:Short = _;	// file id
  var nOID:Short = _;	// offset in one block
  var nBKID:Int = _;	// block id. valid only in given file, block offset in one file only
  var nSID:Long = _;	// row sid, 8 bytes long
  var nBkSID:Int = _;	// block sid, it's nSID/nMaxRowPerPage, actually it's
  // (nSID+1+nMaxRowPerPage-1)/nMaxRowPerPage-1. after reduce it's nSID/nMaxRowPerPage
  // we using nBkSID we can calc it from nSID. global block id
  var nRes:Int = _;
  } // GADB_SRID;	// 16+8 bytes long

  // the following structure is used by other system
  class GADB_USERDATASTRUCT extends AncientData
  {
    var pUserData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pUserData_Data:Array[Byte] = _ // for pUserData pointer ,struct:void;	// this part of data is not administered by numina database
  // so the caller must have a good responsibility
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nUserDataLength:Int = _ ;	// length of user data
  var bUserDataCanBeFreed:Byte = _ ;
    @Length(19)
    var bnRes1:Array[Byte] = _ ;
    @Length(96)
    var bnRes2:Array[Byte] = _ ;
  } // GADB_USERDATASTRUCT;	// user data structure, size is 128 bytes

  class GADB_GETSTRKEYSTRUCT extends AncientData
  {
    @Length(48)
    var szKeyWild:String = _ ;	// key wild
  var nMaxToGot:Int = _;		// maximum number to got
  var nKeyResWidth:Int = _;	// whether or not the caller provide buf to store result, it still expect
    // the callee to store key in the buf according this width.
  } // GADB_GETSTRKEYSTRUCT;

  /*
  class GADB_SIDARRAY extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(2)
  var bnRes:Array[Byte] = _ ;	// reserved 2 bytes
  var bSIDCanBeFree:Byte = _ ;
    @Length(1)
    var bnRes0:Array[Byte] = _ ;
    UCHAR	(*pnSID)[SID_SIZE];	//
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nSIDCount:Int = _ ;	// # of effective sid hold
  var nSIDBufCount:Int = _ ;	// pnSID can hold at most this # of SID
  } // GADB_SIDARRAY;	// size of this structure is 16 bytes long.
  */

  class GADB_SIDRANGE extends AncientData
  {
    var nStartSID:Long = _ ;
    var nEndSID:Long = _ ;
  } // GADB_SIDRANGE;	// sid range;

  class GADB_KEYARRAY extends AncientData
  {
    var cbSize:Int = 32 ;	// size of this structure
  @Length(2)
  var bnRes:Array[Byte] = _ ;	// reserved 2 bytes long
  var bKeyCanBeFree:Byte = _ ;	//
  @Length(1)
  var bnRes0:Array[Byte] = _ ;
//    var pKey_Ptr:Int = _ //using 4 byte as pointer
//  @IgnoreTransfer
  @Length(32)
  var pKey_Data:Array[Byte] = _ // for pKey pointer ,struct:UCHAR;			// pointer to key
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nKeyCount:Int = _ ;	// # of keys hold
  var nKeyBufCount:Int = _ ;	// max # of keys can be hold
  var nKeySize:Short = _ ;		// size of key
  @Length(14)
  var bnRes2:Array[Byte] = _ ;			// reserved, 14 bytes long
  } // GADB_KEYARRAY;	// key array struct, size is 32 bytes long

  class GADB_ROWACCSTRUCT extends AncientData
  {
    var nUserAccMode:Byte = _ ;	// user access mode, GADB_ACCMODE_XX
  var nGrpAccMode:Byte = _ ;	// group access mode, GADB_ACCMODE_XX
  var nOtherAccMode:Byte = _ ;	// other access mode, GADB_ACCMODE_XX
  @Length(1)
  var bnRes:Array[Byte] = _ ;
    var nOwnerID:Short = _ ;
    var nGroupID:Short = _ ;
    var nItemMask:Byte = _ ;		// GADB_ROWACC_ITEMMASK_xxx
  var nOpMask:Byte = _ ;		// GADB_ROWACC_OPMASK_XXX
  @Length(6)
  var bnRes2:Array[Byte] = _ ;
  } // GADB_ROWACCSTRUCT;	// 16 bytes long.

  // GADB_ROWACCSTRUCT::nItemMask
  final val GADB_ROWACC_ITEMMASK_USERACCMODE = 0x1
  final val GADB_ROWACC_ITEMMASK_GRPACCMODE = 0x2
  final val GADB_ROWACC_ITEMMASK_OTHERACCMODE = 0x4
  final val GADB_ROWACC_ITEMMASK_USERID = 0x8
  final val GADB_ROWACC_ITEMMASK_GROUPID = 0x10

  // GADB_ROWACCSTRUCT::nOpMask
  final val GADB_ROWACC_OPMASK_SET = 0x1
  final val GADB_ROWACC_OPMASK_REMOVE = 0x1
  final val GADB_ROWACC_OPMASK_ADD = 0x1


  class GADB_ROWDFINFO extends AncientData
  {
    var nRowDFID:Int = _;		// row data file id.
  var nRowDFDevID:Byte = _ ;	// row data file on this device
  var nLOBDevID:Byte = _ ;
    @Length(2)
    var bnRes:Array[Byte] = _ ;		// reserved
  var nSID :Long  = _;		// sid
  var nLobSize:Int = _;	// estimated lob size
  var nLOBDFID:Int = _;	// reserved
  } // GADB_ROWDFINFO;	// row data file info, size is 24 bytes long.

  class GADB_LOCKCOUNT extends AncientData
  {
    var nThreadID:Int = _;	// thread id.
  var nLockCnt:Int = _;	// # of times lock by this thread.
  } // GADB_LOCKCOUNT;	// 8 bytes long.

  /*
  class GADB_OBJECTLOCK extends AncientData
  {
    var nLockCnt:Int = _;
    var nLockBufCnt:Int = _;
    var pstLock_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLock_Data:Array[GADB_LOCKCOUNT] = _ // for pstLock pointer ,struct:GADB_LOCKCOUNT;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
    var stCrit = new GBASE_CRIT;	// critical section. lock this object only. 96 bytes long.
  } // GADB_OBJECTLOCK;	// 128 bytes long
  */

  /*
  // to change GADB_SHAREOBJECT::nStatus, we must lock at higher level.
  class GADB_SHAREOBJECT extends AncientData
  {
    var nStatus:Int = _;		// status. SHAREOBJ_STATUS_xx
  var nTimeLeft:Int = _;		// used only when nStatus==SHAREOBJ_STATUS_TOMOD, in that case
  // nTimeLeft is the time that the modification need to be finished
  var nRefCount:Int = _;		// refercence count.
  var nTempRefCount:Int = _;	// temp reference count.
  // to here 16 bytes long.
  var hEvent = new GAFIS_EVENT_HANDLE;		// when refcount is decrease to zero, set this event.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var bWillBeDel:Byte = _ ;		// will be deleted. we do not mix nStatus with bWillBeDel because
  // we bWillBeDel is unchangeable and nStatus is changeable.
  @Length(3)
  var bnRes:Array[Byte] = _ ;		// reserved.
  var nOwnerThreadID:Int = _;	//
  var stCrit = new GBASE_CRIT;		// critical section. 96 bytes long.
  var stLock = new GADB_OBJECTLOCK;	// object lock(actually is thread lock), 128 bytes long
  } // GADB_SHAREOBJECT;	// 256 bytes long.
  */

  // GADB_SHAREOBJECT::nStatus
  final val SHAREOBJ_STATUS_READY = 0
  final val SHAREOBJ_STATUS_TOMOD = 1	// to be modified

  final val SHAREOBJ_NEXTACT_WAITREFCNT = 0
  final val SHAREOBJ_NEXTACT_WAITTODEL = 1

  class GADB_DELOBJACT extends Enumeration
  {
    val DELOBJACT_NOTFOUND = Value
    val DELOBJACT_TODEL = Value
    val DELOBJACT_WAITCANBEDEL= Value
    val DELOBJACT_WAITDELFIN = Value
  } //GADB_DELOBJACT;

  // some times we need to find an object by id, uuid or name, following is
  // the type of id.
  class GADB_IDNAMETYPE extends Enumeration
  {
    val IDTYPE_ID = Value
    val IDTYPE_UUID = Value
    val IDTYPE_NAME = Value
  } //GADB_IDNAMETYPE;

  // cache parameters.
  class GADB_CACHEPARAM extends AncientData
  {
    var cbSize:Byte = _ ;	// size is 16 bytes long.
  var nVersion:Byte = _ ;
    var nCachePriority:Byte = _ ;	// 0 is lowest, from [0, 127].
  var nCacheMethod:Byte = _ ;	// GADB_CACHEMETHOD_xxx
  @Length(2)
  var bnRes:Array[Byte] = _ ;		// 2 bytes reserved.
  var nOption:Byte = _ ;	// option.GADB_CACHEOPT_xxx
  var nAgeTimeInSec:Byte = _ ;	// the smaller the swap method.[1 100]. 0 will using default.
  // to here is 8 bytes long.
  // if nCacheMethod==GADB_CACHEMETHOD_BYPERCENT, then
  // the following 2 items' nLow[0] and nHigh[0] is used.
  // if nCacheMethod==GADB_CACHEMETHOD_BYABS, then
  // nLow and nHigh are used, and the unit is in KB. so
  // if Char4To_uint4(nLow)==3 then it means 3KB.
  var nLow:Int = _ ;
    var nHigh:Int = _ ;
  } // GADB_CACHEPARAM;	// 16 bytes long.

  //GADB_CACHEPARAM::nCacheMethod.
  final val GADB_CACHEMETHOD_DEFAULT = 0	// default parameters
  final val GADB_CACHEMETHOD_BYPERCENT = 0x1
  final val GADB_CACHEMETHOD_BYABS = 0x2	// by absolute value
  final val GADB_CACHEMETHOD_CACHEALL = 0x3	// cache all data.
  final val GADB_CACHEMETHOD_NOCACHE = 0x4	// cache nothing.
  final val GADB_CACHEMETHOD_MINIMAL = 0x5	// cache as little as possible.

  //GADB_CACHEPARAM::nOption
  final val GADB_CACHEOPT_PRELOAD = 0x1	// whether preload or not.


  // adjust buffer option
  final val GADB_ADJBUF_LOB = 0x1
  final val GADB_ADJBUF_CT = 0x2
  final val GADB_ADJBUF_IDX = 0x4
  final val GADB_ADJBUF_ISA = 0x8

  // storage type.
  final val GADB_STGTYPE_NORMAL = 0	// normal object.
  final val GADB_STGTYPE_STUB = 1	// only a stub.
  final val GADB_STGTYPE_MEMORY = 3	// memory object.


  // 2007.01.11
  class GADB_DTID extends AncientData
  {
    var nDBID:Short = _;
    var nTID:Short = _;
  } // GADB_DTID;

  class GADB_CHDTID extends AncientData
  {
    var nDBID:Short = _ ;
    var nTID:Short = _ ;
  } // GADB_CHDTID;


}

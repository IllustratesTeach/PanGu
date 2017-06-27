package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._
import nirvana.hall.c.services.gbaselib.gbasedef.{GBASE_APPVERSION, GATIMERANGE}
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.glocdef.GAFIS_IMGIDXSTRING

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
object galoclog {

  class GAFIS_DBLOGRETRSTRUCT extends AncientData
  {
    @Length(48)
    var szKeyWild:String = _ ;	// wild key
  @Length(32)
  var szUserWild:String = _ ;	// wild user name
  var tStartTime = new AFISDateTime;
    var tEndTime = new AFISDateTime;		// included
  var nMaxItem:Int = _ ;	// maximum items
  @Length(SID_SIZE)
  var nStartSID:String = _ ;	// start id, 0 from start, -1, from end start, other real start
  @Length(SID_SIZE)
  var nEndSID:String = _ ;		// end sid
  @Length(4)
  var bnRes_SID:Array[Byte] = _ ;
    var bSearchToEnd:Byte = _ ;	// search to end or search to start, default is from end to start
  var nOperation:Byte = _ ;		// can be combination of DBLOGOP_XXX
  var nItemOption:Byte = _ ;	// which item is used, DBLOGRETR_ITEM_xxx
  var nItemOptionEx:Byte = _ ;	//
  @Length(8)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GAFIS_DBLOGRETRSTRUCT;	// size is 128 bytes

  final val DBLOGRETR_ITEM_KEYWILD = 0x1
  final val DBLOGRETR_ITEM_USERWILD = 0x2
  final val DBLOGRETR_ITEM_STARTTIME = 0x4
  final val DBLOGRETR_ITEM_ENDTIME = 0x8
  final val DBLOGRETR_ITEM_MAXITEM = 0x10
  final val DBLOGRETR_ITEM_STARTSID = 0x20
  final val DBLOGRETR_ITEM_ENDSID = 0x40
  final val DBLOGRETR_ITEM_OPERATION = 0x80

  final val DBLOGRETR_ITEMEX_DIRECTION = 0x1	// control whether use bSearchToEnd flag

  // db modify log
  class GAFIS_DBLOGSTRUCT extends AncientData
  {
    @Length(32)
    var szKey:String = _ ;		// key words
  @Length(16)
  var szUserName:String = _ ;	// user name
  var tLogTime = new AFISDateTime;		// log time
  var nOp:Byte = _ ;			// operation DBLOGOP_ADD, UPDATE, DEL, QUEEDIT, QUECHECK, DBLOGOP_XXX
  var nTableType:Byte = _ ;		// table type.
  var nTableID:Short = _ ;
    var nDBID:Short = _ ;			// database id.
  @Length(2)
  var bnRes2:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;		// SID, 6 bytes, read only
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;				// pointer to data.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;	// reserved
  } // GAFIS_DBLOGSTRUCT;	// size is 64+16 bytes

  final val DBLOGOP_ADD = 0x1
  final val DBLOGOP_UPDATE = 0x2
  final val DBLOGOP_DEL = 0x4
  final val DBLOGOP_QUEEDIT = 0x8
  final val DBLOGOP_QUECHECK = 0x10
  final val DBLOGOP_TEXTINPUT = 0x20

  ////////////////////////////////////////////verifylog structures
  class GAFIS_VERIFYLOGRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  @Length(16)
  var szUserNameWild:String = _ ;	// user name wild, to here is 64 bytes
  var nQueryType:Byte = _ ;
    @Length(7)
    var bnRes:Array[Byte] = _ ;
    var stSubmitTimeRange = new GATIMERANGE;			// 16 bytes
  var stFinishTimeRange = new GATIMERANGE;			// 16 bytes
  var stVerifyTimeRange = new GATIMERANGE;			// 16 bytes
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GQUERYRETR_ITEMFLAG_XXX
  @Length(3)
  var bnRes_1:Array[Byte] = _ ;		// bytes reserved
  } // GAFIS_VERIFYLOGRETRSTRUCT;	// size is 128 bytes

  // also call breakcasestruct.
  class GAFIS_VERIFYLOGSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;				// size of this structure.
  @Length(4)
  var bnRes0:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:Array[Byte] = _ ;			//
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;	// to here is 16 bytes long
  // to here is 16 bytes long.
  @Length(24)
  var szBreakID:String = _ ;
    @Length(16)
    var szQueryTaskID:String = _ ;
    @Length(32)
    var szSrcKey:String = _ ;
    @Length(32)
    var szDestKey:String = _ ;
    // to here is 96+24=120 bytes long.
    var nScore:Short = _ ;
    var nFirstRankScore:Short = _ ;
    var nRank:Short = _ ;
    var nFg:Byte = _ ;	// finger index.
  var nHitPoss:Byte = _ ;
    // to here is 128 bytes long.
    var bIsRmtSearched:Byte = _ ;
    var bIsCrimeCaptured:Byte = _ ;
    var nFgType:Byte = _ ;
    var nTotalMatchedCnt:Byte = _ ;
    var nQueryType:Byte = _ ;
    @Length(3)
    var bnRes2:Array[Byte] = _ ;
    var nSrcDBID:Short = _ ;
    var nDestDBID:Short = _ ;
    @Length(4)
    var bnRes3:Array[Byte] = _ ;

    // to here is 128+16 bytes long.
    @Length(32)
    var szSrcPersonCaseID:String = _ ;
    @Length(32)
    var szDestPersonCaseID:String = _ ;
    @Length(16)
    var szCaseClassCode1:String = _ ;
    @Length(16)
    var szCaseClassCode2:String = _ ;
    @Length(16)
    var szCaseClassCode3:String = _ ;
    @Length(16)
    var szSearchingUnitCode:String = _ ;
    // to here is 256+16 bytes long.
    @Length(16)
    var szSubmitUserName:String = _ ;
    @Length(16)
    var szSubmitUserUnitCode:String = _ ;
    var tSubmitDateTime = new AFISDateTime;

    @Length(16)
    var szBreakUserName:String = _ ;
    @Length(16)
    var szBreakUserUnitCode:String = _ ;
    var tBreakDateTime = new AFISDateTime;

    @Length(16)
    var szReCheckUserName:String = _ ;
    @Length(16)
    var szReCheckerUnitCode:String = _ ;
    var tReCheckDateTime = new AFISDateTime;

    // to here is 256+16+40*3 bytes long.

    // following is 16 pointers. 16*8=128 bytes long.
    var pbnData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnData_Data:Array[Byte] = _ // for pbnData pointer ,struct:char;	// each item index is VFLOG_IDX_xxxx
  @Length(16*4)
  var bnRes_Pt:Array[Byte] = _ ;
    //UCHAR     nDataLen[16][4];
    @Length(16 * 4)
    var nDataLen:Array[Byte]= _ ;
    // above length is 16*4=64 bytes long.
    // to here is 512+8+64 bytes long.
    @Length(16)
    var bDataCanBeFree:String = _ ;
    @Length(40+128)
    var bnRes9:Array[Byte] = _ ;	// reserved.
  } // GAFIS_VERIFYLOGSTRUCT;	// size of this structure is 768 bytes

  final val VFLOG_IDX_SRCMNT = 0
  final val VFLOG_IDX_SRCIMG = 1
  final val VFLOG_IDX_SRCINFO = 2
  final val VFLOG_IDX_DESTMNT = 3
  final val VFLOG_IDX_DESTIMG = 4
  final val VFLOG_IDX_DESTINFO = 5
  final val VFLOG_IDX_COMMENT1 = 6
  final val VFLOG_IDX_COMMENT2 = 7
  final val VFLOG_IDX_RESERVED1 = 8
  final val VFLOG_IDX_RESERVED2 = 9
  final val VFLOG_IDX_RESERVED3 = 10

  type GAFIS_SYSMODLOGSTRUCT = GAFIS_DBLOGSTRUCT
  type GAFIS_QRYMODLOGSTRUCT = GAFIS_DBLOGSTRUCT


  class GAFIS_USERAUTHLOG extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes0:Array[Byte] = _ ;
    @Length(16)
    var szUserName:String = _ ;
    @Length(16)
    var szModuleID:String = _ ;
    var tLoginTime = new AFISDateTime;
    var tLogoutTime = new AFISDateTime;
    var bIsValidUser:Byte = _ ;
    @Length(3)
    var bnRes:Array[Byte] = _ ;
    var nLoginID:Int = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(16)
    var nIP:String = _ ;
    @Length(32)
    var szComputerName:String = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_USERAUTHLOG;	// size is 128 bytes long.

  class GAFIS_DBRUNLOG extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes0:Array[Byte] = _ ;	// align at 8 bytes boundary.
  @Length(16)
  var szShutdownUser:String = _ ;	//
  @Length(16)
  var szVersion:String = _ ;		// version of application
  var tStartupTime = new AFISDateTime;
    var tShutdownTime = new AFISDateTime;
    @Length(16)
    var nIP:String = _ ;			// shutdown user ip
  @Length(32)
  var szComputerName:String = _ ;	// shutdown user compute name
  @Length(32)
  var szComment:String = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
  } // GAFIS_DBRUNLOG;	// db run log. 128+32=160 bytes long

  class GAFIS_QUALCHECKRECORD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  var nDBID:Short = _ ;	// where to store the data.
  var nStatus:Byte = _ ;	// status.	QUALCHK_STATUS_XXX
  var nItemFlag:Byte = _ ;	// used for update, to indicate which item used. QUALCHK_ITEMFLAG_xxx
  // to here is 8 bytes long.
  var tAddTime = new AFISDateTime;
    var tCheckTime = new AFISDateTime;
    @Length(SID_SIZE)
    var nSID:String = _ ;	// current record sid.
  @Length(8-SID_SIZE)
  var bnRes:Array[Byte] = _ ;
    // to here is 32 bytes long.
    @Length(32)
    var szKey:String = _ ;	// card key.
  @Length(16)
  var szChecker:String = _ ;
    @Length(16)
    var szInputUnitCode:String = _ ;
    //char			szComment[32];
    var nItemFlag3:Byte = _ ;		// QUALCHK_ITEMFLAG3_XXX
  @Length(23)
  var bnRes0:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nQueSID:String = _ ;	// que record sid.
  @Length(8-SID_SIZE)
  var bnREs2:Array[Byte] = _ ;
    /// to here is 128 bytes long.
    var tDownloadTime = new AFISDateTime;	// down load time of client.
  @Length(16)
  var szUserName:String = _ ;	// who added this records.
  var nTID:Short = _ ;		// database table id.
  var nItemFlag2:Byte = _ ;		// QUALCHK_ITEMFLAG2_xxx
  var nOption:Byte = _ ;		// QUALCHK_OPT_XXX
  var nDestTenDBID:Short = _ ;
    var nDestLatDBID:Short = _ ;
    var stImgIdx = new GAFIS_IMGIDXSTRING;	// 12 bytes long.
  var nQryDBID:Short = _ ;	//  where to store the query.
  var nQryTID:Short = _ ;		//
  @Length(SID_SIZE)
  var nTQrySID:String = _ ;	// search which tplib.
  var nDestTenTID:Short = _ ;
    @Length(SID_SIZE)
    var nLQrySID:String = _ ;	// search which lplib.
  var nDestLatTID:Short = _ ;
  } // GAFIS_QUALCHECKRECORD;	// 128 + 64bytes long.

  // GAFIS_QUALCHECK::nStatus
  final val QUALCHK_STATUS_NEEDCHECK = 0x1
  final val QUALCHK_STATUS_CHKPASSED = 0x2	// quality is ok.
  final val QUALCHK_STATUS_CHKRESCAN = 0x3	// need re input
  final val QUALCHK_STATUS_PERSONUNAVAIL = 0x4	// the person can not be reinput, may have been released
  final val QUALCHK_STATUS_DOWNLOADED = 0x5
  final val QUALCHK_STATUS_FORCEDTHROUGH = 0x6	// 对于质量检查没有通过而要求派出所重采的指纹，如果派出所长期不处理，
  // 上级可以强制通过，使得对应的等待质检的查询的状态能够变成等待比对
  // 否则这些对应的查询就会一直处于等待质检状态（南京用户提的这个需求）

  final val QUALCHK_STATUS_FINISHED = 0x0	// same as QUALCHK_STATUS_CHKPASSED, so the record can be deleted


  // GAFIS_QUALCHECK::nItemFlag
  final val QUALCHK_ITEMFLAG_STATUS = 0x1
  final val QUALCHK_ITEMFLAG_CHKTIME = 0x2
  final val QUALCHK_ITEMFLAG_ADDTIME = 0x4
  final val QUALCHK_ITEMFLAG_CHECKER = 0x8
  //#define	QUALCHK_ITEMFLAG_COMMENT	0x10
  final val QUALCHK_ITEMFLAG_UNITCODE = 0x20
  final val QUALCHK_ITEMFLAG_DBID = 0x40
  final val QUALCHK_ITEMFLAG_QUESID = 0x80

  // GAFIS_QUALCHECK::nItemFlag2
  final val QUALCHK_ITEMFLAG2_KEY = 0x1
  final val QUALCHK_ITEMFLAG2_DOWNTIME = 0x2
  final val QUALCHK_ITEMFLAG2_TID = 0x4
  final val QUALCHK_ITEMFLAG2_USERNAME = 0x8
  final val QUALCHK_ITEMFLAG2_IMGIDX = 0x10
  final val QUALCHK_ITEMFLAG2_TQRYSID = 0x20
  final val QUALCHK_ITEMFLAG2_LQRYSID = 0x40
  final val QUALCHK_ITEMFLAG2_OPTION = 0x80	// option flag.

  // GAFIS_QUALCHECK::nItemFlag3
  final val QUALCHK_ITEMFLAG3_DESTTENDBID = 0x1
  final val QUALCHK_ITEMFLAG3_DESTTENTID = 0x2
  final val QUALCHK_ITEMFLAG3_DESTLATDBID = 0x4
  final val QUALCHK_ITEMFLAG3_DESTLATTID = 0x8
  final val QUALCHK_ITEMFLAG3_QRYDBID = 0x10
  final val QUALCHK_ITEMFLAG3_QRYTID = 0x20


  // GAFIS_QUALCHECKRECORD::nOption
  final val QUALCHK_OPT_TQRYSENT = 0x1
  final val QUALCHK_OPT_LQRYSENT = 0x2
  final val QUALCHK_OPT_QUESIDUSED = 0x4	// que sid has valid value


  // if record need rescan, we need log this key.
  class GAFIS_QUALCHECKWORKLOG extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  var nDBID:Short = _ ;	// where to store the data.
  var nTID:Short = _ ;		// data table id.
  // to here is 8 bytes long.
  var nStatus:Byte = _ ;	// only passed or need rescan.	see value QUALCHKWORKLOG_STATUS_XXXX
  @Length(7)
  var bnRes3:Array[Byte] = _ ;
    var tCheckTime = new AFISDateTime;
    @Length(SID_SIZE)
    var nSID:String = _ ;	// current record sid.
  @Length(8-SID_SIZE)
  var bnRes:Array[Byte] = _ ;
    // to here is 32 bytes long.
    @Length(32)
    var szKey:String = _ ;	// card key.
  @Length(16)
  var szChecker:String = _ ;
    @Length(16)
    var szUserName:String = _ ;	// who added this records.
  // to here is 96 bytes long.
  @Length(16)
  var szInputUnitCode:String = _ ;
    var stImgIdx = new GAFIS_IMGIDXSTRING;

    @Length(16 - 12)
    var bnRes4:Array[Byte] = _
  } // GAFIS_QUALCHECKWORKLOG;	// quality check log. 128 bytes long.

  // GAFIS_QUALCHECKWORKLOG::bNeedRescan
  final val QUALCHKWORKLOG_STATUS_PASSED = 0
  final val QUALCHKWORKLOG_STATUS_NEEDRESCAN = 1
  final val QUALCHKWORKLOG_STATUS_PERSONUNAVAIL = 2
  final val QUALCHKWORKLOG_STATUS_FORCEDTHROUGH = 3

  class GAFIS_QRYSUBMITLOG extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKey:String = _ ;	// only first key.
  @Length(16)
  var szSubmitUserName:String = _ ;
    @Length(16)
    var szSubmitUserUnitCode:String = _ ;
    var tSubmitDateTime = new AFISDateTime;
    // to here is 80 bytes long.
    var nCandCount:Int = _ ;
    var nQueryType:Byte = _ ;
    var bIsFifoQueQry:Byte = _ ;
    var nFlag:Byte = _ ;
    var nRmtFlag:Byte = _ ;
    var nPriority:Byte = _ ;
    var nStatus:Byte = _ ;
    var nMICCount:Short = _ ;
    @Length(4)
    var bnRes2:Array[Byte] = _ ;
    // to here is 96 bytes long.
    @Length(16)
    var bnComputerIP:Array[Byte] = _ ;
    @Length(32)
    var szComputerName:String = _ ;
    @Length(32)
    var szQUID:String = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(72)
    var bnRes5:Array[Byte] = _ ;
  } // GAFIS_QRYSUBMITLOG;	// 256 bytes long.

  class GAFIS_QRYSEARCHLOG extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(32)
  var szKey:String = _ ;
    @Length(16)
    var szSubmitUser:String = _ ;
    @Length(32)
    var szFirstCandKey:String = _ ;
    var nFirstCandScore:Int = _ ;
    var nRecSearchedCnt:Int = _ ;
    var nDBRecCount:Int = _ ;
    // to here is 96 bytes long.
    var nTimeUsed:Int = _ ;
    var nFinalCandCnt:Int = _ ;
    /*
      var nDBID:Short = _ ;
      var nTID:Short = _ ;
      var nDestDBID:Short = _ ;
      var nDestTID:Short = _ ;
    */
    @Length(8)
    var bnResx:Array[Byte] = _ ;
    // to here is 96+16 bytes long.
    var tSubmitDateTime = new AFISDateTime;
    var tFinishDateTime = new AFISDateTime;
    // to here is 128 bytes long.
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    var nQueryType:Byte = _ ;
    var bIsFifoQue:Byte = _ ;
    var nFlag:Byte = _ ;
    var nRmtFlag:Byte = _ ;
    var nMICCount:Short = _ ;
    var nPriority:Byte = _ ;
    var nHitPoss:Byte = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
    // to here is 128+32 bytes long
    @Length(32)
    var szQUID:String = _ ;
    @Length(16)
    var szSubmitUnitCode:String = _ ;
    @Length(48)
    var bnRes4:Array[Byte] = _ ;
  } // GAFIS_QRYSEARCHLOG;	// 256 bytes long.

  class GAFIS_QRYCHECKLOG_OLD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(32)
  var szKey:String = _ ;
    var nCandCnt:Int = _ ;
    // to here 40 bytes long.
    var nQueryType:Byte = _ ;
    var nHitPoss:Byte = _ ;
    var bIsRmtQuery:Byte = _ ;
    var bIsFifoQue:Byte = _ ;
    var nFlag:Byte = _ ;
    var nRmtFlag:Byte = _ ;
    var nVerifyResult:Byte = _ ;
    var nCheckWorkType:Byte = _ ;	// check or recheck. QRYLOG_CHECKTYPE_XXX
  // to here is 48 bytes long.
  var nFirstCandScore:Int = _ ;
    var nHitCandScore:Int = _ ;
    var nHitCandIndex:Int = _ ;
    @Length(4)
    var bnRes0:Array[Byte] = _ ;
    // to here is 64 bytes long.

    @Length(16)
    var szSubmitUser:String = _ ;
    @Length(16)
    var szSubmitUnitCode:String = _ ;

    @Length(32)
    var szFirstCandKey:String = _ ;
    // to here is 128 bytes long.
    @Length(32)
    var szHitCandKey:String = _ ;

    var tSubmitDateTime = new AFISDateTime;
    var tFinishDateTime = new AFISDateTime;
    var tCheckDateTime = new AFISDateTime;
    var tReCheckDateTime = new AFISDateTime;
    @Length(16)
    var bnRes1:Array[Byte] = _ ;
    // to here is 128+80 bytes long.

    @Length(16)
    var bnIP:Array[Byte] = _ ;	// binary ip address.
  @Length(32)
  var szComputerName:String = _ ;	// submit computer name.
    // to here is 256 bytes long.

    @Length(16)
    var szCheckerName:String = _ ;
    @Length(16)
    var szCheckerUnitCode:String = _ ;
    @Length(16)
    var szReCheckerName:String = _ ;
    @Length(16)
    var szReCheckerUnitCode:String = _ ;
    // to here is 256+64 bytes long.

    @Length(SID_SIZE)
    var nSID:String = _ ;			// current record id.
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    @Length(56)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_QRYCHECKLOG_OLD;	// 128*3=384 bytes long.

  // GAFIS_QRYCHECKLOG::nCheckWorkType(can not be or'ed).
  final val QRYLOG_CHECKTYPE_CHECK = 0x1	// check
  final val QRYLOG_CHECKTYPE_RECHECK = 0x2	// recheck


  class GAFIS_QRYCHECKLOG extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKey:String = _ ;
    @Length(32)
    var szQUID:String = _ ;
    // to here 72 bytes long.
    var nQueryType:Byte = _ ;
    var nHitPoss:Byte = _ ;
    var nCheckResult:Byte = _ ;
    @Length(1)
    var bnRes1:Array[Byte] = _ ;
    var nCandCnt:Int = _ ;
    // to here is 80 bytes long.
    var nFirstCandScore:Int = _ ;
    var nQryFlag:Byte = _ ;
    var nQryRmtFlag:Byte = _ ;
    var nHitCandCnt:Short = _ ;
    // to here is 88 bytes long.

    @Length(16)
    var szSubmitUser:String = _ ;
    @Length(16)
    var szSubmitUserUnitCode:String = _ ;

    // to here is 120 bytes long.

    var tSearchFinishTime = new AFISDateTime;
    var tCheckDateTime = new AFISDateTime;

    @Length(16)
    var szCheckUserName:String = _ ;
    @Length(16)
    var szCheckerUnitCode:String = _ ;
    // to here is 168 bytes long.

    @Length(SID_SIZE)
    var nSID:String = _ ;			// current record id.
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    // to here is 176 bytes long.
    @Length(80)
    var bnRes5:Array[Byte] = _ ;
  } // GAFIS_QRYCHECKLOG;	// 256 bytes long.

  class GAFIS_QRYRECHECKLOG extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKeyID:String = _ ;
    @Length(32)
    var szQUID:String = _ ;
    // to here is 72 bytes long.
    @Length(16)
    var szCheckUserName:String = _ ;
    @Length(16)
    var szReCheckUserName:String = _ ;
    var tCheckDateTime = new AFISDateTime;
    var tReCheckDateTime = new AFISDateTime;
    @Length(16)
    var szSubmitUserUnitCode:String = _ ;
    @Length(16)
    var szCheckerUnitCode:String = _ ;
    @Length(16)
    var szReCheckerUnitCode:String = _ ;
    // to here is 96+72=168 bytes long.
    @Length(SID_SIZE)
    var nSID:String = _ ;			// current record id.
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    var nQueryType:Byte = _ ;
    var nVerifyResult:Byte = _ ;
    var nRecordType:Byte = _ ;
    @Length(1)
    var bnRes1:Array[Byte] = _ ;
    var nQryFlag:Byte = _ ;
    var nQryRmtFlag:Byte = _ ;
    var nHitCandCnt:Short = _ ;
    // to here is 168+16=184 bytes long.
    @Length(72)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_QRYRECHECKLOG;	// 256 bytes long.

  // user work log
  // when scan, edit and textinput, verify we can log as work.
  class GAFIS_WORKLOGSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  var nDBID:Short = _ ;
    var nTID:Short = _ ;
    // to here is 8 bytes long.
    @Length(32)
    var szKey:String = _ ;
    @Length(16)
    var szUserName:String = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bn_SID:Array[Byte] = _ ;
    @Length(8)
    var bnRes0:Array[Byte] = _ ;
    var tWorkDateTime = new AFISDateTime;
    // to here is 80 bytes long
    var nWorkType:Byte = _ ;	// work type. GAFIS_WORKTYPE_XXX
  var nWorkClass:Byte = _ ;	// work class. GAFIS_WORKCLASS_XXX
  @Length(6)
  var bnRes:Array[Byte] = _ ;
    // to here is 88 bytes long.
    // object.
    var bFingerImgChanged:Byte = _ ;
    var nFingerMntChanged:Byte = _ ;
    var bPalmImgChanged:Byte = _ ;
    var nPalmMntChanged:Byte = _ ;
    var bTPlainImgChanged:Byte = _ ;
    var nTPlainMntChanged:Byte = _ ;
    var bTextChanged:Byte = _ ;
    var bOtherChanged:Byte = _ ;
    var bIsTwoFaceCard:Byte = _ ;
    //
    @Length(7)
    var bnRes2:Array[Byte] = _ ;
    // to here is 88+16=104 bytes long.
    @Length(16)
    var szComputerName:String = _ ;
    var nFingerCnt:Byte = _ ;		// roll finger
  var nFingerHQCnt:Byte = _ ;	// high quality count.
  var nTPlainCnt:Byte = _ ;
    var nTPlainHQCnt:Byte = _ ;
    var nPalmCnt:Byte = _ ;
    var nPalmHQCnt:Byte = _ ;
    var nEditTime:Short = _ ;	// edit time in second.
  } // GAFIS_WORKLOGSTRUCT;	// 128 bytes long

  //GAFIS_WORKLOGSTRUCT::nWorkType
  final val GAFIS_WORKTYPE_SCAN = 1	// scan and cut
  final val GAFIS_WORKTYPE_EDIT = 2
  final val GAFIS_WORKTYPE_TXTINPUT = 3
  final val GAFIS_WORKTYPE_SCANTOFILE = 4	// scan to file.
  final val GAFIS_WORKTYPE_SCANFROMFILE = 5	// scan from file(doing cut work). doing cut work.

  final val GAFIS_WORKTYPE_CHECK = 10
  final val GAFIS_WORKTYPE_RECHECK = 11

  // GAFIS_WORKLOGSTRUCT::nWorkClass
  final val GAFIS_WORKCLASS_TP = 1
  final val GAFIS_WORKCLASS_LP = 2
  final val GAFIS_WORKCLASS_QUERY = 3

  class GAFIS_WORKLOGRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  @Length(16)
  var szUserNameWild:String = _ ;	// user name wild, to here is 64 bytes
  @Length(8)
  var bnRes:Array[Byte] = _ ;
    var stTimeRange = new GATIMERANGE;	// 16 bytes
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GQUERYRETR_ITEMFLAG_XXX
  @Length(3)
  var bnRes_1:Array[Byte] = _ ;		// bytes reserved
  @Length(32)
  var bnRes2:Array[Byte] = _ ;
  } // GAFIS_WORKLOGRETRSTRUCT;	// size is 128 bytes

  // mnt correction data.
  // finger ment edit info.
  class GAFIS_FINGERMNTEDITINFO extends AncientData
  {
    @Length(1)
    var bnRes0:Array[Byte] = _ ;
    var nOldMntCnt:Byte = _ ;		// mnt count extracted automatically
  var nManuAddMntCnt:Byte = _ ;	// manually added mnt count. those mnt must be lost by automatic extraction
  var nManuDelMntCnt:Byte = _ ;	// false mnt count.
  var nQualityLevel:Byte = _ ;		// finger quality.
  var nCoreDeltaChangedFlag:Byte = _ ;	// GAFIS_COREDELTA_
  var nOldCoreDeltaFlag:Byte = _ ;		// GAFIS_COREDELTA_
  var nNewCoreDeltaFlag:Byte = _ ;		// GAFIS_COREDELTA_
  var nOldRp:Short = _ ;		// old ridge pattern major and minor.
  var nNewRp:Short = _ ;		// new ridge pattern major and minor
  } // GAFIS_FINGERMNTEDITINFO;	// 12 bytes long.

  // nOldCoreDeltaFlag record automatically extracted core delta info.
  // nNewCoreDeltaFlag record manual edit core delta info.
  // but if core both exists in old and new but position has been changed dramatically
  // then we may view core delta has been changed.
  // GAFIS_FINGERMNTEDITINFO ( core delta flag
  final val GAFIS_COREDELTA_CORE = 0x1
  final val GAFIS_COREDELTA_VCORE = 0x2
  final val GAFIS_COREDELTA_LDELTA = 0x4
  final val GAFIS_COREDELTA_RDELTA = 0x8

  class GAFIS_PALMMNTEDITINFO extends AncientData
  {
    @Length(2)
    var bnRes:Array[Byte] = _ ;
    var nOldMntCnt:Short = _ ;
    var nManuAddMntCnt:Short = _ ;
    var nManuDelMntCnt:Short = _ ;
    // to here is 8 bytes long.
    var nQualityLevel:Byte = _ ;		// finger quality.
  var nOldCoreDeltaCnt:Byte = _ ;
    var nManuAddCoreDeltaCnt:Byte = _ ;
    var nManuDelCoreDeltaCnt:Byte = _ ;
    @Length(4)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_PALMMNTEDITINFO;	// 16 bytes long.

  class GAFIS_MNTEDITLOG extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  var nDBID:Short = _ ;
    var nTID:Short = _ ;
    // to here is 8 bytes long.
    @Length(32)
    var szKey:String = _ ;
    @Length(16)
    var szUserName:String = _ ;
    var tDateTime = new AFISDateTime;	// edit date time.
  // to here is 64 bytes long.
  @Length(SID_SIZE)
  var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(8)
    var bnRes0:Array[Byte] = _ ;
    @Length(10)
    var stFg:Array[GAFIS_FINGERMNTEDITINFO] = _;	// finger edit info. 120 bytes long
  @Length(10)
  var stTPlain:Array[GAFIS_FINGERMNTEDITINFO] = _;	// ten palin fingers.	120 bytes long
  @Length(2)
  var stPalm:Array[GAFIS_PALMMNTEDITINFO] = _;	// two palm 32 bytes long.
  @Length(16)
  var szComputerName:String = _ ;
    @Length(16)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_MNTEDITLOG;	// 384 bytes long.


  class GAFIS_MNTEDITLOGRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  @Length(16)
  var szUserNameWild:String = _ ;	// user name wild, to here is 64 bytes
  @Length(8)
  var bnRes:Array[Byte] = _ ;
    var stTimeRange = new GATIMERANGE;	// 16 bytes
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GQUERYRETR_ITEMFLAG_XXX
  @Length(3)
  var bnRes_1:Array[Byte] = _ ;		// bytes reserved
  @Length(32)
  var bnRes2:Array[Byte] = _ ;
  } // GAFIS_MNTEDITLOGRETRSTRUCT;	// size is 128 bytes


  class GAFIS_EXFERRLOG extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(32)
    var szKey:String = _ ;		// tp card key.
  @Length(16)
  var szComputerName:String = _ ;	// computer name.
  var nDBID:Short = _ ;		// database id.
  var nTID:Short = _ ;		// table id.
  var nErrCode:Int = _ ;	// gafis error code.
  var nRetValue:Int = _ ;	//
  // to here is 64 bytes long.
  var nFingerIndex:Byte = _ ;	// finger index(global)
  var nCompressMethod:Byte = _ ;	// GAIMG_CPRMETHOD_xxx
  var nMntFormat:Byte = _ ;		// mnt format.
  var nSecondFingerIndex:Byte = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    var tDateTime = new AFISDateTime;	// date time.
  var stDllVersion = new GBASE_APPVERSION;	// 8 bytes long.
  var stExfAppVersion = new GBASE_APPVERSION;	// 8 bytes long
  @Length(16)
  var szDllName:String = _ ;	// 16 bytes long.
  @Length(SID_SIZE)
  var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;
  } // GAFIS_EXFERRLOG;	// 128 bytes long.

  class GAFIS_EXFERRLOGRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  @Length(16)
  var szHostNameWild:String = _ ;	// computer name wild, to here is 64 bytes
  @Length(8)
  var bnRes:Array[Byte] = _ ;
    var stTimeRange = new GATIMERANGE;	// 16 bytes
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GQUERYRETR_ITEMFLAG_XXX
  @Length(3)
  var bnRes_1:Array[Byte] = _ ;		// bytes reserved
  @Length(32)
  var bnRes2:Array[Byte] = _ ;
  } // GAFIS_EXFERRLOGRETRSTRUCT;	// 128 bytes long.

  //////////////////////////////























}

package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.internal.c.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object gadbprop {

  class GAQRYDBACTIONSTRUCT extends AncientData
  {
    var nAction:Byte = _ ;	// GAQRYDB_AUTOACTION_XXX
  @Length(7)
  var bnRes:Array[Byte] = _ ;
    var nTime:Int = _ ;	// take action only after this time, seconds.
  @Length(4)
  var bnRes2:Array[Byte] = _ ;
  } // GAQRYDBACTIONSTRUCT;	// size is 16 bytes

  // in this version we add some properties on query que lib
  // such auto backup property and some other informations
  // if a query finished a long time
  // if it was not verified for a long time, del?or backup?
  // if it has been verified for a long time, del?or backup?
  // if backup we have a global variable to do the backup
  // this information belongs to query que
  class GAQRYDBPROPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes:Array[Byte] = _ ;	// reserved
  var stActForFinished = new GAQRYDBACTIONSTRUCT;
    var stActForChecked = new GAQRYDBACTIONSTRUCT;
    var bBackupUsedOnly:Byte = _ ;	// does not get query from this db.
  var nLogFlag:Byte = _ ;			// QRYDB_LOGFLAG_XXX
  @Length(86+128)
  var bnRes2:Array[Byte] = _ ;
  } // GAQRYDBPROPSTRUCT;	// 256 bytes

  final val GAQRYDB_AUTOACTION_NONE = 0x0
  final val GAQRYDB_AUTOACTION_DEL = 0x1
  final val GAQRYDB_AUTOACTION_BACKUP = 0x2

  // GAQRYDBPROPSTRUCT::nLogFlag, can be OR'ed.
  final val QRYDB_LOGFLAG_SUBMITLOG = 0x1
  final val QRYDB_LOGFLAG_SEARCHLOG = 0x2
  final val QRYDB_LOGFLAG_CHECKLOG = 0x4
  final val QRYDB_LOGFLAG_RECHECKLOG = 0x8


  class GADBIDSTRUCT extends AncientData
  {
    var nDBID:Short = _ ;
    var nTableID:Short = _ ;
  } // GADBIDSTRUCT;	// size is 4 bytes

  /*
  class TPLPDBPROPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    /*
    var nFingerMntMaxLen:Short = _ ;	// finger mnt length.
    var nPalmMntMaxLen:Short = _ ;		// palm mnt max length
    var nFingerBinMaxLen:Short = _ ;	// bin data max length for finger.
    var nPalmBinMaxLen:Short = _ ;		// bin data max length for palm
    */
    @Length(8)
    var bnRes_mnt:Array[Byte] = _ ;
    //	UCHAR	nMntFormat;				// format of minutia. GAFIS_MNTFORMAT_XXX
    //	UCHAR	bPalmUseBinToSearch;	//
    //	UCHAR	bFingUseBinToSearch;	//
    @Length(48)
    var bnRes0:Array[Byte] = _ ;	// to here 64 bytes
  var nLPDestDBCount:Byte = _ ;
    var nTPDestDBCount:Byte = _ ;
    @Length(6)
    var bnRes_1:Array[Byte] = _ ;	// 72 bytes
  @Length(4)
  var stTPDestDB:Array[GADBIDSTRUCT] = _;
    @Length(4)
    var stLPDestDB:Array[GADBIDSTRUCT] = _;
    @Length(32)
    var szLiveScanQualCheckEditQue:String = _ ;	// after quality check if need edit then add to this queue.
  var bnRes_2:Byte = _ [256-104-sizeof(GADBIDSTRUCT)*8];
  } // TPLPDBPROPSTRUCT;	// 256 bytes long
  */

  final val GAFIS_MNTFORMAT_XGW = 0	// xgw mnt format
  final val GAFIS_MNTFORMAT_LCW = 1	// lucas wang's format.
  final val GAFIS_MNTFORMAT_XGWLCW = 2	//!< 许公望和王曙光的特征合并在一起的格式，王曙光的特征存放在BIN数据中
  final val GAFIS_MNTFORMAT_ZKY = 5	//!< 中科院方法，为了和galocvar.h中的定义对上。此处先定义为5。
  //!< GFALGORITHMHEADEX结构中stHead.nCurAlgorithm有赋值不一致的情况。所以4先暴漏



  /**
   * bin数据类型(0-9为保留类型)
   */
  final val GAFIS_BINDATA_TYPE_PORETYPE = 10		//!< 汗眼数据
  final val GAFIS_BINDATA_TYPE_LCWMNT = 11		//!< 王曙光的特征数据


  class DBSPECPROPUNION extends AncientData
  {
    @Length(256)
    var data:Array[Byte]  = _
    /*
    GAQRYDBPROPSTRUCT	stQryDBProp;
    TPLPDBPROPSTRUCT	stTPLPDBProp;
    */
  } //DBSPECPROPUNION;

  class GAFIS_PMDB_PROPSTRUCT extends AncientData
  {
    @Length(16)
    var uuidLoc_MachineID:String = _ ;
    @Length(16)
    var uuidSvr_MachineID:String = _ ;
    @Length(16)
    var uuidLoc_DBUUID:String = _ ;
    @Length(16)
    var uuidSvr_DBUUID:String = _ ;
    //	UCHAR	bNotValid;	// if the parallel db is not valid we set this value to one.
    // the following 4 items are used for  backup.
    var tLoc_CreateTime:Int = _ ;
    var tSvr_CreateTime:Int = _ ;
    var tLoc_UpdateTime:Int = _ ;
    var tSvr_UpdateTime:Int = _ ;
    @Length(64-16)
    var bnRes0:Array[Byte] = _ ;
    @Length(128)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_PMDB_PROPSTRUCT;	// size is 256 bytes

  /*
  class GAFIS_PMTABLE_PROPSTRUCT extends AncientData
  {
    var tLoc_CreateTime:Int = _ ;
    var tSvr_CreateTime:Int = _ ;
    var tLoc_UpdateTime:Int = _ ;
    var tSvr_UpdateTime:Int = _ ;
    var stDataAvail = new GA_NETIDRANGE;	// available data sid range
  //	GA_NETIDRANGE	stDataInMem;	// these data is in memory. need not save on disk. so commented.
  @Length(8)
  var bnRes_1:Array[Byte] = _ ;		// 8 bytes reserved to replace stDataAvail.
  // to here is 32 bytes long.
  var nLoc_DBID:Short = _ ;	// dynamic
  var nSvr_DBID:Short = _ ;	// dynamic
  var nLoc_TableID:Short = _ ;
    var nSvr_TableID:Short = _ ;
    //	UCHAR	bGetLob;		// get lob or not
    var nFlag:Byte = _ ;			// PMTABLE_FLAG_XX
  var bNotValid:Byte = _ ;		// need valid database.
  var nStatus:Byte = _ ;		// status.PMTABLE_STATUS_
  var nTableType:Byte = _ ;		// PMTABLE_TYPE_xxx
  var nBinMethod:Byte = _ ;		// PMTABLE_BINMETHOD_xxx
  var nDownloadBinStatus:Byte = _ ;		// down bin mode. PMTABLE_DOWNBINSTATUS_xxx
  var bFirstTimeBackup:Byte = _ ;		// first time backup flag. backup process used only
  var nKeepSkmStatus:Byte = _ ;		// PMTABLE_KSS_xxx
  // to here is 32+16 bytes long
  var stSvr_TableUUID = new GAFIS_UUIDStruct;	// server table uuid.
  // to here is 64 bytes long
  var stBinDownloaded = new GA_NETIDRANGE;	// bin data downloaded.
  @Length(256-64-8)
  var bnRes:Array[Byte] = _ ;
  } // GAFIS_PMTABLE_PROPSTRUCT;	// size is 256 bytes
  */

  //GAFIS_PMTABLE_PROPSTRUCT::nStatus
  final val PMTABLE_STATUS_READY = 0x0	// the table is ok, and need only update.
  final val PMTABLE_STATUS_NEEDGETDATA = 0x1	// need get data(create)
  final val PMTABLE_STATUS_CHANGERANGE = 0x2	// data range changed.

  // GAFIS_PMTABLE_PROPSTRUCT::nBinMethod
  final val PMTABLE_BINMETHOD_SYNC = 0x0	// download same time with mnt.
  final val PMTABLE_BINMETHOD_DELAY = 0x1	// delay down load bin data.

  // GAFIS_PMTABLE_PROPSTRUCT::nDownloadBinStatus
  final val PMTABLE_DOWNBINSTATUS_FINISHED = 0x0
  final val PMTABLE_DOWNBINSTATUS_NEEDDOWN = 0x1	// DOWN LOAD BIN NOT FINISHED.

  // GAFIS_PMTABLE_PROPSTRUCT::nKeepSkmStatus
  final val PMTABLE_KSS_READY = 0
  final val PMTABLE_KSS_NEEDGET = 1	// 本地库结构已经和服务器一致，但是需要下载增加的列的数据。

  // used only when support sublib partial table. if this flag used then
  // it means that table is the last part of the table.
  // GAFIS_PMTABLE_PROPSTRUCT::nFlag
  final val PMTABLE_FLAG_LASTPART = 0x1
  final val PMTABLE_FLAG_NOBIN = 0x2	// no bin data. if no bin data, then nBinMode is not used.

  // we borrow the following structures.
  //type  GAFIS_BKP_TABLEPROP = GAFIS_PMTABLE_PROPSTRUCT  ;	// table prop.
  type GAFIS_BKP_DBPROP =  GAFIS_PMDB_PROPSTRUCT	;	// database prop.

  //GAFIS_BACKUPPROP::nStatus
  final val GAFIS_BKPPROP_STATUS_READY = 0
  final val GAFIS_BKPPROP_STATUS_GETTINGDATA = 1

  // GAFIS_BACKUPPROP::nFlag;
  final val GAFIS_BKPPROP_FLAG_FIRSTTIME = 0x1	// first time.


  // this structure is a mixture of the numina database property and afis database property
  // table and database all have properties, they are saved in an administration table of
  // the database
  class GADBPROPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(32)
  var szName:String = _ ;		// database name
  @Length(128)
  var szComment:String = _ ;	// database comment
  var nDBID:Short = _ ;		// database id
  var nTableCount:Short = _ ;	// how many tables included in this database(to here is 168 bytes)
  var nCaseIDLen:Byte = _ ;
    var nPersonIDLen:Byte = _ ;
    var nFingerIDLen:Byte = _ ;	// for tenprint it's cardid, palm id, for latent it's finger id and palm id
  var nType:Byte = _ ;			// tenprint, latent, query:GADBPROP_TYPE_TENPRINT, LATENT, QUERY
  var nDefaultImgCPRMethod:Byte = _ ;	// GAFISCPR_METHOD_xxx
  var nBuildDBMode:Byte = _ ;	// can be MODE_WITHEDIT, or MODE_WITHOUTEDIT
  var nSkipEditThreshold:Byte = _ ;	// threshold for fingers need not to be edited.
  var nTTLeastHitPoss:Byte = _ ;
    var nTLLeastHitPoss:Byte = _ ;
    var nLTLeastHitPoss:Byte = _ ;
    var nLLLeastHitPoss:Byte = _ ;	// only TTLeastHitPoss has been used
  var nMaxRpError:Byte = _ ;		// value between 0, 3
  var bIsSublib:Byte = _ ;			// is parallel match sublib?
  var bDelImgAfter:Byte = _ ;		// FIFOQUE_DELIMG_XXXX, used only for tp lib
  var nOption:Byte = _ ;			// DBPROPOPT_XXX
  @Length(12)
  var nFingerIdx:String = _ ;		// used for one database that may contain finger is not 10
  // for example, in some civial cases we need only to store
  // 2 or four fingers, so this item is an indicator to which
  // finger will be stored. it is used for parallel matcher
  // to reduce disk space used
  var bSupportPalm:Byte = _ ;		// whether support finger
  var bSupportFinger:Byte = _ ;		// whether support palm
  var nMntFormat:Byte = _ ;			// mnt format, GAFIS_MNTFORMAT_xxx
  @Length(2)
  var bnRes:Array[Byte] = _ ;			// 2 bytes reserved(to here is 200 bytes)
  @Length(32)
  var szCaseIDHead:String = _ ;	// case id head, 32 bytes
  @Length(32)
  var szPersonIDHead:String = _ ;	// person id head, 32 bytes
  @Length(32)
  var szFingerIDHead:String = _ ;	// finger id head, 32 bytes
  var nMaxFingerCount:Int = _ ;	// max record count
  var nMaxPalmCount:Int = _ ;
    var nCurFingerCount:Int = _ ;	// current record count
  var nCurPalmCount:Int = _ ;
    var nCurCaseCount:Int = _ ;	// current case count
  var nCopySchemaFromDBID:Short = _ ;	//
  var nTTLeastScore:Short = _ ;	// only score>=this can the candidate be inserted to cand list
  var nTLLeastScore:Short = _ ;
    var nLTLeastScore:Short = _ ;
    var nLLLeastScore:Short = _ ;
    var bWillPMatch:Byte = _ ;	// will parallelly searched.
  @Length(1)
  var bnRes2:Array[Byte] = _ ;			// reserved
  var tLastCheckPalmTime:Int = _ ;	// because this nPalmCount may be incorrect,
  // so we need to check database this count, but it will be a very
  // long time, so we only check with this time interval.
  //	UCHAR	nPalmCountx[4];			// tpused only : # of records that both palms are null
  @Length(4)
  var bnRes_Palm:Array[Byte] = _ ;			// not used reserved.
  @Length(32)
  var szFirstFIFOQueName:String = _ ;	// first fifo queue name
  @Length(16)
  var guidUUID:String = _ ;		// uuid of this database, we internally use this value to distinguish different database
  @Length(128)
  var szMainPath:String = _ ;	// main path
  var stSpecProp = new DBSPECPROPUNION;
    var stPmDBProp = new GAFIS_PMDB_PROPSTRUCT;	// prop for parallel match
  @Length(48)
  var szDispName:String = _ ;		// display name
  @Length(512-48)
  var bnRes_Fin:Array[Byte] = _ ;
  } // GADBPROPSTRUCT;	// size of this structure is 1024+512 bytes

  final val GADBPROP_TYPE_TENPRINT = 0x1
  final val GADBPROP_TYPE_LATENT = 0x2
  final val GADBPROP_TYPE_QUERY = 0x3
  final val GADBPROP_TYPE_MISC = 0x4

  final val GADBPROP_BDBMODE_WITHEDIT = 0x1
  final val GADBPROP_BDBMODE_WITHOUTEDIT = 0x2

  //#define	GATABLEPROP_TYPE_LATENTFINGER		0x1
  //#define	GATABLEPROP_TYPE_LATENTPALM			0x2
  //#define	GATABLEPROP_TYPE_LATENTCASE			0x3
  //#define	GATABLEPROP_TYPE_TENCARD			0x4
  //#define	GATABLEPROP_TYPE_TENPERSON			0x5
  //#define	GATABLEPROP_TYPE_SEARCHFIFOQUE		0x6
  //#define	GATABLEPROP_TYPE_CHECKFIFOQUE		0x7
  //#define	GATABLEPROP_TYPE_EDITFIFOQUE		0x8
  //#define	GATABLEPROP_TYPE_PROCESSQUE			0x9

  final val DBPROPOPT_SKIPEDITIFOK = 0x1
  final val DBPROPOPT_EDITGETALL = 0x2
  final val DBPROPOPT_NEEDWORKLOG = 0x4
  final val DBPROPOPT_MODDBLOG = 0x8

  /*
  type	GACOLUMNPROPSTRUCT = GADB_COLUMNSCHEMA	;


  type	GAFIS_DBSIMPPROPG ADB_DBSIMPPROP		;
  type GAFIS_TABLESIMPPROP = 	GADB_TABLESIMPPROP	;
  */

  // structure for store information for parallel searching.
  // we reserve many space for future use.
  /*
  class GAFIS_NETPMTABLEPROP extends AncientData
  {
    var stDBUUID = new GAFIS_UUIDStruct;		// dbuuid of the database, 16 bytes long
  var stTableUUID = new GAFIS_UUIDStruct;	// uuid of the table
  // to here is 32 bytes long
  var nDBID:Short = _ ;
    var nTID:Short = _ ;
    var nRowPerBlock:Int = _ ;	// row per block of the server.
  var nMaxSID:Int = _ ;			// max sid of the table.
  @Length(4)
  var bnRes0:Array[Byte] = _ ;			//
  // to here is 32+16 bytes long
  /*
  var nMaxFingMntLen:Short = _ ;	// mnt len for each item.
  var nMaxFingBinLen:Short = _ ;	// bin data length for each item(finger or palm)
  var nMaxPalmMntLen:Short = _ ;
  var nMaxPalmBinLen:Short = _ ;
  var bFingHasBinData:Byte = _ ;	// whether finger has bin data.
  var bPalmHasBinData:Byte = _ ;	// whether palm has bin data
  */
  @Length(10)
  var nPFItemIdx:String = _ ;		// plain finger index, if that finger need parallel match
  // then set corresponding byte to non-zero. different
  // method from nItemIdx.
  var nItemCnt:Byte = _ ;			// # of fingers and/or palms
  var nMntFormat:Byte = _ ;			// mnt format
  var nTableType:Byte = _ ;			// reserved.
  var nStatus:Byte = _ ;			// table status.	PMTABLE_STATUS_XX
  var nFlag:Byte = _ ;				// table flag. PMTABLE_FLAG_XX
  @Length(1)
  var bnRes1:Array[Byte] = _ ;			//
  // to here is 64 bytes long
  @Length(12)
  var nItemIdx:String = _ ;		// for tp used only. indicate which finger or palm is used.
  @Length(4)
  var bnRes2:Array[Byte] = _ ;		//
  var nModLogHistTime:Int = _ ;	// how long in time of record mod history logged.
  @Length(12)
  var bnRes3:Array[Byte] = _ ;
    // to here is 96 bytes long.
    var stDataAvail = new GA_NETIDRANGE;	// available data sid range
  var stDataInMem = new GA_NETIDRANGE;	// data in memory.
  // to here is 96+16 bytes long.
  var nFaceIdx:Byte = _ ;	// if one bit is set then that bit will take parallel match.
  var nVoiceIdx:Byte = _ ;	// if one bit is set then that bit will take parallel match.
  @Length(14)
  var bnRes30:Array[Byte] = _ ;
    @Length(32)
    var szDBName:String = _ ;		// database name. added on Mar. 28, 2006
  @Length(32)
  var szTableName:String = _ ;	// table name. added on Mar. 28, 2006
  @Length(64)
  var bnRes4:Array[Byte] = _ ;
  } // GAFIS_NETPMTABLEPROP;	// pm table prop, size is 256 bytes long.
  */

  // fifo queue properties
  class GAFIFOQUEPROPSTRUCT extends AncientData
  {
    var nFollowedQueCount:Byte = _ ;	// number of queue followed
  @Length(3)
  var bnRes0:Array[Byte] = _ ;
    @Length(32)
    var szDestDBName:String = _ ;	// name of destination db(for check and search only)
    @Length(8 * 32)
  var szQueName:Array[Byte] = _ ;	// maximum 8 queue's can be followed
  var nSearchDBID:Short = _ ;		// default dbid to search
  var nQryDBID:Short = _ ;		// default query dbid
  @Length(220-4)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GAFIFOQUEPROPSTRUCT;	// 512 bytes

  final val FIFOQUE_DELIMG_NONE = 0x0
  final val FIFOQUE_DELIMG_AFTERTHIS = 0x1	// all que finished
  final val FIFOQUE_DELIMG_AFTERQUEFIN = 0x2	// all que finished
/*
  class GATABLEPROPSTRUCT extends AncientData
  {
    var stProp = new GADB_TABLEPROPSCHEMA;	// 384 bytes long.
  var nTableType:Byte = _ ;	// gafis table type.	// TABLETYPE_XXX. this is not the same as
  // the table type in stProp, that tabletype is GADB_TABLETYPE_XXX.
  var nQueType:Byte = _ ;	// if
  var nReclFlag:Byte = _ ;	// recl flag GAFIS_TBL_RECLFLAG_XXX
  var nFlagEx:Byte = _ ;	// GAFIS_TBL_FLAGEX_XXX
  @Length(4)
  var bnRes0:Array[Byte] = _ ;	//
  @Length(SID_SIZE)
  var nPalmRowCount:String = _ ;	// palm row count.
  @Length(8-SID_SIZE)
  var bnRes_PalmCnt:Array[Byte] = _ ;
    // to here is 384+16 bytes long.
    @Length(16)
    var bnReclDBUUID:Array[Byte] = _ ;		// when deleting data from this table, we move data
  @Length(16)
  var bnReclTblUUID:Array[Byte] = _ ;		// to this table.
  @Length(80)
  var bnRes:Array[Byte] = _ ;	// FIFOQUETYPE_XXX
  } // GATABLEPROPSTRUCT;	// 512 bytes
  */

  // GATABLEPROPSTRUCT::nReclFlag
  final val GAFIS_TBL_RECLFLAG_BKPDELDATA = 0x1

  // GATABLEPROPSTRUCT::nFlagEx
  final val GAFIS_TBL_BACKUP_USEDONLY = 0x1	// if tabletype is query, and this flag set, then
  // does not retrieve query from this table to do searching.

  final val TABLETYPE_BADTYPE = 0	// bad type.
  final val TABLETYPE_TPCARD = 1
  final val TABLETYPE_PERSON = 2
  final val TABLETYPE_LATFINGER = 3
  final val TABLETYPE_LATPALM = 4
  final val TABLETYPE_DBLOG = 5
  final val TABLETYPE_CASE = 6
  final val TABLETYPE_QUE = 7
  final val TABLETYPE_QUERY = 8
  final val TABLETYPE_USER = 9
  final val TABLETYPE_CODETABLE = 10
  final val TABLETYPE_MSG = 11
  final val TABLETYPE_BREAKCASE = 12

  final val TABLETYPE_RMT_SERVER = 13
  final val TABLETYPE_RMT_ROUTER = 14
  final val TABLETYPE_RMT_TRANSMITCFG = 15
  final val TABLETYPE_RMT_REPORTQUE = 16
  final val TABLETYPE_RMT_DOWNLOADQUE = 17
  final val TABLETYPE_RMT_KEYQUE = 18
  final val TABLETYPE_RMT_REPORTHISTORY = 19
  final val TABLETYPE_RMT_DOWNLOADHISTORY = 20
  final val TABLETYPE_RMT_QUERYCTRL = 21
  final val TABLETYPE_RMT_PROXYQUERYCTRL = 22
  final val TABLETYPE_RMT_AUTOSENDQUERY = 23
  final val TABLETYPE_RMT_RMTUSERDB = 24
  final val TABLETYPE_RMT_AUTOECHO = 25
  final val TABLETYPE_RMT_STATINFO = 26
  final val TABLETYPE_RMT_MSG = 27
  final val TABLETYPE_RMT_ASYNCMD = 28
  final val TABLETYPE_RMT_DATCTRL = 29

  final val TABLETYPE_SYSMSG = 30
  final val TABLETYPE_BACKUPLOG = 31
  final val TABLETYPE_BACKUP_AUTIT = 32
  final val TABLETYPE_MOBILECASE = 33
  final val TABLETYPE_PARAMETER = 34
  final val TABLETYPE_QRYMODLOG = 35
  final val TABLETYPE_SYSMODLOG = 36
  final val TABLETYPE_USERAUTHLOG = 37
  final val TABLETYPE_DBRUNLOG = 38
  final val TABLETYPE_QUALCHECK = 39
  final val TABLETYPE_WORKLOG = 40
  final val TABLETYPE_MNTEDITLOG = 41
  final val TABLETYPE_LATFACE = 42
  final val TABLETYPE_LATVOICE = 43
  final val TABLETYPE_EXFERRLOG = 44
  final val TABLETYPE_QUALCHECKLOG = 45
  final val TABLETYPE_QRYSEARCHLOG = 46
  final val TABLETYPE_QRYCHECKLOG = 47
  final val TABLETYPE_QRYSUBMITLOG = 48
  final val TABLETYPE_QRYRECHECKLOG = 49
  final val TABLETYPE_TPUNMATCH = 50
  final val TABLETYPE_LPUNMATCH = 51
  final val TABLETYPE_TPLP_UNMATCH = 52
  final val TABLETYPE_TPLP_ASSOCIATE = 53
  final val TABLETYPE_TPLP_FPXQUE = 54
  final val TABLETYPE_TPLP_FPXLOG = 55
  final val TABLETYPE_QRY_FPXQUE = 56
  final val TABLETYPE_QRY_FPXLOG = 57
  final val TABLETYPE_LPGROUP = 58
  final val TABLETYPE_CASEGROUP = 59
  final val TABLETYPE_LSN_MANAGER = 60
  final val TABLETYPE_QRYASSIGN = 61

  final val TABLETYPE_LFICDTSTATUS = 62
  final val TABLETYPE_LFICFBINFO = 63
  final val TABLETYPE_GAXCTASK = 64
  final val TABLETYPE_GAXCLOG = 65
  final val TABLETYPE_WANTED_INFO = 66
  final val TABLETYPE_WANTED_TPCARD = 67
  final val TABLETYPE_WANTED_LOG = 68

  //add by zyn at 2014.08.11 for shanghai xk
  final val TABLETYPE_SHXK_DATASTATUS = 80
  final val TABLETYPE_SHXK_MATCHINFO = 81
  final val TABLETYPE_SHXK_CASESTATUS = 82
  final val TABLETYPE_SHXK_CASETEXT = 83
  //add by nn at 2014 9 25
  final val TABLETYPE_TT_RELATION = 84
  final val TABLETYPE_TT_CANDIDATE = 85

  //!<add by zyn at 2014.10.21 for nj
  final val TABLETYPE_NJDELDATA = 86

  //!<add by wangkui at 2014.12.17 for xc
  final val TABLETYPE_XCDATA = 87
  final val TABLETYPE_XCREPORTDATA = 88



  ///////// pm table type.
  final val PMTABLE_TYPE_TP = TABLETYPE_TPCARD
  final val PMTABLE_TYPE_LPFINGER = TABLETYPE_LATFINGER
  final val PMTABLE_TYPE_LPPALM = TABLETYPE_LATPALM
  final val PMTABLE_TYPE_LPFACE = TABLETYPE_LATFACE
  final val PMTABLE_TYPE_LPVOICE = TABLETYPE_LATVOICE
  final val PMTABLE_TYPE_CASE = TABLETYPE_CASE		// case will be searched parallelly

  class GAFIS_TPCARD_MISIDCONF extends AncientData
  {
    var cbSize:Int = _ ;
    // some user want the mis person id to be generated automatically and
    // we store next available mis person in table prop.
    // if szMISID is null we do nothing. it must satisfy some condition.
    // final format is *yyyymmxxxx.
    @Length(20)
    var szMISIDHead:String = _ ;
    var nYear:Short = _ ;	// store next available year, if year is less than current year we automatically
  // set it to current year.[2000-9999].
  var nMonth:Byte = _ ;		// current month, [01-12]
  var bInited:Byte = _ ;
    // to here is 28 bytes long.
    var nSeq:Int = _ ;	// sequence #.
  // to here is 32 bytes long.
  var tLastAccessTime = new AFISDateTime;
    // to here is 40 bytes long.
    var nOption:Byte = _ ;		// MISIDCONF_OPT_XXX
  var nSeqLen:Byte = _ ;		// how many digits nSeq will occupy
  var nIDLen:Byte = _ ;			// length of id.
  @Length(5)
  var bnRes2:Array[Byte] = _ ;
  } // GAFIS_TPCARD_MISIDCONF;	// 48 bytes long.

  //GAFIS_TPCARD_MISIDCONF::nOption
  final val MISIDCONF_OPT_YEARSHORT = 0x1	// the year is short.
  final val MISIDCONF_OPT_SEQNOTZERO = 0x2	// seq can not be zero.
  final val MISIDCONF_OPT_USEHEAD = 0x4	// misidhead is used.

  class GAFIS_TPCARDTABLE_PROP extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  @Length(4)
  var bnRes:Array[Byte] = _ ;
    var stMisIDConf = new GAFIS_TPCARD_MISIDCONF;
    @Length(72)
    var bnRes1:Array[Byte] = _ ;
  } // GAFIS_TPCARDTABLE_PROP;	// 128 bytes long.

  /*
  typedef	union	tagGAFIS_TABLE_SPECPROP
  {
    GAFIS_TPCARDTABLE_PROP	stTPCardProp;	// tp card property.
    ////////////
    UCHAR	bnRes[128];
  } GAFIS_TABLE_SPECPROP;	// table specific prop. size is 128 bytes long.

  class GAFIS_TABLEPROPSTRUCT extends AncientData
  {
    var stTableProp = new GATABLEPROPSTRUCT;
    var stFQProp = new GAFIFOQUEPROPSTRUCT;	// fifo que properties
  var stPmTableProp = new GAFIS_PMTABLE_PROPSTRUCT;
    var stSpecProp = new GAFIS_TABLE_SPECPROP;
    @Length(128)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_TABLEPROPSTRUCT;	// size is 1024+512 bytes
*/

  ///////////////////////
  // option for get pmdb
  final val GETPMDB_OPT_GETNOTREADY = 0x1	// get not ready db and table.
  final val GETPMDB_OPT_GETNONPMDB = 0x2	// get non pm db.

}

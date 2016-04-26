package nirvana.hall.c.services.grmtlib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
object grmtpara {

  //paramter's name used on tx server
  class RMTPARAMNAMESTRUCT extends AncientData
  {
    var bInited:Int = _;
    @Length(4)
    var bnRes:Array[Int] = _;

    //used by param table by dbsvr
    var pszLocUnitCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocUnitCode_Data:Array[Byte] = _ // for pszLocUnitCode pointer ,struct:char;
  var pszRmtSvrParam_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtSvrParam_Data:Array[Byte] = _ // for pszRmtSvrParam pointer ,struct:char;
  var pszLocDBMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocDBMap_Data:Array[Byte] = _ // for pszLocDBMap pointer ,struct:char;
  var pszSvrDBMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSvrDBMap_Data:Array[Byte] = _ // for pszSvrDBMap pointer ,struct:char;
  var pszIPFilters_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszIPFilters_Data:Array[Byte] = _ // for pszIPFilters pointer ,struct:char;
  var pszReportDatFilters_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportDatFilters_Data:Array[Byte] = _ // for pszReportDatFilters pointer ,struct:char;
  var pszTxSvrInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSvrInfo_Data:Array[Byte] = _ // for pszTxSvrInfo pointer ,struct:char;
  // required text item [11/26/2005]
  var pszRequiredTPCardTextItem_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRequiredTPCardTextItem_Data:Array[Byte] = _ // for pszRequiredTPCardTextItem pointer ,struct:char;	//tpcard text
  var pszRequiredLPCardTextItem_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRequiredLPCardTextItem_Data:Array[Byte] = _ // for pszRequiredLPCardTextItem pointer ,struct:char;	//lpcard text
  var pszRequiredLPCaseTextItem_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRequiredLPCaseTextItem_Data:Array[Byte] = _ // for pszRequiredLPCaseTextItem pointer ,struct:char;	//lpcase text
  var pszLiveScan_EnableReplace_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScan_EnableReplace_Data:Array[Byte] = _ // for pszLiveScan_EnableReplace pointer ,struct:char;	//enable replace exist finger
  var pszLiveScan_MaxTPCardID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScan_MaxTPCardID_Data:Array[Byte] = _ // for pszLiveScan_MaxTPCardID pointer ,struct:char;	//max tpcardid on rmt server

    // Inner and Outre IP Map Table [12/1/2005]
    var pszIPMapTable_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszIPMapTable_Data:Array[Byte] = _ // for pszIPMapTable pointer ,struct:char;

    var pszResName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszResName_Data:Array[Byte] = _ // for pszResName pointer ,struct:char;	//reserved

    //used by txserver.ini
    var pszUseSetMode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseSetMode_Data:Array[Byte] = _ // for pszUseSetMode pointer ,struct:char;
  var pszUseDebugMode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseDebugMode_Data:Array[Byte] = _ // for pszUseDebugMode pointer ,struct:char;
  var pszLiveUpdateSvr_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveUpdateSvr_Data:Array[Byte] = _ // for pszLiveUpdateSvr pointer ,struct:char;
  var pszUseDataCtrl_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseDataCtrl_Data:Array[Byte] = _ // for pszUseDataCtrl pointer ,struct:char;
  var pszWaitTimeWhenSysErrOccur_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszWaitTimeWhenSysErrOccur_Data:Array[Byte] = _ // for pszWaitTimeWhenSysErrOccur pointer ,struct:char;

    var pszUseThread_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseThread_Data:Array[Byte] = _ // for pszUseThread pointer ,struct:char;

    var pszTxSvrType_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSvrType_Data:Array[Byte] = _ // for pszTxSvrType pointer ,struct:char;
  var pszDBSvrName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBSvrName_Data:Array[Byte] = _ // for pszDBSvrName pointer ,struct:char;
  var pszDBSvrPort_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBSvrPort_Data:Array[Byte] = _ // for pszDBSvrPort pointer ,struct:char;
  var pszTxSvrName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSvrName_Data:Array[Byte] = _ // for pszTxSvrName pointer ,struct:char;
  var pszTxSvrPort_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSvrPort_Data:Array[Byte] = _ // for pszTxSvrPort pointer ,struct:char;
  var pszMainTxSvrName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMainTxSvrName_Data:Array[Byte] = _ // for pszMainTxSvrName pointer ,struct:char;
  var pszMainTxSvrPort_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMainTxSvrPort_Data:Array[Byte] = _ // for pszMainTxSvrPort pointer ,struct:char;
  var pszIsMainTxSvr_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszIsMainTxSvr_Data:Array[Byte] = _ // for pszIsMainTxSvr pointer ,struct:char;
  var pszUseMutltiTxSvr_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseMutltiTxSvr_Data:Array[Byte] = _ // for pszUseMutltiTxSvr pointer ,struct:char;
  var pszTxSvrRunAsServe_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSvrRunAsServe_Data:Array[Byte] = _ // for pszTxSvrRunAsServe pointer ,struct:char;
  var pszMaxThreadCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxThreadCount_Data:Array[Byte] = _ // for pszMaxThreadCount pointer ,struct:char;
  var pszDirectThreadCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDirectThreadCount_Data:Array[Byte] = _ // for pszDirectThreadCount pointer ,struct:char;
  var pszCreateThreadInternal_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCreateThreadInternal_Data:Array[Byte] = _ // for pszCreateThreadInternal pointer ,struct:char;
  var pszMaxWaitTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxWaitTime_Data:Array[Byte] = _ // for pszMaxWaitTime pointer ,struct:char;
  var pszTxSendTimeOut_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxSendTimeOut_Data:Array[Byte] = _ // for pszTxSendTimeOut pointer ,struct:char;
  var pszTxRecvTimeOut_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTxRecvTimeOut_Data:Array[Byte] = _ // for pszTxRecvTimeOut pointer ,struct:char;
  var pszMaxTTL_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxTTL_Data:Array[Byte] = _ // for pszMaxTTL pointer ,struct:char;

    var pszRmtQryQueRefreshInternal_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtQryQueRefreshInternal_Data:Array[Byte] = _ // for pszRmtQryQueRefreshInternal pointer ,struct:char;
  var pszRmtQryQueMaxErrTimes_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtQryQueMaxErrTimes_Data:Array[Byte] = _ // for pszRmtQryQueMaxErrTimes pointer ,struct:char;
  var pszRmtQryQueShowIdleInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtQryQueShowIdleInfo_Data:Array[Byte] = _ // for pszRmtQryQueShowIdleInfo pointer ,struct:char;
  var pszRmtQryQuePrefetchCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtQryQuePrefetchCount_Data:Array[Byte] = _ // for pszRmtQryQuePrefetchCount pointer ,struct:char;
  var pszRmtQryQueRetrLatestDays_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtQryQueRetrLatestDays_Data:Array[Byte] = _ // for pszRmtQryQueRetrLatestDays pointer ,struct:char;

    var pszReportQueRefreshInternal_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportQueRefreshInternal_Data:Array[Byte] = _ // for pszReportQueRefreshInternal pointer ,struct:char;
  var pszReportQueMaxErrTimes_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportQueMaxErrTimes_Data:Array[Byte] = _ // for pszReportQueMaxErrTimes pointer ,struct:char;
  var pszReportQueShowIdleInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportQueShowIdleInfo_Data:Array[Byte] = _ // for pszReportQueShowIdleInfo pointer ,struct:char;
  var pszReportQuePrefetchCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportQuePrefetchCount_Data:Array[Byte] = _ // for pszReportQuePrefetchCount pointer ,struct:char;

    var pszDownloadQueRefreshInternal_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDownloadQueRefreshInternal_Data:Array[Byte] = _ // for pszDownloadQueRefreshInternal pointer ,struct:char;
  var pszDownloadQueMaxErrTimes_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDownloadQueMaxErrTimes_Data:Array[Byte] = _ // for pszDownloadQueMaxErrTimes pointer ,struct:char;
  var pszDownloadQueShowIdleInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDownloadQueShowIdleInfo_Data:Array[Byte] = _ // for pszDownloadQueShowIdleInfo pointer ,struct:char;
  var pszDownloadQuePrefetchCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDownloadQuePrefetchCount_Data:Array[Byte] = _ // for pszDownloadQuePrefetchCount pointer ,struct:char;

    var pszRmtKeyQueRefreshInternal_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtKeyQueRefreshInternal_Data:Array[Byte] = _ // for pszRmtKeyQueRefreshInternal pointer ,struct:char;
  var pszRmtKeyQueMaxErrTimes_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtKeyQueMaxErrTimes_Data:Array[Byte] = _ // for pszRmtKeyQueMaxErrTimes pointer ,struct:char;
  var pszRmtKeyQueShowIdleInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtKeyQueShowIdleInfo_Data:Array[Byte] = _ // for pszRmtKeyQueShowIdleInfo pointer ,struct:char;
  var pszRmtKeyQuePrefetchCount_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRmtKeyQuePrefetchCount_Data:Array[Byte] = _ // for pszRmtKeyQuePrefetchCount pointer ,struct:char;

    var pszSvrInfoRefreshInterval_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSvrInfoRefreshInterval_Data:Array[Byte] = _ // for pszSvrInfoRefreshInterval pointer ,struct:char;
  var pszUseLogUserRequest_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseLogUserRequest_Data:Array[Byte] = _ // for pszUseLogUserRequest pointer ,struct:char;
  var pszOutSideIpHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszOutSideIpHead_Data:Array[Byte] = _ // for pszOutSideIpHead pointer ,struct:char;

    var pszBackSvrDBMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszBackSvrDBMap_Data:Array[Byte] = _ // for pszBackSvrDBMap pointer ,struct:char;	// 在清空映射表时保留以前的映射库ID
  } //RMTPARAMNAMESTRUCT;


  final val RMTPARAMNAME_LOCUNITCODE = "UnitCode"
  final val RMTPARAMNAME_RMTSVRPARAM = "RMT_SvrParam"
  final val RMTPARAMNAME_LOCDBMAP = "RMT_LocDBMap"
  final val RMTPARAMNAME_SVRDBMAP = "RMT_SvrDBMap"
  final val RMTPARAMNAME_IPFILTERS = "RMT_IPFilters"
  final val RMTPARAMNAME_REPORTDATFILTERS = "RMT_ReportDatFilters"
  final val RMTPARAMNAME_TXSVRINFO = "RMT_TxSvrInfo"
  final val RMTPARAMNAME_REQUIREDTPCARDTEXT = "RMT_RequiredTPCardTextItem"
  final val RMTPARAMNAME_REQUIREDLPCARDTEXT = "RMT_RequiredLPCardTextItem"
  final val RMTPARAMNAME_REQUIREDLPCASETEXT = "RMT_RequiredLPCaseTextItem"

  final val RMTPARAMNAME_IPMAPTABLE = "RMT_IPMapTable"

  final val RMTPARAMNAME_BACKSVRDBMAP = "RMT_BackSvrDBMap"




  //if leaved disk capacity is less than nMinDiskCapacity,
  //or and current database capacity is big than nMaxDbCapacity,
  //then delete all data that must be leave database at leave tKeepMinDay day
  final val RMTCHECKDB_FLAG_NOTCHECK = 0	//don't check db
  final val RMTCHECKDB_FLAG_MINDISKCAPACITY = 1	//if < min disk capacity, then check db
  final val RMTCHECKDB_FLAG_MAXDBCAPACITY = 2	//if > max db capacity, then check db
  final val RMTCHECKDB_FLAG_ANYONE = 3	//if reach any condition, then check db
  final val RMTCHECKDB_FLAG_ALL = 4	//if reach all condition, then check db

  class RMTCHECKDBSTRUCT extends AncientData
  {
    var tKeepMinDay:Int = _ ;			//day, default is 60
  var nMinDiskCapacity:Int = _ ;	//MB, default is 1024MB
  var nMaxDbCapacity:Int = _ ;		//default is 10000
  var nFlag:Byte = _ ;					//RMTCHECKDB_FLAG_XXX
  @Length(64-13)
  var bnRes:Array[Byte] = _ ;
  } //RMTCHECKDBSTRUCT;	//size is 64, check database condition

  final val RMTPARAM_LTCAND_HIT = 0	//hit finger
  final val RMTPARAM_LTCAND_FIXED = 1	//fixed finger
  final val RMTPARAM_LTCAND_ALL = 2	//all finger
  final val RMTPARAM_LTCAND_ONLYLIST = 3	//only get list
  final val RMTPARAM_LTCAND_ONLYRESULT = 4	//only result
  final val RMTPARAM_LTCAND_ONLYTEXT = 5	//only download text info

  final val RMTPARAM_TTCAND_ALL = 0	//all finger
  final val RMTPARAM_TTCAND_FIXED = 1	//fixed finger
  final val RMTPARAM_TTCAND_ONLYLIST = 2	//only cand list
  final val RMTPARAM_TTCAND_ONLYRESULT = 3	//only result
  final val RMTPARAM_TTCAND_ONLYTEXT = 4	//only download text info

  final val RMTPARAM_TLCAND_DEFAULT = 0	//default, get
  final val RMTPARAM_TLCAND_ONLYLIST = 1	//only list
  final val RMTPARAM_TLCAND_ONLYRESULT = 2	//only result
  final val RMTPARAM_TLCAND_ONLYTEXT = 3	//only admin info and textinfo

  final val RMTPARAM_LLCAND_DEFAULT = 0	//default, get
  final val RMTPARAM_LLCAND_ONLYLIST = 1	//only list
  final val RMTPARAM_LLCAND_ONLYRESULT = 2	//only result
  final val RMTPARAM_LLCAND_ONLYTEXT = 3	//only admin info and textinfo

  class RMTCLIENTQRYPARAMSTRUCT extends AncientData
  {
    var bTTOverWriteQryCandData:Byte = _ ;		//tt, overwrite query cand data?
  var bTLOverWriteQryCandData:Byte = _ ;		//tl, overwrite query cand data?
  var bLTOverWriteQryCandData:Byte = _ ;		//lt, overwrite query cand data?
  var bLLOverWriteQryCandData:Byte = _ ;		//ll, overwrite query cand data?
  var nTTMaxDownloadQryCandCnt:Int = _ ;	//tt,max download query canditem count
  var nTLMaxDownloadQryCandCnt:Int = _ ;	//tl,max download query canditem count
  var nLTMaxDownloadQryCandCnt:Int = _ ;	//lt,max download query canditem count
  var nLLMaxDownloadQryCandCnt:Int = _ ;	//ll,max download query canditem count
  var nTTGetQryCandMode:Byte = _ ;				//RMTPARAM_TTCAND_XXX
  var nTLGetQryCandMode:Byte = _ ;				//RMTPARAM_TLCAND_XXX
  var nLTGetQryCandMode:Byte = _ ;				//RMTPARAM_LTCAND_XXX
  var nLLGetQryCandMode:Byte = _ ;				//RMTPARAM_LLCAND_xxx
  @Length(22)
  var szLTQryCandFingerIndex:String = _ ;		//roll finger,plain finger,palm
  @Length(22)
  var szTTQryCandFingerIndex:String = _ ;
    var bWithCaseInfo:Byte = _ ;			//if true, download case relative with lp finger
  var bOnlyCheckQryCandDB:Byte = _ ;	//if false, check all database
  var bAddTCandToExfQue:Byte = _ ;		//if true, add tp cand into exf fifo queue
    @Length(128-71)
  var bnRes:Byte = _
  } //RMTCLIENTQRYPARAMSTRUCT;//size is 128, rmt client query config

  final val RMTPARAM_DELKEY_NOTDELETE = 0
  final val RMTPARAM_DELKEY_DELATONCE = 1
  final val RMTPARAM_DELKEY_DELBYCONDITION = 2

  final val RMTPARAM_IMGOPT_USECPR = 0
  final val RMTPARAM_IMGOPT_USEIMG = 1
  final val RMTPARAM_IMGOPT_USEALL = 2

  final val RMTPARAM_MUSTHASFLAG_NOTHING = 0x00
  final val RMTPARAM_MUSTHASFLAG_IMG = 0x01
  final val RMTPARAM_MUSTHASFLAG_MNT = 0x02
  final val RMTPARAM_MUSTHASFLAG_TEXT = 0x04

  final val RMTPARAM_UPDATEFLAG_TEXT = 0x01	//text items
  final val RMTPARAM_UPDATEFLAG_IMG = 0x02	//roll/tplain finger, palm image
  final val RMTPARAM_UPDATEFLAG_MNT = 0x04	//roll/tplain finger, palm mnt
  final val RMTPARAM_UPDATEFLAG_OTHER = 0x08	//plain, card...
  final val RMTPARAM_UPDATEFLAG_FACE = 0x10	//face
  final val RMTPARAM_UPDATEFLAG_HITHISTORY = 0x20	//hit history

  //options of transmit data
  class RMTTRANSMITGLOBALOPT extends AncientData
  {
    var nZipMethod:Byte = _ ;			//finger image zip method, GRMTZIP_METHOD_XXX
  var nZipRatio:Byte = _ ;			//finger image zip ratio
  var nImageOpt:Byte = _ ;			//RMTPARAM_IMGOPT_XXX
  var nMustHasFlag:Short = _ ;	//RMTPARAM_MUSTHASFLAG_XXX, REPORT OR DOWNLAOD MUST HAS ITEM
  var nUpdateFlag:Short = _ ;		//RMTPARAM_UPDATEFLAG_XXX, reprot or download update flag
  var bAddTCardToExfQue:Byte = _ ;	//if true, add tp cand into exf fifo queue
  @Length(8)
  var bnRes:Array[Byte] = _ ;
  } //RMTTRANSMITGLOBALOPT;	//size is 16

  class RMTTRANSMITQUALITYOPT extends AncientData
  {
    var nUnQualifiedFingerCount:Byte = _ ;	//unqualified finger count, if 0, do nothing
  var nTrustlessMntRatio:Byte = _ ;			//trustless mnt ratio, if all >= this, it is unqualified
  var nMinMntCount:Short = _ ;			//if less this, it is unqualified
    @Length(32-4)
  var bnRes:Byte = _
  } //RMTTRANSMITQUALITYOPT;	//size is 32

  //client paramter
  class RMTCLIENTPARAMSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(12)
    var szMagic:String = _ ;

    var stReportTPOpt = new RMTTRANSMITGLOBALOPT;		//options of report tp
  var stReportLPOpt = new RMTTRANSMITGLOBALOPT;		//options of report lp
  var stDownloadTPOpt = new RMTTRANSMITGLOBALOPT;	//options of download tp
  var stDownloadLPOpt = new RMTTRANSMITGLOBALOPT;	//options of download lp
  var stDownloadTPQryCandOpt = new RMTTRANSMITGLOBALOPT;	//options of download query tp cand
  var stDownloadLPQryCandOpt = new RMTTRANSMITGLOBALOPT;	//options of download query lp cand

    var nDelKeyIfSendFinished:Byte = _ ;		//RMTPARAM_DELKEY_XXX
  var bDelFinishedQueRec:Byte = _ ;
    var bDelErrorQueRec:Byte = _ ;

    var tLastGetMsgTime:Int = _ ;	//last get message time
  var tLastGetCmdTime:Int = _ ;	//last get asyn cmd time
  var tLastQualChkTime:Int = _ ;//last check quality check queue time
  @Length(1)
  var bnRes0:Array[Byte] = _ ;			//to here is 128

    // added by xxf for tp finger quality control [7/5/2005]
    var stRTTPQOpt = new RMTTRANSMITQUALITYOPT;		//report tp finger quality option
    @Length(512-128-32)
  var bnRes:Byte = _

    var stQry = new RMTCLIENTQRYPARAMSTRUCT;	//query, size is 128

    var stCheckReportQue = new RMTCHECKDBSTRUCT;	//check report queue condition
  @Length(64)
  var bnResCheckReportQue:Array[Byte] = _ ;
    var stCheckQryCandTPDB = new RMTCHECKDBSTRUCT;	//check query cand database condition
  var stCheckQryCandLPDB = new RMTCHECKDBSTRUCT;	//check query cand database condition
  var stCheckTransmitTPDB = new RMTCHECKDBSTRUCT;//check transmit database condition
  var stCheckTransmitLPDB = new RMTCHECKDBSTRUCT;//check transmit database condition
  } //RMTCLIENTPARAMSTRUCT;	//size is 1024

  final val RMTSVRPARAM_FLAG_ALL = 0x00
  final val RMTSVRPARAM_FLAG_CLIENT = 0x01
  final val RMTSVRPARAM_FLAG_AUTOQRY = 0x02
  final val RMTSVRPARAM_FLAG_DB = 0x04

  final val RMTSVRPARAM_FLAGEX_CLIENT_ALL = 0x00
  final val RMTSVRPARAM_FLAGEX_CLIENT_TRANSMIT = 0x01
  final val RMTSVRPARAM_FLAGEX_CLIENT_QUERY = 0x02
  final val RMTSVRPARAM_FLAGEX_CLIENT_CHECKDB = 0x04
  final val RMTSVRPARAM_FLAGEX_CLIENT_LASTGETMSGTIME = 0x08
  final val RMTSVRPARAM_FLAGEX_CLIENT_LASTGETCMDTIME = 0x10
  final val RMTSVRPARAM_FLAGEX_CLIENT_LASTQUALCHKTIME = 0x20

  /*
  class RMTSVRPARAMSTRUCT extends AncientData
  {
    var stClient = new RMTCLIENTPARAMSTRUCT;		//size is 1024
  @Length(4)
  var stAutoQry:Array[RMTCLIENTAUTOQRYSTRUCT] = _;	//size is 512 * 4, TT TL LT LL
  var stDBID = new RMTLOCALDBIDSTRUCT;			//size is 128
  var nFlag:Byte = _ ;
    var nFlagEx:Byte = _ ;
    @Length(5*1024 - 128 - 2)
    var bnRes:Byte = _
  } //RMTSVRPARAMSTRUCT;	//size is 10 * 1024
  */

  // add for database map between shengting and shiju [10/9/2004]
  final val RMTDBMAP_FLAG_DEFAULTREPORTDB = 0x01
  final val RMTDBMAP_FLAG_DEFAULTDOWNLOADDB = 0x02
  final val RMTDBMAP_FLAG_DEFAULTMATCHDB = 0x04

  class RMTDBMAPSTRUCT extends AncientData
  {
    @Length(32)
    var szUnitCode:String = _ ;		//which server
  @Length(32)
  var szDispName:String = _ ;		//disp name
  var nRealDBID:Short = _ ;		//real dbid
  var nMappedDBID:Short = _ ;		//mapped id, rand number
  var bIsLat:Byte = _ ;				//is latent db
  var bCanBeMatched:Byte = _ ;		//can be matched?
  var nFlag:Byte = _ ;			//RMTDBMAP_FLAG_XXX
  var nFlagEx:Byte = _ ;
    @Length(64-8)
    var bnRes:Byte = _
  } //RMTDBMAPSTRUCT;	//size is 128

  /*
  typedef enum tagRMTTHREADSEQSTRUCT
  {
    RMTTDSEQ_SCHEDULE = 0,	//schedule thread
    RMTTDSEQ_ACCEPT,		//tcp accept thread
    RMTTDSEQ_COMMAND,		//udp accept thread
    RMTTDSEQ_TRANSCFG,		//auto transmit config thread
    RMTTDSEQ_REPORT,		//report data thread
    RMTTDSEQ_DOWNLOAD,		//download data thread
    RMTTDSEQ_RMTQRY,		//send rmt query thread
    RMTTDSEQ_AUTODLQRY,		//auto download query thread
    RMTTDSEQ_LIVEUPDATE,	//live update thread
    RMTTDSEQ_QRYECHO,		//auto query echo thread
    RMTTDSEQ_GETMSG,		//get message thread
    RMTTDSEQ_CHECKDB,		//check database thread
    RMTTDSEQ_GETASYNCMD,	//get asyn command thread
    RMTTDSEQ_GETDBMAP,		//get database map
    RMTTDSEQ_SENDASYNCMD,	//send asyn command thread
    RMTTDSEQ_LIVESCANUSED,	//live scan used thread
    RMTTDSEQ_FPX_RPTTPLP,	//fpx report tplp thread
    RMTTDSEQ_FPX_RPTQUERY,	//fpx reprot query
    RMTTDSEQ_INFOSYNC,		//info sync thread
    RMTTDSEQ_DLQRYRESULT,	// Download query result from direct server
    RMTTDSEQ_LIVESCANRMTQRY,// send livescan rmt query thread
    RMTTDSEQ_LAST			//last thread, just end
  }RMTTHREADSEQSTRUCT;
  */

  final val RMTIPFILTER_FLAG_USEFILTER = 0x01
  final val RMTIPFILTER_FLAG_USEALLOWIP = 0x02
  class RMTSVRIPFILTERS extends AncientData
  {
    var cbSize:Int = _ ;			//size of struct
  var nFilterSize:Int = _ ;		//length of struct and ip
  var nIPCount:Short = _ ;		//allow ip count
  @Length(17)
  var bnRes:Array[Byte] = _ ;
    var nFlag:Byte = _ ;				//RMTIPFILTER_FLAG_XXX
  @Length(4)
  var bnNetIP:Array[Byte] = _ ;
  } //RMTSVRIPFILTERS;	//size is 32

  final val RMTRPTDATFILTER_FLAG_USEMINMNTCOUNT = 0x01
  final val RMTRPTDATFILTER_FLAG_USESAMEDIGITCOUNT = 0x02

  final val RMTRPTDATFILTER_RMODE_DELSKEY = 0	//add after delete source record
  final val RMTRPTDATFILTER_RMODE_USETMPDB = 1	//use temp database
  final val RMTRPTDATFILTER_RMODE_USETMPKEY = 2	//add after change source record's key

  class RMTREPORTDATFILTERS extends AncientData
  {
    var cbSize:Int = _ ;			//size of struct
  var nMinMntCount:Short = _ ;	//if lp finger count<nMinMntCount, then cast away
  //if ==0, then do nothing
  var nSameDigitCount:Byte = _ ;	//key and unitcode is belong to a place
  var nFlag:Byte = _ ;				//RMTRPTDATFILTER_FLAG_XXX
  var nReplaceMode:Byte = _ ;		//RMTRPTDATFILTER_RMODE_XXX, replace record mode
  @Length(256-9)
  var bnRes:Array[Byte] = _ ;
  } //RMTREPORTDATFILTERS;	//size is 256

  //remote server infor struct, saved in parameter table of database
  class RMTSVRINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			//size of this struct
  @Length(16)
  var bnUUID:Array[Byte] = _ ;			//server uuid
  @Length(16)
  var bnServerIP:Array[Byte] = _ ;		//server IP, now is int
  var nServerPort:Int = _ ;		//server port
  var nOsType:Int = _ ;			//os type
  var nTotalMemory:Int = _ ;	//total memory MB
  var nFreeMemory:Int = _ ;		//free memory MB
  var nMaxThread:Int = _ ;		//allow max thread count
  var nCurThread:Int = _ ;		//current thread count
  var nCpuNum:Int = _ ;			//cpu number
  var nCpuType:Int = _ ;		//cpu type
  var nCpuSpeed:Int = _ ;		//cpu speed Mhz
  var nCpuUseRate:Byte = _ ;		//cpu use rate
  var nMemUseRate:Byte = _ ;		//mem use rate
  var nThdUseRate:Byte = _ ;		//thread rate
  var bSupportMultiSvr:Byte = _ ;	//support multi tx svr
  var bIsMainTxSvr:Byte = _ ;		//is main tx svr
  @Length(51)
  var bnRes0:Array[Byte] = _ ;			//to here is 128

    @Length(16)
    var bnMainTxSvrIP:Array[Byte] = _ ;	//main tx svr ip
  var nMainTxSvrPort:Int = _ ;	//main tx svr port
  @Length(108)
  var bnRes1:Array[Byte] = _ ;		//to here is 256

    var bHasRmtQueryLic:Byte = _ ;	//has rmt query license	on txsvr computer
  var bHasRmtClientLic:Byte = _ ;	//has rmt client license on txsvr computer
    @Length(1024 - 256 - 2)
  var bnRes2:Byte = _ ;	//reserved
  } //RMTSVRINFOSTRUCT;	//size is 1024


  /*
  final val RMTRTI_TYPE_TPCARD = RMTDATITEMCTRL_ITEMTYPE_TPCARD	//tpcard
  final val RMTRTI_TYPE_LPCARD = RMTDATITEMCTRL_ITEMTYPE_LPCARD	//lpcard
  final val RMTRTI_TYPE_LPCASE = RMTDATITEMCTRL_ITEMTYPE_LPCASE	//lpcase
  */

  class RMTREQUIREDTEXTITEMSTRUCT extends AncientData
  {
    @Length(32)
    var szName:String = _ ;	//text item name
  @Length(32)
  var bnRes:Array[Byte] = _ ;	//reserved
  } //RMTREQUIREDTEXTITEMSTRUCT;

  //inner and outer ip map [12/1/2005]
  class RMTIPMAPTABLE extends AncientData
  {
    var cbSize:Int = _ ;		//size of this strct
  var bIsIPv6:Byte = _ ;		//is ip v6
  @Length(27)
  var bnRes0:Array[Byte] = _ ;		//reserved
  @Length(16)
  var bnInnerIP:Array[Byte] = _ ;	//inner net ip
  @Length(16)
  var bnOuterIP:Array[Byte] = _ ;	//outer net ip
  } //RMTIPMAPTABLE;	//ip map table

  //  [3/13/2006]
  final val RMTFLIBADD_FLAG_REPLACEREC = 0x01	//replace record
  final val RMTFLIBADD_FLAG_ISTEST = 0x02	//only test add operate
  class RMTFLIBADDSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;
    var bSupportQualChk:Byte = _ ;
    var bUseRetQualChk:Byte = _ ;		//[OUT] return value
  var nRetQualChkRes:Byte = _ ;		//[OUT] return value
  var nFlag:Byte = _ ;				//add option, RMTFLIBADD_FLAG_XXX
  @Length(8-SID_SIZE)
  var bnRes0:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nQualChkSid:String = _ ;	//return value
  @Length(8-SID_SIZE)
  var bnRes1:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nTQrySid:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes2:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nLQrySid:String = _ ;
    @Length(4)
    var stMatchedTPDB:Array[GADBIDSTRUCT] = _;
    @Length(4)
    var stMatchedLPDB:Array[GADBIDSTRUCT] = _;
    var nQryDBID:Short = _ ;
    var nQryTID:Short = _ ;
    var nQualChkRes:Byte = _ ;		//[IN] record quality check result
  var bUpdateCaseInfo:Byte = _ ;	// 在上传现场卡片后，更新关联案件中的现场卡片列表信息
  // 在目前的TxSvr中，当往一个已经上传的案件中增加卡片时，
  // 该新增卡片上传时没有被关联到已有的案件中
  @Length(256-70)
  var bnRes3:Array[Byte] = _ ;
  } //RMTFLIBADDSTRUCT;




}

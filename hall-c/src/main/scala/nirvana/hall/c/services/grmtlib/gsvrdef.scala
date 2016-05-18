package nirvana.hall.c.services.grmtlib

import nirvana.hall.c.annotations.{Length, IgnoreTransfer}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gathrdop.GAFIS_CRITSECT
import nirvana.hall.c.services.gloclib.gaqryque.GAQRYCAND_MULTIFILTER
import nirvana.hall.c.services.grmtlib.grmtpara.{RMTIPMAPTABLE, RMTREPORTDATFILTERS, RMTSVRIPFILTERS, RMTSVRINFOSTRUCT}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
object gsvrdef {

//  typedef enum tagRMTSVRTYPE
//  {
val     RMTSVR_TYPE_DEFAULT	= 0	//default server type
val     RMTSVR_TYPE_PAICHUSUO = 1		//pai chu suo
val     RMTSVR_TYPE_FENJU = 2			//fen ju
val     RMTSVR_TYPE_SHIJU = 3			//shi ju
val     RMTSVR_TYPE_SHENGTING = 4		//sheng ting
val     RMTSVR_TYPE_GONGANBU = 5		//gong an bu
//  }RMTSVRTYPE;

  class RMTSVRTHREADSTRUCT extends AncientData
  {
    var cs = new GAFIS_CRITSECT;
    var nCurCount:Int = _;
    var nCurSvrCount:Int = _;
    var nMaxSvrCount:Int = _;
    var nDirectSvrCount:Int = _;	//if cur < this value, then direct connect to dbsvr, don't need wait
  var nInterval:Int = _;		//millisecond
  var nMaxWaitTime:Int = _;	//millisecond
  } //RMTSVRTHREADSTRUCT;

  class RMTSVRITEMSTRUCT extends AncientData
  {
    var cs = new GAFIS_CRITSECT;			//cirtsect
  var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:void;		//item data
  var nCount:Int = _;		//item count
  var bChanged:Int = _;	//changed ?
  var bEnter:Int = _;		//entered?
  var nRefreshTime:Int = _;	//second
  var nMaxErrTimes:Int = _;	//max error times
  var nPrefetchCount:Int = _;	//prefetch count
  var bShowIdleInfo:Int = _;	//show idle info
  var nLatestDays:Int = _;	//default 30 days
  } //RMTSVRITEMSTRUCT;

  class RMTSVRSTRUCT extends AncientData
  {
    var nPort:Int = _;			//tx server port
//  var nTcpSocket = new SOCKET;		//tcp socket
//  var nUdpSocket = new SOCKET;		//udp socket

    var stItemServer = new RMTSVRITEMSTRUCT;		//server config
  var stItemRouter = new RMTSVRITEMSTRUCT;		//router config
  var stItemTransmitCfg = new RMTSVRITEMSTRUCT;	//auto transmit config
  var stItemQryCtrl = new RMTSVRITEMSTRUCT;		//rmt query control
  var stItemProxyDataCtrl = new RMTSVRITEMSTRUCT;//proxy query and data control
  var stItemAutoQry = new RMTSVRITEMSTRUCT;	//auto query control
  var stItemEcho = new RMTSVRITEMSTRUCT;		//rmt query echo config
  var stItemRmtDB = new RMTSVRITEMSTRUCT;	//remote user db config
  var stItemDB = new RMTSVRITEMSTRUCT;		//database list in db server
  var stItemUser = new RMTSVRITEMSTRUCT;		//rmt user list in db server
  var stItemDataCtrl = new RMTSVRITEMSTRUCT;		//data control
  var stItemReportQue = new RMTSVRITEMSTRUCT;		//upload queue
  var stItemDownloadQue = new RMTSVRITEMSTRUCT;		//download queue
  var stItemKeyQue = new RMTSVRITEMSTRUCT;			//key queue
  var stItemWorkingQryQue = new RMTSVRITEMSTRUCT;	//query queue(working status)
  var stItemReadyQryQue = new RMTSVRITEMSTRUCT;		//query queue(ready status)
  var stItemFeedbackQryQue = new RMTSVRITEMSTRUCT;	//query queue(feedback status)
  var stItemSvrInfo = new RMTSVRITEMSTRUCT;			//all sub tx server
  var stItemFinishedQry = new RMTSVRITEMSTRUCT;		//[3/20/2006] finished query
  var stItemCacheDBIDTID = new RMTSVRITEMSTRUCT;		//[11/24/2006]cache dbid tid
  var stItemFatalErr = new RMTSVRITEMSTRUCT;			//[1/4/2007]fatal err list from direct upper server

    var stItemDlQryResultQue = new RMTSVRITEMSTRUCT;	// [2008-04-12], download query result from direct server

    var stItemLiveScanReadQryQue = new RMTSVRITEMSTRUCT;	// added by beagle for livescan remote query to upper upper server
  var stItemLiveScanWorkQryQue = new RMTSVRITEMSTRUCT;

    var stSvrInfo = new RMTSVRINFOSTRUCT;		//local server info
  var stThread = new RMTSVRTHREADSTRUCT;		//rmtsvr thread count
    /*
  var stDialCon = new RMTDIALCONNECTSTRUCT;		//dial connect

    var stParam = new RMTSVRPARAMSTRUCT;		//local and proxy some param
  var stLogReq = new RMTSVRLOGSTRUCT;		//log rmt user request
  var stErrList = new RMTSIMPERRLIST;
  */
    var pstIPFilters_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstIPFilters_Data:Array[RMTSVRIPFILTERS] = _ // for pstIPFilters pointer ,struct:RMTSVRIPFILTERS;	//ip filters
  var stRptDatFilters = new RMTREPORTDATFILTERS;	//report data filters
  var pstCandFilter_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCandFilter_Data:Array[GAQRYCAND_MULTIFILTER] = _ // for pstCandFilter pointer ,struct:GAQRYCAND_MULTIFILTER;	//global cand filter [1/30/2007]

    var pstIPMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstIPMap_Data:Array[RMTIPMAPTABLE] = _ // for pstIPMap pointer ,struct:RMTIPMAPTABLE;		//ip map table
  var nIPMapCount:Int = _;	//ip map count

    var csDownloadQry = new GAFIS_CRITSECT;
    var csIPFilters = new GAFIS_CRITSECT;
    var csRptDatFilters = new GAFIS_CRITSECT;
    var csIPMap = new GAFIS_CRITSECT;
    var csRmtQryQue = new GAFIS_CRITSECT;	//lock retr query queue
  var csCandFilter = new GAFIS_CRITSECT;	// cand filter [1/30/2007]

//    var hMainSvrCon = new HGNETCONNECTIONOBJECT;	//main svr connection object
//  var hCon = new HGNETCONNECTIONOBJECT;			//handle, connect database server
  var bIsValidDBSvr:Int = _;	//special dbserver is valid now

    @Length(16)
    var bnSvrUUID:Array[Byte] = _ ;		//server uuid
  @Length(16)
  var szUnitCode:String = _ ;		//unitcode of local database server
  @Length(16)
  var szUserName:String = _ ;		//user name
  @Length(48)
  var szDBServerName:String = _ ;	//database server name
  @Length(16)
  var szDBServerIP:String = _ ;	//database server IP
  var nDBServerPort:Int = _;		//database server port

    var nTxSvrType:Int = _;		//#0:default, 1:paichusuo, 2:fenju, 3:shiju, 4:shengting, 5:gonganbu
  var bIsMainTxSvr:Int = _;			//is main tx server
  var bUseMultiTxSvr:Int = _;			//use multi tx server
  @Length(48)
  var szMainTxSvrName:String = _ ;	//main tx server name
  @Length(16)
  var szMainTxSvrIP:String = _ ;		//main tx server ip
  @Length(16)
  var szOutSideIpHead:String = _ ;	//outside ip head
  var nMainTxSvrPort:Int = _;			//main tx server port

    var nMaxTTL:Int = _;	// max ttl, default is 8 [9/20/2004]

    var bDownloadQry:Byte = _ ;		//if true, download remote query
  var bGetMessage:Byte = _ ;		//if true, download message
  var bGetDBMapTable:Byte = _ ;		//if true, get database map table on server
  var bCheckDatabase:Byte = _ ;		//if true, check database
  var bLiveUpdate:Byte = _ ;		//if true, get new version gafis system file from hot server
  var bGetAsynCmd:Byte = _ ;		//if true, get asyn command
  var bPorcessAsynCmd:Byte = _ ;	//if true, process asyn command
  var bSendAsynCmd:Byte = _ ;		//if true, send asyn command
  var bIPFiltersChanged:Byte = _ ;	//if true, update IPFilters
  var bRptDatFiltersChanged:Byte = _ ;	//if true, update report data filters
  var bRefreshLiveScanUsedThread:Byte = _ ;	//if true, refresh live scan used thread
  var bRefreshIPMap:Byte = _ ;		//if true, refresh ip map
  var bRefreshFpxRptTPLP:Byte = _ ;	//if true, refresh fpx report tp lp
  var bRefreshFpxRptQuery:Byte = _ ;//if true, refresh fpx report query
  var bRefreshInfoSyncThread:Byte = _ ;	//if true, refresh info sync thread
  var bRefreshCandFilter:Byte = _ ;		//if true, refresh cand filter

    var bEnableFinger:Byte = _ ;	//local database server enable finger or palm
  var bEnablePalm:Byte = _ ;

    //	char	bDlQryResult;	// if true, download query result from direct server
    //	char	nReserved[5];

    var nImgStd:Int = _;		//image standard

    var bStopRmtSvr:Int = _;		//if true, normal stop tx server
  var bTerminateRmtSvr:Int = _;	//if true, terminate tx server

    var bIsSetMode:Int = _;			//if true, only can set rmtconfig
  var bIsDebugMode:Int = _;		//if true, can dynamic adjust parameter's value
  var bUseDataCtrl:Int = _;		//if true, use data control

    var nWaitTimeWhenSysErrOccur:Int = _;	//default is 60s

    // [11/18/2005] for live scan at shanghai
    var nMntFormat:Int = _;
    var nLiveScanMinImageQuality:Int = _;
    var nLiveScanGoodImageQuality:Int = _;
    var nLiveScanWillEditFinger:Int = _;
    // [1/4/2006] database type
    var nDBType:Int = _;
    // used by livescan [3/8/2006]
    var nTTAutoMergeCandScore:Int = _;
    var nLeastScoreTT:Int = _;
    // [3/14/2006]
    var bQueTTSearchAfterEdit:Int = _;
    var bQueTLSearchAfterEdit:Int = _;
    var bQueLTSearchAfterEdit:Int = _;
    var bQueLLSearchAfterEdit:Int = _;

    // [6/6/2007]
    var bSearchTPlainFinger:Int = _;

    // [2008-04-16]
    var bFileFastCommit:Int = _;

    //!< 增加socket的超时参数：单位为秒，取值范围为[10, 3600]，缺省为600		Added On Aug. 7, 2012 in 南昌
    var nRmtSendTimeOut:Int = _;
    var nRmtRecvTimeOut:Int = _;

  } //RMTSVRSTRUCT;

  //#define RMTSVR_PARAMNAME_TTAUTOMERGECANDSCORE		"TTAutoMergeCandScore"
  final val RMTSVR_PARAMNAME_FILEFASTCOMMIT = "FileFastCommit"


}

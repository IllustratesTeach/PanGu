package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.grmtlib.grmtdef._
import nirvana.hall.c.services.grmtlib.grmtpara._
import nirvana.hall.c.services.grmtlib.gsvrdef.{RMTSVRITEMSTRUCT, RMTSVRSTRUCT}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait gsvrfunc {
  lazy val g_pstRmtVar =GAFIS_RMTSVR_Init()
    
    def GAFIS_RMTSVR_Init():RMTSVRSTRUCT=
    {
      val g_pstRmtVar = new RMTSVRSTRUCT

      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemServer, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemRouter, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemTransmitCfg, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemQryCtrl, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemProxyDataCtrl, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemAutoQry, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemEcho, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemRmtDB, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemDB, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemUser, 0, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemDataCtrl, 0, 0);

      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemSvrInfo, RMTITEM_TYPE_SVRINFO, 0);

      g_pstRmtVar.stItemTransmitCfg.nRefreshTime = 30;
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemReportQue, RMTITEM_TYPE_REPORTQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemDownloadQue, RMTITEM_TYPE_DOWNLOADQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemKeyQue, RMTITEM_TYPE_KEYQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemWorkingQryQue, RMTITEM_TYPE_QUERYQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemReadyQryQue, RMTITEM_TYPE_QUERYQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemFeedbackQryQue, RMTITEM_TYPE_QUERYQUEUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemFinishedQry, RMTITEM_TYPE_FINISHEDQRYQUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemCacheDBIDTID, RMTITEM_TYPE_CACHEDBIDTID, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemFatalErr,	RMTITEM_TYPE_FATALERRLIST, 0);

      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemDlQryResultQue, RMTITEM_TYPE_DLQRYRESULTQUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemLiveScanReadQryQue, RMTITEM_TYPE_LIVESCANQRYQUE, 0);
      GAFIS_RMTSVR_InitItem(g_pstRmtVar.stItemLiveScanWorkQryQue, RMTITEM_TYPE_LIVESCANQRYQUE, 0);

      /*
      //	GAFIS_RMTSVR_HGNETCONLIST_Init(g_pstRmtVar.stConList);
      GAFIS_InitCritSect(g_pstRmtVar.csDownloadQry);
      GAFIS_InitCritSect(g_pstRmtVar.stThread.cs);
      GAFIS_InitCritSect(g_pstRmtVar.stDialCon.cs);
      GAFIS_InitCritSect(g_pstRmtVar.csIPFilters);
      GAFIS_InitCritSect(g_pstRmtVar.csRptDatFilters);
      GAFIS_InitCritSect(g_pstRmtVar.csIPMap);
      GAFIS_InitCritSect(g_pstRmtVar.csRmtQryQue);
      GAFIS_InitCritSect(g_pstRmtVar.csCandFilter);
      */
      //	g_pstRmtVar.bDownloadQry	= 1;
      //	g_pstRmtVar.bCheckDatabase= 1;
      //	g_pstRmtVar.bGetMessage	= 1;
      //	g_pstRmtVar.bLiveUpdate	= 1;
      //	g_pstRmtVar.bReportStatInfo= 1;
      //	g_pstRmtVar.bGetDBMapTable= 1;
/*
      //!< 获取超时参数：
      nValue = 0;
      if ( GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszTxSendTimeOut, &nValue) > 0 )
      {
        g_pstRmtVar.nRmtSendTimeOut = GBASE_LimitData(10, 3600, nValue);
      }
      else g_pstRmtVar.nRmtSendTimeOut = 600;
      if ( GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszTxRecvTimeOut, &nValue) > 0 )
      {
        g_pstRmtVar.nRmtRecvTimeOut = GBASE_LimitData(10, 3600, nValue);
      }
      else g_pstRmtVar.nRmtRecvTimeOut = 600;

      //get txsver type
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszTxSvrType, g_pstRmtVar.nTxSvrType);

      //get is main txsvr
      g_pstRmtVar.bIsMainTxSvr = 1;
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszIsMainTxSvr, g_pstRmtVar.bIsMainTxSvr);
      if( g_pstRmtVar.bIsMainTxSvr )
      {
        GAFIS_RMTLIB_PARAMETER_GetDBServerNameAndPort(g_pstRmtVar.szDBServerName, g_pstRmtVar.nDBServerPort);
        if( g_pstRmtVar.szDBServerName[0] == '\0' )	strcpy(g_pstRmtVar.szDBServerName, "localhost");
      }
      else
      {
        GAFIS_RMTLIB_PARAMETER_GetMainTxServerNameAndPort(g_pstRmtVar.szMainTxSvrName, g_pstRmtVar.nMainTxSvrPort);
        if( g_pstRmtVar.szMainTxSvrName[0] == '\0' )
        {
          GAFIS_GAFISERR_SET(GAFISERR_RMT_PARAM_NOMAINTXSVR, 0, 0);
          return -1;
        }
        if( GAFIS_RMTLIB_UTIL_HostNameIsSelf(g_pstRmtVar.szMainTxSvrName) )
        {
          GAFIS_GAFISERR_SET(GAFISERR_RMT_PARAM_MAINTXSVRISSELF, 0, 0);
          return -1;
        }
      }
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszUseMutltiTxSvr, g_pstRmtVar.bUseMultiTxSvr);
      GAFIS_RMTLIB_PARAMETER_GetTXServerNameAndPort(NULL, g_pstRmtVar.nPort);
      g_pstRmtVar.nTcpSocket = AFISINVALIDSOCKET;
      g_pstRmtVar.nUdpSocket = AFISINVALIDSOCKET;

      //get max thread count and create thread interval
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszMaxThreadCount, g_pstRmtVar.stThread.nMaxSvrCount);
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszDirectThreadCount, g_pstRmtVar.stThread.nDirectSvrCount);
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszCreateThreadInternal, g_pstRmtVar.stThread.nInterval);
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszMaxWaitTime, & g_pstRmtVar.stThread.nMaxWaitTime);
      if( g_pstRmtVar.stThread.nMaxSvrCount == 0 )	g_pstRmtVar.stThread.nMaxSvrCount = 64;
      if( g_pstRmtVar.stThread.nInterval == 0 )	g_pstRmtVar.stThread.nInterval = 10;	//10 millisecond
      if( g_pstRmtVar.stThread.nMaxWaitTime == 0 )	g_pstRmtVar.stThread.nMaxWaitTime = 30000;	//30s

      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszMaxTTL, g_pstRmtVar.nMaxTTL);
      if( g_pstRmtVar.nMaxTTL == 0 )	g_pstRmtVar.nMaxTTL = 8;

      nValue = 0;
      GAFIS_RMTLIB_PARAMETER_GetValueInt(	g_stRmtParamName.pszUseLogUserRequest, &nValue);
      if( nValue )	GAFIS_RMTSVRLOG_Init(g_pstRmtVar.stLogReq);

      GAFIS_RMTLIB_SIMPERRLIST_Init(g_pstRmtVar.stErrList);

      //default use data ctrl
      g_pstRmtVar.bUseDataCtrl = 1;
      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszUseDataCtrl, g_pstRmtVar.bUseDataCtrl);

      g_pstRmtVar.nWaitTimeWhenSysErrOccur = 60;	//s
      GAFIS_RMTLIB_PARAMETER_GetValueInt( g_stRmtParamName.pszWaitTimeWhenSysErrOccur,
        g_pstRmtVar.nWaitTimeWhenSysErrOccur
      );
      if( g_pstRmtVar.nWaitTimeWhenSysErrOccur < 10 )
        g_pstRmtVar.nWaitTimeWhenSysErrOccur = 10;

      GAFIS_RMTLIB_PARAMETER_GetValueInt(g_stRmtParamName.pszUseSetMode, g_pstRmtVar.bIsSetMode);

      //get tx server uuid
      GAFIS_RMTSVR_GetTxSvrUUID(g_pstRmtVar.bnSvrUUID);

      //get outside ip head
      GAFIS_RMTLIB_PARAMETER_GetValue(g_stRmtParamName.pszOutSideIpHead, g_pstRmtVar.szOutSideIpHead);

      UTIL_ACCEPTCLIENTQUEUE_Init();
      UTIL_CLICONNECTSTTINFO_Init();
      //	UTIL_NETCONNECTREQSTATINFO_Init(&g_stRmtReqStatInfo);
*/
      return g_pstRmtVar;
    }
  val  RMTITEM_TYPE_QUERYQUEUE		= 1
  val  RMTITEM_TYPE_REPORTQUEUE	= 2
  val  RMTITEM_TYPE_DOWNLOADQUEUE = 	3
  val  RMTITEM_TYPE_KEYQUEUE		= 4
  val  RMTITEM_TYPE_SVRINFO	=	5
  val  RMTITEM_TYPE_FINISHEDQRYQUE	= 6
  val  RMTITEM_TYPE_CACHEDBIDTID	= 7
  val  RMTITEM_TYPE_FATALERRLIST	= 8
  val  RMTITEM_TYPE_DLQRYRESULTQUE =	9
  val  RMTITEM_TYPE_LIVESCANQRYQUE =	10
  def GAFIS_RMTSVR_InitItem(pItem:RMTSVRITEMSTRUCT, nItemType:Int, bChanged:Int)
  {
    var pszMaxErrTimes:String = null
    var pszRefreshTime:String = null
    var pszShowIdleInfo:String = null
    var pszPrefetchCount:String = null
    var pszLatestDays:String = null


    pItem.bChanged = bChanged;
    pItem.nMaxErrTimes = RMTSVRITEM_MAXERRTIMES;
    pItem.nRefreshTime = RMTSVRITEM_REFRESHTIMES;
    pItem.nPrefetchCount = RMTSVRITEM_PREFETCHCOUNT;
    pItem.bShowIdleInfo= 0;
    pItem.nLatestDays= 90;

    nItemType match {
      case RMTITEM_TYPE_QUERYQUEUE | RMTITEM_TYPE_FINISHEDQRYQUE =>
        pszMaxErrTimes = g_stRmtParamName.pszRmtQryQueMaxErrTimes;
        pszRefreshTime = g_stRmtParamName.pszRmtQryQueRefreshInternal;
        pszShowIdleInfo = g_stRmtParamName.pszRmtQryQueShowIdleInfo;
        pszPrefetchCount = g_stRmtParamName.pszRmtQryQuePrefetchCount;
        pszLatestDays = g_stRmtParamName.pszRmtQryQueRetrLatestDays;
      case RMTITEM_TYPE_REPORTQUEUE =>
        pszMaxErrTimes = g_stRmtParamName.pszReportQueMaxErrTimes;
        pszRefreshTime = g_stRmtParamName.pszReportQueRefreshInternal;
        pszShowIdleInfo = g_stRmtParamName.pszReportQueShowIdleInfo;
        pszPrefetchCount = g_stRmtParamName.pszReportQuePrefetchCount;
      case RMTITEM_TYPE_DOWNLOADQUEUE =>
        pszMaxErrTimes = g_stRmtParamName.pszDownloadQueMaxErrTimes;
        pszRefreshTime = g_stRmtParamName.pszDownloadQueRefreshInternal;
        pszShowIdleInfo = g_stRmtParamName.pszDownloadQueShowIdleInfo;
        pszPrefetchCount = g_stRmtParamName.pszDownloadQuePrefetchCount;
      case RMTITEM_TYPE_KEYQUEUE =>
        pszMaxErrTimes = g_stRmtParamName.pszRmtKeyQueMaxErrTimes;
        pszRefreshTime = g_stRmtParamName.pszRmtKeyQueRefreshInternal;
        pszShowIdleInfo = g_stRmtParamName.pszRmtKeyQueShowIdleInfo;
        pszPrefetchCount = g_stRmtParamName.pszRmtKeyQuePrefetchCount;
      case RMTITEM_TYPE_SVRINFO =>
        pItem.nRefreshTime = 60;
        pszRefreshTime = g_stRmtParamName.pszSvrInfoRefreshInterval;
      case RMTITEM_TYPE_DLQRYRESULTQUE =>
        pItem.nPrefetchCount = 1000;
        pItem.nRefreshTime = 300;
        pszRefreshTime = "TxDownloadQryResultInterval";
      case RMTITEM_TYPE_LIVESCANQRYQUE =>
        pItem.nPrefetchCount = 2000;
        pItem.nRefreshTime = 60;
        pszRefreshTime = "TxLiveScanQryQueInterval";
      case other =>
    }
    /*
    if( pszMaxErrTimes != null)
      grmtpara.GAFIS_RMTLIB_PARAMETER_GetValueInt(pszMaxErrTimes, &pItem.nMaxErrTimes);
    if( pszRefreshTime != null)
      GAFIS_RMTLIB_PARAMETER_GetValueInt(pszRefreshTime, &pItem.nRefreshTime);
    if( pszShowIdleInfo != null)
      GAFIS_RMTLIB_PARAMETER_GetValueInt(pszShowIdleInfo, &pItem.bShowIdleInfo);
    if( pszPrefetchCount != null)
      GAFIS_RMTLIB_PARAMETER_GetValueInt(pszPrefetchCount, &pItem.nPrefetchCount);
    if( pszLatestDays != null)
      GAFIS_RMTLIB_PARAMETER_GetValueInt(pszLatestDays, &pItem.nLatestDays);
      */

    if( pItem.nMaxErrTimes < 0 )	pItem.nMaxErrTimes = 3;
    if( pItem.nRefreshTime<30 && nItemType!=RMTITEM_TYPE_SVRINFO )	pItem.nRefreshTime = 30;
    if( nItemType==RMTITEM_TYPE_FINISHEDQRYQUE && pItem.nRefreshTime>300 )	pItem.nRefreshTime=300;
    if( pItem.nPrefetchCount < 100 )	pItem.nPrefetchCount = 100;
    if( pItem.nLatestDays < 30 )	pItem.nLatestDays = 30;
  }
}

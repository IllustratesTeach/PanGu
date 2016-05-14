package nirvana.hall.c.services.gloclib

/**
  * from grmtname.c
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
object grmtname {
  /*
  def GAFIS_RMTTABLE_Init(pstRmt:RMTCOLNAMESTRUCT)
  {
    //server table
    pstRmt.stServer.pszUnitCode	= "UnitCode";
    pstRmt.stServer.pszServerName	= "ServerName";
    pstRmt.stServer.pszServerIP	= "ServerIP";
    pstRmt.stServer.pszEnUserName	= "EnUserName";
    pstRmt.stServer.pszEnUserPass	= "EnUserPass";
    pstRmt.stServer.pszServerUUID	= "ServerUUID";
    pstRmt.stServer.pszServerPort	= "ServerPort";
    pstRmt.stServer.pszServerID	= "ServerID";
    pstRmt.stServer.pszGroupID		= "GroupID";
    pstRmt.stServer.pszCreateTime	= "CreateTime";
    pstRmt.stServer.pszLastOpTime	= "LastOpTime";
    pstRmt.stServer.pszFlag		= "Flag";
    pstRmt.stServer.pszFlagEx		= "FlagEx";
    pstRmt.stServer.pszLevel		= "Level";
    pstRmt.stServer.pszComment		= "Comment";
    pstRmt.stServer.pszResData		= "ResData";
    pstRmt.stServer.pszAdvancedOpt	= "AdvancedOpt";
    pstRmt.stServer.pszDialServer	= "DialSvr";

    //router table
    pstRmt.stRouter.pszID				= "ID";
    pstRmt.stRouter.pszProxyUnitCode	= "ProxyUnitCode";
    pstRmt.stRouter.pszRmtUnitCode		= "RmtUnitCode";
    pstRmt.stRouter.pszProxyServerName	= "ProxyServerName";
    pstRmt.stRouter.pszRmtServerName	= "RmtServerName";
    pstRmt.stRouter.pszFlag			= "Flag";
    pstRmt.stRouter.pszFlagEx			= "FlagEx";
    pstRmt.stRouter.pszResData			= "ResData";

    //auto transmit config
    pstRmt.stTransmitCfg.pszID			= "ID";
    pstRmt.stTransmitCfg.pszCfgName	= "CfgName";
    pstRmt.stTransmitCfg.pszFlag		= "Flag";
    pstRmt.stTransmitCfg.pszFlagEx		= "FlagEx";
    pstRmt.stTransmitCfg.pszTimeInterval	= "TimeInterval";
    pstRmt.stTransmitCfg.pszCreateTime	= "CreateTime";
    pstRmt.stTransmitCfg.pszModifyTime	= "ModifyTime";
    pstRmt.stTransmitCfg.pszLastOpTime	= "LastOpTime";
    pstRmt.stTransmitCfg.pszResData	= "ResData";
    pstRmt.stTransmitCfg.pszRmtDBCfg	= "RmtDBCfg";
    pstRmt.stTransmitCfg.pszRmtTcCfg	= "RmtTcCfg";
    pstRmt.stTransmitCfg.pszRmtSvr		= "RmtSvr";
    pstRmt.stTransmitCfg.pszBlobResData= "BlobResData";
    pstRmt.stTransmitCfg.pszUUID		= "UUID";

    //transmit queue table
    pstRmt.stTransmitQue.pszKey		= "Key";
    pstRmt.stTransmitQue.pszLocUnitCode= "LocUnitCode";
    pstRmt.stTransmitQue.pszRmtUnitCode= "RmtUnitCode";
    pstRmt.stTransmitQue.pszUserName	= "UserName";
    pstRmt.stTransmitQue.pszID			= "ID";
    pstRmt.stTransmitQue.pszPlusID		= "PlusID";
    pstRmt.stTransmitQue.pszLocDB		= "LocDB";
    pstRmt.stTransmitQue.pszRmtDB		= "RmtDB";
    pstRmt.stTransmitQue.pszAddTime	= "AddTime";
    pstRmt.stTransmitQue.pszFinishTime	= "FinishTime";
    pstRmt.stTransmitQue.pszAfisErrNO	= "AfisErrNO";
    pstRmt.stTransmitQue.pszItemType	= "ItemType";
    pstRmt.stTransmitQue.pszStatus		= "Status";
    pstRmt.stTransmitQue.pszTTL		= "TTL";
    pstRmt.stTransmitQue.pszErrTimes	= "ErrTimes";
    pstRmt.stTransmitQue.pszFlag		= "Flag";
    pstRmt.stTransmitQue.pszFlagEx		= "FlagEx";
    pstRmt.stTransmitQue.pszResData	= "ResData";
    pstRmt.stTransmitQue.pszRouterList	= "RouterList";
    pstRmt.stTransmitQue.pszContent	= "Content";
    pstRmt.stTransmitQue.pszTTorLTQuery= "TTorLTQuery";
    pstRmt.stTransmitQue.pszTLorLLQuery= "TLorLLQuery";
    pstRmt.stTransmitQue.pszBlobResData= "BlobResData";
    pstRmt.stTransmitQue.pszPriority	= "Priority";

    //key queue table
    pstRmt.stKeyQue.pszLocUnitCode	= "LocUnitCode";
    pstRmt.stKeyQue.pszRmtUnitCode	= "RmtUnitCode";
    pstRmt.stKeyQue.pszID			= "ID";
    pstRmt.stKeyQue.pszPlusID		= "PlusID";
    //	pstRmt.stKeyQue.pszLocDB		= "LocDB";
    //	pstRmt.stKeyQue.pszRmtDB		= "RmtDB";
    pstRmt.stKeyQue.pszAddTime		= "AddTime";
    pstRmt.stKeyQue.pszFinishTime	= "FinishTime";
    pstRmt.stKeyQue.pszAfisErrNO	= "AfisErrNO";
    pstRmt.stKeyQue.pszItemType	= "ItemType";
    pstRmt.stKeyQue.pszStatus		= "Status";
    pstRmt.stKeyQue.pszTTL			= "TTL";
    pstRmt.stKeyQue.pszErrTimes	= "ErrTimes";
    pstRmt.stKeyQue.pszFlag		= "Flag";
    pstRmt.stKeyQue.pszFlagEx		= "FlagEx";
    pstRmt.stKeyQue.pszResData		= "ResData";
    pstRmt.stKeyQue.pszKeyList		= "KeyList";
    pstRmt.stKeyQue.pszBlobRmtDB	= "BlobRmtDB";
    pstRmt.stKeyQue.pszBlobResData	= "BlobResData";

    //query and data control table
    pstRmt.stQryAndDataCtrl.pszID		= "ID";
    pstRmt.stQryAndDataCtrl.pszUserName= "UserName";
    pstRmt.stQryAndDataCtrl.pszUnitCode= "UnitCode";
    pstRmt.stQryAndDataCtrl.pszFlag	= "Flag";
    pstRmt.stQryAndDataCtrl.pszFlagEx	= "FlagEx";
    pstRmt.stQryAndDataCtrl.pszRmtUserLevel	= "RmtUserLevel";
    pstRmt.stQryAndDataCtrl.pszResData0= "ResData0";
    pstRmt.stQryAndDataCtrl.pszTTCtrl	= "TTCtrl";
    pstRmt.stQryAndDataCtrl.pszTLCtrl	= "TLCtrl";
    pstRmt.stQryAndDataCtrl.pszLTCtrl	= "LTCtrl";
    pstRmt.stQryAndDataCtrl.pszLLCtrl	= "LLCtrl";
    pstRmt.stQryAndDataCtrl.pszQueryCtrl = "QueryCtrl";
    pstRmt.stQryAndDataCtrl.pszTPCardCtrl= "TPCardCtrl";
    pstRmt.stQryAndDataCtrl.pszLPCardCtrl= "LPCardCtrl";
    pstRmt.stQryAndDataCtrl.pszGroupID	= "GroupID";	//group
    pstRmt.stQryAndDataCtrl.pszIsGroup = "IsGroup";	//is group

    //AUTO QUERY TABEL
    pstRmt.stAutoQry.pszID			= "ID";
    pstRmt.stAutoQry.pszUserName	= "UserName";
    pstRmt.stAutoQry.pszUnitCode	= "UnitCode";
    pstRmt.stAutoQry.pszSendTT		= "SendTT";
    pstRmt.stAutoQry.pszSendTL		= "SendTL";
    pstRmt.stAutoQry.pszSendLT		= "SendLT";
    pstRmt.stAutoQry.pszSendLL		= "SendLL";
    pstRmt.stAutoQry.pszCheckTT	= "CheckTT";
    pstRmt.stAutoQry.pszCheckTL	= "CheckTL";
    pstRmt.stAutoQry.pszCheckLT	= "CheckLT";
    pstRmt.stAutoQry.pszCheckLL	= "CheckLL";
    pstRmt.stAutoQry.pszFlag		= "Flag";
    pstRmt.stAutoQry.pszFlagEx		= "FlagEx";
    pstRmt.stAutoQry.pszResData0	= "ResData0";
    pstRmt.stAutoQry.pszResData1	= "ResData1";
    pstRmt.stAutoQry.pszGroupID	= "GroupID";	//group
    pstRmt.stAutoQry.pszIsGroup	= "IsGroup";	//is group

    //transmit history table
    pstRmt.stTransmitHistory.pszID			= "ID";
    pstRmt.stTransmitHistory.pszKey		= "Key";
    pstRmt.stTransmitHistory.pszUserName	= "UserName";
    pstRmt.stTransmitHistory.pszLocUnitCode= "LocUnitCode";
    pstRmt.stTransmitHistory.pszRmtUnitCode= "RmtUnitCode";
    pstRmt.stTransmitHistory.pszLocDB		= "LocDB";
    pstRmt.stTransmitHistory.pszRmtDB		= "RmtDB";
    pstRmt.stTransmitHistory.pszItemType	= "ItemType";
    pstRmt.stTransmitHistory.pszStatus		= "Status";
    pstRmt.stTransmitHistory.pszFlag		= "Flag";
    pstRmt.stTransmitHistory.pszFlagEx		= "FlagEx";
    pstRmt.stTransmitHistory.pszAddTime	= "AddTime";
    pstRmt.stTransmitHistory.pszFinishTime	= "FinishTime";
    pstRmt.stTransmitHistory.pszTTL		= "TTL";
    pstRmt.stTransmitHistory.pszDataSize	= "DataSize";
    pstRmt.stTransmitHistory.pszResData	= "ResData";
    pstRmt.stTransmitHistory.pszIsUpdate	= "IsUpdate";

    //query echo table
    pstRmt.stEcho.pszID		= "ID";
    pstRmt.stEcho.pszUserName	= "UserName";
    pstRmt.stEcho.pszUnitCode	= "UnitCode";
    pstRmt.stEcho.pszLastOpTime= "LastOpTime";
    pstRmt.stEcho.pszFlag		= "Flag";
    pstRmt.stEcho.pszFlagEx	= "FlagEx";
    pstRmt.stEcho.pszOpt		= "Opt";
    pstRmt.stEcho.pszGroupID	= "GroupID";	//group
    pstRmt.stEcho.pszIsGroup	= "IsGroup";	//is group

    //user database config table
    pstRmt.stUserDB.pszID		= "ID";
    pstRmt.stUserDB.pszUserName= "UserName";
    pstRmt.stUserDB.pszUnitCode= "UnitCode";
    pstRmt.stUserDB.pszMatchTPDBID	= "MatchTPDBID";
    pstRmt.stUserDB.pszMatchLPDBID	= "MathcLPDBID";
    pstRmt.stUserDB.pszDownloadTPDBID	= "DownloadTPDBID";
    pstRmt.stUserDB.pszDownloadLPDBID	= "DownloadLPDBID";
    pstRmt.stUserDB.pszReportTPDBID	= "ReportTPDBID";
    pstRmt.stUserDB.pszReportLPDBID	= "ReportLPDBID";
    pstRmt.stUserDB.pszQryDBID			= "QryDBID";
    pstRmt.stUserDB.pszFlag			= "Flag";
    pstRmt.stUserDB.pszFlagEx			= "FlagEx";
    pstRmt.stUserDB.pszResData			= "ResData";
    pstRmt.stUserDB.pszGroupID	= "GroupID";	//group
    pstRmt.stUserDB.pszIsGroup	= "IsGroup";	//is group

    //asyn command table
    pstRmt.stAsynCmd.pszID			= "ID";
    pstRmt.stAsynCmd.pszPlusID		= "PlusID";
    pstRmt.stAsynCmd.pszCommand	= "Command";
    pstRmt.stAsynCmd.pszSignFlag	= "SignFlag";
    pstRmt.stAsynCmd.pszSendUnitCode	= "UserName";	//"SendUnitCode"
    pstRmt.stAsynCmd.pszRecvUnitCode	= "UnitCode";	//"RecvUnitCode"
    pstRmt.stAsynCmd.pszAddTime	= "AddTime";
    pstRmt.stAsynCmd.pszFinishTime	= "FinishTime";
    pstRmt.stAsynCmd.pszExpireTime	= "ExpireTime";
    pstRmt.stAsynCmd.pszAfisErrNO	= "AfisErrNO";
    pstRmt.stAsynCmd.pszType		= "Type";
    pstRmt.stAsynCmd.pszStatus		= "Status";
    pstRmt.stAsynCmd.pszFlag		= "Flag";
    pstRmt.stAsynCmd.pszFlagEx		= "FlagEx";
    pstRmt.stAsynCmd.pszErrTimes	= "ErrTimes";
    pstRmt.stAsynCmd.pszUseLobResult	= "UseLobResult";
    pstRmt.stAsynCmd.pszIntResult	= "IntResult";
    pstRmt.stAsynCmd.pszSimpParam	= "SimpParam";
    pstRmt.stAsynCmd.pszLobResult	= "LobResult";
    pstRmt.stAsynCmd.pszParam1		= "Param1";
    pstRmt.stAsynCmd.pszParam2		= "Param2";
    pstRmt.stAsynCmd.pszResData	= "ResData";
    pstRmt.stAsynCmd.pszSendUserName	= "SendUserName";

    //data  control
    pstRmt.stDatCtrl.pszID			= "ID";
    pstRmt.stDatCtrl.pszUserName	= "UserName";
    pstRmt.stDatCtrl.pszUnitCode	= "UnitCode";
    pstRmt.stDatCtrl.pszFlag		= "Flag";
    pstRmt.stDatCtrl.pszFlagEx		= "FlagEx";
    pstRmt.stDatCtrl.pszResData	= "ResData";
    pstRmt.stDatCtrl.pszItem		= "Item";
    pstRmt.stDatCtrl.pszBlobResData= "BlobResData";
    pstRmt.stDatCtrl.pszGroupID	= "GroupID";	//group
    pstRmt.stDatCtrl.pszIsGroup	= "IsGroup";	//is group

    //remote table id
    pstRmt.stRmtTableName.pszServerTable	= "Rmt_ServerTable";
    pstRmt.stRmtTableName.pszServerData	= "Rmt_ServerData";
    pstRmt.stRmtTableName.pszRouterTable	= "Rmt_RouterTable";
    pstRmt.stRmtTableName.pszRouterData	= "Rmt_RouterData";
    pstRmt.stRmtTableName.pszTransmitCfgTable	= "Rmt_TransmitCfgTable";
    pstRmt.stRmtTableName.pszTransmitCfgData	= "Rmt_TransmitCfgData";
    pstRmt.stRmtTableName.pszReportQueTable	= "Rmt_ReportQueTable";
    pstRmt.stRmtTableName.pszReportQueData		= "Rmt_ReportQueData";
    pstRmt.stRmtTableName.pszDownloadQueTable	= "Rmt_DownloadQueTable";
    pstRmt.stRmtTableName.pszDownloadQueData	= "Rmt_DownloadQueData";
    pstRmt.stRmtTableName.pszKeyQueTable		= "Rmt_KeyQueTable";
    pstRmt.stRmtTableName.pszKeyQueData		= "Rmt_KeyQueData";
    pstRmt.stRmtTableName.pszQryCtrlTable		= "Rmt_QryCtrlTable";
    pstRmt.stRmtTableName.pszQryCtrlData		= "Rmt_QryCtrlData";
    pstRmt.stRmtTableName.pszTransmitQryAndDataCtrlTable	= "Rmt_TransmitQryAndDataCtrlTable";
    pstRmt.stRmtTableName.pszTransmitQryAndDataCtrlData	= "Rmt_TransmitQryAndDataCtrlData";
    pstRmt.stRmtTableName.pszAutoQryTable		= "Rmt_AutoQryTable";
    pstRmt.stRmtTableName.pszAutoQryData		= "Rmt_AutoQryData";
    pstRmt.stRmtTableName.pszReportHistoryTable= "Rmt_ReportHistoryTable";
    pstRmt.stRmtTableName.pszReportHistoryData	= "Rmt_ReportHistoryData";
    pstRmt.stRmtTableName.pszDownloadHistoryTable	= "Rmt_DownloadHistoryTable";
    pstRmt.stRmtTableName.pszDownloadHistoryData	= "Rmt_DownloadHistoryData";
    pstRmt.stRmtTableName.pszEchoTable		= "Rmt_EchoTable";
    pstRmt.stRmtTableName.pszEchoData		= "Rmt_EchoData";
    pstRmt.stRmtTableName.pszUserDBTable	= "Rmt_UserDBTable";
    pstRmt.stRmtTableName.pszUserDBData	= "Rmt_UserDBData";
    pstRmt.stRmtTableName.pszAsynCmdTable	= "Rmt_AsynCmdTable";
    pstRmt.stRmtTableName.pszAsynCmdData	= "Rmt_AsynCmdData";
    pstRmt.stRmtTableName.pszStatInfoTable	= "Rmt_StatInfoTable";
    pstRmt.stRmtTableName.pszStatInfoData	= "Rmt_StatInfoData";
    pstRmt.stRmtTableName.pszDatCtrlTable	= "Rmt_DatCtrlTable";
    pstRmt.stRmtTableName.pszDatCtrlData	= "Rmt_DatCtrlData";

    // Beagle-add-start(2008-03-24): user-remote-config property
    pstRmt.stRmtTableName.pszRmtUserTranCfgTable	= "Rmt_RmtUserTranCfgTable";
    pstRmt.stRmtTableName.pszRmtUserTranCfgData	= "Rmt_RmtUserTranCfgData";
    pstRmt.stRmtTableName.pszUserPropRemoteCfgTable= "Rmt_UserRemotePropTable";
    pstRmt.stRmtTableName.pszUserPropRemoteCfgData	= "Rmt_UserRemotePropData";

    pstRmt.stRmtUserTranCfg.pszID				= "ID";
    pstRmt.stRmtUserTranCfg.pszFlag			= "Flag";
    pstRmt.stRmtUserTranCfg.pszUserName		= "UserName";
    pstRmt.stRmtUserTranCfg.pszCfgID			= "CfgID";
    pstRmt.stRmtUserTranCfg.pszCfgName			= "CfgName";
    pstRmt.stRmtUserTranCfg.pszCfgFlag			= "CfgFlag";
    pstRmt.stRmtUserTranCfg.pszCfgFlagEx		= "CfgFlagEx";
    pstRmt.stRmtUserTranCfg.pszCfgTimeInterval	= "CfgTimeInterval";
    pstRmt.stRmtUserTranCfg.pszCfgCreateTime	= "CfgCreateTime";
    pstRmt.stRmtUserTranCfg.pszCfgModifyTime	= "CfgModifyTime";
    pstRmt.stRmtUserTranCfg.pszCfgLastOpTime	= "CfgLastOpTime";
    pstRmt.stRmtUserTranCfg.pszCfgResData		= "CfgResData";
    pstRmt.stRmtUserTranCfg.pszCfgRmtDBCfg		= "CfgRmtDBCfg";
    pstRmt.stRmtUserTranCfg.pszCfgRmtTcCfg		= "CfgRmtTcCfg";
    pstRmt.stRmtUserTranCfg.pszCfgRmtSvr		= "CfgRmtSvr";
    pstRmt.stRmtUserTranCfg.pszCfgBlobResData	= "CfgBlobResData";
    pstRmt.stRmtUserTranCfg.pszCfgUUID			= "CfgUUID";

    pstRmt.stUserRemoteCfg.pszID			= "ID";
    pstRmt.stUserRemoteCfg.pszFlag			= "Flag";
    pstRmt.stUserRemoteCfg.pszUserName		= "UserName";
    pstRmt.stUserRemoteCfg.pszUnitCode		= "UnitCode";
    pstRmt.stUserRemoteCfg.pszServer		= "Server";
    pstRmt.stUserRemoteCfg.pszRouter		= "Router";
    pstRmt.stUserRemoteCfg.pszSvrParam		= "SvrParam";
    pstRmt.stUserRemoteCfg.pszDBMapTable	= "DBMapTable";
    // Beagle-add-end(2008-03-24)

    pstRmt.stRmtTableName.nTIDServerTable		= 100;
    pstRmt.stRmtTableName.nTIDServerData		= 1001;
    pstRmt.stRmtTableName.nTIDRouterTable		= 101;
    pstRmt.stRmtTableName.nTIDRouterData		= 1001;
    pstRmt.stRmtTableName.nTIDTransmitCfgTable	= 102;
    pstRmt.stRmtTableName.nTIDTransmitCfgData	= 1001;
    pstRmt.stRmtTableName.nTIDReportQueTable	= 103;
    pstRmt.stRmtTableName.nTIDReportQueData	= 1001;
    pstRmt.stRmtTableName.nTIDDownloadQueTable	= 104;
    pstRmt.stRmtTableName.nTIDDownloadQueData	= 1001;
    pstRmt.stRmtTableName.nTIDKeyQueTable		= 105;
    pstRmt.stRmtTableName.nTIDKeyQueData		= 1001;
    pstRmt.stRmtTableName.nTIDQryCtrlTable		= 106;
    pstRmt.stRmtTableName.nTIDQryCtrlData		= 1001;
    pstRmt.stRmtTableName.nTIDTransmitQryAndDataCtrlTable	= 107;
    pstRmt.stRmtTableName.nTIDTransmitQryAndDataCtrlData	= 1001;
    pstRmt.stRmtTableName.nTIDAutoQryTable		= 108;
    pstRmt.stRmtTableName.nTIDAutoQryData		= 1001;
    pstRmt.stRmtTableName.nTIDReportHistoryTable= 109;
    pstRmt.stRmtTableName.nTIDReportHistoryData	= 1001;
    pstRmt.stRmtTableName.nTIDDownloadHistoryTable	= 110;
    pstRmt.stRmtTableName.nTIDDownloadHistoryData	= 1001;
    pstRmt.stRmtTableName.nTIDEchoTable		= 111;
    pstRmt.stRmtTableName.nTIDEchoData		= 1001;
    pstRmt.stRmtTableName.nTIDUserDBTable	= 112;
    pstRmt.stRmtTableName.nTIDUserDBData	= 1001;
    pstRmt.stRmtTableName.nTIDAsynCmdTable	= 113;
    pstRmt.stRmtTableName.nTIDAsynCmdData	= 1001;
    pstRmt.stRmtTableName.nTIDStatInfoTable= 114;
    pstRmt.stRmtTableName.nTIDStatInfoData	= 1001;
    pstRmt.stRmtTableName.nTIDDatCtrlTable	= 115;
    pstRmt.stRmtTableName.nTIDDatCtrlData	= 1001;

    // Beagle-add-start(2008-03-24): user-remote-config property
    pstRmt.stRmtTableName.nTIDRmtUserTranCfgTable	= 116;
    pstRmt.stRmtTableName.nTIDRmtUserTranCfgData	= 1001;
    pstRmt.stRmtTableName.nTIDUserPropRemoteCfgTable = 117;
    pstRmt.stRmtTableName.nTIDUserPropRemoteCfgData  = 1001;
    // Beagle-add-end(2008-03-24)
  }

*/


}

package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.gbaselib.gathrdop.GBASE_CRIT
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object paramadm {
  lazy val g_stParamName = GBASE_PARAMNAME_Init()
  private def GBASE_PARAMNAME_Init():GBASE_PARAMNAMESTRUCT= {
    val pstName = new GBASE_PARAMNAMESTRUCT 
    pstName.pszDBININAME			= "DBININAME";
    pstName.pszDefaultUserName		= "DefaultUserName";
    pstName.pszCpuCount			= "CpuCount";
    pstName.pszPhysicalMemInMB		= "PhysicalMemInMB";
    pstName.pszMainPath			= "MainPath";
    pstName.pszOsPlatform			= "OsPlatform";
    pstName.pszOsType				= "OsType";
    pstName.pszDEF_LANG			= "DEF_LANG";
    pstName.pszDEF_LANG_ID			= "DEF_LANG_ID";
    pstName.pszLID_Message			= "LID_Message";
    pstName.pszLID_Time			= "LID_Time";
    pstName.pszLID_Date			= "LID_Date";
    pstName.pszCollateID_Latin		= "CollateID_Latin";
    pstName.pszCollateID_MB		= "CollateID_MB";
    pstName.pszDBSvrQueryExpireTime= "DBSvrQueryExpireTime";
    pstName.pszTEMP_PATH			= "TEMP_PATH";
    pstName.pszUnitCode			= "UnitCode";
    pstName.pszUnitName			= "UnitName";
    pstName.pszMntFormat			= "MntFormat";
    pstName.pszUseTTFilter			= "UseTTFilter";
    pstName.pszSublibFeat			= "SublibFeat";
    pstName.pszMatchMethod			= "MatchMethod";
    pstName.pszMntExpireTime		= "MntExpireTime";
    pstName.pszMatchMaxIdleTime	= "MatchMaxIdleTime";
    pstName.pszNewDBMainPath		= "NewDBMainPath";
    pstName.pszmcast_ip_head		= "mcast_ip_head";
    pstName.pszmu_max_idle_time	= "mu_max_idle_time";
    pstName.pszMcastSublib			= "McastSublib";
    pstName.pszDBUpdateInterval	= "DBUpdateInterval";
    pstName.pszMmuDownloadThreadCnt= "MmuDownloadThreadCnt";
    pstName.pszSmuDownloadThreadCnt= "SmuDownloadThreadCnt";
    pstName.pszGmuDownloadThreadCnt= "GmuDownloadThreadCnt";
    pstName.pszMUPrefetchTime		= "MUPrefetchTime";
    pstName.pszQryInvalidTime		= "QryInvalidTime";
    pstName.pszMUAutoAddInactiveTime	= "MUAutoAddInactiveTime";
    pstName.pszMUManuAddInactiveTime	= "MUManuAddInactiveTime";
    pstName.pszThreadMatchMaxRowCnt	= "ThreadMatchMaxRowCnt";
    pstName.pszRowTol					= "RowTol";
    pstName.pszMaxQryInQryList			= "MaxQryInQryList";
    pstName.pszMinQryInQryList			= "MinQryInQryList";
    pstName.pszMaxTaskInLocTaskList	= "MaxTaskInLocTaskList";
    pstName.pszMinTaskInLocTaskList	= "MinTaskInLocTaskList";
    pstName.pszMaxTaskInSysTaskList	= "MaxTaskInSysTaskList";
    pstName.pszMinTaskInSysTaskList	= "MinTaskInSysTaskList";
    pstName.pszMinStepOneCandCount		= "MinStepOneCandCount";
    pstName.pszMaxStepOneCandCount		= "MaxStepOneCandCount";
    pstName.pszStepOneRatio			= "StepOneRatio";
    pstName.pszMinErrorTime			= "MinErrorTime";
    pstName.pszMaxErrorCnt				= "MaxErrorCnt";
    pstName.pszTTNoStepTwo				= "TTNoStepTwo";
    pstName.pszLTNoStepTwo				= "LTNoStepTwo";
    pstName.pszLLNoStepTwo				= "LLNoStepTwo";
    pstName.pszTLNoStepTwo				= "TLNoStepTwo";
    pstName.pszMuReportInterval		= "MuReportInterval";
    pstName.pszDefMaxCand				= "DefMaxCand";
    pstName.pszRecCntEachDownload		= "RecCntEachDownload";
    pstName.pszSysTaskMinWorkTime		= "SysTaskMinWorkTime";
    pstName.pszSysTaskMaxWorkTime		= "SysTaskMaxWorkTime";
    pstName.pszLocTaskMinWorkTime		= "LocTaskMinWorkTime";
    pstName.pszLocTaskMaxWorkTime		= "LocTaskMaxWorkTime";
    pstName.pszCandMergeMethod			= "CandMergeMethod";
    pstName.pszNetPrefetchIdleTime		= "NetPrefetchIdleTime";
    pstName.pszTCPServerMaxAllowedThreadCnt		= "TCPServerMaxAllowedThreadCnt";
    pstName.pszDBCreateIdleTime		= "DBCreateIdleTime";
    pstName.pszDataNeedPrefetch		= "DataNeedPrefetch";
    pstName.pszAutoLTQueryPriority		= "AutoLTQueryPriority";
    pstName.pszAutoLLQueryPriority		= "AutoLLQueryPriority";
    pstName.pszAutoTLQueryPriority		= "AutoTLQueryPriority";
    pstName.pszAutoTTQueryPriority		= "AutoTTQueryPriority";
    pstName.pszDBServerPort			= "DBServerPort";
    pstName.pszTotalClient				= "TotalClient";
    pstName.pszCurrentClient			= "CurrentClient";
    pstName.pszCurrentThreadNum		= "CurrentThreadNum";
    pstName.pszDBMemLimitPercent		= "DBMemLimitPercent";
    pstName.pszDBModResTime			= "DBModResTime";
    pstName.pszXLogFileSizeInByte		= "XLogFileSizeInByte";
    pstName.pszMachineUUID				= "MachineUUID";

    pstName.pszMmuMcastSublib			= "MmuMcastSublib";
    pstName.pszSmuMcastSublib			= "SmuMcastSublib";
    pstName.pszGmuMcastSublib			= "GmuMcastSublib";

    pstName.pszDelayMcastInMicro		= "DelayMcastInMicro";
    pstName.pszMcastDataSizeLimit		= "McastDataSizeLimit";
    pstName.pszRemoveSourceCardInTTLL	= "RemoveSourceCardInTTLL";
    pstName.pszThreadTTMatchMinRowCnt	= "ThreadTTMatchMinRowCnt";
    pstName.pszThreadTLMatchMinRowCnt	= "ThreadTLMatchMinRowCnt";
    pstName.pszThreadLTMatchMinRowCnt	= "ThreadLTMatchMinRowCnt";
    pstName.pszThreadLLMatchMinRowCnt	= "ThreadLLMatchMinRowCnt";
    pstName.pszTTSupportExactSearch	= "TTSupportExactSearch";
    pstName.pszLTSupportExactSearch	= "LTSupportExactSearch";
    pstName.pszTLSupportExactSearch	= "TLSupportExactSearch";
    pstName.pszLLSupportExactSearch	= "LLSupportExactSearch";
    pstName.pszMUGetTaskCalcRowCountMethod	= "MUGetTaskCalcRowCountMethod";
    pstName.pszMuTTMatchMinRowCount		= "MuTTMatchMinRowCount";
    pstName.pszMuTLMatchMinRowCount		= "MuTLMatchMinRowCount";
    pstName.pszMuLTMatchMinRowCount		= "MuLTMatchMinRowCount";
    pstName.pszMuLLMatchMinRowCount		= "MuLLMatchMinRowCount";
    pstName.pszMuTTPreferRowCount			= "MuTTPreferRowCount";
    pstName.pszMuTLPreferRowCount			= "MuTLPreferRowCount";
    pstName.pszMuLTPreferRowCount			= "MuLTPreferRowCount";
    pstName.pszMuLLPreferRowCount			= "MuLLPreferRowCount";
    pstName.pszThreadTTPreferRowCount		= "ThreadTTPreferRowCount";
    pstName.pszThreadTLPreferRowCount		= "ThreadTLPreferRowCount";
    pstName.pszThreadLTPreferRowCount		= "ThreadLTPreferRowCount";
    pstName.pszThreadLLPreferRowCount		= "ThreadLLPreferRowCount";
    pstName.pszThreadGetTaskCalcRowCountMethod	= "ThreadGetTaskCalcRowCountMethod";
    pstName.pszMuTTPreferMatchTime			= "MuTTPreferMatchTime";
    pstName.pszMuTLPreferMatchTime			= "MuTLPreferMatchTime";
    pstName.pszMuLTPreferMatchTime			= "MuLTPreferMatchTime";
    pstName.pszMuLLPreferMatchTime			= "MuLLPreferMatchTime";
    pstName.pszThreadTTPreferMatchTime		= "ThreadTTPreferMatchTime";
    pstName.pszThreadTLPreferMatchTime		= "ThreadTLPreferMatchTime";
    pstName.pszThreadLTPreferMatchTime		= "ThreadLTPreferMatchTime";
    pstName.pszThreadLLPreferMatchTime		= "ThreadLLPreferMatchTime";
    pstName.pszMuPrefetchTaskMethod		= "MuPrefetchTaskMethod";
    pstName.pszLocPrefetchTaskMethod		= "LocPrefetchTaskMethod";
    pstName.pszNetPrefetchCheckTime		= "NetPrefetchCheckTime";
    pstName.pszLocPrefetchCheckTime		= "LocPrefetchCheckTime";
    pstName.pszMaxMemCanUsedEachDownload	= "MaxMemCanUsedEachDownload";
    pstName.pszThreadGetTaskCheckTime		= "ThreadGetTaskCheckTime";
    pstName.pszTTFilterMatchRowCount		= "TTFilterMatchRowCount";
    pstName.pszTTStepOneMatchRowCount		= "TTStepOneMatchRowCount";
    pstName.pszTLStepOneMatchRowCount		= "TLStepOneMatchRowCount";
    pstName.pszLTStepOneMatchRowCount		= "LTStepOneMatchRowCount";
    pstName.pszLLStepOneMatchRowCount		= "LLStepOneMatchRowCount";
    pstName.pszTTStepTwoMatchRowCount		= "TTStepTwoMatchRowCount";
    pstName.pszTLStepTwoMatchRowCount		= "TLStepTwoMatchRowCount";
    pstName.pszLTStepTwoMatchRowCount		= "LTStepTwoMatchRowCount";
    pstName.pszLLStepTwoMatchRowCount		= "LLStepTwoMatchRowCount";
    pstName.pszSupportPartialDB			= "SupportPartialDB";
    pstName.pszMuCachePercent				= "MuCachePercent";
    pstName.pszTTAveSpeed					= "TTAveSpeed";
    pstName.pszTLAveSpeed					= "TLAveSpeed";
    pstName.pszLTAveSpeed					= "LTAveSpeed";
    pstName.pszLLAveSpeed					= "LLAveSpeed";
    pstName.pszMcastSendDataMaxWaitTime	= "McastSendDataMaxWaitTime";
    pstName.pszPalmTTAveSpeed				= "PalmTTAveSpeed";
    pstName.pszPalmTLAveSpeed				= "PalmTLAveSpeed";
    pstName.pszPalmLTAveSpeed				= "PalmLTAveSpeed";
    pstName.pszPalmLLAveSpeed				= "PalmLLAveSpeed";
    pstName.pszPalmTTStepOneRowCnt			= "PalmTTStepOneRowCnt";
    pstName.pszPalmTLStepOneRowCnt			= "PalmTLStepOneRowCnt";
    pstName.pszPalmLTStepOneRowCnt			= "PalmLTStepOneRowCnt";
    pstName.pszPalmLLStepOneRowCnt			= "PalmLLStepOneRowCnt";
    pstName.pszPalmTTStepTwoRowCnt			= "PalmTTStepTwoRowCnt";
    pstName.pszPalmTLStepTwoRowCnt			= "PalmTLStepTwoRowCnt";
    pstName.pszPalmLTStepTwoRowCnt			= "PalmLTStepTwoRowCnt";
    pstName.pszPalmLLStepTwoRowCnt			= "PalmLLStepTwoRowCnt";
    pstName.pszCalcSpeedMinRowCount		= "CalcSpeedMinRowCount";
    pstName.pszCalcSpeedRowCount			= "CalcSpeedRowCount";
    pstName.pszPreGetStepTwoData			= "PreGetStepTwoData";
    pstName.pszSublibDropNonExistCol		= "SublibDropNonExistCol";
    pstName.pszQryDeadTime					= "QryDeadTime";
    pstName.pszTLKeepFingerCount			= "TLKeepFingerCount";
    pstName.pszErrorIfNoDestDB				= "ErrorIfNoDestDB";
    pstName.pszViewAsBlobLen				= "ViewAsBlobLen";
    pstName.pszMcastServerPort				= "McastServerPort";
    pstName.pszSearchTPlainFinger			= "SearchTPlainFinger";
    pstName.pszPalmAsBin					= "PalmAsBin";
    pstName.pszSupportPalm					= "SupportPalm";
    pstName.pszSupportFinger				= "SupportFinger";
    pstName.pszDatabaseType				= "DatabaseType";
    pstName.pszMaxTextItemLen				= "MaxTextItemLen";
    pstName.pszImgStandard					= "ImgStandard";
    pstName.pszMmuWillDoStepTwo			= "MmuWillDoStepTwo";
    pstName.pszSmuWillDoStepTwo			= "SmuWillDoStepTwo";
    pstName.pszGmuWillDoStepTwo			= "GmuWillDoStepTwo";
    pstName.pszMcDropRangeCnt				= "McDropRangeCnt";
    pstName.pszMcastPrintDetailInfo		= "McastPrintDetailInfo";
    pstName.pszServerStartupTime			= "ServerStartupTime";

    pstName.pszSystemStartupTimeInt		= "SystemStartupTimeInt";
    pstName.pszSupportStackTrace			= "SupportStackTrace";
    pstName.pszSupportException			= "SupportException";
    pstName.pszFastExpandFile				= "FastExpandFile";
    pstName.pszHiResoTickPerSec			= "HiResoTickPerSec";
    pstName.pszCounter_WriteTimeUsed		= "Counter_WriteTimeUsed";
    pstName.pszCounter_ReadTimeUsed		= "Counter_ReadTimeUsed";
    pstName.pszSystemStartupTime			= "SystemStartupTime";
    pstName.pszDisableTxtLog				= "DisableTxtLog";
    pstName.pszCounter_WriteByteCount		= "Counter_WriteByteCount";
    pstName.pszCounter_ReadByteCount		= "Counter_ReadByteCount";
    pstName.pszCounter_WriteCount			= "Counter_WriteCount";
    pstName.pszCounter_ReadCount			= "Counter_ReadCount";
    pstName.pszBackupErrLogFile			= "BackupErrLogFile";
    pstName.pszSockSendTimeOut				= "SockSendTimeOut";
    pstName.pszSockRecvTimeOut				= "SockRecvTimeOut";
    pstName.pszMcastCliMaxMemUsed			= "McastCliMaxMemUsed";
    pstName.pszUseAsyncNMF					= "UseAsyncNMF";

    pstName.pszMuStepTwoTTPreferRowCount	= "MuStepTwoTTPreferRowCount";
    pstName.pszMuStepTwoTLPreferRowCount	= "MuStepTwoTLPreferRowCount";
    pstName.pszMuStepTwoLTPreferRowCount	= "MuStepTwoLTPreferRowCount";
    pstName.pszMuStepTwoLLPreferRowCount	= "MuStepTwoLLPreferRowCount";

    pstName.pszThreadStepTwoTTPreferRowCount	= "ThreadStepTwoTTPreferRowCount";
    pstName.pszThreadStepTwoTLPreferRowCount	= "ThreadStepTwoTLPreferRowCount";
    pstName.pszThreadStepTwoLTPreferRowCount	= "ThreadStepTwoLTPreferRowCount";
    pstName.pszThreadStepTwoLLPreferRowCount	= "ThreadStepTwoLLPreferRowCount";
    pstName.pszMcastFlowControlInKB			= "McastFlowControlInKB";

    pstName.pszMcastTTL						= "McastTTL";
    pstName.pszMcastBindIF						= "McastBindIF";
    pstName.pszEnableMatchWhileSublib			= "EnableMatchWhileSublib";
    pstName.pszEnableMcSvrMatchWhileSublib		= "EnableMcSvrMatchWhileSublib";

    pstName.pszMaxCandCount					= "MaxCandCount";
    pstName.pszResublibRowPercent				= "ResublibRowPercent";

    pstName.pszSockSafeClose					= "SockSafeClose";

    // following items are added on Jan. 21, 2005
    pstName.pszPalmMuTTPreferRowCount			= "PalmMuTTPreferRowCount";
    pstName.pszPalmMuTLPreferRowCount			= "PalmMuTLPreferRowCount";
    pstName.pszPalmMuLTPreferRowCount			= "PalmMuLTPreferRowCount";
    pstName.pszPalmMuLLPreferRowCount			= "PalmMuLLPreferRowCount";
    pstName.pszPalmThreadTTPreferRowCount		= "PalmThreadTTPreferRowCount";
    pstName.pszPalmThreadTLPreferRowCount		= "PalmThreadTLPreferRowCount";
    pstName.pszPalmThreadLTPreferRowCount		= "PalmThreadLTPreferRowCount";
    pstName.pszPalmThreadLLPreferRowCount		= "PalmThreadLLPreferRowCount";

    pstName.pszPalmMuStepTwoTTPreferRowCount	= "PalmMuStepTwoTTPreferRowCount";
    pstName.pszPalmMuStepTwoTLPreferRowCount	= "PalmMuStepTwoTLPreferRowCount";
    pstName.pszPalmMuStepTwoLTPreferRowCount	= "PalmMuStepTwoLTPreferRowCount";
    pstName.pszPalmMuStepTwoLLPreferRowCount	= "PalmMuStepTwoLLPreferRowCount";

    pstName.pszPalmThreadStepTwoTTPRC			= "PalmThreadStepTwoTTPRC";
    pstName.pszPalmThreadStepTwoTLPRC			= "PalmThreadStepTwoTLPRC";
    pstName.pszPalmThreadStepTwoLTPRC			= "PalmThreadStepTwoLTPRC";
    pstName.pszPalmThreadStepTwoLLPRC			= "PalmThreadStepTwoLLPRC";

    pstName.pszRefCPUFrequence					= "RefCPUFrequence";
    pstName.pszRefCPUSpeedInMhz				= "RefCPUSpeedInMhz";

    pstName.pszDBSkmComptDate					= "DBSkmComptDate";
    pstName.pszGAFISKeepLatestSkm				= "GAFISKeepLatestSkm";
    pstName.pszGAFISKeepOldColumn				= "GAFISKeepOldColumn";

    pstName.pszLeastScoreLL					= "LeastScoreLL";
    pstName.pszLeastScoreLT					= "LeastScoreLT";
    pstName.pszLeastScoreTL					= "LeastScoreTL";
    pstName.pszLeastScoreTT					= "LeastScoreTT";

    pstName.pszExceptionTerminate				= "ExceptionTerminate";
    pstName.pszForceRemoveSelfByKeyInTTLL		= "ForceRemoveSelfByKeyInTTLL";
    pstName.pszInstantQueryGet					= "InstantQueryGet";
    pstName.pszFinishQueryNotify				= "FinishQueryNotify";
    pstName.pszSupportFace						= "SupportFace";
    pstName.pszSupportVoice					= "SupportVoice";

    pstName.pszTPFaceMntAsBin					= "TPFaceMntAsBin";
    pstName.pszTPVoiceMntAsBin					= "TPVoiceMntAsBin";
    pstName.pszSearchFace						= "SearchFace";
    pstName.pszSearchVoice						= "SearchVoice";

    pstName.pszLTMergeDupCard					= "LTMergeDupCard";
    pstName.pszDownLoadBinMethod				= "DownLoadBinMethod";
    ////////////////
    pstName.pszEXFDoRollPlainMatch				= "EXFDoRollPlainMatch";
    pstName.pszEXFDoRollPlainPatternReplace	= "EXFDoRollPlainPatternReplace";
    pstName.pszEXFRollPlainUnmatchScore		= "EXFRollPlainUnmatchScore";
    pstName.pszEXFRollPlainMatchScore			= "EXFRollPlainMatchScore";
    pstName.pszEditDoRollPlainMatch			= "EditDoRollPlainMatch";
    pstName.pszEditDoRollPlainPatternReplace	= "EditDoRollPlainPatternReplace";
    pstName.pszEditRollPlainUnmatchScore		= "EditRollPlainUnmatchScore";
    pstName.pszEditRollPlainMatchScore			= "EditRollPlainMatchScore";


    pstName.pszLiveScanMinImageQuality			= "LiveScanMinImageQuality";
    pstName.pszLiveScanGoodImageQuality		= "LiveScanGoodImageQuality";
    pstName.pszLiveScanDoCrossCheck			= "LiveScanDoCrossCheck";
    pstName.pszLiveScanCrossCheckScore			= "LiveScanCrossCheckScore";
    pstName.pszLiveScanDoSameFingerCheck		= "LiveScanDoSameFingerCheck";
    pstName.pszLiveScanSameFingerScore			= "LiveScanSameFingerScore";

    pstName.pszPolicyNonAdmEditAllLatData		= "PolicyNonAdmEditAllLatData";
    pstName.pszPolicyNonAdmViewAllLatData		= "PolicyNonAdmViewAllLatData";
    pstName.pszPolicyNonAdmViewAllQuery		= "PolicyNonAdmViewAllQuery";
    pstName.pszPolicyQueryNeedReCheck			= "PolicyQueryNeedReCheck";

    pstName.pszLiveScanWillEditFinger			= "LiveScanWillEditFinger";

    pstName.pszLiveScanFingerEnforced			= "LiveScanFingerEnforced";
    pstName.pszLiveScanPlainFingerChoice		= "LiveScanPlainFingerChoice";

    pstName.pszTTCanUsePlain					= "TTCanUsePlain";
    pstName.pszListenBacklogCnt				= "ListenBacklogCnt";

    pstName.pszGfpManagerModule_QryMaxCnt		= "GfpManagerModule_QryMaxCnt";
    pstName.pszGfpManagerModule_CardMaxCnt		= "GfpManagerModule_CardMaxCnt";
    pstName.pszQueryModule_QryMaxCnt			= "QueryModule_QryMaxCnt";
    pstName.pszQueryModule_CardMaxCnt			= "QueryModule_CardMaxCnt";
    pstName.pszEditModule_OnlyOwnerKey			= "EditModule_OnlyOwnerKey";

    pstName.pszQueTTSearchAfterEdit			= "QueTTSearchAfterEdit";
    pstName.pszQueTLSearchAfterEdit			= "QueTLSearchAfterEdit";
    pstName.pszQueLTSearchAfterEdit			= "QueLTSearchAfterEdit";
    pstName.pszQueLLSearchAfterEdit			= "QueLLSearchAfterEdit";

    pstName.pszQueSupportPalmTL				= "QueSupportPalmTL";
    pstName.pszQualCheckSupportQue				= "QualCheckSupportQue";

    pstName.pszAutoMISPersonID					= "AutoMISPersonID";

    pstName.pszQrySourceSupportMulti			= "QrySourceSupportMulti";

    pstName.pszStepTwoBothRollAndPlain			= "StepTwoBothRollAndPlain";


    pstName.pszTLMergeByFgGroup				= "TLMergeByFgGroup";
    pstName.pszTLCaseSuperviseList				= "TLCaseSuperviseList";
    pstName.pszTLUpgradeCount					= "TLUpgradeCount";
    pstName.pszLLMergeByFgGroup				= "LLMergeByFgGroup";
    pstName.pszLLCaseSuperviseList				= "LLCaseSuperviseList";
    pstName.pszLLUpgradeCount					= "LLUpgradeCount";
    pstName.pszTLMergeCandAveFgMethod			= "TLMergeCandAveFgMethod";
    pstName.pszTLMergeCandAveFgBuf				= "TLMergeCandAveFgBuf";

    pstName.pszMUKeepLatestSkm					= "MUKeepLatestSkm";
    pstName.pszMUKeepOldColumn					= "MUKeepOldColumn";
    pstName.pszCandCountForTextSearch			= "CandCountForTextSearch";

    /** added on Feb. 27, 2009
      * 1¡¢Í¼ÏñÑ¹Ëõ·½·¨¡¢Ñ¹Ëõ±ÈÂÊ²ÎÊýÃû
      * 2¡¢ÕÆÎÆÉ¨ÃèÊ±£¨°üÀ¨»îÌå£©ÊÇ·ñ±¸·ÝÒ»·ÝÎÞËðÑ¹Ëõµ½Ó²ÅÌÉÏ£¬
      *    Ö÷ÒªÄ¿µÄÊÇÒ»µ©ÓÐÁË¸üºÃµÄÕÆÎÆÌØÕ÷ÌáÈ¡Ëã·¨£¬¿ÉÒÔÖØÐÂ´Ó±¸·ÝÖÐÖØÌáÌØÕ÷
      */
    pstName.pszImageCprMethod	= "ImageCprMethodNew";
    pstName.pszImageCprRatio	= "ImageCprRatioNew";

    pstName.pszBackupPalmData2Disk = "BackupPalmData2Disk";

    pstName.pszFingerAsBin	= "FingerAsBin";

    /**
      * added on Setp.6, 2010
      * 	°ÑÏÈÇ°µÄnMinStepOneCandCount²ÎÊý°´²éÑ¯ÀàÐÍ·Ö³É4¸ö
      */
    //pstName.pszTTMinStepOneCandCount = "TTMinStepOneCandCount";
    //pstName.pszTLMinStepOneCandCount = "TLMinStepOneCandCount";
    //pstName.pszLTMinStepOneCandCount = "LTMinStepOneCandCount";
    //pstName.pszLLMinStepOneCandCount = "LLMinStepOneCandCount";

    /**
      * ÓÃÀ´±ê¼ÇÔÚµ÷ÓÃGSCH_UTIL_CalcStepTwoCandCountº¯Êý¼ÆËãºòÑ¡¸öÊýÊ±£¬
      * ÊÇ·ñÖ±½ÓÊ¹ÓÃÖ¸¶¨µÄºòÑ¡¸öÊý£¬È±Ê¡Îª0(»áÓÐÒ»¸ö·Å´óËã·¨À´µ÷ÕûºòÑ¡µÄ¸öÊý)
      */
    //pstName.pszStepTwoCandCountEqualStepOne = "StepTwoCandCountEqualStepOne";

    pstName.pszDelNotExistSubLib = "GAFIS_DelNotExistSubLib";

    pstName.pszRowCntPerThreadToPrefLobRead = "RowCntPerThreadToPrefLobRead";
    pstName.pszMaxThreadToPrefLobRead = "MaxThreadToPrefLobRead";

    pstName.pszCandMergeStyle = "GAFIS_CandMergeStyle";

    pstName.pszGDQueryTestOption = "GDQueryTest_Option";

    pstName.pszTTAutoMergeCandScore = "TTAutoMergeCandScore";
    pstName.pszTT_WsgThreshold = "TT_WsgThreshold";
    pstName.pszTT_XgwThreshold = "TT_XgwThreshold";

    pstName.pszGAFIS_SystemRunMode = "GAFIS_SystemRunMode";

    pstName.pszLTCalcStepTwoCandCntMethod = "LTCalcStepTwoCandCntMethod";
    pstName.pszTTCalcStepTwoCandCntMethod = "TTCalcStepTwoCandCntMethod";
    pstName.pszTLCalcStepTwoCandCntMethod = "TLCalcStepTwoCandCntMethod";
    pstName.pszLLCalcStepTwoCandCntMethod = "LLCalcStepTwoCandCntMethod";

    pstName.pszLTStepTwoCandCnt = "LTStepTwoCandCnt";
    pstName.pszTTStepTwoCandCnt = "TTStepTwoCandCnt";
    pstName.pszTLStepTwoCandCnt = "TLStepTwoCandCnt";
    pstName.pszLLStepTwoCandCnt = "LLStepTwoCandCnt";

    /**
      * LTºÏ²¢·½Ê½¡¢ÖØ¿¨²éÑ¯²ÎÊýÃû³Æ
      */
    pstName.pszSearchDupCardInLTCand = "SearchDupCardInLTCand";
    pstName.pszLTDupCard_MaxSearchedCand = "LTDupCard_MaxSearchedCand";
    pstName.pszLTDupCard_SearchingRank = "LTDupCard_SearchingRank";

    pstName.pszLTXWCandMerge_Q		= "LTXWCandMerge_Q";
    pstName.pszLTXWCandMerge_X		= "LTXWCandMerge_X";
    pstName.pszLTXWCandMerge_W		= "LTXWCandMerge_W";
    pstName.pszLTXWCandMerge_Beta	= "LTXWCandMerge_Beta";
    pstName.pszLTXWCandMerge_ScoreRatio = "LTXWCandMerge_ScoreRatio";
    pstName.pszLTXWCandMerge_CandCount = "LTXWCandMerge_CandCount";

    /**
      * TLºÏ²¢·½Ê½¡¢ÖØ¿¨²éÑ¯²ÎÊýÃû³Æ
      */
    pstName.pszSearchDupCardInTLCand = "SearchDupCardInTLCand";
    pstName.pszTLDupCard_MaxSearchedCand = "TLDupCard_MaxSearchedCand";
    pstName.pszTLDupCard_SearchingRank = "TLDupCard_SearchingRank";
    pstName.pszTLDupCard_LLHitThreshold = "TLDupCard_LLHitThreshold";

    pstName.pszTLXWCandMerge_Q		= "TLXWCandMerge_Q";
    pstName.pszTLXWCandMerge_X		= "TLXWCandMerge_X";
    pstName.pszTLXWCandMerge_W		= "TLXWCandMerge_W";
    pstName.pszTLXWCandMerge_Beta	= "TLXWCandMerge_Beta";
    pstName.pszTLXWCandMerge_Option = "TLXWCandMerge_Option";
    pstName.pszTLXWCandMerge_CandCount = "TLXWCandMerge_CandCount";

    pstName.pszLTXWCandMerge_XStepOneRank = "LTXWCandMerge_XStepOneRank";
    pstName.pszLTXWCandMerge_WStepOneRank = "LTXWCandMerge_WStepOneRank";
    pstName.pszTLXWCandMerge_XStepOneRank = "TLXWCandMerge_XStepOneRank";
    pstName.pszTLXWCandMerge_WStepOneRank = "TLXWCandMerge_WStepOneRank";

    pstName.pszLTXWCandMerge_QMaxCandCount = "LTXWCandMerge_QMaxCandCount";
    pstName.pszTLXWCandMerge_QMaxCandCount = "TLXWCandMerge_QMaxCandCount";

    pstName.pszLTDupCard_ProcessOption = "LTDupCard_ProcessOption";

    pstName.pszTT_MinXgwScoreForWsgMatch = "TT_MinXgwScoreForWsgMatch";

    pstName.pszXWMatch_TTStepTwo = "XWMatch_TTStepTwo";
    pstName.pszXWMatch_TLStepTwo = "XWMatch_TLStepTwo";
    pstName.pszXWMatch_LTStepTwo = "XWMatch_LTStepTwo";
    pstName.pszXWMatch_LLStepTwo = "XWMatch_LLStepTwo";

    pstName.pszTTCrossMatch = "TTCrossMatch";
    pstName.pszTTLowScoreMaxCandCount = "TTLowScoreMaxCandCount";

    pstName.pszTLMatchedWithGroup = "TLMatchedWithGroup";
    pstName.pszTLMatchedGroupCnt = "TLMatchedGroupCount";

    pstName;
  }


  // parameter net item.
  class GBASE_PARAM_NETITEM extends AncientData
  {
    @Length(32)
    var szName:String = _ ;
    var nValType:Byte = _ ;	// GBASE_VALTYPE_XX
  var nBufLen:Byte = _ ;
    var nRetVal:Byte = _ ;	// return value if get set from network.
  @Length(1)
  var bnRes:Array[Byte] = _ ;
    var nModTime:Int = _ ;	// modification time.
  // to here is 40 bytes long.
  @Length(8)
  var bnRes2:Array[Byte] = _ ;	// reserved 8 bytes

    @Length(80)
    var stVal:Array[Byte] = _
    /*
  var tagGBASE_PARAM_NETVA = new unionL
    {
      var nInt:Long = _ ;	// int value.
    @Length(80)
    var szVal:String = _ ;
    } // stVal;	// value
    */
  } ;	// size is 128 bytes long.

  class GBASE_PARAM_MEMITEM extends AncientData
  {
    var pszName:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// name of the parameter
    @Length(48 - 4)
    var bnRes:Array[Byte] = _
    /*
  var tagGBASE_PARAM_MEMVA = new unionL
    {
      var pnInt:String = _ //using 4 byte as pointer
    @IgnoreTransfer
    var pnInt_Data:Array[int] = _ // for pnInt pointer ,struct:int;
    var pnLongInt:String = _ //using 4 byte as pointer
    @IgnoreTransfer
    var pnLongInt_Data:Array[ga_int8] = _ // for pnLongInt pointer ,struct:ga_int8;
    var pszString:String = _ //using 4 byte as pointer
    @IgnoreTransfer
    var pszString_Data:Array[Byte] = _ // for pszString pointer ,struct:char;
    var pbnVoid:String = _ //using 4 byte as pointer
    @IgnoreTransfer
    var pbnVoid_Data:Array[Byte] = _ // for pbnVoid pointer ,struct:UCHAR;
    } // stVal;	// value pointer.
    FUNCT_PARAM_MOD	pfnMod;	// modification routine.
    void	*pValParam;	// value parameter(may be a structure contain valid value).
    UCHAR	bnRes_Pt[4*4];
    // to here is 32 bytes long
    UCHAR	nValType;	// GBASE_VALTYPE_xx.
    UCHAR	bnRes[1];	// reserved.
    short	nIndex;		// index.
    int		nModTime;	// modification time.
    int		nBufSize;	// if is string or void, then this value records the buffer size.
    UCHAR	bnRes2[4];	// reserved.
    */
  } ;	// SIZE IS 48 bytes long.

  //GBASE_PARAM_MEMITEM::nValType, GBASE_PARAM_NETITEM::nValType
  final val GBASE_VALTYPE_INT = 0
  final val GBASE_VALTYPE_STRING = 1
  final val GBASE_VALTYPE_BIN = 2	// binary data
  final val GBASE_VALTYPE_LONGINT = 3

  class GBASE_PARAMADMSTRUCT extends AncientData
  {
    @Length(32)
    var stGa:Array[Byte] =  _ //new GARRAY_STRUCT;	// ga rray.32 bytes long.
  var nLastModTime:Int = _;
    var bInited:Int = _;
    @Length(6)
    var nRes:Array[Int] = _;
    var stCrit = new GBASE_CRIT;	// 96 bytes long
  } // GBASE_PARAMADMSTRUCT;	// size is 128 + 32 bytes long.

  final val GBASE_PARAMACT_INIT = 0x1
  final val GBASE_PARAMACT_RELOAD = 0x2
  final val GBASE_PARAMACT_SETTODEFAULT = 0x3
  final val GBASE_PARAMACT_SETVAL = 0x4


  // set parameter used.
  final val GBASE_SETPARAM_MEMORY = 0x1
  final val GBASE_SETPARAM_INIFILE = 0x2

  // parameter name, globally referenced.
  class GBASE_PARAMNAMESTRUCT extends AncientData
  {
    var pszDBININAME:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBININAME_Data:Array[Byte] = _ // for pszDBININAME pointer ,struct:char;
  var pszDefaultUserName:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDefaultUserName_Data:Array[Byte] = _ // for pszDefaultUserName pointer ,struct:char;
  var pszCpuCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCpuCount_Data:Array[Byte] = _ // for pszCpuCount pointer ,struct:char;
  var pszPhysicalMemInMB:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPhysicalMemInMB_Data:Array[Byte] = _ // for pszPhysicalMemInMB pointer ,struct:char;
  var pszMainPath:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMainPath_Data:Array[Byte] = _ // for pszMainPath pointer ,struct:char;
  var pszOsPlatform:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszOsPlatform_Data:Array[Byte] = _ // for pszOsPlatform pointer ,struct:char;
  var pszOsType:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszOsType_Data:Array[Byte] = _ // for pszOsType pointer ,struct:char;
  var pszDEF_LANG:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDEF_LANG_Data:Array[Byte] = _ // for pszDEF_LANG pointer ,struct:char;
  var pszDEF_LANG_ID:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDEF_LANG_ID_Data:Array[Byte] = _ // for pszDEF_LANG_ID pointer ,struct:char;
  var pszLID_Message:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLID_Message_Data:Array[Byte] = _ // for pszLID_Message pointer ,struct:char;
  var pszLID_Time:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLID_Time_Data:Array[Byte] = _ // for pszLID_Time pointer ,struct:char;
  var pszLID_Date:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLID_Date_Data:Array[Byte] = _ // for pszLID_Date pointer ,struct:char;
  var pszCollateID_Latin:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCollateID_Latin_Data:Array[Byte] = _ // for pszCollateID_Latin pointer ,struct:char;
  var pszCollateID_MB:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCollateID_MB_Data:Array[Byte] = _ // for pszCollateID_MB pointer ,struct:char;
  var pszDBSvrQueryExpireTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBSvrQueryExpireTime_Data:Array[Byte] = _ // for pszDBSvrQueryExpireTime pointer ,struct:char;
  var pszTEMP_PATH:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTEMP_PATH_Data:Array[Byte] = _ // for pszTEMP_PATH pointer ,struct:char;
  var pszUnitCode:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
  var pszUnitName:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUnitName_Data:Array[Byte] = _ // for pszUnitName pointer ,struct:char;
  var pszMntFormat:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMntFormat_Data:Array[Byte] = _ // for pszMntFormat pointer ,struct:char;
  var pszMcastSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastSublib_Data:Array[Byte] = _ // for pszMcastSublib pointer ,struct:char;
  var pszUseTTFilter:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseTTFilter_Data:Array[Byte] = _ // for pszUseTTFilter pointer ,struct:char;
  var pszSublibFeat:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSublibFeat_Data:Array[Byte] = _ // for pszSublibFeat pointer ,struct:char;
  var pszMatchMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchMethod_Data:Array[Byte] = _ // for pszMatchMethod pointer ,struct:char;
  var pszMntExpireTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMntExpireTime_Data:Array[Byte] = _ // for pszMntExpireTime pointer ,struct:char;
  var pszMatchMaxIdleTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchMaxIdleTime_Data:Array[Byte] = _ // for pszMatchMaxIdleTime pointer ,struct:char;
  var pszNewDBMainPath:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszNewDBMainPath_Data:Array[Byte] = _ // for pszNewDBMainPath pointer ,struct:char;
  var pszmcast_ip_head:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszmcast_ip_head_Data:Array[Byte] = _ // for pszmcast_ip_head pointer ,struct:char;
  var pszmu_max_idle_time:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszmu_max_idle_time_Data:Array[Byte] = _ // for pszmu_max_idle_time pointer ,struct:char;
  var pszDBUpdateInterval:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBUpdateInterval_Data:Array[Byte] = _ // for pszDBUpdateInterval pointer ,struct:char;
  var pszMmuDownloadThreadCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMmuDownloadThreadCnt_Data:Array[Byte] = _ // for pszMmuDownloadThreadCnt pointer ,struct:char;
  var pszSmuDownloadThreadCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSmuDownloadThreadCnt_Data:Array[Byte] = _ // for pszSmuDownloadThreadCnt pointer ,struct:char;
  var pszGmuDownloadThreadCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGmuDownloadThreadCnt_Data:Array[Byte] = _ // for pszGmuDownloadThreadCnt pointer ,struct:char;
  var pszMUPrefetchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUPrefetchTime_Data:Array[Byte] = _ // for pszMUPrefetchTime pointer ,struct:char;
  var pszQryInvalidTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQryInvalidTime_Data:Array[Byte] = _ // for pszQryInvalidTime pointer ,struct:char;
  var pszMUAutoAddInactiveTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUAutoAddInactiveTime_Data:Array[Byte] = _ // for pszMUAutoAddInactiveTime pointer ,struct:char;
  var pszMUManuAddInactiveTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUManuAddInactiveTime_Data:Array[Byte] = _ // for pszMUManuAddInactiveTime pointer ,struct:char;
  var pszThreadMatchMaxRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadMatchMaxRowCnt_Data:Array[Byte] = _ // for pszThreadMatchMaxRowCnt pointer ,struct:char;
  var pszRowTol:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRowTol_Data:Array[Byte] = _ // for pszRowTol pointer ,struct:char;
  var pszMaxQryInQryList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxQryInQryList_Data:Array[Byte] = _ // for pszMaxQryInQryList pointer ,struct:char;
  var pszMinQryInQryList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMinQryInQryList_Data:Array[Byte] = _ // for pszMinQryInQryList pointer ,struct:char;
  var pszMaxTaskInLocTaskList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxTaskInLocTaskList_Data:Array[Byte] = _ // for pszMaxTaskInLocTaskList pointer ,struct:char;
  var pszMinTaskInLocTaskList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMinTaskInLocTaskList_Data:Array[Byte] = _ // for pszMinTaskInLocTaskList pointer ,struct:char;
  var pszMaxTaskInSysTaskList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxTaskInSysTaskList_Data:Array[Byte] = _ // for pszMaxTaskInSysTaskList pointer ,struct:char;
  var pszMinTaskInSysTaskList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMinTaskInSysTaskList_Data:Array[Byte] = _ // for pszMinTaskInSysTaskList pointer ,struct:char;
  var pszMinStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMinStepOneCandCount_Data:Array[Byte] = _ // for pszMinStepOneCandCount pointer ,struct:char;
  var pszMaxStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxStepOneCandCount_Data:Array[Byte] = _ // for pszMaxStepOneCandCount pointer ,struct:char;
  var pszStepOneRatio:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszStepOneRatio_Data:Array[Byte] = _ // for pszStepOneRatio pointer ,struct:char;
  var pszMinErrorTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMinErrorTime_Data:Array[Byte] = _ // for pszMinErrorTime pointer ,struct:char;
  var pszMaxErrorCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxErrorCnt_Data:Array[Byte] = _ // for pszMaxErrorCnt pointer ,struct:char;
  var pszTTNoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTNoStepTwo_Data:Array[Byte] = _ // for pszTTNoStepTwo pointer ,struct:char;
  var pszLTNoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTNoStepTwo_Data:Array[Byte] = _ // for pszLTNoStepTwo pointer ,struct:char;
  var pszLLNoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLNoStepTwo_Data:Array[Byte] = _ // for pszLLNoStepTwo pointer ,struct:char;
  var pszTLNoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLNoStepTwo_Data:Array[Byte] = _ // for pszTLNoStepTwo pointer ,struct:char;
  var pszMuReportInterval:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuReportInterval_Data:Array[Byte] = _ // for pszMuReportInterval pointer ,struct:char;
  var pszDefMaxCand:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDefMaxCand_Data:Array[Byte] = _ // for pszDefMaxCand pointer ,struct:char;
  var pszRecCntEachDownload:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRecCntEachDownload_Data:Array[Byte] = _ // for pszRecCntEachDownload pointer ,struct:char;
  var pszSysTaskMinWorkTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSysTaskMinWorkTime_Data:Array[Byte] = _ // for pszSysTaskMinWorkTime pointer ,struct:char;
  var pszSysTaskMaxWorkTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSysTaskMaxWorkTime_Data:Array[Byte] = _ // for pszSysTaskMaxWorkTime pointer ,struct:char;
  var pszLocTaskMinWorkTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocTaskMinWorkTime_Data:Array[Byte] = _ // for pszLocTaskMinWorkTime pointer ,struct:char;
  var pszLocTaskMaxWorkTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocTaskMaxWorkTime_Data:Array[Byte] = _ // for pszLocTaskMaxWorkTime pointer ,struct:char;
  var pszCandMergeMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCandMergeMethod_Data:Array[Byte] = _ // for pszCandMergeMethod pointer ,struct:char;
  var pszNetPrefetchIdleTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszNetPrefetchIdleTime_Data:Array[Byte] = _ // for pszNetPrefetchIdleTime pointer ,struct:char;
  var pszTCPServerMaxAllowedThreadCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTCPServerMaxAllowedThreadCnt_Data:Array[Byte] = _ // for pszTCPServerMaxAllowedThreadCnt pointer ,struct:char;
  var pszDBCreateIdleTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBCreateIdleTime_Data:Array[Byte] = _ // for pszDBCreateIdleTime pointer ,struct:char;
  var pszDataNeedPrefetch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDataNeedPrefetch_Data:Array[Byte] = _ // for pszDataNeedPrefetch pointer ,struct:char;
  var pszAutoLTQueryPriority:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszAutoLTQueryPriority_Data:Array[Byte] = _ // for pszAutoLTQueryPriority pointer ,struct:char;
  var pszAutoLLQueryPriority:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszAutoLLQueryPriority_Data:Array[Byte] = _ // for pszAutoLLQueryPriority pointer ,struct:char;
  var pszAutoTLQueryPriority:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszAutoTLQueryPriority_Data:Array[Byte] = _ // for pszAutoTLQueryPriority pointer ,struct:char;
  var pszAutoTTQueryPriority:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszAutoTTQueryPriority_Data:Array[Byte] = _ // for pszAutoTTQueryPriority pointer ,struct:char;
  var pszDBServerPort:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBServerPort_Data:Array[Byte] = _ // for pszDBServerPort pointer ,struct:char;
  var pszTotalClient:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTotalClient_Data:Array[Byte] = _ // for pszTotalClient pointer ,struct:char;
  var pszCurrentClient:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCurrentClient_Data:Array[Byte] = _ // for pszCurrentClient pointer ,struct:char;
  var pszCurrentThreadNum:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCurrentThreadNum_Data:Array[Byte] = _ // for pszCurrentThreadNum pointer ,struct:char;
  var pszDBMemLimitPercent:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBMemLimitPercent_Data:Array[Byte] = _ // for pszDBMemLimitPercent pointer ,struct:char;
  var pszDBModResTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBModResTime_Data:Array[Byte] = _ // for pszDBModResTime pointer ,struct:char;
  var pszXLogFileSizeInByte:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszXLogFileSizeInByte_Data:Array[Byte] = _ // for pszXLogFileSizeInByte pointer ,struct:char;
  var pszMachineUUID:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMachineUUID_Data:Array[Byte] = _ // for pszMachineUUID pointer ,struct:char;

    // following added on nov. 21, 2004.
    var pszMmuMcastSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMmuMcastSublib_Data:Array[Byte] = _ // for pszMmuMcastSublib pointer ,struct:char;
  var pszSmuMcastSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSmuMcastSublib_Data:Array[Byte] = _ // for pszSmuMcastSublib pointer ,struct:char;
  var pszGmuMcastSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGmuMcastSublib_Data:Array[Byte] = _ // for pszGmuMcastSublib pointer ,struct:char;
  var pszDelayMcastInMicro:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDelayMcastInMicro_Data:Array[Byte] = _ // for pszDelayMcastInMicro pointer ,struct:char;
  var pszMcastDataSizeLimit:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastDataSizeLimit_Data:Array[Byte] = _ // for pszMcastDataSizeLimit pointer ,struct:char;
  var pszRemoveSourceCardInTTLL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRemoveSourceCardInTTLL_Data:Array[Byte] = _ // for pszRemoveSourceCardInTTLL pointer ,struct:char;
  var pszThreadTTMatchMinRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTTMatchMinRowCnt_Data:Array[Byte] = _ // for pszThreadTTMatchMinRowCnt pointer ,struct:char;
  var pszThreadTLMatchMinRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTLMatchMinRowCnt_Data:Array[Byte] = _ // for pszThreadTLMatchMinRowCnt pointer ,struct:char;
  var pszThreadLTMatchMinRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLTMatchMinRowCnt_Data:Array[Byte] = _ // for pszThreadLTMatchMinRowCnt pointer ,struct:char;
  var pszThreadLLMatchMinRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLLMatchMinRowCnt_Data:Array[Byte] = _ // for pszThreadLLMatchMinRowCnt pointer ,struct:char;
  var pszTTSupportExactSearch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTSupportExactSearch_Data:Array[Byte] = _ // for pszTTSupportExactSearch pointer ,struct:char;
  var pszLTSupportExactSearch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTSupportExactSearch_Data:Array[Byte] = _ // for pszLTSupportExactSearch pointer ,struct:char;
  var pszTLSupportExactSearch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLSupportExactSearch_Data:Array[Byte] = _ // for pszTLSupportExactSearch pointer ,struct:char;
  var pszLLSupportExactSearch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLSupportExactSearch_Data:Array[Byte] = _ // for pszLLSupportExactSearch pointer ,struct:char;
  var pszMUGetTaskCalcRowCountMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUGetTaskCalcRowCountMethod_Data:Array[Byte] = _ // for pszMUGetTaskCalcRowCountMethod pointer ,struct:char;
  var pszMuTTMatchMinRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTTMatchMinRowCount_Data:Array[Byte] = _ // for pszMuTTMatchMinRowCount pointer ,struct:char;
  var pszMuTLMatchMinRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTLMatchMinRowCount_Data:Array[Byte] = _ // for pszMuTLMatchMinRowCount pointer ,struct:char;
  var pszMuLTMatchMinRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLTMatchMinRowCount_Data:Array[Byte] = _ // for pszMuLTMatchMinRowCount pointer ,struct:char;
  var pszMuLLMatchMinRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLLMatchMinRowCount_Data:Array[Byte] = _ // for pszMuLLMatchMinRowCount pointer ,struct:char;
  var pszMuTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTTPreferRowCount_Data:Array[Byte] = _ // for pszMuTTPreferRowCount pointer ,struct:char;
  var pszMuTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTLPreferRowCount_Data:Array[Byte] = _ // for pszMuTLPreferRowCount pointer ,struct:char;
  var pszMuLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLTPreferRowCount_Data:Array[Byte] = _ // for pszMuLTPreferRowCount pointer ,struct:char;
  var pszMuLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLLPreferRowCount_Data:Array[Byte] = _ // for pszMuLLPreferRowCount pointer ,struct:char;
  var pszThreadTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTTPreferRowCount_Data:Array[Byte] = _ // for pszThreadTTPreferRowCount pointer ,struct:char;
  var pszThreadTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTLPreferRowCount_Data:Array[Byte] = _ // for pszThreadTLPreferRowCount pointer ,struct:char;
  var pszThreadLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLTPreferRowCount_Data:Array[Byte] = _ // for pszThreadLTPreferRowCount pointer ,struct:char;
  var pszThreadLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLLPreferRowCount_Data:Array[Byte] = _ // for pszThreadLLPreferRowCount pointer ,struct:char;
  var pszThreadGetTaskCalcRowCountMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadGetTaskCalcRowCountMethod_Data:Array[Byte] = _ // for pszThreadGetTaskCalcRowCountMethod pointer ,struct:char;
  var pszMuTTPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTTPreferMatchTime_Data:Array[Byte] = _ // for pszMuTTPreferMatchTime pointer ,struct:char;
  var pszMuTLPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuTLPreferMatchTime_Data:Array[Byte] = _ // for pszMuTLPreferMatchTime pointer ,struct:char;
  var pszMuLTPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLTPreferMatchTime_Data:Array[Byte] = _ // for pszMuLTPreferMatchTime pointer ,struct:char;
  var pszMuLLPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuLLPreferMatchTime_Data:Array[Byte] = _ // for pszMuLLPreferMatchTime pointer ,struct:char;
  var pszThreadTTPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTTPreferMatchTime_Data:Array[Byte] = _ // for pszThreadTTPreferMatchTime pointer ,struct:char;
  var pszThreadTLPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadTLPreferMatchTime_Data:Array[Byte] = _ // for pszThreadTLPreferMatchTime pointer ,struct:char;
  var pszThreadLTPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLTPreferMatchTime_Data:Array[Byte] = _ // for pszThreadLTPreferMatchTime pointer ,struct:char;
  var pszThreadLLPreferMatchTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadLLPreferMatchTime_Data:Array[Byte] = _ // for pszThreadLLPreferMatchTime pointer ,struct:char;
  var pszMuPrefetchTaskMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuPrefetchTaskMethod_Data:Array[Byte] = _ // for pszMuPrefetchTaskMethod pointer ,struct:char;
  var pszLocPrefetchTaskMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocPrefetchTaskMethod_Data:Array[Byte] = _ // for pszLocPrefetchTaskMethod pointer ,struct:char;
  var pszNetPrefetchCheckTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszNetPrefetchCheckTime_Data:Array[Byte] = _ // for pszNetPrefetchCheckTime pointer ,struct:char;
  var pszLocPrefetchCheckTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLocPrefetchCheckTime_Data:Array[Byte] = _ // for pszLocPrefetchCheckTime pointer ,struct:char;
  var pszMaxMemCanUsedEachDownload:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxMemCanUsedEachDownload_Data:Array[Byte] = _ // for pszMaxMemCanUsedEachDownload pointer ,struct:char;
  var pszThreadGetTaskCheckTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadGetTaskCheckTime_Data:Array[Byte] = _ // for pszThreadGetTaskCheckTime pointer ,struct:char;
  var pszTTFilterMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTFilterMatchRowCount_Data:Array[Byte] = _ // for pszTTFilterMatchRowCount pointer ,struct:char;
  var pszTTStepOneMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTStepOneMatchRowCount_Data:Array[Byte] = _ // for pszTTStepOneMatchRowCount pointer ,struct:char;
  var pszTLStepOneMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLStepOneMatchRowCount_Data:Array[Byte] = _ // for pszTLStepOneMatchRowCount pointer ,struct:char;
  var pszLTStepOneMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTStepOneMatchRowCount_Data:Array[Byte] = _ // for pszLTStepOneMatchRowCount pointer ,struct:char;
  var pszLLStepOneMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLStepOneMatchRowCount_Data:Array[Byte] = _ // for pszLLStepOneMatchRowCount pointer ,struct:char;
  var pszTTStepTwoMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTStepTwoMatchRowCount_Data:Array[Byte] = _ // for pszTTStepTwoMatchRowCount pointer ,struct:char;
  var pszTLStepTwoMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLStepTwoMatchRowCount_Data:Array[Byte] = _ // for pszTLStepTwoMatchRowCount pointer ,struct:char;
  var pszLTStepTwoMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTStepTwoMatchRowCount_Data:Array[Byte] = _ // for pszLTStepTwoMatchRowCount pointer ,struct:char;
  var pszLLStepTwoMatchRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLStepTwoMatchRowCount_Data:Array[Byte] = _ // for pszLLStepTwoMatchRowCount pointer ,struct:char;
  var pszSupportPartialDB:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportPartialDB_Data:Array[Byte] = _ // for pszSupportPartialDB pointer ,struct:char;
  var pszMuCachePercent:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuCachePercent_Data:Array[Byte] = _ // for pszMuCachePercent pointer ,struct:char;
  var pszTTAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTAveSpeed_Data:Array[Byte] = _ // for pszTTAveSpeed pointer ,struct:char;
  var pszTLAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLAveSpeed_Data:Array[Byte] = _ // for pszTLAveSpeed pointer ,struct:char;
  var pszLTAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTAveSpeed_Data:Array[Byte] = _ // for pszLTAveSpeed pointer ,struct:char;
  var pszLLAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLAveSpeed_Data:Array[Byte] = _ // for pszLLAveSpeed pointer ,struct:char;
  var pszMcastSendDataMaxWaitTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastSendDataMaxWaitTime_Data:Array[Byte] = _ // for pszMcastSendDataMaxWaitTime pointer ,struct:char;
  var pszPalmTTAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTTAveSpeed_Data:Array[Byte] = _ // for pszPalmTTAveSpeed pointer ,struct:char;
  var pszPalmTLAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTLAveSpeed_Data:Array[Byte] = _ // for pszPalmTLAveSpeed pointer ,struct:char;
  var pszPalmLTAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLTAveSpeed_Data:Array[Byte] = _ // for pszPalmLTAveSpeed pointer ,struct:char;
  var pszPalmLLAveSpeed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLLAveSpeed_Data:Array[Byte] = _ // for pszPalmLLAveSpeed pointer ,struct:char;
  var pszPalmTTStepOneRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTTStepOneRowCnt_Data:Array[Byte] = _ // for pszPalmTTStepOneRowCnt pointer ,struct:char;
  var pszPalmTLStepOneRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTLStepOneRowCnt_Data:Array[Byte] = _ // for pszPalmTLStepOneRowCnt pointer ,struct:char;
  var pszPalmLTStepOneRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLTStepOneRowCnt_Data:Array[Byte] = _ // for pszPalmLTStepOneRowCnt pointer ,struct:char;
  var pszPalmLLStepOneRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLLStepOneRowCnt_Data:Array[Byte] = _ // for pszPalmLLStepOneRowCnt pointer ,struct:char;
  var pszPalmTTStepTwoRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTTStepTwoRowCnt_Data:Array[Byte] = _ // for pszPalmTTStepTwoRowCnt pointer ,struct:char;
  var pszPalmTLStepTwoRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmTLStepTwoRowCnt_Data:Array[Byte] = _ // for pszPalmTLStepTwoRowCnt pointer ,struct:char;
  var pszPalmLTStepTwoRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLTStepTwoRowCnt_Data:Array[Byte] = _ // for pszPalmLTStepTwoRowCnt pointer ,struct:char;
  var pszPalmLLStepTwoRowCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmLLStepTwoRowCnt_Data:Array[Byte] = _ // for pszPalmLLStepTwoRowCnt pointer ,struct:char;
  var pszCalcSpeedMinRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCalcSpeedMinRowCount_Data:Array[Byte] = _ // for pszCalcSpeedMinRowCount pointer ,struct:char;
  var pszCalcSpeedRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCalcSpeedRowCount_Data:Array[Byte] = _ // for pszCalcSpeedRowCount pointer ,struct:char;
  var pszPreGetStepTwoData:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPreGetStepTwoData_Data:Array[Byte] = _ // for pszPreGetStepTwoData pointer ,struct:char;
  var pszSublibDropNonExistCol:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSublibDropNonExistCol_Data:Array[Byte] = _ // for pszSublibDropNonExistCol pointer ,struct:char;
  var pszQryDeadTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQryDeadTime_Data:Array[Byte] = _ // for pszQryDeadTime pointer ,struct:char;
  var pszTLKeepFingerCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLKeepFingerCount_Data:Array[Byte] = _ // for pszTLKeepFingerCount pointer ,struct:char;
  var pszErrorIfNoDestDB:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszErrorIfNoDestDB_Data:Array[Byte] = _ // for pszErrorIfNoDestDB pointer ,struct:char;
  var pszViewAsBlobLen:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszViewAsBlobLen_Data:Array[Byte] = _ // for pszViewAsBlobLen pointer ,struct:char;
  var pszMcastServerPort:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastServerPort_Data:Array[Byte] = _ // for pszMcastServerPort pointer ,struct:char;
  var pszSearchTPlainFinger:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSearchTPlainFinger_Data:Array[Byte] = _ // for pszSearchTPlainFinger pointer ,struct:char;
  var pszPalmAsBin:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmAsBin_Data:Array[Byte] = _ // for pszPalmAsBin pointer ,struct:char;
  var pszSupportPalm:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportPalm_Data:Array[Byte] = _ // for pszSupportPalm pointer ,struct:char;
  var pszSupportFinger:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportFinger_Data:Array[Byte] = _ // for pszSupportFinger pointer ,struct:char;
  var pszDatabaseType:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDatabaseType_Data:Array[Byte] = _ // for pszDatabaseType pointer ,struct:char;
  var pszMaxTextItemLen:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxTextItemLen_Data:Array[Byte] = _ // for pszMaxTextItemLen pointer ,struct:char;
  var pszImgStandard:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszImgStandard_Data:Array[Byte] = _ // for pszImgStandard pointer ,struct:char;
  var pszMmuWillDoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMmuWillDoStepTwo_Data:Array[Byte] = _ // for pszMmuWillDoStepTwo pointer ,struct:char;
  var pszSmuWillDoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSmuWillDoStepTwo_Data:Array[Byte] = _ // for pszSmuWillDoStepTwo pointer ,struct:char;
  var pszGmuWillDoStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGmuWillDoStepTwo_Data:Array[Byte] = _ // for pszGmuWillDoStepTwo pointer ,struct:char;
  var pszMcDropRangeCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcDropRangeCnt_Data:Array[Byte] = _ // for pszMcDropRangeCnt pointer ,struct:char;
  var pszMcastPrintDetailInfo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastPrintDetailInfo_Data:Array[Byte] = _ // for pszMcastPrintDetailInfo pointer ,struct:char;
  var pszServerStartupTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszServerStartupTime_Data:Array[Byte] = _ // for pszServerStartupTime pointer ,struct:char;
  var pszSystemStartupTimeInt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSystemStartupTimeInt_Data:Array[Byte] = _ // for pszSystemStartupTimeInt pointer ,struct:char;
  var pszSupportStackTrace:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportStackTrace_Data:Array[Byte] = _ // for pszSupportStackTrace pointer ,struct:char;
  var pszSupportException:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportException_Data:Array[Byte] = _ // for pszSupportException pointer ,struct:char;
  var pszFastExpandFile:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszFastExpandFile_Data:Array[Byte] = _ // for pszFastExpandFile pointer ,struct:char;
  var pszHiResoTickPerSec:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszHiResoTickPerSec_Data:Array[Byte] = _ // for pszHiResoTickPerSec pointer ,struct:char;
  var pszCounter_WriteTimeUsed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_WriteTimeUsed_Data:Array[Byte] = _ // for pszCounter_WriteTimeUsed pointer ,struct:char;
  var pszCounter_ReadTimeUsed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_ReadTimeUsed_Data:Array[Byte] = _ // for pszCounter_ReadTimeUsed pointer ,struct:char;
  var pszSystemStartupTime:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSystemStartupTime_Data:Array[Byte] = _ // for pszSystemStartupTime pointer ,struct:char;
  var pszDisableTxtLog:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDisableTxtLog_Data:Array[Byte] = _ // for pszDisableTxtLog pointer ,struct:char;
  var pszCounter_WriteByteCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_WriteByteCount_Data:Array[Byte] = _ // for pszCounter_WriteByteCount pointer ,struct:char;
  var pszCounter_ReadByteCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_ReadByteCount_Data:Array[Byte] = _ // for pszCounter_ReadByteCount pointer ,struct:char;
  var pszCounter_WriteCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_WriteCount_Data:Array[Byte] = _ // for pszCounter_WriteCount pointer ,struct:char;
  var pszCounter_ReadCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCounter_ReadCount_Data:Array[Byte] = _ // for pszCounter_ReadCount pointer ,struct:char;
  var pszBackupErrLogFile:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszBackupErrLogFile_Data:Array[Byte] = _ // for pszBackupErrLogFile pointer ,struct:char;
  var pszSockSendTimeOut:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSockSendTimeOut_Data:Array[Byte] = _ // for pszSockSendTimeOut pointer ,struct:char;
  var pszSockRecvTimeOut:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSockRecvTimeOut_Data:Array[Byte] = _ // for pszSockRecvTimeOut pointer ,struct:char;
  var pszMcastCliMaxMemUsed:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastCliMaxMemUsed_Data:Array[Byte] = _ // for pszMcastCliMaxMemUsed pointer ,struct:char;
  var pszUseAsyncNMF:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUseAsyncNMF_Data:Array[Byte] = _ // for pszUseAsyncNMF pointer ,struct:char;
  var pszMuStepTwoTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuStepTwoTTPreferRowCount_Data:Array[Byte] = _ // for pszMuStepTwoTTPreferRowCount pointer ,struct:char;
  var pszMuStepTwoTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuStepTwoTLPreferRowCount_Data:Array[Byte] = _ // for pszMuStepTwoTLPreferRowCount pointer ,struct:char;
  var pszMuStepTwoLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuStepTwoLTPreferRowCount_Data:Array[Byte] = _ // for pszMuStepTwoLTPreferRowCount pointer ,struct:char;
  var pszMuStepTwoLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMuStepTwoLLPreferRowCount_Data:Array[Byte] = _ // for pszMuStepTwoLLPreferRowCount pointer ,struct:char;
  var pszThreadStepTwoTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadStepTwoTTPreferRowCount_Data:Array[Byte] = _ // for pszThreadStepTwoTTPreferRowCount pointer ,struct:char;
  var pszThreadStepTwoTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadStepTwoTLPreferRowCount_Data:Array[Byte] = _ // for pszThreadStepTwoTLPreferRowCount pointer ,struct:char;
  var pszThreadStepTwoLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadStepTwoLTPreferRowCount_Data:Array[Byte] = _ // for pszThreadStepTwoLTPreferRowCount pointer ,struct:char;
  var pszThreadStepTwoLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszThreadStepTwoLLPreferRowCount_Data:Array[Byte] = _ // for pszThreadStepTwoLLPreferRowCount pointer ,struct:char;
  var pszMcastFlowControlInKB:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastFlowControlInKB_Data:Array[Byte] = _ // for pszMcastFlowControlInKB pointer ,struct:char;
  var pszMcastTTL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastTTL_Data:Array[Byte] = _ // for pszMcastTTL pointer ,struct:char;
  var pszMcastBindIF:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMcastBindIF_Data:Array[Byte] = _ // for pszMcastBindIF pointer ,struct:char;
  var pszEnableMatchWhileSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEnableMatchWhileSublib_Data:Array[Byte] = _ // for pszEnableMatchWhileSublib pointer ,struct:char;
  var pszEnableMcSvrMatchWhileSublib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEnableMcSvrMatchWhileSublib_Data:Array[Byte] = _ // for pszEnableMcSvrMatchWhileSublib pointer ,struct:char;
  var pszMaxCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxCandCount_Data:Array[Byte] = _ // for pszMaxCandCount pointer ,struct:char;
  var pszResublibRowPercent:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszResublibRowPercent_Data:Array[Byte] = _ // for pszResublibRowPercent pointer ,struct:char;
  var pszSockSafeClose:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSockSafeClose_Data:Array[Byte] = _ // for pszSockSafeClose pointer ,struct:char;

    var pszPalmMuTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuTTPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuTTPreferRowCount pointer ,struct:char;
  var pszPalmMuTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuTLPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuTLPreferRowCount pointer ,struct:char;
  var pszPalmMuLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuLTPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuLTPreferRowCount pointer ,struct:char;
  var pszPalmMuLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuLLPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuLLPreferRowCount pointer ,struct:char;
  var pszPalmThreadTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadTTPreferRowCount_Data:Array[Byte] = _ // for pszPalmThreadTTPreferRowCount pointer ,struct:char;
  var pszPalmThreadTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadTLPreferRowCount_Data:Array[Byte] = _ // for pszPalmThreadTLPreferRowCount pointer ,struct:char;
  var pszPalmThreadLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadLTPreferRowCount_Data:Array[Byte] = _ // for pszPalmThreadLTPreferRowCount pointer ,struct:char;
  var pszPalmThreadLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadLLPreferRowCount_Data:Array[Byte] = _ // for pszPalmThreadLLPreferRowCount pointer ,struct:char;

    var pszPalmMuStepTwoTTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuStepTwoTTPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuStepTwoTTPreferRowCount pointer ,struct:char;
  var pszPalmMuStepTwoTLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuStepTwoTLPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuStepTwoTLPreferRowCount pointer ,struct:char;
  var pszPalmMuStepTwoLTPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuStepTwoLTPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuStepTwoLTPreferRowCount pointer ,struct:char;
  var pszPalmMuStepTwoLLPreferRowCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmMuStepTwoLLPreferRowCount_Data:Array[Byte] = _ // for pszPalmMuStepTwoLLPreferRowCount pointer ,struct:char;
  var pszPalmThreadStepTwoTTPRC:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadStepTwoTTPRC_Data:Array[Byte] = _ // for pszPalmThreadStepTwoTTPRC pointer ,struct:char;
  var pszPalmThreadStepTwoTLPRC:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadStepTwoTLPRC_Data:Array[Byte] = _ // for pszPalmThreadStepTwoTLPRC pointer ,struct:char;
  var pszPalmThreadStepTwoLTPRC:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadStepTwoLTPRC_Data:Array[Byte] = _ // for pszPalmThreadStepTwoLTPRC pointer ,struct:char;
  var pszPalmThreadStepTwoLLPRC:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPalmThreadStepTwoLLPRC_Data:Array[Byte] = _ // for pszPalmThreadStepTwoLLPRC pointer ,struct:char;


    var pszLeastScoreTT:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLeastScoreTT_Data:Array[Byte] = _ // for pszLeastScoreTT pointer ,struct:char;
  var pszLeastScoreTL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLeastScoreTL_Data:Array[Byte] = _ // for pszLeastScoreTL pointer ,struct:char;
  var pszLeastScoreLT:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLeastScoreLT_Data:Array[Byte] = _ // for pszLeastScoreLT pointer ,struct:char;
  var pszLeastScoreLL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLeastScoreLL_Data:Array[Byte] = _ // for pszLeastScoreLL pointer ,struct:char;


    var pszRefCPUFrequence:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRefCPUFrequence_Data:Array[Byte] = _ // for pszRefCPUFrequence pointer ,struct:char;

    var pszDBSkmComptDate:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBSkmComptDate_Data:Array[Byte] = _ // for pszDBSkmComptDate pointer ,struct:char;
  var pszGAFISKeepLatestSkm:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGAFISKeepLatestSkm_Data:Array[Byte] = _ // for pszGAFISKeepLatestSkm pointer ,struct:char;
  var pszGAFISKeepOldColumn:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGAFISKeepOldColumn_Data:Array[Byte] = _ // for pszGAFISKeepOldColumn pointer ,struct:char;
  var pszForceRemoveSelfByKeyInTTLL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszForceRemoveSelfByKeyInTTLL_Data:Array[Byte] = _ // for pszForceRemoveSelfByKeyInTTLL pointer ,struct:char;
  var pszInstantQueryGet:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszInstantQueryGet_Data:Array[Byte] = _ // for pszInstantQueryGet pointer ,struct:char;
  var pszFinishQueryNotify:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszFinishQueryNotify_Data:Array[Byte] = _ // for pszFinishQueryNotify pointer ,struct:char;
  var pszSupportFace:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportFace_Data:Array[Byte] = _ // for pszSupportFace pointer ,struct:char;
  var pszSupportVoice:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSupportVoice_Data:Array[Byte] = _ // for pszSupportVoice pointer ,struct:char;

    // the following is cpu prop.
    var pszRefCPUSpeedInMhz:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRefCPUSpeedInMhz_Data:Array[Byte] = _ // for pszRefCPUSpeedInMhz pointer ,struct:char;
  var pszExceptionTerminate:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszExceptionTerminate_Data:Array[Byte] = _ // for pszExceptionTerminate pointer ,struct:char;


    //////////////////////////////////////////////////////////////////////////
    var pszTPFaceMntAsBin:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTPFaceMntAsBin_Data:Array[Byte] = _ // for pszTPFaceMntAsBin pointer ,struct:char;
  var pszTPVoiceMntAsBin:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTPVoiceMntAsBin_Data:Array[Byte] = _ // for pszTPVoiceMntAsBin pointer ,struct:char;
  var pszSearchFace:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSearchFace_Data:Array[Byte] = _ // for pszSearchFace pointer ,struct:char;
  var pszSearchVoice:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSearchVoice_Data:Array[Byte] = _ // for pszSearchVoice pointer ,struct:char;
    ////////////////////////////////////////

    var pszLTMergeDupCard:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTMergeDupCard_Data:Array[Byte] = _ // for pszLTMergeDupCard pointer ,struct:char;
  var pszDownLoadBinMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDownLoadBinMethod_Data:Array[Byte] = _ // for pszDownLoadBinMethod pointer ,struct:char;	// down load bin data method.

    ///////////// following added on oct. 8, 2005
    var pszEXFDoRollPlainMatch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEXFDoRollPlainMatch_Data:Array[Byte] = _ // for pszEXFDoRollPlainMatch pointer ,struct:char;
  var pszEXFDoRollPlainPatternReplace:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEXFDoRollPlainPatternReplace_Data:Array[Byte] = _ // for pszEXFDoRollPlainPatternReplace pointer ,struct:char;
  var pszEXFRollPlainUnmatchScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEXFRollPlainUnmatchScore_Data:Array[Byte] = _ // for pszEXFRollPlainUnmatchScore pointer ,struct:char;
  var pszEXFRollPlainMatchScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEXFRollPlainMatchScore_Data:Array[Byte] = _ // for pszEXFRollPlainMatchScore pointer ,struct:char;
  var pszEditDoRollPlainMatch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEditDoRollPlainMatch_Data:Array[Byte] = _ // for pszEditDoRollPlainMatch pointer ,struct:char;
  var pszEditDoRollPlainPatternReplace:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEditDoRollPlainPatternReplace_Data:Array[Byte] = _ // for pszEditDoRollPlainPatternReplace pointer ,struct:char;
  var pszEditRollPlainUnmatchScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEditRollPlainUnmatchScore_Data:Array[Byte] = _ // for pszEditRollPlainUnmatchScore pointer ,struct:char;
  var pszEditRollPlainMatchScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEditRollPlainMatchScore_Data:Array[Byte] = _ // for pszEditRollPlainMatchScore pointer ,struct:char;
    ///////////// above lines are added on oct. 8, 2005

    //////////// following are added on nov. 18, 2005
    var pszLiveScanMinImageQuality:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanMinImageQuality_Data:Array[Byte] = _ // for pszLiveScanMinImageQuality pointer ,struct:char;
  var pszLiveScanGoodImageQuality:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanGoodImageQuality_Data:Array[Byte] = _ // for pszLiveScanGoodImageQuality pointer ,struct:char;
  var pszLiveScanDoCrossCheck:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanDoCrossCheck_Data:Array[Byte] = _ // for pszLiveScanDoCrossCheck pointer ,struct:char;
  var pszLiveScanCrossCheckScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanCrossCheckScore_Data:Array[Byte] = _ // for pszLiveScanCrossCheckScore pointer ,struct:char;
  var pszLiveScanDoSameFingerCheck:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanDoSameFingerCheck_Data:Array[Byte] = _ // for pszLiveScanDoSameFingerCheck pointer ,struct:char;
  var pszLiveScanSameFingerScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanSameFingerScore_Data:Array[Byte] = _ // for pszLiveScanSameFingerScore pointer ,struct:char;
    //////////// above added on nov. 18, 2005

    //// following are added on Dec. 15, 2005
    var pszPolicyNonAdmViewAllQuery:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPolicyNonAdmViewAllQuery_Data:Array[Byte] = _ // for pszPolicyNonAdmViewAllQuery pointer ,struct:char;
  var pszPolicyNonAdmViewAllLatData:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPolicyNonAdmViewAllLatData_Data:Array[Byte] = _ // for pszPolicyNonAdmViewAllLatData pointer ,struct:char;
  var pszPolicyNonAdmEditAllLatData:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPolicyNonAdmEditAllLatData_Data:Array[Byte] = _ // for pszPolicyNonAdmEditAllLatData pointer ,struct:char;
  var pszPolicyQueryNeedReCheck:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPolicyQueryNeedReCheck_Data:Array[Byte] = _ // for pszPolicyQueryNeedReCheck pointer ,struct:char;
    //// above are added on Dec. 15, 2005

    ///// following are added on Jan. 13, 2006
    var pszLiveScanWillEditFinger:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanWillEditFinger_Data:Array[Byte] = _ // for pszLiveScanWillEditFinger pointer ,struct:char;
  ///// above are added on Jan. 13, 2006
  //// following are added on Feb. 22, 2006
  var pszLiveScanFingerEnforced:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanFingerEnforced_Data:Array[Byte] = _ // for pszLiveScanFingerEnforced pointer ,struct:char;
  var pszLiveScanPlainFingerChoice:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLiveScanPlainFingerChoice_Data:Array[Byte] = _ // for pszLiveScanPlainFingerChoice pointer ,struct:char;
    ///// above are added on Feb. 22. 2006

    var pszTTCanUsePlain:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTCanUsePlain_Data:Array[Byte] = _ // for pszTTCanUsePlain pointer ,struct:char;
  var pszListenBacklogCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszListenBacklogCnt_Data:Array[Byte] = _ // for pszListenBacklogCnt pointer ,struct:char;

    //
    var pszGfpManagerModule_QryMaxCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGfpManagerModule_QryMaxCnt_Data:Array[Byte] = _ // for pszGfpManagerModule_QryMaxCnt pointer ,struct:char;
  var pszGfpManagerModule_CardMaxCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGfpManagerModule_CardMaxCnt_Data:Array[Byte] = _ // for pszGfpManagerModule_CardMaxCnt pointer ,struct:char;
  var pszQueryModule_QryMaxCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueryModule_QryMaxCnt_Data:Array[Byte] = _ // for pszQueryModule_QryMaxCnt pointer ,struct:char;
  var pszQueryModule_CardMaxCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueryModule_CardMaxCnt_Data:Array[Byte] = _ // for pszQueryModule_CardMaxCnt pointer ,struct:char;
  var pszEditModule_OnlyOwnerKey:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszEditModule_OnlyOwnerKey_Data:Array[Byte] = _ // for pszEditModule_OnlyOwnerKey pointer ,struct:char;
  var pszQueTTSearchAfterEdit:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTTSearchAfterEdit_Data:Array[Byte] = _ // for pszQueTTSearchAfterEdit pointer ,struct:char;
  var pszQueTLSearchAfterEdit:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTLSearchAfterEdit_Data:Array[Byte] = _ // for pszQueTLSearchAfterEdit pointer ,struct:char;
  var pszQueLTSearchAfterEdit:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLTSearchAfterEdit_Data:Array[Byte] = _ // for pszQueLTSearchAfterEdit pointer ,struct:char;
  var pszQueLLSearchAfterEdit:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLLSearchAfterEdit_Data:Array[Byte] = _ // for pszQueLLSearchAfterEdit pointer ,struct:char;

    var pszQueSupportPalmTL:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueSupportPalmTL_Data:Array[Byte] = _ // for pszQueSupportPalmTL pointer ,struct:char;
  var pszQualCheckSupportQue:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQualCheckSupportQue_Data:Array[Byte] = _ // for pszQualCheckSupportQue pointer ,struct:char;
  var pszQrySourceSupportMulti:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQrySourceSupportMulti_Data:Array[Byte] = _ // for pszQrySourceSupportMulti pointer ,struct:char;

    var pszAutoMISPersonID:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszAutoMISPersonID_Data:Array[Byte] = _ // for pszAutoMISPersonID pointer ,struct:char;

    var pszStepTwoBothRollAndPlain:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszStepTwoBothRollAndPlain_Data:Array[Byte] = _ // for pszStepTwoBothRollAndPlain pointer ,struct:char;

    // added on Dec. 29, 2006.
    var pszTLMergeByFgGroup:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMergeByFgGroup_Data:Array[Byte] = _ // for pszTLMergeByFgGroup pointer ,struct:char;
  var pszTLCaseSuperviseList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLCaseSuperviseList_Data:Array[Byte] = _ // for pszTLCaseSuperviseList pointer ,struct:char;
  var pszTLUpgradeCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLUpgradeCount_Data:Array[Byte] = _ // for pszTLUpgradeCount pointer ,struct:char;
  var pszLLMergeByFgGroup:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLMergeByFgGroup_Data:Array[Byte] = _ // for pszLLMergeByFgGroup pointer ,struct:char;
  var pszLLCaseSuperviseList:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLCaseSuperviseList_Data:Array[Byte] = _ // for pszLLCaseSuperviseList pointer ,struct:char;
  var pszLLUpgradeCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLUpgradeCount_Data:Array[Byte] = _ // for pszLLUpgradeCount pointer ,struct:char;
  var pszTLMergeCandAveFgMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMergeCandAveFgMethod_Data:Array[Byte] = _ // for pszTLMergeCandAveFgMethod pointer ,struct:char;
  var pszTLMergeCandAveFgBuf:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMergeCandAveFgBuf_Data:Array[Byte] = _ // for pszTLMergeCandAveFgBuf pointer ,struct:char;
  var pszMUKeepOldColumn:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUKeepOldColumn_Data:Array[Byte] = _ // for pszMUKeepOldColumn pointer ,struct:char;
  var pszMUKeepLatestSkm:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMUKeepLatestSkm_Data:Array[Byte] = _ // for pszMUKeepLatestSkm pointer ,struct:char;
  // added on Mar.05, 2008
  var pszCandCountForTextSearch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCandCountForTextSearch_Data:Array[Byte] = _ // for pszCandCountForTextSearch pointer ,struct:char;

    /** added on Feb. 27, 2009
      * 1、图像压缩方法、压缩比率参数名
      * 2、掌纹扫描时（包括活体）是否备份一份无损压缩到硬盘上，
      *    主要目的是一旦有了更好的掌纹特征提取算法，可以重新从备份中重提特征
      */
    var pszImageCprMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszImageCprMethod_Data:Array[Byte] = _ // for pszImageCprMethod pointer ,struct:char;
  var pszImageCprRatio:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszImageCprRatio_Data:Array[Byte] = _ // for pszImageCprRatio pointer ,struct:char;

    var pszBackupPalmData2Disk:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszBackupPalmData2Disk_Data:Array[Byte] = _ // for pszBackupPalmData2Disk pointer ,struct:char;

    /**
     * added on Step. 7, 2009
     * 增加一个bFingerAsBin，缺省为BIN，但是对于王曙光的特征，比对服务器会用LOB来存储
     */
    var pszFingerAsBin:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszFingerAsBin_Data:Array[Byte] = _ // for pszFingerAsBin pointer ,struct:char;

    /**
     * added on Setp.6, 2010
     * 	把先前的nMinStepOneCandCount参数按查询类型分成4个
     */
    var pszTTMinStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTMinStepOneCandCount_Data:Array[Byte] = _ // for pszTTMinStepOneCandCount pointer ,struct:char;
  var pszTLMinStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMinStepOneCandCount_Data:Array[Byte] = _ // for pszTLMinStepOneCandCount pointer ,struct:char;
  var pszLTMinStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTMinStepOneCandCount_Data:Array[Byte] = _ // for pszLTMinStepOneCandCount pointer ,struct:char;
  var pszLLMinStepOneCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLMinStepOneCandCount_Data:Array[Byte] = _ // for pszLLMinStepOneCandCount pointer ,struct:char;

    /**
     * 用来标记在调用GSCH_UTIL_CalcStepTwoCandCount函数计算候选个数时，
     * 是否直接使用指定的候选个数，缺省为0(会有一个放大算法来调整候选的个数)
     */
    var pszStepTwoCandCountEqualStepOne:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszStepTwoCandCountEqualStepOne_Data:Array[Byte] = _ // for pszStepTwoCandCountEqualStepOne pointer ,struct:char;

    /**
     * 增加一个参数用来标记是否删除在数据库或比对服务器上不存在的分库，缺省为不删除
     * 以免在出现网络问题、误操作、或其它无法预料的问题把分库给删除了
     */
    var pszDelNotExistSubLib:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDelNotExistSubLib_Data:Array[Byte] = _ // for pszDelNotExistSubLib pointer ,struct:char;

    /**
     * Added by beagle on Feb. 24, 2010
     * 预取LOB数据时，每个线程读取的行数，原先是硬编码在代码中（值为100），现在把它作为
     * 比对服务器端的参数，缺省值是50
     */
    var pszRowCntPerThreadToPrefLobRead:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRowCntPerThreadToPrefLobRead_Data:Array[Byte] = _ // for pszRowCntPerThreadToPrefLobRead pointer ,struct:char;
  var pszMaxThreadToPrefLobRead:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMaxThreadToPrefLobRead_Data:Array[Byte] = _ // for pszMaxThreadToPrefLobRead pointer ,struct:char;

    var pszCandMergeStyle:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCandMergeStyle_Data:Array[Byte] = _ // for pszCandMergeStyle pointer ,struct:char;		//<! 不同算法产生的候选的合并方式

    /**
     * 是否在LT查询的候选结果中进行TT查重，对于候选中的重卡，我们给其分组，在GAQUERYCANDSTRUCT中增加一个group字段
     * 从第一个候选开始往下进行TT查重，group值从1开始递增，具有相同group值的卡就是属于同一个重卡组，group为0表示没有重卡
     */
    var pszSearchDupCardInLTCand:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSearchDupCardInLTCand_Data:Array[Byte] = _ // for pszSearchDupCardInLTCand pointer ,struct:char;		//!< 是否在LT查询候选中进行TT重卡查询，缺省为0
  var pszLTDupCard_MaxSearchedCand:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTDupCard_MaxSearchedCand_Data:Array[Byte] = _ // for pszLTDupCard_MaxSearchedCand pointer ,struct:char;	//!< 进行TT重卡查询时，缺省为只查询前50个候选，范围为[5,250]
  var pszLTDupCard_SearchingRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTDupCard_SearchingRank_Data:Array[Byte] = _ // for pszLTDupCard_SearchingRank pointer ,struct:char;	//!< 在进行TT重卡查询时，有多少个候选被进行了TT查重，缺省为分别只对各自算法的前10个候选；

    var pszGDQueryTestOption:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGDQueryTestOption_Data:Array[Byte] = _ // for pszGDQueryTestOption pointer ,struct:char;	//!< 广东比对测试的一些选项设置

    /**
     * 许公望、王曙光的TT比中阈值，合并TT查询的许公望和王曙光的候选使用的参数
     */
    var pszTTAutoMergeCandScore:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTAutoMergeCandScore_Data:Array[Byte] = _ // for pszTTAutoMergeCandScore pointer ,struct:char;
  var pszTT_WsgThreshold:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTT_WsgThreshold_Data:Array[Byte] = _ // for pszTT_WsgThreshold pointer ,struct:char;

    var pszGAFIS_SystemRunMode:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGAFIS_SystemRunMode_Data:Array[Byte] = _ // for pszGAFIS_SystemRunMode pointer ,struct:char;

    var pszSearchDupCardInTLCand:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszSearchDupCardInTLCand_Data:Array[Byte] = _ // for pszSearchDupCardInTLCand pointer ,struct:char;		//!< 是否在TL查询候选中进行LL串查查询，缺省为0
  var pszTLDupCard_MaxSearchedCand:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLDupCard_MaxSearchedCand_Data:Array[Byte] = _ // for pszTLDupCard_MaxSearchedCand pointer ,struct:char;	//!< 进行LL重卡查询时，缺省为只查询前50个候选，范围为[5,250]
  var pszTLDupCard_SearchingRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLDupCard_SearchingRank_Data:Array[Byte] = _ // for pszTLDupCard_SearchingRank pointer ,struct:char;	//!< 在进行LL重卡查询时，有多少个候选被进行了LL串查，缺省为分别只对各自算法的前10个候选；
  var pszTLDupCard_LLHitThreshold:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLDupCard_LLHitThreshold_Data:Array[Byte] = _ // for pszTLDupCard_LLHitThreshold pointer ,struct:char;	//!< 在TL候选中进行LL串查，比中（认为是重卡）的阈值，缺省为700

    var pszLTCalcStepTwoCandCntMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTCalcStepTwoCandCntMethod_Data:Array[Byte] = _ // for pszLTCalcStepTwoCandCntMethod pointer ,struct:char;	//!< CALCSTEPTWOCANDCNTMETHOD_xxxx
  var pszTTCalcStepTwoCandCntMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTCalcStepTwoCandCntMethod_Data:Array[Byte] = _ // for pszTTCalcStepTwoCandCntMethod pointer ,struct:char;
  var pszTLCalcStepTwoCandCntMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLCalcStepTwoCandCntMethod_Data:Array[Byte] = _ // for pszTLCalcStepTwoCandCntMethod pointer ,struct:char;
  var pszLLCalcStepTwoCandCntMethod:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLCalcStepTwoCandCntMethod_Data:Array[Byte] = _ // for pszLLCalcStepTwoCandCntMethod pointer ,struct:char;

    var pszLTStepTwoCandCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTStepTwoCandCnt_Data:Array[Byte] = _ // for pszLTStepTwoCandCnt pointer ,struct:char;
  var pszTTStepTwoCandCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTStepTwoCandCnt_Data:Array[Byte] = _ // for pszTTStepTwoCandCnt pointer ,struct:char;
  var pszTLStepTwoCandCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLStepTwoCandCnt_Data:Array[Byte] = _ // for pszTLStepTwoCandCnt pointer ,struct:char;
  var pszLLStepTwoCandCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLLStepTwoCandCnt_Data:Array[Byte] = _ // for pszLLStepTwoCandCnt pointer ,struct:char;

    /**
     * 广东模式下，LT合并算法用到的参数，详细说明请参考“广州测试候选合并方式及相关参数.docx”文档
     * 加权系统Q、X、W、Beta
     */
    var pszLTXWCandMerge_Q:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_Q_Data:Array[Byte] = _ // for pszLTXWCandMerge_Q pointer ,struct:char;
  var pszLTXWCandMerge_X:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_X_Data:Array[Byte] = _ // for pszLTXWCandMerge_X pointer ,struct:char;
  var pszLTXWCandMerge_W:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_W_Data:Array[Byte] = _ // for pszLTXWCandMerge_W pointer ,struct:char;
  var pszLTXWCandMerge_Beta:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_Beta_Data:Array[Byte] = _ // for pszLTXWCandMerge_Beta pointer ,struct:char;
  var pszLTXWCandMerge_ScoreRatio:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_ScoreRatio_Data:Array[Byte] = _ // for pszLTXWCandMerge_ScoreRatio pointer ,struct:char;
  var pszLTXWCandMerge_CandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_CandCount_Data:Array[Byte] = _ // for pszLTXWCandMerge_CandCount pointer ,struct:char;	//!< 在进行合并时，最多使用的候选个数，缺省为2000个

    var pszTLXWCandMerge_Q:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_Q_Data:Array[Byte] = _ // for pszTLXWCandMerge_Q pointer ,struct:char;
  var pszTLXWCandMerge_X:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_X_Data:Array[Byte] = _ // for pszTLXWCandMerge_X pointer ,struct:char;
  var pszTLXWCandMerge_W:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_W_Data:Array[Byte] = _ // for pszTLXWCandMerge_W pointer ,struct:char;
  var pszTLXWCandMerge_Beta:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_Beta_Data:Array[Byte] = _ // for pszTLXWCandMerge_Beta pointer ,struct:char;
  var pszTLXWCandMerge_Option:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_Option_Data:Array[Byte] = _ // for pszTLXWCandMerge_Option pointer ,struct:char;
  var pszTLXWCandMerge_CandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_CandCount_Data:Array[Byte] = _ // for pszTLXWCandMerge_CandCount pointer ,struct:char;	//!< 在进行合并时，最多使用的候选个数，缺省为2000个

    /**
     * 合并时使用一次比对的最大排名
     */
    var pszLTXWCandMerge_XStepOneRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_XStepOneRank_Data:Array[Byte] = _ // for pszLTXWCandMerge_XStepOneRank pointer ,struct:char;
  var pszLTXWCandMerge_WStepOneRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_WStepOneRank_Data:Array[Byte] = _ // for pszLTXWCandMerge_WStepOneRank pointer ,struct:char;
  var pszTLXWCandMerge_XStepOneRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_XStepOneRank_Data:Array[Byte] = _ // for pszTLXWCandMerge_XStepOneRank pointer ,struct:char;
  var pszTLXWCandMerge_WStepOneRank:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_WStepOneRank_Data:Array[Byte] = _ // for pszTLXWCandMerge_WStepOneRank pointer ,struct:char;

    /**
     * 合并时查找两者候选的交时，最多查找各自的前n名，缺省为200
     */
    var pszLTXWCandMerge_QMaxCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTXWCandMerge_QMaxCandCount_Data:Array[Byte] = _ // for pszLTXWCandMerge_QMaxCandCount pointer ,struct:char;
  var pszTLXWCandMerge_QMaxCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLXWCandMerge_QMaxCandCount_Data:Array[Byte] = _ // for pszTLXWCandMerge_QMaxCandCount pointer ,struct:char;

    /**
     * LT候选中重卡的处理方式
     */
    var pszLTDupCard_ProcessOption:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszLTDupCard_ProcessOption_Data:Array[Byte] = _ // for pszLTDupCard_ProcessOption pointer ,struct:char;

    var pszTT_MinXgwScoreForWsgMatch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTT_MinXgwScoreForWsgMatch_Data:Array[Byte] = _ // for pszTT_MinXgwScoreForWsgMatch pointer ,struct:char;

    /**
     * 合并算法比对时，用来控制许公望和王曙光算法是否进行二次比对的参数
     */
    var pszXWMatch_TTStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszXWMatch_TTStepTwo_Data:Array[Byte] = _ // for pszXWMatch_TTStepTwo pointer ,struct:char;
  var pszXWMatch_TLStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszXWMatch_TLStepTwo_Data:Array[Byte] = _ // for pszXWMatch_TLStepTwo pointer ,struct:char;
  var pszXWMatch_LTStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszXWMatch_LTStepTwo_Data:Array[Byte] = _ // for pszXWMatch_LTStepTwo pointer ,struct:char;
  var pszXWMatch_LLStepTwo:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszXWMatch_LLStepTwo_Data:Array[Byte] = _ // for pszXWMatch_LLStepTwo pointer ,struct:char;

    var pszTTCrossMatch:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTCrossMatch_Data:Array[Byte] = _ // for pszTTCrossMatch pointer ,struct:char;				//!< 是否在TT候选中进行交叉比对（对得分低于阈值的候选，与高得分候选和源卡进行查重）
  var pszTTLowScoreMaxCandCount:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTTLowScoreMaxCandCount_Data:Array[Byte] = _ // for pszTTLowScoreMaxCandCount pointer ,struct:char;		//!< 对于与阈值分差超过10名的候选，最多取该参数指定的个数进行查重，缺省为10，范围为[0,500]
  var pszTT_XgwThreshold:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTT_XgwThreshold_Data:Array[Byte] = _ // for pszTT_XgwThreshold pointer ,struct:char;

    //!< 增加参数来控制是否把普通TL变成按人倒查（从对应的TT中获取比中的卡片），并指定最多取几张重卡
    var pszTLMatchedWithGroup:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMatchedWithGroup_Data:Array[Byte] = _ // for pszTLMatchedWithGroup pointer ,struct:char;
  var pszTLMatchedGroupCnt:String = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTLMatchedGroupCnt_Data:Array[Byte] = _ // for pszTLMatchedGroupCnt pointer ,struct:char;

  } // GBASE_PARAMNAMESTRUCT;



}

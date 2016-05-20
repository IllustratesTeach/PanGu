package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.ganumia.isafile.GADB_RESCOLNAME
import nirvana.hall.c.services.ganumia.{isafile, ganmuser}
import nirvana.hall.c.services.gloclib.gacodetb.GAFIS_CODE_ENTRYSTRUCT
import nirvana.hall.c.services.gloclib.glocname._

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
object gcolnames {
  lazy val g_stCN:COLNAMESTRUCT = GAFIS_COLNAME_Init()

  def	COLNAME_TPLP_FPX_COMMON_Init(pstName:COLNAME_TPLP_FPX_COMMON)
  {
    pstName.pszFPXStatus			= "FPXStatus";
    pstName.pszFPXIsForeign		= "FPXIsForeign";
    pstName.pszFPXForeignUnitCode	= "FPXForeignUnitCode";
    pstName.pszFPXPurpose			= "FPXPurpose";
  }


  def	GAFIS_COLNAME_SetQryName(pQn:COLQRYNAMESTRUCT)
  {
    pQn.pszKeyID = "KeyID";
    pQn.pszUserName = "UserName";
    pQn.pszQueryType = "QueryType";
    pQn.pszPriority = "Priority";
    pQn.pszHitPoss = "HitPossibility";
    pQn.pszStatus = "Status";
    pQn.pszFlag = "Flag";
    pQn.pszIsRmtQuery = "IsRmtQuery";
    pQn.pszStage = "Stage";
    pQn.pszFlagEx = "FlagEx";
    pQn.pszSourceDB = "SourceDB";
    pQn.pszDestDB = "DestDB";
    pQn.pszSubmitTime = "SubmitTime";
    pQn.pszFinishTime = "FinishTime";
    pQn.pszStartLibID = "StartLibID";
    pQn.pszEndLibID = "EndLibID";
    pQn.pszRmtAddTime = "RmtAddTime";
    pQn.pszTimeUsed = "TimeUsed";
    pQn.pszStartKey1 = "StartKey1";
    pQn.pszEndKey1 = "EndKey1";
    pQn.pszStartKey2 = "StartKey2";
    pQn.pszEndKey2 = "EndKey2";
    pQn.pszMaxCandNum = "MaxCandNum";
    pQn.pszCurCandNum = "CurCandNum";
    pQn.pszCandHead = "CandHead";
    pQn.pszCandList = "CandList";
    pQn.pszQryCondition = "QryCondition";
    pQn.pszSrcDataMnt = "SrcDataMnt";
    pQn.pszSrcDataImage = "SrcDataImage";
    pQn.pszGroupID = "GroupID";
    pQn.pszQueryID = "QueryID";
    pQn.pszCheckUserName = "CheckUserName";
    pQn.pszCheckTime = "CheckTime";
    pQn.pszVerifyResult = "VerifyResult";
    pQn.pszRmtState = "RmtState";
    pQn.pszMISState = "MISState";
    pQn.pszDestDBCount = "DestDBCount";
    pQn.pszRmtFlag = "RmtFlag";
    pQn.pszMIC = "MIC";
    pQn.pszMISCond = "MISCond";
    pQn.pszServerList = "ServerList";
    pQn.pszTextSql = "TextSql";
    pQn.pszReCheckDate = "ReCheckDate";
    pQn.pszReCheckUserName = "ReCheckUserName";
    pQn.pszVerifyPri = "VerifyPriority";
    pQn.pszComment = "Comment";
    pQn.pszFlag3	= "FlagC";
    pQn.pszFlag4	= "FlagD";
    pQn.pszFlag5	= "FlagE";
    pQn.pszFlag6	= "FlagF";
    pQn.pszFlag7	= "FlagG";
    pQn.pszFlag8	= "FlagH";
    pQn.pszUserUnitCode	= "UserUnitCode";
    pQn.pszCheckerUnitCode = "CheckerUnitCode";
    pQn.pszReCheckerUnitCode	= "ReCheckerUnitCode";

    pQn.pszMinScore			= "MinScore";
    pQn.pszSchCandCnt			= "SchCandCnt";

    pQn.pszComputerIP			= "ComputerIP";
    pQn.pszComputerName		= "ComputerName";

    pQn.pszIsFofoQueQry		= "IsFifoQueQry";
    pQn.pszFifoQueSID			= "FifoQueSID";
    pQn.pszFifoQueDBID			= "FifoQueDBID";
    pQn.pszFifoQueTID			= "FifoQueTID";

    //	pQn.pszReserved= "ReservedCol";

    pQn.pszQryUID				= "QryUID";
    pQn.pszFPXStatus			= "FPXStatus";
    pQn.pszFPXVerifyResult		= "FPXVerifyResult";
    pQn.pszFPXVerifyUnitCode	= "FPXVerifyUnitCode";
    pQn.pszFPXForeignTaskID	= "FPXForeignTaskID";

    pQn.pszRecSearched			= "RecSearched";
  }

  def	GAFIS_COLNAME_FIFOQUEName(pFq:COLFIFOQUESTRUCT)
  {
    pFq.pszKeyID = "KeyID";
    pFq.pszSourceTID= "SourceDataTableID";
    pFq.pszQueueType = "QueueType";
    pFq.pszStatus = "Status";
    pFq.pszCandHead = "CandHead";
    pFq.pszCandList = "CandList";
    pFq.pszItemFlag = "ItemFlag";
    pFq.pszTenString = "TenString";
    pFq.pszUseCPR = "UseCPR";
    pFq.pszOption = "QueOption";
    pFq.pszQueOption	= "QueOptionEx";
    pFq.pszSourceDBID	= "SourceDBID";
    pFq.pszQueryType	= "QueryType";
    pFq.pszFlag		= "Flag";
    pFq.pszFlagEx		= "FlagEx";
    pFq.pszQrySID		= "QrySID";
    pFq.pszIsQrySubmitted	= "IsQrySubmitted";
    pFq.pszDestDBID	= "DestDBID";
    pFq.pszDestTID		= "DestTID";
    pFq.pszQryDBID		= "QryDBID";
    pFq.pszQryTID		= "QryTID";
    pFq.pszLQueryType	= "LQueryType";
    pFq.pszLQrySID		= "LQrySID";
    pFq.pszDestLatDBID	= "DestLatDBID";
    pFq.pszDestLatTID	= "DestLatTID";

    pFq.pszErrorCount	= "ErrorCount";
  }

  /**
    * 人像查询队列表列名
    */
  def	GAFIS_COLNAME_SetFaceQryName(pFQn:COLFACEQRYNAMESTRUCT)
  {
    pFQn.pszKeyID = "KeyID";
    pFQn.pszQueryType = "QueryType";
    pFQn.pszPriority = "Priority";
    pFQn.pszHitPoss = "HitPossibility";
    pFQn.pszStatus = "Status";
    pFQn.pszFlag = "Flag";

    pFQn.pszSourceDB = "SourceDB";
    pFQn.pszDestDB = "DestDB";
    pFQn.pszDestDBCount = "DestDBCount";

    pFQn.pszUserName = "UserName";
    pFQn.pszSubmitTime = "SubmitTime";
    pFQn.pszFinishTime = "FinishTime";
    pFQn.pszTimeUsed = "TimeUsed";
    pFQn.pszCheckUserName = "CheckUserName";
    pFQn.pszCheckTime = "CheckTime";
    pFQn.pszReCheckUserName = "ReCheckUserName";
    pFQn.pszReCheckDate = "ReCheckDate";
    pFQn.pszUserUnitCode	= "UserUnitCode";
    pFQn.pszCheckerUnitCode = "CheckerUnitCode";
    pFQn.pszReCheckerUnitCode	= "ReCheckerUnitCode";

    pFQn.pszMaxCandNum = "MaxCandNum";
    pFQn.pszCurCandNum = "CurCandNum";
    pFQn.pszCandHead = "CandHead";
    pFQn.pszCandList = "CandList";
    pFQn.pszMIC = "MIC";

    pFQn.pszQueryID = "QueryID";
    pFQn.pszVerifyResult = "VerifyResult";
    pFQn.pszMinScore = "MinScore";

    pFQn.pszComputerIP		= "ComputerIP";
    pFQn.pszComputerName	= "ComputerName";

    pFQn.pszStartLibID = "StartLibID";
    pFQn.pszEndLibID = "EndLibID";
    pFQn.pszStartKey = "StartKey";
    pFQn.pszEndKey = "EndKey";
  }

  def	GAFIS_TBLNAME_TPName(p:TPLIBTABLENAMESTRUCT)
  {
    p.pszCardText = "TPCardText";
    p.pszFingerMnt = "FingerMnt";
    p.pszFingerFeat = "FingerFeat";
    p.pszImgData = "ImgData";			//
    p.pszTPCardTable = "TPCardInfo";	//
    p.pszPersonTable = "PersonInfo";
    p.pszPersonText = "PersonText";
    p.pszPersonCard = "PersonCard";
    p.pszTPCardAdmin = "TPCardAdmin";
    p.pszLogTable = "LogTable";
    p.pszLogData = "LogData";
    p.pszParamTable = "ParamTable";
    p.pszParamData = "ParamData";

    p.pszPalmMnt = "PalmMntTable";
    p.pszPalmMntAdmin = "PalmMntAdmin";
    p.pszRightPalmMnt = "RightPalmMnt";
    p.pszLeftPalmMnt = "LeftPalmMnt";
    p.pszPalmFeat		= "PalmFeature";

    p.pszPlainFinger	= "PlainFinger";
    p.pszFaceMnt		= "FaceMnt";
    p.pszVoiceMnt		= "VoiceMnt";

    p.pszTPUnmatch		= "TPUnmatch";
    p.pszCTTPUnmatch	= "CTTPUnmatch";

    p.nTIDTPCardTable = 2;
    p.nTIDFingerMnt = 1001;
    p.nTIDFingerFeat = 1002;
    p.nTIDImgData = 1005;
    p.nTIDCardText = 1006;
    p.nTIDTPCardAdmin = 1007;

    p.nTIDPersonTable = 3;
    p.nTIDPersonCard = 1001;
    p.nTIDPersonText = 1002;

    p.nTIDLogTable = 4;
    p.nTIDLogData = 1001;

    p.nTIDParamTable = 1;
    p.nTIDParamData = 1001;

    p.nTIDPalmMnt = 5;
    p.nTIDPalmMntAdmin = 1001+10;
    p.nTIDLeftPalmMnt = 1002+10;
    p.nTIDRightPalmMnt = 1003+10;
    p.nTIDPalmFeat		= 1004+10;

    p.nTIDPlainFinger	= 1008;
    p.nTIDFaceMnt		= 1020;
    p.nTIDVoiceMnt		= 1021;

    p.nTIDTPUnmatch	= 6;
    p.nCTIDTPUnmatch	= 1001;
  }

  def	GAFIS_TBLNAME_LPName(p:LPLIBTABLENAMESTRUCT)
  {
    p.pszCase = "Case";
    p.pszLatFinger = "LatFinger";
    p.pszLatPalm = "LatPalm";
    p.pszLatFingerMnt = "LatFingerMnt";
    p.pszLatFingerText = "LatFingerText";
    p.pszLatPalmMnt = "LatPalmMnt";
    p.pszLatPalmText = "LatPalmText";
    p.pszCaseInfo = "CaseInfo";
    p.pszCaseText = "CaseText";
    p.pszLatFingerAdmin = "LatFingerAdmin";
    p.pszLatPalmAdmin = "LatPalmAdmin";
    p.pszLogTable = "LogTable";
    p.pszLogData = "LogData";
    p.pszParamTable = "ParamTable";
    p.pszParamData = "ParamData";

    p.pszFace		= "Face";
    p.pszCTFace	= "CTFace";
    p.pszCTFaceText	= "CTFaceText";
    p.pszCTFaceMnt		= "CTFaceMnt";

    p.pszVoice		= "Voice";
    p.pszCTVoice	= "CTVoice";
    p.pszCTVoiceText	= "CTVoiceText";
    p.pszCTVoiceMnt	= "CTVoiceMnt";

    p.pszLPGroup		= "LPGroup";
    p.pszCTLPGroup		= "CTLPGroup";

    p.pszCaseGroup		= "CaseGroup";
    p.pszCTCaseGroup	= "CTCaseGroup";

    p.pszLPFingerUnmatch	= "LPFingerUnmatch";
    p.pszCTLPFingerUnmatch	= "CTLPFingerUnmatch";

    p.pszLPPalmUnmatch		= "LPPalmUnmatch";
    p.pszCTLPPalmUnmatch	= "CTLPPalmUnmatch";

    p.nTIDLatFinger = 2;
    p.nTIDLatFingerMnt = 1001;
    p.nTIDLatFingerText = 1002;
    p.nTIDLatFingerAdmin = 1003;

    p.nTIDLatPalm = 3;
    p.nTIDLatPalmMnt = 1001;
    p.nTIDLatPalmText = 1002;
    p.nTIDLatPalmAdmin = 1003;

    p.nTIDCase = 4;
    p.nTIDCaseInfo = 1001;
    p.nTIDCaseText = 1002;

    p.nTIDLogTable = 5;
    p.nTIDLogData = 1001;

    p.nTIDParamTable = 1;
    p.nTIDParamData = 1001;

    p.nTIDFace		= 7;
    p.nCTIDFace	= 1001;
    p.nCTIDFaceText	= 1002;
    p.nCTIDFaceMnt		= 1003;

    p.nTIDVoice	= 8;
    p.nCTIDVoice	= 1001;
    p.nCTIDVoiceText	= 1002;
    p.nCTIDVoiceMnt	= 1003;

    p.nTIDLPGroup		= 9;
    p.nCTIDLPGroup		= 1001;

    p.nTIDCaseGroup	= 10;
    p.nCTIDCaseGroup	= 1001;

    p.nTIDLPFingerUnmatch	= 11;
    p.nCTIDLPFingerUnmatch	= 1001;

    p.nTIDLPPalmUnmatch	= 12;
    p.nCTIDLPPalmUnmatch	= 1001;
  }

  def	GAFIS_TBLNAME_QRYName(p:QUERYTABLENAMESTRUCT)
  {
    p.pszQueryTableName = "QueryQueTable";
    p.pszQueryQue = "QueryQueData";
    p.pszLogTable = "LogTable";
    p.pszLogData = "LogData";
    p.pszParamTable = "ParamTable";
    p.pszParamData = "ParamData";

    p.pszFaceQueryTableName = "FaceQryQueTable";
    p.pszFaceQueryQue = "FaceQryQueData";

    p.nTIDQueryTableName = 2;
    p.nTIDQueryQue = 1001;
    p.nTIDLogTable = 3;
    p.nTIDLogData = 1001;
    p.nTIDParamTable = 1;
    p.nTIDParamData = 1001;

    p.nTIDFaceQueryTableName = 4;
    p.nTIDFaceQueryQue = 1001;
  }

  def	GAFIS_TBLNAME_AdminDB(pa:ADMINTABLENAMESTRUCT)
  {
    pa.pszUserTable = "UserAccount";
    pa.pszCTUserTable = "UserAccountData";
    pa.pszSysMsgTable = "SystemMessage";
    pa.pszCTSysMsgTable = "SystemMessageData";
    pa.pszBreakCaseTable = "BreakCase";
    pa.pszCTBreakCaseTable = "BreakCaseData";
    pa.pszSYSLogTable = "SysLog";
    pa.pszSYSLogData = "SysLogData";
    pa.pszDBLogTable = "DBModLog";
    pa.pszDBLogData = "DBModLogData";
    pa.pszParamTable = "ParamTable";
    pa.pszParamData = "ParamData";
    pa.pszMobileCaseTable = "MobileCaseTable";
    pa.pszMobileCaseData = "MobileCaseData";
    pa.pszDBPropTable = "DBPropTable";
    pa.pszDBPropData = "DBPropData";

    pa.nTIDUserTable = 2;
    pa.nTIDCTUserTable = 1001;
    pa.nTIDSysMsgTable = 3;
    pa.nTIDCTSysMsgTable = 1001;
    pa.nTIDBreakCaseTable = 4;
    pa.nTIDCTBreakCaseTable = 1001;
    pa.nTIDSYSLogTable = 5;
    pa.nTIDSYSLogData = 1001;
    pa.nTIDDBLogTable = 6;
    pa.nTIDDBLogData = 1001;
    pa.nTIDParamTable = 9;
    pa.nTIDParamData = 1001;
    pa.nTIDMobileCaseTable = 7;
    pa.nTIDMobileCaseData = 1001;
    pa.nTIDDBPropTable = 1;
    pa.nTIDDBPropData = 1001;
  }

  def	GAFIS_TPCOLNAME_ADMINInit(p:TPADMINCOLNAME)
  {
    p.pszPersonID = "PersonID";
    p.pszMISPersonID = "MISPersonID";
    p.pszCreateUserName = "CreateUserName";
    p.pszUpdateUserName = "UpdateUserName";
    p.pszCreateTime = "CreateTime";
    p.pszUpdateTime = "UpdateTime";
    p.pszScanCardConfigID = "ScanCardConfigID";
    p.pszDispCardConfigID = "DispCardConfigID";
    p.pszGroupID = "GroupID";
    p.pszAccuTLCount = "AccuTLCount";
    p.pszAccuTTCount = "AccuTTCount";
    p.pszTLCount = "TLCount";
    p.pszTTCount = "TTCount";
    p.pszPersonType = "PersonType";
    p.pszTLDate = "TLDate";
    p.pszTTDate = "TTDate";
    p.pszTLUser = "TLUser";
    p.pszTTUser = "TTUser";
    p.pszEditCount = "EditCount";
    p.pszGroupName	= "GroupName";
    p.pszGroupCode	= "GroupCode";
    p.pszCaseID	= "CaseID";
    p.pszPersonState = "PersonState";
    //	p.pszTPAdminReserved = "TPAdminReserved";
    p.pszOrgScanner			= "OrgScanner";
    p.pszOrgScanUnitCode		= "OrgScanUnitCode";
    p.pszOrgAFISType			= "OrgAFISType";
    p.pszRollDigitizeMethod	= "RollDigitizeMethod";
    p.pszTPlainDigitizeMethod	= "TPlainDigitizeMethod";
    p.pszPalmDigitizeMethod	= "PalmDigitizeMethod";

    p.pszDigitizedTime	= "DigitizedTime";
  }

  def	GAFIS_LPCOLNAME_ADMINInit(p:LPADMINCOLNAME)
  {
    p.pszCreateUserName = "CreateUserName";
    p.pszUpdateUserName = "UpdateUserName";
    p.pszCreateTime = "CreateTime";
    p.pszUpdateTime = "UpdateTime";
    p.pszPersonID = "PersonID";
    p.pszGroupID = "GroupID";
    p.pszAccuLTCount = "AccuLTCount";
    p.pszAccuLLCount = "AccuLLCount";
    p.pszLTCount = "LTCount";
    p.pszLLCount = "LLCount";
    p.pszFingerType = "FingerType";
    p.pszLTDate = "LTDate";
    p.pszLLDate = "LLDate";
    p.pszLTUser = "LTUser";
    p.pszLLUser = "LLUser";
    p.pszEditCount = "EditCount";
    p.pszGroupName	= "GroupName";
    p.pszIsBroken			= "IsBroken";
    p.pszGroupCode			= "GroupCode";
    p.pszBrokenUnitCode	= "BrokenUnitCode";
    p.pszBrokenUser		= "BrokenUser";
    p.pszBrokenDate		= "BrokenDate";
    p.pszIsLTBroken		= "IsLTBroken";
    p.pszReChecker			= "ReChecker";
    p.pszMntExtractMethod	= "MntExtractMethod";
    p.pszHitPersonState	= "HitPersonState";
    //	p.pszAdminReserved		= "AdminReserved";
    p.pszOrgScanner		= "OrgScanner";
    p.pszOrgScanUnitCode	= "OrgScanUnitCode";
    p.pszOrgAFISType		= "OrgAFISType";
    p.pszDigitizeMethod	= "DigitizeMethod";

    p.pszFgGroup			= "FgGroup";
    p.pszFgIndex			= "FgIndex";
  }

  def	GAFIS_USERCOLNAME_Init(p:GAFIS_USERTABLECOLNAME)
  {
    p.pszName = ganmuser.NMUSER_COL_GetUserName();
    p.pszFullName = "FullUserName";
    p.pszComment = "Comment";
    p.pszBinData1 = "BinData1";
    p.pszBinData2 = "BinData2";
    p.pszPhone(0) = "Phone_0";
    p.pszPhone(1) = "Phone_1";
    p.pszPhone(2) = "Phone_2";
    p.pszAddress = "Address";
    p.pszMail = "Mail";
    p.pszPostCode = "PostCode";
    p.pszFingerData = "FingerData";
    p.pszNuminaPriv = ganmuser.NMUSER_COL_GetPrivName();
    p.pszIsGroup	 = "IsGroup";
    p.pszLoginIPMask = "LoginIPMask";

    p.pszOrgID = "OrgID";
    p.pszCreateTime = "CreateTime";
    p.pszUpdateTime = "UpdateTime";
  }

  def	GAFIS_MSGCOLNAME_Init(p:GAFIS_MSGTABLECOLNAME)
  {
    p.pszSender = "Sender";
    p.pszTitle = "Title";
    p.pszSendDateTime = "SendDateTime";
    p.pszAttach = "Attach";
    p.pszContent = "Content";
    p.pszState = "State";
    p.pszImportance = "Importance";
  }

  def	GAFIS_PARAMCOLNAME_Init(p:GAFIS_PARAMTABLECOLNAME)
  {
    p.pszParamName = "ParamName";
    p.pszParamValue = "ParamValue";
    p.pszParamData = "ParamData";
  }

  def	GAFIS_CODECOLNAME_Init(p:GAFIS_CODETABLECOLNAME)
  {
    p.pszCode = "Code";
    p.pszInputCode = "InputCode";
    p.pszName = "Name";
  }

  // break case(may be lt ll, tl or tt.
  def	GAFIS_BCCOLNAME_Init(p:GAFIS_BCTABLECOLNAME)
  {
    p.pszBreakID		= "BreakID";
    p.pszQueryTaskID	= "QueryTaskID";

    p.pszSrcKey		= "SrcKey";
    p.pszDestKey		= "DestKey";
    p.pszScore			= "Score";
    p.pszRank			= "Rank";
    p.pszFg			= "Fg";
    p.pszSrcPersonCaseID	= "SrcPersonCaseID";
    p.pszDestPersonCaseID	= "DestPersonCaseID";

    p.pszCaseClassCode1	= "CaseClassCode1";
    p.pszCaseClassCode2	= "CaseClassCode2";
    p.pszCaseClassCode3	= "CaseClassCode3";

    p.pszFirstRankScore	= "FirstRankScore";
    p.pszHitPoss			= "HitPoss";
    p.pszIsRemoteSearched	= "IsRemoteSearched";
    p.pszIsCrimeCaptured	= "IsCrimeCaptured";
    p.pszTotoalMatchedCnt	= "TotalMatchedCnt";
    p.pszFgType			= "FgType";
    p.pszSearchingUnitCode	= "SearchingUnitCode";

    p.pszSrcMnt		= "SrcMnt";
    p.pszSrcImg		= "SrcImg";
    p.pszSrcInfo		= "SrcInfo";
    p.pszSrcDBID		= "SrcDBID";

    p.pszDestMnt		= "DestMnt";
    p.pszDestImg		= "DestImg";
    p.pszDestInfo		= "DestInfo";
    p.pszDestDBID		= "DestDBID";

    p.pszSubmitDateTime	= "SubmitDateTime";
    p.pszSubmitUserName	= "SubmitUserName";
    p.pszSubmitUserUnitCode= "SubmitUserUnitCode";

    p.pszBreakUserName		= "BreakUserName";
    p.pszBreakDateTime		= "BreakDateTime";
    p.pszBreakUserUnitCode	= "BreakUserUnitCode";

    p.pszReCheckDate		= "ReCheckDate";
    p.pszReCheckerUnitCode	= "ReCheckerUnitCode";
    p.pszReCheckUserName	= "ReCheckerUserName";

    p.pszQueryType			= "QueryType";
    p.pszComment1			= "Comment1";
    p.pszComment2			= "Comment2";
    p.pszReserved1			= "Reserved1";
    p.pszReserved2			= "Reserved2";
    p.pszReserved3			= "Reserved3";

    p.pszNotUsedA			= "NotUsedA";
    p.pszNotUsedB			= "NotUsedB";
  }

  def	GAFIS_DTPROPCOLNAME_Init(p:GAFIS_DTPROPTABLECOLNAME)
  {
    p.pszName = "Name";
    p.pszData = "Data";
  }

  def	GAFIS_DBMLOGCOLNAME_Init(p:GAFIS_DBMOGLOGTABLECOLNAME)
  {
    p.pszUserName = "UserName";
    p.pszKey = "Key";
    p.pszDBID = "DBID";
    p.pszTableID = "TableID";
    p.pszModDateTime = "ModDateTime";
    p.pszOperation = "Operation";
    p.pszTableType	= "TableType";
    p.pszData		= "Data";
    p.pszNotUsed	= "NotUsed";
  }

  def	GAFIS_SYSMLOGCOLNAME_Init(p:GAFIS_SYSMODLOGTABLECOLNAME)
  {
    p.pszUserName = "UserName";
    p.pszModDateTime = "ModDateTime";
    p.pszData = "Data";
  }

  def	GAFIS_MOBILECASECOLNAME_Init(p:MOBILECASECOLNAME)
  {
    p.pszCaseID = "CaseID";
    p.pszComment = "Comment";
    p.pszCreateDateTime = "CreateDateTime";
    p.pszUserName = "UserName";
    p.pszGroupID = "GroupID";
    p.pszBinData = "BinData";
  }

  def	GAFIS_QRYMODLOGCOLNAME_Init(p:GAFIS_QRYMODLOGTABLECOLNAME)
  {
    p.pszUserName = "UserName";
    p.pszModDateTime = "ModDateTime";
    p.pszData = "Data";
  }

  def	GAFIS_NUMINACOL_Init(p:NUMINAINNERCOLNAME)
  {
    import GADB_RESCOLNAME._
    p.pszCreateTime = isafile.GADB_RESCOL_GetName(RESCOL_CREATETIME);
    p.pszNextSID = isafile.GADB_RESCOL_GetName(RESCOL_NEXTSID);
    p.pszPrevSID = isafile.GADB_RESCOL_GetName(RESCOL_PREVSID);
    p.pszQueFlag = isafile.GADB_RESCOL_GetName(RESCOL_QUEFLAG);
    p.pszSID = isafile.GADB_RESCOL_GetName(RESCOL_SID);
    p.pszUpdateTime = isafile.GADB_RESCOL_GetName(RESCOL_UPDATETIME);
    p.pszUUID = isafile.GADB_RESCOL_GetName(RESCOL_UUID);
    p.pszCreator = isafile.GADB_RESCOL_GetName(RESCOL_CREATOR);
    p.pszUpdator = isafile.GADB_RESCOL_GetName(RESCOL_UPDATOR);
  }

  def	GAFIS_FIFOQUE_TPInit(p:TPDEFFIFOQUENAMESTRUCT)
  {
    p.pszExfQue = "InputExfFifoQue";
    p.pszEditQue = "InputEditFifoQue";
    p.pszTTSearchQue = "InputTTSearchFifoQue";
    p.pszTTCheckQue = "InputTTCheckFifoQue";
    p.pszTextInputQue = "TextInputFifoQue";
    p.pszTLSearchQue	= "InputTLSearchFifoQue";
    p.pszTLCheckQue	= "InputTLCheckFifoQue";
    p.pszEditQue2		= "EditFifoQue2";
  }

  def	GAFIS_FIFOQUE_LPInit(p:LPDEFFIFOQUENAMESTRUCT)
  {
    p.pszEditQue = "InputEditFifoQue";
    p.pszLTSearchQue = "InputLTSearchFifoQue";
    p.pszLTCheckQue = "InputLTCheckFifoQue";
    p.pszExfQue		= "InputExfFifoQue";	// latent exf fifo queue.
  }

  def	GAFIS_TEXT_InitTPCard_50(p:TPCARDDEFTEXT_50)
  {
    p.pszName = "Name";
    p.pszNamePinYin = "NamePinYin";
    p.pszAlias = "Alias";
    p.pszAddressCode = "AddressCode";
    p.pszAddress = "Address";
    p.pszBirthDate = "BirthDate";
    p.pszCaseClass1 = "CaseClass1";
    p.pszCaseClass2 = "CaseClass2";
    p.pszCaseClass3 = "CaseClass3";

    p.pszCaseClass1Code = "CaseClass1Code";
    p.pszCaseClass2Code = "CaseClass2Code";
    p.pszCaseClass3Code = "CaseClass3Code";

    p.pszPersonClass = "PersonClass";
    p.pszComment = "Comment";
    p.pszHuKouPlace = "HuKouPlace";
    p.pszHuKouPlaceCode = "HuKouPlaceCode";
    p.pszPrintDate = "PrintDate";
    p.pszPrinterName = "PrinterName";
    p.pszPrinterUnitCode = "PrinterUnitCode";
    p.pszPrinterUnitName = "PrinterUnitName";
    p.pszSex = "Sex";
    p.pszSexCode = "SexCode";
    p.pszShenFenID = "ShenFenID";
  }


  def	GAFIS_TEXT_InitTPCard(p:TPCARDDEFTEXT)
  {
    p.pszName = "Name";
    p.pszNamePinYin = "NamePinYin";
    p.pszAlias = "Alias";
    p.pszAddressCode = "AddressCode";
    p.pszBirthDate = "BirthDate";

    p.pszCaseClass1Code = "CaseClass1Code";
    p.pszCaseClass2Code = "CaseClass2Code";
    p.pszCaseClass3Code = "CaseClass3Code";

    p.pszPersonClassCode	= "PersonClassCode";
    p.pszPersonClassTail	= "PersonClassTail";

    p.pszComment = "Comment";
    p.pszHuKouPlaceCode = "HuKouPlaceCode";
    p.pszPrintDate = "PrintDate";
    p.pszPrinterName = "PrinterName";
    p.pszPrinterUnitCode = "PrinterUnitCode";
    p.pszSexCode = "SexCode";
    p.pszShenFenID = "ShenFenID";

    // added on may 6, 2004
    p.pszAddressTail = "AddressTail";
    p.pszHuKouPlaceTail = "HuKouPlaceTail";
    p.pszPrinterUnitNameTail = "PrinterUnitNameTail";

    p.pszCaseID1			= "CaseID1";
    p.pszCaseID2			= "CaseID2";

    p.pszShenFenIDTypeCode	= "ShenFenIDTypeCode";

    p.pszMISConnectPersonID	 = "MISConnectPersonID";

    p.pszUnitNameTail		= "UnitNameTail";
    p.pszUnitNameCode		= "UnitNameCode";
    p.pszHeight			= "Height";
    p.pszFootLen			= "FootLen";
    p.pszRaceCode			= "RaceCode";
    p.pszBodyFeature		= "BodyFeature";

    p.pszCreatorUnitCode = "CreatorUnitCode";
    p.pszUpdatorUnitCode = "UpdatorUnitCode";
    p.pszMicbUpdatorUserName = "MicbUpdatorUserName";
    p.pszMicbUpdatorUnitCode = "MicbUpdatorUnitCode";

    //	p.pszResserved1 = "Reserved1";
    //	p.pszImgDataReserved = "ImgDataReserved";
    p.pszHitHistory		= "HitHistory";

    // add on Feb. 19, 2008 by beagle
    p.pszNationality		= "Nationality";
    p.pszCertificateType	= "CertificateType";
    p.pszCertificateCode	= "CertificateCode";
    p.pszIsCriminalRecord	= "IsCriminalRecord";
    p.pszCriminalRecordDesc= "CriminalRecordDesc";

    /**
      * added by beagle on Dec. 14, 2008 
      * 指纹信息采集系统用到:采集系统类型、活体指纹采集仪类型、指纹图像拼接软件类型、采集仪GA认证标志序列号、采集点编号
      * 采集时间 -- 对于活体采集也很有用，因为CreateTime对采集端和中心端会不一样，有了这个采集时间就可以比较上下级的卡片是否就是同一张卡片
      */
    p.pszCaptureDate		= "CaptureDate";
    p.pszLFICSystemType	= "LFICSystemType";
    p.pszLFICScannerType	= "LFICScannerType";
    p.pszLFICMosaicType	= "LFICMosaicType";
    p.pszLFICGASerial		= "LFICGASerial";
    p.pszLFICCaptureCode	= "LFICCaptureCode";

    /**
      * added by beagle on March. 09, 2011
      * 增加一些人员信息
      */

    p.pszDNASerial1 = "DNA_1";
    p.pszDNASerial2 = "DNA_2";

    p.pszFamilyName = "FamilyName";
    p.pszGivenName = "GivenName";
    p.pszMiddleName = "MiddleName";

    p.pszBloodType = "BloodType";
    p.pszWeight = "Weight";
    p.pszAccent = "Accent";
    p.pszEducation = "Education";
    p.pszOccupation = "Occupation";
    p.pszFigureType = "FigureType";
    p.pszFaceType = "FaceType";
    p.pszBirthPlaceName = "BirthPlaceName";
    p.pszBirthPlaceCode = "BirthPlaceCode";
  }

  def	GAFIS_TEXT_InitLPCard(p:LPCARDDEFTEXT)
  {
    p.pszComment = "Comment";
    p.pszRemainPlace = "RemainPlace";
    p.pszRidgeColor = "RidgeColor";
    p.pszSeqNo = "SeqNo";
    p.pszSenderFingerID = "SenderFingerID";
    //	p.pszResserved1 = "Reserved1";
    p.pszCreatorUnitCode = "CreatorUnitCode";
    p.pszUpdatorUnitCode = "UpdatorUnitCode";
    p.pszMicbUpdatorUserName = "MicbUpdatorUserName";
    p.pszMicbUpdatorUnitCode = "MicbUpdatorUnitCode";
    p.pszCaptureMethod			= "CaptureMethod";
    p.pszHitHistory			= "HitHistory";

    p.pszIsUnknownBody		= "IsUnknownBody";
    p.pszUnknownBodyCode	= "UnknownBodyCode";
    p.pszMntExtractMethod	= "MntExtractMethod";
    p.pszGuessedFinger		= "GuessedFinger";
    p.pszChainStartFinger	= "ChainStartFinger";
    p.pszChainEndFinger	= "ChainEndFinger";
  }

  def	GAFIS_TEXT_InitLPCase_50(p:LPCASEDEFTEXT_50)
  {
    p.pszCaseClass1 = "CaseClass1";
    p.pszCaseClass2 = "CaseClass2";
    p.pszCaseClass3 = "CaseClass3";
    p.pszCaseClass1Code = "CaseClass1Code";
    p.pszCaseClass2Code = "CaseClass2Code";
    p.pszCaseClass3Code = "CaseClass3Code";

    p.pszCaseOccurDate = "CaseOccurDate";
    p.pszCaseOccurPlace = "CaseOccurPlace";
    p.pszComment = "Comment";
    p.pszExtractUnitCode = "ExtractUnitCode";
    p.pszExtractUnitName = "ExtractUnitName";
    p.pszExtractor = "Extractor";
    p.pszSuperviseLevel = "SuperviseLevel";
    p.pszSuspiciousArea1 = "SuspiciousArea1";
    p.pszSuspiciousArea2 = "SuspiciousArea2";
    p.pszSuspiciousArea3 = "SuspiciousArea3";

    p.pszSuspiciousArea1Code = "SuspiciousArea1Code";
    p.pszSuspiciousArea2Code = "SuspiciousArea2Code";
    p.pszSuspiciousArea3Code = "SuspiciousArea3Code";

    p.pszSenderCardID = "SenderCardID";
    p.pszIllicitMoney = "IllicitMoney";
  }

  def	GAFIS_TEXT_InitLPCase(p:LPCASEDEFTEXT)
  {
    p.pszCaseClass1Code = "CaseClass1Code";
    p.pszCaseClass2Code = "CaseClass2Code";
    p.pszCaseClass3Code = "CaseClass3Code";

    p.pszCaseOccurDate = "CaseOccurDate";
    p.pszCaseOccurPlaceTail = "CaseOccurPlaceTail";
    p.pszComment = "Comment";
    p.pszExtractUnitCode = "ExtractUnitCode";
    p.pszSuperviseLevel = "SuperviseLevel";
    p.pszExtractor1 = "Extractor1";
    p.pszExtractor2 = "Extractor2";
    p.pszExtractor3 = "Extractor3";

    p.pszSuspiciousArea1Code = "SuspiciousArea1Code";
    p.pszSuspiciousArea2Code = "SuspiciousArea2Code";
    p.pszSuspiciousArea3Code = "SuspiciousArea3Code";

    p.pszSenderCardID = "SenderCardID";
    p.pszIllicitMoney = "IllicitMoney";

    p.pszCaseOccurPlaceCode = "CaseOccurPlaceCode";
    p.pszExtractUnitNameTail = "ExtractUnitNameTail";

    p.pszPremium		= "Premium";
    p.pszHitHistory	= "HitHistory";

    p.pszCreateUserName	= "CreateUserName";
    p.pszUpdateUserName	= "UpdateUserName";
    p.pszCreatorUnitCode	= "CreatorUnitCode";
    p.pszUpdatorUnitCode	= "UpdatorUnitCode";

    //	p.pszResserved1 = "Reserved1";	// 256 bytes long

    // add on Feb. 19, 2008 by beagle
    p.pszIsUnknownBody		= "IsUnknownBody";
    p.pszUnknownBodyCode	= "UnknownBodyCode";
    p.pszExtractDate		= "ExtractDate";
    p.pszCaseState			= "CaseState";
  }

  def	GAFIS_TEXT_InitPerson(p:TPPERSONDEFTEXT)
  {
    p.pszComment			= "Comment";
    p.pszOperator			= "Operator";
    p.pszUpdateDateTime	= "UpdateDateTime";
    p.pszUpdatorName		= "UpdatorName";
    p.pszCreateDateTime	= "CreateDateTime";
    p.pszCreatorName		= "CreatorName";
    p.pszCreateUnitCode	= "CreateUnitCode";
    p.pszUpdateUnitCode	= "UpdateUnitCode";
    p.pszLPGroupID			= "LPGroupID";
    p.pszLPGroupDBID		= "LPGroupDBID";
    p.pszLPGroupTID		= "LPGroupTID";
    p.pszFlag				= "Flag";
  }

  def	GAFIS_CODETABLEPROP_Init(pstProp:GAFIS_CODETABLEPROP,
    pszTableName:String,
    pszCTableName:String,
    pszComment:String,
    nCodeLen:Int,
    nInputCodeLen:Int,
    nNameLen:Int,
    nTableID:Int
    )
  {
    pstProp.pszTableName	= pszTableName;
    pstProp.pszCTableName	= pszCTableName;
    pstProp.pszComment		= pszComment;
    pstProp.nCodeLen		= nCodeLen;
    pstProp.nInputCodeLen	= nInputCodeLen;
    pstProp.nNameLen		= nNameLen;
    pstProp.nTableID		= nTableID.toShort;
  }

  def	GAFIS_CODETABLE_Init(pstCode:TABLENAME_CODETABLE)
  {
    val stEntry = new GAFIS_CODE_ENTRYSTRUCT;
    var nCTID = 201
    var (_,nInputCodeLen) = stEntry.findFieldOffsetAndLength("szInputCode");
    nInputCodeLen -= 1
    
    GAFIS_CODETABLEPROP_Init(pstCode.stAddress, "Code_AddressTable", "AddressData",
      "Address list", 16, nInputCodeLen, 120, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stCaseClass, "Code_CaseClassTable", "CaseClassData",
      "Case name list", 16, 16, 120, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stFinger, "Code_FingerNameTable", "FingerNameData",
      "Finger name list", 4, 8, 32, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stOperator, "Code_OperatorNameTable", "OperatorNameData",
      "Operator list", 8, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stRace, "Code_RaceTable", "RaceData",
      "Race list", 16, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stRidgeColor, "Code_RidgeColorTable", "RidgeColorData",
      "Ridge color list", 4, 8, 24, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stSex, "Code_SexTable", "SexData",
      "Sex list", 8, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stSupervise, "Code_SuperviseLevelTable", "SuperviseLevelData",
      "Supervise level list", 4, 8, 24, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stUnit, "Code_UnitTable", "UnitData",
      "Unit name list", 16, nInputCodeLen, 120, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stPersonClass, "Code_PersonClassTable", "PersonClassData",
      "person class list", 16, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stGroupCode, "Code_GroupCode", "GroupCodeCT",
      "group code", 8, 16, 24, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stCertificateCode, "Code_Certificate", "CertificateType",
      "certificate type code", 8, 16, 32, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stMurderCode, "Code_Murder", "MurderType",
      "murder case type code", 8, 16, 32, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stLatFingerDevelopMethod, "Code_LatDevelopMethod", "LatDevelopType",
      "latent finger development method", 8, 16, 32, nCTID);nCTID+=1;

    // add on Feb. 19, 2008 by beagle
    GAFIS_CODETABLEPROP_Init(pstCode.stNationality, "Code_Nationality", "NationalityCode",
      "nationality and area code", 8, 16, 120, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stCaseState, "Code_CaseState", "CaseStateCode",
      "case state code", 4, 16, 32, nCTID);nCTID+=1;

    //	#ifdef	UTIL_20110309_NEW_COLUMN
    // add on March 09, 2011 by beagle
    GAFIS_CODETABLEPROP_Init(pstCode.stBloodType, "Code_BloodType", "BloodTypeCode",
      "blood type code", 4, 16, 32, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stEducationLevel, "Code_EducationLevel", "EducationLevelCode",
      "education level code", 16, 16, 80, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stMaritalStatus, "Code_MaritalStatus", "MaritalStatusCode",
      "marital status code", 16, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stAccent, "Code_Accent", "AccentCode",
      "accent code", 16, 16, 80, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stColorCode, "Code_Color", "ColorCode",
      "color code", 8, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stFigureType, "Code_FigureType", "FigureTypeCode",
      "figure type code", 16, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stFaceType, "Code_FaceType", "FaceTypeCode",
      "face type code", 16, 16, 60, nCTID);nCTID+=1;
    GAFIS_CODETABLEPROP_Init(pstCode.stMntExtractMethod, "Code_MntExtractMethod", "MntExtractMethod",
      "mnt extract method code", 4, 16, 60, nCTID);nCTID+=1;
    //#endif
  }



  // plain finger name like
  // TPlainRMMnt(etc).
  def	GAFIS_TP_PLAIN_InitColName(pstNames:Array[COLMICBNAMESTRUCT])
  {
    var i = 0
    var pstName = pstNames(i);
    // right hand thumb
    pstName.pszMntName		= "TPlainRMMnt";
    pstName.pszFeatName	= "TPlainRMFeat";
    pstName.pszBinName		= "TPlainRMBin";
    pstName.pszCprName		= "TPlainRMCpr";
    pstName.pszImgName		= "TPlainRMImg";

    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainRSMnt";
    pstName.pszFeatName	= "TPlainRSFeat";
    pstName.pszBinName		= "TPlainRSBin";
    pstName.pszCprName		= "TPlainRSCpr";
    pstName.pszImgName		= "TPlainRSImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainRZMnt";
    pstName.pszFeatName	= "TPlainRZFeat";
    pstName.pszBinName		= "TPlainRZBin";
    pstName.pszCprName		= "TPlainRZCpr";
    pstName.pszImgName		= "TPlainRZImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainRHMnt";
    pstName.pszFeatName	= "TPlainRHFeat";
    pstName.pszBinName		= "TPlainRHBin";
    pstName.pszCprName		= "TPlainRHCpr";
    pstName.pszImgName		= "TPlainRHImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainRXMnt";
    pstName.pszFeatName	= "TPlainRXFeat";
    pstName.pszBinName		= "TPlainRXBin";
    pstName.pszCprName		= "TPlainRXCpr";
    pstName.pszImgName		= "TPlainRXImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainLMMnt";
    pstName.pszFeatName	= "TPlainLMFeat";
    pstName.pszBinName		= "TPlainLMBin";
    pstName.pszCprName		= "TPlainLMCpr";
    pstName.pszImgName		= "TPlainLMImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainLSMnt";
    pstName.pszFeatName	= "TPlainLSFeat";
    pstName.pszBinName		= "TPlainLSBin";
    pstName.pszCprName		= "TPlainLSCpr";
    pstName.pszImgName		= "TPlainLSImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainLZMnt";
    pstName.pszFeatName	= "TPlainLZFeat";
    pstName.pszBinName		= "TPlainLZBin";
    pstName.pszCprName		= "TPlainLZCpr";
    pstName.pszImgName		= "TPlainLZImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainLHMnt";
    pstName.pszFeatName	= "TPlainLHFeat";
    pstName.pszBinName		= "TPlainLHBin";
    pstName.pszCprName		= "TPlainLHCpr";
    pstName.pszImgName		= "TPlainLHImg";
    i += 1
     pstName = pstNames(i);

    pstName.pszMntName		= "TPlainLXMnt";
    pstName.pszFeatName	= "TPlainLXFeat";
    pstName.pszBinName		= "TPlainLXBin";
    pstName.pszCprName		= "TPlainLXCpr";
    pstName.pszImgName		= "TPlainLXImg";
  }

  def	GAFIS_CASEADMINCOLNAME_Init(p:GAFIS_CASEADMINCOLNAME)
  {
    p.pszIsBroken			= "IsBroken";
    p.pszBrokenUnitCode	= "BrokenUnitCode";
    p.pszBrokenUser		= "BrokenUser";
    p.pszReChecker			= "ReChecker";
    p.pszBrokenDate		= "BrokenDate";
    p.pszHasPersonKilled	= "HasPersonKilled";
    p.pszPersonKilledCount	= "PersonKilledCount";
    p.pszIsLTBroken		= "IsLTBroken";
    p.pszGroupCode			= "GroupCode";

    p.pszFaceIDCount		= "FaceIDCount";
    p.pszFaceIDList		= "FaceIDList";
    p.pszVoiceIDCount		= "VoiceIDCount";
    p.pszVoiceIDList		= "VoiceIDList";
    p.pszCreateDateTime	= "CreateDateTime";
    p.pszUpdateDateTime	= "UpdateDateTime";
    p.pszCreateUser		= "CreateUser";
    p.pszUpdateUser		= "UpdateUser";

    //	p.pszCaseAdminReserved	= "CaseAdminReserved";

    p.pszCaseGroupID		= "CaseGroupID";
    p.pszOrgScanner		= "OrgScanner";
    p.pszOrgScanUnitCode	= "OrgScanUnitCode";
    p.pszOrgAFISType		= "OrgAFISType";
    p.pszItemKeyList		= "ItemKeyList";

    p.pszMISConnectCaseID	= "MISConnectCaseID";

//    #ifdef USER_CUSTOM_LianNing_Add_XkID_JqID_AjId
    //fan:add 添加以下3个字段
    p.pszAJID	= "AJID";
    p.pszXKID	= "XKID";
    p.pszJQID	= "JQID";
//    #endif
    p.pszQryAssignChecker	= "QryAssignChecker";
    p.pszQryAssignHasChecked	= "QryAssignHasChecked";
  }


  def	GAFIS_ADMINTABLENAME_Init(p:GAFIS_ADMINTABLENAME)
  {
    p.pszDBModLog		= "DBModLog";
    p.pszQueEdit		= "Que_Edit";
    p.pszQueExf		= "Que_Exf";
    p.pszQueTextInput	= "Que_TextInput";
    /*
    p.pszQueLLCheck	= "Que_LLCheck";
    p.pszQueLLSearch	= "Que_LLSearch";
    p.pszQueLTCheck	= "Que_LTCheck";
    p.pszQueLTSearch	= "Que_LTSearch";
    p.pszQueTLCheck	= "Que_TLCheck";
    p.pszQueTLSearch	= "Que_TLSearch";
    p.pszQueTTCheck	= "Que_TTCheck";
    p.pszQueTTSearch	= "Que_TTSearch";
    */
    p.pszQueSearch		= "Que_Search";
    p.pszQueCheck		= "Que_Check";
    p.pszUserAuthLog	= "UserAuthLog";
    p.pszDBRunLog		= "DBRunLog";
    p.pszQualCheck		= "QualCheck";
    p.pszWorkLog		= "WorkLog";
    p.pszMntEditLog	= "MntEditLog";
    p.pszExfErrLog		= "ExfErrLog";
    p.pszQualCheckLog	= "QualCheckLog";
    p.pszQrySearchLog	= "QrySearchLog";
    p.pszQryCheckLog	= "QryCheckLog";
    p.pszQualCheckQue	= "QualCheckQue";
    p.pszQrySubmitLog	= "QrySubmitLog";
    p.pszQryReCheckLog	= "QryReCheckLog";

    p.pszTPLPAssociate	= "TPLPAssociate";
    p.pszTPLPUnmatch	= "TPLPUnmatch";
    p.pszTPLPFpxQue	= "TPLPFpxQue";
    p.pszTPLPFpxLog	= "TPLPFpxLog";
    p.pszQryFpxQue		= "QryFpxQue";
    p.pszQryFpxLog		= "QryFpxLog";
    p.pszLsnTable		= "LsnTable";
    p.pszQryCheckAssignTable	= "QryCheckAssignTable";

    p.pszLFICDTStatusTable	= "LFIC_DataTransmitStatusTable";
    p.pszLFICFBInfoTable	= "LFIC_FeedbackInfoTable";

    p.pszGAXCTaskTable		= "GAFPTXCDATA";
    p.pszGAXCLogTable		= "GAFPTXCLOG";
    p.pszWantedListTable	= "WANTED_LIST";
    p.pszWantedTPCardTable	= "WANTED_TP_CARD";
    p.pszWantedLogTable	= "WANTED_OPLOG";

    //!<add by zyn at 2014.08.11 for shanghai xk
    p.pszShxkDataStatusTable	= "XK_DATASTATE";
    p.pszShxkMatchInfoTable	= "XK_MATCHINFO";
    p.pszShxkCaseStatusTable	= "XK_CASEBREAKSTATE";
    p.pszShxkCaseTextTable		= "XK_CASETEXTINFO";
    //!<add by nn at 2014.9.23 for nanjing
    p.pszTTRelationTable = "TT_RELATION";
    p.pszTTCandidateTable= "TT_CANDIDATE";

    //!<add by zyn at 2014.10.21 for nj
    p.pszNjDelDataTable		= "NJ_DELDATA";
    //!<add by wangkui
    p.pszXCDataTable = "XC_DataInfo";
    //!<add by wangkui
    p.pszXCReportTable = "XC_ReportDataInfo";
//    #ifdef USER_CUSTOM_LianNing_HX
    p.pszLNHXReportDataTable = "HAIXIN_ZC_TTTL_RESULT";
//    #endif

    p.nDBModLogTID		= 301;
    p.nQueEditTID		= 302;
    p.nQueExfTID		= 303;
    p.nQueTextInputTID	= 304;
    /*
    p.nQueLLCheckTID	= 305;
    p.nQueLLSearchTID	= 306;
    p.nQueLTCheckTID	= 307;
    p.nQueLTSearchTID	= 308;
    p.nQueTLCheckTID	= 309;
    p.nQueTLSearchTID	= 310;
    p.nQueTTCheckTID	= 311;
    p.nQueTTSearchTID	= 312;
    */
    p.nQueSearchTID	= 313;
    p.nQueCheckTID		= 314;

    p.nUserAuthLog		= 320;
    p.nDBRunLog		= 321;
    p.nQualCheckTID	= 322;
    p.nWorkLogTID		= 323;
    p.nMntEditLogTID	= 324;
    p.nExfErrLogTID	= 325;
    p.nQualCheckLogTID	= 326;
    p.nQrySearchLogTID	= 327;
    p.nQryCheckLogTID	= 328;
    p.nQualCheckQueTID	= 329;
    p.nQrySubmitLogTID	= 330;
    p.nQryReCheckLogTID	= 331;

    p.nTPLPAssociate		= 340;
    p.nTPLPUnmatch			= 341;
    p.nTPLPFpxQue			= 342;
    p.nTPLPFpxLog			= 343;
    p.nQryFpxQue			= 344;
    p.nQryFpxLog			= 345;

    p.nLsnTable			= 350;
    p.nQryCheckAssignTable	= 352;

    p.nLFICDTStatusTable	= 360;
    p.nLFICFBInfoTable		= 361;

    p.nGAXCTaskTable		= 370;
    p.nGAXCLogTable		= 371;
    p.nWantedListTable		= 372;
    p.nWantedTPCardTable	= 373;
    p.nWantedLogTable		= 374;

    //!<add by zyn at 2014.08.11 for shanghai xk
    p.nShxkDataStatusTable	= 380;
    p.nShxkMatchInfoTable	= 381;
    p.nShxkCaseStatusTable	= 382;
    p.nShxkCaseTextTable	= 383;
    //!<add by nn at 2014.9.23 for nanjing
    p.nTTRelationTable		= 384;
    p.nTTCandidateTable	= 385;

    //!<add by zyn at 2014.10.21 for nj
    p.nNjDelDataTable		= 386;
    //!<add by wangkui
    p.nXCDataTable			= 387;
    p.nXCReportDataTable	= 388;
//    #ifdef USER_CUSTOM_LianNing_HX
    p.nLNHXReportDataTable = 389;
//    #endif
  }

  def	GAFIS_USERAUTHLOGCOLNAME_InitName(pstUa:GAFIS_USERAUTHLOGCOLNAME)
  {
    pstUa.pszComputer			= "ComputerName";
    pstUa.pszIP				= "IP";
    pstUa.pszLoginDateTime		= "LoginDateTime";
    pstUa.pszLogoutDateTime	= "LogoutDateTime";
    pstUa.pszLoginID			= "LoginID";
    pstUa.pszUserName			= "UserName";
    pstUa.pszIsValidUser		= "IsValidUser";
    pstUa.pszModuleID			= "ModuleID";	// which module called the login.
    pstUa.pszRes				= "Res";
  }

  // have 4 items.
  def	GAFIS_COL_InitVoice(pstMics:Array[COLMICBNAMESTRUCT])
  {
    var i = 0
    var pstMic = pstMics(i)

    pstMic.pszMntName	= "VoiceMnt_1";
    pstMic.pszImgName	= "VoiceData_1";
    pstMic.pszCprName	= "VoiceCpr_1";

    i += 1
    pstMic = pstMics(i)

    pstMic.pszMntName	= "VoiceMnt_2";
    pstMic.pszImgName	= "VoiceData_2";
    pstMic.pszCprName	= "VoiceCpr_2";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszMntName	= "VoiceMnt_3";
    pstMic.pszImgName	= "VoiceData_3";
    pstMic.pszCprName	= "VoiceCpr_3";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszMntName	= "VoiceMnt_4";
    pstMic.pszImgName	= "VoiceData_4";
    pstMic.pszCprName	= "VoiceCpr_4";
  }

  def	GAFIS_COL_InitFace(pstMics:Array[COLMICBNAMESTRUCT])
  {
    var i = 0
    var pstMic = pstMics(i)

    pstMic.pszImgName	= "FaceFrontImg";
    pstMic.pszMntName	= "FaceFrontMnt" ;
    pstMic.pszCprName	= "FaceFrontCpr";
    pstMic.pszFeatName	= "FaceFrontFeat";
    pstMic.pszBinName	= "FaceFrontBin";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszImgName	= "FaceNoseLImg";
    pstMic.pszMntName	= "FaceNoseLMnt";
    pstMic.pszCprName	= "FaceNoseLCpr";
    pstMic.pszFeatName	= "FaceNoseLFeat";
    pstMic.pszBinName	= "FaceNoseLBin";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszImgName	= "FaceNoseRImg";
    pstMic.pszMntName	= "FaceNoseRMnt";
    pstMic.pszCprName	= "FaceNoseRCpr";
    pstMic.pszFeatName	= "FaceNoseRFeat";
    pstMic.pszBinName	= "FaceNoseRBin";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszImgName	= "Face_4_Img";
    pstMic.pszMntName	= "Face_4_Mnt";
    pstMic.pszCprName	= "Face_4_Cpr";
    pstMic.pszFeatName	= "Face_4_Feat";
    pstMic.pszBinName	= "Face_4_Bin";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszImgName	= "Face_5_Img";
    pstMic.pszMntName	= "Face_5_Mnt";
    pstMic.pszCprName	= "Face_5_Cpr";
    pstMic.pszFeatName	= "Face_5_Feat";
    pstMic.pszBinName	= "Face_5_Bin";
    i += 1
    pstMic = pstMics(i)

    pstMic.pszImgName	= "Face_6_Img";
    pstMic.pszMntName	= "Face_6_Mnt";
    pstMic.pszCprName	= "Face_6_Cpr";
    pstMic.pszFeatName	= "Face_6_Feat";
    pstMic.pszBinName	= "Face_6_Bin";

  }

  def	COLNAME_DBRUNLOG_Init(p:COLNAME_DBRUNLOG)
  {
    p.pszUserIP		= "UserIP";
    p.pszStartupTime	= "StartupTime";
    p.pszShutdownTime	= "ShutdownTime";
    p.pszShutdownUser	= "ShutdownUser";
    p.pszSvrVersion	= "SvrVersion";
    p.pszComment		= "Comment";
    p.pszComputerName	= "ComputerName";
  }

  def	COLNAME_TPQUALCHK_Init(pstChk:COLNAME_TPQUALCHK)
  {

    pstChk.pszKey		= "Key";
    pstChk.pszStatus	= "Status";
    pstChk.pszAddTime	= "AddTime";
    pstChk.pszChkTime	= "CheckTime";
    pstChk.pszInputUnitCode	= "InputUnitCode";
    pstChk.pszChecker	= "Checker";
    //pstChk.pszComment	= "Comment";
    pstChk.pszQueSID		= "QueSID";
    pstChk.pszDBID		= "DBID";
    pstChk.pszTID		= "TID";
    pstChk.pszUserName	= "UserName";
    pstChk.pszDownloadTime	= "DownloadTime";
    pstChk.pszFingerIndex	= "FingerIndex";
    pstChk.pszOption		= "Options";
    pstChk.pszTQrySID		= "TQrySID";
    pstChk.pszLQrySID		= "LQrySID";

    pstChk.pszQryDBID		= "QryDBID";
    pstChk.pszQryTID		= "QryTID";
    pstChk.pszDestTenDBID	= "DestTenDBID";
    pstChk.pszDestTenTID	= "DestTenTID";
    pstChk.pszDestLatDBID	= "DestLatDBID";
    pstChk.pszDestLatTID	= "DestLatTID";
  }

  def	COLNAME_TPQUALCHKWORKLOG_Init(pstName:COLNAME_TPQUALCHKWORKLOG)
  {

    pstName.pszKey		= "Key";
    pstName.pszChkTime	= "CheckTime";
    pstName.pszInputUnitCode	= "InputUnitCode";
    pstName.pszChecker	= "Checker";
    pstName.pszDBID	= "DBID";
    pstName.pszTID		= "TID";
    pstName.pszUserName	= "UserName";
    pstName.pszNeedRescan	= "NeedRescan";
    pstName.pszFingerIndex = "FingerIndex";
  }


  def	COLNAME_WORKLOG_Init(pstName:COLNAME_WORKLOG)
  {

    pstName.pszDateTime	= "DateTime";
    pstName.pszKey			= "Key";
    pstName.pszDBID		= "DBID";
    pstName.pszTID			= "TID";
    pstName.pszUserName	= "UserName";
    pstName.pszWorkType	= "WorkType";
    pstName.pszWorkClass	= "WorkClass";
    pstName.pszFingerImgChanged	= "FingerImgChanged";
    pstName.pszFingerMntChanged	= "FingerMntChanged";
    pstName.pszTPlainImgChanged	= "TPlainImgChanged";
    pstName.pszTPlainMntChanged	= "TPlainMntChanged";
    pstName.pszPalmImgChanged		= "PalmImgChanged";
    pstName.pszPalmMntChanged		= "PalmMntChanged";
    pstName.pszOtherChanged		= "OtherChanged";
    pstName.pszTextChanged			= "TextChanged";
    pstName.pszIsTwoFaceCard		= "IsTwoFaceCard";

    pstName.pszComputerName		= "ComputerName";
    pstName.pszFingerCnt			= "FingerCnt";
    pstName.pszFingerHQCnt			= "FingerHQCnt";
    pstName.pszTPlainCnt			= "TPlainCnt";
    pstName.pszTPlainHQCnt			= "TPlainHQCnt";
    pstName.pszPalmCnt				= "PalmCnt";
    pstName.pszPalmHQCnt			= "PalmHQCnt";
    pstName.pszEditTime			= "EditTime";	// how long have edited
  }

  def	COLNAME_MNTEDITLOG_Init(pstName:COLNAME_MNTEDITLOG)
  {

    pstName.pszKey			= "Key";
    pstName.pszUserName	= "UserName";
    pstName.pszDateTime	= "DateTime";
    pstName.pszDBID		= "DBID";
    pstName.pszTID			= "TID";
    pstName.pszFingerEditInfo	= "FingerEditInfo";
    pstName.pszPalmEditInfo	= "PalmEditInfo";
    pstName.pszTPlainEditInfo	= "TPlainEditInfo";
    pstName.pszComputerName	= "ComputerName";
  }

  def	COLNAME_EXFERRLOG_Init(pstName:COLNAME_EXFERRLOG)
  {
    pstName.pszKey				= "Key";
    pstName.pszComputerName	= "ComputerName";
    pstName.pszMntFormat		= "MntFormat";
    pstName.pszRetValue		= "RetValue";
    pstName.pszDBID			= "DBID";
    pstName.pszTID				= "TID";
    pstName.pszCompressMethod	= "CompressMethod";
    pstName.pszDateTime		= "DateTime";
    pstName.pszDllName			= "DllName";
    pstName.pszDllVersion		= "DllVersion";
    pstName.pszExfAppVersion	= "ExfAppVersion";
    pstName.pszFingerIndex		= "FingerIndex";
    pstName.pszSecondFingerIndex	= "SecondFingerIndex";
    pstName.pszErrorCode		= "ErrorCode";
  }

  def	COLNAME_LPVOICETEXT_Init(pstName:COLNAME_LPVOICETEXT)
  {
    pstName.pszComment				= "Comment";
    pstName.pszCreatorUnitCode		= "CreatorUnitCode";
    pstName.pszUpdatorUnitCode		= "UpdatorUnitCode";
    pstName.pszMicbUpdatorUserName	= "MicbUpdatorUserName";
    pstName.pszMicbUpdatorUnitCode	= "MicbUpdatorUnitCode";
    pstName.pszCaptureMethod		= "CaptureMethod";
    pstName.pszHitHistory			= "HitHistory";
    pstName.pszSeqNo				= "SeqNo";
  }

  def	COLNAME_LPFACETEXT_Init(pstName:COLNAME_LPFACETEXT)
  {
    pstName.pszComment				= "Comment";
    pstName.pszCreatorUnitCode		= "CreatorUnitCode";
    pstName.pszUpdatorUnitCode		= "UpdatorUnitCode";
    pstName.pszMicbUpdatorUserName	= "MicbUpdatorUserName";
    pstName.pszMicbUpdatorUnitCode	= "MicbUpdatorUnitCode";
    pstName.pszCaptureMethod		= "CaptureMethod";
    pstName.pszHitHistory			= "HitHistory";
    pstName.pszSeqNo				= "SeqNo";
  }

  def	COLNAME_QUERYSEARCHLOG_Init(pstName:COLNAME_QUERYSEARCHLOG)
  {
    pstName.pszKey		= "Key";
    pstName.pszQueryType	= "QueryType";
    pstName.pszDBRecCount	= "DBRecCount";
    pstName.pszFirstCandScore	= "FirstCandScore";
    pstName.pszFirstCandKey	= "FirstCandKey";
    pstName.pszFinishDateTime	= "FinishDateTime";
    pstName.pszSubmitDateTime	= "SubmitDateTime";
    pstName.pszSubmitUser		= "SubmitUser";
    pstName.pszSubmitUnitCode	= "SubmitUnitCode";
    /*
      pstName.pszDBID	= "DBID";
      pstName.pszTID		= "TID";
      pstName.pszDestDBID		= "DestDBID";
      pstName.pszDestTID			= "DestTID";
    */
    pstName.pszIsFifoQue		= "IsFifoQue";
    pstName.pszRecSearchedCnt	= "RecSearchedCnt";
    pstName.pszTimeUsed		= "TimeUsed";
    pstName.pszFlag			= "Flag";
    pstName.pszRmtFlag			= "RmtFlag";
    pstName.pszFinalCandCnt	= "FinalCandCnt";
    pstName.pszQUID			= "QUID";
    pstName.pszMICCount		= "MICCount";
    pstName.pszPriority		= "Priority";
    pstName.pszHitPoss			= "HitPoss";
  }

  def	COLNAME_QUERYCHECKLOG_OLD_Init(pstName:COLNAME_QUERYCHECKLOG_OLD)
  {
    pstName.pszKeyID				= "KeyID";
    pstName.pszSubmitUserName		= "SubmitUserName";
    pstName.pszCheckWorkType		= "CheckWorkType";	// check, recheck.
    pstName.pszQueryType			= "QueryType";
    pstName.pszHitPoss				= "HitPoss";
    pstName.pszIsRmtQuery			= "IsRmtQuery";
    pstName.pszSubmitTime			= "SubmitTime";
    pstName.pszFinishTime			= "FinishTime";
    pstName.pszCheckUserName		= "CheckUserName";
    pstName.pszCheckDateTime		= "CheckDateTime";
    pstName.pszVerifyResult		= "VerifyResult";
    pstName.pszRmtFlag				= "RmtFlag";
    pstName.pszFlag				= "Flag";
    pstName.pszReCheckUserName		= "ReCheckUserName";
    pstName.pszReCheckDateTime		= "ReCheckDateTime";
    pstName.pszSubmitUserUnitCode	= "SubmitUserUnitCode";
    pstName.pszCheckerUnitCode		= "CheckerUnitCode";
    pstName.pszReCheckerUnitCode	= "ReCheckerUnitCode";
    pstName.pszComputerIP			= "ComputerIP";		// query is sending from this ip.
    pstName.pszComputerName		= "ComputerName";	// query is sending from this ip.
    pstName.pszIsFofoQueQry		= "IsFofoQueQry";
    pstName.pszFirstCandScore		= "FirstCandScore";
    pstName.pszFirstCandKey		= "FirstCandKey";
    pstName.pszHitCandScore		= "HitCandScore";
    pstName.pszHitCandKey			= "HitCandKey";
    pstName.pszCandCount			= "CandCount";
    pstName.pszHitCandIndex		= "HitCandIndex";
  }

  def	COLNAME_QUERYSUBMITLOG_Init(pstName:COLNAME_QUERYSUBMITLOG)
  {
    pstName.pszKeyID				= "KeyID";
    pstName.pszSubmitUserName		= "SubmitUserName";
    pstName.pszSubmitTime			= "SubmitTime";
    pstName.pszSubmitUserUnitCode	= "SubmitUserUnitCode";
    pstName.pszCandCount			= "CandCount";
    pstName.pszQueryType			= "QueryType";
    pstName.pszIsFifoQueQry		= "IsFifoQueQry";
    pstName.pszFlag				= "Flag";
    pstName.pszRmtFlag				= "RmtFlag";
    //	pstName.pszIsRmtQuery			= "IsRmtQuery";
    pstName.pszComputerIP			= "ComputerIP";
    pstName.pszComputerName		= "ComputerName";
    pstName.pszQryUID				= "QryUID";
    pstName.pszMICCount			= "MICCount";
    pstName.pszPriority			= "Priority";
    pstName.pszStatus				= "Status";
  }

  def	COLNAME_QUERYCHECKLOG_Init(pstName:COLNAME_QUERYCHECKLOG)
  {
    pstName.pszKeyID				= "KeyID";
    pstName.pszQUID				= "QUID";
    pstName.pszCheckUserName		= "CheckUserName";
    pstName.pszFirstCandScore		= "FirstCandScore";
    pstName.pszCandCount			= "CandCount";
    pstName.pszSearchFinishTime	= "SearchFinishTime";
    pstName.pszCheckDateTime		= "CheckDateTime";
    pstName.pszSubmitUser			= "SubmitUser";
    pstName.pszSubmitUserUnitCode	= "SubmitUserUnitCode";
    pstName.pszCheckerUnitCode		= "CheckerUnitCode";
    pstName.pszQueryType			= "QueryType";
    pstName.pszHitPoss				= "HitPoss";
    pstName.pszCheckResult			= "CheckResult";
    pstName.pszQryRmtFlag			= "QryRmtFlag";
    pstName.pszHitCandCnt			= "HitCandCnt";
    pstName.pszQryFlag				= "QryFlag";
  }

  def	COLNAME_QUERYRECHECKLOG_Init(pstName:COLNAME_QUERYRECHECKLOG)
  {
    pstName.pszKeyID				= "KeyID";
    pstName.pszQUID				= "QUID";
    pstName.pszCheckUserName		= "CheckUserName";
    pstName.pszReCheckUserName		= "ReCheckUserName";
    pstName.pszCheckDateTime		= "CheckDateTime";
    pstName.pszReCheckDateTime		= "ReCheckDateTime";
    pstName.pszSubmitUserUnitCode	= "SubmitUserUnitCode";
    pstName.pszCheckerUnitCode		= "CheckerUnitCode";
    pstName.pszReCheckerUnitCode	= "ReCheckerUnitCode";
    pstName.pszQueryType			= "QueryType";
    pstName.pszVerifyResult		= "VerifyResult";
    pstName.pszRecordType			= "RecordType";
    pstName.pszQryRmtFlag			= "QryRmtFlag";
    pstName.pszHitCandCnt			= "HitCandCnt";
    pstName.pszQryFlag				= "QryFlag";
  }

  def	COLNAME_LPGROUP_Init(pstName:COLNAME_LPGROUP)
  {
    pstName.pszGroupID			= "GroupID";
    pstName.pszKeyList			= "KeyList";
    pstName.pszCreateUserName	= "CreateUserName";
    pstName.pszCreateDateTime	= "CreateDateTime";
    pstName.pszCreateUnitCode	= "CreateUnitCode";
    pstName.pszUpdateUserName	= "UpdateUserName";
    pstName.pszUpdateDateTime	= "UpdateDateTime";
    pstName.pszUpdateUnitCode	= "UpdateUnitCode";
    pstName.pszFlag			= "Flag";
  }

  def	COLNAME_CASEGROUP_Init(pstName:COLNAME_CASEGROUP)
  {
    pstName.pszCaseGroupID		= "CaseGroupID";
    pstName.pszKeyList			= "KeyList";
    pstName.pszCreateUserName	= "CreateUserName";
    pstName.pszCreateDateTime	= "CreateDateTime";
    pstName.pszCreateUnitCode	= "CreateUnitCode";
    pstName.pszUpdateUserName	= "UpdateUserName";
    pstName.pszUpdateDateTime	= "UpdateDateTime";
    pstName.pszUpdateUnitCode	= "UpdateUnitCode";
  }

  def	COLNAME_TPLP_ASSOCIATE_Init(pstName:COLNAME_TPLP_ASSOCIATE)
  {
    pstName.pszTPPersonID		= "TPPersonID";
    pstName.pszLPGroupID		= "LPGroupID";
    pstName.pszCreateUserName	= "CreateUserName";
    pstName.pszCreateDateTime	= "CreateDateTime";
    pstName.pszCreateUnitCode	= "CreateUnitCode";
    pstName.pszUpdateUserName	= "UpdateUserName";
    pstName.pszUpdateDateTime	= "UpdateDateTime";
    pstName.pszUpdateUnitCode	= "UpdateUnitCode";
    pstName.pszIdentifyUserName	= "IdentifyUserName";
    pstName.pszIdentifyDateTime	= "IdentifyDateTime";
    pstName.pszIdentifyUnitCode	= "IdentifyUnitCode";
    pstName.pszTPDBID				= "TPDBID";
    pstName.pszTPTID				= "TPTID";
    pstName.pszLPDBID				= "LPDBID";
    pstName.pszLPTID				= "LPTID";
  }

  def	COLNAME_TPLP_UNMATCH_Init(pstName:COLNAME_TPLP_UNMATCH)
  {
    pstName.pszTPPersonID		= "TPPersonID";
    pstName.pszLPGroupID		= "LPGroupID";
    pstName.pszLatFgGroup		= "LatFgGroup";
    pstName.pszLatKeyType		= "LatKeyType";
    pstName.pszTPIndex			= "TPIndex";
    pstName.pszIdentifyUserName	= "IdentifyUserName";
    pstName.pszIdentifyDateTime	= "IdentifyDateTime";
    pstName.pszTPDBID				= "TPDBID";
    pstName.pszTPTID				= "TPTID";
    pstName.pszLPDBID				= "LPDBID";
    pstName.pszLPTID				= "LPTID";
  }

  def	COLNAME_TP_UNMATCH_Init(pstName:COLNAME_TP_UNMATCH)
  {
    pstName.pszKey1		= "Key1";
    pstName.pszKey2		= "Key2";
    pstName.pszIdentifyUserName	= "IdentifyUserName";
    pstName.pszIdentifyDateTime	= "IdentifyDateTime";
  }

  def	COLNAME_LP_UNMATCH_Init(pstName:COLNAME_LP_UNMATCH)
  {
    pstName.pszKey1		= "Key1";
    pstName.pszFgGroup1	= "FgGroup1";
    pstName.pszKey2		= "Key2";
    pstName.pszFgGroup2	= "FgGroup2";
    pstName.pszIdentifyUserName	= "IdentifyUserName";
    pstName.pszIdentifyDateTime	= "IdentifyDateTime";
  }


  def	COLNAME_LP_MULTIMNT_Init(pstName:COLNAME_LP_MULTIMNT)
  {
    pstName.pszMnt1			= "Mnt1";
    pstName.pszBin1			= "Bin1";
    /*
      pstName.pszUpdateUser1		= "UpdateUser1";
      pstName.pszUpdateUnitCode1	= "UpdateUnitCode1";
      pstName.pszUpdateDateTime1	= "UpdateDateTime1";
    */

    pstName.pszMnt2			= "Mnt2";
    pstName.pszBin2			= "Bin2";
    /*
      pstName.pszUpdateUser2		= "UpdateUser2";
      pstName.pszUpdateUnitCode2	= "UpdateUnitCode2";
      pstName.pszUpdateDateTime2	= "UpdateDateTime2";
    */

    pstName.pszMnt3			= "Mnt3";
    pstName.pszBin3			= "Bin3";
    /*
      pstName.pszUpdateUser3		= "UpdateUser3";
      pstName.pszUpdateUnitCode3	= "UpdateUnitCode3";
      pstName.pszUpdateDateTime3	= "UpdateDateTime3";
    */

    pstName.pszMnt4			= "Mnt4";
    pstName.pszBin4			= "Bin4";
    /*
      pstName.pszUpdateUser4		= "UpdateUser4";
      pstName.pszUpdateUnitCode4	= "UpdateUnitCode4";
      pstName.pszUpdateDateTime4	= "UpdateDateTime4";
    */

    pstName.pszMnt5			= "Mnt5";
    pstName.pszBin5			= "Bin5";
    /*
      pstName.pszUpdateUser5		= "UpdateUser5";
      pstName.pszUpdateUnitCode5	= "UpdateUnitCode5";
      pstName.pszUpdateDateTime5	= "UpdateDateTime5";
    */

    pstName.pszMnt6			= "Mnt6";
    pstName.pszBin6			= "Bin6";

    pstName.pszMnt7			= "Mnt7";
    pstName.pszBin7			= "Bin7";

    pstName.pszMnt8			= "Mnt8";
    pstName.pszBin8			= "Bin8";

    pstName.pszMnt9			= "Mnt9";
    pstName.pszBin9			= "Bin9";
  }

  def	COLNAME_TPLP_FPXQUE_TABLE_Init(pstName:COLNAME_TPLP_FPXQUE_TABLE)
  {
    pstName.pszStatus			= "Status";
    pstName.pszDataType		= "DataType";
    pstName.pszDBID			= "DBID";
    pstName.pszTID				= "TID";
    pstName.pszFlag			= "Flag";
    pstName.pszOption			= "Options";	// in oracle option is reserved word
    pstName.pszLastOpTime		= "LastOpTime";
    pstName.pszLastOpUser		= "LastOpUser";
    pstName.pszSubmitUser		= "SubmitUser";
    pstName.pszSubmitUnitCode	= "SubmitUnitCode";
    pstName.pszSubmitDateTime	= "SubmitDateTime";
    pstName.pszKey				= "Key";
    pstName.pszPersonIDCaseID	= "PersonIDCaseID";
    pstName.pszPurpose			= "Purpose";
    pstName.pszFPXComment		= "FPXComment";
    pstName.pszAFISComment		= "AFISComment";
    pstName.pszRmtDownloadDateTime	= "RmtDownloadDateTime";
    pstName.pszRmtUploadDateTime	= "RmtUploadDateTime";
    pstName.pszRmtUploadStatus		= "RmtUploadStatus";
    pstName.pszRmtDownloadStatus	= "RmtDownloadStatus";
  }

  def	COLNAME_TPLP_FPXLOG_TABLE_Init(pstName:COLNAME_TPLP_FPXLOG_TABLE)
  {
    pstName.pszStatus			= "Status";
    pstName.pszDataType		= "DataType";
    pstName.pszDBID			= "DBID";
    pstName.pszTID				= "TID";
    pstName.pszFlag			= "Flag";
    pstName.pszOption			= "Options";
    pstName.pszOpTime			= "OpTime";
    pstName.pszOpUser			= "OpUser";
    pstName.pszSubmitUser		= "SubmitUser";
    pstName.pszSubmitUnitCode	= "SubmitUnitCode";
    pstName.pszSubmitDateTime	= "SubmitDateTime";
    pstName.pszKey				= "Key";
    pstName.pszPersonIDCaseID	= "PersonIDCaseID";
    pstName.pszPurpose			= "Purpose";
  }

  def	COLNAME_QUERY_FPXQUE_TABLE_Init(pstName:COLNAME_QUERY_FPXQUE_TABLE)
  {
    pstName.pszStatus			= "Status";
    pstName.pszRecordType		= "RecordType";
    pstName.pszDBID			= "DBID";
    pstName.pszTID				= "TID";
    pstName.pszFlag			= "Flag";
    pstName.pszOption			= "Options";
    pstName.pszLastOpTime		= "LastOpTime";
    pstName.pszLastOpUser		= "LastOpUser";
    pstName.pszKey				= "Key";
    pstName.pszQueryType		= "QueryType";
    pstName.pszRequestTime		= "RequestTime";
    pstName.pszRequestUser		= "RequestUser";
    pstName.pszRequestUnitCode	= "RequestUnitCode";
    pstName.pszTaskID			= "TaskID";
    pstName.pszForeignUnitCode	= "ForeignUnitCode";
    pstName.pszFPXComment		= "FPXComment";
    pstName.pszAFISComment		= "AFISComment";
    pstName.pszVerifyResult	= "VerifyResult";
    pstName.pszRmtDownloadDateTime	= "RmtDownloadDateTime";
    pstName.pszRmtUploadDateTime	= "RmtUploadDateTime";
    pstName.pszRmtUploadStatus		= "RmtUploadStatus";
    pstName.pszRmtDownloadStatus	= "RmtDownloadStatus";
  }

  def	COLNAME_QUERY_FPXLOG_TABLE_Init(pstName:COLNAME_QUERY_FPXLOG_TABLE)
  {
    pstName.pszStatus				= "Status";
    pstName.pszQueryType			= "QueryType";
    pstName.pszRecordType			= "RecordType";
    pstName.pszDBID				= "DBID";
    pstName.pszTID					= "TID";
    pstName.pszFlag				= "Flag";
    pstName.pszOption				= "Options";
    pstName.pszKey					= "Key";
    pstName.pszForeignUnitCode		= "ForeignUnitCode";
    pstName.pszRequestTime			= "RequestTime";
    pstName.pszRequestUser			= "RequestUser";
    pstName.pszRequestUnitCode		= "RequestUnitCode";
    pstName.pszTaskID				= "TaskID";
    pstName.pszVerifyResult		= "VerifyResult";
  }

  def	COLNAME_LICENSEMGR_Init(pstName:COLNAME_LICENSEMGR)
  {
    pstName.pszUserID			= "UserID";			// currently it's mac address.
    pstName.pszUnitCode		= "UnitCode";
    pstName.pszCreateUser		= "CreateUser";
    pstName.pszUpdateUser		= "UpdateUser";
    pstName.pszCreateTime		= "CreateTime";
    pstName.pszUpdateTime		= "UpdateTime";
    pstName.pszSnoUpdateTime	= "SnoUpdateTime";	// if pszSno changed, then change this time.
    pstName.pszCSigUpdateTime	= "CSigUpdateTime";	// if pszCSig changed, then change this time
    pstName.pszSNo				= "SNo";		// serial no. STRING.
    pstName.pszCSig			= "CSig";		// computer signature. TEXT
  }

  def	COLNAME_QRYASSIGN_Init(pstName:COLNAME_QRYASSIGN)
  {
    pstName.pszUserID					= "UserID";
    pstName.pszTotalCaseChecked		= "TotalCaseChecked";
    pstName.pszTotalLTFingerChecked	= "TotalLTFingerChecked";
    pstName.pszTotalLLFingerChecked	= "TotalLLFingerChecked";
    pstName.pszTotalTLFingerChecked	= "TotalTLFingerChecked";
    pstName.pszTotalTTFingerChecked	= "TotalTTFingerChecked";

    pstName.pszCurCaseChecked			= "CurCaseChecked";
    pstName.pszCurLTFingerChecked		= "CurLTFingerChecked";
    pstName.pszCurLLFingerChecked		= "CurLLFingerChecked";
    pstName.pszCurTLFingerChecked		= "CurTLFingerChecked";
    pstName.pszCurTTFingerChecked		= "CurTTFingerChecked";

    pstName.pszCurStageStartDate		= "CurStageStartDate";
    pstName.pszCurStageStopDate		= "CurStageStopDate";

    pstName.pszCreateUser	= "CreateUser";
    pstName.pszCreateTime	= "CreateTime";
    pstName.pszUpdateUser	= "UpdateUser";
    pstName.pszUpdateTime	= "UpdateTime";
  }

  def	COLNAME_LFICDataTransmit_Init(pstName:COLNAME_LFIC_DATATRANSMITSTATUS)
  {
    pstName.pszCardKey		= "CardKey";
    pstName.pszMISPersonID	= "MISPersonID";
    pstName.pszDBID		= "DBID";
    pstName.pszTID			= "TID";
    pstName.pszUnitCode	= "UnitCode";
    pstName.pszCreator		= "Creator";
    pstName.pszUpdater		= "Updater";
    pstName.pszCreateTime	= "CreateTime";
    pstName.pszUpdateTime	= "UpdateTime";
    pstName.pszSystemType	= "SystemType";
    pstName.pszStatus		= "Status";
  }

  def	COLNAME_LFICFeedback_Init(pstName:COLNAME_LFIC_FEEDBACKINFO)
  {
    pstName.pszCardKey		= "CardKey";
    pstName.pszMISPersonID	= "MISPersonID";
    pstName.pszDBID		= "DBID";
    pstName.pszTID			= "TID";
    pstName.pszUnitCode	= "UnitCode";
    pstName.pszUserName	= "UserName";
    pstName.pszCreateTime	= "CreateTime";
    pstName.pszFeedbackDate= "FeedbackDate";
    pstName.pszSystemType	= "SystemType";
    pstName.pszType		= "Type";
    pstName.pszStatus		= "Status";
    pstName.pszDescription	= "Description";
    pstName.pszUpdateTime  = "UpdateTime";
  }

  //!< 与公安部协查平台的互连、数据交换、日志信息
  def	COLNAME_GAFPTXCDataTable_Init(pstName:GAFIS_GAFPTXCDATATABLECOLNAME)
  {
    pstName.pszDBID	= "DBID";
    pstName.pszTID		= "TID";
    pstName.pszCardID	= "CardID";
    pstName.pszXCPriority	= "XCPriority";
    pstName.pszXCPurpose	= "XCPurpose";
    pstName.pszXCSource	= "XCSource";
    pstName.pszXCStatus	= "XCStatus";
    pstName.pszAssoPersonID = "AssoPersonID";
    pstName.pszAssoCaseID	= "AssoCaseID";
    pstName.pszXCExpireTime= "XCExpireTime";
    pstName.pszXCCancelTime= "XCCancelTime";
    pstName.pszXCUnitCode	= "XCUnitCode";
    pstName.pszXCUnitName	= "XCUnitName";
    pstName.pszXCDateTime	= "XCDateTime";
    pstName.pszLinkMan		= "LinkMan";
    pstName.pszLinkPhone	= "LinkPhone";
    pstName.pszApprovedBy	= "ApprovedBy";
    pstName.pszXCDescript	= "XCDescript";
    pstName.pszCreateTime	= "CreateTime";
    pstName.pszUpdateTime	= "UpdateTime";
    pstName.pszLatestOpTime = "LatestOpTime";
    pstName.pszCheckTime	= "CheckTime";
    pstName.pszIsValid		= "IsValid";
    pstName.pszFlag = "Flag";
    pstName.pszQryTaskID	= "QryTaskID";
    pstName.pszTaskID		= "TaskID";
  }

  def	COLNAME_GAFPTXCLogTable_Init(pstName:GAFIS_GAFPTXCLOGTABLECOLNAME)
  {
    pstName.pszXCType = "XCType";
    pstName.pszXCPurpose = "XCPurpose";
    pstName.pszCardID	= "CardID";
    pstName.pszCreateTime	= "CreateTime";
    pstName.pszUpdateTime	= "UpdateTime";
  }

  def	COLNAME_WantedListTable_Init(pstName:GAFIS_WANTEDLISTTABLECOLNAME)
  {
    pstName.pszWanted_Type = "Wanted_Type";
    pstName.pszWanted_Status = "Wanted_Status";
    pstName.pszWanted_NO = "Wanted_NO";
    pstName.pszWanted_By = "Wanted_By";
    pstName.pszConnect_With = "Connect_With";
    pstName.pszConnect_Phnoe = "Connect_Phnoe";
    pstName.pszCreate_Time = "CreateTime";
    pstName.pszCreate_User = "CreateUser";
    pstName.pszCreate_UnitCode = "CreateUnitCode";
    pstName.pszUpdate_Time = "UpdateTime";
    pstName.pszUpdate_User = "UpdateUser";
    pstName.pszUpdate_UnitCode = "UpdateUnitCode";
    pstName.pszComments = "Comments";
    pstName.pszCaseID = "CaseID";
    pstName.pszCaseClassCode = "CaseClassCode";
    pstName.pszWanted_TpCardID = "Wanted_TpCardID";
    pstName.pszName = "Name";
    pstName.pszAlias = "Alias";
    pstName.pszSexCode = "SexCode";
    pstName.pszBirthDate = "BirthDate";
    pstName.pszShenFenID = "ShenFenID";
    pstName.pszHuKouPlaceCode = "HuKouPlaceCode";
    pstName.pszHuKouPlace = "HuKouPlace";
    pstName.pszAddressCode = "AddressCode";
    pstName.pszAddress = "Address";
  }

  def	COLNAME_WantedTpCardTable_Init(pstName:GAFIS_WANTEDTPCARDTABLECOLNAME)
  {
    pstName.pszWanted_NO = "Wanted_NO";
    pstName.pszCardID = "CardID";
    pstName.pszCreateTime = "CreateTime";
    pstName.pszUpdateTime = "UpdateTime";
    pstName.pszCreateUser = "CreateUser";
    pstName.pszUpdateUser = "UpdateUser";
    pstName.pszFillInUser = "FillInUser";
    pstName.pszFillInUnitCode = "FillInUnitCode";
    pstName.pszFillInTime = "FillInTime";
    pstName.pszDBID = "DBID";
  }

  def	COLNAME_WantedOpLogTable_Init(pstName:GAFIS_WANTEDOPLOGTABLECOLNAME)
  {
    pstName.pszWanted_NO = "Wanted_NO";
    pstName.pszOP_Type = "OP_Type";
    pstName.pszTpCardID = "TpCardID";
    pstName.pszCreateTime = "CreateTime";
    pstName.pszUpdateTime = "UpdateTime";
  }

  def	COLNAME_SHXK_DataStatusTable_Init(pstName:SHXK_DATASTATUSCOLNAME)
  {
    pstName.pszXk_No = "XKID";
    pstName.pszResult1 = "EXECUTERESULT1";
    pstName.pszResult2 = "EXECUTERESULT2";
    pstName.pszDataFile = "DATAFILENAME";
    pstName.pszIsAgain = "KEEPEXECUTE";
    pstName.pszComment = "DSCE";
    pstName.pszUpdateTime = "UPDATETIME";
  }

  def	COLNAME_SHXK_MatchInfoTable_Init(pstName:SHXK_MATCHINFOCOLNAME)
  {
    pstName.pszQryID = "QUERYID";
    pstName.pszXk_No = "XKID";
    pstName.pszLatCardNo = "LPCARDID";
    pstName.pszQryType = "QUERYTYPE";
    pstName.pszCount = "MATCHCOUNT";
    pstName.pszSubmitTime = "COMMITTIME";
    pstName.pszMatchTime = "MATCHTIME";
    pstName.pszSubmitUser = "COMMITUSER";
    pstName.pszSubmitUnit = "COMMITUNITCODE";
    pstName.pszResult1 = "EXECUTERESULT1";
    pstName.pszResult2 = "EXECUTERESULT2";
    pstName.pszIsAgain = "KEEPEXECUTE";
    pstName.pszComment = "DSCE";
    pstName.pszUpdateTime = "UPDATETIME";
    pstName.pszCandList = "CANDLIST";
  }

  def	COLNAME_SHXK_CaseStatusTable_Init(pstName:SHXK_CASESTATUSCOLNAME)
  {
    pstName.pszXk_No = "XKID";
    pstName.pszBrokenTime = "BREAKDATE";
    pstName.pszStatus = "BREAKSTA";
    pstName.pszSysType = "BREAKSYSCODE";
    pstName.pszUpdateTime = "UPDATEDATE";
  }

  def	COLNAME_SHXK_CaseTextTable_Init(pstName:SHXK_CASETEXTCOLNAME)
  {
    pstName.pszXk_No = "XKID";
    pstName.pszCaseID = "CASEID";
    pstName.pszOccurUnit = "CASEPLACECODE";
    pstName.pszOccurPlace = "CASEPLACE";
    pstName.pszOccurTime = "CASECREATTIME";
    pstName.pszExtractor = "CREATTOR";
    pstName.pszExtractUnit = "CREATUNITCODE";
    pstName.pszExtractPlace = "CREATUNITNAME";
    pstName.pszExtractTime = "CREATTIME";
    pstName.pszCaseType1Code = "CASECLASS1";
    pstName.pszCaseType2Code = "CASECLASS2";
    pstName.pszCaseType3Code = "CASECLASS3";
    pstName.pszMoney = "CASEMONEY";
    pstName.pszCaseStatus = "CASESTATE";
    pstName.pszComment = "CASEDESCRIPT";
    pstName.pszUpdateTime = "UPDATETIME";
  }

  def	COLNAME_NJ_DelDataTable_Init(pstName:NJ_DELDATACOLNAME)
  {
    pstName.pKey = "DELKEY";
    pstName.pKeyType = "KEYTYPE";
    pstName.pCurUser = "OPUSER";
    pstName.pResult = "RESULT";
    pstName.pOpNum = "OPNUM";
    pstName.pTaskID = "TASKID";
  }

  //!<add by wangkui
  def COLNAME_XCDataTable_Init( pstName:COLNAME_XCDATA)
  {
    pstName.pszCardID = "CardID";
    pstName.pszCardType = "CardType";
    pstName.pszTaskID = "TaskID";
    pstName.pszQryID = "QryID";
    pstName.pszTaskType = "TaskType";
    pstName.pszSendTime = "SendTime";
    pstName.pszRecvTime = "RecvTime";
    pstName.pszEntryTime = "EntryTime";
    pstName.pszUpdateTime = "UpdateTime";
    pstName.pszResendTime = "ResendTime";
    pstName.pszCheckTime = "CheckTime";
    pstName.pszCancelTime = "CancelTime";
    pstName.pszReportTime = "ReportTime";
    pstName.pszExtractReturnTime = "ExtractReturnTime";
    pstName.pszCancelStatus = "CancelStatus";
    pstName.pszCancelTaskFailedReason = "CancelTaskFailedReason";
    pstName.pszCancelTaskCount = "CancelTaskCount";
    pstName.pszCancelTaskMaxCount = "CancelTaskMaxCount";
    pstName.pszExtractStatus = "ExtractStatus";
    pstName.pszExtractFailedReason = "ExtractFailedReason";
    pstName.pszReportStatus = "ReportStatus";
    pstName.pszReportFailedReason = "ReportFailedReason";
    pstName.pszReportCount = "ReportCount";
    pstName.pszReportMaxCount = "ReportMaxCount";
    pstName.pszXCPurpose = "XCPurpose";
    pstName.pszXCStatus = "XCStatus";
    pstName.pszEntryStatus = "EntryStatus";
    pstName.pszEntryMaxCount = "EntryMaxCount";
    pstName.pszEntryCount = "EntryCount";
    pstName.pszEntryFailedReason = "EntryFailedReason";
    pstName.pszRecvAck = "RecvAck";
    pstName.pszXCLevel = "XCLevel";
    pstName.pszBKFlag = "BKFlag";
    pstName.pszReportCardID = "ReportCardID";
  }
  //<!add by wangkui at 2014.12.16
  def COLNAME_XCReportData_Table_Init(pstName:COLNAME_XCREPORTDATA)
  {
    pstName.pszKey = "Key";
    pstName.pszCardID = "CardID";
    pstName.pszCardType = "CardType";
    pstName.pszTaskType = "TaskType";
    pstName.pszReportTime = "ReportTime";
    pstName.pszReportStatus = "ReportStatus";
    pstName.pszReportFailedReason = "ReportFailedReason";
    pstName.pszReportCount = "ReportCount";
    pstName.pszReportMaxCount = "ReportMaxCount";
    pstName.pszInRAID = "InRAID";
    pstName.pszOutRAID = "OutRAID";
  }
  def	COLNAME_TTRelation_Table_Init(pstName:COLNAME_TT_RELATION)
  {
    pstName.pszPersonNo1		="PersonNo1";
    pstName.pszPersonNo2		="PersonNo2";
    pstName.pszMatchUnitCode	="MatchUnitCode";
    pstName.pszMatchUnitName	="MatchUnitName";
    pstName.pszMatchUser		="MatchUser";
    pstName.pszMatchDateTime	="MatchDateTime";
    pstName.pszNote			="Note";
    pstName.pszReportUser		="ReportUser";
    pstName.pszReportDateTime	="ReportDateTime";
    pstName.pszApprovalUser	="ApprovalUser";
    pstName.pszApprovalDateTime="ApprovalDateTime";
    pstName.pszReportUnitCode	="ReportUnitCode";
    pstName.pszReportUnitName	="ReportUnitName";
    pstName.pszRecheckUser		="RecheckUser";
    pstName.pszRecheckUnitCode	="RecheckUnitCode";
    pstName.pszRecheckDateTime	="RecheckDateTime";
    pstName.pszGroupID			="GroupID";

  }
  def	COLNAME_TTCand_Table_Init(pstName:COLNAME_TT_CAND)
  {
    pstName.pszPersonNo1		= "PersonNo1";
    pstName.pszPersonNo2		= "PersonNo2";
    pstName.pszMatchUnitCode	= "MatchUnitCode";
    pstName.pszMatchDateTime	= "MatchDateTime";
    pstName.pszScore			= "Score";
    pstName.pszRank			= "Rank";
    pstName.pszGroupID			= "GroupID";
  };
  def	GAFIS_COLNAME_Init():COLNAMESTRUCT=
  {
    val g_stCN = new COLNAMESTRUCT
    COLNAME_TPLP_FPX_COMMON_Init(g_stCN.stTPLPFPXCommon);

    ////////////////////////////////////////////TENPRINT///////-------START----------///////////////////////
    g_stCN.stTcID.pszName = "CardID";
    g_stCN.stTPnID.pszName = "PersonID";
    g_stCN.stTCardCount.pszName = "TPCardCount";
    g_stCN.stTCardIDList.pszName = "TPCardIDList";

    // signature name
    g_stCN.stTSign(0).pszImgName = "CriminalSignatureIMG";
    g_stCN.stTSign(1).pszImgName = "PrinterSignatureIMG";

    g_stCN.stTSign(0).pszMntName	= "CriminalSignatureMnt";
    g_stCN.stTSign(0).pszCprName	= "CriminalSignatureCpr";
    g_stCN.stTSign(0).pszFeatName	= "CriminalSignatureFeat";
    g_stCN.stTSign(0).pszBinName	= "CriminalSignatureBin";

    g_stCN.stTSign(1).pszMntName	= "PrinterSignatureMnt";
    g_stCN.stTSign(1).pszCprName	= "PrinterSignatureCpr";
    g_stCN.stTSign(1).pszFeatName	= "PrinterSignatureFeat";
    g_stCN.stTSign(1).pszBinName	= "PrinterSignatureBin";

    // tenprint minutia name
    g_stCN.stTFg(0).pszMntName = "FingerRHMMnt";
    g_stCN.stTFg(1).pszMntName = "FingerRHSMnt";
    g_stCN.stTFg(2).pszMntName = "FingerRHZMnt";
    g_stCN.stTFg(3).pszMntName = "FingerRHHMnt";
    g_stCN.stTFg(4).pszMntName = "FingerRHXMnt";
    g_stCN.stTFg(5).pszMntName = "FingerLHMMnt";
    g_stCN.stTFg(6).pszMntName = "FingerLHSMnt";
    g_stCN.stTFg(7).pszMntName = "FingerLHZMnt";
    g_stCN.stTFg(8).pszMntName = "FingerLHHMnt";
    g_stCN.stTFg(9).pszMntName = "FingerLHXMnt";

    // tenprint image name
    g_stCN.stTFg(0).pszImgName = "FingerRHMImg";
    g_stCN.stTFg(1).pszImgName = "FingerRHSImg";
    g_stCN.stTFg(2).pszImgName = "FingerRHZImg";
    g_stCN.stTFg(3).pszImgName = "FingerRHHImg";
    g_stCN.stTFg(4).pszImgName = "FingerRHXImg";
    g_stCN.stTFg(5).pszImgName = "FingerLHMImg";
    g_stCN.stTFg(6).pszImgName = "FingerLHSImg";
    g_stCN.stTFg(7).pszImgName = "FingerLHZImg";
    g_stCN.stTFg(8).pszImgName = "FingerLHHImg";
    g_stCN.stTFg(9).pszImgName = "FingerLHXImg";

    // tenprint cpr name
    g_stCN.stTFg(0).pszCprName = "FingerRHMCpr";
    g_stCN.stTFg(1).pszCprName = "FingerRHSCpr";
    g_stCN.stTFg(2).pszCprName = "FingerRHZCpr";
    g_stCN.stTFg(3).pszCprName = "FingerRHHCpr";
    g_stCN.stTFg(4).pszCprName = "FingerRHXCpr";
    g_stCN.stTFg(5).pszCprName = "FingerLHMCpr";
    g_stCN.stTFg(6).pszCprName = "FingerLHSCpr";
    g_stCN.stTFg(7).pszCprName = "FingerLHZCpr";
    g_stCN.stTFg(8).pszCprName = "FingerLHHCpr";
    g_stCN.stTFg(9).pszCprName = "FingerLHXCpr";

    // tenprint binary data name
    g_stCN.stTFg(0).pszBinName = "FingerRHMBin";
    g_stCN.stTFg(1).pszBinName = "FingerRHSBin";
    g_stCN.stTFg(2).pszBinName = "FingerRHZBin";
    g_stCN.stTFg(3).pszBinName = "FingerRHHBin";
    g_stCN.stTFg(4).pszBinName = "FingerRHXBin";
    g_stCN.stTFg(5).pszBinName = "FingerLHMBin";
    g_stCN.stTFg(6).pszBinName = "FingerLHSBin";
    g_stCN.stTFg(7).pszBinName = "FingerLHZBin";
    g_stCN.stTFg(8).pszBinName = "FingerLHHBin";
    g_stCN.stTFg(9).pszBinName = "FingerLHXBin";

    // tenprint feature name
    g_stCN.stTFg(0).pszFeatName = "FingerRHMFeat";
    g_stCN.stTFg(1).pszFeatName = "FingerRHSFeat";
    g_stCN.stTFg(2).pszFeatName = "FingerRHZFeat";
    g_stCN.stTFg(3).pszFeatName = "FingerRHHFeat";
    g_stCN.stTFg(4).pszFeatName = "FingerRHXFeat";
    g_stCN.stTFg(5).pszFeatName = "FingerLHMFeat";
    g_stCN.stTFg(6).pszFeatName = "FingerLHSFeat";
    g_stCN.stTFg(7).pszFeatName = "FingerLHZFeat";
    g_stCN.stTFg(8).pszFeatName = "FingerLHHFeat";
    g_stCN.stTFg(9).pszFeatName = "FingerLHXFeat";

    GAFIS_TP_PLAIN_InitColName(g_stCN.stTpf);
    // tenprint palm, right hand
    //    || || || ||
    // || || || || || PalmFinger Part, stTPm(2), stTPm(3)
    // || || || || ||
    //  \           /
    //   |          | Palm Part, stTPm(0), stTPm(1)
    //    \        /
    //     --------
    // right hand
    g_stCN.stTPm(0).pszMntName = "PalmRMnt";
    g_stCN.stTPm(0).pszImgName = "PalmRImg";
    g_stCN.stTPm(0).pszCprName = "PalmRCpr";
    g_stCN.stTPm(0).pszBinName = "PalmRBin";
    g_stCN.stTPm(0).pszFeatName = "PalmRFeat";

    // tenprint palm left hand
    g_stCN.stTPm(1).pszMntName = "PalmLMnt";
    g_stCN.stTPm(1).pszImgName = "PalmLImg";
    g_stCN.stTPm(1).pszCprName = "PalmLCpr";
    g_stCN.stTPm(1).pszBinName = "PalmLBin";
    g_stCN.stTPm(1).pszFeatName = "PalmLFeat";

    // tenprint palm finger part right hand
    g_stCN.stTPm(2).pszMntName = "PalmFingerRMnt";
    g_stCN.stTPm(2).pszImgName = "PalmFingerRImg";
    g_stCN.stTPm(2).pszCprName = "PalmFingerRCpr";
    g_stCN.stTPm(2).pszBinName = "PalmFingerRBin";
    g_stCN.stTPm(2).pszFeatName = "PalmFingerRFeat";

    // tenprint palm finger part left hand
    g_stCN.stTPm(3).pszMntName = "PalmFingerLMnt";
    g_stCN.stTPm(3).pszImgName = "PalmFingerLImg";
    g_stCN.stTPm(3).pszCprName = "PalmFingerLCpr";
    g_stCN.stTPm(3).pszBinName = "PalmFingerLBin";
    g_stCN.stTPm(3).pszFeatName = "PalmFingerLFeat";

    // tenprint palm thumb lower part, right hand
    g_stCN.stTPm(4).pszMntName = "PalmThumbLowRMnt";
    g_stCN.stTPm(4).pszImgName = "PalmThumbLowRImg";
    g_stCN.stTPm(4).pszCprName = "PalmThumbLowRCpr";
    g_stCN.stTPm(4).pszBinName = "PalmThumbLowRBin";
    g_stCN.stTPm(4).pszFeatName = "PalmThumbLowRFeat";

    // tenprint palm thumb upper part, right hand
    g_stCN.stTPm(5).pszMntName = "PalmThumbUpRMnt";
    g_stCN.stTPm(5).pszImgName = "PalmThumbUpRImg";
    g_stCN.stTPm(5).pszCprName = "PalmThumbUpRCpr";
    g_stCN.stTPm(5).pszBinName = "PalmThumbUpRBin";
    g_stCN.stTPm(5).pszFeatName = "PalmThumbUpRFeat";

    // tenprint palm thumb lower part, left hand
    g_stCN.stTPm(6).pszMntName = "PalmThumbLowLMnt";
    g_stCN.stTPm(6).pszImgName = "PalmThumbLowLImg";
    g_stCN.stTPm(6).pszCprName = "PalmThumbLowLCpr";
    g_stCN.stTPm(6).pszBinName = "PalmThumbLowLBin";
    g_stCN.stTPm(6).pszFeatName = "PalmThumbLowLFeat";

    // tenprint palm thumb upper part, left hand
    g_stCN.stTPm(7).pszMntName = "PalmThumbUpLMnt";
    g_stCN.stTPm(7).pszImgName = "PalmThumbUpLImg";
    g_stCN.stTPm(7).pszCprName = "PalmThumbUpLCpr";
    g_stCN.stTPm(7).pszBinName = "PalmThumbUpLBin";
    g_stCN.stTPm(7).pszFeatName = "PalmThumbUpLFeat";

    /**
      * 右侧掌、左侧掌
      * Added by Beagle on Jun. 29, 2010
      */
//    #ifdef UTIL_PALMWRITER_ADDTODB
    g_stCN.stTPm(8).pszMntName = "PalmWriterRMnt";
    g_stCN.stTPm(8).pszImgName = "PalmWriterRImg";
    g_stCN.stTPm(8).pszCprName = "PalmWriterRCpr";
    g_stCN.stTPm(8).pszBinName = "PalmWriterRBin";
    g_stCN.stTPm(8).pszFeatName = "PalmWriterRFeat";

    g_stCN.stTPm(9).pszMntName = "PalmWriterLMnt";
    g_stCN.stTPm(9).pszImgName = "PalmWriterLImg";
    g_stCN.stTPm(9).pszCprName = "PalmWriterLCpr";
    g_stCN.stTPm(9).pszBinName = "PalmWriterLBin";
    g_stCN.stTPm(9).pszFeatName = "PalmWriterLFeat";
//    #endif	// UTIL_PALMWRITER_ADDTODB

    // tenprint face image
    GAFIS_COL_InitFace(g_stCN.stFace);	// init face data.

    // plain image data
    // tenprint right thumb
    g_stCN.stTPl(0).pszMntName = "PlainFingerRThumbMnt";
    g_stCN.stTPl(0).pszImgName = "PlainFingerRThumbImg";
    g_stCN.stTPl(0).pszCprName = "PlainFingerRThumbCpr";
    g_stCN.stTPl(0).pszBinName = "PlainFingerRThumbBin";
    g_stCN.stTPl(0).pszFeatName = "PlainFingerRThumbFeat";

    // tenprint right four
    g_stCN.stTPl(1).pszMntName = "PlainFingerRFourMnt";
    g_stCN.stTPl(1).pszImgName = "PlainFingerRFourImg";
    g_stCN.stTPl(1).pszCprName = "PlainFingerRFourCpr";
    g_stCN.stTPl(1).pszBinName = "PlainFingerRFourBin";
    g_stCN.stTPl(1).pszFeatName = "PlainFingerRFourFeat";

    // tenprint left thumb
    g_stCN.stTPl(2).pszMntName = "PlainFingerLThumbMnt";
    g_stCN.stTPl(2).pszImgName = "PlainFingerLThumbImg";
    g_stCN.stTPl(2).pszCprName = "PlainFingerLThumbCpr";
    g_stCN.stTPl(2).pszBinName = "PlainFingerLThumbBin";
    g_stCN.stTPl(2).pszFeatName = "PlainFingerLThumbFeat";

    // tenprint left four
    g_stCN.stTPl(3).pszMntName = "PlainFingerLFourMnt";
    g_stCN.stTPl(3).pszImgName = "PlainFingerLFourImg";
    g_stCN.stTPl(3).pszCprName = "PlainFingerLFourCpr";
    g_stCN.stTPl(3).pszBinName = "PlainFingerLFourBin";
    g_stCN.stTPl(3).pszFeatName = "PlainFingerLFourFeat";

    //!< 双拇指模式平面采集指纹
    g_stCN.stTPl(4).pszMntName = "PlainTwoThumbsMnt";
    g_stCN.stTPl(4).pszImgName = "PlainTwoThumbsImg";
    g_stCN.stTPl(4).pszCprName = "PlainTwoThumbsCpr";
    g_stCN.stTPl(4).pszBinName = "PlainTwoThumbsBin";
    g_stCN.stTPl(4).pszFeatName = "PlainTwoThumbsFeat";


    // tenprint six finger
    g_stCN.stTExtra.pszMntName = "ExtraFingerMnt";
    g_stCN.stTExtra.pszImgName = "ExtraFingerImg";
    g_stCN.stTExtra.pszCprName = "ExtraFingerCpr";
    g_stCN.stTExtra.pszBinName = "ExtraFingerBin";
    g_stCN.stTExtra.pszFeatName = "ExtraFingerFeat";

    // tenprint card binary data
    g_stCN.stTCb(0).pszName = "CardBinData1";
    g_stCN.stTCb(1).pszName = "CardBinData2";
    g_stCN.stTCb(2).pszName = "CardBinData3";
    g_stCN.stTCb(3).pszName = "CardBinData4";
    g_stCN.stTCb(4).pszName = "CardBinData5";
    g_stCN.stTCb(5).pszName = "CardBinData6";
    g_stCN.stTCb(6).pszName = "CardBinData7";
    g_stCN.stTCb(7).pszName = "CardBinData8";


    ////////////////////////////////////////////TENPRINT///////-------END----------///////////////////////


    ////////////////////////////////////////////LATENT///////-------START----------///////////////////////
    g_stCN.stLFgID.pszName = "FingerID";
    g_stCN.stLPmID.pszName = "PalmID";
    g_stCN.stLCsID.pszName = "CaseID";
    g_stCN.stLFingerCount.pszName = "FingerCount";
    g_stCN.stLFingerIDList.pszName = "FingerIDList";
    g_stCN.stLPalmCount.pszName = "PalmCount";
    g_stCN.stLPalmIDList.pszName = "PalmIDList";

    // latent finger
    g_stCN.stLFg.pszMntName = "FingerMnt";
    g_stCN.stLFg.pszImgName = "FingerImg";
    g_stCN.stLFg.pszCprName = "FingerCpr";
    g_stCN.stLFg.pszBinName = "FingerBin";
    g_stCN.stLFg.pszFeatName = "FingerFeat";

    // latent palm
    g_stCN.stLPm.pszMntName = "PalmMnt";
    g_stCN.stLPm.pszImgName = "PalmImg";
    g_stCN.stLPm.pszCprName = "PalmCpr";
    g_stCN.stLPm.pszBinName = "PalmBin";
    g_stCN.stLPm.pszFeatName = "PalmFeat";

    // latent voice.
    g_stCN.stLpVoice.pszMntName		= "LPVoiceMnt";
    g_stCN.stLpVoice.pszImgName		= "LPVoiceData";
    g_stCN.stLpVoice.pszFeatName	= "LPVoiceFeat";
    g_stCN.stLpVoice.pszCprName		= "LPVoiceCpr";
    g_stCN.stLpVoice.pszBinName		= "LPVoiceBin";

    // latent face.
    g_stCN.stLpFace.pszMntName		= "LPFaceMnt";
    g_stCN.stLpFace.pszImgName		= "LPFaceData";
    g_stCN.stLpFace.pszFeatName		= "LPFaceFeat";
    g_stCN.stLpFace.pszCprName		= "LPFaceCpr";
    g_stCN.stLpFace.pszBinName		= "LPFaceBin";

    g_stCN.stLVoiceID.pszName	= "VoiceID";
    g_stCN.stLFaceID.pszName	= "FaceID";

    g_stCN.stLGuessedFinger.pszName = "GuessedFinger";
    /* commented by xcg on 2007.04.10
      //g_stCN.stMISCaseID.pszName = "MISCaseID";
    */
    g_stCN.stGroupID.pszName = "GroupID";

    ////////////////////////////////////////////LATENT///////-------END----------///////////////////////
    GAFIS_COLNAME_SetQryName(g_stCN.stQn);


    GAFIS_COLNAME_FIFOQUEName(g_stCN.stFq);
    GAFIS_TBLNAME_TPName(g_stCN.stTt);
    GAFIS_TBLNAME_LPName(g_stCN.stLt);
    GAFIS_TBLNAME_QRYName(g_stCN.stQt);

    GAFIS_MOBILECASECOLNAME_Init(g_stCN.stMc);

    //added by xia xinfeng on 2002.09.12
//    GAFIS_RMTTABLE_Init(g_stCN.stRmtTable);

    GAFIS_TBLNAME_AdminDB(g_stCN.stSysAdm);
    GAFIS_TPCOLNAME_ADMINInit(g_stCN.stTAdm);
    GAFIS_LPCOLNAME_ADMINInit(g_stCN.stLAdm);
    GAFIS_USERCOLNAME_Init(g_stCN.stUser);
    GAFIS_MSGCOLNAME_Init(g_stCN.stMsg);
    GAFIS_PARAMCOLNAME_Init(g_stCN.stParam);
    GAFIS_CODECOLNAME_Init(g_stCN.stCode);
    GAFIS_BCCOLNAME_Init(g_stCN.stBc);
    GAFIS_DTPROPCOLNAME_Init(g_stCN.stDTProp);
    GAFIS_DBMLOGCOLNAME_Init(g_stCN.stDBMLog);
    GAFIS_SYSMLOGCOLNAME_Init(g_stCN.stSYSMLog);
    GAFIS_QRYMODLOGCOLNAME_Init(g_stCN.stQryMLog);
    GAFIS_NUMINACOL_Init(g_stCN.stNuminaCol);
    GAFIS_FIFOQUE_TPInit(g_stCN.stTPQueName);
    GAFIS_FIFOQUE_LPInit(g_stCN.stLPQueName);

    GAFIS_TEXT_InitTPCard(g_stCN.stTCardText);
    GAFIS_TEXT_InitPerson(g_stCN.stTPerText);
    GAFIS_TEXT_InitLPCard(g_stCN.stLCardText);
    GAFIS_TEXT_InitLPCase(g_stCN.stLCaseText);
    GAFIS_CODETABLE_Init(g_stCN.stCodeTable);
    GAFIS_TEXT_InitLPCase_50(g_stCN.stLCaseText_50);
    GAFIS_TEXT_InitTPCard_50(g_stCN.stTCardText_50);
//    GADB_BKP_COLNAME_InitAll(g_stCN.stBkp);
    GAFIS_CASEADMINCOLNAME_Init(g_stCN.stCaseAdm);
    GAFIS_ADMINTABLENAME_Init(g_stCN.stAdmTable);

    GAFIS_USERAUTHLOGCOLNAME_InitName(g_stCN.stUserAuth);
    GAFIS_COL_InitVoice(g_stCN.stVoice);	// initializing voice data.
    COLNAME_DBRUNLOG_Init(g_stCN.stDBRun);
    COLNAME_TPQUALCHK_Init(g_stCN.stQualChk);	// quality check
    COLNAME_TPQUALCHKWORKLOG_Init(g_stCN.stQualChkLog);	// quality check log.
    COLNAME_WORKLOG_Init(g_stCN.stWorkLog);	// work log.
    COLNAME_MNTEDITLOG_Init(g_stCN.stMntEditLog);
    COLNAME_LPVOICETEXT_Init(g_stCN.stLPVoiceText);
    COLNAME_LPFACETEXT_Init(g_stCN.stLPFaceText);
    COLNAME_EXFERRLOG_Init(g_stCN.stExfErrLog);
    COLNAME_QUERYSEARCHLOG_Init(g_stCN.stQrySearchLog);
    COLNAME_QUERYCHECKLOG_Init(g_stCN.stQryCheckLog);
    COLNAME_QUERYSUBMITLOG_Init(g_stCN.stQrySubmitLog);
    COLNAME_QUERYRECHECKLOG_Init(g_stCN.stQryRecheckLog);
    COLNAME_LPGROUP_Init(g_stCN.stLPGroup);
    COLNAME_CASEGROUP_Init(g_stCN.stCaseGroup);
    COLNAME_TPLP_ASSOCIATE_Init(g_stCN.stTPLPAssociate);
    COLNAME_TP_UNMATCH_Init(g_stCN.stTPUnmatch);
    COLNAME_LP_UNMATCH_Init(g_stCN.stLPUnmatch);
    COLNAME_TPLP_UNMATCH_Init(g_stCN.stTPLPUnmatch);
    COLNAME_LP_MULTIMNT_Init(g_stCN.stLPMultiMnt);
    COLNAME_TPLP_FPXQUE_TABLE_Init(g_stCN.stTPLPFpxQue);
    COLNAME_TPLP_FPXLOG_TABLE_Init(g_stCN.stTPLPFpxLog);
    COLNAME_QUERY_FPXQUE_TABLE_Init(g_stCN.stQryFpxQue);
    COLNAME_QUERY_FPXLOG_TABLE_Init(g_stCN.stQryFpxLog);
    COLNAME_LICENSEMGR_Init(g_stCN.stLsnMgr);
    COLNAME_QRYASSIGN_Init(g_stCN.stQryAssign);

    /**
      * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表 
      * added by beagle on Nov. 27, 2008
      */
    COLNAME_LFICDataTransmit_Init(g_stCN.stLFIC_DataTransmit);
    COLNAME_LFICFeedback_Init(g_stCN.stLFIC_Feedback);

    //!< 人像查询队列表列名
    GAFIS_COLNAME_SetFaceQryName(g_stCN.stFaceQryQue);

    //!< 与公安部协查平台的互连、数据交换、日志信息
    COLNAME_GAFPTXCDataTable_Init(g_stCN.stGAFPTXC_Data);
    COLNAME_GAFPTXCLogTable_Init(g_stCN.stGAFPTXC_Log);
    COLNAME_WantedListTable_Init(g_stCN.stWanted_List);
    COLNAME_WantedTpCardTable_Init(g_stCN.stWanted_TpCard);
    COLNAME_WantedOpLogTable_Init(g_stCN.stWanted_OpLog);

    //!<add by zyn at 2014.08.11 for shanghai xk
    COLNAME_SHXK_DataStatusTable_Init(g_stCN.stShxkDataStatus);
    COLNAME_SHXK_MatchInfoTable_Init(g_stCN.stShxkMatchInfo);
    COLNAME_SHXK_CaseStatusTable_Init(g_stCN.stShxkCaseStatus);
    COLNAME_SHXK_CaseTextTable_Init(g_stCN.stShxkCaseText);
    COLNAME_TTRelation_Table_Init(g_stCN.stTTRealtion);
    COLNAME_TTCand_Table_Init(g_stCN.stTTCandData);

    //!<add by zyn at 2014.10.21 for nj
    COLNAME_NJ_DelDataTable_Init(g_stCN.stNjDelData);

    //!<add by wangkui at 2014.10.30
    COLNAME_XCDataTable_Init(g_stCN.stXCData);
    //!<add by wangkui at 2014.12.16
    COLNAME_XCReportData_Table_Init(g_stCN.stXCReportData);
    //!<add by fanjuan 20160219
//    #ifdef USER_CUSTOM_LianNing_HX
//    COLNAME_LNHXREPORT_Table_Init(g_stCN.stLNHXn);
//    #endif


    return g_stCN
  }


}

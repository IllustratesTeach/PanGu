package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{Length, IgnoreTransfer}
import nirvana.hall.c.services.AncientData

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */

object glocname {
class COLNAME_TPLP_FPX_COMMON extends AncientData
{
  var pszFPXStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXStatus_Data:Array[Byte] = _ // for pszFPXStatus pointer ,struct:char;
var pszFPXIsForeign:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXIsForeign_Data:Array[Byte] = _ // for pszFPXIsForeign pointer ,struct:char;
var pszFPXForeignUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXForeignUnitCode_Data:Array[Byte] = _ // for pszFPXForeignUnitCode pointer ,struct:char;
var pszFPXPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXPurpose_Data:Array[Byte] = _ // for pszFPXPurpose pointer ,struct:char;
} // COLNAME_TPLP_FPX_COMMON;

// queries columns names
class COLQRYNAMESTRUCT extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszPriority:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszIsRmtQuery:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsRmtQuery_Data:Array[Byte] = _ // for pszIsRmtQuery pointer ,struct:char;
var pszStage:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStage_Data:Array[Byte] = _ // for pszStage pointer ,struct:char;
var pszFlagEx:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlagEx_Data:Array[Byte] = _ // for pszFlagEx pointer ,struct:char;
var pszDestDB:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDB_Data:Array[Byte] = _ // for pszDestDB pointer ,struct:char;
var pszSourceDB:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSourceDB_Data:Array[Byte] = _ // for pszSourceDB pointer ,struct:char;
var pszSubmitTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszFinishTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;
var pszStartLibID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartLibID_Data:Array[Byte] = _ // for pszStartLibID pointer ,struct:char;
var pszEndLibID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEndLibID_Data:Array[Byte] = _ // for pszEndLibID pointer ,struct:char;
var pszRmtAddTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtAddTime_Data:Array[Byte] = _ // for pszRmtAddTime pointer ,struct:char;
var pszTimeUsed:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;
var pszStartKey1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartKey1_Data:Array[Byte] = _ // for pszStartKey1 pointer ,struct:char;
var pszEndKey1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEndKey1_Data:Array[Byte] = _ // for pszEndKey1 pointer ,struct:char;
var pszStartKey2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartKey2_Data:Array[Byte] = _ // for pszStartKey2 pointer ,struct:char;
var pszEndKey2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEndKey2_Data:Array[Byte] = _ // for pszEndKey2 pointer ,struct:char;
var pszMaxCandNum:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMaxCandNum_Data:Array[Byte] = _ // for pszMaxCandNum pointer ,struct:char;
var pszCurCandNum:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurCandNum_Data:Array[Byte] = _ // for pszCurCandNum pointer ,struct:char;
var pszCandHead:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;
var pszCandList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;
var pszQryCondition:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryCondition_Data:Array[Byte] = _ // for pszQryCondition pointer ,struct:char;
var pszSrcDataMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcDataMnt_Data:Array[Byte] = _ // for pszSrcDataMnt pointer ,struct:char;
var pszSrcDataImage:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcDataImage_Data:Array[Byte] = _ // for pszSrcDataImage pointer ,struct:char;
var pszQueryID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryID_Data:Array[Byte] = _ // for pszQueryID pointer ,struct:char;
var pszCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszCheckTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtState_Data:Array[Byte] = _ // for pszRmtState pointer ,struct:char;
var pszMISState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISState_Data:Array[Byte] = _ // for pszMISState pointer ,struct:char;
var pszDestDBCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDBCount_Data:Array[Byte] = _ // for pszDestDBCount pointer ,struct:char;
var pszRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
var pszMIC:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMIC_Data:Array[Byte] = _ // for pszMIC pointer ,struct:char;
var pszMISCond:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISCond_Data:Array[Byte] = _ // for pszMISCond pointer ,struct:char;
var pszServerList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszServerList_Data:Array[Byte] = _ // for pszServerList pointer ,struct:char;
var pszTextSql:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTextSql_Data:Array[Byte] = _ // for pszTextSql pointer ,struct:char;
var pszGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszReCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;
var pszVerifyPri:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyPri_Data:Array[Byte] = _ // for pszVerifyPri pointer ,struct:char;	//
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszFlag3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag3_Data:Array[Byte] = _ // for pszFlag3 pointer ,struct:char;
var pszFlag4:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag4_Data:Array[Byte] = _ // for pszFlag4 pointer ,struct:char;
var pszFlag5:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag5_Data:Array[Byte] = _ // for pszFlag5 pointer ,struct:char;
var pszFlag6:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag6_Data:Array[Byte] = _ // for pszFlag6 pointer ,struct:char;
var pszFlag7:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag7_Data:Array[Byte] = _ // for pszFlag7 pointer ,struct:char;
var pszFlag8:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag8_Data:Array[Byte] = _ // for pszFlag8 pointer ,struct:char;
var pszUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserUnitCode_Data:Array[Byte] = _ // for pszUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszMinScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMinScore_Data:Array[Byte] = _ // for pszMinScore pointer ,struct:char;
var pszSchCandCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSchCandCnt_Data:Array[Byte] = _ // for pszSchCandCnt pointer ,struct:char;
var pszComputerIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this ip.
var pszIsFofoQueQry:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsFofoQueQry_Data:Array[Byte] = _ // for pszIsFofoQueQry pointer ,struct:char;
var pszFifoQueSID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueSID_Data:Array[Byte] = _ // for pszFifoQueSID pointer ,struct:char;
var pszFifoQueDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueDBID_Data:Array[Byte] = _ // for pszFifoQueDBID pointer ,struct:char;
var pszFifoQueTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueTID_Data:Array[Byte] = _ // for pszFifoQueTID pointer ,struct:char;
//	char	*pszReserved;	// reserved.
// following columns are added on July 31, 2006
var pszQryUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryUID_Data:Array[Byte] = _ // for pszQryUID pointer ,struct:char;
var pszFPXStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXStatus_Data:Array[Byte] = _ // for pszFPXStatus pointer ,struct:char;
var pszFPXVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXVerifyResult_Data:Array[Byte] = _ // for pszFPXVerifyResult pointer ,struct:char;
var pszFPXVerifyUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXVerifyUnitCode_Data:Array[Byte] = _ // for pszFPXVerifyUnitCode pointer ,struct:char;
var pszFPXForeignTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXForeignTaskID_Data:Array[Byte] = _ // for pszFPXForeignTaskID pointer ,struct:char;
// above columns are added on July 31, 2006
var pszRecSearched:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecSearched_Data:Array[Byte] = _ // for pszRecSearched pointer ,struct:char;
} // COLQRYNAMESTRUCT;

class COLFIFOQUESTRUCT extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSourceDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSourceDBID_Data:Array[Byte] = _ // for pszSourceDBID pointer ,struct:char;	// source dbid.
var pszSourceTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSourceTID_Data:Array[Byte] = _ // for pszSourceTID pointer ,struct:char;	// source data file table id
var pszQueueType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueueType_Data:Array[Byte] = _ // for pszQueueType pointer ,struct:char;	// queue type
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;		// status
var pszCandHead:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;	// candidate head
var pszCandList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;	// candidate list
var pszItemFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszItemFlag_Data:Array[Byte] = _ // for pszItemFlag pointer ,struct:char;	// item flag
var pszTenString:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTenString_Data:Array[Byte] = _ // for pszTenString pointer ,struct:char;	// tenstring, only process those finger value is 1, not char '1'
var pszUseCPR:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUseCPR_Data:Array[Byte] = _ // for pszUseCPR pointer ,struct:char;		// if this value is true, then, we'll decompress image first then extract
// feature, then we'll not compress the image again
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;		// QueOption
var pszQueOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueOption_Data:Array[Byte] = _ // for pszQueOption pointer ,struct:char;	// QueOptionEx
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;	// query type.
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszFlagEx:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlagEx_Data:Array[Byte] = _ // for pszFlagEx pointer ,struct:char;
var pszQrySID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQrySID_Data:Array[Byte] = _ // for pszQrySID pointer ,struct:char;		// TQry sid.
var pszIsQrySubmitted:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsQrySubmitted_Data:Array[Byte] = _ // for pszIsQrySubmitted pointer ,struct:char;
var pszDestDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;
var pszDestTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestTID_Data:Array[Byte] = _ // for pszDestTID pointer ,struct:char;
var pszQryDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryDBID_Data:Array[Byte] = _ // for pszQryDBID pointer ,struct:char;
var pszQryTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryTID_Data:Array[Byte] = _ // for pszQryTID pointer ,struct:char;
var pszLQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLQueryType_Data:Array[Byte] = _ // for pszLQueryType pointer ,struct:char;
var pszLQrySID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLQrySID_Data:Array[Byte] = _ // for pszLQrySID pointer ,struct:char;
var pszDestLatDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestLatDBID_Data:Array[Byte] = _ // for pszDestLatDBID pointer ,struct:char;
var pszDestLatTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestLatTID_Data:Array[Byte] = _ // for pszDestLatTID pointer ,struct:char;

  var pszErrorCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszErrorCount_Data:Array[Byte] = _ // for pszErrorCount pointer ,struct:char;
} // COLFIFOQUESTRUCT;

/**
  * 人像查询队列表结构
  */
class COLFACEQRYNAMESTRUCT extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;			//!< 被查条码
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;		//!< 查询类型
var pszPriority:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;		//!< 优先级
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;		//!< 查中概率
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;			//!< 查询状态
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			//!< 查询标志

  var pszSourceDB:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSourceDB_Data:Array[Byte] = _ // for pszSourceDB pointer ,struct:char;		//!< 源数据库
var pszDestDB:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDB_Data:Array[Byte] = _ // for pszDestDB pointer ,struct:char;			//!< 目的数据库
var pszDestDBCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDBCount_Data:Array[Byte] = _ // for pszDestDBCount pointer ,struct:char;	//!< 目的数据库个数

  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;		//!< 查询提交者
var pszSubmitTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;		//!< 提交时间
var pszFinishTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;		//!< 完成时间
var pszTimeUsed:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;		//!< 用时
var pszCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;	//!< 认定用户
var pszCheckTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;		//!< 认定时间
var pszReCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;	//!< 复核用户
var pszReCheckDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;		//!< 复核时间
var pszUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserUnitCode_Data:Array[Byte] = _ // for pszUserUnitCode pointer ,struct:char;		//!< 提交用户单位代码
var pszCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;	//!< 认定用户单位代码
var pszReCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;	//!< 复核用户单位代码

  var pszMaxCandNum:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMaxCandNum_Data:Array[Byte] = _ // for pszMaxCandNum pointer ,struct:char;		//!< 最大候选个数
var pszCurCandNum:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurCandNum_Data:Array[Byte] = _ // for pszCurCandNum pointer ,struct:char;		//!< 当前候选个数
var pszCandHead:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;		//!< 候选头结构
var pszCandList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;		//!< 候选列表
var pszMIC:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMIC_Data:Array[Byte] = _ // for pszMIC pointer ,struct:char;			//!< 被查人像数据

  var pszQueryID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryID_Data:Array[Byte] = _ // for pszQueryID pointer ,struct:char;		//!< 查询ID
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;	//!< 核查结果
var pszMinScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMinScore_Data:Array[Byte] = _ // for pszMinScore pointer ,struct:char;		//!< 可以进入候选的最低得分

  var pszComputerIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this computer.

  var pszStartLibID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartLibID_Data:Array[Byte] = _ // for pszStartLibID pointer ,struct:char;
var pszEndLibID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEndLibID_Data:Array[Byte] = _ // for pszEndLibID pointer ,struct:char;
var pszStartKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartKey_Data:Array[Byte] = _ // for pszStartKey pointer ,struct:char;
var pszEndKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEndKey_Data:Array[Byte] = _ // for pszEndKey pointer ,struct:char;
} // COLFACEQRYNAMESTRUCT;


class COLMICBNAMESTRUCT extends AncientData
{
  var pszMntName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMntName_Data:Array[Byte] = _ // for pszMntName pointer ,struct:char;	// mnt name
var pszImgName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszImgName_Data:Array[Byte] = _ // for pszImgName pointer ,struct:char;	// image
var pszCprName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCprName_Data:Array[Byte] = _ // for pszCprName pointer ,struct:char;	// compressed
var pszBinName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBinName_Data:Array[Byte] = _ // for pszBinName pointer ,struct:char;	// binary
var pszFeatName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFeatName_Data:Array[Byte] = _ // for pszFeatName pointer ,struct:char;	// feature
} // COLMICBNAMESTRUCT;	// sizeof this structure is 20 or 40 bytes(depends on platform)

class COLGENNAMESTRUCT extends AncientData
{
  var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
} // COLGENNAMESTRUCT;

class TPDEFFIFOQUENAMESTRUCT extends AncientData
{
  var pszExfQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExfQue_Data:Array[Byte] = _ // for pszExfQue pointer ,struct:char;
var pszEditQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditQue_Data:Array[Byte] = _ // for pszEditQue pointer ,struct:char;
var pszTTSearchQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTSearchQue_Data:Array[Byte] = _ // for pszTTSearchQue pointer ,struct:char;
var pszTTCheckQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTCheckQue_Data:Array[Byte] = _ // for pszTTCheckQue pointer ,struct:char;
var pszTextInputQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTextInputQue_Data:Array[Byte] = _ // for pszTextInputQue pointer ,struct:char;
var pszTLSearchQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTLSearchQue_Data:Array[Byte] = _ // for pszTLSearchQue pointer ,struct:char;
var pszTLCheckQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTLCheckQue_Data:Array[Byte] = _ // for pszTLCheckQue pointer ,struct:char;
var pszEditQue2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditQue2_Data:Array[Byte] = _ // for pszEditQue2 pointer ,struct:char;
} // TPDEFFIFOQUENAMESTRUCT;

class LPDEFFIFOQUENAMESTRUCT extends AncientData
{
  var pszEditQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditQue_Data:Array[Byte] = _ // for pszEditQue pointer ,struct:char;
var pszLTSearchQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLTSearchQue_Data:Array[Byte] = _ // for pszLTSearchQue pointer ,struct:char;
var pszLTCheckQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLTCheckQue_Data:Array[Byte] = _ // for pszLTCheckQue pointer ,struct:char;
var pszExfQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExfQue_Data:Array[Byte] = _ // for pszExfQue pointer ,struct:char;
} // LPDEFFIFOQUENAMESTRUCT;


// tplib table name's every table has at least one child table
// dbid tableid and childtableid are all not in the same name space
// all child table ID is larger than 1000
// all table id is less than 1000
class TPLIBTABLENAMESTRUCT extends AncientData
{
  var pszTPCardTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPCardTable_Data:Array[Byte] = _ // for pszTPCardTable pointer ,struct:char;	// table
var pszFingerMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerMnt_Data:Array[Byte] = _ // for pszFingerMnt pointer ,struct:char;		// child table
var pszFingerFeat:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerFeat_Data:Array[Byte] = _ // for pszFingerFeat pointer ,struct:char;		// child table
var pszImgData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszImgData_Data:Array[Byte] = _ // for pszImgData pointer ,struct:char;		// child table
var pszCardText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardText_Data:Array[Byte] = _ // for pszCardText pointer ,struct:char;		// child table
var pszTPCardAdmin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPCardAdmin_Data:Array[Byte] = _ // for pszTPCardAdmin pointer ,struct:char;	// child table(admin table)
var pszPersonTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonTable_Data:Array[Byte] = _ // for pszPersonTable pointer ,struct:char;	// table
var pszPersonCard:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonCard_Data:Array[Byte] = _ // for pszPersonCard pointer ,struct:char;		// child table
var pszPersonText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonText_Data:Array[Byte] = _ // for pszPersonText pointer ,struct:char;		// child table
var pszLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// table(modification log)
var pszLogData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// (table)parameter/administration
var pszParamData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  var pszPalmMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmMnt_Data:Array[Byte] = _ // for pszPalmMnt pointer ,struct:char;		// table
var pszPalmMntAdmin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmMntAdmin_Data:Array[Byte] = _ // for pszPalmMntAdmin pointer ,struct:char;	// child table
var pszLeftPalmMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLeftPalmMnt_Data:Array[Byte] = _ // for pszLeftPalmMnt pointer ,struct:char;	// child table
var pszRightPalmMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRightPalmMnt_Data:Array[Byte] = _ // for pszRightPalmMnt pointer ,struct:char;	// child table
var pszPalmFeat:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmFeat_Data:Array[Byte] = _ // for pszPalmFeat pointer ,struct:char;		// child table.

  var pszPlainFinger:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPlainFinger_Data:Array[Byte] = _ // for pszPlainFinger pointer ,struct:char;	// child table.
var pszFaceMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceMnt_Data:Array[Byte] = _ // for pszFaceMnt pointer ,struct:char;		// child table.
var pszVoiceMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVoiceMnt_Data:Array[Byte] = _ // for pszVoiceMnt pointer ,struct:char;		// child table.
// table store that two records are not same.
var pszTPUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPUnmatch_Data:Array[Byte] = _ // for pszTPUnmatch pointer ,struct:char;		// table name.
var pszCTTPUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTTPUnmatch_Data:Array[Byte] = _ // for pszCTTPUnmatch pointer ,struct:char;	// child table name.

  var nTIDTPCardTable:Short = _;	// 2
var nTIDFingerMnt:Short = _;		// 1001
var nTIDFingerFeat:Short = _;		// 1002
var nTIDImgData:Short = _;		// 1005
var nTIDCardText:Short = _;		// 1006
var nTIDTPCardAdmin:Short = _;	// 1007
var nTIDPersonTable:Short = _;	// 3
var nTIDPersonCard:Short = _;		// 1001
var nTIDPersonText:Short = _;		// 1002
var nTIDLogTable:Short = _;		// 4
var nTIDLogData:Short = _;		// 1001
var nTIDParamTable:Short = _;		// 1
var nTIDParamData:Short = _;		// 1001

  var nTIDPalmMnt:Short = _;		// 5
var nTIDPalmMntAdmin:Short = _;	// 1011
var nTIDLeftPalmMnt:Short = _;	// 1012
var nTIDRightPalmMnt:Short = _;	// 1013
var nTIDPalmFeat:Short = _;		// 1014

  var nTIDPlainFinger:Short = _;	// 1008
var nTIDFaceMnt:Short = _;		// 1020
var nTIDVoiceMnt:Short = _;		// 1021

  // table store that two records are not same.
  var nTIDTPUnmatch:Short = _;		// 6
var nCTIDTPUnmatch:Short = _;		// 1001

} // TPLIBTABLENAMESTRUCT;

class LPLIBTABLENAMESTRUCT extends AncientData
{
  var pszLatFinger:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatFinger_Data:Array[Byte] = _ // for pszLatFinger pointer ,struct:char;		// table
var pszLatFingerMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerMnt_Data:Array[Byte] = _ // for pszLatFingerMnt pointer ,struct:char;	// child table
var pszLatFingerText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerText_Data:Array[Byte] = _ // for pszLatFingerText pointer ,struct:char;	// child table
var pszLatFingerAdmin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerAdmin_Data:Array[Byte] = _ // for pszLatFingerAdmin pointer ,struct:char;	// child table
var pszLatPalm:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatPalm_Data:Array[Byte] = _ // for pszLatPalm pointer ,struct:char;		// table
var pszLatPalmMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmMnt_Data:Array[Byte] = _ // for pszLatPalmMnt pointer ,struct:char;		// child table
var pszLatPalmText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmText_Data:Array[Byte] = _ // for pszLatPalmText pointer ,struct:char;	// child table
var pszLatPalmAdmin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmAdmin_Data:Array[Byte] = _ // for pszLatPalmAdmin pointer ,struct:char;	// child table
var pszCase:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCase_Data:Array[Byte] = _ // for pszCase pointer ,struct:char;			// table
var pszCaseInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseInfo_Data:Array[Byte] = _ // for pszCaseInfo pointer ,struct:char;		// child table
var pszCaseText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseText_Data:Array[Byte] = _ // for pszCaseText pointer ,struct:char;		// child table
var pszLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// modification log table
var pszLogData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration table
var pszParamData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  // new added.
  var pszFace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFace_Data:Array[Byte] = _ // for pszFace pointer ,struct:char;			// table face.
var pszCTFace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTFace_Data:Array[Byte] = _ // for pszCTFace pointer ,struct:char;			// face child table.
var pszCTFaceText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTFaceText_Data:Array[Byte] = _ // for pszCTFaceText pointer ,struct:char;		// face text child table.
var pszCTFaceMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTFaceMnt_Data:Array[Byte] = _ // for pszCTFaceMnt pointer ,struct:char;		// face mnt child table.
var pszVoice:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVoice_Data:Array[Byte] = _ // for pszVoice pointer ,struct:char;			// voice table.
var pszCTVoice:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTVoice_Data:Array[Byte] = _ // for pszCTVoice pointer ,struct:char;		// voice child table.
var pszCTVoiceText:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTVoiceText_Data:Array[Byte] = _ // for pszCTVoiceText pointer ,struct:char;	// voice text table
var pszCTVoiceMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTVoiceMnt_Data:Array[Byte] = _ // for pszCTVoiceMnt pointer ,struct:char;

  // following are added on Aug. 9, 2006
  var pszLPGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroup_Data:Array[Byte] = _ // for pszLPGroup pointer ,struct:char;		// lat finger, palm, face, voice, etc group.
var pszCTLPGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTLPGroup_Data:Array[Byte] = _ // for pszCTLPGroup pointer ,struct:char;		// LPGroup child table.

  var pszCaseGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroup_Data:Array[Byte] = _ // for pszCaseGroup pointer ,struct:char;		// case group.
var pszCTCaseGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTCaseGroup_Data:Array[Byte] = _ // for pszCTCaseGroup pointer ,struct:char;	// child table for CaseGroup;

  var pszLPFingerUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPFingerUnmatch_Data:Array[Byte] = _ // for pszLPFingerUnmatch pointer ,struct:char;	// two lp finger are not same.
var pszCTLPFingerUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTLPFingerUnmatch_Data:Array[Byte] = _ // for pszCTLPFingerUnmatch pointer ,struct:char;	// child table.

  var pszLPPalmUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPPalmUnmatch_Data:Array[Byte] = _ // for pszLPPalmUnmatch pointer ,struct:char;
var pszCTLPPalmUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTLPPalmUnmatch_Data:Array[Byte] = _ // for pszCTLPPalmUnmatch pointer ,struct:char;
  // above added on Aug. 9, 2006

  var nTIDLatFinger:Short = _;		// 2
var nTIDLatFingerMnt:Short = _;	// 1001
var nTIDLatFingerText:Short = _;	// 1002
var nTIDLatFingerAdmin:Short = _;	// 1003
var nTIDLatPalm:Short = _;		// 3
var nTIDLatPalmMnt:Short = _;		// 1001
var nTIDLatPalmText:Short = _;	// 1002
var nTIDLatPalmAdmin:Short = _;	// 1003
var nTIDCase:Short = _;			// table, 4
var nTIDCaseInfo:Short = _;		// child table, 1001
var nTIDCaseText:Short = _;		// child table, 1002

  var nTIDLogTable:Short = _;		// 5
var nTIDLogData:Short = _;		// 1001
var nTIDParamTable:Short = _;		// 1
var nTIDParamData:Short = _;		// 1001

  var nTIDFace:Short = _;			// 7
var nCTIDFace:Short = _;			// 1001
var nCTIDFaceText:Short = _;		// 1002
var nCTIDFaceMnt:Short = _;		// 1003

  var nTIDVoice:Short = _;			// 8
var nCTIDVoice:Short = _;			// 1001
var nCTIDVoiceText:Short = _;		// 1002
var nCTIDVoiceMnt:Short = _;		// 1003

  var nTIDLPGroup:Short = _;		// 9
var nCTIDLPGroup:Short = _;		// 1001

  var nTIDCaseGroup:Short = _;		// 10
var nCTIDCaseGroup:Short = _;		// 1001

  var nTIDLPFingerUnmatch:Short = _;	// 11
var nCTIDLPFingerUnmatch:Short = _;	// 1001

  var nTIDLPPalmUnmatch:Short = _;		// 12
var nCTIDLPPalmUnmatch:Short = _;		// 1001
} // LPLIBTABLENAMESTRUCT;

class QUERYTABLENAMESTRUCT extends AncientData
{
  var pszQueryTableName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryTableName_Data:Array[Byte] = _ // for pszQueryTableName pointer ,struct:char;	// table
var pszQueryQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryQue_Data:Array[Byte] = _ // for pszQueryQue pointer ,struct:char;		// child table
var pszLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// modification log table
var pszLogData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration table
var pszParamData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  /**
    * 人像查询队列表
    */
  var pszFaceQueryTableName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceQueryTableName_Data:Array[Byte] = _ // for pszFaceQueryTableName pointer ,struct:char;	//!< table
var pszFaceQueryQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceQueryQue_Data:Array[Byte] = _ // for pszFaceQueryQue pointer ,struct:char;		//!< child table

  var nTIDQueryTableName:Short = _;	// table, 2
var nTIDQueryQue:Short = _;		// child table, 1001
var nTIDLogTable:Short = _;		// modification log table, 3
var nTIDLogData:Short = _;		// child table, 1001
var nTIDParamTable:Short = _;		// parameter/administration table, 1
var nTIDParamData:Short = _;		// child table, 1001

  var nTIDFaceQueryTableName:Short = _;	//!< table, 4
var nTIDFaceQueryQue:Short = _;		//!< chile table, 1001

} // QUERYTABLENAMESTRUCT;

class NUMINAINNERCOLNAME extends AncientData
{
  var pszUUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUUID_Data:Array[Byte] = _ // for pszUUID pointer ,struct:char;
var pszSID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSID_Data:Array[Byte] = _ // for pszSID pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszPrevSID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrevSID_Data:Array[Byte] = _ // for pszPrevSID pointer ,struct:char;
var pszNextSID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNextSID_Data:Array[Byte] = _ // for pszNextSID pointer ,struct:char;
var pszQueFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueFlag_Data:Array[Byte] = _ // for pszQueFlag pointer ,struct:char;
var pszCreator:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreator_Data:Array[Byte] = _ // for pszCreator pointer ,struct:char;
var pszUpdator:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdator_Data:Array[Byte] = _ // for pszUpdator pointer ,struct:char;
} // NUMINAINNERCOLNAME;

class ADMINTABLENAMESTRUCT extends AncientData
{
  var pszUserTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserTable_Data:Array[Byte] = _ // for pszUserTable pointer ,struct:char;		// table
var pszCTUserTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTUserTable_Data:Array[Byte] = _ // for pszCTUserTable pointer ,struct:char;	// child table
var pszSysMsgTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSysMsgTable_Data:Array[Byte] = _ // for pszSysMsgTable pointer ,struct:char;	// table
var pszCTSysMsgTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTSysMsgTable_Data:Array[Byte] = _ // for pszCTSysMsgTable pointer ,struct:char;	// child table
//char	*pszCodeTable;		// table, there are many code tables
//char	*pszCTCodeTable;	// child table
var pszBreakCaseTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBreakCaseTable_Data:Array[Byte] = _ // for pszBreakCaseTable pointer ,struct:char;	// table
var pszCTBreakCaseTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTBreakCaseTable_Data:Array[Byte] = _ // for pszCTBreakCaseTable pointer ,struct:char;	// child table
var pszSYSLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSYSLogTable_Data:Array[Byte] = _ // for pszSYSLogTable pointer ,struct:char;		// modification log table
var pszSYSLogData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSYSLogData_Data:Array[Byte] = _ // for pszSYSLogData pointer ,struct:char;		// child table
var pszDBLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBLogTable_Data:Array[Byte] = _ // for pszDBLogTable pointer ,struct:char;		// modification log table
var pszDBLogData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBLogData_Data:Array[Byte] = _ // for pszDBLogData pointer ,struct:char;		// child table
var pszParamTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration talbe
var pszParamData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table
var pszMobileCaseTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMobileCaseTable_Data:Array[Byte] = _ // for pszMobileCaseTable pointer ,struct:char;		// table
var pszMobileCaseData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMobileCaseData_Data:Array[Byte] = _ // for pszMobileCaseData pointer ,struct:char;			// child table
var pszDBPropTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBPropTable_Data:Array[Byte] = _ // for pszDBPropTable pointer ,struct:char;
var pszDBPropData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBPropData_Data:Array[Byte] = _ // for pszDBPropData pointer ,struct:char;

  ///**
  // * 与公安部协查平台互连的相关表名及其表ID，Added on Nov. 17, 2011
  // */
  //char	*pszGAFPTXCDataTable, pszCTGAFPTXCDataTable;		//!< 处理与公安部协查平台相关的协查任务
  //char	*pszGAFPTXCLogTable, pszGAFPTXCLogData;				//!< 与公安部协查平台交互的日志信息
  //char	*pszWantedListTable, pszWantedListData;				//!< 布控（追逃）人员列表
  //char	*pszWantedTpCardTable, pszWantedTpCardData;			//!< 所有与缉控人员有重卡关系的捺印卡片ID。每个捺印人员只能对应一个辑控记录
  //char	*pszWantedOpLogTable, pszWantedOpLogData;			//!< 追逃的操作记录

  var nTIDUserTable:Short = _;		// table, 2
var nTIDCTUserTable:Short = _;	// child table, 1001
var nTIDSysMsgTable:Short = _;	// table, 3
var nTIDCTSysMsgTable:Short = _;	// child table, 1001
var nTIDBreakCaseTable:Short = _;	// table, 4
var nTIDCTBreakCaseTable:Short = _;	// child table, 1001
var nTIDSYSLogTable:Short = _;		// modification log table, 5
var nTIDSYSLogData:Short = _;		// child table, 1001
var nTIDDBLogTable:Short = _;		// modification log table, 6
var nTIDDBLogData:Short = _;		// child table, 1001
var nTIDParamTable:Short = _;		// parameter/administration talbe, 8
var nTIDParamData:Short = _;		// child table, 1001
var nTIDMobileCaseTable:Short = _;	// table, 7
var nTIDMobileCaseData:Short = _;		// child table, 1001
var nTIDDBPropTable:Short = _;		// table, 1
var nTIDDBPropData:Short = _;			// child table, 1001

  //uint2	nTIDGAFPTXCDataTable, nTIDCTGAFPTXCDataTable;	//!< table ,		child table 1001
  //uint2	nTIDGAFPTXCLogTable, nTIDGAFPTXCLogData;		//!< table ,		child table 1001
  //uint2	nTIDWantedListTable, nTIDWantedListData;		//!< table ,		child table 1001
  //uint2	nTIDWantedTpCardTable, nTIDWantedTpCardData;	//!< table ,		child table 1001
  //uint2	nTIDWantedOpLogTable, nTIDWantedOpLogData;		//!< table ,		child table 1001

} // ADMINTABLENAMESTRUCT;

class GAFIS_CODETABLEPROP extends AncientData
{
  var pszTableName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTableName_Data:Array[Byte] = _ // for pszTableName pointer ,struct:char;	// table name
var pszCTableName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCTableName_Data:Array[Byte] = _ // for pszCTableName pointer ,struct:char;	// child table name.
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;	// table comment.
var nCodeLen:Int = _;
  var nInputCodeLen:Int = _;
  var nNameLen:Int = _;
  var nTableID:Short = _;
} // GAFIS_CODETABLEPROP;

//#define UTIL_20110309_NEW_COLUMN


class TABLENAME_CODETABLE extends AncientData
{
  var stSex = new GAFIS_CODETABLEPROP;			// sex table.
// for address only has large address, but for detailed is not coded, so
// need some other part to store it.
var stAddress = new GAFIS_CODETABLEPROP;		// address table
var stUnit = new GAFIS_CODETABLEPROP;			// unit table
var stRace = new GAFIS_CODETABLEPROP;			// race
var stFinger = new GAFIS_CODETABLEPROP;		// finger code.
var stSupervise = new GAFIS_CODETABLEPROP;	// supervise
var stRidgeColor = new GAFIS_CODETABLEPROP;	// ridge color
var stCaseClass = new GAFIS_CODETABLEPROP;	// case class
var stOperator = new GAFIS_CODETABLEPROP;		// operator.
var stPersonClass = new GAFIS_CODETABLEPROP;	// person class.
var stGroupCode = new GAFIS_CODETABLEPROP;	// group code.
var stCertificateCode = new GAFIS_CODETABLEPROP;	// type of certificate.
var stMurderCode = new GAFIS_CODETABLEPROP;		// type or murder
var stLatFingerDevelopMethod = new GAFIS_CODETABLEPROP;	// latent finger development method.指纹提取方法

  // add on Feb. 19, 2008 by beagle
  var stNationality = new GAFIS_CODETABLEPROP;		// 世界各国和地区名称代码表
var stCaseState = new GAFIS_CODETABLEPROP;		// 案件状态代码表, GA XX5.11-2007

  //#ifdef	UTIL_20110309_NEW_COLUMN
  //!< add on March. 9, 2011 by beagle
  var stBloodType = new GAFIS_CODETABLEPROP;		//!< 血型代码表
var stEducationLevel = new GAFIS_CODETABLEPROP;	//!< 文化程度
var stMaritalStatus = new GAFIS_CODETABLEPROP;	//!< 婚姻状况
var stAccent = new GAFIS_CODETABLEPROP;			//!< 口音
var stColorCode = new GAFIS_CODETABLEPROP;		//!< 头发、眼睛等的颜色
var stFigureType = new GAFIS_CODETABLEPROP;		//!< 体型
var stFaceType = new GAFIS_CODETABLEPROP;			//!< 脸型
var stMntExtractMethod = new GAFIS_CODETABLEPROP;	//!< 特征提取方式
  //#endif

} // TABLENAME_CODETABLE;


class TPADMINCOLNAME extends AncientData
{
  var pszPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonID_Data:Array[Byte] = _ // for pszPersonID pointer ,struct:char;	// for duplicate card use only
var pszMISPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszScanCardConfigID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszScanCardConfigID_Data:Array[Byte] = _ // for pszScanCardConfigID pointer ,struct:char;
var pszDispCardConfigID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDispCardConfigID_Data:Array[Byte] = _ // for pszDispCardConfigID pointer ,struct:char;
var pszGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszAccuTLCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAccuTLCount_Data:Array[Byte] = _ // for pszAccuTLCount pointer ,struct:char;
var pszAccuTTCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAccuTTCount_Data:Array[Byte] = _ // for pszAccuTTCount pointer ,struct:char;
var pszTLCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTLCount_Data:Array[Byte] = _ // for pszTLCount pointer ,struct:char;
var pszTTCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTCount_Data:Array[Byte] = _ // for pszTTCount pointer ,struct:char;
var pszEditCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditCount_Data:Array[Byte] = _ // for pszEditCount pointer ,struct:char;	// # of times edited
var pszPersonType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonType_Data:Array[Byte] = _ // for pszPersonType pointer ,struct:char;
var pszTLDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTLDate_Data:Array[Byte] = _ // for pszTLDate pointer ,struct:char;
var pszTTDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTDate_Data:Array[Byte] = _ // for pszTTDate pointer ,struct:char;
var pszTLUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTLUser_Data:Array[Byte] = _ // for pszTLUser pointer ,struct:char;
var pszTTUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTUser_Data:Array[Byte] = _ // for pszTTUser pointer ,struct:char;
var pszGroupName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupName_Data:Array[Byte] = _ // for pszGroupName pointer ,struct:char;
var pszGroupCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;	//
var pszPersonState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonState_Data:Array[Byte] = _ // for pszPersonState pointer ,struct:char;	// TPPERSONSTATE_XXX, unknown, free, detain, escaped, dead,
//	char	*pszTPAdminReserved;
// following columns are added on July. 31, 2006
var pszOrgScanner:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszRollDigitizeMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRollDigitizeMethod_Data:Array[Byte] = _ // for pszRollDigitizeMethod pointer ,struct:char;
var pszTPlainDigitizeMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainDigitizeMethod_Data:Array[Byte] = _ // for pszTPlainDigitizeMethod pointer ,struct:char;
var pszPalmDigitizeMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmDigitizeMethod_Data:Array[Byte] = _ // for pszPalmDigitizeMethod pointer ,struct:char;
  // above columns are added on July. 31, 2006

  //!< 数字化时间 added on Mar.18, 2009
  var pszDigitizedTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDigitizedTime_Data:Array[Byte] = _ // for pszDigitizedTime pointer ,struct:char;
} // TPADMINCOLNAME;

class TPCARDDEFTEXT_50 extends AncientData
{
  var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// 40
var pszNamePinYin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNamePinYin_Data:Array[Byte] = _ // for pszNamePinYin pointer ,struct:char;	// 40 name pinyin
var pszAlias:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;	// 40
var pszSex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSex_Data:Array[Byte] = _ // for pszSex pointer ,struct:char;	// 10
var pszSexCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;	// 1
var pszBirthDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;	// birthdate; 9, format 20021101
var pszShenFenID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			// 19
var pszHuKouPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlace_Data:Array[Byte] = _ // for pszHuKouPlace pointer ,struct:char;			// 70
var pszHuKouPlaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;		// 6
var pszAddressCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;		// 6
var pszAddress:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;			// 70
var pszPersonClass:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonClass_Data:Array[Byte] = _ // for pszPersonClass pointer ,struct:char;		// 2
var pszCaseClass1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1_Data:Array[Byte] = _ // for pszCaseClass1 pointer ,struct:char;			// 70	// GA 240. 1-2000
var pszCaseClass2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2_Data:Array[Byte] = _ // for pszCaseClass2 pointer ,struct:char;			// 70
var pszCaseClass3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3_Data:Array[Byte] = _ // for pszCaseClass3 pointer ,struct:char;			// 70

  var pszCaseClass1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;			// 8	// GA 240. 1-2000
var pszCaseClass2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;			// 8
var pszCaseClass3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;			// 8


  // for administrative
  var pszPrinterUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitCode_Data:Array[Byte] = _ // for pszPrinterUnitCode pointer ,struct:char;	// 10
var pszPrinterUnitName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitName_Data:Array[Byte] = _ // for pszPrinterUnitName pointer ,struct:char;	// 70
var pszPrinterName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterName_Data:Array[Byte] = _ // for pszPrinterName pointer ,struct:char;		// 40
var pszPrintDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrintDate_Data:Array[Byte] = _ // for pszPrintDate pointer ,struct:char;			// 9	// format 20021101
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// 512
} // TPCARDDEFTEXT_50;

class TPCARDDEFTEXT extends AncientData
{
  var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// 40
var pszNamePinYin:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNamePinYin_Data:Array[Byte] = _ // for pszNamePinYin pointer ,struct:char;	// 40 name pinyin
var pszAlias:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;	// 40
var pszSexCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;	// 2
var pszBirthDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;	// birthdate; 9, format 20021101
var pszShenFenID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			// 19
var pszHuKouPlaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;		// 6
var pszAddressCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;		// 6
var pszPersonClassCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonClassCode_Data:Array[Byte] = _ // for pszPersonClassCode pointer ,struct:char;	// 2
var pszPersonClassTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonClassTail_Data:Array[Byte] = _ // for pszPersonClassTail pointer ,struct:char;	// 24

  var pszCaseClass1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;			// 8	// GA 240. 1-2000
var pszCaseClass2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;			// 8
var pszCaseClass3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;			// 8

  // for administrative
  var pszPrinterUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitCode_Data:Array[Byte] = _ // for pszPrinterUnitCode pointer ,struct:char;	// 10
var pszPrinterName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterName_Data:Array[Byte] = _ // for pszPrinterName pointer ,struct:char;		// 40
var pszPrintDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrintDate_Data:Array[Byte] = _ // for pszPrintDate pointer ,struct:char;			// 9	// format 20021101
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// 512
var pszHitHistory:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  // for hukou place and some other place, it may contain more specific info
  // such Room602, West 4 ring road of Zhongguancun, Haidian district, Beijing.
  // so place only to haidian district, Beijing, so we need some place to
  // store other info.
  var pszAddressTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddressTail_Data:Array[Byte] = _ // for pszAddressTail pointer ,struct:char;	// 70
var pszHuKouPlaceTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceTail_Data:Array[Byte] = _ // for pszHuKouPlaceTail pointer ,struct:char;	// 70.
var pszPrinterUnitNameTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitNameTail_Data:Array[Byte] = _ // for pszPrinterUnitNameTail pointer ,struct:char;	// 50

  var pszCaseID1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID1_Data:Array[Byte] = _ // for pszCaseID1 pointer ,struct:char;		// case id one person may be connected with one case.
var pszCaseID2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID2_Data:Array[Byte] = _ // for pszCaseID2 pointer ,struct:char;		// allow 2
var pszShenFenIDTypeCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShenFenIDTypeCode_Data:Array[Byte] = _ // for pszShenFenIDTypeCode pointer ,struct:char;	//

  var pszMISConnectPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISConnectPersonID_Data:Array[Byte] = _ // for pszMISConnectPersonID pointer ,struct:char;

  // some newly added items
  var pszUnitNameCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnitNameCode_Data:Array[Byte] = _ // for pszUnitNameCode pointer ,struct:char;	// 16
var pszUnitNameTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnitNameTail_Data:Array[Byte] = _ // for pszUnitNameTail pointer ,struct:char;	// 70
var pszHeight:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHeight_Data:Array[Byte] = _ // for pszHeight pointer ,struct:char;			// 1, in cm
var pszFootLen:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFootLen_Data:Array[Byte] = _ // for pszFootLen pointer ,struct:char;		// 1, in cm
var pszRaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRaceCode_Data:Array[Byte] = _ // for pszRaceCode pointer ,struct:char;		// 6, nationality.
var pszBodyFeature:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBodyFeature_Data:Array[Byte] = _ // for pszBodyFeature pointer ,struct:char;	// 80;	body feature.
// for card scanner, user name has been recorded in tp card admin structure, but
// unitcode has not been logged. there is no space to place there, so we have to
// place it here.
var pszCreatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.
  // following are columns reserved.	for code used only.
  // we can split this columns later.
  //	char	*pszResserved1;		// 256 bytes reserved.

  // add on Feb. 19, 2008 by beagle
  var pszNationality:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNationality_Data:Array[Byte] = _ // for pszNationality pointer ,struct:char;		// 国籍, 6
var pszCertificateType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCertificateType_Data:Array[Byte] = _ // for pszCertificateType pointer ,struct:char;	// 证件类型, 6
var pszCertificateCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCertificateCode_Data:Array[Byte] = _ // for pszCertificateCode pointer ,struct:char;	// 证件号码, 32
var pszIsCriminalRecord:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsCriminalRecord_Data:Array[Byte] = _ // for pszIsCriminalRecord pointer ,struct:char;	// 前科标识, 1
var pszCriminalRecordDesc:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCriminalRecordDesc_Data:Array[Byte] = _ // for pszCriminalRecordDesc pointer ,struct:char;	// 前科情况描述, 1024

  /**
    * added by beagle on Dec. 14, 2008
    * 指纹信息采集系统用到:采集系统类型、活体指纹采集仪类型、指纹图像拼接软件类型、采集仪GA认证标志序列号、采集点编号
    * 采集时间 -- 对于活体采集也很有用，因为CreateTime对采集端和中心端会不一样，有了这个采集时间就可以比较上下级的卡片是否就是同一张卡片
    */
  var pszCaptureDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaptureDate_Data:Array[Byte] = _ // for pszCaptureDate pointer ,struct:char;	// 采集时间，14，YYYYMMDDHH24MMSS
var pszLFICSystemType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICSystemType_Data:Array[Byte] = _ // for pszLFICSystemType pointer ,struct:char;
var pszLFICScannerType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICScannerType_Data:Array[Byte] = _ // for pszLFICScannerType pointer ,struct:char;
var pszLFICMosaicType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICMosaicType_Data:Array[Byte] = _ // for pszLFICMosaicType pointer ,struct:char;
var pszLFICGASerial:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICGASerial_Data:Array[Byte] = _ // for pszLFICGASerial pointer ,struct:char;
var pszLFICCaptureCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICCaptureCode_Data:Array[Byte] = _ // for pszLFICCaptureCode pointer ,struct:char;

  //#ifdef	UTIL_20110309_NEW_COLUMN
  /**
    * added by beagle on March. 09, 2011
    * 增加一些人员信息
    */
  var pszDNASerial1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDNASerial1_Data:Array[Byte] = _ // for pszDNASerial1 pointer ,struct:char;		//!< 两个DNA序号
var pszDNASerial2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDNASerial2_Data:Array[Byte] = _ // for pszDNASerial2 pointer ,struct:char;

  /**
    * 一般老外姓名的表示：last name + middle name + first name；
    * 其中 last name + middle name 为名；first name 为姓
    * 又称为：教名+自取名+姓
    */
  var pszFamilyName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFamilyName_Data:Array[Byte] = _ // for pszFamilyName pointer ,struct:char;		//!< 老外的first name
var pszGivenName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGivenName_Data:Array[Byte] = _ // for pszGivenName pointer ,struct:char;		//!< 老外的last name
var pszMiddleName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMiddleName_Data:Array[Byte] = _ // for pszMiddleName pointer ,struct:char;		//!< 老外的middle name

  var pszBloodType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBloodType_Data:Array[Byte] = _ // for pszBloodType pointer ,struct:char;		//!< 血型
var pszWeight:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWeight_Data:Array[Byte] = _ // for pszWeight pointer ,struct:char;			//!< 体重
var pszAccent:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAccent_Data:Array[Byte] = _ // for pszAccent pointer ,struct:char;			//!< 口音
var pszEducation:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEducation_Data:Array[Byte] = _ // for pszEducation pointer ,struct:char;		//!< 教育程度
var pszOccupation:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOccupation_Data:Array[Byte] = _ // for pszOccupation pointer ,struct:char;		//!< 职业
var pszFigureType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFigureType_Data:Array[Byte] = _ // for pszFigureType pointer ,struct:char;		//!< 体型
var pszFaceType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceType_Data:Array[Byte] = _ // for pszFaceType pointer ,struct:char;		//!< 脸型
var pszBirthPlaceName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBirthPlaceName_Data:Array[Byte] = _ // for pszBirthPlaceName pointer ,struct:char;	//!< 出生地
var pszBirthPlaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBirthPlaceCode_Data:Array[Byte] = _ // for pszBirthPlaceCode pointer ,struct:char;	//!< 出生地代码
  //#endif

} // TPCARDDEFTEXT;


// for palm and finger
class LPCARDDEFTEXT extends AncientData
{
  var pszRidgeColor:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRidgeColor_Data:Array[Byte] = _ // for pszRidgeColor pointer ,struct:char;		// 1	// not standard
var pszRemainPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRemainPlace_Data:Array[Byte] = _ // for pszRemainPlace pointer ,struct:char;	// 30
var pszSeqNo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;			// 2, if latent finger id's encoding is standard then
// the last two digit is seqno.
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// comment, 128 bytes, not standard
var pszSenderFingerID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSenderFingerID_Data:Array[Byte] = _ // for pszSenderFingerID pointer ,struct:char;	// 20, ID of finger, for DOPS used only and for interchange

  var pszCreatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  /**
    * added on March. 10, 2011 by beagle
    * 未知名尸体标识和编号是针对现场卡片的，当初错加到了案件表里了
    */
  var pszIsUnknownBody:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsUnknownBody_Data:Array[Byte] = _ // for pszIsUnknownBody pointer ,struct:char;		// 未知名尸体标识, 1
var pszUnknownBodyCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnknownBodyCode_Data:Array[Byte] = _ // for pszUnknownBodyCode pointer ,struct:char;	// 未知名尸体编号, 32
var pszMntExtractMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMntExtractMethod_Data:Array[Byte] = _ // for pszMntExtractMethod pointer ,struct:char;	// 特征提取方法，代码
var pszGuessedFinger:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGuessedFinger_Data:Array[Byte] = _ // for pszGuessedFinger pointer ,struct:char;		//!< 分析指位。对应10个手指
var pszChainStartFinger:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChainStartFinger_Data:Array[Byte] = _ // for pszChainStartFinger pointer ,struct:char;	//!< 连指开始和结束序号
var pszChainEndFinger:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChainEndFinger_Data:Array[Byte] = _ // for pszChainEndFinger pointer ,struct:char;
} // LPCARDDEFTEXT;

class COLNAME_LPFACETEXT extends AncientData
{
  var pszSeqNo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// TEXT COLUMN

  var pszCreatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history
} // COLNAME_LPFACETEXT;

class COLNAME_LPVOICETEXT extends AncientData
{
  var pszSeqNo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// TEXT COLUMN

  var pszCreatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history
} // COLNAME_LPVOICETEXT;

class LPCASEDEFTEXT_50 extends AncientData
{
  var pszCaseClass1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1_Data:Array[Byte] = _ // for pszCaseClass1 pointer ,struct:char;		// 8
var pszCaseClass2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2_Data:Array[Byte] = _ // for pszCaseClass2 pointer ,struct:char;		// 8
var pszCaseClass3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3_Data:Array[Byte] = _ // for pszCaseClass3 pointer ,struct:char;		// 8
var pszCaseClass1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;		// 70
var pszCaseClass2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;		// 70
var pszCaseClass3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;		// 70
var pszCaseOccurPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlace_Data:Array[Byte] = _ // for pszCaseOccurPlace pointer ,struct:char;	// 70
var pszCaseOccurDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurDate_Data:Array[Byte] = _ // for pszCaseOccurDate pointer ,struct:char;	// 9
var pszExtractUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitCode_Data:Array[Byte] = _ // for pszExtractUnitCode pointer ,struct:char;	// 10
var pszExtractUnitName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitName_Data:Array[Byte] = _ // for pszExtractUnitName pointer ,struct:char;	// 70
var pszSuperviseLevel:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuperviseLevel_Data:Array[Byte] = _ // for pszSuperviseLevel pointer ,struct:char;		// 2
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// comment, 512 bytes
var pszSuspiciousArea1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1_Data:Array[Byte] = _ // for pszSuspiciousArea1 pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2_Data:Array[Byte] = _ // for pszSuspiciousArea2 pointer ,struct:char;	// 6
var pszSuspiciousArea3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3_Data:Array[Byte] = _ // for pszSuspiciousArea3 pointer ,struct:char;	// 6
var pszSuspiciousArea1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1Code_Data:Array[Byte] = _ // for pszSuspiciousArea1Code pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2Code_Data:Array[Byte] = _ // for pszSuspiciousArea2Code pointer ,struct:char;	// 6
var pszSuspiciousArea3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3Code_Data:Array[Byte] = _ // for pszSuspiciousArea3Code pointer ,struct:char;	// 6
var pszIllicitMoney:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIllicitMoney_Data:Array[Byte] = _ // for pszIllicitMoney pointer ,struct:char;		// 10, unit is Yuan
var pszSenderCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSenderCardID_Data:Array[Byte] = _ // for pszSenderCardID pointer ,struct:char;		// id of latent card, used to store other systems
// info. not used by GAFIS. for interchange.
// when convert .fpt file to GAFIS we store card id in this item
var pszExtractor:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractor_Data:Array[Byte] = _ // for pszExtractor pointer ,struct:char;		// 30
} // LPCASEDEFTEXT_50;

// used by GAFIS 6.0
class LPCASEDEFTEXT extends AncientData
{
  var pszCaseClass1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;		// 8
var pszCaseClass2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;		// 8
var pszCaseClass3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;		// 8
var pszCaseOccurPlaceTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlaceTail_Data:Array[Byte] = _ // for pszCaseOccurPlaceTail pointer ,struct:char;	// 70
var pszCaseOccurDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurDate_Data:Array[Byte] = _ // for pszCaseOccurDate pointer ,struct:char;	// 9
var pszExtractUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitCode_Data:Array[Byte] = _ // for pszExtractUnitCode pointer ,struct:char;	// 10
var pszSuperviseLevel:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuperviseLevel_Data:Array[Byte] = _ // for pszSuperviseLevel pointer ,struct:char;		// 2
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// comment, 512 bytes，简要案情
var pszSuspiciousArea1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1Code_Data:Array[Byte] = _ // for pszSuspiciousArea1Code pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2Code_Data:Array[Byte] = _ // for pszSuspiciousArea2Code pointer ,struct:char;	// 6
var pszSuspiciousArea3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3Code_Data:Array[Byte] = _ // for pszSuspiciousArea3Code pointer ,struct:char;	// 6
var pszIllicitMoney:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIllicitMoney_Data:Array[Byte] = _ // for pszIllicitMoney pointer ,struct:char;		// 10, unit is Yuan
var pszSenderCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSenderCardID_Data:Array[Byte] = _ // for pszSenderCardID pointer ,struct:char;		// id of latent card, used to store other systems
// info. not used by GAFIS. for interchange.
// when convert .fpt file to GAFIS we store card id in this item
var pszExtractor1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractor1_Data:Array[Byte] = _ // for pszExtractor1 pointer ,struct:char;		// 30
var pszExtractor2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractor2_Data:Array[Byte] = _ // for pszExtractor2 pointer ,struct:char;		// 30
var pszExtractor3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractor3_Data:Array[Byte] = _ // for pszExtractor3 pointer ,struct:char;		// 30

  // added on May 6 2004
  var pszExtractUnitNameTail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitNameTail_Data:Array[Byte] = _ // for pszExtractUnitNameTail pointer ,struct:char;	// tail part, 50.
var pszCaseOccurPlaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlaceCode_Data:Array[Byte] = _ // for pszCaseOccurPlaceCode pointer ,struct:char;	// head part.

  var pszPremium:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPremium_Data:Array[Byte] = _ // for pszPremium pointer ,struct:char;	// money will be pain for broken of this case.
var pszHitHistory:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;
var pszUpdatorUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;

  // add on Feb. 19, 2008 by beagle
  var pszIsUnknownBody:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsUnknownBody_Data:Array[Byte] = _ // for pszIsUnknownBody pointer ,struct:char;		// 未知名尸体标识, 1
var pszUnknownBodyCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnknownBodyCode_Data:Array[Byte] = _ // for pszUnknownBodyCode pointer ,struct:char;	// 未知名尸体编号, 32
var pszExtractDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractDate_Data:Array[Byte] = _ // for pszExtractDate pointer ,struct:char;		// 提取日期, 9
var pszCaseState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseState_Data:Array[Byte] = _ // for pszCaseState pointer ,struct:char;			// 案件状态, 4

} // LPCASEDEFTEXT;


class TPPERSONDEFTEXT extends AncientData
{
  var pszOperator:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOperator_Data:Array[Byte] = _ // for pszOperator pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszCreatorName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreatorName_Data:Array[Byte] = _ // for pszCreatorName pointer ,struct:char;
var pszUpdatorName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorName_Data:Array[Byte] = _ // for pszUpdatorName pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszUpdateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszCreateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszLPGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;	// group id for lp.
var pszLPGroupDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupDBID_Data:Array[Byte] = _ // for pszLPGroupDBID pointer ,struct:char;
var pszLPGroupTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupTID_Data:Array[Byte] = _ // for pszLPGroupTID pointer ,struct:char;		// group table id.
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
} // TPPERSONDEFTEXT;


class LPADMINCOLNAME extends AncientData
{
  var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonID_Data:Array[Byte] = _ // for pszPersonID pointer ,struct:char;
var pszGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszAccuLTCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAccuLTCount_Data:Array[Byte] = _ // for pszAccuLTCount pointer ,struct:char;
var pszAccuLLCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAccuLLCount_Data:Array[Byte] = _ // for pszAccuLLCount pointer ,struct:char;
var pszLTCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLTCount_Data:Array[Byte] = _ // for pszLTCount pointer ,struct:char;
var pszLLCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLLCount_Data:Array[Byte] = _ // for pszLLCount pointer ,struct:char;
var pszEditCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditCount_Data:Array[Byte] = _ // for pszEditCount pointer ,struct:char;	// # of times edited
var pszFingerType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerType_Data:Array[Byte] = _ // for pszFingerType pointer ,struct:char;
var pszLTDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLTDate_Data:Array[Byte] = _ // for pszLTDate pointer ,struct:char;
var pszLLDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLLDate_Data:Array[Byte] = _ // for pszLLDate pointer ,struct:char;
var pszLTUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLTUser_Data:Array[Byte] = _ // for pszLTUser pointer ,struct:char;
var pszLLUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLLUser_Data:Array[Byte] = _ // for pszLLUser pointer ,struct:char;
var pszGroupName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupName_Data:Array[Byte] = _ // for pszGroupName pointer ,struct:char;
var pszGroupCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszIsBroken:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsBroken_Data:Array[Byte] = _ // for pszIsBroken pointer ,struct:char;
var pszBrokenUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUser_Data:Array[Byte] = _ // for pszBrokenUser pointer ,struct:char;
var pszBrokenUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUnitCode_Data:Array[Byte] = _ // for pszBrokenUnitCode pointer ,struct:char;
var pszBrokenDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenDate_Data:Array[Byte] = _ // for pszBrokenDate pointer ,struct:char;
var pszReChecker:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReChecker_Data:Array[Byte] = _ // for pszReChecker pointer ,struct:char;
var pszIsLTBroken:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsLTBroken_Data:Array[Byte] = _ // for pszIsLTBroken pointer ,struct:char;	// may be lt or tl.
var pszMntExtractMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMntExtractMethod_Data:Array[Byte] = _ // for pszMntExtractMethod pointer ,struct:char;	//
var pszHitPersonState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPersonState_Data:Array[Byte] = _ // for pszHitPersonState pointer ,struct:char;		//
//	char	*pszAdminReserved;
// following columns are added on July 31, 2006
var pszOrgScanner:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszDigitizeMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDigitizeMethod_Data:Array[Byte] = _ // for pszDigitizeMethod pointer ,struct:char;
// above columns are added on July 31, 2006
var pszFgGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup_Data:Array[Byte] = _ // for pszFgGroup pointer ,struct:char;	// finger group. when doing affiliate of lp finger, we mark finger belong to
// same person's same finger(unknown exist finger position) with same fggroup.
var pszFgIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFgIndex_Data:Array[Byte] = _ // for pszFgIndex pointer ,struct:char;	// finger index. global. known finger position.
} // LPADMINCOLNAME;

class MOBILECASECOLNAME extends AncientData
{
  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszBinData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBinData_Data:Array[Byte] = _ // for pszBinData pointer ,struct:char;
} // MOBILECASECOLNAME;

// all system will have a prefix SYS_
class GAFIS_USERTABLECOLNAME extends AncientData
{
  var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// user name
var pszFullName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFullName_Data:Array[Byte] = _ // for pszFullName pointer ,struct:char;	// full user name
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszAddress:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;
var pszPhone:Array[String] = new Array[String](3)//using 4 byte as pointer
@IgnoreTransfer
var pszPhone_Data:Array[Byte] = _ // for pszPhone pointer ,struct:char;
var pszMail:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMail_Data:Array[Byte] = _ // for pszMail pointer ,struct:char;
var pszPostCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPostCode_Data:Array[Byte] = _ // for pszPostCode pointer ,struct:char;
var pszBinData1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBinData1_Data:Array[Byte] = _ // for pszBinData1 pointer ,struct:char;	// misc data
var pszBinData2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBinData2_Data:Array[Byte] = _ // for pszBinData2 pointer ,struct:char;	// right data
var pszFingerData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerData_Data:Array[Byte] = _ // for pszFingerData pointer ,struct:char;	/// finger data
var pszNuminaPriv:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNuminaPriv_Data:Array[Byte] = _ // for pszNuminaPriv pointer ,struct:char;
var pszIsGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsGroup_Data:Array[Byte] = _ // for pszIsGroup pointer ,struct:char;	// whether is group.

  // added by beagle on July.10, 2008
  var pszLoginIPMask:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLoginIPMask_Data:Array[Byte] = _ // for pszLoginIPMask pointer ,struct:char;

  //!< added by beagle on march.16, 2011
  var pszOrgID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgID_Data:Array[Byte] = _ // for pszOrgID pointer ,struct:char;	//!< 用户所在机构ID
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;	//!< 数据最后修改时间
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;	//!< 创建时间
} // GAFIS_USERTABLECOLNAME;

class GAFIS_MSGTABLECOLNAME extends AncientData
{
  var pszSender:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSender_Data:Array[Byte] = _ // for pszSender pointer ,struct:char;	// sender
var pszTitle:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTitle_Data:Array[Byte] = _ // for pszTitle pointer ,struct:char;
var pszSendDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSendDateTime_Data:Array[Byte] = _ // for pszSendDateTime pointer ,struct:char;
var pszContent:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszContent_Data:Array[Byte] = _ // for pszContent pointer ,struct:char;
var pszAttach:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAttach_Data:Array[Byte] = _ // for pszAttach pointer ,struct:char;
var pszState:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszState_Data:Array[Byte] = _ // for pszState pointer ,struct:char;
var pszImportance:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszImportance_Data:Array[Byte] = _ // for pszImportance pointer ,struct:char;
} // GAFIS_MSGTABLECOLNAME;

class GAFIS_PARAMTABLECOLNAME extends AncientData
{
  var pszParamName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamName_Data:Array[Byte] = _ // for pszParamName pointer ,struct:char;
var pszParamValue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamValue_Data:Array[Byte] = _ // for pszParamValue pointer ,struct:char;	// for small data object
var pszParamData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;	// for large data object
} // GAFIS_PARAMTABLECOLNAME;

class GAFIS_CODETABLECOLNAME extends AncientData
{
  var pszCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCode_Data:Array[Byte] = _ // for pszCode pointer ,struct:char;
var pszInputCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszInputCode_Data:Array[Byte] = _ // for pszInputCode pointer ,struct:char;
var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
} // GAFIS_CODETABLECOLNAME;

// break case table.
class GAFIS_BCTABLECOLNAME extends AncientData
{
  var pszBreakID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBreakID_Data:Array[Byte] = _ // for pszBreakID pointer ,struct:char;	// key of the action
var pszQueryTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryTaskID_Data:Array[Byte] = _ // for pszQueryTaskID pointer ,struct:char;	// query task id.

  var pszSrcKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcKey_Data:Array[Byte] = _ // for pszSrcKey pointer ,struct:char;
var pszDestKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestKey_Data:Array[Byte] = _ // for pszDestKey pointer ,struct:char;
var pszScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszScore_Data:Array[Byte] = _ // for pszScore pointer ,struct:char;
var pszRank:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRank_Data:Array[Byte] = _ // for pszRank pointer ,struct:char;
var pszFg:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFg_Data:Array[Byte] = _ // for pszFg pointer ,struct:char;	// globale image index.
var pszSrcPersonCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcPersonCaseID_Data:Array[Byte] = _ // for pszSrcPersonCaseID pointer ,struct:char;
var pszDestPersonCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestPersonCaseID_Data:Array[Byte] = _ // for pszDestPersonCaseID pointer ,struct:char;

  var pszCaseClassCode1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode1_Data:Array[Byte] = _ // for pszCaseClassCode1 pointer ,struct:char;
var pszCaseClassCode2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode2_Data:Array[Byte] = _ // for pszCaseClassCode2 pointer ,struct:char;
var pszCaseClassCode3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode3_Data:Array[Byte] = _ // for pszCaseClassCode3 pointer ,struct:char;

  var pszFirstRankScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstRankScore_Data:Array[Byte] = _ // for pszFirstRankScore pointer ,struct:char;
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszIsRemoteSearched:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsRemoteSearched_Data:Array[Byte] = _ // for pszIsRemoteSearched pointer ,struct:char;
var pszSearchingUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSearchingUnitCode_Data:Array[Byte] = _ // for pszSearchingUnitCode pointer ,struct:char;
var pszIsCrimeCaptured:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsCrimeCaptured_Data:Array[Byte] = _ // for pszIsCrimeCaptured pointer ,struct:char;
var pszFgType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFgType_Data:Array[Byte] = _ // for pszFgType pointer ,struct:char;	// finger, palm or tplain.

  var pszSrcMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcMnt_Data:Array[Byte] = _ // for pszSrcMnt pointer ,struct:char;
var pszDestMnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestMnt_Data:Array[Byte] = _ // for pszDestMnt pointer ,struct:char;
var pszSrcImg:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcImg_Data:Array[Byte] = _ // for pszSrcImg pointer ,struct:char;
var pszDestImg:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestImg_Data:Array[Byte] = _ // for pszDestImg pointer ,struct:char;
var pszSrcInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcInfo_Data:Array[Byte] = _ // for pszSrcInfo pointer ,struct:char;
var pszDestInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestInfo_Data:Array[Byte] = _ // for pszDestInfo pointer ,struct:char;
var pszSrcDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcDBID_Data:Array[Byte] = _ // for pszSrcDBID pointer ,struct:char;
var pszDestDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;

  var pszTotoalMatchedCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotoalMatchedCnt_Data:Array[Byte] = _ // for pszTotoalMatchedCnt pointer ,struct:char;

  // submit info.
  var pszSubmitUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszSubmitUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszSubmitDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;

  // break user info.
  var pszBreakUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBreakUserName_Data:Array[Byte] = _ // for pszBreakUserName pointer ,struct:char;
var pszBreakDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBreakDateTime_Data:Array[Byte] = _ // for pszBreakDateTime pointer ,struct:char;
var pszBreakUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBreakUserUnitCode_Data:Array[Byte] = _ // for pszBreakUserUnitCode pointer ,struct:char;

  // checker user info.
  var pszReCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;
var pszReCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;

  var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszComment1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment1_Data:Array[Byte] = _ // for pszComment1 pointer ,struct:char;
var pszComment2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment2_Data:Array[Byte] = _ // for pszComment2 pointer ,struct:char;
var pszReserved1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReserved1_Data:Array[Byte] = _ // for pszReserved1 pointer ,struct:char;
var pszReserved2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReserved2_Data:Array[Byte] = _ // for pszReserved2 pointer ,struct:char;
var pszReserved3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReserved3_Data:Array[Byte] = _ // for pszReserved3 pointer ,struct:char;

  var pszNotUsedA:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNotUsedA_Data:Array[Byte] = _ // for pszNotUsedA pointer ,struct:char;
var pszNotUsedB:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNotUsedB_Data:Array[Byte] = _ // for pszNotUsedB pointer ,struct:char;
} // GAFIS_BCTABLECOLNAME;

class GAFIS_DTPROPTABLECOLNAME extends AncientData
{
  var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
var pszData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_DTPROPTABLECOLNAME;

class GAFIS_DBMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTableID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTableID_Data:Array[Byte] = _ // for pszTableID pointer ,struct:char;
var pszModDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszOperation:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOperation_Data:Array[Byte] = _ // for pszOperation pointer ,struct:char;
var pszTableType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTableType_Data:Array[Byte] = _ // for pszTableType pointer ,struct:char;
var pszData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
var pszNotUsed:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNotUsed_Data:Array[Byte] = _ // for pszNotUsed pointer ,struct:char;
} // GAFIS_DBMOGLOGTABLECOLNAME;
  type GAFIS_DBMOGLOGTABLECOLNAME = GAFIS_DBMODLOGTABLECOLNAME

class GAFIS_SYSMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszModDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_SYSMODLOGTABLECOLNAME;

class GAFIS_USERAUTHLOGCOLNAME extends AncientData
{
  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszLoginDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLoginDateTime_Data:Array[Byte] = _ // for pszLoginDateTime pointer ,struct:char;
var pszLogoutDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLogoutDateTime_Data:Array[Byte] = _ // for pszLogoutDateTime pointer ,struct:char;
var pszLoginID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLoginID_Data:Array[Byte] = _ // for pszLoginID pointer ,struct:char;
var pszIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIP_Data:Array[Byte] = _ // for pszIP pointer ,struct:char;
var pszComputer:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputer_Data:Array[Byte] = _ // for pszComputer pointer ,struct:char;
var pszIsValidUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsValidUser_Data:Array[Byte] = _ // for pszIsValidUser pointer ,struct:char;
var pszModuleID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszModuleID_Data:Array[Byte] = _ // for pszModuleID pointer ,struct:char;
var pszRes:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRes_Data:Array[Byte] = _ // for pszRes pointer ,struct:char;
} // GAFIS_USERAUTHLOGCOLNAME;

class GAFIS_QRYMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszModDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszData:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_QRYMODLOGTABLECOLNAME;

class GAFIS_CASEADMINCOLNAME extends AncientData
{
  var pszIsBroken:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsBroken_Data:Array[Byte] = _ // for pszIsBroken pointer ,struct:char;
var pszBrokenUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUser_Data:Array[Byte] = _ // for pszBrokenUser pointer ,struct:char;
var pszReChecker:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReChecker_Data:Array[Byte] = _ // for pszReChecker pointer ,struct:char;
var pszBrokenUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUnitCode_Data:Array[Byte] = _ // for pszBrokenUnitCode pointer ,struct:char;
var pszBrokenDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenDate_Data:Array[Byte] = _ // for pszBrokenDate pointer ,struct:char;
var pszHasPersonKilled:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHasPersonKilled_Data:Array[Byte] = _ // for pszHasPersonKilled pointer ,struct:char;	// in this case whether has people killed.
var pszPersonKilledCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonKilledCount_Data:Array[Byte] = _ // for pszPersonKilledCount pointer ,struct:char;	// how many people killed.
var pszIsLTBroken:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsLTBroken_Data:Array[Byte] = _ // for pszIsLTBroken pointer ,struct:char;			// may be lt or tl.
var pszGroupCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszFaceIDCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceIDCount_Data:Array[Byte] = _ // for pszFaceIDCount pointer ,struct:char;
var pszFaceIDList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFaceIDList_Data:Array[Byte] = _ // for pszFaceIDList pointer ,struct:char;
var pszVoiceIDCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVoiceIDCount_Data:Array[Byte] = _ // for pszVoiceIDCount pointer ,struct:char;
var pszVoiceIDList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVoiceIDList_Data:Array[Byte] = _ // for pszVoiceIDList pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszUpdateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszCreateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
//	char	*pszCaseAdminReserved;	// reserved column
var pszCaseGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroupID_Data:Array[Byte] = _ // for pszCaseGroupID pointer ,struct:char;	// case group id.
var pszOrgScanner:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszItemKeyList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszItemKeyList_Data:Array[Byte] = _ // for pszItemKeyList pointer ,struct:char;
var pszMISConnectCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISConnectCaseID_Data:Array[Byte] = _ // for pszMISConnectCaseID pointer ,struct:char;

  var pszQryAssignChecker:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryAssignChecker_Data:Array[Byte] = _ // for pszQryAssignChecker pointer ,struct:char;
var pszQryAssignHasChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryAssignHasChecked_Data:Array[Byte] = _ // for pszQryAssignHasChecked pointer ,struct:char;

  //fan:add 添加以下3个字段
  var pszAJID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAJID_Data:Array[Byte] = _ // for pszAJID pointer ,struct:char;
var pszXKID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXKID_Data:Array[Byte] = _ // for pszXKID pointer ,struct:char;
var pszJQID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszJQID_Data:Array[Byte] = _ // for pszJQID pointer ,struct:char;
} // GAFIS_CASEADMINCOLNAME;	//

class GAFIS_ADMINTABLENAME extends AncientData
{
  var pszQueExf:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueExf_Data:Array[Byte] = _ // for pszQueExf pointer ,struct:char;
var pszQueEdit:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueEdit_Data:Array[Byte] = _ // for pszQueEdit pointer ,struct:char;
var pszQueTextInput:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueTextInput_Data:Array[Byte] = _ // for pszQueTextInput pointer ,struct:char;
/*
	// not used, we using querytype id in que.
  var pszQueLTSearch:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLTSearch_Data:Array[Byte] = _ // for pszQueLTSearch pointer ,struct:char;
  var pszQueLTCheck:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLTCheck_Data:Array[Byte] = _ // for pszQueLTCheck pointer ,struct:char;
  var pszQueLLSearch:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLLSearch_Data:Array[Byte] = _ // for pszQueLLSearch pointer ,struct:char;
  var pszQueLLCheck:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLLCheck_Data:Array[Byte] = _ // for pszQueLLCheck pointer ,struct:char;
  var pszQueTTSearch:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTTSearch_Data:Array[Byte] = _ // for pszQueTTSearch pointer ,struct:char;
  var pszQueTTCheck:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTTCheck_Data:Array[Byte] = _ // for pszQueTTCheck pointer ,struct:char;
  var pszQueTLSearch:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTLSearch_Data:Array[Byte] = _ // for pszQueTLSearch pointer ,struct:char;
  var pszQueTLCheck:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTLCheck_Data:Array[Byte] = _ // for pszQueTLCheck pointer ,struct:char;
*/
var pszDBModLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBModLog_Data:Array[Byte] = _ // for pszDBModLog pointer ,struct:char;
var pszQueSearch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueSearch_Data:Array[Byte] = _ // for pszQueSearch pointer ,struct:char;
var pszQueCheck:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueCheck_Data:Array[Byte] = _ // for pszQueCheck pointer ,struct:char;

  var pszUserAuthLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserAuthLog_Data:Array[Byte] = _ // for pszUserAuthLog pointer ,struct:char;
var pszDBRunLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBRunLog_Data:Array[Byte] = _ // for pszDBRunLog pointer ,struct:char;
var pszQualCheck:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQualCheck_Data:Array[Byte] = _ // for pszQualCheck pointer ,struct:char;
var pszWorkLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWorkLog_Data:Array[Byte] = _ // for pszWorkLog pointer ,struct:char;
var pszMntEditLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMntEditLog_Data:Array[Byte] = _ // for pszMntEditLog pointer ,struct:char;
var pszExfErrLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExfErrLog_Data:Array[Byte] = _ // for pszExfErrLog pointer ,struct:char;
var pszQualCheckLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQualCheckLog_Data:Array[Byte] = _ // for pszQualCheckLog pointer ,struct:char;
var pszQrySearchLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQrySearchLog_Data:Array[Byte] = _ // for pszQrySearchLog pointer ,struct:char;
var pszQryCheckLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryCheckLog_Data:Array[Byte] = _ // for pszQryCheckLog pointer ,struct:char;
var pszQualCheckQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQualCheckQue_Data:Array[Byte] = _ // for pszQualCheckQue pointer ,struct:char;

  var pszQrySubmitLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQrySubmitLog_Data:Array[Byte] = _ // for pszQrySubmitLog pointer ,struct:char;
var pszQryReCheckLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryReCheckLog_Data:Array[Byte] = _ // for pszQryReCheckLog pointer ,struct:char;
var pszTPLPAssociate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPLPAssociate_Data:Array[Byte] = _ // for pszTPLPAssociate pointer ,struct:char;
var pszTPLPUnmatch:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPLPUnmatch_Data:Array[Byte] = _ // for pszTPLPUnmatch pointer ,struct:char;
var pszTPLPFpxQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPLPFpxQue_Data:Array[Byte] = _ // for pszTPLPFpxQue pointer ,struct:char;
var pszTPLPFpxLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPLPFpxLog_Data:Array[Byte] = _ // for pszTPLPFpxLog pointer ,struct:char;
var pszQryFpxQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryFpxQue_Data:Array[Byte] = _ // for pszQryFpxQue pointer ,struct:char;
var pszQryFpxLog:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryFpxLog_Data:Array[Byte] = _ // for pszQryFpxLog pointer ,struct:char;
var pszLsnTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLsnTable_Data:Array[Byte] = _ // for pszLsnTable pointer ,struct:char;

  var pszQryCheckAssignTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryCheckAssignTable_Data:Array[Byte] = _ // for pszQryCheckAssignTable pointer ,struct:char;

  /**
    * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
    * added by beagle on Nov. 27, 2008
    */
  var pszLFICDTStatusTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICDTStatusTable_Data:Array[Byte] = _ // for pszLFICDTStatusTable pointer ,struct:char;
var pszLFICFBInfoTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLFICFBInfoTable_Data:Array[Byte] = _ // for pszLFICFBInfoTable pointer ,struct:char;

  /**
    * 公安部协查平台及缉控人员表
    * [11/23/2011 xia_xinfeng]
    */
  var pszGAXCTaskTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGAXCTaskTable_Data:Array[Byte] = _ // for pszGAXCTaskTable pointer ,struct:char;
var pszGAXCLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGAXCLogTable_Data:Array[Byte] = _ // for pszGAXCLogTable pointer ,struct:char;
var pszWantedListTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWantedListTable_Data:Array[Byte] = _ // for pszWantedListTable pointer ,struct:char;
var pszWantedTPCardTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWantedTPCardTable_Data:Array[Byte] = _ // for pszWantedTPCardTable pointer ,struct:char;
var pszWantedLogTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWantedLogTable_Data:Array[Byte] = _ // for pszWantedLogTable pointer ,struct:char;

  //!<add by zyn at 2014.08.11 for shanghai xk
  var pszShxkDataStatusTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShxkDataStatusTable_Data:Array[Byte] = _ // for pszShxkDataStatusTable pointer ,struct:char;
var pszShxkMatchInfoTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShxkMatchInfoTable_Data:Array[Byte] = _ // for pszShxkMatchInfoTable pointer ,struct:char;
var pszShxkCaseStatusTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShxkCaseStatusTable_Data:Array[Byte] = _ // for pszShxkCaseStatusTable pointer ,struct:char;
var pszShxkCaseTextTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShxkCaseTextTable_Data:Array[Byte] = _ // for pszShxkCaseTextTable pointer ,struct:char;

  //!<add by nn at 2014.9.23 for nanjing
  var pszTTRelationTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTRelationTable_Data:Array[Byte] = _ // for pszTTRelationTable pointer ,struct:char;
var pszTTCandidateTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTTCandidateTable_Data:Array[Byte] = _ // for pszTTCandidateTable pointer ,struct:char;

  //!<add by zyn at 2014.10.21 for nj
  var pszNjDelDataTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNjDelDataTable_Data:Array[Byte] = _ // for pszNjDelDataTable pointer ,struct:char;

  //!<add by wangkui
  var pszXCDataTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCDataTable_Data:Array[Byte] = _ // for pszXCDataTable pointer ,struct:char;
var pszXCReportTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCReportTable_Data:Array[Byte] = _ // for pszXCReportTable pointer ,struct:char;

  //!<add by fanjuan 20160219
  var pszLNHXReportDataTable:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLNHXReportDataTable_Data:Array[Byte] = _ // for pszLNHXReportDataTable pointer ,struct:char;

  var nQueExfTID:Short = _;
  var nQueEditTID:Short = _;
  var nQueTextInputTID:Short = _;
  /*
  var nQueLTSearchTID:Short = _;
  var nQueLTCheckTID:Short = _;
  var nQueLLSearchTID:Short = _;
  var nQueLLCheckTID:Short = _;
  var nQueTTSearchTID:Short = _;
  var nQueTTCheckTID:Short = _;
  var nQueTLSearchTID:Short = _;
  var nQueTLCheckTID:Short = _;
*/
  var nDBModLogTID:Short = _;
  var nQueSearchTID:Short = _;
  var nQueCheckTID:Short = _;

  var nUserAuthLog:Short = _;	// tableid.
var nDBRunLog:Short = _;	// table id
var nQualCheckTID:Short = _;	// table id.
var nWorkLogTID:Short = _;
  var nMntEditLogTID:Short = _;
  var nExfErrLogTID:Short = _;
  var nQualCheckLogTID:Short = _;
  var nQrySearchLogTID:Short = _;
  var nQryCheckLogTID:Short = _;
  var nQualCheckQueTID:Short = _;
  var nQrySubmitLogTID:Short = _;
  var nQryReCheckLogTID:Short = _;

  var nTPLPAssociate:Short = _;
  var nTPLPUnmatch:Short = _;
  var nTPLPFpxQue:Short = _;
  var nTPLPFpxLog:Short = _;
  var nQryFpxQue:Short = _;
  var nQryFpxLog:Short = _;
  var nLsnTable:Short = _;
  var nQryCheckAssignTable:Short = _;

  /**
    * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
    * added by beagle on Nov. 27, 2008
    */
  var nLFICDTStatusTable:Short = _;
  var nLFICFBInfoTable:Short = _;

  var nGAXCTaskTable:Short = _;
  var nGAXCLogTable:Short = _;
  var nWantedListTable:Short = _;
  var nWantedTPCardTable:Short = _;
  var nWantedLogTable:Short = _;

  //!<add by zyn at 2014.08.11 for shanghai xk
  var nShxkDataStatusTable:Short = _;
  var nShxkMatchInfoTable:Short = _;
  var nShxkCaseStatusTable:Short = _;
  var nShxkCaseTextTable:Short = _;
  //!<add by nn at 2014.9.23 for nanjing
  var nTTRelationTable:Short = _;
  var nTTCandidateTable:Short = _;

  //!<add by zyn at 2014.10.21
  var nNjDelDataTable:Short = _;

  //!<add by wangkui
  var nXCDataTable:Short = _;
  var nXCReportDataTable:Short = _;

  //!<add by fanjuan 20160219
  var nLNHXReportDataTable:Short = _;
} // GAFIS_ADMINTABLENAME;	// some table name on admin db.

class COLNAME_DBRUNLOG extends AncientData
{
  var pszStartupTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStartupTime_Data:Array[Byte] = _ // for pszStartupTime pointer ,struct:char;
var pszShutdownTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShutdownTime_Data:Array[Byte] = _ // for pszShutdownTime pointer ,struct:char;
var pszShutdownUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShutdownUser_Data:Array[Byte] = _ // for pszShutdownUser pointer ,struct:char;
var pszUserIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserIP_Data:Array[Byte] = _ // for pszUserIP pointer ,struct:char;
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// name of computer.
var pszSvrVersion:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSvrVersion_Data:Array[Byte] = _ // for pszSvrVersion pointer ,struct:char;		// version string.
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} // COLNAME_DBRUNLOG;

// when remote user transmit card to central system
// the central system may enforce a restriction on
// tenprint card quality
class COLNAME_TPQUALCHK extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszInputUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszInputUnitCode_Data:Array[Byte] = _ // for pszInputUnitCode pointer ,struct:char;
var pszAddTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddTime_Data:Array[Byte] = _ // for pszAddTime pointer ,struct:char;
var pszChkTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChkTime_Data:Array[Byte] = _ // for pszChkTime pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
//char	*pszComment;
var pszQueSID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueSID_Data:Array[Byte] = _ // for pszQueSID pointer ,struct:char;
var pszChecker:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChecker_Data:Array[Byte] = _ // for pszChecker pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;	// which database the card is in.
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszDownloadTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDownloadTime_Data:Array[Byte] = _ // for pszDownloadTime pointer ,struct:char;
var pszFingerIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszTQrySID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTQrySID_Data:Array[Byte] = _ // for pszTQrySID pointer ,struct:char;
var pszLQrySID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLQrySID_Data:Array[Byte] = _ // for pszLQrySID pointer ,struct:char;
var pszQryDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryDBID_Data:Array[Byte] = _ // for pszQryDBID pointer ,struct:char;
var pszQryTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryTID_Data:Array[Byte] = _ // for pszQryTID pointer ,struct:char;
var pszDestTenDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestTenDBID_Data:Array[Byte] = _ // for pszDestTenDBID pointer ,struct:char;
var pszDestTenTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestTenTID_Data:Array[Byte] = _ // for pszDestTenTID pointer ,struct:char;
var pszDestLatDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestLatDBID_Data:Array[Byte] = _ // for pszDestLatDBID pointer ,struct:char;
var pszDestLatTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestLatTID_Data:Array[Byte] = _ // for pszDestLatTID pointer ,struct:char;
} // COLNAME_TPQUALCHK;

class COLNAME_TPQUALCHKWORKLOG extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszInputUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszInputUnitCode_Data:Array[Byte] = _ // for pszInputUnitCode pointer ,struct:char;
var pszChkTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChkTime_Data:Array[Byte] = _ // for pszChkTime pointer ,struct:char;
var pszChecker:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszChecker_Data:Array[Byte] = _ // for pszChecker pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;	// which database the card is in.
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszNeedRescan:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszNeedRescan_Data:Array[Byte] = _ // for pszNeedRescan pointer ,struct:char;
var pszFingerIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
} // COLNAME_TPQUALCHKWORKLOG;

class COLNAME_WORKLOG extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;		// source key.
var pszWorkType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWorkType_Data:Array[Byte] = _ // for pszWorkType pointer ,struct:char;	// work type.
var pszWorkClass:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWorkClass_Data:Array[Byte] = _ // for pszWorkClass pointer ,struct:char;	// work class
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;	// user name.
var pszDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;	// data time
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFingerImgChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerImgChanged_Data:Array[Byte] = _ // for pszFingerImgChanged pointer ,struct:char;	// finger image changed.
var pszFingerMntChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerMntChanged_Data:Array[Byte] = _ // for pszFingerMntChanged pointer ,struct:char;	// mnt changed.
var pszPalmImgChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmImgChanged_Data:Array[Byte] = _ // for pszPalmImgChanged pointer ,struct:char;
var pszPalmMntChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmMntChanged_Data:Array[Byte] = _ // for pszPalmMntChanged pointer ,struct:char;
var pszTextChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTextChanged_Data:Array[Byte] = _ // for pszTextChanged pointer ,struct:char;
var pszTPlainImgChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainImgChanged_Data:Array[Byte] = _ // for pszTPlainImgChanged pointer ,struct:char;
var pszTPlainMntChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainMntChanged_Data:Array[Byte] = _ // for pszTPlainMntChanged pointer ,struct:char;
var pszOtherChanged:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOtherChanged_Data:Array[Byte] = _ // for pszOtherChanged pointer ,struct:char;
var pszIsTwoFaceCard:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsTwoFaceCard_Data:Array[Byte] = _ // for pszIsTwoFaceCard pointer ,struct:char;

  var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszFingerCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerCnt_Data:Array[Byte] = _ // for pszFingerCnt pointer ,struct:char;
var pszFingerHQCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerHQCnt_Data:Array[Byte] = _ // for pszFingerHQCnt pointer ,struct:char;
var pszTPlainCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainCnt_Data:Array[Byte] = _ // for pszTPlainCnt pointer ,struct:char;
var pszTPlainHQCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainHQCnt_Data:Array[Byte] = _ // for pszTPlainHQCnt pointer ,struct:char;
var pszPalmCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmCnt_Data:Array[Byte] = _ // for pszPalmCnt pointer ,struct:char;
var pszPalmHQCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmHQCnt_Data:Array[Byte] = _ // for pszPalmHQCnt pointer ,struct:char;
var pszEditTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEditTime_Data:Array[Byte] = _ // for pszEditTime pointer ,struct:char;

} // COLNAME_WORKLOG;

// minutia correction statistics for tp used only.
class COLNAME_MNTEDITLOG extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;	// date time
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFingerEditInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerEditInfo_Data:Array[Byte] = _ // for pszFingerEditInfo pointer ,struct:char;
var pszTPlainEditInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPlainEditInfo_Data:Array[Byte] = _ // for pszTPlainEditInfo pointer ,struct:char;
var pszPalmEditInfo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPalmEditInfo_Data:Array[Byte] = _ // for pszPalmEditInfo pointer ,struct:char;
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
} // COLNAME_MNTEDITLOG;

class COLNAME_EXFERRLOG extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszErrorCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszErrorCode_Data:Array[Byte] = _ // for pszErrorCode pointer ,struct:char;
var pszRetValue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRetValue_Data:Array[Byte] = _ // for pszRetValue pointer ,struct:char;
var pszMntFormat:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMntFormat_Data:Array[Byte] = _ // for pszMntFormat pointer ,struct:char;
var pszFingerIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
var pszSecondFingerIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSecondFingerIndex_Data:Array[Byte] = _ // for pszSecondFingerIndex pointer ,struct:char;
var pszCompressMethod:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCompressMethod_Data:Array[Byte] = _ // for pszCompressMethod pointer ,struct:char;
var pszDllName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDllName_Data:Array[Byte] = _ // for pszDllName pointer ,struct:char;
var pszDllVersion:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDllVersion_Data:Array[Byte] = _ // for pszDllVersion pointer ,struct:char;
var pszExfAppVersion:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExfAppVersion_Data:Array[Byte] = _ // for pszExfAppVersion pointer ,struct:char;
} // COLNAME_EXFERRLOG;

class COLNAME_QUERYSEARCHLOG extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszTimeUsed:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;
var pszDBRecCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBRecCount_Data:Array[Byte] = _ // for pszDBRecCount pointer ,struct:char;
var pszFirstCandScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;	// first candidate score.
var pszFirstCandKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandKey_Data:Array[Byte] = _ // for pszFirstCandKey pointer ,struct:char;	// first candidate key.
var pszSubmitDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;	// submit date time.
var pszFinishDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFinishDateTime_Data:Array[Byte] = _ // for pszFinishDateTime pointer ,struct:char;	// when finished searching.
var pszSubmitUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
/*
  var pszDBID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
  var pszTID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
  var pszDestDBID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;
  var pszDestTID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszDestTID_Data:Array[Byte] = _ // for pszDestTID pointer ,struct:char;
*/
var pszIsFifoQue:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsFifoQue_Data:Array[Byte] = _ // for pszIsFifoQue pointer ,struct:char;
var pszRecSearchedCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecSearchedCnt_Data:Array[Byte] = _ // for pszRecSearchedCnt pointer ,struct:char;	// # of records searched.
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			// flag.
var pszRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;		// remote flag
var pszFinalCandCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFinalCandCnt_Data:Array[Byte] = _ // for pszFinalCandCnt pointer ,struct:char;	// final candidate count.
var pszQUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszMICCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMICCount_Data:Array[Byte] = _ // for pszMICCount pointer ,struct:char;
var pszPriority:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
} // COLNAME_QUERYSEARCHLOG;	// query search log.

class COLNAME_QUERYCHECKLOG_OLD extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSubmitUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszCheckWorkType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckWorkType_Data:Array[Byte] = _ // for pszCheckWorkType pointer ,struct:char;	// check, recheck.
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszIsRmtQuery:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsRmtQuery_Data:Array[Byte] = _ // for pszIsRmtQuery pointer ,struct:char;
var pszSubmitTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszFinishTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;
var pszCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszCheckDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszReCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDateTime_Data:Array[Byte] = _ // for pszReCheckDateTime pointer ,struct:char;
var pszSubmitUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszComputerIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this ip.
var pszIsFofoQueQry:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsFofoQueQry_Data:Array[Byte] = _ // for pszIsFofoQueQry pointer ,struct:char;
var pszFirstCandScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;
var pszFirstCandKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandKey_Data:Array[Byte] = _ // for pszFirstCandKey pointer ,struct:char;
var pszHitCandScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitCandScore_Data:Array[Byte] = _ // for pszHitCandScore pointer ,struct:char;
var pszHitCandKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitCandKey_Data:Array[Byte] = _ // for pszHitCandKey pointer ,struct:char;
var pszCandCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;	// candidate count.
var pszHitCandIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitCandIndex_Data:Array[Byte] = _ // for pszHitCandIndex pointer ,struct:char;
} // COLNAME_QUERYCHECKLOG_OLD;

class COLNAME_QUERYSUBMITLOG extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSubmitUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszSubmitTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszSubmitUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCandCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszIsFifoQueQry:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsFifoQueQry_Data:Array[Byte] = _ // for pszIsFifoQueQry pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
//	char	*pszIsRmtQuery;
var pszComputerIP:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;
var pszComputerName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszQryUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryUID_Data:Array[Byte] = _ // for pszQryUID pointer ,struct:char;
var pszMICCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMICCount_Data:Array[Byte] = _ // for pszMICCount pointer ,struct:char;
var pszPriority:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;	// submit query status.
} // COLNAME_QUERYSUBMITLOG;


class COLNAME_QUERYCHECKLOG extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszQUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszFirstCandScore:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;
var pszCandCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;	// candidate count.
var pszSearchFinishTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSearchFinishTime_Data:Array[Byte] = _ // for pszSearchFinishTime pointer ,struct:char;
var pszCheckDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszSubmitUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszHitPoss:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszCheckResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckResult_Data:Array[Byte] = _ // for pszCheckResult pointer ,struct:char;
var pszQryFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryFlag_Data:Array[Byte] = _ // for pszQryFlag pointer ,struct:char;
var pszQryRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryRmtFlag_Data:Array[Byte] = _ // for pszQryRmtFlag pointer ,struct:char;
var pszHitCandCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitCandCnt_Data:Array[Byte] = _ // for pszHitCandCnt pointer ,struct:char;
} // COLNAME_QUERYCHECKLOG;


class COLNAME_QUERYRECHECKLOG extends AncientData
{
  var pszKeyID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszQUID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszReCheckUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszCheckDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszReCheckDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDateTime_Data:Array[Byte] = _ // for pszReCheckDateTime pointer ,struct:char;
var pszSubmitUserUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRecordType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszQryFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryFlag_Data:Array[Byte] = _ // for pszQryFlag pointer ,struct:char;
var pszQryRmtFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryRmtFlag_Data:Array[Byte] = _ // for pszQryRmtFlag pointer ,struct:char;
var pszHitCandCnt:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHitCandCnt_Data:Array[Byte] = _ // for pszHitCandCnt pointer ,struct:char;
} // COLNAME_QUERYRECHECKLOG;

class COLNAME_LPGROUP extends AncientData
{
  var pszGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszKeyList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyList_Data:Array[Byte] = _ // for pszKeyList pointer ,struct:char;
var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
} // COLNAME_LPGROUP;

class COLNAME_CASEGROUP extends AncientData
{
  var pszCaseGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroupID_Data:Array[Byte] = _ // for pszCaseGroupID pointer ,struct:char;
var pszKeyList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKeyList_Data:Array[Byte] = _ // for pszKeyList pointer ,struct:char;
var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
} // COLNAME_CASEGROUP;

class COLNAME_TPLP_ASSOCIATE extends AncientData
{
  var pszTPPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPPersonID_Data:Array[Byte] = _ // for pszTPPersonID pointer ,struct:char;
var pszLPGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;
var pszCreateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszIdentifyUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
var pszIdentifyUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUnitCode_Data:Array[Byte] = _ // for pszIdentifyUnitCode pointer ,struct:char;
var pszTPDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPDBID_Data:Array[Byte] = _ // for pszTPDBID pointer ,struct:char;
var pszTPTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPTID_Data:Array[Byte] = _ // for pszTPTID pointer ,struct:char;
var pszLPDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPDBID_Data:Array[Byte] = _ // for pszLPDBID pointer ,struct:char;
var pszLPTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPTID_Data:Array[Byte] = _ // for pszLPTID pointer ,struct:char;
} // COLNAME_TPLP_ASSOCIATE;

class COLNAME_TPLP_UNMATCH extends AncientData
{
  var pszTPPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPPersonID_Data:Array[Byte] = _ // for pszTPPersonID pointer ,struct:char;
var pszLPGroupID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;
var pszLatFgGroup:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatFgGroup_Data:Array[Byte] = _ // for pszLatFgGroup pointer ,struct:char;
var pszLatKeyType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatKeyType_Data:Array[Byte] = _ // for pszLatKeyType pointer ,struct:char;
var pszTPIndex:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPIndex_Data:Array[Byte] = _ // for pszTPIndex pointer ,struct:char;
var pszIdentifyUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
var pszTPDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPDBID_Data:Array[Byte] = _ // for pszTPDBID pointer ,struct:char;
var pszTPTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTPTID_Data:Array[Byte] = _ // for pszTPTID pointer ,struct:char;
var pszLPDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPDBID_Data:Array[Byte] = _ // for pszLPDBID pointer ,struct:char;
var pszLPTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLPTID_Data:Array[Byte] = _ // for pszLPTID pointer ,struct:char;
} // COLNAME_TPLP_UNMATCH;

class COLNAME_TP_UNMATCH extends AncientData
{
  var pszKey1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey1_Data:Array[Byte] = _ // for pszKey1 pointer ,struct:char;
var pszKey2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey2_Data:Array[Byte] = _ // for pszKey2 pointer ,struct:char;
var pszIdentifyUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
} // COLNAME_TP_UNMATCH;

class COLNAME_LP_UNMATCH extends AncientData
{
  var pszKey1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey1_Data:Array[Byte] = _ // for pszKey1 pointer ,struct:char;
var pszFgGroup1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup1_Data:Array[Byte] = _ // for pszFgGroup1 pointer ,struct:char;
var pszKey2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey2_Data:Array[Byte] = _ // for pszKey2 pointer ,struct:char;
var pszFgGroup2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup2_Data:Array[Byte] = _ // for pszFgGroup2 pointer ,struct:char;
var pszIdentifyUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
} // COLNAME_LP_UNMATCH;


class COLNAME_LP_MULTIMNT extends AncientData
{
  // first group.
  var pszMnt1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt1_Data:Array[Byte] = _ // for pszMnt1 pointer ,struct:char;
var pszBin1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin1_Data:Array[Byte] = _ // for pszBin1 pointer ,struct:char;
  /*
  var pszUpdateUser1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
*/

  // second group.
  var pszMnt2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt2_Data:Array[Byte] = _ // for pszMnt2 pointer ,struct:char;
var pszBin2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin2_Data:Array[Byte] = _ // for pszBin2 pointer ,struct:char;
  /*
  var pszUpdateUser2:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser2_Data:Array[Byte] = _ // for pszUpdateUser2 pointer ,struct:char;
  var pszUpdateUnitCode2:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode2_Data:Array[Byte] = _ // for pszUpdateUnitCode2 pointer ,struct:char;
  var pszUpdateDateTime2:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime2_Data:Array[Byte] = _ // for pszUpdateDateTime2 pointer ,struct:char;
*/

  // third group.
  var pszMnt3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt3_Data:Array[Byte] = _ // for pszMnt3 pointer ,struct:char;
var pszBin3:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin3_Data:Array[Byte] = _ // for pszBin3 pointer ,struct:char;
  /*
  var pszUpdateUser3:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser3_Data:Array[Byte] = _ // for pszUpdateUser3 pointer ,struct:char;
  var pszUpdateUnitCode3:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode3_Data:Array[Byte] = _ // for pszUpdateUnitCode3 pointer ,struct:char;
  var pszUpdateDateTime3:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime3_Data:Array[Byte] = _ // for pszUpdateDateTime3 pointer ,struct:char;
*/

  // fourth group.
  var pszMnt4:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt4_Data:Array[Byte] = _ // for pszMnt4 pointer ,struct:char;
var pszBin4:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin4_Data:Array[Byte] = _ // for pszBin4 pointer ,struct:char;
  /*
  var pszUpdateUser4:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser4_Data:Array[Byte] = _ // for pszUpdateUser4 pointer ,struct:char;
  var pszUpdateUnitCode4:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode4_Data:Array[Byte] = _ // for pszUpdateUnitCode4 pointer ,struct:char;
  var pszUpdateDateTime4:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime4_Data:Array[Byte] = _ // for pszUpdateDateTime4 pointer ,struct:char;
*/

  // fifth group.
  var pszMnt5:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt5_Data:Array[Byte] = _ // for pszMnt5 pointer ,struct:char;
var pszBin5:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin5_Data:Array[Byte] = _ // for pszBin5 pointer ,struct:char;
/*
  var pszUpdateUser5:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser5_Data:Array[Byte] = _ // for pszUpdateUser5 pointer ,struct:char;
  var pszUpdateUnitCode5:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode5_Data:Array[Byte] = _ // for pszUpdateUnitCode5 pointer ,struct:char;
  var pszUpdateDateTime5:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime5_Data:Array[Byte] = _ // for pszUpdateDateTime5 pointer ,struct:char;
*/
var pszMnt6:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt6_Data:Array[Byte] = _ // for pszMnt6 pointer ,struct:char;
var pszBin6:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin6_Data:Array[Byte] = _ // for pszBin6 pointer ,struct:char;
  /*
  var pszUpdateUser1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt7:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt7_Data:Array[Byte] = _ // for pszMnt7 pointer ,struct:char;
var pszBin7:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin7_Data:Array[Byte] = _ // for pszBin7 pointer ,struct:char;
  /*
  var pszUpdateUser1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt8:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt8_Data:Array[Byte] = _ // for pszMnt8 pointer ,struct:char;
var pszBin8:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin8_Data:Array[Byte] = _ // for pszBin8 pointer ,struct:char;
  /*
  var pszUpdateUser1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt9:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMnt9_Data:Array[Byte] = _ // for pszMnt9 pointer ,struct:char;
var pszBin9:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBin9_Data:Array[Byte] = _ // for pszBin9 pointer ,struct:char;
  /*
  var pszUpdateUser1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/


} // COLNAME_LP_MULTIMNT;


class COLNAME_TPLP_FPXQUE_TABLE extends AncientData
{
  var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDataType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDataType_Data:Array[Byte] = _ // for pszDataType pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszLastOpTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLastOpTime_Data:Array[Byte] = _ // for pszLastOpTime pointer ,struct:char;
var pszLastOpUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLastOpUser_Data:Array[Byte] = _ // for pszLastOpUser pointer ,struct:char;
var pszSubmitUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
var pszSubmitDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;
var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszPersonIDCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonIDCaseID_Data:Array[Byte] = _ // for pszPersonIDCaseID pointer ,struct:char;
var pszPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPurpose_Data:Array[Byte] = _ // for pszPurpose pointer ,struct:char;
var pszFPXComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXComment_Data:Array[Byte] = _ // for pszFPXComment pointer ,struct:char;
var pszAFISComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAFISComment_Data:Array[Byte] = _ // for pszAFISComment pointer ,struct:char;
var pszRmtUploadStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadStatus_Data:Array[Byte] = _ // for pszRmtUploadStatus pointer ,struct:char;
var pszRmtDownloadStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadStatus_Data:Array[Byte] = _ // for pszRmtDownloadStatus pointer ,struct:char;
var pszRmtUploadDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadDateTime_Data:Array[Byte] = _ // for pszRmtUploadDateTime pointer ,struct:char;
var pszRmtDownloadDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadDateTime_Data:Array[Byte] = _ // for pszRmtDownloadDateTime pointer ,struct:char;
} // COLNAME_TPLP_FPXQUE_TABLE;

class COLNAME_TPLP_FPXLOG_TABLE extends AncientData
{
  var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDataType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDataType_Data:Array[Byte] = _ // for pszDataType pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszOpTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOpTime_Data:Array[Byte] = _ // for pszOpTime pointer ,struct:char;
var pszOpUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOpUser_Data:Array[Byte] = _ // for pszOpUser pointer ,struct:char;
var pszSubmitUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
var pszSubmitDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;
var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszPersonIDCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPersonIDCaseID_Data:Array[Byte] = _ // for pszPersonIDCaseID pointer ,struct:char;
var pszPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszPurpose_Data:Array[Byte] = _ // for pszPurpose pointer ,struct:char;
} // COLNAME_TPLP_FPXLOG_TABLE;

class COLNAME_QUERY_FPXQUE_TABLE extends AncientData
{
  var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszRecordType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszLastOpTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLastOpTime_Data:Array[Byte] = _ // for pszLastOpTime pointer ,struct:char;
var pszLastOpUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLastOpUser_Data:Array[Byte] = _ // for pszLastOpUser pointer ,struct:char;
var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszRequestTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestTime_Data:Array[Byte] = _ // for pszRequestTime pointer ,struct:char;
var pszRequestUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestUser_Data:Array[Byte] = _ // for pszRequestUser pointer ,struct:char;
var pszRequestUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestUnitCode_Data:Array[Byte] = _ // for pszRequestUnitCode pointer ,struct:char;
var pszTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;
var pszForeignUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszForeignUnitCode_Data:Array[Byte] = _ // for pszForeignUnitCode pointer ,struct:char;
var pszFPXComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFPXComment_Data:Array[Byte] = _ // for pszFPXComment pointer ,struct:char;
var pszAFISComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAFISComment_Data:Array[Byte] = _ // for pszAFISComment pointer ,struct:char;
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtUploadStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadStatus_Data:Array[Byte] = _ // for pszRmtUploadStatus pointer ,struct:char;
var pszRmtDownloadStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadStatus_Data:Array[Byte] = _ // for pszRmtDownloadStatus pointer ,struct:char;
var pszRmtUploadDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadDateTime_Data:Array[Byte] = _ // for pszRmtUploadDateTime pointer ,struct:char;
var pszRmtDownloadDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadDateTime_Data:Array[Byte] = _ // for pszRmtDownloadDateTime pointer ,struct:char;
} // COLNAME_QUERY_FPXQUE_TABLE;

class COLNAME_QUERY_FPXLOG_TABLE extends AncientData
{
  var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszRecordType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszForeignUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszForeignUnitCode_Data:Array[Byte] = _ // for pszForeignUnitCode pointer ,struct:char;
var pszRequestTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestTime_Data:Array[Byte] = _ // for pszRequestTime pointer ,struct:char;
var pszRequestUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestUser_Data:Array[Byte] = _ // for pszRequestUser pointer ,struct:char;
var pszRequestUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRequestUnitCode_Data:Array[Byte] = _ // for pszRequestUnitCode pointer ,struct:char;
var pszTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;
var pszVerifyResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
} // COLNAME_QUERY_FPXLOG_TABLE;

class COLNAME_LICENSEMGR extends AncientData
{
  var pszUserID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserID_Data:Array[Byte] = _ // for pszUserID pointer ,struct:char;			// currently it's mac address.
var pszUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszCreateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszSnoUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSnoUpdateTime_Data:Array[Byte] = _ // for pszSnoUpdateTime pointer ,struct:char;	// if pszSno changed, then change this time.
var pszCSigUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCSigUpdateTime_Data:Array[Byte] = _ // for pszCSigUpdateTime pointer ,struct:char;	// if pszCSig changed, then change this time
var pszSNo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSNo_Data:Array[Byte] = _ // for pszSNo pointer ,struct:char;		// serial no. STRING.
var pszCSig:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCSig_Data:Array[Byte] = _ // for pszCSig pointer ,struct:char;		// computer signature. TEXT
} // COLNAME_LICENSEMGR;

class COLNAME_QRYASSIGN extends AncientData
{
  var pszUserID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserID_Data:Array[Byte] = _ // for pszUserID pointer ,struct:char;
var pszTotalCaseChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotalCaseChecked_Data:Array[Byte] = _ // for pszTotalCaseChecked pointer ,struct:char;
var pszTotalLTFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotalLTFingerChecked_Data:Array[Byte] = _ // for pszTotalLTFingerChecked pointer ,struct:char;
var pszTotalLLFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotalLLFingerChecked_Data:Array[Byte] = _ // for pszTotalLLFingerChecked pointer ,struct:char;
var pszTotalTLFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotalTLFingerChecked_Data:Array[Byte] = _ // for pszTotalTLFingerChecked pointer ,struct:char;
var pszTotalTTFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTotalTTFingerChecked_Data:Array[Byte] = _ // for pszTotalTTFingerChecked pointer ,struct:char;
var pszCurCaseChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurCaseChecked_Data:Array[Byte] = _ // for pszCurCaseChecked pointer ,struct:char;
var pszCurLTFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurLTFingerChecked_Data:Array[Byte] = _ // for pszCurLTFingerChecked pointer ,struct:char;
var pszCurLLFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurLLFingerChecked_Data:Array[Byte] = _ // for pszCurLLFingerChecked pointer ,struct:char;
var pszCurTLFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurTLFingerChecked_Data:Array[Byte] = _ // for pszCurTLFingerChecked pointer ,struct:char;
var pszCurTTFingerChecked:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurTTFingerChecked_Data:Array[Byte] = _ // for pszCurTTFingerChecked pointer ,struct:char;
var pszCurStageStartDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurStageStartDate_Data:Array[Byte] = _ // for pszCurStageStartDate pointer ,struct:char;
var pszCurStageStopDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCurStageStopDate_Data:Array[Byte] = _ // for pszCurStageStopDate pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszCreateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
} // COLNAME_QRYASSIGN;


/**
  * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
  * added by beagle on Nov. 27, 2008
  */
class COLNAME_LFIC_DATATRANSMITSTATUS extends AncientData
{
  var pszCardKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardKey_Data:Array[Byte] = _ // for pszCardKey pointer ,struct:char;
var pszMISPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszCreator:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreator_Data:Array[Byte] = _ // for pszCreator pointer ,struct:char;
var pszUpdater:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdater_Data:Array[Byte] = _ // for pszUpdater pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszSystemType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSystemType_Data:Array[Byte] = _ // for pszSystemType pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
} //COLNAME_LFIC_DATATRANSMITSTATUS;

class COLNAME_LFIC_FEEDBACKINFO extends AncientData
{
  var pszCardKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardKey_Data:Array[Byte] = _ // for pszCardKey pointer ,struct:char;
var pszMISPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszUserName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszFeedbackDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFeedbackDate_Data:Array[Byte] = _ // for pszFeedbackDate pointer ,struct:char;
var pszSystemType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSystemType_Data:Array[Byte] = _ // for pszSystemType pointer ,struct:char;
var pszType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszType_Data:Array[Byte] = _ // for pszType pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDescription:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDescription_Data:Array[Byte] = _ // for pszDescription pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
} //COLNAME_LFIC_FEEDBACKINFO;


/**
  * 增加掌纹侧掌字段，增加一个宏用来控制是否要在数据库中增加侧掌字段
  * Added by Beagle on Jun. 29, 2010
  */
//#define UTIL_PALMWRITER_ADDTODB			1

final val UTIL_PALMDATA_ITEMCOUNT = 10
//#else
//final val UTIL_PALMDATA_ITEMCOUNT = 8


/**
  * 增加几张表，用来处理与公安部协查平台的互连、数据交换、日志信息等
  *	Added on Nov. 4, 2011
  */
class GAFIS_GAFPTXCDATATABLECOLNAME extends AncientData
{
  var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;			//!< 协查任务对应的卡片存放的数据库ID
var pszTID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;			//!< 表ID
var pszCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;			//!< 卡号（条码号）
var pszXCPriority:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCPriority_Data:Array[Byte] = _ // for pszXCPriority pointer ,struct:char;		//!< 协查级别
var pszXCPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;		//!< 协查目的：根据公安部FPT4.0中提供的协查目的分类的基础上进行扩充
var pszXCSource:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCSource_Data:Array[Byte] = _ // for pszXCSource pointer ,struct:char;		//!< 协查源：该协查任务的来源，根据文档《省级指纹自动识别系统接口改造要求补充说明（2011.10.31修改稿）.doc
var pszXCStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCStatus_Data:Array[Byte] = _ // for pszXCStatus pointer ,struct:char;		//!< 该协查任务的当前状态
var pszAssoPersonID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAssoPersonID_Data:Array[Byte] = _ // for pszAssoPersonID pointer ,struct:char;	//!< 关联人员编号。采用人员编号或者在逃人员编号编码规则
var pszAssoCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAssoCaseID_Data:Array[Byte] = _ // for pszAssoCaseID pointer ,struct:char;		//!< 关联案件编号。同案件编号规则
var pszXCExpireTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCExpireTime_Data:Array[Byte] = _ // for pszXCExpireTime pointer ,struct:char;	//!< 协查过期时间//协查有效时限：1. 一个月	2. 三个月	3 六个月	4 长期
var pszXCCancelTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCCancelTime_Data:Array[Byte] = _ // for pszXCCancelTime pointer ,struct:char;	//!< 协查撤销时间
var pszXCUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCUnitCode_Data:Array[Byte] = _ // for pszXCUnitCode pointer ,struct:char;		//!< 协查请求单位代码
var pszXCUnitName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCUnitName_Data:Array[Byte] = _ // for pszXCUnitName pointer ,struct:char;		//!< 协查请求单位名称
var pszXCDateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCDateTime_Data:Array[Byte] = _ // for pszXCDateTime pointer ,struct:char;		//!< 协查请求日期
var pszLinkMan:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLinkMan_Data:Array[Byte] = _ // for pszLinkMan pointer ,struct:char;		//!< 联系人
var pszLinkPhone:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLinkPhone_Data:Array[Byte] = _ // for pszLinkPhone pointer ,struct:char;		//!< 联系电话
var pszApprovedBy:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszApprovedBy_Data:Array[Byte] = _ // for pszApprovedBy pointer ,struct:char;		//!< 审批人
var pszXCDescript:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCDescript_Data:Array[Byte] = _ // for pszXCDescript pointer ,struct:char;		//!< 协查请求说明
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
var pszLatestOpTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatestOpTime_Data:Array[Byte] = _ // for pszLatestOpTime pointer ,struct:char;	//!< 最近一次的操作时间：对于布控，记录的是最近一次重发查询的时间
var pszCheckTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;		//!< 最近一次检查时间：目前主要用在布控任务上，用来记录布控卡片与活体采集是否有比中结果的检查时间
var pszIsValid:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsValid_Data:Array[Byte] = _ // for pszIsValid pointer ,struct:char;		//!< 协查任务是否被撤销了，0 - 表示撤销，1 - 表示有效
var pszFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			//!< 一些标记：布控标记、比中任务是否必须返回标记等
var pszQryTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryTaskID_Data:Array[Byte] = _ // for pszQryTaskID pointer ,struct:char;		//!< 对应该协查任务的最新的查询ID
var pszTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;			//!< 对应该协查任务的任务控制号
} //GAFIS_GAFPTXCDATATABLECOLNAME;

class GAFIS_GAFPTXCLOGTABLECOLNAME extends AncientData
{
  var pszXCType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCType_Data:Array[Byte] = _ // for pszXCType pointer ,struct:char;		//!< 协查log类型：自动查询任务、跨省协查任务...等待
var pszXCPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;	//!< 自动查询任务和跨省协查任务的协查目的
var pszCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;		//!< 对应GAFIS系统的捺印卡号、现场卡号或案件编号
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
} //GAFIS_GAFPTXCLOGTABLECOLNAME;


class GAFIS_WANTEDLISTTABLECOLNAME extends AncientData 	//!< 布控（追逃）人员表
{
  var pszWanted_Type:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_Type_Data:Array[Byte] = _ // for pszWanted_Type pointer ,struct:char;		//!< 缉控的类型：公安部，本系统
var pszWanted_Status:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_Status_Data:Array[Byte] = _ // for pszWanted_Status pointer ,struct:char;		//!< 缉控状态
var pszWanted_NO:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;			//!< 追逃号码
var pszWanted_By:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_By_Data:Array[Byte] = _ // for pszWanted_By pointer ,struct:char;			//!< 追逃单位
var pszConnect_With:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszConnect_With_Data:Array[Byte] = _ // for pszConnect_With pointer ,struct:char;		//!< 联系人
var pszConnect_Phnoe:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszConnect_Phnoe_Data:Array[Byte] = _ // for pszConnect_Phnoe pointer ,struct:char;		//!< 联系电话
var pszCreate_Time:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreate_Time_Data:Array[Byte] = _ // for pszCreate_Time pointer ,struct:char;		//!< 记录创建时间
var pszCreate_User:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreate_User_Data:Array[Byte] = _ // for pszCreate_User pointer ,struct:char;		//!< 记录创建用户
var pszCreate_UnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreate_UnitCode_Data:Array[Byte] = _ // for pszCreate_UnitCode pointer ,struct:char;
var pszUpdate_Time:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_Time_Data:Array[Byte] = _ // for pszUpdate_Time pointer ,struct:char;		//!< 更新时间
var pszUpdate_User:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_User_Data:Array[Byte] = _ // for pszUpdate_User pointer ,struct:char;		//!< 更新用户
var pszUpdate_UnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_UnitCode_Data:Array[Byte] = _ // for pszUpdate_UnitCode pointer ,struct:char;
var pszComments:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComments_Data:Array[Byte] = _ // for pszComments pointer ,struct:char;			//!< 备注(按照公安部的要求有512个字节)
var pszCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;				//!< 如果此人与案件相关，则使用此ID
var pszCaseClassCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode_Data:Array[Byte] = _ // for pszCaseClassCode pointer ,struct:char;		//!< 案件类别
var pszWanted_TpCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_TpCardID_Data:Array[Byte] = _ // for pszWanted_TpCardID pointer ,struct:char;	//!< 追逃指纹编号
var pszName:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
var pszAlias:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;				//!< 别名
var pszSexCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;
var pszBirthDate:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;
var pszShenFenID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			//!< 身份证
var pszHuKouPlaceCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;
var pszHuKouPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlace_Data:Array[Byte] = _ // for pszHuKouPlace pointer ,struct:char;
var pszAddressCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;
var pszAddress:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;
} //GAFIS_WANTEDLISTTABLECOLNAME;


//!< 所有与缉控人员有重卡关系的捺印卡片ID。每个捺印人员只能对应一个辑控记录'
class GAFIS_WANTEDTPCARDTABLECOLNAME extends AncientData
{
  var pszWanted_NO:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;		//!< 外键，与GAFIS_WANTEDLISTTABLECOLNAME表的Wanted_NO关联
var pszCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;			//!< 与该辑控人员关联的捺印卡号
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
var pszCreateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;		//!< 记录创建用户
var pszUpdateUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;		//!< 更新用户
var pszFillInUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFillInUser_Data:Array[Byte] = _ // for pszFillInUser pointer ,struct:char;		//!< 谁填报的。就是谁确认他们是属于这个卡片组的
var pszFillInUnitCode:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFillInUnitCode_Data:Array[Byte] = _ // for pszFillInUnitCode pointer ,struct:char;	//!< 填报单位
var pszFillInTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszFillInTime_Data:Array[Byte] = _ // for pszFillInTime pointer ,struct:char;		//!< 填报时间
var pszDBID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;			//!< 捺印卡片所在的数据库ID
} //GAFIS_WANTEDTPCARDTABLECOLNAME;

class GAFIS_WANTEDOPLOGTABLECOLNAME extends AncientData 	//!< 追逃的操作记录
{
  var pszWanted_NO:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;			//!< 追逃号码
var pszOP_Type:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOP_Type_Data:Array[Byte] = _ // for pszOP_Type pointer ,struct:char;
var pszTpCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTpCardID_Data:Array[Byte] = _ // for pszTpCardID pointer ,struct:char;
var pszCreateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
} //GAFIS_WANTEDOPLOGTABLECOLNAME;

//!<add by zyn at 2014.08.08
class Shxk_DataStatusColName extends AncientData
{
  var pszXk_No:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszResult1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszResult1_Data:Array[Byte] = _ // for pszResult1 pointer ,struct:char;
var pszResult2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszResult2_Data:Array[Byte] = _ // for pszResult2 pointer ,struct:char;
var pszDataFile:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDataFile_Data:Array[Byte] = _ // for pszDataFile pointer ,struct:char;
var pszOperationNumber:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOperationNumber_Data:Array[Byte] = _ // for pszOperationNumber pointer ,struct:char;
var pszIsAgain:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsAgain_Data:Array[Byte] = _ // for pszIsAgain pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_DATASTATUSCOLNAME;
type SHXK_DATASTATUSCOLNAME = Shxk_DataStatusColName
class Shxk_MatchInfoColName extends AncientData
{
  var pszXk_No:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszLatCardNo:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszLatCardNo_Data:Array[Byte] = _ // for pszLatCardNo pointer ,struct:char;
var pszQryID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszQryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryType_Data:Array[Byte] = _ // for pszQryType pointer ,struct:char;
var pszCandList:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;
var pszCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCount_Data:Array[Byte] = _ // for pszCount pointer ,struct:char;
var pszSubmitTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszMatchTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMatchTime_Data:Array[Byte] = _ // for pszMatchTime pointer ,struct:char;
var pszSubmitUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnit:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnit_Data:Array[Byte] = _ // for pszSubmitUnit pointer ,struct:char;
var pszResult1:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszResult1_Data:Array[Byte] = _ // for pszResult1 pointer ,struct:char;
var pszResult2:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszResult2_Data:Array[Byte] = _ // for pszResult2 pointer ,struct:char;
var pszIsAgain:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszIsAgain_Data:Array[Byte] = _ // for pszIsAgain pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_MATCHINFOCOLNAME;
type SHXK_MATCHINFOCOLNAME = Shxk_MatchInfoColName
class Shxk_CaseStatusColName extends AncientData
{
  var pszXk_No:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszBrokenTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBrokenTime_Data:Array[Byte] = _ // for pszBrokenTime pointer ,struct:char;
var pszStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszSysType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSysType_Data:Array[Byte] = _ // for pszSysType pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
} //SHXK_CASESTATUSCOLNAME;

type SHXK_CASESTATUSCOLNAME =  Shxk_CaseStatusColName

class Shxk_CaseTextColName extends AncientData
{
  var pszXk_No:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszCaseID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;
var pszOccurUnit:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOccurUnit_Data:Array[Byte] = _ // for pszOccurUnit pointer ,struct:char;
var pszOccurPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOccurPlace_Data:Array[Byte] = _ // for pszOccurPlace pointer ,struct:char;
var pszOccurTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOccurTime_Data:Array[Byte] = _ // for pszOccurTime pointer ,struct:char;
var pszExtractor:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractor_Data:Array[Byte] = _ // for pszExtractor pointer ,struct:char;
var pszExtractUnit:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnit_Data:Array[Byte] = _ // for pszExtractUnit pointer ,struct:char;
var pszExtractPlace:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractPlace_Data:Array[Byte] = _ // for pszExtractPlace pointer ,struct:char;
var pszExtractTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractTime_Data:Array[Byte] = _ // for pszExtractTime pointer ,struct:char;
var pszCaseType1Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseType1Code_Data:Array[Byte] = _ // for pszCaseType1Code pointer ,struct:char;
var pszCaseType2Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseType2Code_Data:Array[Byte] = _ // for pszCaseType2Code pointer ,struct:char;
var pszCaseType3Code:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseType3Code_Data:Array[Byte] = _ // for pszCaseType3Code pointer ,struct:char;
var pszMoney:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMoney_Data:Array[Byte] = _ // for pszMoney pointer ,struct:char;
var pszCaseStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCaseStatus_Data:Array[Byte] = _ // for pszCaseStatus pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_CASETEXTCOLNAME;

  type SHXK_CASETEXTCOLNAME = Shxk_CaseTextColName
//!<add by zyn at 2014.10.21
class COLNAME_NjDelData extends AncientData
{
  var pKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pKey_Data:Array[Byte] = _ // for pKey pointer ,struct:char;
var pKeyType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pKeyType_Data:Array[Byte] = _ // for pKeyType pointer ,struct:char;
var pCurUser:String = _//using 4 byte as pointer
@IgnoreTransfer
var pCurUser_Data:Array[Byte] = _ // for pCurUser pointer ,struct:char;
var pResult:String = _//using 4 byte as pointer
@IgnoreTransfer
var pResult_Data:Array[Byte] = _ // for pResult pointer ,struct:char;
var pOpNum:String = _//using 4 byte as pointer
@IgnoreTransfer
var pOpNum_Data:Array[Byte] = _ // for pOpNum pointer ,struct:char;
var pTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pTaskID_Data:Array[Byte] = _ // for pTaskID pointer ,struct:char;
} //NJ_DELDATACOLNAME;
type NJ_DELDATACOLNAME = COLNAME_NjDelData
class COLNAME_TTRelation extends AncientData {

  var pszPersonNo1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo1_Data:Array[Byte] = _ // for pszPersonNo1 pointer ,struct:char;
  var pszPersonNo2:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo2_Data:Array[Byte] = _ // for pszPersonNo2 pointer ,struct:char;
  var pszMatchUnitCode:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitCode_Data:Array[Byte] = _ // for pszMatchUnitCode pointer ,struct:char;
  var pszMatchUnitName:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitName_Data:Array[Byte] = _ // for pszMatchUnitName pointer ,struct:char;
  var pszMatchUser:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUser_Data:Array[Byte] = _ // for pszMatchUser pointer ,struct:char;
  var pszMatchDateTime:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchDateTime_Data:Array[Byte] = _ // for pszMatchDateTime pointer ,struct:char;
  var pszNote:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszNote_Data:Array[Byte] = _ // for pszNote pointer ,struct:char;
  var pszReportUser:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUser_Data:Array[Byte] = _ // for pszReportUser pointer ,struct:char;
  var pszReportDateTime:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszReportDateTime_Data:Array[Byte] = _ // for pszReportDateTime pointer ,struct:char;
  var pszApprovalUser:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszApprovalUser_Data:Array[Byte] = _ // for pszApprovalUser pointer ,struct:char;
  var pszApprovalDateTime:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszApprovalDateTime_Data:Array[Byte] = _ // for pszApprovalDateTime pointer ,struct:char;
  var pszReportUnitCode:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUnitCode_Data:Array[Byte] = _ // for pszReportUnitCode pointer ,struct:char;
  var pszReportUnitName:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUnitName_Data:Array[Byte] = _ // for pszReportUnitName pointer ,struct:char;
  var pszRecheckUser:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckUser_Data:Array[Byte] = _ // for pszRecheckUser pointer ,struct:char;
  var pszRecheckUnitCode:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckUnitCode_Data:Array[Byte] = _ // for pszRecheckUnitCode pointer ,struct:char;
  var pszRecheckDateTime:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckDateTime_Data:Array[Byte] = _ // for pszRecheckDateTime pointer ,struct:char;
  var pszGroupID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
} //COLNAME_TT_RELATION;
  type COLNAME_TT_RELATION = COLNAME_TTRelation
class COLNAME_TTCandidate extends AncientData {

  var pszPersonNo1:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo1_Data:Array[Byte] = _ // for pszPersonNo1 pointer ,struct:char;
  var pszPersonNo2:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo2_Data:Array[Byte] = _ // for pszPersonNo2 pointer ,struct:char;
  var pszMatchUnitCode:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitCode_Data:Array[Byte] = _ // for pszMatchUnitCode pointer ,struct:char;
  var pszMatchDateTime:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchDateTime_Data:Array[Byte] = _ // for pszMatchDateTime pointer ,struct:char;
  var pszScore:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszScore_Data:Array[Byte] = _ // for pszScore pointer ,struct:char;
  var pszRank:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszRank_Data:Array[Byte] = _ // for pszRank pointer ,struct:char;
  var pszGroupID:String = _//using 4 byte as pointer
  @IgnoreTransfer
  var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
} //COLNAME_TT_CAND;
  type COLNAME_TT_CAND = COLNAME_TTCandidate
//ad by wangkui at 2014.10.30
class COLNAME_XCData extends AncientData
{
  var pszTaskID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;//key
var pszCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;
var pszCardType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardType_Data:Array[Byte] = _ // for pszCardType pointer ,struct:char;
var pszQryID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszTaskType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskType_Data:Array[Byte] = _ // for pszTaskType pointer ,struct:char;
var pszSendTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSendTime_Data:Array[Byte] = _ // for pszSendTime pointer ,struct:char;
var pszRecvTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecvTime_Data:Array[Byte] = _ // for pszRecvTime pointer ,struct:char;
var pszEntryTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEntryTime_Data:Array[Byte] = _ // for pszEntryTime pointer ,struct:char;
var pszUpdateTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszResendTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszResendTime_Data:Array[Byte] = _ // for pszResendTime pointer ,struct:char;
var pszCheckTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;
var pszCancelTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCancelTime_Data:Array[Byte] = _ // for pszCancelTime pointer ,struct:char;
var pszReportTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszExtractReturnTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractReturnTime_Data:Array[Byte] = _ // for pszExtractReturnTime pointer ,struct:char;
var pszCancelStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCancelStatus_Data:Array[Byte] = _ // for pszCancelStatus pointer ,struct:char;
var pszCancelTaskFailedReason:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskFailedReason_Data:Array[Byte] = _ // for pszCancelTaskFailedReason pointer ,struct:char;
var pszCancelTaskCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskCount_Data:Array[Byte] = _ // for pszCancelTaskCount pointer ,struct:char;
var pszCancelTaskMaxCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskMaxCount_Data:Array[Byte] = _ // for pszCancelTaskMaxCount pointer ,struct:char;
var pszExtractStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractStatus_Data:Array[Byte] = _ // for pszExtractStatus pointer ,struct:char;
var pszExtractFailedReason:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszExtractFailedReason_Data:Array[Byte] = _ // for pszExtractFailedReason pointer ,struct:char;
var pszReportStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportStatus_Data:Array[Byte] = _ // for pszReportStatus pointer ,struct:char;
var pszReportFailedReason:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportFailedReason_Data:Array[Byte] = _ // for pszReportFailedReason pointer ,struct:char;
var pszReportCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportCount_Data:Array[Byte] = _ // for pszReportCount pointer ,struct:char;
var pszReportMaxCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportMaxCount_Data:Array[Byte] = _ // for pszReportMaxCount pointer ,struct:char;
var pszXCPurpose:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;
var pszXCStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCStatus_Data:Array[Byte] = _ // for pszXCStatus pointer ,struct:char;
var pszEntryStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEntryStatus_Data:Array[Byte] = _ // for pszEntryStatus pointer ,struct:char;
var pszEntryMaxCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEntryMaxCount_Data:Array[Byte] = _ // for pszEntryMaxCount pointer ,struct:char;
var pszEntryCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEntryCount_Data:Array[Byte] = _ // for pszEntryCount pointer ,struct:char;
var pszEntryFailedReason:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszEntryFailedReason_Data:Array[Byte] = _ // for pszEntryFailedReason pointer ,struct:char;
var pszRecvAck:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszRecvAck_Data:Array[Byte] = _ // for pszRecvAck pointer ,struct:char;
var pszXCLevel:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszXCLevel_Data:Array[Byte] = _ // for pszXCLevel pointer ,struct:char;
var pszBKFlag:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszBKFlag_Data:Array[Byte] = _ // for pszBKFlag pointer ,struct:char;
var pszReportCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportCardID_Data:Array[Byte] = _ // for pszReportCardID pointer ,struct:char;
} //COLNAME_XCDATA;
  type COLNAME_XCDATA=COLNAME_XCData
//add by wangkui
class COLNAME_XCREPORTDATA extends AncientData
{
  var pszKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszCardID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;
var pszCardType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszCardType_Data:Array[Byte] = _ // for pszCardType pointer ,struct:char;
var pszTaskType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszTaskType_Data:Array[Byte] = _ // for pszTaskType pointer ,struct:char;
var pszReportTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszReportStatus:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportStatus_Data:Array[Byte] = _ // for pszReportStatus pointer ,struct:char;
var pszReportFailedReason:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportFailedReason_Data:Array[Byte] = _ // for pszReportFailedReason pointer ,struct:char;
var pszReportCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportCount_Data:Array[Byte] = _ // for pszReportCount pointer ,struct:char;
var pszReportMaxCount:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportMaxCount_Data:Array[Byte] = _ // for pszReportMaxCount pointer ,struct:char;
var pszInRAID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszInRAID_Data:Array[Byte] = _ // for pszInRAID pointer ,struct:char;
var pszOutRAID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszOutRAID_Data:Array[Byte] = _ // for pszOutRAID pointer ,struct:char;
} //COLNAME_XCREPORTDATA;

//add by fanjuanjuan 20160219
class COLNAME_LNHXReportData extends AncientData
{
  var pszReportTime:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszMatchTabe:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszMatchTabe_Data:Array[Byte] = _ // for pszMatchTabe pointer ,struct:char;
var pszQryID:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszQueryType:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszSrcKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszSrcKey_Data:Array[Byte] = _ // for pszSrcKey pointer ,struct:char;
var pszDestKey:String = _//using 4 byte as pointer
@IgnoreTransfer
var pszDestKey_Data:Array[Byte] = _ // for pszDestKey pointer ,struct:char;
} //COLNAME_LNHXReportData;

// contain all column names
class COLNAMESTRUCT extends AncientData
{
  var stTPLPFPXCommon = new COLNAME_TPLP_FPX_COMMON;	// tp lp FPX common columns
var stTcID = new COLGENNAMESTRUCT;			// tenprint card id, ( card id, barcode)
var stTPnID = new COLGENNAMESTRUCT;		// person id
var stTAdm = new TPADMINCOLNAME;
  @Length(10)
  var stTFg:Array[COLMICBNAMESTRUCT] = Range(0,10).map(x=>new COLMICBNAMESTRUCT).toArray		// tenprint finger 10 tenfingers 10X5=50 names(rolled)
@Length(10)
var stTpf:Array[COLMICBNAMESTRUCT] = Range(0,10).map(x=>new COLMICBNAMESTRUCT).toArray		// tenprint plain fingers(unrolled), and extension
  // of the system.

  /**
    * 增加了左右侧掌
    */
  @Length(UTIL_PALMDATA_ITEMCOUNT)
  var stTPm:Array[COLMICBNAMESTRUCT] = Range(0,UTIL_PALMDATA_ITEMCOUNT).map(x=>new COLMICBNAMESTRUCT).toArray
  // tenprint 2 palms, 2*5 = 10 names(no feature
  // stTPm[0] right hand palm
  // stTPm[1] left hand palm
  // stTPm[2] right hand palm fingers
  // stTPm[3] left hand palm fingers
  // stTPm[4] right hand thumb lower part
  // stTPm[5] right hand thumb upper part
  // stTPm[6] left hand thumb lower part
  // stTPm[7] left hand thumb upper part
  // stTPm[8] 右侧掌
  // stTPm[9] 左侧掌

  @Length(2)
  var stTSign:Array[COLMICBNAMESTRUCT] = Range(0,2).map(x=>new COLMICBNAMESTRUCT).toArray		// signature names for criminal and printer.
@Length(8)
var stTCb:Array[COLGENNAMESTRUCT] = Range(0,8).map(x=>new COLGENNAMESTRUCT).toArray		// tenprint card binary data, 8 names

  /**
    * 增加双拇指模式平面采集指纹
    */
  @Length(5)
  var stTPl:Array[COLMICBNAMESTRUCT] = Range(0,5).map(x=>new COLMICBNAMESTRUCT).toArray		// plain fingers only four part 4*2=8 items(no mnt, bin, and feat),
  // 0--right thumb, 1-right four, 2 - left thumb 3 -left four
  //	COLMICBNAMESTRUCT	stTFsFr;		// face front
  //	COLMICBNAMESTRUCT	stTFsNl;		// face nose left
  //	COLMICBNAMESTRUCT	stTFsNr;		// face nose right

  @Length(6)
  var stFace:Array[COLMICBNAMESTRUCT] = Range(0,6).map(x=>new COLMICBNAMESTRUCT).toArray		// 6 extra face data.
  // stFace[0] for face front.
  // stFace[1] for face nose left.
  // stFace[2] for face nose right.
  // stFace[3:5] is used for any direction.

  var stTExtra = new COLMICBNAMESTRUCT;		// extra data, (six finger)
var stTCardCount = new COLGENNAMESTRUCT;	// tp card count
var stTCardIDList = new COLGENNAMESTRUCT;	// tp card id list

  var stLFgID = new COLGENNAMESTRUCT;		// latent finger id
var stLPmID = new COLGENNAMESTRUCT;		// latent palm id
var stLCsID = new COLGENNAMESTRUCT;		// case id
var stLFg = new COLMICBNAMESTRUCT;			// latent finger
var stLPm = new COLMICBNAMESTRUCT;			// latent palm
var stLFingerCount = new COLGENNAMESTRUCT;
  var stLFingerIDList = new COLGENNAMESTRUCT;
  var stLPalmCount = new COLGENNAMESTRUCT;
  var stLPalmIDList = new COLGENNAMESTRUCT;
  var stLAdm = new LPADMINCOLNAME;
  var stLGuessedFinger = new COLGENNAMESTRUCT;

  // stMISCaseID一直没有被使用（虽然在更新记录的时候被使用了，但是一直没有在数据库
  // 表中创建这一列。因此被注释掉，而采用	GAFIS_CASEADMINCOLNAME::pszMISConnectCaseID
  // 2007.04.10
  // COLGENNAMESTRUCT	stMISCaseID;
  var stGroupID = new COLGENNAMESTRUCT;

  var stUser = new GAFIS_USERTABLECOLNAME;
  var stMsg = new GAFIS_MSGTABLECOLNAME;
  var stParam = new GAFIS_PARAMTABLECOLNAME;
  var stCode = new GAFIS_CODETABLECOLNAME;
  var stBc = new GAFIS_BCTABLECOLNAME;
  var stDTProp = new GAFIS_DTPROPTABLECOLNAME;
  var stDBMLog = new GAFIS_DBMOGLOGTABLECOLNAME;
  var stSYSMLog = new GAFIS_SYSMODLOGTABLECOLNAME;
  var stQryMLog = new GAFIS_QRYMODLOGTABLECOLNAME;

  var stUserAuth = new GAFIS_USERAUTHLOGCOLNAME;

  var stSysAdm = new ADMINTABLENAMESTRUCT;
  var stMc = new MOBILECASECOLNAME;	// mobile case
// query
var stQn = new COLQRYNAMESTRUCT;
  var stQt = new QUERYTABLENAMESTRUCT;

  // fifo queue
  var stFq = new COLFIFOQUESTRUCT;

  // TPLIB
  var stTt = new TPLIBTABLENAMESTRUCT;	// tp table names
var stLt = new LPLIBTABLENAMESTRUCT;	// lp table names

//  var stRmtTable = new RMTCOLNAMESTRUCT;	// remote table define,added by xia xinfeng on 2002.09.12

  var stNuminaCol = new NUMINAINNERCOLNAME;

  var stTPQueName = new TPDEFFIFOQUENAMESTRUCT;
  var stLPQueName = new LPDEFFIFOQUENAMESTRUCT;


  var stTCardText_50 = new TPCARDDEFTEXT_50;
  var stLCardText = new LPCARDDEFTEXT;
  var stLCaseText_50 = new LPCASEDEFTEXT_50;
  var stTPerText = new TPPERSONDEFTEXT;

  // TPCARD TEXT AND LPCASE TEXT. new definition.
  var stTCardText = new TPCARDDEFTEXT;
  var stLCaseText = new LPCASEDEFTEXT;

  var stCodeTable = new TABLENAME_CODETABLE;
  // newly added on Jun. 14, 2004
//  var stBkp = new BKP_COLNAME_ALL;

  // GAFIS_CASEADMINCOLNAME
  var stCaseAdm = new GAFIS_CASEADMINCOLNAME;

  var stAdmTable = new GAFIS_ADMINTABLENAME;

  @Length(4)
  var stVoice:Array[COLMICBNAMESTRUCT] = Range(0,4).map(x=>new COLMICBNAMESTRUCT).toArray	// 4 voices. for tp.

  var stLpVoice = new COLMICBNAMESTRUCT;
  var stLpFace = new COLMICBNAMESTRUCT;
  var stLVoiceID = new COLGENNAMESTRUCT;
  var stLFaceID = new COLGENNAMESTRUCT;
  var stLPVoiceText = new COLNAME_LPVOICETEXT;
  var stLPFaceText = new COLNAME_LPFACETEXT;

  var stDBRun = new COLNAME_DBRUNLOG;
  var stQualChk = new COLNAME_TPQUALCHK;	// quality check
var stQualChkLog = new COLNAME_TPQUALCHKWORKLOG;
  var stWorkLog = new COLNAME_WORKLOG;
  var stMntEditLog = new COLNAME_MNTEDITLOG;
  var stExfErrLog = new COLNAME_EXFERRLOG;
  var stQrySearchLog = new COLNAME_QUERYSEARCHLOG;
  var stOldQryCheckLog = new COLNAME_QUERYCHECKLOG_OLD;
  var stQryCheckLog = new COLNAME_QUERYCHECKLOG;
  var stQrySubmitLog = new COLNAME_QUERYSUBMITLOG;
  var stQryRecheckLog = new COLNAME_QUERYRECHECKLOG;
  var stLPGroup = new COLNAME_LPGROUP;
  var stCaseGroup = new COLNAME_CASEGROUP;
  var stTPLPAssociate = new COLNAME_TPLP_ASSOCIATE;
  var stTPUnmatch = new COLNAME_TP_UNMATCH;
  var stLPUnmatch = new COLNAME_LP_UNMATCH;
  var stTPLPUnmatch = new COLNAME_TPLP_UNMATCH;
  var stLPMultiMnt = new COLNAME_LP_MULTIMNT;
  var stTPLPFpxQue = new COLNAME_TPLP_FPXQUE_TABLE;
  var stTPLPFpxLog = new COLNAME_TPLP_FPXLOG_TABLE;
  var stQryFpxQue = new COLNAME_QUERY_FPXQUE_TABLE;
  var stQryFpxLog = new COLNAME_QUERY_FPXLOG_TABLE;
  var stLsnMgr = new COLNAME_LICENSEMGR;
  var stQryAssign = new COLNAME_QRYASSIGN;

  /**
    * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
    * added by beagle on Nov. 27, 2008
    */
  var stLFIC_DataTransmit = new COLNAME_LFIC_DATATRANSMITSTATUS;
  var stLFIC_Feedback = new COLNAME_LFIC_FEEDBACKINFO;

  /**
    * 定义人像查询队列表的列名
    * added by beagle on June. 22, 2009
    */
  var stFaceQryQue = new COLFACEQRYNAMESTRUCT;

  //!< 与公安部协查平台的互连、数据交换、日志信息表等
  var stGAFPTXC_Data = new GAFIS_GAFPTXCDATATABLECOLNAME;
  var stGAFPTXC_Log = new GAFIS_GAFPTXCLOGTABLECOLNAME;
  var stWanted_List = new GAFIS_WANTEDLISTTABLECOLNAME;		//!< 布控（追逃）人员表
var stWanted_TpCard = new GAFIS_WANTEDTPCARDTABLECOLNAME;
  var stWanted_OpLog = new GAFIS_WANTEDOPLOGTABLECOLNAME;

  //!<add by zyn 2014.08.08
  var stShxkDataStatus = new SHXK_DATASTATUSCOLNAME;
  var stShxkMatchInfo = new SHXK_MATCHINFOCOLNAME;
  var stShxkCaseStatus = new SHXK_CASESTATUSCOLNAME;
  var stShxkCaseText = new SHXK_CASETEXTCOLNAME;
  //!<add by nn 2014.09.28
  var stTTRealtion = new COLNAME_TT_RELATION;
  var stTTCandData = new COLNAME_TT_CAND;

  //!<add by wangkui at 2014.10.31
  var stXCData = new COLNAME_XCDATA;
  //!<add by wangkui at 2014.12.16
  var stXCReportData = new COLNAME_XCREPORTDATA;

  //!<add by zyn at 2014.10.21
  var stNjDelData = new NJ_DELDATACOLNAME;

  //add by fanjuanjuan 20160219
  var stLNHXn = new COLNAME_LNHXReportData;
} // COLNAMESTRUCT;



}




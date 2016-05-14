package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.IgnoreTransfer
import nirvana.hall.c.services.AncientData

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */

object glocname {
class COLNAME_TPLP_FPX_COMMON extends AncientData
{
  var pszFPXStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXStatus_Data:Array[Byte] = _ // for pszFPXStatus pointer ,struct:char;
var pszFPXIsForeign_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXIsForeign_Data:Array[Byte] = _ // for pszFPXIsForeign pointer ,struct:char;
var pszFPXForeignUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXForeignUnitCode_Data:Array[Byte] = _ // for pszFPXForeignUnitCode pointer ,struct:char;
var pszFPXPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXPurpose_Data:Array[Byte] = _ // for pszFPXPurpose pointer ,struct:char;
} // COLNAME_TPLP_FPX_COMMON;

// queries columns names
class COLQRYNAMESTRUCT extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszPriority_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszIsRmtQuery_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsRmtQuery_Data:Array[Byte] = _ // for pszIsRmtQuery pointer ,struct:char;
var pszStage_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStage_Data:Array[Byte] = _ // for pszStage pointer ,struct:char;
var pszFlagEx_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlagEx_Data:Array[Byte] = _ // for pszFlagEx pointer ,struct:char;
var pszDestDB_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDB_Data:Array[Byte] = _ // for pszDestDB pointer ,struct:char;
var pszSourceDB_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSourceDB_Data:Array[Byte] = _ // for pszSourceDB pointer ,struct:char;
var pszSubmitTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszFinishTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;
var pszStartLibID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartLibID_Data:Array[Byte] = _ // for pszStartLibID pointer ,struct:char;
var pszEndLibID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEndLibID_Data:Array[Byte] = _ // for pszEndLibID pointer ,struct:char;
var pszRmtAddTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtAddTime_Data:Array[Byte] = _ // for pszRmtAddTime pointer ,struct:char;
var pszTimeUsed_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;
var pszStartKey1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartKey1_Data:Array[Byte] = _ // for pszStartKey1 pointer ,struct:char;
var pszEndKey1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEndKey1_Data:Array[Byte] = _ // for pszEndKey1 pointer ,struct:char;
var pszStartKey2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartKey2_Data:Array[Byte] = _ // for pszStartKey2 pointer ,struct:char;
var pszEndKey2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEndKey2_Data:Array[Byte] = _ // for pszEndKey2 pointer ,struct:char;
var pszMaxCandNum_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMaxCandNum_Data:Array[Byte] = _ // for pszMaxCandNum pointer ,struct:char;
var pszCurCandNum_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurCandNum_Data:Array[Byte] = _ // for pszCurCandNum pointer ,struct:char;
var pszCandHead_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;
var pszCandList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;
var pszQryCondition_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryCondition_Data:Array[Byte] = _ // for pszQryCondition pointer ,struct:char;
var pszSrcDataMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcDataMnt_Data:Array[Byte] = _ // for pszSrcDataMnt pointer ,struct:char;
var pszSrcDataImage_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcDataImage_Data:Array[Byte] = _ // for pszSrcDataImage pointer ,struct:char;
var pszQueryID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryID_Data:Array[Byte] = _ // for pszQueryID pointer ,struct:char;
var pszCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszCheckTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtState_Data:Array[Byte] = _ // for pszRmtState pointer ,struct:char;
var pszMISState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISState_Data:Array[Byte] = _ // for pszMISState pointer ,struct:char;
var pszDestDBCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDBCount_Data:Array[Byte] = _ // for pszDestDBCount pointer ,struct:char;
var pszRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
var pszMIC_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMIC_Data:Array[Byte] = _ // for pszMIC pointer ,struct:char;
var pszMISCond_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISCond_Data:Array[Byte] = _ // for pszMISCond pointer ,struct:char;
var pszServerList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszServerList_Data:Array[Byte] = _ // for pszServerList pointer ,struct:char;
var pszTextSql_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTextSql_Data:Array[Byte] = _ // for pszTextSql pointer ,struct:char;
var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszReCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;
var pszVerifyPri_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyPri_Data:Array[Byte] = _ // for pszVerifyPri pointer ,struct:char;	//
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszFlag3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag3_Data:Array[Byte] = _ // for pszFlag3 pointer ,struct:char;
var pszFlag4_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag4_Data:Array[Byte] = _ // for pszFlag4 pointer ,struct:char;
var pszFlag5_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag5_Data:Array[Byte] = _ // for pszFlag5 pointer ,struct:char;
var pszFlag6_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag6_Data:Array[Byte] = _ // for pszFlag6 pointer ,struct:char;
var pszFlag7_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag7_Data:Array[Byte] = _ // for pszFlag7 pointer ,struct:char;
var pszFlag8_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag8_Data:Array[Byte] = _ // for pszFlag8 pointer ,struct:char;
var pszUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserUnitCode_Data:Array[Byte] = _ // for pszUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszMinScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMinScore_Data:Array[Byte] = _ // for pszMinScore pointer ,struct:char;
var pszSchCandCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSchCandCnt_Data:Array[Byte] = _ // for pszSchCandCnt pointer ,struct:char;
var pszComputerIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this ip.
var pszIsFofoQueQry_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsFofoQueQry_Data:Array[Byte] = _ // for pszIsFofoQueQry pointer ,struct:char;
var pszFifoQueSID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueSID_Data:Array[Byte] = _ // for pszFifoQueSID pointer ,struct:char;
var pszFifoQueDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueDBID_Data:Array[Byte] = _ // for pszFifoQueDBID pointer ,struct:char;
var pszFifoQueTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFifoQueTID_Data:Array[Byte] = _ // for pszFifoQueTID pointer ,struct:char;
//	char	*pszReserved;	// reserved.
// following columns are added on July 31, 2006
var pszQryUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryUID_Data:Array[Byte] = _ // for pszQryUID pointer ,struct:char;
var pszFPXStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXStatus_Data:Array[Byte] = _ // for pszFPXStatus pointer ,struct:char;
var pszFPXVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXVerifyResult_Data:Array[Byte] = _ // for pszFPXVerifyResult pointer ,struct:char;
var pszFPXVerifyUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXVerifyUnitCode_Data:Array[Byte] = _ // for pszFPXVerifyUnitCode pointer ,struct:char;
var pszFPXForeignTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXForeignTaskID_Data:Array[Byte] = _ // for pszFPXForeignTaskID pointer ,struct:char;
// above columns are added on July 31, 2006
var pszRecSearched_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecSearched_Data:Array[Byte] = _ // for pszRecSearched pointer ,struct:char;
} // COLQRYNAMESTRUCT;

class COLFIFOQUESTRUCT extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSourceDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSourceDBID_Data:Array[Byte] = _ // for pszSourceDBID pointer ,struct:char;	// source dbid.
var pszSourceTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSourceTID_Data:Array[Byte] = _ // for pszSourceTID pointer ,struct:char;	// source data file table id
var pszQueueType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueueType_Data:Array[Byte] = _ // for pszQueueType pointer ,struct:char;	// queue type
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;		// status
var pszCandHead_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;	// candidate head
var pszCandList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;	// candidate list
var pszItemFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszItemFlag_Data:Array[Byte] = _ // for pszItemFlag pointer ,struct:char;	// item flag
var pszTenString_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTenString_Data:Array[Byte] = _ // for pszTenString pointer ,struct:char;	// tenstring, only process those finger value is 1, not char '1'
var pszUseCPR_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUseCPR_Data:Array[Byte] = _ // for pszUseCPR pointer ,struct:char;		// if this value is true, then, we'll decompress image first then extract
// feature, then we'll not compress the image again
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;		// QueOption
var pszQueOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueOption_Data:Array[Byte] = _ // for pszQueOption pointer ,struct:char;	// QueOptionEx
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;	// query type.
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszFlagEx_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlagEx_Data:Array[Byte] = _ // for pszFlagEx pointer ,struct:char;
var pszQrySID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQrySID_Data:Array[Byte] = _ // for pszQrySID pointer ,struct:char;		// TQry sid.
var pszIsQrySubmitted_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsQrySubmitted_Data:Array[Byte] = _ // for pszIsQrySubmitted pointer ,struct:char;
var pszDestDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;
var pszDestTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestTID_Data:Array[Byte] = _ // for pszDestTID pointer ,struct:char;
var pszQryDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryDBID_Data:Array[Byte] = _ // for pszQryDBID pointer ,struct:char;
var pszQryTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryTID_Data:Array[Byte] = _ // for pszQryTID pointer ,struct:char;
var pszLQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLQueryType_Data:Array[Byte] = _ // for pszLQueryType pointer ,struct:char;
var pszLQrySID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLQrySID_Data:Array[Byte] = _ // for pszLQrySID pointer ,struct:char;
var pszDestLatDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestLatDBID_Data:Array[Byte] = _ // for pszDestLatDBID pointer ,struct:char;
var pszDestLatTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestLatTID_Data:Array[Byte] = _ // for pszDestLatTID pointer ,struct:char;

  var pszErrorCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszErrorCount_Data:Array[Byte] = _ // for pszErrorCount pointer ,struct:char;
} // COLFIFOQUESTRUCT;

/**
  * 人像查询队列表结构
  */
class COLFACEQRYNAMESTRUCT extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;			//!< 被查条码
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;		//!< 查询类型
var pszPriority_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;		//!< 优先级
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;		//!< 查中概率
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;			//!< 查询状态
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			//!< 查询标志

  var pszSourceDB_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSourceDB_Data:Array[Byte] = _ // for pszSourceDB pointer ,struct:char;		//!< 源数据库
var pszDestDB_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDB_Data:Array[Byte] = _ // for pszDestDB pointer ,struct:char;			//!< 目的数据库
var pszDestDBCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDBCount_Data:Array[Byte] = _ // for pszDestDBCount pointer ,struct:char;	//!< 目的数据库个数

  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;		//!< 查询提交者
var pszSubmitTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;		//!< 提交时间
var pszFinishTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;		//!< 完成时间
var pszTimeUsed_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;		//!< 用时
var pszCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;	//!< 认定用户
var pszCheckTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;		//!< 认定时间
var pszReCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;	//!< 复核用户
var pszReCheckDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;		//!< 复核时间
var pszUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserUnitCode_Data:Array[Byte] = _ // for pszUserUnitCode pointer ,struct:char;		//!< 提交用户单位代码
var pszCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;	//!< 认定用户单位代码
var pszReCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;	//!< 复核用户单位代码

  var pszMaxCandNum_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMaxCandNum_Data:Array[Byte] = _ // for pszMaxCandNum pointer ,struct:char;		//!< 最大候选个数
var pszCurCandNum_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurCandNum_Data:Array[Byte] = _ // for pszCurCandNum pointer ,struct:char;		//!< 当前候选个数
var pszCandHead_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandHead_Data:Array[Byte] = _ // for pszCandHead pointer ,struct:char;		//!< 候选头结构
var pszCandList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;		//!< 候选列表
var pszMIC_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMIC_Data:Array[Byte] = _ // for pszMIC pointer ,struct:char;			//!< 被查人像数据

  var pszQueryID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryID_Data:Array[Byte] = _ // for pszQueryID pointer ,struct:char;		//!< 查询ID
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;	//!< 核查结果
var pszMinScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMinScore_Data:Array[Byte] = _ // for pszMinScore pointer ,struct:char;		//!< 可以进入候选的最低得分

  var pszComputerIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this computer.

  var pszStartLibID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartLibID_Data:Array[Byte] = _ // for pszStartLibID pointer ,struct:char;
var pszEndLibID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEndLibID_Data:Array[Byte] = _ // for pszEndLibID pointer ,struct:char;
var pszStartKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartKey_Data:Array[Byte] = _ // for pszStartKey pointer ,struct:char;
var pszEndKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEndKey_Data:Array[Byte] = _ // for pszEndKey pointer ,struct:char;
} // COLFACEQRYNAMESTRUCT;


class COLMICBNAMESTRUCT extends AncientData
{
  var pszMntName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMntName_Data:Array[Byte] = _ // for pszMntName pointer ,struct:char;	// mnt name
var pszImgName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszImgName_Data:Array[Byte] = _ // for pszImgName pointer ,struct:char;	// image
var pszCprName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCprName_Data:Array[Byte] = _ // for pszCprName pointer ,struct:char;	// compressed
var pszBinName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBinName_Data:Array[Byte] = _ // for pszBinName pointer ,struct:char;	// binary
var pszFeatName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFeatName_Data:Array[Byte] = _ // for pszFeatName pointer ,struct:char;	// feature
} // COLMICBNAMESTRUCT;	// sizeof this structure is 20 or 40 bytes(depends on platform)

class COLGENNAMESTRUCT extends AncientData
{
  var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
} // COLGENNAMESTRUCT;

class TPDEFFIFOQUENAMESTRUCT extends AncientData
{
  var pszExfQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExfQue_Data:Array[Byte] = _ // for pszExfQue pointer ,struct:char;
var pszEditQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditQue_Data:Array[Byte] = _ // for pszEditQue pointer ,struct:char;
var pszTTSearchQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTSearchQue_Data:Array[Byte] = _ // for pszTTSearchQue pointer ,struct:char;
var pszTTCheckQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTCheckQue_Data:Array[Byte] = _ // for pszTTCheckQue pointer ,struct:char;
var pszTextInputQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTextInputQue_Data:Array[Byte] = _ // for pszTextInputQue pointer ,struct:char;
var pszTLSearchQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTLSearchQue_Data:Array[Byte] = _ // for pszTLSearchQue pointer ,struct:char;
var pszTLCheckQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTLCheckQue_Data:Array[Byte] = _ // for pszTLCheckQue pointer ,struct:char;
var pszEditQue2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditQue2_Data:Array[Byte] = _ // for pszEditQue2 pointer ,struct:char;
} // TPDEFFIFOQUENAMESTRUCT;

class LPDEFFIFOQUENAMESTRUCT extends AncientData
{
  var pszEditQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditQue_Data:Array[Byte] = _ // for pszEditQue pointer ,struct:char;
var pszLTSearchQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLTSearchQue_Data:Array[Byte] = _ // for pszLTSearchQue pointer ,struct:char;
var pszLTCheckQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLTCheckQue_Data:Array[Byte] = _ // for pszLTCheckQue pointer ,struct:char;
var pszExfQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExfQue_Data:Array[Byte] = _ // for pszExfQue pointer ,struct:char;
} // LPDEFFIFOQUENAMESTRUCT;


// tplib table name's every table has at least one child table
// dbid tableid and childtableid are all not in the same name space
// all child table ID is larger than 1000
// all table id is less than 1000
class TPLIBTABLENAMESTRUCT extends AncientData
{
  var pszTPCardTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPCardTable_Data:Array[Byte] = _ // for pszTPCardTable pointer ,struct:char;	// table
var pszFingerMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerMnt_Data:Array[Byte] = _ // for pszFingerMnt pointer ,struct:char;		// child table
var pszFingerFeat_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerFeat_Data:Array[Byte] = _ // for pszFingerFeat pointer ,struct:char;		// child table
var pszImgData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszImgData_Data:Array[Byte] = _ // for pszImgData pointer ,struct:char;		// child table
var pszCardText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardText_Data:Array[Byte] = _ // for pszCardText pointer ,struct:char;		// child table
var pszTPCardAdmin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPCardAdmin_Data:Array[Byte] = _ // for pszTPCardAdmin pointer ,struct:char;	// child table(admin table)
var pszPersonTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonTable_Data:Array[Byte] = _ // for pszPersonTable pointer ,struct:char;	// table
var pszPersonCard_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonCard_Data:Array[Byte] = _ // for pszPersonCard pointer ,struct:char;		// child table
var pszPersonText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonText_Data:Array[Byte] = _ // for pszPersonText pointer ,struct:char;		// child table
var pszLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// table(modification log)
var pszLogData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// (table)parameter/administration
var pszParamData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  var pszPalmMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmMnt_Data:Array[Byte] = _ // for pszPalmMnt pointer ,struct:char;		// table
var pszPalmMntAdmin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmMntAdmin_Data:Array[Byte] = _ // for pszPalmMntAdmin pointer ,struct:char;	// child table
var pszLeftPalmMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLeftPalmMnt_Data:Array[Byte] = _ // for pszLeftPalmMnt pointer ,struct:char;	// child table
var pszRightPalmMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRightPalmMnt_Data:Array[Byte] = _ // for pszRightPalmMnt pointer ,struct:char;	// child table
var pszPalmFeat_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmFeat_Data:Array[Byte] = _ // for pszPalmFeat pointer ,struct:char;		// child table.

  var pszPlainFinger_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPlainFinger_Data:Array[Byte] = _ // for pszPlainFinger pointer ,struct:char;	// child table.
var pszFaceMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFaceMnt_Data:Array[Byte] = _ // for pszFaceMnt pointer ,struct:char;		// child table.
var pszVoiceMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVoiceMnt_Data:Array[Byte] = _ // for pszVoiceMnt pointer ,struct:char;		// child table.
// table store that two records are not same.
var pszTPUnmatch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPUnmatch_Data:Array[Byte] = _ // for pszTPUnmatch pointer ,struct:char;		// table name.
var pszCTTPUnmatch_Ptr:Int = _ //using 4 byte as pointer
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
  var pszLatFinger_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatFinger_Data:Array[Byte] = _ // for pszLatFinger pointer ,struct:char;		// table
var pszLatFingerMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerMnt_Data:Array[Byte] = _ // for pszLatFingerMnt pointer ,struct:char;	// child table
var pszLatFingerText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerText_Data:Array[Byte] = _ // for pszLatFingerText pointer ,struct:char;	// child table
var pszLatFingerAdmin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatFingerAdmin_Data:Array[Byte] = _ // for pszLatFingerAdmin pointer ,struct:char;	// child table
var pszLatPalm_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatPalm_Data:Array[Byte] = _ // for pszLatPalm pointer ,struct:char;		// table
var pszLatPalmMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmMnt_Data:Array[Byte] = _ // for pszLatPalmMnt pointer ,struct:char;		// child table
var pszLatPalmText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmText_Data:Array[Byte] = _ // for pszLatPalmText pointer ,struct:char;	// child table
var pszLatPalmAdmin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatPalmAdmin_Data:Array[Byte] = _ // for pszLatPalmAdmin pointer ,struct:char;	// child table
var pszCase_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCase_Data:Array[Byte] = _ // for pszCase pointer ,struct:char;			// table
var pszCaseInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseInfo_Data:Array[Byte] = _ // for pszCaseInfo pointer ,struct:char;		// child table
var pszCaseText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseText_Data:Array[Byte] = _ // for pszCaseText pointer ,struct:char;		// child table
var pszLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// modification log table
var pszLogData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration table
var pszParamData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  // new added.
  var pszFace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFace_Data:Array[Byte] = _ // for pszFace pointer ,struct:char;			// table face.
var pszCTFace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTFace_Data:Array[Byte] = _ // for pszCTFace pointer ,struct:char;			// face child table.
var pszCTFaceText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTFaceText_Data:Array[Byte] = _ // for pszCTFaceText pointer ,struct:char;		// face text child table.
var pszCTFaceMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTFaceMnt_Data:Array[Byte] = _ // for pszCTFaceMnt pointer ,struct:char;		// face mnt child table.
var pszVoice_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVoice_Data:Array[Byte] = _ // for pszVoice pointer ,struct:char;			// voice table.
var pszCTVoice_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTVoice_Data:Array[Byte] = _ // for pszCTVoice pointer ,struct:char;		// voice child table.
var pszCTVoiceText_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTVoiceText_Data:Array[Byte] = _ // for pszCTVoiceText pointer ,struct:char;	// voice text table
var pszCTVoiceMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTVoiceMnt_Data:Array[Byte] = _ // for pszCTVoiceMnt pointer ,struct:char;

  // following are added on Aug. 9, 2006
  var pszLPGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroup_Data:Array[Byte] = _ // for pszLPGroup pointer ,struct:char;		// lat finger, palm, face, voice, etc group.
var pszCTLPGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTLPGroup_Data:Array[Byte] = _ // for pszCTLPGroup pointer ,struct:char;		// LPGroup child table.

  var pszCaseGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroup_Data:Array[Byte] = _ // for pszCaseGroup pointer ,struct:char;		// case group.
var pszCTCaseGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTCaseGroup_Data:Array[Byte] = _ // for pszCTCaseGroup pointer ,struct:char;	// child table for CaseGroup;

  var pszLPFingerUnmatch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPFingerUnmatch_Data:Array[Byte] = _ // for pszLPFingerUnmatch pointer ,struct:char;	// two lp finger are not same.
var pszCTLPFingerUnmatch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTLPFingerUnmatch_Data:Array[Byte] = _ // for pszCTLPFingerUnmatch pointer ,struct:char;	// child table.

  var pszLPPalmUnmatch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPPalmUnmatch_Data:Array[Byte] = _ // for pszLPPalmUnmatch pointer ,struct:char;
var pszCTLPPalmUnmatch_Ptr:Int = _ //using 4 byte as pointer
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
  var pszQueryTableName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryTableName_Data:Array[Byte] = _ // for pszQueryTableName pointer ,struct:char;	// table
var pszQueryQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryQue_Data:Array[Byte] = _ // for pszQueryQue pointer ,struct:char;		// child table
var pszLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogTable_Data:Array[Byte] = _ // for pszLogTable pointer ,struct:char;		// modification log table
var pszLogData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogData_Data:Array[Byte] = _ // for pszLogData pointer ,struct:char;		// child table
var pszParamTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration table
var pszParamData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table

  /**
    * 人像查询队列表
    */
  var pszFaceQueryTableName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFaceQueryTableName_Data:Array[Byte] = _ // for pszFaceQueryTableName pointer ,struct:char;	//!< table
var pszFaceQueryQue_Ptr:Int = _ //using 4 byte as pointer
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
  var pszUUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUUID_Data:Array[Byte] = _ // for pszUUID pointer ,struct:char;
var pszSID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSID_Data:Array[Byte] = _ // for pszSID pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszPrevSID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrevSID_Data:Array[Byte] = _ // for pszPrevSID pointer ,struct:char;
var pszNextSID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNextSID_Data:Array[Byte] = _ // for pszNextSID pointer ,struct:char;
var pszQueFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueFlag_Data:Array[Byte] = _ // for pszQueFlag pointer ,struct:char;
var pszCreator_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreator_Data:Array[Byte] = _ // for pszCreator pointer ,struct:char;
var pszUpdator_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdator_Data:Array[Byte] = _ // for pszUpdator pointer ,struct:char;
} // NUMINAINNERCOLNAME;

class ADMINTABLENAMESTRUCT extends AncientData
{
  var pszUserTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserTable_Data:Array[Byte] = _ // for pszUserTable pointer ,struct:char;		// table
var pszCTUserTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTUserTable_Data:Array[Byte] = _ // for pszCTUserTable pointer ,struct:char;	// child table
var pszSysMsgTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSysMsgTable_Data:Array[Byte] = _ // for pszSysMsgTable pointer ,struct:char;	// table
var pszCTSysMsgTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTSysMsgTable_Data:Array[Byte] = _ // for pszCTSysMsgTable pointer ,struct:char;	// child table
//char	*pszCodeTable;		// table, there are many code tables
//char	*pszCTCodeTable;	// child table
var pszBreakCaseTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBreakCaseTable_Data:Array[Byte] = _ // for pszBreakCaseTable pointer ,struct:char;	// table
var pszCTBreakCaseTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTBreakCaseTable_Data:Array[Byte] = _ // for pszCTBreakCaseTable pointer ,struct:char;	// child table
var pszSYSLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSYSLogTable_Data:Array[Byte] = _ // for pszSYSLogTable pointer ,struct:char;		// modification log table
var pszSYSLogData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSYSLogData_Data:Array[Byte] = _ // for pszSYSLogData pointer ,struct:char;		// child table
var pszDBLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBLogTable_Data:Array[Byte] = _ // for pszDBLogTable pointer ,struct:char;		// modification log table
var pszDBLogData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBLogData_Data:Array[Byte] = _ // for pszDBLogData pointer ,struct:char;		// child table
var pszParamTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamTable_Data:Array[Byte] = _ // for pszParamTable pointer ,struct:char;		// parameter/administration talbe
var pszParamData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;		// child table
var pszMobileCaseTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMobileCaseTable_Data:Array[Byte] = _ // for pszMobileCaseTable pointer ,struct:char;		// table
var pszMobileCaseData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMobileCaseData_Data:Array[Byte] = _ // for pszMobileCaseData pointer ,struct:char;			// child table
var pszDBPropTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBPropTable_Data:Array[Byte] = _ // for pszDBPropTable pointer ,struct:char;
var pszDBPropData_Ptr:Int = _ //using 4 byte as pointer
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
  var pszTableName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTableName_Data:Array[Byte] = _ // for pszTableName pointer ,struct:char;	// table name
var pszCTableName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCTableName_Data:Array[Byte] = _ // for pszCTableName pointer ,struct:char;	// child table name.
var pszComment_Ptr:Int = _ //using 4 byte as pointer
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
  var pszPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonID_Data:Array[Byte] = _ // for pszPersonID pointer ,struct:char;	// for duplicate card use only
var pszMISPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszScanCardConfigID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszScanCardConfigID_Data:Array[Byte] = _ // for pszScanCardConfigID pointer ,struct:char;
var pszDispCardConfigID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDispCardConfigID_Data:Array[Byte] = _ // for pszDispCardConfigID pointer ,struct:char;
var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszAccuTLCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAccuTLCount_Data:Array[Byte] = _ // for pszAccuTLCount pointer ,struct:char;
var pszAccuTTCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAccuTTCount_Data:Array[Byte] = _ // for pszAccuTTCount pointer ,struct:char;
var pszTLCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTLCount_Data:Array[Byte] = _ // for pszTLCount pointer ,struct:char;
var pszTTCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTCount_Data:Array[Byte] = _ // for pszTTCount pointer ,struct:char;
var pszEditCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditCount_Data:Array[Byte] = _ // for pszEditCount pointer ,struct:char;	// # of times edited
var pszPersonType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonType_Data:Array[Byte] = _ // for pszPersonType pointer ,struct:char;
var pszTLDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTLDate_Data:Array[Byte] = _ // for pszTLDate pointer ,struct:char;
var pszTTDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTDate_Data:Array[Byte] = _ // for pszTTDate pointer ,struct:char;
var pszTLUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTLUser_Data:Array[Byte] = _ // for pszTLUser pointer ,struct:char;
var pszTTUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTUser_Data:Array[Byte] = _ // for pszTTUser pointer ,struct:char;
var pszGroupName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupName_Data:Array[Byte] = _ // for pszGroupName pointer ,struct:char;
var pszGroupCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;	//
var pszPersonState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonState_Data:Array[Byte] = _ // for pszPersonState pointer ,struct:char;	// TPPERSONSTATE_XXX, unknown, free, detain, escaped, dead,
//	char	*pszTPAdminReserved;
// following columns are added on July. 31, 2006
var pszOrgScanner_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszRollDigitizeMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRollDigitizeMethod_Data:Array[Byte] = _ // for pszRollDigitizeMethod pointer ,struct:char;
var pszTPlainDigitizeMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainDigitizeMethod_Data:Array[Byte] = _ // for pszTPlainDigitizeMethod pointer ,struct:char;
var pszPalmDigitizeMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmDigitizeMethod_Data:Array[Byte] = _ // for pszPalmDigitizeMethod pointer ,struct:char;
  // above columns are added on July. 31, 2006

  //!< 数字化时间 added on Mar.18, 2009
  var pszDigitizedTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDigitizedTime_Data:Array[Byte] = _ // for pszDigitizedTime pointer ,struct:char;
} // TPADMINCOLNAME;

class TPCARDDEFTEXT_50 extends AncientData
{
  var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// 40
var pszNamePinYin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNamePinYin_Data:Array[Byte] = _ // for pszNamePinYin pointer ,struct:char;	// 40 name pinyin
var pszAlias_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;	// 40
var pszSex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSex_Data:Array[Byte] = _ // for pszSex pointer ,struct:char;	// 10
var pszSexCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;	// 1
var pszBirthDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;	// birthdate; 9, format 20021101
var pszShenFenID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			// 19
var pszHuKouPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlace_Data:Array[Byte] = _ // for pszHuKouPlace pointer ,struct:char;			// 70
var pszHuKouPlaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;		// 6
var pszAddressCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;		// 6
var pszAddress_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;			// 70
var pszPersonClass_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonClass_Data:Array[Byte] = _ // for pszPersonClass pointer ,struct:char;		// 2
var pszCaseClass1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1_Data:Array[Byte] = _ // for pszCaseClass1 pointer ,struct:char;			// 70	// GA 240. 1-2000
var pszCaseClass2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2_Data:Array[Byte] = _ // for pszCaseClass2 pointer ,struct:char;			// 70
var pszCaseClass3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3_Data:Array[Byte] = _ // for pszCaseClass3 pointer ,struct:char;			// 70

  var pszCaseClass1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;			// 8	// GA 240. 1-2000
var pszCaseClass2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;			// 8
var pszCaseClass3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;			// 8


  // for administrative
  var pszPrinterUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitCode_Data:Array[Byte] = _ // for pszPrinterUnitCode pointer ,struct:char;	// 10
var pszPrinterUnitName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitName_Data:Array[Byte] = _ // for pszPrinterUnitName pointer ,struct:char;	// 70
var pszPrinterName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterName_Data:Array[Byte] = _ // for pszPrinterName pointer ,struct:char;		// 40
var pszPrintDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrintDate_Data:Array[Byte] = _ // for pszPrintDate pointer ,struct:char;			// 9	// format 20021101
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// 512
} // TPCARDDEFTEXT_50;

class TPCARDDEFTEXT extends AncientData
{
  var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// 40
var pszNamePinYin_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNamePinYin_Data:Array[Byte] = _ // for pszNamePinYin pointer ,struct:char;	// 40 name pinyin
var pszAlias_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;	// 40
var pszSexCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;	// 2
var pszBirthDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;	// birthdate; 9, format 20021101
var pszShenFenID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			// 19
var pszHuKouPlaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;		// 6
var pszAddressCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;		// 6
var pszPersonClassCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonClassCode_Data:Array[Byte] = _ // for pszPersonClassCode pointer ,struct:char;	// 2
var pszPersonClassTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonClassTail_Data:Array[Byte] = _ // for pszPersonClassTail pointer ,struct:char;	// 24

  var pszCaseClass1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;			// 8	// GA 240. 1-2000
var pszCaseClass2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;			// 8
var pszCaseClass3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;			// 8

  // for administrative
  var pszPrinterUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitCode_Data:Array[Byte] = _ // for pszPrinterUnitCode pointer ,struct:char;	// 10
var pszPrinterName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterName_Data:Array[Byte] = _ // for pszPrinterName pointer ,struct:char;		// 40
var pszPrintDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrintDate_Data:Array[Byte] = _ // for pszPrintDate pointer ,struct:char;			// 9	// format 20021101
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// 512
var pszHitHistory_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  // for hukou place and some other place, it may contain more specific info
  // such Room602, West 4 ring road of Zhongguancun, Haidian district, Beijing.
  // so place only to haidian district, Beijing, so we need some place to
  // store other info.
  var pszAddressTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddressTail_Data:Array[Byte] = _ // for pszAddressTail pointer ,struct:char;	// 70
var pszHuKouPlaceTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceTail_Data:Array[Byte] = _ // for pszHuKouPlaceTail pointer ,struct:char;	// 70.
var pszPrinterUnitNameTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPrinterUnitNameTail_Data:Array[Byte] = _ // for pszPrinterUnitNameTail pointer ,struct:char;	// 50

  var pszCaseID1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID1_Data:Array[Byte] = _ // for pszCaseID1 pointer ,struct:char;		// case id one person may be connected with one case.
var pszCaseID2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID2_Data:Array[Byte] = _ // for pszCaseID2 pointer ,struct:char;		// allow 2
var pszShenFenIDTypeCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShenFenIDTypeCode_Data:Array[Byte] = _ // for pszShenFenIDTypeCode pointer ,struct:char;	//

  var pszMISConnectPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISConnectPersonID_Data:Array[Byte] = _ // for pszMISConnectPersonID pointer ,struct:char;

  // some newly added items
  var pszUnitNameCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnitNameCode_Data:Array[Byte] = _ // for pszUnitNameCode pointer ,struct:char;	// 16
var pszUnitNameTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnitNameTail_Data:Array[Byte] = _ // for pszUnitNameTail pointer ,struct:char;	// 70
var pszHeight_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHeight_Data:Array[Byte] = _ // for pszHeight pointer ,struct:char;			// 1, in cm
var pszFootLen_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFootLen_Data:Array[Byte] = _ // for pszFootLen pointer ,struct:char;		// 1, in cm
var pszRaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRaceCode_Data:Array[Byte] = _ // for pszRaceCode pointer ,struct:char;		// 6, nationality.
var pszBodyFeature_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBodyFeature_Data:Array[Byte] = _ // for pszBodyFeature pointer ,struct:char;	// 80;	body feature.
// for card scanner, user name has been recorded in tp card admin structure, but
// unitcode has not been logged. there is no space to place there, so we have to
// place it here.
var pszCreatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.
  // following are columns reserved.	for code used only.
  // we can split this columns later.
  //	char	*pszResserved1;		// 256 bytes reserved.

  // add on Feb. 19, 2008 by beagle
  var pszNationality_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNationality_Data:Array[Byte] = _ // for pszNationality pointer ,struct:char;		// 国籍, 6
var pszCertificateType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCertificateType_Data:Array[Byte] = _ // for pszCertificateType pointer ,struct:char;	// 证件类型, 6
var pszCertificateCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCertificateCode_Data:Array[Byte] = _ // for pszCertificateCode pointer ,struct:char;	// 证件号码, 32
var pszIsCriminalRecord_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsCriminalRecord_Data:Array[Byte] = _ // for pszIsCriminalRecord pointer ,struct:char;	// 前科标识, 1
var pszCriminalRecordDesc_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCriminalRecordDesc_Data:Array[Byte] = _ // for pszCriminalRecordDesc pointer ,struct:char;	// 前科情况描述, 1024

  /**
    * added by beagle on Dec. 14, 2008
    * 指纹信息采集系统用到:采集系统类型、活体指纹采集仪类型、指纹图像拼接软件类型、采集仪GA认证标志序列号、采集点编号
    * 采集时间 -- 对于活体采集也很有用，因为CreateTime对采集端和中心端会不一样，有了这个采集时间就可以比较上下级的卡片是否就是同一张卡片
    */
  var pszCaptureDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaptureDate_Data:Array[Byte] = _ // for pszCaptureDate pointer ,struct:char;	// 采集时间，14，YYYYMMDDHH24MMSS
var pszLFICSystemType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICSystemType_Data:Array[Byte] = _ // for pszLFICSystemType pointer ,struct:char;
var pszLFICScannerType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICScannerType_Data:Array[Byte] = _ // for pszLFICScannerType pointer ,struct:char;
var pszLFICMosaicType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICMosaicType_Data:Array[Byte] = _ // for pszLFICMosaicType pointer ,struct:char;
var pszLFICGASerial_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICGASerial_Data:Array[Byte] = _ // for pszLFICGASerial pointer ,struct:char;
var pszLFICCaptureCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICCaptureCode_Data:Array[Byte] = _ // for pszLFICCaptureCode pointer ,struct:char;

  //#ifdef	UTIL_20110309_NEW_COLUMN
  /**
    * added by beagle on March. 09, 2011
    * 增加一些人员信息
    */
  var pszDNASerial1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDNASerial1_Data:Array[Byte] = _ // for pszDNASerial1 pointer ,struct:char;		//!< 两个DNA序号
var pszDNASerial2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDNASerial2_Data:Array[Byte] = _ // for pszDNASerial2 pointer ,struct:char;

  /**
    * 一般老外姓名的表示：last name + middle name + first name；
    * 其中 last name + middle name 为名；first name 为姓
    * 又称为：教名+自取名+姓
    */
  var pszFamilyName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFamilyName_Data:Array[Byte] = _ // for pszFamilyName pointer ,struct:char;		//!< 老外的first name
var pszGivenName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGivenName_Data:Array[Byte] = _ // for pszGivenName pointer ,struct:char;		//!< 老外的last name
var pszMiddleName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMiddleName_Data:Array[Byte] = _ // for pszMiddleName pointer ,struct:char;		//!< 老外的middle name

  var pszBloodType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBloodType_Data:Array[Byte] = _ // for pszBloodType pointer ,struct:char;		//!< 血型
var pszWeight_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWeight_Data:Array[Byte] = _ // for pszWeight pointer ,struct:char;			//!< 体重
var pszAccent_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAccent_Data:Array[Byte] = _ // for pszAccent pointer ,struct:char;			//!< 口音
var pszEducation_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEducation_Data:Array[Byte] = _ // for pszEducation pointer ,struct:char;		//!< 教育程度
var pszOccupation_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOccupation_Data:Array[Byte] = _ // for pszOccupation pointer ,struct:char;		//!< 职业
var pszFigureType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFigureType_Data:Array[Byte] = _ // for pszFigureType pointer ,struct:char;		//!< 体型
var pszFaceType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFaceType_Data:Array[Byte] = _ // for pszFaceType pointer ,struct:char;		//!< 脸型
var pszBirthPlaceName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBirthPlaceName_Data:Array[Byte] = _ // for pszBirthPlaceName pointer ,struct:char;	//!< 出生地
var pszBirthPlaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBirthPlaceCode_Data:Array[Byte] = _ // for pszBirthPlaceCode pointer ,struct:char;	//!< 出生地代码
  //#endif

} // TPCARDDEFTEXT;


// for palm and finger
class LPCARDDEFTEXT extends AncientData
{
  var pszRidgeColor_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRidgeColor_Data:Array[Byte] = _ // for pszRidgeColor pointer ,struct:char;		// 1	// not standard
var pszRemainPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRemainPlace_Data:Array[Byte] = _ // for pszRemainPlace pointer ,struct:char;	// 30
var pszSeqNo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;			// 2, if latent finger id's encoding is standard then
// the last two digit is seqno.
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// comment, 128 bytes, not standard
var pszSenderFingerID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSenderFingerID_Data:Array[Byte] = _ // for pszSenderFingerID pointer ,struct:char;	// 20, ID of finger, for DOPS used only and for interchange

  var pszCreatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  /**
    * added on March. 10, 2011 by beagle
    * 未知名尸体标识和编号是针对现场卡片的，当初错加到了案件表里了
    */
  var pszIsUnknownBody_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsUnknownBody_Data:Array[Byte] = _ // for pszIsUnknownBody pointer ,struct:char;		// 未知名尸体标识, 1
var pszUnknownBodyCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnknownBodyCode_Data:Array[Byte] = _ // for pszUnknownBodyCode pointer ,struct:char;	// 未知名尸体编号, 32
var pszMntExtractMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMntExtractMethod_Data:Array[Byte] = _ // for pszMntExtractMethod pointer ,struct:char;	// 特征提取方法，代码
var pszGuessedFinger_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGuessedFinger_Data:Array[Byte] = _ // for pszGuessedFinger pointer ,struct:char;		//!< 分析指位。对应10个手指
var pszChainStartFinger_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChainStartFinger_Data:Array[Byte] = _ // for pszChainStartFinger pointer ,struct:char;	//!< 连指开始和结束序号
var pszChainEndFinger_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChainEndFinger_Data:Array[Byte] = _ // for pszChainEndFinger pointer ,struct:char;
} // LPCARDDEFTEXT;

class COLNAME_LPFACETEXT extends AncientData
{
  var pszSeqNo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// TEXT COLUMN

  var pszCreatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history
} // COLNAME_LPFACETEXT;

class COLNAME_LPVOICETEXT extends AncientData
{
  var pszSeqNo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSeqNo_Data:Array[Byte] = _ // for pszSeqNo pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;		// TEXT COLUMN

  var pszCreatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;	// place unit code.
var pszUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;	// place unit code.
var pszMicbUpdatorUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUserName_Data:Array[Byte] = _ // for pszMicbUpdatorUserName pointer ,struct:char;	// change only micb changed.
var pszMicbUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMicbUpdatorUnitCode_Data:Array[Byte] = _ // for pszMicbUpdatorUnitCode pointer ,struct:char;	// changed only micb changed.

  var pszCaptureMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaptureMethod_Data:Array[Byte] = _ // for pszCaptureMethod pointer ,struct:char;	// finger capture method.
var pszHitHistory_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history
} // COLNAME_LPVOICETEXT;

class LPCASEDEFTEXT_50 extends AncientData
{
  var pszCaseClass1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1_Data:Array[Byte] = _ // for pszCaseClass1 pointer ,struct:char;		// 8
var pszCaseClass2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2_Data:Array[Byte] = _ // for pszCaseClass2 pointer ,struct:char;		// 8
var pszCaseClass3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3_Data:Array[Byte] = _ // for pszCaseClass3 pointer ,struct:char;		// 8
var pszCaseClass1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;		// 70
var pszCaseClass2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;		// 70
var pszCaseClass3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;		// 70
var pszCaseOccurPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlace_Data:Array[Byte] = _ // for pszCaseOccurPlace pointer ,struct:char;	// 70
var pszCaseOccurDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurDate_Data:Array[Byte] = _ // for pszCaseOccurDate pointer ,struct:char;	// 9
var pszExtractUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitCode_Data:Array[Byte] = _ // for pszExtractUnitCode pointer ,struct:char;	// 10
var pszExtractUnitName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitName_Data:Array[Byte] = _ // for pszExtractUnitName pointer ,struct:char;	// 70
var pszSuperviseLevel_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuperviseLevel_Data:Array[Byte] = _ // for pszSuperviseLevel pointer ,struct:char;		// 2
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// comment, 512 bytes
var pszSuspiciousArea1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1_Data:Array[Byte] = _ // for pszSuspiciousArea1 pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2_Data:Array[Byte] = _ // for pszSuspiciousArea2 pointer ,struct:char;	// 6
var pszSuspiciousArea3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3_Data:Array[Byte] = _ // for pszSuspiciousArea3 pointer ,struct:char;	// 6
var pszSuspiciousArea1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1Code_Data:Array[Byte] = _ // for pszSuspiciousArea1Code pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2Code_Data:Array[Byte] = _ // for pszSuspiciousArea2Code pointer ,struct:char;	// 6
var pszSuspiciousArea3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3Code_Data:Array[Byte] = _ // for pszSuspiciousArea3Code pointer ,struct:char;	// 6
var pszIllicitMoney_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIllicitMoney_Data:Array[Byte] = _ // for pszIllicitMoney pointer ,struct:char;		// 10, unit is Yuan
var pszSenderCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSenderCardID_Data:Array[Byte] = _ // for pszSenderCardID pointer ,struct:char;		// id of latent card, used to store other systems
// info. not used by GAFIS. for interchange.
// when convert .fpt file to GAFIS we store card id in this item
var pszExtractor_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractor_Data:Array[Byte] = _ // for pszExtractor pointer ,struct:char;		// 30
} // LPCASEDEFTEXT_50;

// used by GAFIS 6.0
class LPCASEDEFTEXT extends AncientData
{
  var pszCaseClass1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass1Code_Data:Array[Byte] = _ // for pszCaseClass1Code pointer ,struct:char;		// 8
var pszCaseClass2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass2Code_Data:Array[Byte] = _ // for pszCaseClass2Code pointer ,struct:char;		// 8
var pszCaseClass3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClass3Code_Data:Array[Byte] = _ // for pszCaseClass3Code pointer ,struct:char;		// 8
var pszCaseOccurPlaceTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlaceTail_Data:Array[Byte] = _ // for pszCaseOccurPlaceTail pointer ,struct:char;	// 70
var pszCaseOccurDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurDate_Data:Array[Byte] = _ // for pszCaseOccurDate pointer ,struct:char;	// 9
var pszExtractUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitCode_Data:Array[Byte] = _ // for pszExtractUnitCode pointer ,struct:char;	// 10
var pszSuperviseLevel_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuperviseLevel_Data:Array[Byte] = _ // for pszSuperviseLevel pointer ,struct:char;		// 2
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;			// comment, 512 bytes，简要案情
var pszSuspiciousArea1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea1Code_Data:Array[Byte] = _ // for pszSuspiciousArea1Code pointer ,struct:char;	// 6 GB/T 2260-1999
var pszSuspiciousArea2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea2Code_Data:Array[Byte] = _ // for pszSuspiciousArea2Code pointer ,struct:char;	// 6
var pszSuspiciousArea3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSuspiciousArea3Code_Data:Array[Byte] = _ // for pszSuspiciousArea3Code pointer ,struct:char;	// 6
var pszIllicitMoney_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIllicitMoney_Data:Array[Byte] = _ // for pszIllicitMoney pointer ,struct:char;		// 10, unit is Yuan
var pszSenderCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSenderCardID_Data:Array[Byte] = _ // for pszSenderCardID pointer ,struct:char;		// id of latent card, used to store other systems
// info. not used by GAFIS. for interchange.
// when convert .fpt file to GAFIS we store card id in this item
var pszExtractor1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractor1_Data:Array[Byte] = _ // for pszExtractor1 pointer ,struct:char;		// 30
var pszExtractor2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractor2_Data:Array[Byte] = _ // for pszExtractor2 pointer ,struct:char;		// 30
var pszExtractor3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractor3_Data:Array[Byte] = _ // for pszExtractor3 pointer ,struct:char;		// 30

  // added on May 6 2004
  var pszExtractUnitNameTail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnitNameTail_Data:Array[Byte] = _ // for pszExtractUnitNameTail pointer ,struct:char;	// tail part, 50.
var pszCaseOccurPlaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseOccurPlaceCode_Data:Array[Byte] = _ // for pszCaseOccurPlaceCode pointer ,struct:char;	// head part.

  var pszPremium_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPremium_Data:Array[Byte] = _ // for pszPremium pointer ,struct:char;	// money will be pain for broken of this case.
var pszHitHistory_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitHistory_Data:Array[Byte] = _ // for pszHitHistory pointer ,struct:char;		// hit history

  var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorUnitCode_Data:Array[Byte] = _ // for pszCreatorUnitCode pointer ,struct:char;
var pszUpdatorUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorUnitCode_Data:Array[Byte] = _ // for pszUpdatorUnitCode pointer ,struct:char;

  // add on Feb. 19, 2008 by beagle
  var pszIsUnknownBody_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsUnknownBody_Data:Array[Byte] = _ // for pszIsUnknownBody pointer ,struct:char;		// 未知名尸体标识, 1
var pszUnknownBodyCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnknownBodyCode_Data:Array[Byte] = _ // for pszUnknownBodyCode pointer ,struct:char;	// 未知名尸体编号, 32
var pszExtractDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractDate_Data:Array[Byte] = _ // for pszExtractDate pointer ,struct:char;		// 提取日期, 9
var pszCaseState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseState_Data:Array[Byte] = _ // for pszCaseState pointer ,struct:char;			// 案件状态, 4

} // LPCASEDEFTEXT;


class TPPERSONDEFTEXT extends AncientData
{
  var pszOperator_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOperator_Data:Array[Byte] = _ // for pszOperator pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszCreatorName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreatorName_Data:Array[Byte] = _ // for pszCreatorName pointer ,struct:char;
var pszUpdatorName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdatorName_Data:Array[Byte] = _ // for pszUpdatorName pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszUpdateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszCreateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszLPGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;	// group id for lp.
var pszLPGroupDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupDBID_Data:Array[Byte] = _ // for pszLPGroupDBID pointer ,struct:char;
var pszLPGroupTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupTID_Data:Array[Byte] = _ // for pszLPGroupTID pointer ,struct:char;		// group table id.
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
} // TPPERSONDEFTEXT;


class LPADMINCOLNAME extends AncientData
{
  var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonID_Data:Array[Byte] = _ // for pszPersonID pointer ,struct:char;
var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszAccuLTCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAccuLTCount_Data:Array[Byte] = _ // for pszAccuLTCount pointer ,struct:char;
var pszAccuLLCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAccuLLCount_Data:Array[Byte] = _ // for pszAccuLLCount pointer ,struct:char;
var pszLTCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLTCount_Data:Array[Byte] = _ // for pszLTCount pointer ,struct:char;
var pszLLCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLLCount_Data:Array[Byte] = _ // for pszLLCount pointer ,struct:char;
var pszEditCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditCount_Data:Array[Byte] = _ // for pszEditCount pointer ,struct:char;	// # of times edited
var pszFingerType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerType_Data:Array[Byte] = _ // for pszFingerType pointer ,struct:char;
var pszLTDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLTDate_Data:Array[Byte] = _ // for pszLTDate pointer ,struct:char;
var pszLLDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLLDate_Data:Array[Byte] = _ // for pszLLDate pointer ,struct:char;
var pszLTUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLTUser_Data:Array[Byte] = _ // for pszLTUser pointer ,struct:char;
var pszLLUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLLUser_Data:Array[Byte] = _ // for pszLLUser pointer ,struct:char;
var pszGroupName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupName_Data:Array[Byte] = _ // for pszGroupName pointer ,struct:char;
var pszGroupCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszIsBroken_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsBroken_Data:Array[Byte] = _ // for pszIsBroken pointer ,struct:char;
var pszBrokenUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUser_Data:Array[Byte] = _ // for pszBrokenUser pointer ,struct:char;
var pszBrokenUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUnitCode_Data:Array[Byte] = _ // for pszBrokenUnitCode pointer ,struct:char;
var pszBrokenDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenDate_Data:Array[Byte] = _ // for pszBrokenDate pointer ,struct:char;
var pszReChecker_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReChecker_Data:Array[Byte] = _ // for pszReChecker pointer ,struct:char;
var pszIsLTBroken_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsLTBroken_Data:Array[Byte] = _ // for pszIsLTBroken pointer ,struct:char;	// may be lt or tl.
var pszMntExtractMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMntExtractMethod_Data:Array[Byte] = _ // for pszMntExtractMethod pointer ,struct:char;	//
var pszHitPersonState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPersonState_Data:Array[Byte] = _ // for pszHitPersonState pointer ,struct:char;		//
//	char	*pszAdminReserved;
// following columns are added on July 31, 2006
var pszOrgScanner_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszDigitizeMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDigitizeMethod_Data:Array[Byte] = _ // for pszDigitizeMethod pointer ,struct:char;
// above columns are added on July 31, 2006
var pszFgGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup_Data:Array[Byte] = _ // for pszFgGroup pointer ,struct:char;	// finger group. when doing affiliate of lp finger, we mark finger belong to
// same person's same finger(unknown exist finger position) with same fggroup.
var pszFgIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFgIndex_Data:Array[Byte] = _ // for pszFgIndex pointer ,struct:char;	// finger index. global. known finger position.
} // LPADMINCOLNAME;

class MOBILECASECOLNAME extends AncientData
{
  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszBinData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBinData_Data:Array[Byte] = _ // for pszBinData pointer ,struct:char;
} // MOBILECASECOLNAME;

// all system will have a prefix SYS_
class GAFIS_USERTABLECOLNAME extends AncientData
{
  var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;	// user name
var pszFullName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFullName_Data:Array[Byte] = _ // for pszFullName pointer ,struct:char;	// full user name
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
var pszAddress_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;
var pszPhone_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPhone_Data:Array[Byte] = _ // for pszPhone pointer ,struct:char;
var pszMail_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMail_Data:Array[Byte] = _ // for pszMail pointer ,struct:char;
var pszPostCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPostCode_Data:Array[Byte] = _ // for pszPostCode pointer ,struct:char;
var pszBinData1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBinData1_Data:Array[Byte] = _ // for pszBinData1 pointer ,struct:char;	// misc data
var pszBinData2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBinData2_Data:Array[Byte] = _ // for pszBinData2 pointer ,struct:char;	// right data
var pszFingerData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerData_Data:Array[Byte] = _ // for pszFingerData pointer ,struct:char;	/// finger data
var pszNuminaPriv_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNuminaPriv_Data:Array[Byte] = _ // for pszNuminaPriv pointer ,struct:char;
var pszIsGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsGroup_Data:Array[Byte] = _ // for pszIsGroup pointer ,struct:char;	// whether is group.

  // added by beagle on July.10, 2008
  var pszLoginIPMask_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLoginIPMask_Data:Array[Byte] = _ // for pszLoginIPMask pointer ,struct:char;

  //!< added by beagle on march.16, 2011
  var pszOrgID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgID_Data:Array[Byte] = _ // for pszOrgID pointer ,struct:char;	//!< 用户所在机构ID
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;	//!< 数据最后修改时间
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;	//!< 创建时间
} // GAFIS_USERTABLECOLNAME;

class GAFIS_MSGTABLECOLNAME extends AncientData
{
  var pszSender_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSender_Data:Array[Byte] = _ // for pszSender pointer ,struct:char;	// sender
var pszTitle_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTitle_Data:Array[Byte] = _ // for pszTitle pointer ,struct:char;
var pszSendDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSendDateTime_Data:Array[Byte] = _ // for pszSendDateTime pointer ,struct:char;
var pszContent_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszContent_Data:Array[Byte] = _ // for pszContent pointer ,struct:char;
var pszAttach_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAttach_Data:Array[Byte] = _ // for pszAttach pointer ,struct:char;
var pszState_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszState_Data:Array[Byte] = _ // for pszState pointer ,struct:char;
var pszImportance_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszImportance_Data:Array[Byte] = _ // for pszImportance pointer ,struct:char;
} // GAFIS_MSGTABLECOLNAME;

class GAFIS_PARAMTABLECOLNAME extends AncientData
{
  var pszParamName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamName_Data:Array[Byte] = _ // for pszParamName pointer ,struct:char;
var pszParamValue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamValue_Data:Array[Byte] = _ // for pszParamValue pointer ,struct:char;	// for small data object
var pszParamData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszParamData_Data:Array[Byte] = _ // for pszParamData pointer ,struct:char;	// for large data object
} // GAFIS_PARAMTABLECOLNAME;

class GAFIS_CODETABLECOLNAME extends AncientData
{
  var pszCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCode_Data:Array[Byte] = _ // for pszCode pointer ,struct:char;
var pszInputCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszInputCode_Data:Array[Byte] = _ // for pszInputCode pointer ,struct:char;
var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
} // GAFIS_CODETABLECOLNAME;

// break case table.
class GAFIS_BCTABLECOLNAME extends AncientData
{
  var pszBreakID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBreakID_Data:Array[Byte] = _ // for pszBreakID pointer ,struct:char;	// key of the action
var pszQueryTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryTaskID_Data:Array[Byte] = _ // for pszQueryTaskID pointer ,struct:char;	// query task id.

  var pszSrcKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcKey_Data:Array[Byte] = _ // for pszSrcKey pointer ,struct:char;
var pszDestKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestKey_Data:Array[Byte] = _ // for pszDestKey pointer ,struct:char;
var pszScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszScore_Data:Array[Byte] = _ // for pszScore pointer ,struct:char;
var pszRank_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRank_Data:Array[Byte] = _ // for pszRank pointer ,struct:char;
var pszFg_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFg_Data:Array[Byte] = _ // for pszFg pointer ,struct:char;	// globale image index.
var pszSrcPersonCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcPersonCaseID_Data:Array[Byte] = _ // for pszSrcPersonCaseID pointer ,struct:char;
var pszDestPersonCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestPersonCaseID_Data:Array[Byte] = _ // for pszDestPersonCaseID pointer ,struct:char;

  var pszCaseClassCode1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode1_Data:Array[Byte] = _ // for pszCaseClassCode1 pointer ,struct:char;
var pszCaseClassCode2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode2_Data:Array[Byte] = _ // for pszCaseClassCode2 pointer ,struct:char;
var pszCaseClassCode3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode3_Data:Array[Byte] = _ // for pszCaseClassCode3 pointer ,struct:char;

  var pszFirstRankScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstRankScore_Data:Array[Byte] = _ // for pszFirstRankScore pointer ,struct:char;
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszIsRemoteSearched_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsRemoteSearched_Data:Array[Byte] = _ // for pszIsRemoteSearched pointer ,struct:char;
var pszSearchingUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSearchingUnitCode_Data:Array[Byte] = _ // for pszSearchingUnitCode pointer ,struct:char;
var pszIsCrimeCaptured_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsCrimeCaptured_Data:Array[Byte] = _ // for pszIsCrimeCaptured pointer ,struct:char;
var pszFgType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFgType_Data:Array[Byte] = _ // for pszFgType pointer ,struct:char;	// finger, palm or tplain.

  var pszSrcMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcMnt_Data:Array[Byte] = _ // for pszSrcMnt pointer ,struct:char;
var pszDestMnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestMnt_Data:Array[Byte] = _ // for pszDestMnt pointer ,struct:char;
var pszSrcImg_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcImg_Data:Array[Byte] = _ // for pszSrcImg pointer ,struct:char;
var pszDestImg_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestImg_Data:Array[Byte] = _ // for pszDestImg pointer ,struct:char;
var pszSrcInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcInfo_Data:Array[Byte] = _ // for pszSrcInfo pointer ,struct:char;
var pszDestInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestInfo_Data:Array[Byte] = _ // for pszDestInfo pointer ,struct:char;
var pszSrcDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcDBID_Data:Array[Byte] = _ // for pszSrcDBID pointer ,struct:char;
var pszDestDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;

  var pszTotoalMatchedCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotoalMatchedCnt_Data:Array[Byte] = _ // for pszTotoalMatchedCnt pointer ,struct:char;

  // submit info.
  var pszSubmitUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszSubmitUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszSubmitDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;

  // break user info.
  var pszBreakUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBreakUserName_Data:Array[Byte] = _ // for pszBreakUserName pointer ,struct:char;
var pszBreakDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBreakDateTime_Data:Array[Byte] = _ // for pszBreakDateTime pointer ,struct:char;
var pszBreakUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBreakUserUnitCode_Data:Array[Byte] = _ // for pszBreakUserUnitCode pointer ,struct:char;

  // checker user info.
  var pszReCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDate_Data:Array[Byte] = _ // for pszReCheckDate pointer ,struct:char;
var pszReCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;

  var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszComment1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment1_Data:Array[Byte] = _ // for pszComment1 pointer ,struct:char;
var pszComment2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment2_Data:Array[Byte] = _ // for pszComment2 pointer ,struct:char;
var pszReserved1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReserved1_Data:Array[Byte] = _ // for pszReserved1 pointer ,struct:char;
var pszReserved2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReserved2_Data:Array[Byte] = _ // for pszReserved2 pointer ,struct:char;
var pszReserved3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReserved3_Data:Array[Byte] = _ // for pszReserved3 pointer ,struct:char;

  var pszNotUsedA_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNotUsedA_Data:Array[Byte] = _ // for pszNotUsedA pointer ,struct:char;
var pszNotUsedB_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNotUsedB_Data:Array[Byte] = _ // for pszNotUsedB pointer ,struct:char;
} // GAFIS_BCTABLECOLNAME;

class GAFIS_DTPROPTABLECOLNAME extends AncientData
{
  var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
var pszData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_DTPROPTABLECOLNAME;

class GAFIS_DBMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTableID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTableID_Data:Array[Byte] = _ // for pszTableID pointer ,struct:char;
var pszModDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszOperation_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOperation_Data:Array[Byte] = _ // for pszOperation pointer ,struct:char;
var pszTableType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTableType_Data:Array[Byte] = _ // for pszTableType pointer ,struct:char;
var pszData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
var pszNotUsed_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNotUsed_Data:Array[Byte] = _ // for pszNotUsed pointer ,struct:char;
} // GAFIS_DBMOGLOGTABLECOLNAME;

class GAFIS_SYSMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszModDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_SYSMODLOGTABLECOLNAME;

class GAFIS_USERAUTHLOGCOLNAME extends AncientData
{
  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszLoginDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLoginDateTime_Data:Array[Byte] = _ // for pszLoginDateTime pointer ,struct:char;
var pszLogoutDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLogoutDateTime_Data:Array[Byte] = _ // for pszLogoutDateTime pointer ,struct:char;
var pszLoginID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLoginID_Data:Array[Byte] = _ // for pszLoginID pointer ,struct:char;
var pszIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIP_Data:Array[Byte] = _ // for pszIP pointer ,struct:char;
var pszComputer_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputer_Data:Array[Byte] = _ // for pszComputer pointer ,struct:char;
var pszIsValidUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsValidUser_Data:Array[Byte] = _ // for pszIsValidUser pointer ,struct:char;
var pszModuleID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszModuleID_Data:Array[Byte] = _ // for pszModuleID pointer ,struct:char;
var pszRes_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRes_Data:Array[Byte] = _ // for pszRes pointer ,struct:char;
} // GAFIS_USERAUTHLOGCOLNAME;

class GAFIS_QRYMODLOGTABLECOLNAME extends AncientData
{
  var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszModDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszModDateTime_Data:Array[Byte] = _ // for pszModDateTime pointer ,struct:char;
var pszData_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszData_Data:Array[Byte] = _ // for pszData pointer ,struct:char;
} // GAFIS_QRYMODLOGTABLECOLNAME;

class GAFIS_CASEADMINCOLNAME extends AncientData
{
  var pszIsBroken_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsBroken_Data:Array[Byte] = _ // for pszIsBroken pointer ,struct:char;
var pszBrokenUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUser_Data:Array[Byte] = _ // for pszBrokenUser pointer ,struct:char;
var pszReChecker_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReChecker_Data:Array[Byte] = _ // for pszReChecker pointer ,struct:char;
var pszBrokenUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenUnitCode_Data:Array[Byte] = _ // for pszBrokenUnitCode pointer ,struct:char;
var pszBrokenDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenDate_Data:Array[Byte] = _ // for pszBrokenDate pointer ,struct:char;
var pszHasPersonKilled_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHasPersonKilled_Data:Array[Byte] = _ // for pszHasPersonKilled pointer ,struct:char;	// in this case whether has people killed.
var pszPersonKilledCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonKilledCount_Data:Array[Byte] = _ // for pszPersonKilledCount pointer ,struct:char;	// how many people killed.
var pszIsLTBroken_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsLTBroken_Data:Array[Byte] = _ // for pszIsLTBroken pointer ,struct:char;			// may be lt or tl.
var pszGroupCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupCode_Data:Array[Byte] = _ // for pszGroupCode pointer ,struct:char;
var pszFaceIDCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFaceIDCount_Data:Array[Byte] = _ // for pszFaceIDCount pointer ,struct:char;
var pszFaceIDList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFaceIDList_Data:Array[Byte] = _ // for pszFaceIDList pointer ,struct:char;
var pszVoiceIDCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVoiceIDCount_Data:Array[Byte] = _ // for pszVoiceIDCount pointer ,struct:char;
var pszVoiceIDList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVoiceIDList_Data:Array[Byte] = _ // for pszVoiceIDList pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszUpdateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszCreateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
//	char	*pszCaseAdminReserved;	// reserved column
var pszCaseGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroupID_Data:Array[Byte] = _ // for pszCaseGroupID pointer ,struct:char;	// case group id.
var pszOrgScanner_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanner_Data:Array[Byte] = _ // for pszOrgScanner pointer ,struct:char;
var pszOrgScanUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgScanUnitCode_Data:Array[Byte] = _ // for pszOrgScanUnitCode pointer ,struct:char;
var pszOrgAFISType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOrgAFISType_Data:Array[Byte] = _ // for pszOrgAFISType pointer ,struct:char;
var pszItemKeyList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszItemKeyList_Data:Array[Byte] = _ // for pszItemKeyList pointer ,struct:char;
var pszMISConnectCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISConnectCaseID_Data:Array[Byte] = _ // for pszMISConnectCaseID pointer ,struct:char;

  var pszQryAssignChecker_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryAssignChecker_Data:Array[Byte] = _ // for pszQryAssignChecker pointer ,struct:char;
var pszQryAssignHasChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryAssignHasChecked_Data:Array[Byte] = _ // for pszQryAssignHasChecked pointer ,struct:char;

  //fan:add 添加以下3个字段
  var pszAJID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAJID_Data:Array[Byte] = _ // for pszAJID pointer ,struct:char;
var pszXKID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXKID_Data:Array[Byte] = _ // for pszXKID pointer ,struct:char;
var pszJQID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszJQID_Data:Array[Byte] = _ // for pszJQID pointer ,struct:char;
} // GAFIS_CASEADMINCOLNAME;	//

class GAFIS_ADMINTABLENAME extends AncientData
{
  var pszQueExf_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueExf_Data:Array[Byte] = _ // for pszQueExf pointer ,struct:char;
var pszQueEdit_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueEdit_Data:Array[Byte] = _ // for pszQueEdit pointer ,struct:char;
var pszQueTextInput_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueTextInput_Data:Array[Byte] = _ // for pszQueTextInput pointer ,struct:char;
/*
	// not used, we using querytype id in que.
  var pszQueLTSearch_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLTSearch_Data:Array[Byte] = _ // for pszQueLTSearch pointer ,struct:char;
  var pszQueLTCheck_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLTCheck_Data:Array[Byte] = _ // for pszQueLTCheck pointer ,struct:char;
  var pszQueLLSearch_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLLSearch_Data:Array[Byte] = _ // for pszQueLLSearch pointer ,struct:char;
  var pszQueLLCheck_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueLLCheck_Data:Array[Byte] = _ // for pszQueLLCheck pointer ,struct:char;
  var pszQueTTSearch_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTTSearch_Data:Array[Byte] = _ // for pszQueTTSearch pointer ,struct:char;
  var pszQueTTCheck_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTTCheck_Data:Array[Byte] = _ // for pszQueTTCheck pointer ,struct:char;
  var pszQueTLSearch_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTLSearch_Data:Array[Byte] = _ // for pszQueTLSearch pointer ,struct:char;
  var pszQueTLCheck_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszQueTLCheck_Data:Array[Byte] = _ // for pszQueTLCheck pointer ,struct:char;
*/
var pszDBModLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBModLog_Data:Array[Byte] = _ // for pszDBModLog pointer ,struct:char;
var pszQueSearch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueSearch_Data:Array[Byte] = _ // for pszQueSearch pointer ,struct:char;
var pszQueCheck_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueCheck_Data:Array[Byte] = _ // for pszQueCheck pointer ,struct:char;

  var pszUserAuthLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserAuthLog_Data:Array[Byte] = _ // for pszUserAuthLog pointer ,struct:char;
var pszDBRunLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBRunLog_Data:Array[Byte] = _ // for pszDBRunLog pointer ,struct:char;
var pszQualCheck_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQualCheck_Data:Array[Byte] = _ // for pszQualCheck pointer ,struct:char;
var pszWorkLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWorkLog_Data:Array[Byte] = _ // for pszWorkLog pointer ,struct:char;
var pszMntEditLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMntEditLog_Data:Array[Byte] = _ // for pszMntEditLog pointer ,struct:char;
var pszExfErrLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExfErrLog_Data:Array[Byte] = _ // for pszExfErrLog pointer ,struct:char;
var pszQualCheckLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQualCheckLog_Data:Array[Byte] = _ // for pszQualCheckLog pointer ,struct:char;
var pszQrySearchLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQrySearchLog_Data:Array[Byte] = _ // for pszQrySearchLog pointer ,struct:char;
var pszQryCheckLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryCheckLog_Data:Array[Byte] = _ // for pszQryCheckLog pointer ,struct:char;
var pszQualCheckQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQualCheckQue_Data:Array[Byte] = _ // for pszQualCheckQue pointer ,struct:char;

  var pszQrySubmitLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQrySubmitLog_Data:Array[Byte] = _ // for pszQrySubmitLog pointer ,struct:char;
var pszQryReCheckLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryReCheckLog_Data:Array[Byte] = _ // for pszQryReCheckLog pointer ,struct:char;
var pszTPLPAssociate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPLPAssociate_Data:Array[Byte] = _ // for pszTPLPAssociate pointer ,struct:char;
var pszTPLPUnmatch_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPLPUnmatch_Data:Array[Byte] = _ // for pszTPLPUnmatch pointer ,struct:char;
var pszTPLPFpxQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPLPFpxQue_Data:Array[Byte] = _ // for pszTPLPFpxQue pointer ,struct:char;
var pszTPLPFpxLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPLPFpxLog_Data:Array[Byte] = _ // for pszTPLPFpxLog pointer ,struct:char;
var pszQryFpxQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryFpxQue_Data:Array[Byte] = _ // for pszQryFpxQue pointer ,struct:char;
var pszQryFpxLog_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryFpxLog_Data:Array[Byte] = _ // for pszQryFpxLog pointer ,struct:char;
var pszLsnTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLsnTable_Data:Array[Byte] = _ // for pszLsnTable pointer ,struct:char;

  var pszQryCheckAssignTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryCheckAssignTable_Data:Array[Byte] = _ // for pszQryCheckAssignTable pointer ,struct:char;

  /**
    * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
    * added by beagle on Nov. 27, 2008
    */
  var pszLFICDTStatusTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICDTStatusTable_Data:Array[Byte] = _ // for pszLFICDTStatusTable pointer ,struct:char;
var pszLFICFBInfoTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLFICFBInfoTable_Data:Array[Byte] = _ // for pszLFICFBInfoTable pointer ,struct:char;

  /**
    * 公安部协查平台及缉控人员表
    * [11/23/2011 xia_xinfeng]
    */
  var pszGAXCTaskTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGAXCTaskTable_Data:Array[Byte] = _ // for pszGAXCTaskTable pointer ,struct:char;
var pszGAXCLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGAXCLogTable_Data:Array[Byte] = _ // for pszGAXCLogTable pointer ,struct:char;
var pszWantedListTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWantedListTable_Data:Array[Byte] = _ // for pszWantedListTable pointer ,struct:char;
var pszWantedTPCardTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWantedTPCardTable_Data:Array[Byte] = _ // for pszWantedTPCardTable pointer ,struct:char;
var pszWantedLogTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWantedLogTable_Data:Array[Byte] = _ // for pszWantedLogTable pointer ,struct:char;

  //!<add by zyn at 2014.08.11 for shanghai xk
  var pszShxkDataStatusTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShxkDataStatusTable_Data:Array[Byte] = _ // for pszShxkDataStatusTable pointer ,struct:char;
var pszShxkMatchInfoTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShxkMatchInfoTable_Data:Array[Byte] = _ // for pszShxkMatchInfoTable pointer ,struct:char;
var pszShxkCaseStatusTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShxkCaseStatusTable_Data:Array[Byte] = _ // for pszShxkCaseStatusTable pointer ,struct:char;
var pszShxkCaseTextTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShxkCaseTextTable_Data:Array[Byte] = _ // for pszShxkCaseTextTable pointer ,struct:char;

  //!<add by nn at 2014.9.23 for nanjing
  var pszTTRelationTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTRelationTable_Data:Array[Byte] = _ // for pszTTRelationTable pointer ,struct:char;
var pszTTCandidateTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTTCandidateTable_Data:Array[Byte] = _ // for pszTTCandidateTable pointer ,struct:char;

  //!<add by zyn at 2014.10.21 for nj
  var pszNjDelDataTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNjDelDataTable_Data:Array[Byte] = _ // for pszNjDelDataTable pointer ,struct:char;

  //!<add by wangkui
  var pszXCDataTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCDataTable_Data:Array[Byte] = _ // for pszXCDataTable pointer ,struct:char;
var pszXCReportTable_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCReportTable_Data:Array[Byte] = _ // for pszXCReportTable pointer ,struct:char;

  //!<add by fanjuan 20160219
  var pszLNHXReportDataTable_Ptr:Int = _ //using 4 byte as pointer
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
  var pszStartupTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStartupTime_Data:Array[Byte] = _ // for pszStartupTime pointer ,struct:char;
var pszShutdownTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShutdownTime_Data:Array[Byte] = _ // for pszShutdownTime pointer ,struct:char;
var pszShutdownUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShutdownUser_Data:Array[Byte] = _ // for pszShutdownUser pointer ,struct:char;
var pszUserIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserIP_Data:Array[Byte] = _ // for pszUserIP pointer ,struct:char;
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// name of computer.
var pszSvrVersion_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSvrVersion_Data:Array[Byte] = _ // for pszSvrVersion pointer ,struct:char;		// version string.
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} // COLNAME_DBRUNLOG;

// when remote user transmit card to central system
// the central system may enforce a restriction on
// tenprint card quality
class COLNAME_TPQUALCHK extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszInputUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszInputUnitCode_Data:Array[Byte] = _ // for pszInputUnitCode pointer ,struct:char;
var pszAddTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddTime_Data:Array[Byte] = _ // for pszAddTime pointer ,struct:char;
var pszChkTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChkTime_Data:Array[Byte] = _ // for pszChkTime pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
//char	*pszComment;
var pszQueSID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueSID_Data:Array[Byte] = _ // for pszQueSID pointer ,struct:char;
var pszChecker_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChecker_Data:Array[Byte] = _ // for pszChecker pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;	// which database the card is in.
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszDownloadTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDownloadTime_Data:Array[Byte] = _ // for pszDownloadTime pointer ,struct:char;
var pszFingerIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszTQrySID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTQrySID_Data:Array[Byte] = _ // for pszTQrySID pointer ,struct:char;
var pszLQrySID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLQrySID_Data:Array[Byte] = _ // for pszLQrySID pointer ,struct:char;
var pszQryDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryDBID_Data:Array[Byte] = _ // for pszQryDBID pointer ,struct:char;
var pszQryTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryTID_Data:Array[Byte] = _ // for pszQryTID pointer ,struct:char;
var pszDestTenDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestTenDBID_Data:Array[Byte] = _ // for pszDestTenDBID pointer ,struct:char;
var pszDestTenTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestTenTID_Data:Array[Byte] = _ // for pszDestTenTID pointer ,struct:char;
var pszDestLatDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestLatDBID_Data:Array[Byte] = _ // for pszDestLatDBID pointer ,struct:char;
var pszDestLatTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestLatTID_Data:Array[Byte] = _ // for pszDestLatTID pointer ,struct:char;
} // COLNAME_TPQUALCHK;

class COLNAME_TPQUALCHKWORKLOG extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszInputUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszInputUnitCode_Data:Array[Byte] = _ // for pszInputUnitCode pointer ,struct:char;
var pszChkTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChkTime_Data:Array[Byte] = _ // for pszChkTime pointer ,struct:char;
var pszChecker_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszChecker_Data:Array[Byte] = _ // for pszChecker pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;	// which database the card is in.
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszNeedRescan_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszNeedRescan_Data:Array[Byte] = _ // for pszNeedRescan pointer ,struct:char;
var pszFingerIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
} // COLNAME_TPQUALCHKWORKLOG;

class COLNAME_WORKLOG extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;		// source key.
var pszWorkType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWorkType_Data:Array[Byte] = _ // for pszWorkType pointer ,struct:char;	// work type.
var pszWorkClass_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWorkClass_Data:Array[Byte] = _ // for pszWorkClass pointer ,struct:char;	// work class
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;	// user name.
var pszDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;	// data time
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFingerImgChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerImgChanged_Data:Array[Byte] = _ // for pszFingerImgChanged pointer ,struct:char;	// finger image changed.
var pszFingerMntChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerMntChanged_Data:Array[Byte] = _ // for pszFingerMntChanged pointer ,struct:char;	// mnt changed.
var pszPalmImgChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmImgChanged_Data:Array[Byte] = _ // for pszPalmImgChanged pointer ,struct:char;
var pszPalmMntChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmMntChanged_Data:Array[Byte] = _ // for pszPalmMntChanged pointer ,struct:char;
var pszTextChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTextChanged_Data:Array[Byte] = _ // for pszTextChanged pointer ,struct:char;
var pszTPlainImgChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainImgChanged_Data:Array[Byte] = _ // for pszTPlainImgChanged pointer ,struct:char;
var pszTPlainMntChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainMntChanged_Data:Array[Byte] = _ // for pszTPlainMntChanged pointer ,struct:char;
var pszOtherChanged_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOtherChanged_Data:Array[Byte] = _ // for pszOtherChanged pointer ,struct:char;
var pszIsTwoFaceCard_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsTwoFaceCard_Data:Array[Byte] = _ // for pszIsTwoFaceCard pointer ,struct:char;

  var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszFingerCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerCnt_Data:Array[Byte] = _ // for pszFingerCnt pointer ,struct:char;
var pszFingerHQCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerHQCnt_Data:Array[Byte] = _ // for pszFingerHQCnt pointer ,struct:char;
var pszTPlainCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainCnt_Data:Array[Byte] = _ // for pszTPlainCnt pointer ,struct:char;
var pszTPlainHQCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainHQCnt_Data:Array[Byte] = _ // for pszTPlainHQCnt pointer ,struct:char;
var pszPalmCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmCnt_Data:Array[Byte] = _ // for pszPalmCnt pointer ,struct:char;
var pszPalmHQCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmHQCnt_Data:Array[Byte] = _ // for pszPalmHQCnt pointer ,struct:char;
var pszEditTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEditTime_Data:Array[Byte] = _ // for pszEditTime pointer ,struct:char;

} // COLNAME_WORKLOG;

// minutia correction statistics for tp used only.
class COLNAME_MNTEDITLOG extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;	// date time
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFingerEditInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerEditInfo_Data:Array[Byte] = _ // for pszFingerEditInfo pointer ,struct:char;
var pszTPlainEditInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPlainEditInfo_Data:Array[Byte] = _ // for pszTPlainEditInfo pointer ,struct:char;
var pszPalmEditInfo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPalmEditInfo_Data:Array[Byte] = _ // for pszPalmEditInfo pointer ,struct:char;
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
} // COLNAME_MNTEDITLOG;

class COLNAME_EXFERRLOG extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDateTime_Data:Array[Byte] = _ // for pszDateTime pointer ,struct:char;
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszErrorCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszErrorCode_Data:Array[Byte] = _ // for pszErrorCode pointer ,struct:char;
var pszRetValue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRetValue_Data:Array[Byte] = _ // for pszRetValue pointer ,struct:char;
var pszMntFormat_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMntFormat_Data:Array[Byte] = _ // for pszMntFormat pointer ,struct:char;
var pszFingerIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFingerIndex_Data:Array[Byte] = _ // for pszFingerIndex pointer ,struct:char;
var pszSecondFingerIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSecondFingerIndex_Data:Array[Byte] = _ // for pszSecondFingerIndex pointer ,struct:char;
var pszCompressMethod_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCompressMethod_Data:Array[Byte] = _ // for pszCompressMethod pointer ,struct:char;
var pszDllName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDllName_Data:Array[Byte] = _ // for pszDllName pointer ,struct:char;
var pszDllVersion_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDllVersion_Data:Array[Byte] = _ // for pszDllVersion pointer ,struct:char;
var pszExfAppVersion_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExfAppVersion_Data:Array[Byte] = _ // for pszExfAppVersion pointer ,struct:char;
} // COLNAME_EXFERRLOG;

class COLNAME_QUERYSEARCHLOG extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszTimeUsed_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTimeUsed_Data:Array[Byte] = _ // for pszTimeUsed pointer ,struct:char;
var pszDBRecCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBRecCount_Data:Array[Byte] = _ // for pszDBRecCount pointer ,struct:char;
var pszFirstCandScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;	// first candidate score.
var pszFirstCandKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandKey_Data:Array[Byte] = _ // for pszFirstCandKey pointer ,struct:char;	// first candidate key.
var pszSubmitDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;	// submit date time.
var pszFinishDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFinishDateTime_Data:Array[Byte] = _ // for pszFinishDateTime pointer ,struct:char;	// when finished searching.
var pszSubmitUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
/*
  var pszDBID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
  var pszTID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
  var pszDestDBID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDestDBID_Data:Array[Byte] = _ // for pszDestDBID pointer ,struct:char;
  var pszDestTID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszDestTID_Data:Array[Byte] = _ // for pszDestTID pointer ,struct:char;
*/
var pszIsFifoQue_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsFifoQue_Data:Array[Byte] = _ // for pszIsFifoQue pointer ,struct:char;
var pszRecSearchedCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecSearchedCnt_Data:Array[Byte] = _ // for pszRecSearchedCnt pointer ,struct:char;	// # of records searched.
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			// flag.
var pszRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;		// remote flag
var pszFinalCandCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFinalCandCnt_Data:Array[Byte] = _ // for pszFinalCandCnt pointer ,struct:char;	// final candidate count.
var pszQUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszMICCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMICCount_Data:Array[Byte] = _ // for pszMICCount pointer ,struct:char;
var pszPriority_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
} // COLNAME_QUERYSEARCHLOG;	// query search log.

class COLNAME_QUERYCHECKLOG_OLD extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSubmitUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszCheckWorkType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckWorkType_Data:Array[Byte] = _ // for pszCheckWorkType pointer ,struct:char;	// check, recheck.
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszIsRmtQuery_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsRmtQuery_Data:Array[Byte] = _ // for pszIsRmtQuery pointer ,struct:char;
var pszSubmitTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszFinishTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFinishTime_Data:Array[Byte] = _ // for pszFinishTime pointer ,struct:char;
var pszCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszCheckDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszReCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszReCheckDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDateTime_Data:Array[Byte] = _ // for pszReCheckDateTime pointer ,struct:char;
var pszSubmitUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszComputerIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;		// query is sending from this ip.
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;	// query is sending from this ip.
var pszIsFofoQueQry_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsFofoQueQry_Data:Array[Byte] = _ // for pszIsFofoQueQry pointer ,struct:char;
var pszFirstCandScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;
var pszFirstCandKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandKey_Data:Array[Byte] = _ // for pszFirstCandKey pointer ,struct:char;
var pszHitCandScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitCandScore_Data:Array[Byte] = _ // for pszHitCandScore pointer ,struct:char;
var pszHitCandKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitCandKey_Data:Array[Byte] = _ // for pszHitCandKey pointer ,struct:char;
var pszCandCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;	// candidate count.
var pszHitCandIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitCandIndex_Data:Array[Byte] = _ // for pszHitCandIndex pointer ,struct:char;
} // COLNAME_QUERYCHECKLOG_OLD;

class COLNAME_QUERYSUBMITLOG extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszSubmitUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserName_Data:Array[Byte] = _ // for pszSubmitUserName pointer ,struct:char;
var pszSubmitTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszSubmitUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCandCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszIsFifoQueQry_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsFifoQueQry_Data:Array[Byte] = _ // for pszIsFifoQueQry pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtFlag_Data:Array[Byte] = _ // for pszRmtFlag pointer ,struct:char;
//	char	*pszIsRmtQuery;
var pszComputerIP_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerIP_Data:Array[Byte] = _ // for pszComputerIP pointer ,struct:char;
var pszComputerName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComputerName_Data:Array[Byte] = _ // for pszComputerName pointer ,struct:char;
var pszQryUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryUID_Data:Array[Byte] = _ // for pszQryUID pointer ,struct:char;
var pszMICCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMICCount_Data:Array[Byte] = _ // for pszMICCount pointer ,struct:char;
var pszPriority_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPriority_Data:Array[Byte] = _ // for pszPriority pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;	// submit query status.
} // COLNAME_QUERYSUBMITLOG;


class COLNAME_QUERYCHECKLOG extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszQUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszFirstCandScore_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFirstCandScore_Data:Array[Byte] = _ // for pszFirstCandScore pointer ,struct:char;
var pszCandCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandCount_Data:Array[Byte] = _ // for pszCandCount pointer ,struct:char;	// candidate count.
var pszSearchFinishTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSearchFinishTime_Data:Array[Byte] = _ // for pszSearchFinishTime pointer ,struct:char;
var pszCheckDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszSubmitUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszHitPoss_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitPoss_Data:Array[Byte] = _ // for pszHitPoss pointer ,struct:char;
var pszCheckResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckResult_Data:Array[Byte] = _ // for pszCheckResult pointer ,struct:char;
var pszQryFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryFlag_Data:Array[Byte] = _ // for pszQryFlag pointer ,struct:char;
var pszQryRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryRmtFlag_Data:Array[Byte] = _ // for pszQryRmtFlag pointer ,struct:char;
var pszHitCandCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitCandCnt_Data:Array[Byte] = _ // for pszHitCandCnt pointer ,struct:char;
} // COLNAME_QUERYCHECKLOG;


class COLNAME_QUERYRECHECKLOG extends AncientData
{
  var pszKeyID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyID_Data:Array[Byte] = _ // for pszKeyID pointer ,struct:char;
var pszQUID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQUID_Data:Array[Byte] = _ // for pszQUID pointer ,struct:char;
var pszCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckUserName_Data:Array[Byte] = _ // for pszCheckUserName pointer ,struct:char;
var pszReCheckUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckUserName_Data:Array[Byte] = _ // for pszReCheckUserName pointer ,struct:char;
var pszCheckDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckDateTime_Data:Array[Byte] = _ // for pszCheckDateTime pointer ,struct:char;
var pszReCheckDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckDateTime_Data:Array[Byte] = _ // for pszReCheckDateTime pointer ,struct:char;
var pszSubmitUserUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUserUnitCode_Data:Array[Byte] = _ // for pszSubmitUserUnitCode pointer ,struct:char;
var pszCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckerUnitCode_Data:Array[Byte] = _ // for pszCheckerUnitCode pointer ,struct:char;
var pszReCheckerUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReCheckerUnitCode_Data:Array[Byte] = _ // for pszReCheckerUnitCode pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRecordType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszQryFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryFlag_Data:Array[Byte] = _ // for pszQryFlag pointer ,struct:char;
var pszQryRmtFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryRmtFlag_Data:Array[Byte] = _ // for pszQryRmtFlag pointer ,struct:char;
var pszHitCandCnt_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHitCandCnt_Data:Array[Byte] = _ // for pszHitCandCnt pointer ,struct:char;
} // COLNAME_QUERYRECHECKLOG;

class COLNAME_LPGROUP extends AncientData
{
  var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
var pszKeyList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyList_Data:Array[Byte] = _ // for pszKeyList pointer ,struct:char;
var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
} // COLNAME_LPGROUP;

class COLNAME_CASEGROUP extends AncientData
{
  var pszCaseGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseGroupID_Data:Array[Byte] = _ // for pszCaseGroupID pointer ,struct:char;
var pszKeyList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKeyList_Data:Array[Byte] = _ // for pszKeyList pointer ,struct:char;
var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
} // COLNAME_CASEGROUP;

class COLNAME_TPLP_ASSOCIATE extends AncientData
{
  var pszTPPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPPersonID_Data:Array[Byte] = _ // for pszTPPersonID pointer ,struct:char;
var pszLPGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;
var pszCreateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUserName_Data:Array[Byte] = _ // for pszCreateUserName pointer ,struct:char;
var pszCreateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateDateTime_Data:Array[Byte] = _ // for pszCreateDateTime pointer ,struct:char;
var pszCreateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUnitCode_Data:Array[Byte] = _ // for pszCreateUnitCode pointer ,struct:char;
var pszUpdateUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUserName_Data:Array[Byte] = _ // for pszUpdateUserName pointer ,struct:char;
var pszUpdateDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateDateTime_Data:Array[Byte] = _ // for pszUpdateDateTime pointer ,struct:char;
var pszUpdateUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUnitCode_Data:Array[Byte] = _ // for pszUpdateUnitCode pointer ,struct:char;
var pszIdentifyUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
var pszIdentifyUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUnitCode_Data:Array[Byte] = _ // for pszIdentifyUnitCode pointer ,struct:char;
var pszTPDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPDBID_Data:Array[Byte] = _ // for pszTPDBID pointer ,struct:char;
var pszTPTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPTID_Data:Array[Byte] = _ // for pszTPTID pointer ,struct:char;
var pszLPDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPDBID_Data:Array[Byte] = _ // for pszLPDBID pointer ,struct:char;
var pszLPTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPTID_Data:Array[Byte] = _ // for pszLPTID pointer ,struct:char;
} // COLNAME_TPLP_ASSOCIATE;

class COLNAME_TPLP_UNMATCH extends AncientData
{
  var pszTPPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPPersonID_Data:Array[Byte] = _ // for pszTPPersonID pointer ,struct:char;
var pszLPGroupID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPGroupID_Data:Array[Byte] = _ // for pszLPGroupID pointer ,struct:char;
var pszLatFgGroup_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatFgGroup_Data:Array[Byte] = _ // for pszLatFgGroup pointer ,struct:char;
var pszLatKeyType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatKeyType_Data:Array[Byte] = _ // for pszLatKeyType pointer ,struct:char;
var pszTPIndex_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPIndex_Data:Array[Byte] = _ // for pszTPIndex pointer ,struct:char;
var pszIdentifyUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
var pszTPDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPDBID_Data:Array[Byte] = _ // for pszTPDBID pointer ,struct:char;
var pszTPTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTPTID_Data:Array[Byte] = _ // for pszTPTID pointer ,struct:char;
var pszLPDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPDBID_Data:Array[Byte] = _ // for pszLPDBID pointer ,struct:char;
var pszLPTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLPTID_Data:Array[Byte] = _ // for pszLPTID pointer ,struct:char;
} // COLNAME_TPLP_UNMATCH;

class COLNAME_TP_UNMATCH extends AncientData
{
  var pszKey1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey1_Data:Array[Byte] = _ // for pszKey1 pointer ,struct:char;
var pszKey2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey2_Data:Array[Byte] = _ // for pszKey2 pointer ,struct:char;
var pszIdentifyUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
} // COLNAME_TP_UNMATCH;

class COLNAME_LP_UNMATCH extends AncientData
{
  var pszKey1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey1_Data:Array[Byte] = _ // for pszKey1 pointer ,struct:char;
var pszFgGroup1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup1_Data:Array[Byte] = _ // for pszFgGroup1 pointer ,struct:char;
var pszKey2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey2_Data:Array[Byte] = _ // for pszKey2 pointer ,struct:char;
var pszFgGroup2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFgGroup2_Data:Array[Byte] = _ // for pszFgGroup2 pointer ,struct:char;
var pszIdentifyUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyUserName_Data:Array[Byte] = _ // for pszIdentifyUserName pointer ,struct:char;
var pszIdentifyDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIdentifyDateTime_Data:Array[Byte] = _ // for pszIdentifyDateTime pointer ,struct:char;
} // COLNAME_LP_UNMATCH;


class COLNAME_LP_MULTIMNT extends AncientData
{
  // first group.
  var pszMnt1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt1_Data:Array[Byte] = _ // for pszMnt1 pointer ,struct:char;
var pszBin1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin1_Data:Array[Byte] = _ // for pszBin1 pointer ,struct:char;
  /*
  var pszUpdateUser1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
*/

  // second group.
  var pszMnt2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt2_Data:Array[Byte] = _ // for pszMnt2 pointer ,struct:char;
var pszBin2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin2_Data:Array[Byte] = _ // for pszBin2 pointer ,struct:char;
  /*
  var pszUpdateUser2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser2_Data:Array[Byte] = _ // for pszUpdateUser2 pointer ,struct:char;
  var pszUpdateUnitCode2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode2_Data:Array[Byte] = _ // for pszUpdateUnitCode2 pointer ,struct:char;
  var pszUpdateDateTime2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime2_Data:Array[Byte] = _ // for pszUpdateDateTime2 pointer ,struct:char;
*/

  // third group.
  var pszMnt3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt3_Data:Array[Byte] = _ // for pszMnt3 pointer ,struct:char;
var pszBin3_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin3_Data:Array[Byte] = _ // for pszBin3 pointer ,struct:char;
  /*
  var pszUpdateUser3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser3_Data:Array[Byte] = _ // for pszUpdateUser3 pointer ,struct:char;
  var pszUpdateUnitCode3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode3_Data:Array[Byte] = _ // for pszUpdateUnitCode3 pointer ,struct:char;
  var pszUpdateDateTime3_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime3_Data:Array[Byte] = _ // for pszUpdateDateTime3 pointer ,struct:char;
*/

  // fourth group.
  var pszMnt4_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt4_Data:Array[Byte] = _ // for pszMnt4 pointer ,struct:char;
var pszBin4_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin4_Data:Array[Byte] = _ // for pszBin4 pointer ,struct:char;
  /*
  var pszUpdateUser4_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser4_Data:Array[Byte] = _ // for pszUpdateUser4 pointer ,struct:char;
  var pszUpdateUnitCode4_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode4_Data:Array[Byte] = _ // for pszUpdateUnitCode4 pointer ,struct:char;
  var pszUpdateDateTime4_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime4_Data:Array[Byte] = _ // for pszUpdateDateTime4 pointer ,struct:char;
*/

  // fifth group.
  var pszMnt5_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt5_Data:Array[Byte] = _ // for pszMnt5 pointer ,struct:char;
var pszBin5_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin5_Data:Array[Byte] = _ // for pszBin5 pointer ,struct:char;
/*
  var pszUpdateUser5_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser5_Data:Array[Byte] = _ // for pszUpdateUser5 pointer ,struct:char;
  var pszUpdateUnitCode5_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode5_Data:Array[Byte] = _ // for pszUpdateUnitCode5 pointer ,struct:char;
  var pszUpdateDateTime5_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime5_Data:Array[Byte] = _ // for pszUpdateDateTime5 pointer ,struct:char;
*/
var pszMnt6_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt6_Data:Array[Byte] = _ // for pszMnt6 pointer ,struct:char;
var pszBin6_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin6_Data:Array[Byte] = _ // for pszBin6 pointer ,struct:char;
  /*
  var pszUpdateUser1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt7_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt7_Data:Array[Byte] = _ // for pszMnt7 pointer ,struct:char;
var pszBin7_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin7_Data:Array[Byte] = _ // for pszBin7 pointer ,struct:char;
  /*
  var pszUpdateUser1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt8_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt8_Data:Array[Byte] = _ // for pszMnt8 pointer ,struct:char;
var pszBin8_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin8_Data:Array[Byte] = _ // for pszBin8 pointer ,struct:char;
  /*
  var pszUpdateUser1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/

  var pszMnt9_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMnt9_Data:Array[Byte] = _ // for pszMnt9 pointer ,struct:char;
var pszBin9_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBin9_Data:Array[Byte] = _ // for pszBin9 pointer ,struct:char;
  /*
  var pszUpdateUser1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUser1_Data:Array[Byte] = _ // for pszUpdateUser1 pointer ,struct:char;
  var pszUpdateUnitCode1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateUnitCode1_Data:Array[Byte] = _ // for pszUpdateUnitCode1 pointer ,struct:char;
  var pszUpdateDateTime1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszUpdateDateTime1_Data:Array[Byte] = _ // for pszUpdateDateTime1 pointer ,struct:char;
	*/


} // COLNAME_LP_MULTIMNT;


class COLNAME_TPLP_FPXQUE_TABLE extends AncientData
{
  var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDataType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDataType_Data:Array[Byte] = _ // for pszDataType pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszLastOpTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLastOpTime_Data:Array[Byte] = _ // for pszLastOpTime pointer ,struct:char;
var pszLastOpUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLastOpUser_Data:Array[Byte] = _ // for pszLastOpUser pointer ,struct:char;
var pszSubmitUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
var pszSubmitDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;
var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszPersonIDCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonIDCaseID_Data:Array[Byte] = _ // for pszPersonIDCaseID pointer ,struct:char;
var pszPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPurpose_Data:Array[Byte] = _ // for pszPurpose pointer ,struct:char;
var pszFPXComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXComment_Data:Array[Byte] = _ // for pszFPXComment pointer ,struct:char;
var pszAFISComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAFISComment_Data:Array[Byte] = _ // for pszAFISComment pointer ,struct:char;
var pszRmtUploadStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadStatus_Data:Array[Byte] = _ // for pszRmtUploadStatus pointer ,struct:char;
var pszRmtDownloadStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadStatus_Data:Array[Byte] = _ // for pszRmtDownloadStatus pointer ,struct:char;
var pszRmtUploadDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadDateTime_Data:Array[Byte] = _ // for pszRmtUploadDateTime pointer ,struct:char;
var pszRmtDownloadDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadDateTime_Data:Array[Byte] = _ // for pszRmtDownloadDateTime pointer ,struct:char;
} // COLNAME_TPLP_FPXQUE_TABLE;

class COLNAME_TPLP_FPXLOG_TABLE extends AncientData
{
  var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDataType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDataType_Data:Array[Byte] = _ // for pszDataType pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszOpTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOpTime_Data:Array[Byte] = _ // for pszOpTime pointer ,struct:char;
var pszOpUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOpUser_Data:Array[Byte] = _ // for pszOpUser pointer ,struct:char;
var pszSubmitUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnitCode_Data:Array[Byte] = _ // for pszSubmitUnitCode pointer ,struct:char;
var pszSubmitDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitDateTime_Data:Array[Byte] = _ // for pszSubmitDateTime pointer ,struct:char;
var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszPersonIDCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPersonIDCaseID_Data:Array[Byte] = _ // for pszPersonIDCaseID pointer ,struct:char;
var pszPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszPurpose_Data:Array[Byte] = _ // for pszPurpose pointer ,struct:char;
} // COLNAME_TPLP_FPXLOG_TABLE;

class COLNAME_QUERY_FPXQUE_TABLE extends AncientData
{
  var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszRecordType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszLastOpTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLastOpTime_Data:Array[Byte] = _ // for pszLastOpTime pointer ,struct:char;
var pszLastOpUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLastOpUser_Data:Array[Byte] = _ // for pszLastOpUser pointer ,struct:char;
var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszRequestTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestTime_Data:Array[Byte] = _ // for pszRequestTime pointer ,struct:char;
var pszRequestUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestUser_Data:Array[Byte] = _ // for pszRequestUser pointer ,struct:char;
var pszRequestUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestUnitCode_Data:Array[Byte] = _ // for pszRequestUnitCode pointer ,struct:char;
var pszTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;
var pszForeignUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszForeignUnitCode_Data:Array[Byte] = _ // for pszForeignUnitCode pointer ,struct:char;
var pszFPXComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFPXComment_Data:Array[Byte] = _ // for pszFPXComment pointer ,struct:char;
var pszAFISComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAFISComment_Data:Array[Byte] = _ // for pszAFISComment pointer ,struct:char;
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
var pszRmtUploadStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadStatus_Data:Array[Byte] = _ // for pszRmtUploadStatus pointer ,struct:char;
var pszRmtDownloadStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadStatus_Data:Array[Byte] = _ // for pszRmtDownloadStatus pointer ,struct:char;
var pszRmtUploadDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtUploadDateTime_Data:Array[Byte] = _ // for pszRmtUploadDateTime pointer ,struct:char;
var pszRmtDownloadDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRmtDownloadDateTime_Data:Array[Byte] = _ // for pszRmtDownloadDateTime pointer ,struct:char;
} // COLNAME_QUERY_FPXQUE_TABLE;

class COLNAME_QUERY_FPXLOG_TABLE extends AncientData
{
  var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszRecordType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecordType_Data:Array[Byte] = _ // for pszRecordType pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;
var pszOption_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOption_Data:Array[Byte] = _ // for pszOption pointer ,struct:char;
var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszForeignUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszForeignUnitCode_Data:Array[Byte] = _ // for pszForeignUnitCode pointer ,struct:char;
var pszRequestTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestTime_Data:Array[Byte] = _ // for pszRequestTime pointer ,struct:char;
var pszRequestUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestUser_Data:Array[Byte] = _ // for pszRequestUser pointer ,struct:char;
var pszRequestUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRequestUnitCode_Data:Array[Byte] = _ // for pszRequestUnitCode pointer ,struct:char;
var pszTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;
var pszVerifyResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszVerifyResult_Data:Array[Byte] = _ // for pszVerifyResult pointer ,struct:char;
} // COLNAME_QUERY_FPXLOG_TABLE;

class COLNAME_LICENSEMGR extends AncientData
{
  var pszUserID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserID_Data:Array[Byte] = _ // for pszUserID pointer ,struct:char;			// currently it's mac address.
var pszUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszCreateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszSnoUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSnoUpdateTime_Data:Array[Byte] = _ // for pszSnoUpdateTime pointer ,struct:char;	// if pszSno changed, then change this time.
var pszCSigUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCSigUpdateTime_Data:Array[Byte] = _ // for pszCSigUpdateTime pointer ,struct:char;	// if pszCSig changed, then change this time
var pszSNo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSNo_Data:Array[Byte] = _ // for pszSNo pointer ,struct:char;		// serial no. STRING.
var pszCSig_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCSig_Data:Array[Byte] = _ // for pszCSig pointer ,struct:char;		// computer signature. TEXT
} // COLNAME_LICENSEMGR;

class COLNAME_QRYASSIGN extends AncientData
{
  var pszUserID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserID_Data:Array[Byte] = _ // for pszUserID pointer ,struct:char;
var pszTotalCaseChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotalCaseChecked_Data:Array[Byte] = _ // for pszTotalCaseChecked pointer ,struct:char;
var pszTotalLTFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotalLTFingerChecked_Data:Array[Byte] = _ // for pszTotalLTFingerChecked pointer ,struct:char;
var pszTotalLLFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotalLLFingerChecked_Data:Array[Byte] = _ // for pszTotalLLFingerChecked pointer ,struct:char;
var pszTotalTLFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotalTLFingerChecked_Data:Array[Byte] = _ // for pszTotalTLFingerChecked pointer ,struct:char;
var pszTotalTTFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTotalTTFingerChecked_Data:Array[Byte] = _ // for pszTotalTTFingerChecked pointer ,struct:char;
var pszCurCaseChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurCaseChecked_Data:Array[Byte] = _ // for pszCurCaseChecked pointer ,struct:char;
var pszCurLTFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurLTFingerChecked_Data:Array[Byte] = _ // for pszCurLTFingerChecked pointer ,struct:char;
var pszCurLLFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurLLFingerChecked_Data:Array[Byte] = _ // for pszCurLLFingerChecked pointer ,struct:char;
var pszCurTLFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurTLFingerChecked_Data:Array[Byte] = _ // for pszCurTLFingerChecked pointer ,struct:char;
var pszCurTTFingerChecked_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurTTFingerChecked_Data:Array[Byte] = _ // for pszCurTTFingerChecked pointer ,struct:char;
var pszCurStageStartDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurStageStartDate_Data:Array[Byte] = _ // for pszCurStageStartDate pointer ,struct:char;
var pszCurStageStopDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCurStageStopDate_Data:Array[Byte] = _ // for pszCurStageStopDate pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszCreateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;
var pszUpdateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;
} // COLNAME_QRYASSIGN;


/**
  * 定义指纹信息采集系统需要使用的数据交换状态表、信息反馈表
  * added by beagle on Nov. 27, 2008
  */
class COLNAME_LFIC_DATATRANSMITSTATUS extends AncientData
{
  var pszCardKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardKey_Data:Array[Byte] = _ // for pszCardKey pointer ,struct:char;
var pszMISPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszCreator_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreator_Data:Array[Byte] = _ // for pszCreator pointer ,struct:char;
var pszUpdater_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdater_Data:Array[Byte] = _ // for pszUpdater pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszSystemType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSystemType_Data:Array[Byte] = _ // for pszSystemType pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
} //COLNAME_LFIC_DATATRANSMITSTATUS;

class COLNAME_LFIC_FEEDBACKINFO extends AncientData
{
  var pszCardKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardKey_Data:Array[Byte] = _ // for pszCardKey pointer ,struct:char;
var pszMISPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMISPersonID_Data:Array[Byte] = _ // for pszMISPersonID pointer ,struct:char;
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;
var pszUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUnitCode_Data:Array[Byte] = _ // for pszUnitCode pointer ,struct:char;
var pszUserName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUserName_Data:Array[Byte] = _ // for pszUserName pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;
var pszFeedbackDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFeedbackDate_Data:Array[Byte] = _ // for pszFeedbackDate pointer ,struct:char;
var pszSystemType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSystemType_Data:Array[Byte] = _ // for pszSystemType pointer ,struct:char;
var pszType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszType_Data:Array[Byte] = _ // for pszType pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszDescription_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDescription_Data:Array[Byte] = _ // for pszDescription pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
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
  var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;			//!< 协查任务对应的卡片存放的数据库ID
var pszTID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTID_Data:Array[Byte] = _ // for pszTID pointer ,struct:char;			//!< 表ID
var pszCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;			//!< 卡号（条码号）
var pszXCPriority_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCPriority_Data:Array[Byte] = _ // for pszXCPriority pointer ,struct:char;		//!< 协查级别
var pszXCPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;		//!< 协查目的：根据公安部FPT4.0中提供的协查目的分类的基础上进行扩充
var pszXCSource_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCSource_Data:Array[Byte] = _ // for pszXCSource pointer ,struct:char;		//!< 协查源：该协查任务的来源，根据文档《省级指纹自动识别系统接口改造要求补充说明（2011.10.31修改稿）.doc
var pszXCStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCStatus_Data:Array[Byte] = _ // for pszXCStatus pointer ,struct:char;		//!< 该协查任务的当前状态
var pszAssoPersonID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAssoPersonID_Data:Array[Byte] = _ // for pszAssoPersonID pointer ,struct:char;	//!< 关联人员编号。采用人员编号或者在逃人员编号编码规则
var pszAssoCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAssoCaseID_Data:Array[Byte] = _ // for pszAssoCaseID pointer ,struct:char;		//!< 关联案件编号。同案件编号规则
var pszXCExpireTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCExpireTime_Data:Array[Byte] = _ // for pszXCExpireTime pointer ,struct:char;	//!< 协查过期时间//协查有效时限：1. 一个月	2. 三个月	3 六个月	4 长期
var pszXCCancelTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCCancelTime_Data:Array[Byte] = _ // for pszXCCancelTime pointer ,struct:char;	//!< 协查撤销时间
var pszXCUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCUnitCode_Data:Array[Byte] = _ // for pszXCUnitCode pointer ,struct:char;		//!< 协查请求单位代码
var pszXCUnitName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCUnitName_Data:Array[Byte] = _ // for pszXCUnitName pointer ,struct:char;		//!< 协查请求单位名称
var pszXCDateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCDateTime_Data:Array[Byte] = _ // for pszXCDateTime pointer ,struct:char;		//!< 协查请求日期
var pszLinkMan_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLinkMan_Data:Array[Byte] = _ // for pszLinkMan pointer ,struct:char;		//!< 联系人
var pszLinkPhone_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLinkPhone_Data:Array[Byte] = _ // for pszLinkPhone pointer ,struct:char;		//!< 联系电话
var pszApprovedBy_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszApprovedBy_Data:Array[Byte] = _ // for pszApprovedBy pointer ,struct:char;		//!< 审批人
var pszXCDescript_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCDescript_Data:Array[Byte] = _ // for pszXCDescript pointer ,struct:char;		//!< 协查请求说明
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
var pszLatestOpTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatestOpTime_Data:Array[Byte] = _ // for pszLatestOpTime pointer ,struct:char;	//!< 最近一次的操作时间：对于布控，记录的是最近一次重发查询的时间
var pszCheckTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;		//!< 最近一次检查时间：目前主要用在布控任务上，用来记录布控卡片与活体采集是否有比中结果的检查时间
var pszIsValid_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsValid_Data:Array[Byte] = _ // for pszIsValid pointer ,struct:char;		//!< 协查任务是否被撤销了，0 - 表示撤销，1 - 表示有效
var pszFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFlag_Data:Array[Byte] = _ // for pszFlag pointer ,struct:char;			//!< 一些标记：布控标记、比中任务是否必须返回标记等
var pszQryTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryTaskID_Data:Array[Byte] = _ // for pszQryTaskID pointer ,struct:char;		//!< 对应该协查任务的最新的查询ID
var pszTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;			//!< 对应该协查任务的任务控制号
} //GAFIS_GAFPTXCDATATABLECOLNAME;

class GAFIS_GAFPTXCLOGTABLECOLNAME extends AncientData
{
  var pszXCType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCType_Data:Array[Byte] = _ // for pszXCType pointer ,struct:char;		//!< 协查log类型：自动查询任务、跨省协查任务...等待
var pszXCPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;	//!< 自动查询任务和跨省协查任务的协查目的
var pszCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;		//!< 对应GAFIS系统的捺印卡号、现场卡号或案件编号
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
} //GAFIS_GAFPTXCLOGTABLECOLNAME;


class GAFIS_WANTEDLISTTABLECOLNAME extends AncientData 	//!< 布控（追逃）人员表
{
  var pszWanted_Type_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_Type_Data:Array[Byte] = _ // for pszWanted_Type pointer ,struct:char;		//!< 缉控的类型：公安部，本系统
var pszWanted_Status_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_Status_Data:Array[Byte] = _ // for pszWanted_Status pointer ,struct:char;		//!< 缉控状态
var pszWanted_NO_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;			//!< 追逃号码
var pszWanted_By_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_By_Data:Array[Byte] = _ // for pszWanted_By pointer ,struct:char;			//!< 追逃单位
var pszConnect_With_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszConnect_With_Data:Array[Byte] = _ // for pszConnect_With pointer ,struct:char;		//!< 联系人
var pszConnect_Phnoe_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszConnect_Phnoe_Data:Array[Byte] = _ // for pszConnect_Phnoe pointer ,struct:char;		//!< 联系电话
var pszCreate_Time_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreate_Time_Data:Array[Byte] = _ // for pszCreate_Time pointer ,struct:char;		//!< 记录创建时间
var pszCreate_User_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreate_User_Data:Array[Byte] = _ // for pszCreate_User pointer ,struct:char;		//!< 记录创建用户
var pszCreate_UnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreate_UnitCode_Data:Array[Byte] = _ // for pszCreate_UnitCode pointer ,struct:char;
var pszUpdate_Time_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_Time_Data:Array[Byte] = _ // for pszUpdate_Time pointer ,struct:char;		//!< 更新时间
var pszUpdate_User_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_User_Data:Array[Byte] = _ // for pszUpdate_User pointer ,struct:char;		//!< 更新用户
var pszUpdate_UnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdate_UnitCode_Data:Array[Byte] = _ // for pszUpdate_UnitCode pointer ,struct:char;
var pszComments_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComments_Data:Array[Byte] = _ // for pszComments pointer ,struct:char;			//!< 备注(按照公安部的要求有512个字节)
var pszCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;				//!< 如果此人与案件相关，则使用此ID
var pszCaseClassCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseClassCode_Data:Array[Byte] = _ // for pszCaseClassCode pointer ,struct:char;		//!< 案件类别
var pszWanted_TpCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_TpCardID_Data:Array[Byte] = _ // for pszWanted_TpCardID pointer ,struct:char;	//!< 追逃指纹编号
var pszName_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
var pszAlias_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAlias_Data:Array[Byte] = _ // for pszAlias pointer ,struct:char;				//!< 别名
var pszSexCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSexCode_Data:Array[Byte] = _ // for pszSexCode pointer ,struct:char;
var pszBirthDate_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBirthDate_Data:Array[Byte] = _ // for pszBirthDate pointer ,struct:char;
var pszShenFenID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszShenFenID_Data:Array[Byte] = _ // for pszShenFenID pointer ,struct:char;			//!< 身份证
var pszHuKouPlaceCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlaceCode_Data:Array[Byte] = _ // for pszHuKouPlaceCode pointer ,struct:char;
var pszHuKouPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszHuKouPlace_Data:Array[Byte] = _ // for pszHuKouPlace pointer ,struct:char;
var pszAddressCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddressCode_Data:Array[Byte] = _ // for pszAddressCode pointer ,struct:char;
var pszAddress_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszAddress_Data:Array[Byte] = _ // for pszAddress pointer ,struct:char;
} //GAFIS_WANTEDLISTTABLECOLNAME;


//!< 所有与缉控人员有重卡关系的捺印卡片ID。每个捺印人员只能对应一个辑控记录'
class GAFIS_WANTEDTPCARDTABLECOLNAME extends AncientData
{
  var pszWanted_NO_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;		//!< 外键，与GAFIS_WANTEDLISTTABLECOLNAME表的Wanted_NO关联
var pszCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;			//!< 与该辑控人员关联的捺印卡号
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
var pszCreateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateUser_Data:Array[Byte] = _ // for pszCreateUser pointer ,struct:char;		//!< 记录创建用户
var pszUpdateUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateUser_Data:Array[Byte] = _ // for pszUpdateUser pointer ,struct:char;		//!< 更新用户
var pszFillInUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFillInUser_Data:Array[Byte] = _ // for pszFillInUser pointer ,struct:char;		//!< 谁填报的。就是谁确认他们是属于这个卡片组的
var pszFillInUnitCode_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFillInUnitCode_Data:Array[Byte] = _ // for pszFillInUnitCode pointer ,struct:char;	//!< 填报单位
var pszFillInTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszFillInTime_Data:Array[Byte] = _ // for pszFillInTime pointer ,struct:char;		//!< 填报时间
var pszDBID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDBID_Data:Array[Byte] = _ // for pszDBID pointer ,struct:char;			//!< 捺印卡片所在的数据库ID
} //GAFIS_WANTEDTPCARDTABLECOLNAME;

class GAFIS_WANTEDOPLOGTABLECOLNAME extends AncientData 	//!< 追逃的操作记录
{
  var pszWanted_NO_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszWanted_NO_Data:Array[Byte] = _ // for pszWanted_NO pointer ,struct:char;			//!< 追逃号码
var pszOP_Type_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOP_Type_Data:Array[Byte] = _ // for pszOP_Type pointer ,struct:char;
var pszTpCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTpCardID_Data:Array[Byte] = _ // for pszTpCardID pointer ,struct:char;
var pszCreateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCreateTime_Data:Array[Byte] = _ // for pszCreateTime pointer ,struct:char;		//!< 创建时间
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;		//!< 更新时间
} //GAFIS_WANTEDOPLOGTABLECOLNAME;

//!<add by zyn at 2014.08.08
class Shxk_DataStatusColName extends AncientData
{
  var pszXk_No_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszResult1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszResult1_Data:Array[Byte] = _ // for pszResult1 pointer ,struct:char;
var pszResult2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszResult2_Data:Array[Byte] = _ // for pszResult2 pointer ,struct:char;
var pszDataFile_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDataFile_Data:Array[Byte] = _ // for pszDataFile pointer ,struct:char;
var pszOperationNumber_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOperationNumber_Data:Array[Byte] = _ // for pszOperationNumber pointer ,struct:char;
var pszIsAgain_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsAgain_Data:Array[Byte] = _ // for pszIsAgain pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_DATASTATUSCOLNAME;
type SHXK_DATASTATUSCOLNAME = Shxk_DataStatusColName
class Shxk_MatchInfoColName extends AncientData
{
  var pszXk_No_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszLatCardNo_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszLatCardNo_Data:Array[Byte] = _ // for pszLatCardNo pointer ,struct:char;
var pszQryID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszQryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryType_Data:Array[Byte] = _ // for pszQryType pointer ,struct:char;
var pszCandList_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCandList_Data:Array[Byte] = _ // for pszCandList pointer ,struct:char;
var pszCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCount_Data:Array[Byte] = _ // for pszCount pointer ,struct:char;
var pszSubmitTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitTime_Data:Array[Byte] = _ // for pszSubmitTime pointer ,struct:char;
var pszMatchTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMatchTime_Data:Array[Byte] = _ // for pszMatchTime pointer ,struct:char;
var pszSubmitUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUser_Data:Array[Byte] = _ // for pszSubmitUser pointer ,struct:char;
var pszSubmitUnit_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSubmitUnit_Data:Array[Byte] = _ // for pszSubmitUnit pointer ,struct:char;
var pszResult1_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszResult1_Data:Array[Byte] = _ // for pszResult1 pointer ,struct:char;
var pszResult2_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszResult2_Data:Array[Byte] = _ // for pszResult2 pointer ,struct:char;
var pszIsAgain_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszIsAgain_Data:Array[Byte] = _ // for pszIsAgain pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_MATCHINFOCOLNAME;
type SHXK_MATCHINFOCOLNAME = Shxk_MatchInfoColName
class Shxk_CaseStatusColName extends AncientData
{
  var pszXk_No_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszBrokenTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBrokenTime_Data:Array[Byte] = _ // for pszBrokenTime pointer ,struct:char;
var pszStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszStatus_Data:Array[Byte] = _ // for pszStatus pointer ,struct:char;
var pszSysType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSysType_Data:Array[Byte] = _ // for pszSysType pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
} //SHXK_CASESTATUSCOLNAME;

type SHXK_CASESTATUSCOLNAME =  Shxk_CaseStatusColName

class Shxk_CaseTextColName extends AncientData
{
  var pszXk_No_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXk_No_Data:Array[Byte] = _ // for pszXk_No pointer ,struct:char;
var pszCaseID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseID_Data:Array[Byte] = _ // for pszCaseID pointer ,struct:char;
var pszOccurUnit_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOccurUnit_Data:Array[Byte] = _ // for pszOccurUnit pointer ,struct:char;
var pszOccurPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOccurPlace_Data:Array[Byte] = _ // for pszOccurPlace pointer ,struct:char;
var pszOccurTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOccurTime_Data:Array[Byte] = _ // for pszOccurTime pointer ,struct:char;
var pszExtractor_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractor_Data:Array[Byte] = _ // for pszExtractor pointer ,struct:char;
var pszExtractUnit_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractUnit_Data:Array[Byte] = _ // for pszExtractUnit pointer ,struct:char;
var pszExtractPlace_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractPlace_Data:Array[Byte] = _ // for pszExtractPlace pointer ,struct:char;
var pszExtractTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractTime_Data:Array[Byte] = _ // for pszExtractTime pointer ,struct:char;
var pszCaseType1Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseType1Code_Data:Array[Byte] = _ // for pszCaseType1Code pointer ,struct:char;
var pszCaseType2Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseType2Code_Data:Array[Byte] = _ // for pszCaseType2Code pointer ,struct:char;
var pszCaseType3Code_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseType3Code_Data:Array[Byte] = _ // for pszCaseType3Code pointer ,struct:char;
var pszMoney_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMoney_Data:Array[Byte] = _ // for pszMoney pointer ,struct:char;
var pszCaseStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCaseStatus_Data:Array[Byte] = _ // for pszCaseStatus pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszComment_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszComment_Data:Array[Byte] = _ // for pszComment pointer ,struct:char;
} //SHXK_CASETEXTCOLNAME;

  type SHXK_CASETEXTCOLNAME = Shxk_CaseTextColName
//!<add by zyn at 2014.10.21
class COLNAME_NjDelData extends AncientData
{
  var pKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pKey_Data:Array[Byte] = _ // for pKey pointer ,struct:char;
var pKeyType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pKeyType_Data:Array[Byte] = _ // for pKeyType pointer ,struct:char;
var pCurUser_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pCurUser_Data:Array[Byte] = _ // for pCurUser pointer ,struct:char;
var pResult_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pResult_Data:Array[Byte] = _ // for pResult pointer ,struct:char;
var pOpNum_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pOpNum_Data:Array[Byte] = _ // for pOpNum pointer ,struct:char;
var pTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pTaskID_Data:Array[Byte] = _ // for pTaskID pointer ,struct:char;
} //NJ_DELDATACOLNAME;
type NJ_DELDATACOLNAME = COLNAME_NjDelData
class COLNAME_TTRelation extends AncientData {

  var pszPersonNo1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo1_Data:Array[Byte] = _ // for pszPersonNo1 pointer ,struct:char;
  var pszPersonNo2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo2_Data:Array[Byte] = _ // for pszPersonNo2 pointer ,struct:char;
  var pszMatchUnitCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitCode_Data:Array[Byte] = _ // for pszMatchUnitCode pointer ,struct:char;
  var pszMatchUnitName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitName_Data:Array[Byte] = _ // for pszMatchUnitName pointer ,struct:char;
  var pszMatchUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUser_Data:Array[Byte] = _ // for pszMatchUser pointer ,struct:char;
  var pszMatchDateTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchDateTime_Data:Array[Byte] = _ // for pszMatchDateTime pointer ,struct:char;
  var pszNote_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszNote_Data:Array[Byte] = _ // for pszNote pointer ,struct:char;
  var pszReportUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUser_Data:Array[Byte] = _ // for pszReportUser pointer ,struct:char;
  var pszReportDateTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportDateTime_Data:Array[Byte] = _ // for pszReportDateTime pointer ,struct:char;
  var pszApprovalUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszApprovalUser_Data:Array[Byte] = _ // for pszApprovalUser pointer ,struct:char;
  var pszApprovalDateTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszApprovalDateTime_Data:Array[Byte] = _ // for pszApprovalDateTime pointer ,struct:char;
  var pszReportUnitCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUnitCode_Data:Array[Byte] = _ // for pszReportUnitCode pointer ,struct:char;
  var pszReportUnitName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszReportUnitName_Data:Array[Byte] = _ // for pszReportUnitName pointer ,struct:char;
  var pszRecheckUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckUser_Data:Array[Byte] = _ // for pszRecheckUser pointer ,struct:char;
  var pszRecheckUnitCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckUnitCode_Data:Array[Byte] = _ // for pszRecheckUnitCode pointer ,struct:char;
  var pszRecheckDateTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRecheckDateTime_Data:Array[Byte] = _ // for pszRecheckDateTime pointer ,struct:char;
  var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
} //COLNAME_TT_RELATION;
  type COLNAME_TT_RELATION = COLNAME_TTRelation
class COLNAME_TTCandidate extends AncientData {

  var pszPersonNo1_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo1_Data:Array[Byte] = _ // for pszPersonNo1 pointer ,struct:char;
  var pszPersonNo2_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszPersonNo2_Data:Array[Byte] = _ // for pszPersonNo2 pointer ,struct:char;
  var pszMatchUnitCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchUnitCode_Data:Array[Byte] = _ // for pszMatchUnitCode pointer ,struct:char;
  var pszMatchDateTime_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMatchDateTime_Data:Array[Byte] = _ // for pszMatchDateTime pointer ,struct:char;
  var pszScore_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszScore_Data:Array[Byte] = _ // for pszScore pointer ,struct:char;
  var pszRank_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszRank_Data:Array[Byte] = _ // for pszRank pointer ,struct:char;
  var pszGroupID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszGroupID_Data:Array[Byte] = _ // for pszGroupID pointer ,struct:char;
} //COLNAME_TT_CAND;
  type COLNAME_TT_CAND = COLNAME_TTCandidate
//ad by wangkui at 2014.10.30
class COLNAME_XCData extends AncientData
{
  var pszTaskID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskID_Data:Array[Byte] = _ // for pszTaskID pointer ,struct:char;//key
var pszCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;
var pszCardType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardType_Data:Array[Byte] = _ // for pszCardType pointer ,struct:char;
var pszQryID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszTaskType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskType_Data:Array[Byte] = _ // for pszTaskType pointer ,struct:char;
var pszSendTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSendTime_Data:Array[Byte] = _ // for pszSendTime pointer ,struct:char;
var pszRecvTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecvTime_Data:Array[Byte] = _ // for pszRecvTime pointer ,struct:char;
var pszEntryTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEntryTime_Data:Array[Byte] = _ // for pszEntryTime pointer ,struct:char;
var pszUpdateTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszUpdateTime_Data:Array[Byte] = _ // for pszUpdateTime pointer ,struct:char;
var pszResendTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszResendTime_Data:Array[Byte] = _ // for pszResendTime pointer ,struct:char;
var pszCheckTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCheckTime_Data:Array[Byte] = _ // for pszCheckTime pointer ,struct:char;
var pszCancelTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCancelTime_Data:Array[Byte] = _ // for pszCancelTime pointer ,struct:char;
var pszReportTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszExtractReturnTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractReturnTime_Data:Array[Byte] = _ // for pszExtractReturnTime pointer ,struct:char;
var pszCancelStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCancelStatus_Data:Array[Byte] = _ // for pszCancelStatus pointer ,struct:char;
var pszCancelTaskFailedReason_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskFailedReason_Data:Array[Byte] = _ // for pszCancelTaskFailedReason pointer ,struct:char;
var pszCancelTaskCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskCount_Data:Array[Byte] = _ // for pszCancelTaskCount pointer ,struct:char;
var pszCancelTaskMaxCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCancelTaskMaxCount_Data:Array[Byte] = _ // for pszCancelTaskMaxCount pointer ,struct:char;
var pszExtractStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractStatus_Data:Array[Byte] = _ // for pszExtractStatus pointer ,struct:char;
var pszExtractFailedReason_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszExtractFailedReason_Data:Array[Byte] = _ // for pszExtractFailedReason pointer ,struct:char;
var pszReportStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportStatus_Data:Array[Byte] = _ // for pszReportStatus pointer ,struct:char;
var pszReportFailedReason_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportFailedReason_Data:Array[Byte] = _ // for pszReportFailedReason pointer ,struct:char;
var pszReportCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportCount_Data:Array[Byte] = _ // for pszReportCount pointer ,struct:char;
var pszReportMaxCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportMaxCount_Data:Array[Byte] = _ // for pszReportMaxCount pointer ,struct:char;
var pszXCPurpose_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCPurpose_Data:Array[Byte] = _ // for pszXCPurpose pointer ,struct:char;
var pszXCStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCStatus_Data:Array[Byte] = _ // for pszXCStatus pointer ,struct:char;
var pszEntryStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEntryStatus_Data:Array[Byte] = _ // for pszEntryStatus pointer ,struct:char;
var pszEntryMaxCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEntryMaxCount_Data:Array[Byte] = _ // for pszEntryMaxCount pointer ,struct:char;
var pszEntryCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEntryCount_Data:Array[Byte] = _ // for pszEntryCount pointer ,struct:char;
var pszEntryFailedReason_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszEntryFailedReason_Data:Array[Byte] = _ // for pszEntryFailedReason pointer ,struct:char;
var pszRecvAck_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszRecvAck_Data:Array[Byte] = _ // for pszRecvAck pointer ,struct:char;
var pszXCLevel_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszXCLevel_Data:Array[Byte] = _ // for pszXCLevel pointer ,struct:char;
var pszBKFlag_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszBKFlag_Data:Array[Byte] = _ // for pszBKFlag pointer ,struct:char;
var pszReportCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportCardID_Data:Array[Byte] = _ // for pszReportCardID pointer ,struct:char;
} //COLNAME_XCDATA;
  type COLNAME_XCDATA=COLNAME_XCData
//add by wangkui
class COLNAME_XCREPORTDATA extends AncientData
{
  var pszKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszKey_Data:Array[Byte] = _ // for pszKey pointer ,struct:char;
var pszCardID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardID_Data:Array[Byte] = _ // for pszCardID pointer ,struct:char;
var pszCardType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszCardType_Data:Array[Byte] = _ // for pszCardType pointer ,struct:char;
var pszTaskType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszTaskType_Data:Array[Byte] = _ // for pszTaskType pointer ,struct:char;
var pszReportTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszReportStatus_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportStatus_Data:Array[Byte] = _ // for pszReportStatus pointer ,struct:char;
var pszReportFailedReason_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportFailedReason_Data:Array[Byte] = _ // for pszReportFailedReason pointer ,struct:char;
var pszReportCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportCount_Data:Array[Byte] = _ // for pszReportCount pointer ,struct:char;
var pszReportMaxCount_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportMaxCount_Data:Array[Byte] = _ // for pszReportMaxCount pointer ,struct:char;
var pszInRAID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszInRAID_Data:Array[Byte] = _ // for pszInRAID pointer ,struct:char;
var pszOutRAID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszOutRAID_Data:Array[Byte] = _ // for pszOutRAID pointer ,struct:char;
} //COLNAME_XCREPORTDATA;

//add by fanjuanjuan 20160219
class COLNAME_LNHXReportData extends AncientData
{
  var pszReportTime_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszReportTime_Data:Array[Byte] = _ // for pszReportTime pointer ,struct:char;
var pszMatchTabe_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszMatchTabe_Data:Array[Byte] = _ // for pszMatchTabe pointer ,struct:char;
var pszQryID_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQryID_Data:Array[Byte] = _ // for pszQryID pointer ,struct:char;
var pszQueryType_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszQueryType_Data:Array[Byte] = _ // for pszQueryType pointer ,struct:char;
var pszSrcKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszSrcKey_Data:Array[Byte] = _ // for pszSrcKey pointer ,struct:char;
var pszDestKey_Ptr:Int = _ //using 4 byte as pointer
@IgnoreTransfer
var pszDestKey_Data:Array[Byte] = _ // for pszDestKey pointer ,struct:char;
} //COLNAME_LNHXReportData;

  /*
// contain all column names
class COLNAMESTRUCT extends AncientData
{
  var stTPLPFPXCommon = new COLNAME_TPLP_FPX_COMMON;	// tp lp FPX common columns
var stTcID = new COLGENNAMESTRUCT;			// tenprint card id, ( card id, barcode)
var stTPnID = new COLGENNAMESTRUCT;		// person id
var stTAdm = new TPADMINCOLNAME;
  @Length(10)
  var stTFg:Array[COLMICBNAMESTRUCT] = _;		// tenprint finger 10 tenfingers 10X5=50 names(rolled)
@Length(10)
var stTpf:Array[COLMICBNAMESTRUCT] = _;		// tenprint plain fingers(unrolled), and extension
  // of the system.

  /**
    * 增加了左右侧掌
    */
  @Length(UTIL_PALMDATA_ITEMCOUNT)
  var stTPm:Array[COLMICBNAMESTRUCT] = _;
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
  var stTSign:Array[COLMICBNAMESTRUCT] = _;		// signature names for criminal and printer.
@Length(8)
var stTCb:Array[COLGENNAMESTRUCT] = _;		// tenprint card binary data, 8 names

  /**
    * 增加双拇指模式平面采集指纹
    */
  @Length(5)
  var stTPl:Array[COLMICBNAMESTRUCT] = _;		// plain fingers only four part 4*2=8 items(no mnt, bin, and feat),
  // 0--right thumb, 1-right four, 2 - left thumb 3 -left four
  //	COLMICBNAMESTRUCT	stTFsFr;		// face front
  //	COLMICBNAMESTRUCT	stTFsNl;		// face nose left
  //	COLMICBNAMESTRUCT	stTFsNr;		// face nose right

  @Length(6)
  var stFace:Array[COLMICBNAMESTRUCT] = _;		// 6 extra face data.
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

  var stRmtTable = new RMTCOLNAMESTRUCT;	// remote table define,added by xia xinfeng on 2002.09.12

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
  var stBkp = new BKP_COLNAME_ALL;

  // GAFIS_CASEADMINCOLNAME
  var stCaseAdm = new GAFIS_CASEADMINCOLNAME;

  var stAdmTable = new GAFIS_ADMINTABLENAME;

  @Length(4)
  var stVoice:Array[COLMICBNAMESTRUCT] = _;	// 4 voices. for tp.

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

*/


}




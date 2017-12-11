package nirvana.hall.api.internal.fpt.vo

/**
  * Created by yuchen on 2017/12/9.
  */

/**
  * 任务编号	rwbh	字符型	23
指掌纹比对任务类型代码	zzhwbdrwlxdm	字符型	1
指纹比对系统描述	zwbdxtlxms	字符型	4
原始系统_案事件编号	ysxt_asjbh	字符型	23
案事件编号	asjbh	字符型	23
现场勘验编号	xckybh	字符型	23
原始系统_现场指掌纹编号	ysxt_xczzhwbh	字符型	..30
现场物证编号	xcwzbh	字符型	30
现场指掌纹卡编号	xczzhwkbh	字符型	..23
提交时间	tjsj	日期时间型
  */
class LatentTaskInfo {
  var taskId:String = _
  var matchTaskTypeCode:String = _
  var matchSystemTypeDescript:String = _
  var originSystemCaseId:String = _
  var caseId:String = _
  var surveyId:String = _
  var originSystemFingerId:String = _
  var latentPhysicalId:String = _
  var latentCardId:String = _
  var submitTime:String = _
}

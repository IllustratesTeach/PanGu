package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services._

/**
  * Created by songpeng on 2018/1/16.
  */
object survey {
  /*
   * galocsurvey.h : afis survey structures
   * Creation Date : January.10, 2018
   * Copyright(c) 1996-2018
   */

  // we put all functions of memory operations on survey in this file

  final val SURVEY_RECORD_CONDITION_STATE:Byte = 0x00000001
  // 获取案件状态为0的现勘数据
  final val SURVEY_RECORD_CONDITION_SURVEYNUMBER:Byte = 0x00000002
  // 获取指定现勘号的现勘数据
  final val SURVEY_RECORD_CONDITION_JIEJINGSTATE:Byte = 0x00000004
  // 获取没有获取接警数据的现勘数据

  final val SURVEY_HITRESULT_RECORD_CONDITION_STATE:Byte = 0x00000001
  // 获取比中状态为0的记录
  final val SURVEY_HITRESULT_RECORD_CONDITION_ORASID:Byte = 0x00000010

  final val SURVEY_STATE_DEFAULT: Byte= 0
  final val SURVEY_STATE_SUCCESS: Byte = 1
  final val SURVEY_STATE_FAIL: Byte = 2

  //警综案事件编号不存在
  final val POLICE_INCIDENT_NOTExist:Byte = 0
  //警综案事件编号存在
  final val POLICE_INCIDENT_Exist:Byte = 1


  // 根据任务号获取记录
  class SURVEYRECORD extends AncientData {
    var cbSize: Int = _
    // 这个结构的大小
    @Length(SID_SIZE)
    var nSID: Array[Byte] = _
    // SID
    @Length(30)
    var szKNo: String = _
    @Length(32)
    var szFingerid: String = _ // 指纹编号
    // 现勘号
    @Length(32)
    var szPhyEvidenceNo: String = _
    // 现场物证编号
    @Length(150)
    var szCaseName: String = _
    // 案件名称
    var nJieJingState: Byte = _ // 接警状态
    @Length(32)
    var szJieJingNo: String = _ // 接警编号
    var nState: Byte = _ //状态
    var PoliceIncidentExist:Byte = POLICE_INCIDENT_NOTExist //默认 不存在
    //标识警综案事件编号是否存在
  }

  //SURVEYRECORD,*PSURVEYRECORD
  class SURVEYCONFIG extends AncientData {
    var cbSize: Int = _
    // 这个结构的大小
    @Length(SID_SIZE)
    var nSID: Array[Byte] = _
    // SID
    @Length(20)
    var szStartTime: String = _
    // 开始时间
    @Length(20)
    var szEndTime: String = _ // 结束时间
    var nSeq: Int = _ //开始位置
    var nFlages: Byte = _ // 状态
    //单位代码
    @Length(7)
    var szUnitCode: String = _
    //应用服务器配置信息
    @Length(200)
    var szConfig: String = _
  }

  //SURVEYCONFIG,*PSURVEYCONFIG
  // we submit some survey record condition to retrieve
  class SURVEYRECORDLISTCONDITION extends AncientData {
    var cbSize: Int = _
    // size of this structure
    @Length(55)
    var szKeyWild: String = _
    // wild key, 44 bytes
    var nMaxToGet: Int = _
    // at most at this values of records
    var nItemFlag: Byte = _ // indicate which item to use, SURVEY_RECORD_CONDITION_XXX
  }

  // SURVEYRECORDLISTCONDITION,*PSURVEYRECORDLISTCONDITION	// size is 64 bytes

  //20180115 huxw
  class SURVEYHITRESULTRECORD extends AncientData {
    var cbSize: Int = _
    // 这个结构的大小
    @Length(SID_SIZE)
    var nSID: Array[Byte] = _
    // current record sid
    @Length(32)
    var szFingerID: String = _
    @Length(32)
    var szHitFingerID: String = _
    // 指纹编号
    @Length(SID_SIZE)
    var nOraSID: Array[Byte] = _
    // 任务号
    var nQueryType: Byte = _
    //查询类型
    var nState: Byte = _ // 状态
    var nHitFgp: Byte = _ //比中指位
  }

  //SURVEYHITRESULTRECORD, *PSURVEYHITRESULTRECORD

  // we submit some survey hit result record condition to retrieve
  class SURVEYHITRESULTRECORDLISTCONDITION extends AncientData {
    var cbSize: Int = _
    // size of this structure
    @Length(55)
    var szKeyWild: String = _
    // wild key, 44 bytes
    var nMaxToGet: Int = _
    // at most at this values of records
    var nItemFlag: Byte = _ // indicate which item to use, SURVEY_HITRESULT_RECORD_CONDITION_XXX
  }

}

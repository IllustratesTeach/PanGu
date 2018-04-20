package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.survey.SURVEYRECORD
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.services.survey.SurveyRecordService

/**
  * Created by songpeng on 2018/1/16.
  */
class SurveyRecordServiceImpl(v62Facade: V62Facade) extends SurveyRecordService{
  /**
    * 添加现堪记录
    * @param surveyRecord
    */
  override def addSurveyRecord(surveyRecord: SURVEYRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, surveyRecord)
  }

  /**
    * 更新现堪记录
    * @param surveyRecord
    */
  override def updateSurveyRecord(surveyRecord: SURVEYRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, surveyRecord)
  }

  /**
    * 获取现堪记录列表
    * @param state 状态
    * @param limit 数量
    * @return
    */
  override def getSurveyRecordListByState(state: Byte, limit: Int): Seq[SURVEYRECORD] = {
    v62Facade.NET_GAFIS_SURVEYRECORD_LIST_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, state, limit)
  }

  /**
    * 获取现堪记录列表
    * @param jiejingState 接警状态
    * @param limit 数量
    * @return
    */
  override def getSurveyRecordListByJieJingState(jiejingState: Byte, limit: Int): Seq[SURVEYRECORD] = {
    v62Facade.NET_GAFIS_SURVEYRECORD_LIST_GET_BY_JIEJINGSTATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, jiejingState, limit)
  }

  /**
    * 获取物证编号
    * @param fingerId 指纹编号
    * @return
    */
  override def getPhyEvidenceNoByFingerId(fingerId: String): Option[String] = {
    val statement = Option("(fingerid='%s')".format(fingerId))
    val mapper = Map("kno"->"szKey")
    val result = v62Facade.queryV62Table[GAKEYSTRUCT](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      1)

    if(result.nonEmpty){
      Option(result.head.szKey)
    }else{
      None
    }
  }
}

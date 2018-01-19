package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.services.survey.SurveyHitResultRecordService

/**
  * Created by songpeng on 2018/1/18.
  */
class SurveyHitResultRecordServiceImpl(v62Facade: V62Facade) extends SurveyHitResultRecordService{
  /**
    * 添加现勘比中信息
    * @param hitResult
    */
  override def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现堪比中信息
    * @param hitResult
    */
  override def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现勘比中信息
    * @param state 状态
    * @return
    */
  override def getSurveyHitResultRecordList(state: Byte): Seq[SURVEYHITRESULTRECORD] = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, state)
  }
}

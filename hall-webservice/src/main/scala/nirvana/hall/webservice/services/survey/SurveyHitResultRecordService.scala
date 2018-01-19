package nirvana.hall.webservice.services.survey

import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD

/**
  * Created by songpeng on 2018/1/16.
  */
trait SurveyHitResultRecordService {

  /**
    * 添加现勘比中信息
    * @param hitResult
    */
  def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  /**
    * 获取现堪比中信息
    * @param hitResult
    */
  def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD)

  /**
    * 获取现勘比中信息
    * @param state 状态survey.SURVEY_STATE_xx
    * @param limit 数量
    * @return
    */
  def getSurveyHitResultRecordList(state: Byte, limit: Int): Seq[SURVEYHITRESULTRECORD]

}

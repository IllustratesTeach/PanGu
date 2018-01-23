package nirvana.hall.webservice.services.survey

import nirvana.hall.c.services.gloclib.survey.SURVEYRECORD

/**
  * Created by songpeng on 2018/1/16.
  */
trait SurveyRecordService {

  /**
    * 添加现堪记录
    * @param surveyRecord
    */
  def addSurveyRecord(surveyRecord: SURVEYRECORD)

  /**
    * 更新现堪记录
    * @param surveyRecord
    */
  def updateSurveyRecord(surveyRecord: SURVEYRECORD)

  /**
    * 获取现堪记录列表
    * @param state 状态survey.SURVEY_STATE_xx
    * @param limit 数量
    * @return
    */
  def getSurveyRecordListByState(state: Byte, limit: Int): Seq[SURVEYRECORD]

  /**
    * 获取现堪记录列表
    * @param jiejingState 接警状态survey.SURVEY_STATE_xx
    * @param limit 数量
    * @return
    */
  def getSurveyRecordListByJieJingState(jiejingState: Byte, limit: Int): Seq[SURVEYRECORD]

  /**
    * 获取现堪记录物证编号
    * @param fingerId 指纹编号
    * @return
    */
  def getPhyEvidenceNoByFingerId(fingerId: String): Option[String]
}

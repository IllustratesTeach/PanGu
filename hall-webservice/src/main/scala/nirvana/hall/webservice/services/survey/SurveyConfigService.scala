package nirvana.hall.webservice.services.survey

import nirvana.hall.c.services.gloclib.survey.SURVEYCONFIG

/**
  * Created by songpeng on 2018/1/16.
  * 现堪配置service
  */
trait SurveyConfigService {

  /**
    * 添加配置信息
    * @param surveyConfig
    */
  def addSurveyConfig(surveyConfig: SURVEYCONFIG)

  /**
    * 更新配置信息
    * @param surveyConfig
    */
  def updateSurveyConfig(surveyConfig: SURVEYCONFIG)

  /**
    * 获取所有配置信息
    */
  def getSurveyConfigList(): Seq[SURVEYCONFIG]
}

package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.c.services.gloclib.survey.SURVEYCONFIG
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.webservice.services.survey.SurveyConfigService

/**
  * Created by songpeng on 2018/1/16.
  */
class SurveyConfigServiceImpl(v62Facade: V62Facade) extends SurveyConfigService{
  /**
    * 添加配置信息
    * @param surveyConfig
    */
  override def addSurveyConfig(surveyConfig: SURVEYCONFIG): Unit = {
    v62Facade.NET_GAFIS_SURVEYCONFIG_ADD(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_SURVEYRECORD, surveyConfig)
  }

  /**
    * 更新配置信息
    * @param surveyConfig
    */
  override def updateSurveyConfig(surveyConfig: SURVEYCONFIG): Unit = {
    v62Facade.NET_GAFIS_SURVEYCONFIG_UPDATE(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_SURVEYRECORD, surveyConfig)
  }

  /**
    * 获取所有配置信息
    */
  override def getSurveyConfigList(): Seq[SURVEYCONFIG]= {
    v62Facade.NET_GAFIS_SURVEYCONFIG_GET(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_SURVEYRECORD)
  }
}

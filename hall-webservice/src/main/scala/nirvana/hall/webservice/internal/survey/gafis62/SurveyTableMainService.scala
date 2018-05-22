package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
  * Created by yuchen on 2018/4/20.
  */
class SurveyTableMainService(v62Facade: V62Facade
                             , v62Config: HallV62Config) {

  def updateSurveyHitResultStateByOraSid(dbid:Short,tid:Short,oraSid:String,value:String): Unit ={

    v62Facade.NET_GAFIS_COL_UpdateBySID(dbid, tid,oraSid.toLong, "state", value.getBytes())
  }
}

package nirvana.hall.webservice.internal.survey.gafis62


import nirvana.hall.c.services.gloclib.survey.SURVEYRECORD
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
  * Created by yuchen on 2018/4/20.
  */
class SurveyTableMaintenanceService(v62Facade: V62Facade
                             , v62Config: HallV62Config) {

  def updateSurveyHitResultStateByOraSid(dbid:Short,tid:Short,oraSid:String,value:String): Unit ={

    val statement = Option("($$SID=%s)".format(oraSid))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    val result = v62Facade.queryV62Table[SURVEYRECORD](
      dbid,
      tid,
      mapper,
      statement,
      1)

    var surveyRecord = new SURVEYRECORD
    surveyRecord = result.headOption.getOrElse(throw new Exception("not get struts"))
    surveyRecord.nState = value.toInt.toByte

    v62Facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY
      , V62Facade.TID_SURVEYRECORD
      ,surveyRecord)
  }
}

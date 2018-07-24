package nirvana.hall.webservice.internal.survey.gafis62


import nirvana.hall.api.internal.DataConverter
import nirvana.hall.c.services.gloclib.survey.{SURVEYHITRESULTRECORD, SURVEYRECORD}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
  * Created by yuchen on 2018/4/20.
  */
class SurveyTableMaintenanceService(v62Facade: V62Facade
                             , v62Config: HallV62Config) {

  def updateSurveyRecordStateByOraSid(sid:String,value:String): Unit ={

    val statement = Option("($$SID=%s)".format(sid))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    val result = v62Facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      1)

    var surveyRecord = new SURVEYRECORD
    surveyRecord = result.headOption.getOrElse(throw new Exception("not get struts"))
    surveyRecord.nState = value.toInt.toByte
    surveyRecord.nSID = DataConverter.convertLongAsSixByteArray(sid.toLong)

    v62Facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY
      , V62Facade.TID_SURVEYRECORD
      ,surveyRecord)
  }


  def updateSurveyHitResultStateByOraSid(sid:String,value:String): Unit ={
    val statement = Option("($$SID=%s)".format(sid))
    val mapper = Map("FINGERID"->"szFingerID"
      ,"HITFINGERID" -> "szHitFingerID"
      ,"ORASID" -> "nOraSID"
      ,"QUEUETYPE" -> "nQueryType"
      ,"STATE" -> "nState"
      ,"HITFGP" -> "nHitFgp")

    val result = v62Facade.queryV62Table[SURVEYHITRESULTRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYHITRESULTRECORD,
      mapper,
      statement,
      1)

    var surveyHitResultRecord = new SURVEYHITRESULTRECORD
    surveyHitResultRecord = result.headOption.getOrElse(throw new Exception("not get struts"))
    surveyHitResultRecord.nState = value.toInt.toByte
    surveyHitResultRecord.nSID = DataConverter.convertLongAsSixByteArray(sid.toLong)
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(V62Facade.DBID_SURVEY
      , V62Facade.TID_SURVEYHITRESULTRECORD
      ,surveyHitResultRecord)
  }
}

package nirvana.hall.webservice.internal.survey.gafis62

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.survey
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
    * 获取现场勘验编号
    * @param fingerId 指纹编号
    * @return
    */
  override def getKNoByFingerId(fingerId: String): Option[String] = {
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

  /**
    * 获取不存在警综案事件编号的surveyRecord
    *
    * @return
    */
  override def getSurveyRecordWithPoliceIncidentIsNotExist: Seq[SURVEYRECORD] = {
    val statement = Option("(POLICEINCIDENTEXIST=%d)".format(survey.POLICE_INCIDENT_NOTExist))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    val a = v62Facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      10)
    a.foreach{
      t =>
        if(null == t.szKNo){
          println("getSurveyRecordWithPoliceIncidentIsNotExist null")
        }
        println("kNO:" + t.szKNo)
    }
    a
  }

  override def getCaseIdByKNo(kNo:String): Option[String] ={
    val statement = Option("(xkid='%s')".format(kNo))
    val mapper = Map("CASEID"->"szKey")
    val result = v62Facade.queryV62Table[GAKEYSTRUCT](
      V62Facade.DBID_LP_DEFAULT,
      V62Facade.TID_CASE,
      mapper,
      statement,
      1)

    if(result.nonEmpty){
      Option(result.head.szKey)
    }else{
      None
    }
  }

  override def isExistSurveyRecord(physicalevidenceno:String):Boolean = {
    val statement = Option("(physicalevidenceno='%s')".format(physicalevidenceno))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    v62Facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      10).size > 0
  }
}

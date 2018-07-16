package nirvana.hall.v62.internal.c.gnetlib

import junit.framework.Assert
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.c.services.gloclib.survey.{SURVEYCONFIG, SURVEYHITRESULTRECORD, SURVEYRECORD}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v70.internal.query.QueryConstants
import org.junit.Test

/**
  * Created by songpeng on 2018/1/16.
  */
class gnetsurveyTest {

  val config = new HallV62Config
  config.appServer.host = "192.168.1.80"
  config.appServer.port = 6798
  config.appServer.user = "afisadmin"
  config.appServer.password=""
  val facade = new V62Facade(config)

  @Test
  def test_NET_GAFIS_SURVEYRECORD_ADD(): Unit ={
    val record = new SURVEYRECORD
    record.szCaseName = "casename"
    record.nState = survey.SURVEY_STATE_DEFAULT
    record.szJieJingNo = "jiejing01"
    record.szKNo = "k00001"
    record.szPhyEvidenceNo = "phyevidenceno01"
    record.PoliceIncidentExist = 0
    facade.NET_GAFIS_SURVEYRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD,record)
  }

  @Test
  def test_NET_GAFIS_SURVEYRECORD_UPDATE(): Unit ={
    val record = new SURVEYRECORD
    record.cbSize = record.toByteArray().length
    record.nSID = gaqryqueConverter.convertLongAsSixByteArray(0)
    record.szCaseName = "casename2"
    record.szKNo = "k00001"
    record.szPhyEvidenceNo = "phyevidenceno01"
    record.nState = 1
    record.szJieJingNo = "jiejing01"
    record.PoliceIncidentExist = survey.POLICE_INCIDENT_Exist
    facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD,record)
  }

  @Test
  def test_NET_GAFIS_SURVEYRECORD_LIST_GET(): Unit ={
    val list = facade.NET_GAFIS_SURVEYRECORD_LIST_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, survey.SURVEY_STATE_DEFAULT)
    val list1 = facade.NET_GAFIS_SURVEYRECORD_LIST_GET_BY_JIEJINGSTATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYRECORD, survey.SURVEY_STATE_DEFAULT)
    Assert.assertTrue(list.nonEmpty)
    Assert.assertTrue(list1.nonEmpty)
  }

  @Test
  def test_NET_GAFIS_SURVEYCONFIG_ADD(): Unit ={
    val config = new SURVEYCONFIG
    config.nFlages = 0
    config.szUnitCode = "110000"
    config.szStartTime = "2008-08-08 08:08:08"
    config.szEndTime = "2018-08-08 08:08:08"
    config.szConfig = "{}"
    config.nSeq = 0
    facade.NET_GAFIS_SURVEYCONFIG_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYCONFIG,config)
  }

  @Test
  def test_NET_GAFIS_SURVEYCONFIG_UPDATE(): Unit ={
    val config = new SURVEYCONFIG
    config.cbSize = config.toByteArray().length
    config.nSID = gaqryqueConverter.convertLongAsSixByteArray(0)
    config.szUnitCode = "120000"
    config.szStartTime = "2008-08-08 08:08:08"
    config.szEndTime = "2018-08-08 08:08:08"
    config.szConfig = "{}"
    config.nSeq = 100
    config.nFlages = 1
    facade.NET_GAFIS_SURVEYCONFIG_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYCONFIG,config)
  }

  @Test
  def test_NET_GAFIS_SURVEYCONFIG_Get(): Unit ={
    val configList = facade.NET_GAFIS_SURVEYCONFIG_GET(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYCONFIG)
    Assert.assertTrue(configList.nonEmpty)
  }

  @Test
  def test_NET_GAFIS_SURVEYHITRESULTRECORD_ADD(): Unit ={
    val record = new SURVEYHITRESULTRECORD
    record.nQueryType = 1
    record.nState = 1
    record.szFingerID = "A12301"
    record.szHitFingerID= "123456"
    facade.NET_GAFIS_SURVEYHITRESULTRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, record)
  }

  @Test
  def test_NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(): Unit ={
    val record = new SURVEYHITRESULTRECORD
    record.nSID = gaqryqueConverter.convertLongAsSixByteArray(0)
    record.nQueryType = QueryConstants.QUERY_TYPE_TL.toByte
    record.nState = 0
    record.szFingerID = "P1234567890"
    record.szHitFingerID= "A123456"
    facade.NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, record)
  }

  @Test
  def test_NET_GAFIS_SURVEYHITRESULTRECORD_Get(): Unit ={
    val recordList = facade.NET_GAFIS_SURVEYHITRESULTRECORD_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, survey.SURVEY_STATE_DEFAULT)
    Assert.assertTrue(recordList.nonEmpty)
  }

  @Test
  def test_NET_GAFIS_SURVEYRECORD_LIST_GET_BY_STATE(): Unit ={

    val statement = Option("(physicalevidenceno='%s')".format("F13010000000020180100091101002"))
    val mapper = Map("kno"->"szKNo"
                    ,"fingerid"->"szFingerid"
                    ,"physicalevidenceno" -> "szPhyEvidenceNo"
                    ,"casename" -> "szCaseName"
                    ,"jiejingstate" -> "nJieJingState"
                    ,"jiejingnumber" -> "szJieJingNo"
                    ,"STATE" -> "nState")

    val result = facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      1)
    result.headOption.getOrElse(throw new Exception("not get struts")).nState = 1.toByte
    println("")
    facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY
      , V62Facade.TID_SURVEYRECORD
      ,result.head)


  }

  @Test
  def test_ModifySurveyRecordState: Unit ={
    val statement = Option("($$SID=%s)".format("0"))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    val result = facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      1)
    result.headOption.getOrElse(throw new Exception("not get struts")).nState = 1.toByte
    println("")

    var a = new SURVEYRECORD
    a = result.head
    a.nState = survey.SURVEY_STATE_SUCCESS

    facade.NET_GAFIS_SURVEYRECORD_UPDATE(V62Facade.DBID_SURVEY
      , V62Facade.TID_SURVEYRECORD
      ,a)
  }

  @Test
  def getSurveyRecordWithPoliceIncidentIsNotExist: Unit = {
    val statement = Option("(POLICEINCIDENTEXIST='%s')".format(survey.POLICE_INCIDENT_NOTExist))
    val mapper = Map("kno"->"szKNo"
      ,"fingerid"->"szFingerid"
      ,"physicalevidenceno" -> "szPhyEvidenceNo"
      ,"casename" -> "szCaseName"
      ,"jiejingstate" -> "nJieJingState"
      ,"jiejingnumber" -> "szJieJingNo"
      ,"STATE" -> "nState"
      ,"POLICEINCIDENTEXIST" -> "PoliceIncidentExist")

    val result = facade.queryV62Table[SURVEYRECORD](
      V62Facade.DBID_SURVEY,
      V62Facade.TID_SURVEYRECORD,
      mapper,
      statement,
      10)
    println(result.size)
  }

  @Test
  def getCaseIdByKNo: Unit ={
    val statement = Option("(xkid='%s')".format("K5001030000002018050081"))
    val mapper = Map("CASEID"->"szKey")
    val result = facade.queryV62Table[GAKEYSTRUCT](
      V62Facade.DBID_LP_DEFAULT,
      V62Facade.TID_CASE,
      mapper,
      statement,
      1)
    Assert.assertEquals("5001030000002018050081",result.head.szKey)
  }
}

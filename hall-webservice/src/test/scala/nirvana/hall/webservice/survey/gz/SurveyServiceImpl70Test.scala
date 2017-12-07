package nirvana.hall.webservice.survey.gz


import java.io.File
import java.sql.Timestamp
import java.util.Date

import monad.support.services.XmlLoader
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.ExceptRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.BaseTestCase
import nirvana.hall.webservice.internal.survey.gz.vo.{ListNode, OriginalList}
import nirvana.hall.webservice.internal.survey.gz.{CommonUtil, Constant}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
  * Created by ssj on 2017/11/16.
  */
class SurveyServiceImpl70Test extends BaseTestCase{


  @Test
  def test_isSleep:Unit={
    val service = getService[SurveyRecordService]
    val result = service.isSleep(new Date().getTime)
    Assert.assertEquals(false,result._1)
  }

  /**
    * 日志入库处理
    */
  @Test
  def test_saveSurveyLogRecord: Unit ={
    val service = getService[SurveyRecordService]
    val params = CommonUtil.appendParam("userId:"
      ,"password:"
      ,"unitCode:"
      ,"kNo:"
      ,"startTime:"
      ,"endTime:")
    service.saveSurveyLogRecord(Constant.GET_ORIGINAL_DATA_COUNT,"","",params,"55","")
    //service.updateSnoState(Constant.SNO_SUCCESS,"01") 一会拿走
  }
  /**
    * xml押解包操作 单元测试
    * 解析xml转对象方法，用于获取xml内容
    *
    * <?xml version=”1.0” encoding=”utf-8”?>
    * <List>
    * <K>
    * <K_No>关联编号</K_No>
    * <S_No>指纹序号1</S_No>
    * <card_type>P</card_type>
    * <CASE_NAME>案件名</CASE_NAME>
    * </K>
    * <K>
    * <K_No>关联编号</K_No>
    * <S_No>指纹序号2</S_No>
    * <card_type>F</card_type>
    * <CASE_NAME>案件名</CASE_NAME>
    * </K>
    * </List>
    * 注：P代表掌纹，F代表指纹
    *
    */
  @Test
  def test_streams(): Unit = {
    val arrayListItem = new java.util.ArrayList[ListNode]

    val listNode = new ListNode
    listNode.card_type = "F"
    listNode.CASE_NAME = "案件名称"
    listNode.K_No = "K0001"
    listNode.S_No = "S01"

    arrayListItem.add(listNode)
    arrayListItem.add(listNode)
    arrayListItem.add(listNode)

    val originalList = new OriginalList
    originalList.K = arrayListItem

    println(XmlLoader.toXml(originalList))
    val dataHandle = new ByteArrayDataSource(XmlLoader.toXml(originalList).getBytes)
    println("转换后:" + dataHandle)
    println("恢复后:" + new String(IOUtils.toByteArray(dataHandle.getInputStream)))
    val sss = XmlLoader.parseXML[OriginalList](new String(IOUtils.toByteArray(getClass.getResourceAsStream("/list.xml"))))
    val ss = sss.K.size()
    println("转换后:" + ss)
  }


  @Test
  def test_saveSurveySnoRecord: Unit ={
    val service = getService[SurveyRecordService]
    val originalList = XmlLoader.parseXML[OriginalList](new String(IOUtils.toByteArray(getClass.getResourceAsStream("/ss.xml"))))
    val iterator = originalList.K.iterator
    while(iterator.hasNext){
      val obj = iterator.next
      service.saveSurveySnoRecord(obj.K_No
        ,obj.S_No
        ,obj.card_type
        ,obj.CASE_NAME)
    }
  }


  @Test
  def saveSurvey_kno(): Unit ={
    val service = getService[SurveyRecordService]
    var bStr = false
    if(!service.isKno("K002323")){
      service.saveSurveyKnoRecord("K002323")
      bStr = true
    }
    println(bStr)
  }

  @Test
  def test_updateSurveyConfig: Unit ={
    val service = getService[SurveyRecordService]
    service.updateSurveyConfig(new Timestamp(DateConverter.convertString2Date("2017-11-20 16:27:54",Constant.DATETIME_FORMAT).getTime))
  }

  @Test
  def test_getXkcodebyState:Unit ={
    val service = getService[SurveyRecordService]
    val list = service.getXkcodebyState(0,10)
    Assert.assertEquals(3,list.size)
  }

  @Test
  def test_updateXkcodeState: Unit ={
    val service = getService[SurveyRecordService]
    service.updateXkcodeState(Constant.SURVEY_CODE_KNO_SUCCESS,"K002321")
  }

  @Test
  def test_save_caseinfo: Unit ={
    val service = getService[SurveyRecordService]
    service.saveSurveycaseInfo("K002321",new String(IOUtils.toByteArray(getClass.getResourceAsStream("/case.xml"))))
  }

  @Test
  def test_getSurveySnoRecord:Unit = {
    val service = getService[SurveyRecordService]
    val list = service.getSurveySnoRecord("K002321")
    Assert.assertEquals(3,list.size)
  }

  @Test
  def test_addcaselp:Unit = {
    val fPTService = getService[FPTService]
    val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/A3202050008882016050249.fpt"))
    fptFile match {
      case Right(fpt4) =>
        fpt4.logic03Recs.foreach { logic03Rec =>
          fPTService.addLogic03Res(logic03Rec)
        }
    }
  }

  @Test
  def test_updateSnoState:Unit = {
    val service = getService[SurveyRecordService]
    service.updateSnoState(Constant.SNO_SUCCESS,"K002321","1")
  }

  @Test
  def test_savePalmpath:Unit = {
    val service = getService[SurveyRecordService]
    service.savePalmpath("123","456","K002321")
  }

  @Test
  def test_updateCasePeception:Unit = {
    val service = getService[SurveyRecordService]
    service.updateCasePeception("123123","K5200000000002017040003")
  }

  @Test
  def test_getSurveyHit:Unit = {
    val service = getService[SurveyRecordService]
    val list = service.getSurveyHit(10)
    Assert.assertEquals(3,list.size)
  }

  @Test
  def test_updateSurveyHitState:Unit = {
    val service = getService[SurveyRecordService]
    service.updateSurveyHitState("1","2")
  }


  @Test
  def getHitResult(): Unit ={
    val Exceptservice = getService[ExceptRelationService]
    val hitfpt = Exceptservice.exportMatchRelation("","224915")
    val path = "D:\\" + "224915" + ".FPT"
    FileUtils.writeByteArrayToFile(new File(path), IOUtils.toByteArray(hitfpt.getInputStream))
  }



}

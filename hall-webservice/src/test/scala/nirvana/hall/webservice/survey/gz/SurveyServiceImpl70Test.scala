package nirvana.hall.webservice.survey.gz

import java.io.ByteArrayInputStream
import java.sql.Timestamp
import java.util.Date

import monad.support.services.XmlLoader
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.internal.survey.XmlToObject
import nirvana.hall.webservice.internal.survey.gz.{CommonUtil, Constant}
import nirvana.hall.webservice.internal.survey.gz.vo.{CaseTextList, ListNode, OriginalList}
import nirvana.hall.webservice.services.survey.gz.SurveyRecordService
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.io.IOUtils
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
        <List>
          <K>
            <K_No>关联编号</K_No>
            <S_No>指纹序号1</S_No>
            <card_type>P</card_type>
            <CASE_NAME>案件名</CASE_NAME>
          </K>
          <K>
            <K_No>关联编号</K_No>
            <S_No>指纹序号2</S_No>
            <card_type>F</card_type>
            <CASE_NAME>案件名</CASE_NAME>
          </K>
        </List>
         注：P代表掌纹，F代表指纹
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
    val originalList = XmlLoader.parseXML[OriginalList](new String(IOUtils.toByteArray(getClass.getResourceAsStream("/list.xml"))))
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
    if(!service.isKno("K002321")){
      service.saveSurveyKnoRecord("K002321")
      bStr = true
    }
    println(bStr)
  }

  @Test
  def test_updateSurveyConfig: Unit ={
    val service = getService[SurveyRecordService]
    service.updateSurveyConfig(new Timestamp(DateConverter.convertString2Date("2017-11-20 16:27:54",Constant.DATETIME_FORMAT).getTime))
  }

  /**
    * 存储文字信息解析并gz入库功能
    */
  @Test
  def saveCaseinfo(): Unit ={
    val service = getService[SurveyRecord]
    val caseinfo = IOUtils.toByteArray(getClass.getResourceAsStream("/case.xml"))
    val caseText = XmlToObject.parseXML[CaseTextList](new ByteArrayInputStream(caseinfo))
    for(i <- 0 until caseText.K.size()){
      //案件文字信息解析入库操作(默认caseid案件编号存在或不存在)
      service.addCaseInfo(caseText.K.get(i),"12")
      //更新现勘表状态为入库状态（入库成功）
      service.updateXkcodeState(Constant.SURVEY_CODE_KNO_SUCCESS,"asd")
    }
  }

  /**
    * 存储现场指纹解析并gz入库功能
    */
  @Test
  def saveFingers(): Unit ={
    val service = getService[SurveyRecord]
    val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/A3202050008882016050249.fpt"))
    val logic03Rec = fptFile.right.get.logic03Recs(0)
    service.addFingers(logic03Rec)

    service.updateSnoState(Constant.SNO_SUCCESS,"asd")

  }

  /**
    * 存储现场掌纹解析并gz入库功能
    */
  @Test
  def savePalms(): Unit ={
    val service = getService[SurveyRecord]
    val fptFile = FPTFile.parseFromInputStream(getClass.getResourceAsStream("/A3202050008882016050249.fpt"))
    val logic03Rec = fptFile.right.get.logic03Recs(0)
    service.addPalms(logic03Rec)

    service.updateSnoState(Constant.SNO_SUCCESS,"asd")

  }


}

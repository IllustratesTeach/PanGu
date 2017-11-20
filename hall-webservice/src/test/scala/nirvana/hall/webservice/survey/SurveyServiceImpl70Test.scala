package nirvana.hall.webservice.survey

import java.io.ByteArrayInputStream

import monad.support.services.XmlLoader
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.webservice.internal.survey.XmlToObject
import nirvana.hall.webservice.internal.survey.gz.Constant
import nirvana.hall.webservice.internal.survey.gz.vo.{CaseTextList, ListNode, OriginalList}
import nirvana.hall.webservice.services.survey.SurveyRecord
import org.apache.axiom.attachments.ByteArrayDataSource
import org.apache.commons.io.IOUtils
import org.junit.Test

/**
  * Created by ssj on 2017/11/16.
  */
class SurveyServiceImpl70Test extends BaseTestCase{

  /**
    * xml押解包操作 单元测试
    * 解析xml转对象方法，用于获取xml内容
    */
  @Test
  def test_streams(): Unit = {
    val arrayListItem = new java.util.ArrayList[ListNode]

    val listNode = new ListNode
    listNode.card_type = "asd"
    listNode.CASE_NAME = "asd"
    listNode.K_No = "asd"
    listNode.S_No = "asd"

    arrayListItem.add(listNode)
    arrayListItem.add(listNode)
    arrayListItem.add(listNode)

    val originalList = new OriginalList
    originalList.K = arrayListItem

    println(XmlLoader.toXml(originalList))
    val dataHandle = new ByteArrayDataSource(XmlLoader.toXml(originalList).getBytes)
    println("转换后:" + dataHandle)
    println("恢复后:" + new String(IOUtils.toByteArray(dataHandle.getInputStream)))

    val datelist = IOUtils.toByteArray(getClass.getResourceAsStream("/ss.xml"))
    val sss = XmlToObject.parseXML[OriginalList](new ByteArrayInputStream(datelist))
    val ss = sss.K.size()
    println("转换后:" + ss)
  }

  /**
    * 存储现勘列表，sno数据库方法
    * 单元测试
    */
  @Test
  def saveSurvey_sno(): Unit ={
    val service = getService[SurveyRecord]
    val datelist = IOUtils.toByteArray(getClass.getResourceAsStream("/ss.xml"))
    val sss = XmlToObject.parseXML[OriginalList](new ByteArrayInputStream(datelist))
    for(i <- 0 until sss.K.size()){
      sss.K.get(i).K_No
      sss.K.get(i).S_No
      sss.K.get(i).card_type
      sss.K.get(i).CASE_NAME
      service.saveSurveySnoRecord(sss.K.get(i).K_No,sss.K.get(i).S_No,sss.K.get(i).card_type,sss.K.get(i).CASE_NAME)
    }
  }

  /**
    * 存储现勘列表，kno数据库方法
    * 单元测试
    */
  @Test
  def saveSurvey_kno(): Unit ={
    val service = getService[SurveyRecord]
    val datelist = IOUtils.toByteArray(getClass.getResourceAsStream("/ss.xml"))
    val sss = XmlToObject.parseXML[OriginalList](new ByteArrayInputStream(datelist))
    for(i <- 0 until sss.K.size()){
      sss.K.get(i).K_No
      sss.K.get(i).S_No
      sss.K.get(i).card_type
      sss.K.get(i).CASE_NAME
      if(service.isKno(sss.K.get(i).K_No)<=0){
        service.saveSurveyKnoRecord(sss.K.get(i).K_No)
      }
    }
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
      service.updateXkcodeState(Constant.xkcodeknosuccess,"asd")
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

    service.updateSnoState(Constant.snosuccess,"asd")

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

    service.updateSnoState(Constant.snosuccess,"asd")

  }

  /**
    * 日志入库处理
    */
  @Test
  def saveloggers(): Unit ={
    val service = getService[SurveyRecord]

    val map = new scala.collection.mutable.HashMap[String,Any]
    map += ("a" -> "1234")
    map += ("b" -> "34444")
    map += ("c" -> "123456")
    map += ("d" -> "123123455")
    map += ("e" -> "99999")


    val requestmsgs = service.mapToSting("getOriginalDataCount",map)
    service.saveSurveyLogRecord("getOriginalDataCount","","",requestmsgs,"55","")
    service.updateSnoState(Constant.snosuccess,"asd")

  }
}

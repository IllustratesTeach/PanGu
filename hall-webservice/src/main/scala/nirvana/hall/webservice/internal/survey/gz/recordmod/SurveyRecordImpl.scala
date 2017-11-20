package nirvana.hall.webservice.internal.survey.gz.recordmod

import java.sql.Timestamp
import java.util.UUID
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, LPPalmService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic03Rec
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.image.internal.FPTImageConverter
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.internal.survey.gz.vo.ListCaseNode
import nirvana.hall.webservice.services.survey.SurveyRecord

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Created by ssj on 2017/11/16.
  */
class SurveyRecordImpl(hallImageRemoteService: HallImageRemoteService,
                       caseInfoService: CaseInfoService,
                       lPCardService: LPCardService,
                       lPPalmService: LPPalmService,
                       implicit val dataSource: DataSource) extends SurveyRecord{

  //fpt处理需要加载jni
  JniLoaderUtil.loadExtractorJNI()
  JniLoaderUtil.loadImageJNI()

  /**
    * 获取现勘时间配置信息
    *
    * @return
    */
  def getSurveyConfig(): mutable.HashMap[String,Any] =  {
    val sql = s"SELECT * " +
      s"FROM SURVEY_CONFIG t where t.flags = '1'"
    var map = new scala.collection.mutable.HashMap[String,Any]
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
    }{rs=>
      if(rs.next()){
        map += ("starttime" -> rs.getTimestamp("START_TIME"))
        map += ("increments" -> rs.getString("INCREMENTS"))
      }
    }
    map
  }

  /**
    * 更新现勘时间配置表 开始时间字段
    *
    * @param endTime
    * @return
    */
  def updateSurveyConfig(endTime : Timestamp): Unit =  {
    val sql = s"update SURVEY_CONFIG " +
      s"set START_TIME = ? "
    JdbcDatabase.update(sql){ps=>
      ps.setTimestamp(1,endTime)
    }
  }

  /**
    * 存入现勘现场数据入记录表操作
    * @param kno
    * @param sno
    * @param cardtype
    * @param casename
    */

  def saveSurveySnoRecord(kno:String,sno:String,cardtype:String,casename:String): Unit={
    val sql = s"insert into SURVEY_SNO_RECORD(UUID,KNO,SNO,CARDTYPE,CASENAME,BACKSTATE,STATE,INSERTTIME,UPDATETIME) " +
      s"values (?,?,?,?,?,0,0,sysdate,null)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
      ps.setString(3,sno)
      ps.setString(4,cardtype)
      ps.setString(5,casename)
    }
  }

  /**
    * 判断现勘号再现勘记录表中是否存在
    * @param kno
    * @return
    */
  def isKno(kno:String): Int={
    val sql = s"select count(1) num from SURVEY_XKCODE_RECORD where kno = ?"
    var num = 0
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,kno)
    }{rs=>
      if(rs.next()){
        num = rs.getInt("num")
      }
    }
    num
  }

  /**
    * 存储现勘号记录到现勘记录表中
    * @param kno
    */
  def saveSurveyKnoRecord(kno:String): Unit={
    val sql = s"insert into SURVEY_XKCODE_RECORD(UUID,KNO,STATE,INSERTTIME,UPDATETIME) " +
      s"values (?,?,0,sysdate,null)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,kno)
    }
  }

  /**
    * 存储调用接口后的请求和返回入库操作
    * @param interfacetype
    * @param kno
    * @param Sno
    * @param requestmsg
    * @param responsemsg
    * @param error
    */
  override def saveSurveyLogRecord(interfacetype: String, kno: String, Sno: String, requestmsg: String, responsemsg: String, error: String): Unit = {
    val sql = s"insert into SURVEY_LOGGER_RECORD(UUID,INTERFACETYPE,KNO,SNO,REQUESTMSG,RESPONSEMSG,ERRORMSG,CREATETIME) " +
      s"values (?,?,?,?,?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,interfacetype)
      ps.setString(3,kno)
      ps.setString(4,Sno)
      ps.setString(5,requestmsg)
      ps.setString(6,responsemsg)
      ps.setString(7,error)
    }
  }

  /**
    * 根据状态，获取不同的现勘号业务
    * @param state
    * @return
    */
  def getXkcodebyState(state: Int) : ListBuffer[String] ={
    val sql = "select t.kno from survey_xkcode_record t where state=?"
    val resultList = new mutable.ListBuffer[String]
    JdbcDatabase.queryWithPsSetter(sql){ ps =>
      ps.setInt(1,state)
    }
    { rs =>
      var kno: String = ""
      kno = rs.getString("kno")
      resultList.append(kno)
    }
    resultList
  }

  /**
    * 更新现勘表状态根据kno
    * @param state
    * @param kno
    */
  def updateXkcodeState(state: Int,kno : String): Unit =  {
    val sql = s"update survey_xkcode_record " +
      s"set state = ?,updatetime = sysdate where kno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,state)
      ps.setString(2,kno)
    }
  }

  /**
    * 根据现勘号查询对应的现勘现场数据
    * @param kno
    */
  def getSurveySnoRecord(kno: String): ListBuffer[mutable.HashMap[String,Any]] = {
    val sql = s"SELECT * " +
      s"FROM SURVEY_SNO_RECORD t where t.kno = ? and state = 0 "
    val resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]

    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,kno)
    }{rs=>
      var map = new scala.collection.mutable.HashMap[String,Any]
      map += ("sno" -> rs.getString("SNO"))
      map += ("cardtype" -> rs.getString("CARDTYPE"))
      map += ("casename" -> rs.getString("CASENAME"))
      resultList.append(map)
    }
    resultList
  }

  /**
    * 更新现勘现场数据状态，根据sno
    * @param state
    * @param sno
    */
  def updateSnoState(state: Int,sno : String): Unit = {
    val sql = s"update survey_sno_record " +
      s"set state = ?,updatetime = sysdate where sno = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1,state)
      ps.setString(2,sno)
    }
  }

  /**
    * 案件的文字信息解析入库操作
    * @param caseNode
    * @param caseid
    */
  def addCaseInfo(caseNode : ListCaseNode,caseid: String): Unit ={
    val caseInfo = Case.newBuilder()
    val textBuilder = caseInfo.getTextBuilder
    caseInfo.setStrCaseID(if(caseNode.CaseID==null||caseNode.CaseID=="") caseid else caseNode.CaseID)
    caseInfo.setNCaseFingerCount(caseNode.FingerCount)
    textBuilder.setStrCaseType1(caseNode.ClassCode1) //案件类别
    textBuilder.setStrCaseType2(caseNode.ClassCode2)
    textBuilder.setStrCaseType3(caseNode.ClassCode3)
    textBuilder.setStrSuspArea1Code(caseNode.SuspiciousPlaceCode1) //可疑地区行政区划
    textBuilder.setStrSuspArea2Code(caseNode.SuspiciousPlaceCode2)
    textBuilder.setStrSuspArea3Code(caseNode.SuspiciousPlaceCode3)
    textBuilder.setStrCaseOccurDate(caseNode.Date) //案发日期
    textBuilder.setStrCaseOccurPlaceCode(caseNode.OccurPlaceCode) //案发地点代码
    textBuilder.setStrCaseOccurPlace(caseNode.OccurPlace) //案发地址详情

    textBuilder.setStrExtractUnitCode(caseNode.GatherUnitCode) //提取单位代码
    textBuilder.setStrExtractUnitName(caseNode.GatherUnitName) //提取单位
    textBuilder.setStrExtractor(caseNode.GatherManName) //提取人
    textBuilder.setStrExtractDate(caseNode.GatherDate) //提取时间
    textBuilder.setStrMoneyLost(caseNode.MoneyInvolved) //涉案金额
    textBuilder.setStrPremium(caseNode.Bonus) //协查奖金
    textBuilder.setBPersonKilled("1".equals(caseNode.HomicideCaseFlag)) //命案标识
    textBuilder.setStrComment(caseNode.Comment) //备注,简要案情
    textBuilder.setStrXieChaDate(caseNode.CooperationDate) //协查日期
    textBuilder.setStrXieChaRequestUnitName(caseNode.CooperationUnitName) //协查单位名称
    textBuilder.setStrXieChaRequestUnitCode(caseNode.CooperationUnitCode) //协查单位代码

    //隐式转换，字符串转数字
    implicit def string2Int(str: String): Int = {
      if (str != null && str.matches("[0-9]+"))
        Integer.parseInt(str)
      else 0
    }
    textBuilder.setNSuperviseLevel(caseNode.UrgeLevel) //协查级别
    textBuilder.setNCaseState(caseNode.CaseStatus) //案件状态
    textBuilder.setNCaseState(caseNode.CooperationStatus) //协查状态
    textBuilder.setNCancelFlag(caseNode.WithDrawReason) //撤销标识

    caseInfoService.addCaseInfo(caseInfo.build())
  }

  /**
    * 案件的指纹信息解析入库操作
    *
    * @param logic03Rec
    */
  def addFingers(logic03Rec: Logic03Rec): Unit ={
    val lPCardList = convertLogic03Res2LPCard(logic03Rec,0)
    lPCardList.foreach{lPCard =>
      val lpCardBuiler = lPCard.toBuilder
      //图像解压
      val blobBuilder = lpCardBuiler.getBlobBuilder
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blobBuilder.getStImageBytes.toByteArray)
      if(gafisImage.stHead.bIsCompressed > 0){
        val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
        blobBuilder.setStImageBytes(ByteString.copyFrom(originalImage.toByteArray()))
      }
      lPCardService.addLPCard(lpCardBuiler.build())
    }
  }
  def addPalms(logic03Rec: Logic03Rec): Unit ={
    val lPCardList = convertLogic03Res2LPCard(logic03Rec,1)
    lPCardList.foreach{lPCard =>
      val lpCardBuiler = lPCard.toBuilder
      //图像解压
      val blobBuilder = lpCardBuiler.getBlobBuilder
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blobBuilder.getStImageBytes.toByteArray)
      if(gafisImage.stHead.bIsCompressed > 0){
        val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
        blobBuilder.setStImageBytes(ByteString.copyFrom(originalImage.toByteArray()))
      }
      lPPalmService.addLPCard(lpCardBuiler.build())
    }
  }
  def convertLogic03Res2LPCard(logic03Rec: Logic03Rec,ispalm: Int): Seq[LPCard]={
    val lpCardList = new ArrayBuffer[LPCard]()
    logic03Rec.fingers.foreach { finger =>
      val lpCard = LPCard.newBuilder()
      lpCard.setStrCardID(finger.fingerId)
      val textBuilder = lpCard.getTextBuilder
      textBuilder.setStrCaseId(logic03Rec.caseId)
      textBuilder.setStrSeq(finger.fingerNo)
      textBuilder.setStrRemainPlace(finger.remainPlace) //遗留部位
      textBuilder.setStrRidgeColor(finger.ridgeColor) //乳突线颜色
      textBuilder.setBDeadBody("1".equals(finger.ridgeColor)) //未知名尸体标识
      textBuilder.setStrDeadPersonNo(finger.corpseNo) //未知名尸体编号
      textBuilder.setStrStart(finger.mittensBegNo) //联指开始序号
      textBuilder.setStrEnd(finger.mittensEndNo) //联指结束序号
      textBuilder.setStrCaptureMethod(finger.extractMethod) //提取方式

      if (finger.imgData != null && finger.imgData.length > 0) {
        val blobBuilder = lpCard.getBlobBuilder
        if(ispalm==0){
          blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
          if (finger.fgp != null && finger.fgp.length > 0) {
            0.until(finger.fgp.length)
              .filter("1" == finger.fgp.charAt(_))
              .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
          }
        }else if(ispalm==1){
          blobBuilder.setType(ImageType.IMAGETYPE_PALM)
          if (finger.fgp != null && finger.fgp.length > 0) {
            0.until(finger.fgp.length)
              .filter("1" == finger.fgp.charAt(_))
              .foreach(i => blobBuilder.setPalmFgp(PalmFgp.valueOf(i + 1)))
          }
        }
        val gafisImage = FPTImageConverter.convert2GafisImage(finger, true)
        val gafisMnt = FPTMntConverter.convertFingerLData2GafisMnt(finger)
        blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
        blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
        blobBuilder.setStrMntExtractMethod(finger.extractMethod) //提取方式
      }
      //隐式转换，字符串转数字
      implicit def string2Int(str: String): Int = {
        if (str != null && str.matches("[0-9]+"))
          Integer.parseInt(str)
        else 0
      }
      textBuilder.setNXieChaState(finger.isFingerAssist)
      textBuilder.setNBiDuiState(finger.matchStatus)
      lpCardList += lpCard.build()
    }
    lpCardList
  }

  /**
    * 增加接警编号到case表 中
    */
  def updateCasePeception(peception: String,kno: String): Unit = {
    val sql = s"update gafis_case " +
      s"set peception_no = ? where SCENE_SURVEY_ID = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1,peception)
      ps.setString(2,kno)
    }
  }

  /**
    * map转string 拼接request
    * @param functionname
    * @param map
    * @return
    */
  def mapToSting(functionname: String ,map: mutable.HashMap[String,Any]): String = {
    var result = ""
    functionname match{
      case "getOriginalDataCount" =>
        result += map.get("a").get.asInstanceOf[String]+"-*-"
        result += map.get("b").get.asInstanceOf[String]+"-*-"
        result += map.get("c").get.asInstanceOf[String]+"-*-"
        result += map.get("d").get.asInstanceOf[String]+"-*-"
        result += map.get("e").get.asInstanceOf[String]
      case "getOriginalDataList" =>
        result += map.get("a").get.asInstanceOf[String]+"-*-"
        result += map.get("b").get.asInstanceOf[String]+"-*-"
        result += map.get("c").get.asInstanceOf[String]+"-*-"
        result += map.get("d").get.asInstanceOf[String]+"-*-"
        result += map.get("e").get.asInstanceOf[String]+"-*-"
        result += map.get("f").get.asInstanceOf[String]+"-*-"
        result += map.get("g").get.asInstanceOf[String]+"-*-"
        result += map.get("h").get.asInstanceOf[String]
      case "getCaseNo" =>
        result += map.get("a").get.asInstanceOf[String]+"-*-"
        result += map.get("b").get.asInstanceOf[String]+"-*-"
        result += map.get("c").get.asInstanceOf[String]
      case "getOriginalData" =>
        result += map.get("a").get.asInstanceOf[String]+"-*-"
        result += map.get("b").get.asInstanceOf[String]+"-*-"
        result += map.get("c").get.asInstanceOf[String]+"-*-"
        result += map.get("d").get.asInstanceOf[String]+"-*-"
        result += map.get("e").get.asInstanceOf[String]
      case "FBUseCondition" =>
        result += map.get("a").get.asInstanceOf[String]+"-*-"
        result += map.get("b").get.asInstanceOf[String]+"-*-"
        result += map.get("c").get.asInstanceOf[String]+"-*-"
        result += map.get("d").get.asInstanceOf[String]+"-*-"
        result += map.get("e").get.asInstanceOf[String]+"-*-"
        result += map.get("f").get.asInstanceOf[String]+"-*-"
        result += map.get("g").get.asInstanceOf[String]
      case _ =>
    }
    result
  }
}

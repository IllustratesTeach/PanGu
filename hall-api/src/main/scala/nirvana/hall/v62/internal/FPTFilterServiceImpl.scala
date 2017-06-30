package nirvana.hall.v62.internal

import java.io.{File, FileOutputStream}
import java.text.{DecimalFormat, SimpleDateFormat}
import java.util.Date
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.FPTFilterService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT3File.FPT3File
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.{FPT4File, FPTFile}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.commons.lang.StringUtils

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by win-20161010 on 2017/5/13.
  */
class FPTFilterServiceImpl(implicit val dataSource: DataSource) extends FPTFilterService with LoggerSupport {


  override def handler(count: Int): Unit = {
    var id = ""
    var serviceId = ""
    var path = ""

    getWillFilterFPTFilePath(count).foreach {
      f: (mutable.HashMap[String, Any])
      =>
        info("handle start id:{}", id)
        id = f.get("id").get.toString
        serviceId = f.get("serviceid").get.toString
        path = f.get("FPT_PATH").get.toString
        val is = FileUtils.openInputStream(new File(path))
        try {
          val fptFile = FPTFile.parseFromInputStream(is, AncientConstants.GBK_ENCODING)
          val fPT4File = filter(fptFile.right.get)
          FileUtils.writeByteArrayToFile(new File(path)
            ,fPT4File.build().toByteArray(AncientConstants.GBK_ENCODING))
          modfiyFiltedFPTStatus(id)
        } catch {
          case e: Exception =>
            val errorMsg = ExceptionUtil.getStackTraceInfo(e)
            error("filter error:id={},serviceId={},path={},errorInfo:{}"
              , id, serviceId, path, errorMsg)
            modfiyFiltedFPTErrorMsg(id, errorMsg)
        }finally {
          IOUtils.closeQuietly(is)
        }
        info("handle end id:{}", id)
    }

    def convertFromPathToFile(path: String): Either[FPT3File, FPT4File] = {
      val is = FileUtils.openInputStream(new File(path))
      val fptFile = FPTFile.parseFromInputStream(is, AncientConstants.GBK_ENCODING)
      IOUtils.closeQuietly(is)
      fptFile
    }

    /**
      *
      * @param rowNum
      * @return
      */
    def getWillFilterFPTFilePath(rowNum: Int): ListBuffer[mutable.HashMap[String, Any]] = {
      val sql = s"SELECT t.id,t.serviceid,t.FPT_PATH " +
        s"FROM hall_xc_report t " +
        s" WHERE t.status = '9' " +
        s"AND update_time IS NULL " +
        s"AND rownum <=?"
      val resultList = new mutable.ListBuffer[mutable.HashMap[String, Any]]
      JdbcDatabase.queryWithPsSetter(sql) { ps =>
        ps.setInt(1, rowNum)
      } { rs =>
        var map = new scala.collection.mutable.HashMap[String, Any]
        map += ("id" -> rs.getString("id"))
        map += ("serviceid" -> rs.getString("serviceid"))
        map += ("FPT_PATH" -> rs.getString("FPT_PATH"))
        resultList.append(map)
      }
      resultList
    }

    def modfiyFiltedFPTStatus(id: String): Int = {
      val sql = s"UPDATE hall_xc_report t " +
        s"SET t.status = '1',t.update_time = sysdate " +
        s" WHERE t.id = ?"
      JdbcDatabase.update(sql) { ps =>
        ps.setString(1, id)
      }
    }

    def modfiyFiltedFPTErrorMsg(id: String, errInfo: String): Int = {
      val sql = s"UPDATE hall_xc_report t " +
        s"SET t.status = '9',t.ERRORMSG=?,t.update_time = sysdate " +
        s" WHERE t.id = ?"
      JdbcDatabase.update(sql) { ps =>
        ps.setString(1, errInfo)
        ps.setString(2, id)
      }
    }



    /**
      *
      * @param fPT4File
      * @return
      */
    def filter(fPT4File: FPT4File): FPT4File = {
      var fPTFilterResult: FPT4File = null
      if (fPT4File.logic02Recs.length > 0) {
        fPTFilterResult = filterTPFPTFile(fPT4File)
      } else if (fPT4File.logic03Recs.length > 0) {
        fPTFilterResult = filterLPFPTFile(fPT4File)
      }
      fPTFilterResult
    }


    /**
      * 过滤捺印FPT方法
      *
      * @param fPT4File
      * @return
      */
    def filterTPFPTFile(fPT4File: FPT4File): FPT4File = {

      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.personId)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.personId)) {
        throw new Exception("personId is empty")
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.cardId)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.cardId)) {
        throw new Exception("cardId is empty")
      }

//      if((!fPT4File.logic02Recs.head.personId.startsWith("R"))||(fPT4File.logic02Recs.head.personId.length == 22)){
//        //人员编号	必须为23位长度
//        fPT4File.logic02Recs.head.personId = "R" + fPT4File.logic02Recs.head.personId
//      }

      if(fPT4File.logic02Recs.head.personId.length != 23){
        if(fPT4File.logic02Recs.head.personId.length <= 22){
          fPT4File.logic02Recs.head.personId = "R" + fPT4File.logic02Recs.head.personId
        }else{
          throw new Exception("personId 's rules is error:" + fPT4File.logic02Recs.head.personId)
        }
      }else{
        fPT4File.logic02Recs.head.personId = "R" + fPT4File.logic02Recs.head.personId.substring(1,23)
      }

      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.index)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.index)) {
        //序号	不能为空
        fPT4File.logic02Recs.head.index = "1"
      }
      if (fPT4File.logic02Recs.head.fingers.head.imgData == null || fPT4File.logic02Recs.head.fingers.head.imgData.length <= 0) {
        throw new Exception("image is empty");
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.fingers.head.imgDataLength)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.fingers.head.imgDataLength)
        || Integer.valueOf(fPT4File.logic02Recs.head.fingers.head.imgDataLength) <= 0) {
        fPT4File.logic02Recs.head.fingers.head.imgDataLength = fPT4File.logic02Recs.head.fingers.head.imgData.length.toString
      }



      fPT4File.logic02Recs.head.systemType = "1900" //系统类型	不能为空


      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.personName)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.personName)) {
        //姓名	不能为空
        fPT4File.logic02Recs.head.personName = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.gender)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.gender)) {
        fPT4File.logic02Recs.head.gender = "0"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.birthday)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.birthday)) {
        fPT4File.logic02Recs.head.birthday = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.door)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.door)) {
        fPT4File.logic02Recs.head.door = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.doorDetail)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.doorDetail)) {
        fPT4File.logic02Recs.head.doorDetail = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.isCriminal)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.isCriminal)) {
        fPT4File.logic02Recs.head.isCriminal = "2" //前科库标识 1：有；2：无
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.gatherUnitCode)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.gatherUnitCode)) {
        //捺印单位代码	不能同时和捺印单位名称为空
        fPT4File.logic02Recs.head.gatherUnitCode = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.gatherUnitName)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.gatherUnitName)) {
        //捺印单位名称	不能同时和捺印单位代码为空
        fPT4File.logic02Recs.head.gatherUnitName = "未知"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.gatherName)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.gatherName)) {
        //捺印人姓名		不能为空
        fPT4File.logic02Recs.head.gatherName = "管理员"
      }
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.gatherDate)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.gatherDate)) {
        //捺印日期		不能为空
        fPT4File.logic02Recs.head.gatherDate = new SimpleDateFormat("YYYYMMDD").format(new Date)
      }
      //发送指纹个数
      if (StringUtils.isEmpty(fPT4File.logic02Recs.head.sendFingerCount)
        ||StringUtils.isBlank(fPT4File.logic02Recs.head.sendFingerCount)
        || fPT4File.logic02Recs.head.sendFingerCount.length <= 0) {
        fPT4File.logic02Recs.head.sendFingerCount = fPT4File.logic02Recs.head.fingers.length.toString
      }
      if (fPT4File.logic02Recs.head.sendFingerCount.toInt <= 0) {
        //发送指纹个数	不能为空，不能为0（0代表索引数据）
        throw new Exception("send finger num is null")
      }
      //指纹信息长度 指纹信息长度	必须为数字
      val fingersCount = fPT4File.logic02Recs.head.fingers.length
      if (fingersCount > 0) {
        fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
        => (f.dataLength = f.toByteArray(AncientConstants.GBK_ENCODING).length.toString)
        )
      }

      //自定义信息长度不能为空
      if (fingersCount > 0) {
        fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
        => (f.customInfoLength = f.customInfo.length.toString)
        )
      }

      fPT4File.logic02Recs.head.fingers.head.sendNo = "01" //发送序号	不能为空

      var fgp = 1
      fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
      => if (StringUtils.isEmpty(f.fgp)||StringUtils.isBlank(f.fgp)){
          f.fgp += fgp
          fgp+=1
        }

      )

      //特征点为空的指位的个数 特征点个数	不能为空 特征点	不能超过4个以上为空
      var featureIsNullCount = 0
      fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
      => if (StringUtils.isEmpty(f.feature)||StringUtils.isBlank(f.feature))
          featureIsNullCount += 1)
      if (featureIsNullCount > 4) {
        throw new Exception("cardNo:" + fPT4File.logic02Recs.head.cardId + " No feature point refers to the number of more than 5:" + featureIsNullCount)
      }

      fPT4File.logic02Recs.head.fingers.foreach { (f: FPT4File.FingerTData)
      => f.customInfoLength = "0" //自定义信息长度	不能为空 必须为数字
         f.imgHorizontalLength = "640" //    图像水平方向长度	必须为640
         f.imgVerticalLength = "640" //      图像垂直方向长度	必须为640
         f.dpi = "500" //      图像分辨率	必须为500
        if(!f.imgCompressMethod.startsWith("14")){
          f.imgCompressMethod = "1419" //      图像压缩方法代码	不能为空 必须为14开头的WSQ压缩方法
        }
      }
      //    fPT4File.sendTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date)
      //    fPT4File.sendUnitCode = sendUnitCode
      //    fPT4File.sendUnitName = sendUnitName
      //    fPT4File.sender = senderName
      fPT4File
    }

    /**
      * 过滤现场案件FPT
      *
      * @param fPT4File
      * @return
      *
      */
    def filterLPFPTFile(fPT4File: FPT4File): FPT4File = {
      //逻辑记录长度	必须为数字
      //hall导出时，该项属性肯定为数字
      //案件编号	必须23位长度
//      if((!fPT4File.logic03Recs.head.caseId.startsWith("A"))||(fPT4File.logic03Recs.head.caseId.length == 22)){
//        fPT4File.logic03Recs.head.caseId = "A" + fPT4File.logic03Recs.head.caseId
//      }
      if (fPT4File.logic03Recs.head.caseId.length != 23) {
        if(fPT4File.logic03Recs.head.caseId.length <= 22){
          fPT4File.logic03Recs.head.caseId = "A" + fPT4File.logic03Recs.head.caseId
        }else{
          throw new Exception("caseId 's rules is error:" + fPT4File.logic03Recs.head.caseId)
        }
      }else{
        fPT4File.logic03Recs.head.caseId = "A" + fPT4File.logic03Recs.head.caseId.substring(1,23)
      }
      //特征点	不能为空
      fPT4File.logic03Recs.head.fingers.foreach { (f: FPT4File.FingerLData)
      => if (StringUtils.isEmpty(f.feature)||StringUtils.isBlank(f.feature)) {
        throw new Exception("feature is empty")
        }
      }
      if (fPT4File.logic03Recs.head.sendFingerCount.toInt <= 0) {
        //发送指纹个数	不能为空，不能为0（0代表索引数据）
        throw new Exception("send finger num is null")
      }


      //分析指位	不能为空
      var fgp = 1
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (StringUtils.isEmpty(f.fgp)||StringUtils.isBlank(f.fgp))

        f.fgp += fgp
        fgp += 1
      }
      //      *图像长度				不能为空 不能为0
      if (fPT4File.logic03Recs.head.fingers.head.imgData.length == 0) {
        throw new Exception("ImgData length is empty")
      }
      //发案日期	不能为空
      if (StringUtils.isEmpty(fPT4File.logic03Recs.head.occurDate)
        ||StringUtils.isBlank(fPT4File.logic03Recs.head.occurDate)) {
        fPT4File.logic03Recs.head.occurDate = new SimpleDateFormat("YYYYMMDD").format(new Date)
      }
      //发案地点代码	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.occurPlaceCode)) {
        fPT4File.logic03Recs.head.occurPlaceCode = "未知"
      }
      //发案地点	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.occurPlace)) {
        fPT4File.logic03Recs.head.occurPlace = "未知"
      }
      //简要案情	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.caseBriefDetail)) {
        fPT4File.logic03Recs.head.caseBriefDetail = "未知"
      }
      //命案标识	不能为空，只能为0/1
      if (isNullOrEmpty(fPT4File.logic03Recs.head.isMurder)
        && (!fPT4File.logic03Recs.head.isMurder.equals("0"))
        && (!fPT4File.logic03Recs.head.isMurder.equals("1"))) {
        fPT4File.logic03Recs.head.isMurder = "0"
      }
      //      协查级别		协查级别只能出现1/2/3/4/9
      if (!fPT4File.logic03Recs.head.assistLevel.equals("1")
        && !fPT4File.logic03Recs.head.assistLevel.equals("2")
        && !fPT4File.logic03Recs.head.assistLevel.equals("3")
        && !fPT4File.logic03Recs.head.assistLevel.equals("4")
        && !fPT4File.logic03Recs.head.assistLevel.equals("9")) {
        fPT4File.logic03Recs.head.assistLevel = "1"
      }

      //      提取单位代码	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.extractUnitCode)) {
        fPT4File.logic03Recs.head.extractUnitCode = "未知"
      }
      //        提取单位名称	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.extractUnitName)) {
        fPT4File.logic03Recs.head.extractUnitName = "未知"
      }
      //        提取日期	不能为空
      if (isNullOrEmpty(fPT4File.logic03Recs.head.extractDate)) {
        fPT4File.logic03Recs.head.extractDate
          = new SimpleDateFormat("YYYYMMDD").format(new Date)
      }
      //        提取人	不能为空
      fPT4File.logic03Recs.foreach{
        t => if(isNullOrEmpty(t.extractor)){
           t.extractor = "未知"
        }
      }
      // 发送现场指纹个数	不能为空 不能为0且必须为数字
      //发送指纹个数
      if (isNullOrEmpty(fPT4File.logic03Recs.head.sendFingerCount)
        || fPT4File.logic03Recs.head.sendFingerCount.length <= 0) {
        fPT4File.logic03Recs.head.sendFingerCount = fPT4File.logic03Recs.head.fingers.length.toString
      }

      //指纹信息长度 指纹信息长度	必须为数字
      val fingersCount = fPT4File.logic03Recs.head.fingers.length
      if (fingersCount > 0) {
        fPT4File.logic03Recs.head.fingers.foreach((f: FPT4File.FingerLData)
        => (f.dataLength = f.toByteArray(AncientConstants.GBK_ENCODING).length.toString))
      }

      //自定义信息长度不能为空
      if (fingersCount > 0) {
        fPT4File.logic03Recs.head.fingers.foreach((f: FPT4File.FingerLData)
        => (f.customInfoLength = f.customInfo.length.toString)
        )
      }


      //发送序号	不能为空
      var sendNo = 1
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (isNullOrEmpty(f.sendNo))
        f.sendNo = sendNo.toString
        sendNo += 1
      }

      //指纹序号	不能为空
      var fingerNo = 1
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (isNullOrEmpty(f.fingerNo))
        f.fingerNo = fingerNo.toString
        fingerNo += 1
      }

      //乳突线颜色 不能为空
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (isNullOrEmpty(f.ridgeColor))
        f.ridgeColor = "9"
      }
      //指纹比对状态 不能为空
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (isNullOrEmpty(f.matchStatus))
        f.matchStatus = "9"
      }
      //指纹纹型分类 不能为空
      fPT4File.logic03Recs.head.fingers.foreach { f: (FPT4File.FingerLData)
      => if (isNullOrEmpty(f.pattern))
        f.pattern = "9"
      }

      //特征点个数	不能为空
      var featureCount = 1
      fPT4File.logic03Recs.head.fingers.foreach { (f: FPT4File.FingerLData)
      => if (isNullOrEmpty(f.featureCount)) {
        f.featureCount = featureCount.toString
        featureCount += 1
      }
      }


      fPT4File.logic03Recs.head.fingers.foreach { (f: FPT4File.FingerLData)
      => f.customInfoLength = "0" //自定义信息长度	不能为空 必须为数字
        f.imgHorizontalLength = "512" //    图像水平方向长度	必须为512
        f.imgVerticalLength = "512" //      图像垂直方向长度	必须为512
        f.dpi = "500" //      图像分辨率	必须为500
        f.imgCompressMethod = "0000" //      图像压缩方法代码	不能为空 必须为14开头的WSQ压缩方法
      }
      fPT4File
    }

    /**
      * 查重比中关系FPT过滤
      *
      * @param fPT4File
      * @return
      */
    def filterTTHitResultFPTFile(fPT4File: FPT4File): FPT4File ={

      //逻辑记录长度不能为空
      if(StringUtils.isBlank(fPT4File.logic05Recs.length.toString)
        || fPT4File.logic05Recs.length == 0){
        throw new Exception("logic06Recs 's length is 0")
      }
      //逻辑记录类型必须为05
      fPT4File.logic05Recs.head.head.dataType = "05"

      var seqNo = 1
      fPT4File.logic05Recs.foreach{
        t =>
          //序号不能为空
          if(StringUtils.isBlank(t.index)){
            t.index = seqNo.toString
            seqNo += 1
          }

          //人员编号1不能为空且长度必须为23
          if(StringUtils.isBlank(t.personId1)){
            throw new Exception("personId1 is empty")
          }
          if(t.personId1.length!=23){
            throw new Exception("personId1 's length is not 23")
          }

          //人员编号2不能为空
          if(StringUtils.isBlank(t.personId2)){
            throw new Exception("person_no2 is empty")
          }
          //比对单位代码不能为空
          if(StringUtils.isBlank(t.matchUnitCode)){
            t.matchUnitCode = "未知"
          }
          //比对单位名称不能为空
          if(StringUtils.isBlank(t.matchName)){
            t.matchName = "未知"
          }
          //比对人员姓名不能为空
          if(StringUtils.isBlank(t.matcher)){
            t.matcher = "未知"
          }
          //比对日期不能为空
          if(StringUtils.isBlank(t.matchDate)){
            t.matchDate = new SimpleDateFormat("YYYYMMDD").format((new Date))
          }
      }
      fPT4File
    }

    /**
      * 正查或倒查比中关系FPT过滤
      *
      * @param fPT4File
      * @return
      */
    def filterLTOrTLHitResultFPTFile(fPT4File: FPT4File): FPT4File ={

      //逻辑记录长度不能为空且必须是正确数字
      if(StringUtils.isBlank(fPT4File.logic04Recs.length.toString)
        || fPT4File.logic04Recs.length == 0){
        throw new Exception("logic04Recs 's length is 0")
      }
      //逻辑记录类型必须为04
      fPT4File.logic04Recs.head.head.dataType = "04"
      //系统类型不能为空
      fPT4File.logic04Recs.head.systemType = "1900"

      var seqNo = 1
      fPT4File.logic04Recs.foreach{
        t =>
          //序号不能为空
          if(StringUtils.isBlank(t.index)){
            t.index = seqNo.toString
            seqNo += 1
          }

          //      人员编号不能为空且长度必须为23
          if(StringUtils.isBlank(t.personId)){
            throw new Exception("personId is empty")
          }
          if(t.personId.length != 23){
            throw new Exception("personId 's length is not 23")
          }
          //十指指纹指位不能为空
          if(StringUtils.isBlank(t.fgp)){
            throw new Exception("fgp is empty")
          }
          //比对单位代码不能为空
          if(StringUtils.isBlank(t.matchUnitCode)){
            t.matchUnitCode = "未知"
          }
          //比对单位名称不能为空
          if(StringUtils.isBlank(t.matchName)){
            t.matchName = "未知"
          }
          //比对人员姓名不能为空
          if(StringUtils.isBlank(t.matcher)){
            t.matcher = "未知"
          }
          //比对日期不能为空
          if(StringUtils.isBlank(t.matchDate)){
            t.matchDate = new SimpleDateFormat("YYYYMMDD").format(new Date)
          }
      }
      fPT4File
    }
  }


  private def isNullOrEmpty(value:String): Boolean ={
    var bStr = false
    if(value.equals("")
      ||StringUtils.isEmpty(value)
      ||StringUtils.isBlank(value)){
      bStr = true
    }
    bStr
  }
}

package nirvana.hall.webservice.internal.xingzhuan

import java.sql.Timestamp

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.internal.fpt.{FPTConverter, FPTServiceImpl}
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services._
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec, Logic03Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, LPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.{HallDatasource, HallWebserviceConstants}
import nirvana.hall.webservice.services.xingzhuan.{HallDatasourceService, SendQueryService}
import org.springframework.transaction.annotation.Transactional
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.FPTImageConverter
import nirvana.hall.webservice.config.HallWebserviceConfig

import scala.collection.mutable.ArrayBuffer


/**
  * Created by ssj on 2017/5/31.
  */
class SendQueryServiceImpl(queryService: QueryService
                           , assistCheckRecordService: AssistCheckRecordService
                           , caseInfoService: CaseInfoService
                           ,hallImageRemoteService: HallImageRemoteService
                           , tPCardService: TPCardService
                           ,lPCardService: LPCardService
                           ,hallDatasourceService: HallDatasourceService
                           ,extractor: FeatureExtractor
                           ,hallWebserviceConfig: HallWebserviceConfig
                           , fPTService: FPTService) extends SendQueryService with LoggerSupport{
  override def sendQuery(fPTFile: FPT4File,id:String,custom1:String,executetimes:Int): Unit = {
    val queryId = fPTFile.sid
    val ts = new Timestamp(System.currentTimeMillis());
    val isAutoQuery = hallWebserviceConfig.XingZhuanSetting.isAutoQuery
    var isAdd = "0"
    assistCheckRecordService.updateXcTask(id,executetimes+1)
    var custom4 = ""
    try {
      if (fPTFile.logic03Recs.length > 0) {
        fPTFile.logic03Recs.foreach { sLogic03Rec =>
          if (!caseInfoService.isExist(sLogic03Rec.caseId)) {
            isAdd = HallWebserviceConstants.IsAdd
            custom4 = addLogic03Res(sLogic03Rec,custom1)
          }
          //判断是否发送查询
          if(isAutoQuery.equals("1") &&(!(custom1.equals("")))){
          var oraSid = 0L
          var fingerId = ""
          val fingernos = custom1.split(",")
          if (sLogic03Rec.fingers.length > 0) {
            for (i <- 0 until fingernos.size) {
              val fingerno = "0" + fingernos(i)
              sLogic03Rec.fingers.foreach { finger =>
                if (finger.fingerNo.equals(fingerno) || finger.fingerNo.equals(fingernos(i))) {
                  //判断指纹序号的格式“01”“011”或“1”“11”
                  fingerId = finger.fingerId
                  assistCheckRecordService.saveXcQuery(id, fingerId, HallWebserviceConstants.TaskXC, HallWebserviceConstants.Status, "", queryId, sLogic03Rec.caseId, "", ts)
                  try {
                    oraSid = queryService.sendQueryByCardIdAndMatchType(fingerId, MatchType.FINGER_LT)
                    assistCheckRecordService.updateXcQuery(id, fingerId, HallWebserviceConstants.TaskXC, HallWebserviceConstants.SucStatus_SendQuery, oraSid.toString, queryId, "", ts)
                  } catch {
                    case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
                      assistCheckRecordService.updateXcQuery(id, fingerId, HallWebserviceConstants.TaskXC, HallWebserviceConstants.ErrStatus, oraSid.toString, queryId, ExceptionUtil.getStackTraceInfo(e), ts)
                      throw new Exception(ExceptionUtil.getStackTraceInfo(e))
                  }
                }
              }
            }
          }
        }
        }
      } else if(fPTFile.logic02Recs.length > 0) {
        fPTFile.logic02Recs.foreach { sLogic02Rec =>
          if (!tPCardService.isExist(sLogic02Rec.cardId)) {
            isAdd = HallWebserviceConstants.IsAdd
            addLogic02Res(sLogic02Rec)
          }
          //判断是否发送查询
          if (isAutoQuery.equals("1")){
          var oraSid = 0L
          assistCheckRecordService.saveXcQuery(id, sLogic02Rec.personId, HallWebserviceConstants.TaskZT, HallWebserviceConstants.Status, "", queryId, sLogic02Rec.personId, "", ts)
          try {
            oraSid = queryService.sendQueryByCardIdAndMatchType(sLogic02Rec.personId, MatchType.FINGER_TT)
            assistCheckRecordService.updateXcQuery(id, sLogic02Rec.personId, HallWebserviceConstants.TaskZT, HallWebserviceConstants.SucStatus_SendQuery, oraSid.toString, queryId, "", ts)
          } catch {
            case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
              assistCheckRecordService.updateXcQuery(id, sLogic02Rec.personId, HallWebserviceConstants.TaskZT, HallWebserviceConstants.SucStatus_SendQuery, oraSid.toString, queryId, ExceptionUtil.getStackTraceInfo(e), ts)
              throw new Exception(ExceptionUtil.getStackTraceInfo(e))
          }
        }
        }
      }
      if (fPTFile.logic03Recs.length <= 0){
        if (isAutoQuery.equals("1"))
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_SendQuery,"","",fPTFile.logic02Recs(0).personId.toString,isAdd)
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_NotSendQuery,"","",fPTFile.logic02Recs(0).personId.toString,isAdd)
      }else{
        if (isAutoQuery.equals("1"))
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_SendQuery,"","",fPTFile.logic03Recs(0).caseId.toString,isAdd,custom4)
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_NotSendQuery,"","",fPTFile.logic03Recs(0).caseId.toString,isAdd,custom4)
      }
    }catch{
      case e:Exception=>
        var exceptionInfo = ExceptionUtil.getStackTraceInfo(e);
        var msg = e.getMessage();
        if(msg == null) {
          var index = exceptionInfo.indexOf("\r\n");
          if(index == -1) {
            if(exceptionInfo.length() < 100) index = exceptionInfo.length()
            index = 100
          }
          msg = exceptionInfo.substring(0, index);
        }
        if (fPTFile.logic03Recs.length <= 0){
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,exceptionInfo,msg,fPTFile.logic02Recs(0).personId.toString,isAdd)
        }else{
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,exceptionInfo,msg,fPTFile.logic03Recs(0).caseId.toString,isAdd,custom4)
        }
    }
  }

  @Transactional
  def addLogic03Res(logic03Rec: Logic03Rec,custom1:String): String = {

    val caseInfo = FPTConverter.convertLogic03Res2Case(logic03Rec)
    var custom4 = ""
    try {
      caseInfoService.addCaseInfo(caseInfo)
      val hallDatasource = new HallDatasource(logic03Rec.caseId, logic03Rec.caseId, "", HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD, HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(hallDatasource, HallDatasource.TABLE_V62_CASE)
    }
    catch {
      case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
        throw new Exception(ExceptionUtil.getStackTraceInfo(e))
    }

    try {
      val lPCardList = convertLogic03Res2LPCard(logic03Rec,custom1)
      lPCardList.foreach { lPCard =>
        val lpCardBuiler = lPCard.toBuilder
        //图像解压
        val blobBuilder = lpCardBuiler.getBlobBuilder
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blobBuilder.getStImageBytes.toByteArray)
        if (gafisImage.stHead.bIsCompressed > 0) {
          val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
          blobBuilder.setStImageBytes(ByteString.copyFrom(originalImage.toByteArray()))
        }
        if (!lPCardService.isExist(lPCard.getStrCardID)) {
          lPCardService.addLPCard(lpCardBuiler.build())
          val hallDatasource = new HallDatasource(lPCard.getStrCardID, lPCard.getStrCardID, "", HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD, HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD)
          hallDatasourceService.save(hallDatasource, HallDatasource.TABLE_V62_LATFINGER)
          custom4 += lPCard.getTextOrBuilder.getStrSeq+","
        }
      }
      custom4.substring(1,custom4.length)
    }
     catch {
        case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
           throw new Exception(ExceptionUtil.getStackTraceInfo(e))
      }
  }

  @Transactional
  def addLogic02Res(logic02Rec: Logic02Rec): Unit = {
    try{
      val tpCardBuilder = FPTConverter.convertLogic02Rec2TPCard(logic02Rec).toBuilder
      //图像转换和特征提取
      val iter = tpCardBuilder.getBlobBuilderList.iterator()
      while (iter.hasNext) {
        val blob = iter.next()
        if (blob.getType == ImageType.IMAGETYPE_FINGER) {
          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
          if (gafisImage.stHead.bIsCompressed > 0) {
            val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
            val mntData = extractByGAFISIMG(originalImage, false)
            blob.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
            //          blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))

            val compressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
            if (compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_BY_GFS_CODE) {
              blob.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
            } else {
              val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(originalImage)
              blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
            }
          } else {
            val mntData = extractByGAFISIMG(gafisImage, false)
            blob.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
            //          blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
            val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
            blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
          }
        }
      }
      tPCardService.addTPCard(tpCardBuilder.build())
      val hallDatasource=new HallDatasource(tpCardBuilder.getStrCardID,tpCardBuilder.getStrCardID,"",HallDatasource.SERVICE_TYPE_TaskXC,HallDatasource.OPERATION_TYPE_ADD,HallDatasource.SERVICE_TYPE_TaskXC,HallDatasource.OPERATION_TYPE_ADD)
      hallDatasourceService.save(hallDatasource,HallDatasource.TABLE_V62_TPCARD)
    }
    catch {
      case e: Exception => error(ExceptionUtil.getStackTraceInfo(e))
        throw new Exception(ExceptionUtil.getStackTraceInfo(e))
    }
  }

  def extractByGAFISIMG(originalImage: GAFISIMAGESTRUCT, isLatent: Boolean): (GAFISIMAGESTRUCT, GAFISIMAGESTRUCT) ={
    if(isLatent){
      extractor.extractByGAFISIMG(originalImage, FingerPosition.FINGER_UNDET, FeatureType.FingerLatent)
    }else{
      val fingerIndex = originalImage.stHead.nFingerIndex
      val fingerPos = if(fingerIndex > 10){
        FingerPosition.valueOf(fingerIndex - 10)
      }else{
        FingerPosition.valueOf(fingerIndex)
      }
      extractor.extractByGAFISIMG(originalImage, fingerPos, FeatureType.FingerTemplate)
    }
  }

  def convertLogic03Res2LPCard(logic03Rec: Logic03Rec,custom1:String): Seq[LPCard] = {
    val lpCardList = new ArrayBuffer[LPCard]()
    if(!custom1.equals("")) {
    var fingernos = custom1.split(",")
    fingernos.foreach {
      fingerno =>
        logic03Rec.fingers.foreach {
          finger =>
            var fingerNo = Integer.parseInt(finger.fingerNo) + ""
            if (fingerno == fingerNo) {
              val lpCard = LPCard.newBuilder()
              if (finger.fingerId.equals(""))
                lpCard.setStrCardID(logic03Rec.caseId + finger.fingerNo)
              else lpCard.setStrCardID(finger.fingerId)
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
                blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
                if (finger.fgp != null && finger.fgp.length > 0) {
                  0.until(finger.fgp.length)
                    .filter("1" == finger.fgp.charAt(_))
                    .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
                }
                val gafisImage = FPTImageConverter.convert2GafisImage(finger, true)
                val gafisMnt = FPTMntConverter.convertFingerLData2GafisMnt(finger)
                blobBuilder.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
                blobBuilder.setStMntBytes(ByteString.copyFrom(gafisMnt.toByteArray()))
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
        }
    }
  }
    lpCardList
  }
}

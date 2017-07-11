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
import nirvana.hall.protocol.api.FPTProto.ImageType
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.webservice.{HallDatasource, HallWebserviceConstants}
import nirvana.hall.webservice.services.xingzhuan.{HallDatasourceService, SendQueryService}
import org.springframework.transaction.annotation.Transactional
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
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
    try {
      if (fPTFile.logic03Recs.length > 0) {
        fPTFile.logic03Recs.foreach { sLogic03Rec =>
          if (!caseInfoService.isExist(sLogic03Rec.caseId)) {
            isAdd = HallWebserviceConstants.IsAdd
            addLogic03Res(sLogic03Rec)
          }
          //判断是否发送查询
          if(isAutoQuery.equals("1")){
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
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_SendQuery,"",fPTFile.logic02Recs(0).personId.toString,isAdd)
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_NotSendQuery,"",fPTFile.logic02Recs(0).personId.toString,isAdd)
      }else{
        if (isAutoQuery.equals("1"))
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_SendQuery,"",fPTFile.logic03Recs(0).caseId.toString,isAdd)
        assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.SucStatus_NotSendQuery,"",fPTFile.logic03Recs(0).caseId.toString,isAdd)
      }
    }catch{
      case e:Exception=> error(ExceptionUtil.getStackTraceInfo(e))
        if (fPTFile.logic03Recs.length <= 0){
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,ExceptionUtil.getStackTraceInfo(e),fPTFile.logic02Recs(0).personId.toString,isAdd)
        }else{
          assistCheckRecordService.updateXcTask(id,HallWebserviceConstants.ErrStatus,ExceptionUtil.getStackTraceInfo(e),fPTFile.logic03Recs(0).caseId.toString,isAdd)
        }
    }
  }

  @Transactional
  def addLogic03Res(logic03Rec: Logic03Rec): Unit = {

    val caseInfo = FPTConverter.convertLogic03Res2Case(logic03Rec)
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
      val lPCardList = FPTConverter.convertLogic03Res2LPCard(logic03Rec)
      lPCardList.foreach { lPCard =>
        val lpCardBuiler = lPCard.toBuilder
        //图像解压
        val blobBuilder = lpCardBuiler.getBlobBuilder
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blobBuilder.getStImageBytes.toByteArray)
        if (gafisImage.stHead.bIsCompressed > 0) {
          val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
          blobBuilder.setStImageBytes(ByteString.copyFrom(originalImage.toByteArray()))
        }
        lPCardService.addLPCard(lpCardBuiler.build())
        val hallDatasource = new HallDatasource(lPCard.getStrCardID, lPCard.getStrCardID, "", HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD, HallDatasource.SERVICE_TYPE_TaskXC, HallDatasource.OPERATION_TYPE_ADD)
        hallDatasourceService.save(hallDatasource, HallDatasource.TABLE_V62_LATFINGER)
      }
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
}

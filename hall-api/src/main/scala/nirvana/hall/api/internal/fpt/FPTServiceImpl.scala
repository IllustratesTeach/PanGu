package nirvana.hall.api.internal.fpt

import com.google.protobuf.ByteString
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.protocol.api.FPTProto.{ImageType, LPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.springframework.transaction.annotation.Transactional

/**
  * Created by songpeng on 2017/1/23.
  */
class FPTServiceImpl(hallImageRemoteService: HallImageRemoteService,
                     tPCardService: TPCardService,
                     caseInfoService: CaseInfoService,
                     lPCardService: LPCardService) extends FPTService{
  private lazy val extractor = new FeatureExtractorImpl

  override def getLogic02Rec(cardId: String, dbId: Option[String]): Logic02Rec = {
    if(tPCardService.isExist(cardId)){
      val tpCard = tPCardService.getTPCard(cardId).toBuilder
      //转换数据格式wsq
      val iter = tpCard.getBlobBuilderList.iterator()
      while (iter.hasNext){
        val blob = iter.next()
        blob.getType match {
          case ImageType.IMAGETYPE_FINGER =>
            val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
            if(gafisImage.stHead.nCompressMethod != glocdef.GAIMG_CPRMETHOD_WSQ){
              val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
              blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
            }
          case other =>
        }
      }

      FPTFileBuilder.convertTPCard2Logic02Res(tpCard.build())
    }else{
      null
    }

  }

  override def getLogic03Rec(caseId: String, dbId: Option[String]): Logic03Rec = {
    val lpCardList = new scala.collection.mutable.ListBuffer[LPCard]
    if(caseId != null && caseInfoService.isExist(caseId)) {
      val caseInfo = caseInfoService.getCaseInfo(caseId)
      val fingerIdCount = caseInfo.getStrFingerIDList.size
      for (i <- 0 until fingerIdCount) {
        val lPCard = lPCardService.getLPCard(caseInfo.getStrFingerID(i))
        lpCardList.append(lPCard)
      }

      FPTFileBuilder.convertCaseAndLPCard2Logic03Rec(caseInfo, lpCardList)
    }else{
      null
    }
  }

  @Transactional
  override def addLogic02Res(logic02Rec: Logic02Rec): Unit = {
    val tpCardBuilder = FPTConverter.convertLogic02Rec2TPCard(logic02Rec).toBuilder
    //图像转换和特征提取
    val iter = tpCardBuilder.getBlobBuilderList.iterator()
    while(iter.hasNext){
      val blob = iter.next()
      if(blob.getType == ImageType.IMAGETYPE_FINGER){
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
        if(gafisImage.stHead.bIsCompressed > 0){
          val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
          val mntData = extractByGAFISIMG(originalImage, false)
          blob.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
//          blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))

          val compressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
          if(compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_BY_GFS_CODE){
            blob.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
          }else{
            val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(originalImage)
            blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
          }
        }else{
          val mntData = extractByGAFISIMG(gafisImage, false)
          blob.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
//          blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
          val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
          blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
        }
      }
    }

    tPCardService.addTPCard(tpCardBuilder.build())
  }

  @Transactional
  override def addLogic03Res(logic03Rec: Logic03Rec): Unit = {
    val caseInfo = FPTConverter.convertLogic03Res2Case(logic03Rec)
    caseInfoService.addCaseInfo(caseInfo)
    val lPCardList = FPTConverter.convertLogic03Res2LPCard(logic03Rec)
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

  private def extractByGAFISIMG(originalImage: GAFISIMAGESTRUCT, isLatent: Boolean): (GAFISIMAGESTRUCT, GAFISIMAGESTRUCT) ={
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

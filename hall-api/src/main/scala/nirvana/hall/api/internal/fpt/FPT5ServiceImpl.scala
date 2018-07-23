package nirvana.hall.api.internal.fpt

import java.io.ByteArrayInputStream

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.api.services.fpt.exchange.FPTExchangeService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{MatchRelationService, TPCardService, _}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{LatentPackage, _}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.protocol.api.FPTProto.ImageType
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition


import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 2017/11/3.
  */
class FPT5ServiceImpl(hallImageRemoteService: HallImageRemoteService,
                      tPCardService: TPCardService,
                      caseInfoService: CaseInfoService,
                      lPCardService: LPCardService,
                      lPPalmService: LPPalmService,
                      matchRelationService: MatchRelationService,
                      extractor: FeatureExtractor) extends FPT5Service{
  JniLoaderUtil.loadExtractorJNI()
  JniLoaderUtil.loadImageJNI()
  /**
    * 获取捺印信息
    * @param cardId 捺印卡号
    * @return
    */
  override def getFingerprintPackage(cardId: String): FingerprintPackage = {
    val tpCard = tPCardService.getTPCard(cardId).toBuilder
    //转换数据格式wsq
    val iter = tpCard.getBlobBuilderList.iterator()
    while (iter.hasNext){
      val blob = iter.next()
      blob.getType match {
        case ImageType.IMAGETYPE_FINGER | ImageType.IMAGETYPE_PALM | ImageType.IMAGETYPE_KNUCKLEPRINTS
                |ImageType.IMAGETYPE_FOURPRINT | ImageType.IMAGETYPE_FULLPALM =>
          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
          if(gafisImage.stHead.nCompressMethod != glocdef.GAIMG_CPRMETHOD_WSQ){
            val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
            blob.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
          }
        case _ =>
      }
    }
    FPT5Converter.convertTPCard2FingerprintPackage(tpCard.build)
  }

  /**
    * 捺印FPT5.0导入
    * @param fingerprintPackage
    * @param dbId
    */
  override def addFingerprintPackage(fingerprintPackage: FingerprintPackage, dbId: Option[String]): Unit = {
      tPCardService.addTPCard(getTpCardBuilder(fingerprintPackage).build,dbId)
  }

  /**
    * 捺印一体化采集FPT5.0导入(不提取特征)
    * @param fingerprintPackage
    * @param dbId
    */
  override def addQualityFingerprintPackage(fingerprintPackage: FingerprintPackage, dbId: Option[String]): Unit = {
    val tpCard = getQualityTpCardBuilder(fingerprintPackage).build
    if(tPCardService.isExist(tpCard.getStrCardID)){
      throw new Exception("人员编号记录已存在！")
    }
    tPCardService.addTPCard(tpCard,dbId)
  }


  /**
    * 获取现场信息
    * @param cardId 现场卡号
    * @return
    */
  override def getLatentPackage(cardId: String): LatentPackage = {
    val caseInfo = caseInfoService.getCaseInfo(cardId)
    val lpCardList = caseInfo.getStrFingerIDList.map{fingerId=>
      lPCardService.getLPCard(fingerId)
    }
    val palmList = caseInfo.getStrPalmIDList.map{palmId=>
      lPPalmService.getLPCard(palmId)
    }
    FPT5Converter.convertCaseInfoAndLPCard2LatentPackage(caseInfo, lpCardList, palmList)
  }

  /**
    * 现场指纹导入
    * @param latentPackage
    */
  override def addLatentPackage(latentPackage: LatentPackage, dbId: Option[String]): Unit = {
    val caseInfo = FPT5Converter.convertLatentPackage2Case(latentPackage)
    if(!caseInfoService.isExist(caseInfo.getStrCaseID)){
      caseInfoService.addCaseInfo(caseInfo)
    }else{
      val oldCaseInfo = caseInfoService.getCaseInfo(caseInfo.getStrCaseID)
      caseInfoService.updateCaseInfo(FPT5Converter.updateCaseFingerorPalmIDList(caseInfo,oldCaseInfo))
    }
    val lPCardList = FPT5Converter.convertLatentPackage2LPCard(latentPackage)
    lPCardList.foreach{lPCard =>
      val lpCardBuiler = lPCard.toBuilder
      //图像解压
      val blobBuilder = lpCardBuiler.getBlobBuilder
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blobBuilder.getStImageBytes.toByteArray)
      if(gafisImage.stHead.bIsCompressed > 0){
        val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
        blobBuilder.setStImageBytes(ByteString.copyFrom(originalImage.toByteArray()))
      }

      if(lPCard.getBlob.getType.equals(ImageType.IMAGETYPE_PALM)){
        if(!lPPalmService.isExist(lPCard.getStrCardID)){
          lPPalmService.addLPCard(lpCardBuiler.build())
        }else{
          lPPalmService.updateLPCard(lpCardBuiler.build())
        }
      }else{
        if(!lPCardService.isExist(lPCard.getStrCardID)){
          lPCardService.addLPCard(lpCardBuiler.build())
        }else{
          lPCardService.updateLPCard(lpCardBuiler.build())
        }
      }
    }
  }



  private def extractByGAFISIMG(originalImage: GAFISIMAGESTRUCT, isLatent: Boolean,isPalm:Boolean = false): (GAFISIMAGESTRUCT, GAFISIMAGESTRUCT) ={

    if(!isPalm){
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
    }else{
      val ext = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(originalImage.toByteArray())
        ,FingerPosition.FINGER_L_THUMB
        ,FeatureType.PalmTemplate)
      (new GAFISIMAGESTRUCT().fromByteArray(ext.get._1),new GAFISIMAGESTRUCT().fromByteArray(ext.get._2))
    }

  }


  /**
    *
    * @param fingerprintPackage
    * @return
    */
  private def  getTpCardBuilder(fingerprintPackage: FingerprintPackage) = {
    val tpCardBuilder = FPT5Converter.convertFingerprintPackage2TPCard(fingerprintPackage).toBuilder
    //图像转换和特征提取
    tpCardBuilder.getBlobBuilderList.foreach{
      t =>
        if(t.getType == ImageType.IMAGETYPE_FINGER | t.getType == ImageType.IMAGETYPE_PALM){
          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(t.getStImageBytes.toByteArray)
          if(gafisImage.stHead.bIsCompressed > 0){
            val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
            if(t.getType == ImageType.IMAGETYPE_FINGER){
              val mntData = extractByGAFISIMG(originalImage, false)
              t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
            }else if(t.getType == ImageType.IMAGETYPE_PALM){
              val mntData = extractByGAFISIMG(originalImage,false,true)
              t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
            }
            //blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
            val compressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
            if(compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_BY_GFS_CODE
              ||compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_CODE){
              t.setStImageBytes(ByteString.copyFrom(gafisImage.toByteArray()))
            }else{
              val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(originalImage)
              t.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
            }
          }else{
            if(t.getType == ImageType.IMAGETYPE_FINGER){
              val mntData = extractByGAFISIMG(gafisImage, false)
              t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
              //blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
            }else if(t.getType == ImageType.IMAGETYPE_PALM){
              val mntData = extractByGAFISIMG(gafisImage,false,true)
              t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
              //blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
            }
            val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
            t.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
          }
        }
    }
    tpCardBuilder
  }

  /**
    * Quality 一体化采集 捺印proto
    * @param fingerprintPackage
    * @return
    */
  private def  getQualityTpCardBuilder(fingerprintPackage: FingerprintPackage) = {
    val tpCardBuilder = FPT5Converter.convertFingerprintPackage2TPCard(fingerprintPackage).toBuilder
    tpCardBuilder
  }

  override def getLatentTaskPackage(taskId: String): LatenttaskPackage = ???

  override def addLatentTaskPackage(latenttaskPackage: LatenttaskPackage): Unit = ???

  override def getPrintTaskPackage(taskId: String): PrinttaskPackage = ???

  override def addPrintTaskPackage(printtaskPackage: PrinttaskPackage): Unit = ???

  override def getLTResultPackage(taskId: String): LtResultPackage = ???

  override def addLTResultPackage(ltResultPackage: LtResultPackage): Unit = ???

  override def getTlResultPackage(taskId: String): TlResultPackage = ???

  override def addTlResultPackage(tlResultPackage: TlResultPackage): Unit = ???

  override def getTTResultPackage(taskId: String): TtResultPackage = ???

  override def addTTResultPackage(ttResultPackage: TtResultPackage): Unit = ???

  override def getLLResultPackage(taskId: String): LlResultPackage = ???

  override def addLLResultPackage(llResultPackage: LlResultPackage): Unit = ???

  /**
    * 获得正查或倒查比中关系package
    * @param oraSid
    * @return
    */
  override def getLTHitResultPackage(oraSid: String): LtHitResultPackage = {
    matchRelationService.getLtHitResultPackageByOraSid(oraSid).head
  }

  override def addLTHitResultPackage(ltHitResultPackage: LtHitResultPackage): Unit = ???

  /**
    * 获得查重比中关系package
    * @param oraSid
    * @return
    */
  override def getTtHitResultPackage(oraSid: String): TtHitResultPackage = {
    matchRelationService.getTtHitResultPackageByOraSid(oraSid).head
  }

  override def addTtHitResultPackage(ttHitResultPackage: TtHitResultPackage): Unit = ???

  /**
    * 获得串查比中关系package
    * @param oraSid
    * @return
    */
  override def getLlHitResultPackage(oraSid: String): LlHitResultPackage = {
    matchRelationService.getLlHitResultPackageByOraSid(oraSid).head
  }

  override def addLlHitResultPackage(llHitResultPackage: LlHitResultPackage): Unit = ???

  override def getCancelLatentPackage(originSystemCaseId: String): cancelLatentPackage = ???

  override def addCancelLatentPackage(cancelLatentPackage: cancelLatentPackage): Unit = ???

}

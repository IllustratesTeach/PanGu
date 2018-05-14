package nirvana.hall.api.internal.fpt.fpttransform

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.internal.fpt.{FPT5Converter, FPTConverter}
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.api.FPTProto.{Case, ImageType, LPCard, TPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable._

/**
  * Created by yuchen on 2018/5/12.
  */
class FPTTransformer(hallImageRemoteService: HallImageRemoteService,
                     extractor: FeatureExtractor) {

  JniLoaderUtil.loadExtractorJNI()
  JniLoaderUtil.loadImageJNI()

  val firmDecoder = new FirmDecoderImpl("support",new HallImageConfig)
  val imageEncoder = new ImageEncoderImpl(firmDecoder)

  def  getTpCardBuilder(logic02Rec: Logic02Rec) = {
    val tpCardBuilder = FPTConverter.convertLogic02Rec2TPCard(logic02Rec).toBuilder
    //图像转换和特征提取
    val iter = tpCardBuilder.getBlobBuilderList.iterator()
    while(iter.hasNext){
      val blob = iter.next()
      if(blob.getType == ImageType.IMAGETYPE_FINGER){
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
        if(gafisImage.stHead.bIsCompressed > 0){
          val originalImage = hallImageRemoteService.decodeGafisImage(gafisImage)
          val mntData = try{
            extractByGAFISIMG(originalImage, false)
          }catch{
            case ex:ArithmeticException => extractByGAFISIMG(originalImage, false)
          }

          blob.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
          //blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))

          val compressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
          if(compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_BY_GFS_CODE
            ||compressMethod == fpt4code.GAIMG_CPRMETHOD_WSQ_CODE){
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
    tpCardBuilder
  }



  def buildFingerPrintPackage(tPCard: TPCard): FingerprintPackage ={
    val tpCard = tPCard.toBuilder
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



  def getCaseBuilder(logic03Rec: Logic03Rec):Case = FPTConverter.convertLogic03Res2Case(logic03Rec)

  def getLPCardBuilder(logic03Rec: Logic03Rec): Seq[LPCard] ={
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
      lpCardBuiler.build
    }
    lPCardList.asInstanceOf[mutable.Seq[LPCard]]
  }

  def builderLatentPackage(caseInfo:Case,lpCardList:Seq[LPCard]): LatentPackage ={


    val fingerLpCardArrayBuffer = new ArrayBuffer[LPCard]
    caseInfo.getStrFingerIDList.foreach{
      fingerId =>
        val list = lpCardList.filter(t => t.getStrCardID.drop(1).equals(fingerId))
        list.foreach(t => fingerLpCardArrayBuffer+=t)
    }
    val palmLpCardArrayBuffer = new ArrayBuffer[LPCard]()
    caseInfo.getStrPalmIDList.foreach{
      palmId =>
        lpCardList.filter(t => t.getStrCardID.equals(palmId)).foreach(t => palmLpCardArrayBuffer += t)
    }
    FPT5Converter.convertCaseInfoAndLPCard2LatentPackage(caseInfo, fingerLpCardArrayBuffer, palmLpCardArrayBuffer)
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

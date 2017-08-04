package nirvana.hall.api.internal.fpt

import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, ExceptRelationService, TPCardService}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, _}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
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
                     lPCardService: LPCardService,
                     exceptRelationService: ExceptRelationService,
                     extractor: FeatureExtractor,
                     implicit val dataSource: DataSource) extends FPTService{
  //fpt处理需要加载jni
  JniLoaderUtil.loadExtractorJNI()
  JniLoaderUtil.loadImageJNI()

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
        //有可能现场没有图像，为null
        if(lPCard != null){
          lpCardList.append(lPCard)
        }
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

  override def getLogic04Rec(pkid: String,num:Int, dbId: Option[String]): Logic04Rec = {

    val gafisMatchInfo = exceptRelationService.getSearchMatchRelation(pkid,num)
    val logic04Rec = new Logic04Rec
    logic04Rec.systemType = "1900"
    if(gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length-2).length==22){
      logic04Rec.caseId = "A"+gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length-2)
    }else{
      logic04Rec.caseId = gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length-2)
    }
    logic04Rec.seqNo = gafisMatchInfo.code.substring(gafisMatchInfo.code.length-2)
    if(gafisMatchInfo.tcode.length()==22){
      logic04Rec.personId = "R"+gafisMatchInfo.tcode
    }else{
      logic04Rec.personId = gafisMatchInfo.tcode
    }
    logic04Rec.fgp = gafisMatchInfo.fgp
    logic04Rec.capture = "0" //7.0web项目直接写的是0,有待确认
    //比中类型(捺印查重(TT):0;  捺印倒查(TL):1;  现场查案(LT):2;  现场串查(LL):3)
    logic04Rec.matchMethod = gafisMatchInfo.querytype match {
      case "2" => "1"
      case "1" => "2"
      case other => "9"
    }
    logic04Rec.matchUnitCode = gafisMatchInfo.registerOrg
    logic04Rec.matchName = gafisMatchInfo.matchName
    logic04Rec.matcher = gafisMatchInfo.registerUser
    logic04Rec.matchDate = gafisMatchInfo.registerTime
    logic04Rec.head.fileLength = logic04Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    //-----------------以下内容FPT上报时可空---------------------//
    //      logic04Rec.remark = ""
    //      logic04Rec.inputer = ""
    //      logic04Rec.inputDate = ""
    //      logic04Rec.approver = ""
    //      logic04Rec.approveDate = ""
    //      logic04Rec.inputUnitCode = ""
    //      logic04Rec.inputUnitName = ""
    //      logic04Rec.rechecker = ""
    //      logic04Rec.recheckUnitCode = ""
    //      logic04Rec.recheckDate = ""
    logic04Rec
  }


  override def getLogic05Rec(pkid: String,num:Int, dbId: Option[String]): Logic05Rec = {

    val gafisMatchInfo = exceptRelationService.getSearchMatchRelation(pkid,num)

    val logic05Rec = new Logic05Rec
    logic05Rec.systemType = "1900"
    if(gafisMatchInfo.code.length()==22){
      logic05Rec.personId1 = "R"+gafisMatchInfo.code
    }else{
      logic05Rec.personId1 = gafisMatchInfo.code
    }
    if(gafisMatchInfo.tcode.length()==22){
      logic05Rec.personId2 = "R"+gafisMatchInfo.tcode
    }else{
      logic05Rec.personId2 = gafisMatchInfo.tcode
    }
    logic05Rec.matchUnitCode = gafisMatchInfo.registerOrg
    logic05Rec.matchName = gafisMatchInfo.matchName
    logic05Rec.matcher = gafisMatchInfo.registerUser
    logic05Rec.matchDate = gafisMatchInfo.registerTime
    logic05Rec.head.fileLength = logic05Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    logic05Rec
  }

  override def getLogic06Rec(pkid: String,num:Int, dbId: Option[String]): Logic06Rec = {

    val gafisMatchInfo = exceptRelationService.getSearchMatchRelation(pkid,num)
    val logic06Rec = new Logic06Rec
    logic06Rec.systemType = "1900"
    if(gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length - 2).length==22){
      logic06Rec.caseId1 = "A"+gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length - 2)
    }else{
      logic06Rec.caseId1 = gafisMatchInfo.code.substring(0,gafisMatchInfo.code.length - 2)
    }
    logic06Rec.seqNo1 = gafisMatchInfo.code.substring(gafisMatchInfo.code.length - 2)
    if(gafisMatchInfo.tcode.substring(0,gafisMatchInfo.tcode.length - 2).length==22){
      logic06Rec.caseId2 = "A"+gafisMatchInfo.tcode.substring(0,gafisMatchInfo.tcode.length - 2)
    }else{
      logic06Rec.caseId2 = gafisMatchInfo.tcode.substring(0,gafisMatchInfo.tcode.length - 2)
    }
    logic06Rec.seqNo2 = gafisMatchInfo.tcode.substring(gafisMatchInfo.tcode.length - 2)
    logic06Rec.matchUnitCode = gafisMatchInfo.registerOrg
    logic06Rec.matchName = gafisMatchInfo.matchName
    logic06Rec.matcher = gafisMatchInfo.registerUser
    logic06Rec.matchDate = gafisMatchInfo.registerTime
    logic06Rec.head.fileLength = logic06Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    logic06Rec
  }
}

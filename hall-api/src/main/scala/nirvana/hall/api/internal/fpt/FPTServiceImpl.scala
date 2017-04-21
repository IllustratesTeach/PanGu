package nirvana.hall.api.internal.fpt

import java.text.SimpleDateFormat
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, _}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.protocol.api.FPTProto.{ImageType, LPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.v70.jpa._
import org.springframework.transaction.annotation.Transactional

/**
  * Created by songpeng on 2017/1/23.
  */
class FPTServiceImpl(hallImageRemoteService: HallImageRemoteService,
                     tPCardService: TPCardService,
                     caseInfoService: CaseInfoService,
                     lPCardService: LPCardService,
                     implicit val dataSource: DataSource) extends FPTService{
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

  override def getLogic04Rec(gafisCheckinInfo: GafisCheckinInfo, dbId: Option[String]): Logic04Rec = {

    val logic04Rec = new Logic04Rec
    logic04Rec.systemType = "1900"
    logic04Rec.caseId = gafisCheckinInfo.code.substring(0,gafisCheckinInfo.code.length-2)
    logic04Rec.seqNo = gafisCheckinInfo.code.substring(gafisCheckinInfo.code.length-2)
    logic04Rec.personId = gafisCheckinInfo.code
    logic04Rec.fgp = gafisCheckinInfo.fgp
    logic04Rec.capture = "0" //7.0web项目直接写的是0,有待确认
    //比中类型(捺印查重(TT):0;  捺印倒查(TL):1;  现场查案(LT):2;  现场串查(LL):3)
    logic04Rec.matchMethod = gafisCheckinInfo.querytype.toString match {
      case "2" => "1"
      case "1" => "2"
      case other => "9"
    }
    logic04Rec.matchUnitCode = gafisCheckinInfo.registerOrg
    logic04Rec.matchName = SysDepart.find_by_code(gafisCheckinInfo.registerOrg).headOption.getOrElse(gafisCheckinInfo.registerOrg).toString
    logic04Rec.matcher = gafisCheckinInfo.registerUser
    logic04Rec.matchDate = new SimpleDateFormat("yyyyMMdd").format(gafisCheckinInfo.registerTime)
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


  override def getLogic05Rec(gafisCheckinInfo: GafisCheckinInfo, dbId: Option[String]): Logic05Rec = {

    val logic05Rec = new Logic05Rec
    logic05Rec.systemType = "1900"
    logic05Rec.personId1 = gafisCheckinInfo.code
    logic05Rec.personId2 = gafisCheckinInfo.tcode
    logic05Rec.matchUnitCode = gafisCheckinInfo.registerOrg
    logic05Rec.matchName = SysDepart.find_by_code(gafisCheckinInfo.registerOrg).headOption.getOrElse(gafisCheckinInfo.registerOrg).toString
    logic05Rec.matcher = gafisCheckinInfo.registerUser
    logic05Rec.matchDate = new SimpleDateFormat("yyyyMMdd").format(gafisCheckinInfo.registerTime)
    logic05Rec.head.fileLength = logic05Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    logic05Rec
  }

  override def getLogic06Rec(gafisCheckinInfo: GafisCheckinInfo, dbId: Option[String]): Logic06Rec = {

    val logic06Rec = new Logic06Rec
    logic06Rec.systemType = "1900"
    logic06Rec.caseId1 = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.code).head.caseId
    logic06Rec.seqNo1 = gafisCheckinInfo.code.substring(gafisCheckinInfo.code.length - 2)
    logic06Rec.caseId2 = GafisCaseFinger.find_by_fingerId(gafisCheckinInfo.tcode).head.caseId
    logic06Rec.seqNo2 = gafisCheckinInfo.tcode.substring(gafisCheckinInfo.tcode.length - 2)
    logic06Rec.matchUnitCode = gafisCheckinInfo.registerOrg
    logic06Rec.matchName = SysDepart.find_by_code(gafisCheckinInfo.registerOrg).headOption.getOrElse(gafisCheckinInfo.registerOrg).toString
    logic06Rec.matcher = gafisCheckinInfo.registerUser
    logic06Rec.matchDate = new SimpleDateFormat("yyyyMMdd").format(gafisCheckinInfo.registerTime)
    logic06Rec.head.fileLength = logic06Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    logic06Rec
  }
}

package nirvana.hall.api.internal.fpt

import java.text.SimpleDateFormat
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.JniLoaderUtil
import nirvana.hall.api.services.fpt.FPT5Service
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
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v70.gz.jpa.SysDepart
import nirvana.hall.v70.gz.services.versionfpt5.QueryServiceImpl

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
                      queryService: QueryServiceImpl,
                      matchRelationService: MatchRelationService,
                      extractor: FeatureExtractor,
                      implicit val dataSource:DataSource) extends FPT5Service{
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
            val mntData = try{
              extractByGAFISIMG(originalImage, false)
            }catch{
              case ex:ArithmeticException => extractByGAFISIMG(originalImage, false)
            }
            t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
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
            val mntData = extractByGAFISIMG(gafisImage, false)
            t.setStMntBytes(ByteString.copyFrom(mntData._1.toByteArray()))
            //          blob.setStBinBytes(ByteString.copyFrom(mntData._2.toByteArray()))
            val wsqImg = hallImageRemoteService.encodeGafisImage2Wsq(gafisImage)
            t.setStImageBytes(ByteString.copyFrom(wsqImg.toByteArray()))
          }

        }
    }
    tpCardBuilder
  }

  override def getLatentTaskPackage(taskId: String): LatenttaskPackage = ???

  override def addLatentTaskPackage(latenttaskPackage: LatenttaskPackage): Unit = ???

  override def getPrintTaskPackage(taskId: String): PrinttaskPackage = ???

  override def addPrintTaskPackage(printtaskPackage: PrinttaskPackage): Unit = ???

  override def getLTResultPackage(taskId: String): LtResultPackage = {
    val ltResultPackage = new LtResultPackage()
    ltResultPackage.taskId = taskId
    var orasid:Long = 0
    val sql = s"select zwbdxtlxms," +
      s"ysxt_asjbh," +
      s"asjbh," +
      s"xckybh," +
      s"ysxt_xczzhwbh," +
      s"xcwzbh," +
      s"xczzhwkbh," +
      s"ora_sid from fpt_lphit_request where rwbh = ? "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,taskId)
    }{rs=>
      orasid =  rs.getString("ora_sid").toLong
      val query = queryService.getQuery(orasid)
      ltResultPackage.comparisonSystemTypeDescript = rs.getString("zwbdxtlxms")
      ltResultPackage.originalSystemCaseId = rs.getString("ysxt_asjbh")
      ltResultPackage.caseId = rs.getString("asjbh")
      ltResultPackage.latentSurveyId = rs.getString("xckybh")
      ltResultPackage.originalSystemLatentFingerId = rs.getNString("ysxt_xczzhwbh")
      ltResultPackage.latentPhysicalId = rs.getString("xcwzbh")
      ltResultPackage.latentCardId = rs.getString("xczzhwkbh")
      ltResultPackage.comparisonUnitCode = query.userunitcode
      ltResultPackage.comparisonUnitName = SysDepart.find(query.userunitcode).name
      ltResultPackage.comparisonCompleteDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(query.finishtime)
    }
    val matchResult = queryService.getMatchResult(orasid)
    if(matchResult.nonEmpty){
      val lTResultMsgs = new ArrayBuffer[LTResultMsg]()
      matchResult.foreach{
        result =>
          val it = result.getCandidateResultList.iterator()
          var index = 1
          while(it.hasNext){
            val lTResultMsg = new LTResultMsg()
            val result = it.next()
              val tpCard = tPCardService.getTPCard(result.getObjectId)
                lTResultMsg.resultRanking = index
                lTResultMsg.resultScore = result.getScore
                lTResultMsg.resultOriginalSystemPersonId = tpCard.getStrMisPersonID
                lTResultMsg.resultJingZongPersonId = tpCard.getStrJingZongPersonId
                lTResultMsg.resultPersonId = tpCard.getStrCasePersonID
                lTResultMsg.resultCardId = tpCard.getStrCardID
                if(result.getPos.toString.matches("^([1-9])$")){
                  lTResultMsg.resultFingerPalmPostionCode = ("0"+result.getPos.toString)
                } else {
                  if(result.getPos > 20 ){
                    lTResultMsg.resultFingerPalmPostionCode = (result.getPos-10).toString
                  }else{
                    lTResultMsg.resultFingerPalmPostionCode = result.getPos.toString
                  }
                }
                lTResultMsgs += lTResultMsg
                index += 1
          }
          val  ltResultMsgSet = new LTResultMsgSet()
          ltResultMsgSet.resultMsg = lTResultMsgs.toArray
          ltResultPackage.ltResultMsgSet = ltResultMsgSet
      }
    }
    ltResultPackage
  }

  override def addLTResultPackage(ltResultPackage: LtResultPackage): Unit = ???

  override def getTlResultPackage(taskId: String): TlResultPackage = {
    val tlResultPackage = new TlResultPackage()
    var orasid:Long = 0
    tlResultPackage.taskId = taskId
    val sql = s"select zwbdxtlxms," +
      s"ysxt_asjxgrybh," +
      s"jzrybh," +
      s"asjxgrybh," +
      s"zzhwkbh," +
      s"ora_sid from fpt_tphit_request where rwbh = ? "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,taskId)
    }{rs=>
      tlResultPackage.comparisonSystemTypeDescript = rs.getString("zwbdxtlxms")
      tlResultPackage.originalSystemPersonId = rs.getString("ysxt_asjxgrybh")
      tlResultPackage.jingZongPersonId = rs.getString("jzrybh")
      tlResultPackage.personId = rs.getString("asjxgrybh")
      tlResultPackage.cardId = rs.getString("zzhwkbh")
      orasid =  rs.getString("ora_sid").toLong
      val query = queryService.getQuery(orasid)
      tlResultPackage.comparisonUnitCode = query.userunitcode
      tlResultPackage.comparisonUnitName = SysDepart.find(query.userunitcode).name
      tlResultPackage.comparisonCompleteDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(query.finishtime)
    }
    val matchResult = queryService.getMatchResult(orasid)
    if(matchResult.nonEmpty){
      val tlResultMsgs = new ArrayBuffer[TLResultMsg]()
      matchResult.foreach{
        result =>
          val it = result.getCandidateResultList.iterator()
          var index = 1
          while(it.hasNext){
            val tlResultMsg = new TLResultMsg()
            val result = it.next()
            val caseid = result.getObjectId.substring(0,result.getObjectId.length-2)
            val caseInfo = caseInfoService.getCaseInfo(caseid)
            val lpcard = lPCardService.getLPCard(result.getObjectId)
              tlResultMsg.resultRanking = index
              tlResultMsg.resultScore = result.getScore
              tlResultMsg.resultOriginalSystemCaseId = caseInfo.getStrCaseID
              tlResultMsg.resultCaseId = caseInfo.getStrJingZongCaseId
              tlResultMsg.resultLatentSurveyId = caseInfo.getStrSurveyId
              tlResultMsg.resultOriginalSystemLatentFingerPalmId = lpcard.getStrCardID
              tlResultMsg.resultLatentPhysicalId = lpcard.getStrPhysicalId
              tlResultMsg.resultCardId = " "
              tlResultMsg.resultFingerPalmPostionCode = if(result.getPos.toString.matches("^([1-9])$")) ("0"+result.getPos.toString) else (result.getPos.toString)
              tlResultMsgs += tlResultMsg
              index += 1
          }
      }
      val tlResultMsgSet = new TLResultMsgSet()
      tlResultMsgSet.resultMsg = tlResultMsgs.toArray
      tlResultPackage.tlResultMsgSet = tlResultMsgSet
    }
    tlResultPackage
  }

  override def addTlResultPackage(tlResultPackage: TlResultPackage): Unit = ???

  override def getTTResultPackage(taskId: String): TtResultPackage = {
    val ttResultPackage = new TtResultPackage()
    var orasid:Long = 0
    ttResultPackage.taskId = taskId
    val sql = s"select zwbdxtlxms," +
      s"ysxt_asjxgrybh," +
      s"jzrybh," +
      s"asjxgrybh," +
      s"zzhwkbh," +
      s"ora_sid from fpt_tphit_request where rwbh = ? "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,taskId)
    }{rs=>
      orasid = rs.getLong("ora_sid")
      val query = queryService.getQuery(orasid)
      ttResultPackage.comparisonSystemTypeDescript = rs.getString("zwbdxtlxms") //fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
      ttResultPackage.comparisonUnitCode = query.userunitcode
      ttResultPackage.comparisonUnitName = SysDepart.find(query.userunitcode).name
      ttResultPackage.comparisonCompleteDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(query.finishtime)
      ttResultPackage.originalSystemPersonId = rs.getString("ysxt_asjxgrybh") //query.keyid //"A5200000000002017040001"
      ttResultPackage.jingZongPersonId = rs.getString("jzrybh") //tpcard.getStrJingZongPersonId//"J5200000000002017040001"
      ttResultPackage.personId = rs.getString("asjxgrybh") //tpcard.getStrCasePersonID //"P5200000000002017040001"
      ttResultPackage.cardId = rs.getString("zzhwkbh") //tpcard.getStrCardID //query.keyid.substring(0,query.keyid.length-2)
      ttResultPackage.whetherFingerJudgmentMark = "1"
    }
    val matchResult = queryService.getMatchResult(orasid)
    if(matchResult.nonEmpty){
      val ttResultMsgs =  new ArrayBuffer[TTResultMsg]()
      matchResult.foreach{
        result  =>
          val it = result.getCandidateResultList.iterator()
          var index = 1
          while(it.hasNext){
            val ttResultMsg = new TTResultMsg
            val result = it.next()
            val tpCard = tPCardService.getTPCard(result.getObjectId)
            ttResultMsg.resultRanking = index
            ttResultMsg.resultScore = result.getScore
            ttResultMsg.resultOriginalSystemPersonId = tpCard.getStrMisPersonID
            ttResultMsg.resultJingZongPersonId = tpCard.getStrJingZongPersonId
            ttResultMsg.resultPersonId = tpCard.getStrCasePersonID
            ttResultMsg.resultCardId = tpCard.getStrCardID
            ttResultMsg.ttHitTypeCode = fpt5util.TT_MATCHED_MIRROR
            ttResultMsgs += ttResultMsg
            index += 1
          }
      }
      val ttResultMsgSet = new TtResultMsgSet
      ttResultMsgSet.resultMsg = ttResultMsgs.toArray
      ttResultPackage.ttResultMsgSet = ttResultMsgSet
    }
    ttResultPackage
  }

  override def addTTResultPackage(ttResultPackage: TtResultPackage): Unit = ???

  override def getLLResultPackage(taskId: String): LlResultPackage = {
    val llResultPackage = new LlResultPackage()
    llResultPackage.taskId = taskId
    var orasid:Long = 0
    val sql = s"select zwbdxtlxms,ysxt_asjbh,asjbh,xckybh,ysxt_xczzhwbh,xcwzbh,xczzhwkbh,ora_sid from fpt_lphit_request where rwbh = ? "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,taskId)
    } { rs =>
      orasid = rs.getLong("ora_sid")
      val query = queryService.getQuery(orasid)
      llResultPackage.comparisonSystemTypeDescript = rs.getString("zwbdxtlxms")
      llResultPackage.originalSystemCaseId = rs.getString("ysxt_asjbh")
      llResultPackage.caseId = rs.getString("asjbh")
      llResultPackage.latentSurveyId = rs.getString("xckybh")
      llResultPackage.originalSystemLatentFingerId = rs.getString("ysxt_xczzhwbh")
      llResultPackage.latentPhysicalId = rs.getString("xcwzbh")
      llResultPackage.latentCardId = rs.getString("xczzhwkbh")
      llResultPackage.comparisonUnitCode = query.userunitcode
      llResultPackage.comparisonUnitName = SysDepart.find(query.userunitcode).name
      llResultPackage.comparisonCompleteDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(query.finishtime)
    }
    val matchResult = queryService.getMatchResult(orasid)
    if(matchResult.nonEmpty){
      val llResultMsgs = new ArrayBuffer[LLResultMsg]()
      matchResult.foreach{
        result =>
          val it = result.getCandidateResultList.iterator()
          var index = 1
          while(it.hasNext){
            val llResultMsg = new LLResultMsg
            val result = it.next()
            val caseid = result.getObjectId.substring(0,result.getObjectId.length-2)
            val caseinfo = caseInfoService.getCaseInfo(caseid)
            val lpcard = lPCardService.getLPCard(result.getObjectId)
            llResultMsg.resultRanking = index
            llResultMsg.resultScore = result.getScore
            llResultMsg.resultOriginalSystemCaseId = caseinfo.getStrCaseID
            llResultMsg.resultCaseId = caseinfo.getStrJingZongCaseId
            llResultMsg.resultLatentSurveyId = caseinfo.getStrSurveyId
            llResultMsg.resultLatentPhysicalId = lpcard.getStrPhysicalId
            llResultMsg.resultOriginalSystemLatentFingerId = lpcard.getStrCardID
            llResultMsgs += llResultMsg
            index += 1
          }
      }
      val llResultMsgSet = new LlResultMsgSet
      llResultMsgSet.resultMsg = llResultMsgs.toArray
      llResultPackage.llResultMsgSet = llResultMsgSet
    }
    llResultPackage
  }

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

package nirvana.hall.api.internal.fpt

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File._
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FPTMntConverter
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult.MatchResultObject

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * Created by yuchen on 2016/12/2.
  * 将proto转换为FPT4File
  */
object FPTFileBuilder {
  /**
    * TPCard转换为FPT4File
    * @param card
    * @return
    */
  def convertTPCard2FPT4File(card: TPCard): FPT4File= {
    val fPT4File = new FPT4File
    val logic02Rec = convertTPCard2Logic02Res(card)
    fPT4File.logic02Recs = Array(logic02Rec)

    fPT4File.build()
  }

  /**
    * TPCard转换为十指指纹Logic02Rec
    * @param card 人员卡号
    * @return FPT4File对象
    */
  def convertTPCard2Logic02Res(card: TPCard): Logic02Rec = {
    val logic02Rec = new Logic02Rec
    logic02Rec.systemType = "1900" //1900 东方金指
    logic02Rec.personId = card.getStrPersonID
    logic02Rec.cardId = card.getStrCardID
    logic02Rec.personName = card.getText.getStrName
    logic02Rec.alias = card.getText.getStrAliasName
    logic02Rec.gender = card.getText.getNSex.toString
    logic02Rec.birthday = card.getText.getStrBirthDate
    logic02Rec.nativeplace = card.getText.getStrNation
    logic02Rec.nation = card.getText.getStrRace
    logic02Rec.idCardNo = card.getText.getStrIdentityNum
    logic02Rec.certificateType = card.getText.getStrCertifType
    logic02Rec.certificateNo = card.getText.getStrCertifID
    logic02Rec.door = card.getText.getStrHuKouPlaceCode
    logic02Rec.doorDetail = card.getText.getStrHuKouPlaceTail
    logic02Rec.address = card.getText.getStrAddrCode
    logic02Rec.addressDetail = card.getText.getStrAddr
    logic02Rec.category = card.getText.getStrPersonType
    logic02Rec.caseClass1Code = card.getText.getStrCaseType1
    logic02Rec.caseClass2Code = card.getText.getStrCaseType2
    logic02Rec.caseClass3Code = card.getText.getStrCaseType3
    logic02Rec.isCriminal = if(card.getText.getBHasCriminalRecord) "1" else "0"
    logic02Rec.criminalInfo = card.getText.getStrCriminalRecordDesc
    logic02Rec.gatherUnitCode = card.getText.getStrPrintUnitCode
    logic02Rec.gatherUnitName = card.getText.getStrPrintUnitName
    logic02Rec.gatherName = card.getText.getStrPrinter
    logic02Rec.gatherDate = card.getText.getStrPrintDate
    logic02Rec.assistLevel = card.getText.getNXieChaLevel.toString
    logic02Rec.bonus = card.getText.getStrPremium
    logic02Rec.assistPurpose = card.getText.getStrXieChaForWhat
    logic02Rec.relatedPersonId = card.getText.getStrRelPersonNo
    logic02Rec.relatedCaseId = card.getText.getStrRelCaseNo
    logic02Rec.assistTimeLimit = card.getText.getStrXieChaTimeLimit
    logic02Rec.assistAskingInfo = card.getText.getStrXieChaRequestComment
    logic02Rec.assistUnitCode = card.getText.getStrXieChaRequestUnitCode
    logic02Rec.assistUnitName = card.getText.getStrXieChaRequestUnitName
    logic02Rec.assistDate = card.getText.getStrXieChaDate
    logic02Rec.contact = card.getText.getStrXieChaContacter
    logic02Rec.contactPhone  = card.getText.getStrXieChaTelNo
    logic02Rec.approver = card.getText.getStrShenPiBy
    logic02Rec.remark = card.getText.getStrComment
    logic02Rec.isAssist = card.getText.getNXieChaFlag.toString
    //人像数据初始化为0
    logic02Rec.portraitDataLength = "0"

    val iter = card.getBlobList.iterator()
    var fingers = new mutable.ArrayBuffer[FingerTData]
    var sendNo = 1 //指纹序号，从1开始计数
    while (iter.hasNext){
      val blob = iter.next()
      blob.getType match {
        //指纹
        case ImageType.IMAGETYPE_FINGER =>
          val gafisMnt = new GAFISIMAGESTRUCT
          gafisMnt.fromStreamReader(blob.getStMntBytes.newInput())
          val fingerTData = FPTMntConverter.convertGafisMnt2FingerTData(gafisMnt)
          fingerTData.sendNo = sendNo.toString
          sendNo += 1
          fingerTData.extractMethod = fpt4code.EXTRACT_METHOD_A

          val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(blob.getStImageBytes.toByteArray)
          fingerTData.imgData = gafisImage.bnData
          fingerTData.imgDataLength = fingerTData.imgData.length.toString
          fingerTData.imgCompressMethod = fpt4code.gafisCprCodeToFPTCode(gafisImage.stHead.nCompressMethod)
          fingerTData.dataLength = fingerTData.toByteArray(AncientConstants.GBK_ENCODING).length.toString

          fingers += fingerTData
        //人像
        case ImageType.IMAGETYPE_FACE =>
          //正面人像
          if(blob.getFacefgp == FaceFgp.FACE_FRONT){
            logic02Rec.portraitData = blob.getStImageBytes.toByteArray
            logic02Rec.portraitDataLength = logic02Rec.portraitData.length.toString
          }
        case _ =>
      }
    }

    logic02Rec.fingers = fingers.toArray
    logic02Rec.sendFingerCount = fingers.length.toString
    logic02Rec.head.fileLength = logic02Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString

    logic02Rec
  }

  /**
    * 案件和现场指纹转换为FPT4File
    * @param caseInfo 案件信息
    * @param lPCard 现场卡片信息
    * @return
    */
  def convertCaseAndLPCard2Logic03Rec(caseInfo: Case, lPCard: Seq[LPCard]): Logic03Rec={
    val logic03Rec = new Logic03Rec
    val fingerLDataList = new ArrayBuffer[FingerLData]()
    logic03Rec.systemType = "1900"
    logic03Rec.caseId = if (22 == caseInfo.getStrCaseID.length) {
      "A".concat(caseInfo.getStrCaseID)
    } else {
      caseInfo.getStrCaseID
    }
    logic03Rec.cardId = caseInfo.getStrCaseID
    logic03Rec.caseClass1Code = caseInfo.getText.getStrCaseType1
    logic03Rec.caseClass2Code = caseInfo.getText.getStrCaseType2
    logic03Rec.caseClass3Code = caseInfo.getText.getStrCaseType3
    logic03Rec.occurDate = caseInfo.getText.getStrCaseOccurDate
    logic03Rec.occurPlaceCode = caseInfo.getText.getStrCaseOccurPlaceCode
    logic03Rec.occurPlace = caseInfo.getText.getStrCaseOccurPlace
    logic03Rec.caseBriefDetail = caseInfo.getText.getStrComment
    logic03Rec.isMurder = if (caseInfo.getText.getBPersonKilled) "1" else "0"
    logic03Rec.amount = caseInfo.getText.getStrMoneyLost
    logic03Rec.extractUnitCode = caseInfo.getText.getStrExtractUnitCode
    logic03Rec.extractUnitName = caseInfo.getText.getStrExtractUnitName
    logic03Rec.extractDate = caseInfo.getText.getStrExtractDate
    logic03Rec.extractor = caseInfo.getText.getStrExtractor
    logic03Rec.suspiciousArea1Code = caseInfo.getText.getStrSuspArea1Code
    logic03Rec.suspiciousArea2Code = caseInfo.getText.getStrSuspArea2Code
    logic03Rec.suspiciousArea3Code = caseInfo.getText.getStrSuspArea3Code
    logic03Rec.assistLevel = caseInfo.getText.getNSuperviseLevel.toString
    logic03Rec.bonus = caseInfo.getText.getStrPremium
    logic03Rec.assistUnitCode = caseInfo.getText.getStrXieChaRequestUnitName
    logic03Rec.assistUnitName = caseInfo.getText.getStrXieChaRequestUnitCode
    logic03Rec.assistDate = caseInfo.getText.getStrXieChaDate
    logic03Rec.isCaseAssist = caseInfo.getText.getNXieChaState.toString
    logic03Rec.isRevoke = "" ///获取到 是 31 caseInfo.getText.getNCancelFlag.toString
    logic03Rec.caseStatus = caseInfo.getText.getNCaseState.toString
    logic03Rec.fingerCount = lPCard.size.toString
    logic03Rec.sendFingerCount = lPCard.size.toString

    var sendNo = 1 //发送编号，从1开始计数
    lPCard.foreach{card =>
      var fingerLData = new FingerLData
      if(card.getBlob.getStMnt.nonEmpty){//判断是否有特征
        val gafisMnt =  new GAFISIMAGESTRUCT
        gafisMnt.fromStreamReader(card.getBlob.getStMntBytes.newInput())
        fingerLData = FPTMntConverter.convertGafisMnt2FingerLData(gafisMnt)
      }
      fingerLData.sendNo = sendNo + ""
      sendNo += 1
      fingerLData.fingerNo = card.getText.getStrSeq
      fingerLData.fingerId = card.getText.getStrCaseId
      fingerLData.isCorpse = if (card.getText.getBDeadBody) "1" else "0"
      fingerLData.corpseNo = card.getText.getStrDeadPersonNo
      fingerLData.remainPlace = card.getText.getStrRemainPlace
      fingerLData.ridgeColor = card.getText.getStrRidgeColor
      fingerLData.mittensBegNo = card.getText.getStrStart
      fingerLData.mittensEndNo = card.getText.getStrEnd
      fingerLData.isFingerAssist = card.getText.getNXieChaState.toString
      fingerLData.matchStatus = card.getText.getNBiDuiState.toString
      fingerLData.extractMethod = fpt4code.EXTRACT_METHOD_M
      val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(card.getBlob.getStImageBytes.toByteArray)
      fingerLData.imgCompressMethod = fpt4code.GAIMG_CPRMETHOD_NOCPR_CODE //现场指纹统一为原图
      fingerLData.imgData = gafisImage.bnData
      fingerLData.imgDataLength = fingerLData.imgData.length.toString
      fingerLData.dataLength = fingerLData.toByteArray(AncientConstants.GBK_ENCODING).length.toString
      fingerLDataList += fingerLData
    }
    logic03Rec.fingers = fingerLDataList.toArray
    logic03Rec.head.fileLength = logic03Rec.toByteArray(AncientConstants.GBK_ENCODING).length.toString
    logic03Rec
  }

  /**
    * 建造案件FPT文件
    * @return FPT4File对象
    */
  def buildLatentFpt(caseInfo: Case, lPCard: Seq[LPCard]): FPT4File = {
    val fPT4File = new FPT4File
    val logic03Rec = convertCaseAndLPCard2Logic03Rec(caseInfo,lPCard)
    fPT4File.logic03Recs = Array(logic03Rec)
    fPT4File.build()
  }

  /**
    * 构建比对查重候选FPT
    * @param  matchResult 比对结果集合
    * @return FPT4File对象
    */
  def buildLogic11RecFpt(matchResult:MatchResult,queryId:Long): FPT4File = {
    val fpt4File =  new FPT4File
    val resultCount = matchResult.getCandidateResultCount
    fpt4File.ttCandidateCount = resultCount + ""
    val logic11RecArray = new Array[Logic11Rec](resultCount)
    for(i <-0 to resultCount-1){
      val matchResultObject:MatchResultObject = matchResult.getCandidateResult(i)
      val logic11Rec = new Logic11Rec
      logic11Rec.head.fileLength = "0"                                //逻辑记录长度
      logic11Rec.head.dataType = "11"                                 //逻辑记录类型
      logic11Rec.index = ""                                           //序号
      logic11Rec.matchMethod = ""                                     //比对方法代码
      logic11Rec.matchUnitCode = "1900"                               //比对单位代码
      logic11Rec.matchFinshDate = ""                                  //比对完成时间
      logic11Rec.submitPersonId = ""                                  //送检指纹人员编号
      logic11Rec.personCount = resultCount + ""                       //实际返回人员数
      logic11Rec.candidatePlace = (i+1) + ""                          //侯选名次
      logic11Rec.candidateScore = matchResultObject.getScore + ""     //侯选得分
      logic11Rec.personId = matchResultObject.getObjectId + ""        //人员编号
      logic11Rec.head.fileLength = logic11Rec.toByteArray(AncientConstants.GBK_ENCODING).length + ""
      logic11RecArray.update(i, logic11Rec)
    }
    fpt4File.logic11Recs = logic11RecArray
    fpt4File.build()
  }

  /**
    * 构建比对正查候选FPT
    * @param  matchResult 比对结果集合
    * @param  ajid 案件ID
    * @param  queryId sid
    * @return FPT4File对象
    */
  def buildLogic09RecFpt(matchResult:MatchResult,ajid:String,queryId:Long): FPT4File = {
    val fpt4File =  new FPT4File
    val resultCount = matchResult.getCandidateResultCount
    fpt4File.sid = queryId  + ""
    fpt4File.ltCandidateCount = resultCount + ""
    val logic09RecArray = new Array[Logic09Rec](resultCount)
    for(i <-0 to resultCount-1){
      val matchResultObject:MatchResultObject = matchResult.getCandidateResult(i)
      val logic09Rec = new Logic09Rec
      logic09Rec.head.fileLength = "0"                                //逻辑记录长度
      logic09Rec.head.dataType = "09"                                 //逻辑记录类型
      logic09Rec.index = ""                                           //序号
      logic09Rec.matchMethod = ""                                     //比对方法代码
      logic09Rec.matchUnitCode = "1900"                               //提交结果单位代码
      logic09Rec.matchFinshDate = ""                                  //比对完成时间
      logic09Rec.authenticator = ""                                   //认证人姓名
      logic09Rec.caseId = ajid                                        //送检指纹案件编号
      logic09Rec.seqNo = ""                                           //送检指纹序号
      logic09Rec.tpFingerCount = resultCount + ""                     //实际返回十指指纹数
      logic09Rec.candidatePlace = (i+1) + ""                          //侯选名次
      logic09Rec.candidateScore = matchResultObject.getScore + ""     //侯选得分
      logic09Rec.personId = matchResultObject.getObjectId + ""        //人员编号
      logic09Rec.fgp = matchResultObject.getPos + ""                  //指位
      logic09Rec.head.fileLength = logic09Rec.toByteArray(AncientConstants.GBK_ENCODING).length + ""
      logic09RecArray.update(i, logic09Rec)
    }
    fpt4File.logic09Recs = logic09RecArray
    fpt4File.build()
  }

}

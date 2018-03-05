package nirvana.hall.v62.internal.c.gloclib

import com.google.protobuf.{ByteString, ProtocolStringList}
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.galoclp.{GCASEINFOSTRUCT, GLPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISIMAGESTRUCT, GAFISMICSTRUCT, GATEXTITEMSTRUCT}
import nirvana.hall.c.services.kernel.mnt_def.FINGERLATMNTSTRUCT
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v62.services.DictCodeConverter
import org.apache.commons.lang.StringUtils

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-14
 */
object galoclpConverter extends LoggerSupport{
  /**
   * convert protobuf object to latent card object
   * @param card protobuf object
   * @return gafis latent card object
   */
  def convertProtoBuf2GLPCARDINFOSTRUCT(card: LPCard): GLPCARDINFOSTRUCT= {
    val data = new GLPCARDINFOSTRUCT
    data.szCardID = card.getStrCardID
    //TODO 案件编号,操作信息
    data.stAdmData.szCaseID = card.getText.getStrCaseId
    data.stAdmData.szCUserName = card.getAdmData.getCreator
    data.stAdmData.szMUserName = card.getAdmData.getUpdator
//    data.stAdmData.tCDateTime =

    if(card.hasText) {
      val text = card.getText

      val buffer = mutable.Buffer[GATEXTITEMSTRUCT]()

      //text information
      appendTextStruct(buffer, "SeqNo",text.getStrSeq)
      appendTextStruct(buffer, "RemainPlace",text.getStrRemainPlace)
      appendTextStruct(buffer, "RidgeColor",text.getStrRidgeColor)
      if(text.hasBDeadBody)
        appendTextStruct(buffer, "IsUnknownBody",if(text.getBDeadBody) "1" else "0")
      appendTextStruct(buffer, "UnknownBodyCode",text.getStrDeadPersonNo)
      if(text.hasNXieChaState)
        appendTextStruct(buffer, "XieChaFlag",text.getNXieChaState.toString)
      if(text.hasNBiDuiState)
        appendTextStruct(buffer, "BiDuiState",text.getNBiDuiState.toString)
      appendTextStruct(buffer, "LatStart",text.getStrStart)
      appendTextStruct(buffer, "LatEnd",text.getStrEnd)

      appendTextStruct(buffer, "Comment",text.getStrComment)
      appendTextStruct(buffer, "CaptureMethod",text.getStrCaptureMethod)

      //FPT5.0新增
      appendTextStruct(buffer, "EvidenceNo",card.getStrPhysicalId) //现场物证编号
      appendTextStruct(buffer, "UnionFingerStartEvidenceNo",text.getStrStart) //现场指纹_连指开始_现场物证编号
      appendTextStruct(buffer, "UnionFingerEndEvidenceNo",text.getStrEnd) //现场指纹_连指结束_现场物证编号
      appendTextStruct(buffer, "FingerPalmCompStateCode",text.getNBiDuiState.toString) //现场指纹_指掌纹比对状态代码
      appendTextStruct(buffer, "FeatureGoupID",text.getStrFeatureGroupIdentifier) //现场指纹_特征组合标示符
      appendTextStruct(buffer, "PalmDeltaPositionTypeCode",text.getStrFeatureGroupDscriptInfo) //现场指纹_特征组合描述信息

      data.pstText_Data = buffer.toArray
      data.nTextItemCount = buffer.size.asInstanceOf[Short]
    }

    if(card.hasBlob){
      val blob = card.getBlob
      val mic = new GAFISMICSTRUCT
      var flag = 0
      if(blob.hasStMnt){
        mic.pstMnt_Data = blob.getStMntBytes.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length

        flag |= glocdef.GAMIC_ITEMFLAG_MNT
      }
      if(blob.hasStImage){
        val imgType = blob.getStImageBytes.byteAt(9) //see tagGAFISIMAGEHEADSTRUCT.bIsCompressed
        if(imgType == 1){ //image compressed
          mic.pstCpr_Data = blob.getStImageBytes.toByteArray
          mic.nCprLen = mic.pstCpr_Data.length
          flag |= glocdef.GAMIC_ITEMFLAG_CPR
        }else{
          mic.pstImg_Data = blob.getStImageBytes.toByteArray
          mic.nImgLen = mic.pstImg_Data.length
          flag |= glocdef.GAMIC_ITEMFLAG_IMG
        }
      }

      mic.nItemFlag = flag.asInstanceOf[Byte] //传送的特征类型 ,特征+图像 , 1 2 4 8

      blob.getType match{
        case FPTProto.ImageType.IMAGETYPE_FINGER =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_FACE =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_FACE.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_CARDIMG =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_DATA.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_PALM =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_PALM.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_VOICE =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_VOICE.asInstanceOf[Byte]
        case other =>
          throw new UnsupportedOperationException
      }
      mic.bIsLatent = 1 //是否位现场数据
      //TODO 设置现场指位

      data.pstMIC_Data = Array(mic)
      data.nMicItemCount = 1
    }
    data
  }

  /**
   * convert GLPCARDINFOSTRUCT object to LPCard object
   */
  def convertGLPCARDINFOSTRUCT2ProtoBuf(gCard: GLPCARDINFOSTRUCT): LPCard = {
    val card = LPCard.newBuilder()
    card.setStrCardID(gCard.szCardID)
    val text = card.getTextBuilder
    val admData = card.getAdmDataBuilder
    if(StringUtils.isNotEmpty(gCard.stAdmData.szCaseID)||StringUtils.isNotBlank(gCard.stAdmData.szCaseID)){
      text.setStrCaseId(gCard.stAdmData.szCaseID)
    }else{
      text.setStrCaseId(gCard.szCardID.substring(0,gCard.szCardID.length-2))
    }
    gCard.pstText_Data.foreach{ item =>
      val bytes = if (item.bIsPointer == 1) item.stData.textContent else item.stData.bnData
      val textContent = new String(bytes, AncientConstants.GBK_ENCODING).trim
      if (textContent.length > 0) {
        item szItemName match {
          case "SeqNo" =>
            text.setStrSeq(textContent)
          case "RemainPlace" =>
            text.setStrRemainPlace(textContent)
          case "RidgeColor" =>
            text.setStrRidgeColor(textContent)
          case "IsUnknownBody" =>
            text.setBDeadBody("1".equals(textContent))
          case "UnknownBodyCode" =>
            text.setStrDeadPersonNo(textContent)
          case "XieChaFlag" =>
            text.setNXieChaState(Integer.parseInt(textContent))
          case "BiDuiState" =>
            text.setNBiDuiState(Integer.parseInt(textContent))
          case "LatStart" =>
            text.setStrStart(textContent)
          case "LatEnd" =>
            text.setStrEnd(textContent)
          case "CaptureMethod" =>
            text.setStrCaptureMethod(textContent)
          case "Comment" =>
            text.setStrComment(textContent)
            //FPT5.0新增
          case "EvidenceNo" =>
            card.setStrPhysicalId(textContent) //现场物证编号
          case "UnionFingerStartEvidenceNo" =>
            text.setStrStart(textContent) //现场指纹_连指开始_现场物证编号
          case "UnionFingerEndEvidenceNo" =>
            text.setStrEnd(textContent) //现场指纹_连指结束_现场物证编号
          case "FingerPalmCompStateCode" =>
            text.setNBiDuiState(textContent.toInt) //现场指纹_指掌纹比对状态代码
          case "FeatureGoupID" =>
            text.setStrFeatureGroupIdentifier(textContent) //现场指纹_特征组合标示符
          case "PalmDeltaPositionTypeCode" =>
            text.setStrFeatureGroupDscriptInfo(textContent) //现场指纹_特征组合描述信息
           //hall7抓6 新增
          case "MicbUpdatorUserName" =>
            text.setStrMicbUpdatorUserName(textContent) //特征更改用户
          case "MicbUpdatorUnitCode" =>
            text.setStrMicbUpdatorUnitCode(textContent) //特征更改单位
          case "CreatorUnitCode" =>
            admData.setCreateUnitCode(textContent) //创建单位
          case "UpdatorUnitCode" =>
            admData.setUpdateUnitCode(textContent) //修改单位
          case other =>
        }
      }
    }
    val mic = card.getBlobBuilder
    gCard.pstMIC_Data.foreach{ item =>
      //特征
      if(item.nMntLen > 0){
        mic.setStMntBytes(ByteString.copyFrom(item.pstMnt_Data))
        //获取候选指位和纹型
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(item.pstMnt_Data)
        val mnt = new FINGERLATMNTSTRUCT().fromByteArray(gafisImage.bnData)
        convertFingerCode2FingerFgpList(mnt.FingerCode).foreach(mic.addFgp)
        convertRpCode2PatternType(mnt.RpCode).foreach(mic.addRp)
      }
      //纹线
      if(item.nBinLen > 0)
        mic.setStBinBytes(ByteString.copyFrom(item.pstBin_Data))
      //图像
      if(item.nCprLen > 0) {
        mic.setStImageBytes(ByteString.copyFrom(item.pstCpr_Data))
      }else if(item.nImgLen > 0) {
        mic.setStImageBytes(ByteString.copyFrom(item.pstImg_Data))
      }
      item.nItemType match {
        case glocdef.GAMIC_ITEMTYPE_FINGER =>
          mic.setType(ImageType.IMAGETYPE_FINGER)
        case glocdef.GAMIC_ITEMTYPE_FACE =>
          mic.setType(ImageType.IMAGETYPE_FACE)
        case glocdef.GAMIC_ITEMTYPE_DATA =>
          mic.setType(ImageType.IMAGETYPE_CARDIMG)
        case glocdef.GAMIC_ITEMTYPE_PALM =>
          mic.setType(ImageType.IMAGETYPE_PALM)
        case glocdef.GAMIC_ITEMTYPE_VOICE =>
          mic.setType(ImageType.IMAGETYPE_VOICE)
        case other =>
          mic.setType(ImageType.IMAGETYPE_UNKNOWN)
      }
    }
    //操作信息
    val stAdmData = gCard.stAdmData
    admData.setCreator(stAdmData.szCUserName)
    admData.setUpdator(stAdmData.szMUserName)
    admData.setCreateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tCDateTime))
    admData.setUpdateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tMDateTime))
    admData.setStrLtDate(DateConverter.convertAFISDateTime2String(stAdmData.tSubmitLLDate)) //LT发送时间
    admData.setStrLlDate(DateConverter.convertAFISDateTime2String(stAdmData.tSubmitLLDate)) //LL发送时间
    admData.setStrLtUser(stAdmData.szLTUserName)  //LT发送用户
    admData.setStrLlUser(stAdmData.szLLUserName)  //LT发送用户
    admData.setNLtCount(stAdmData.nAccuLTCount)   //LT累计发送次数
    admData.setNLlCount(stAdmData.nAccuLLCount)   //LL累计发送次数
    admData.setNEditCount(stAdmData.nEditCount.toInt)   //编辑次数
    card.setNLtStatus(stAdmData.bIsLTBroken)      //查案比中状态：1：比中；0：未比中 ; 6.2:是否LT破案
    //数据字典校验
    DictCodeConverter.convertLPCardText6to7(card.getTextBuilder)
    //seqNo如果没有，截取指纹编号后两位
    val seqNo = card.getText.getStrSeq
    if(seqNo == null && seqNo.length == 0){
      card.getTextBuilder.setStrSeq(card.getStrCardID.substring(card.getStrCardID.length - 2))
    }
    card.build()
  }

  /**
    * 候选指位转换
    * @param fingerCode 候选指位（按位计算）前10位依次是指位1-10
    * @return
    */
  private def convertFingerCode2FingerFgpList(fingerCode: Short): Seq[FingerFgp]={
    val fgpList = new ArrayBuffer[FingerFgp]()
    (6 until 16).map(i=> (i-5, (fingerCode & (1 << i)) > 0)).foreach{x=>
      if(x._2){
        fgpList += FingerFgp.valueOf(x._1)
      }
    }
    fgpList
  }

  /**
    * 候选纹型转换
    * @param rpCode 候选纹型（按位计算）前4位依次是 弓，左，右，斗
    * @return
    */
  private def convertRpCode2PatternType(rpCode: Byte): Seq[PatternType] ={
    val patternTypeList = new ArrayBuffer[PatternType]()
    (4 until 8).map(i=> (i-3, (rpCode & (1 << i)) > 0)).foreach{x=>
      if(x._2){
        patternTypeList += PatternType.valueOf(x._1)
      }
    }
    patternTypeList
  }

  //convert protocol string list as gafis GAKEYSTRUCT
  private def convertAsKeyArray(stringList:ProtocolStringList): Array[GAKEYSTRUCT] ={
    stringList.map{id=>
      val key = new GAKEYSTRUCT
      key.szKey = id
      key
    }.toArray
  }
  private[gloclib] def appendTextStruct(buffer:mutable.Buffer[GATEXTITEMSTRUCT],name:String,value:String):Unit = {
    if(value != null && value.length > 0) {
      val textStruct = new GATEXTITEMSTRUCT()
      textStruct.bIsPointer = 1
      textStruct.szItemName = name
      //convert as GBK encoding,because 6.2 need gbk encoding
      textStruct.stData.textContent = value.getBytes(AncientConstants.GBK_ENCODING)
      textStruct.nItemLen = textStruct.stData.textContent.length

      buffer += textStruct
    }

  }
  def convertProtobuf2GCASEINFOSTRUCT(protoCase:Case):GCASEINFOSTRUCT = {
    //TODO 添加数据长度校验
    val gafisCase = new GCASEINFOSTRUCT
    gafisCase.nItemFlag = (1 + 2 + 4 + 8 + 16).asInstanceOf[Byte]
    gafisCase.szCaseID = protoCase.getStrCaseID

    gafisCase.pstFingerID_Data = convertAsKeyArray(protoCase.getStrFingerIDList)
    gafisCase.nFingerCount = gafisCase.pstFingerID_Data.length.asInstanceOf[Short]

    gafisCase.pstPalmID_Data= convertAsKeyArray(protoCase.getStrPalmIDList)
    gafisCase.nPalmCount = gafisCase.pstPalmID_Data.length.asInstanceOf[Short]


    if (protoCase.hasText) {
      val text = protoCase.getText
      val buffer = mutable.Buffer[GATEXTITEMSTRUCT]()

      appendTextStruct(buffer, "CaseClass1Code", text.getStrCaseType1)
      appendTextStruct(buffer, "CaseClass2Code", text.getStrCaseType2)
      appendTextStruct(buffer, "CaseClass3Code", text.getStrCaseType3)
      appendTextStruct(buffer, "SuspiciousArea1Code", text.getStrSuspArea1Code)
      appendTextStruct(buffer, "SuspiciousArea2Code", text.getStrSuspArea2Code)
      appendTextStruct(buffer, "SuspiciousArea3Code", text.getStrSuspArea3Code)
      appendTextStruct(buffer, "CaseOccurDate", text.getStrCaseOccurDate)
      appendTextStruct(buffer, "CaseOccurPlaceCode", text.getStrCaseOccurPlaceCode)
      appendTextStruct(buffer, "CaseOccurPlaceTail", text.getStrCaseOccurPlace)

      if(text.hasNSuperviseLevel)
        appendTextStruct(buffer, "SuperviseLevel", text.getNSuperviseLevel.toString)

      appendTextStruct(buffer, "ExtractUnitCode", text.getStrExtractUnitCode)
      appendTextStruct(buffer, "ExtractUnitNameTail", text.getStrExtractUnitName)
      appendTextStruct(buffer, "Extractor1", text.getStrExtractor)
      appendTextStruct(buffer, "ExtractDate", text.getStrExtractDate.substring(0,8))
      appendTextStruct(buffer, "IllicitMoney", text.getStrMoneyLost)
      appendTextStruct(buffer, "Premium", text.getStrPremium)
      if(text.hasBPersonKilled)
        appendTextStruct(buffer, "HasPersonKilled", if (text.getBPersonKilled()) "1" else "0")
      appendTextStruct(buffer, "Comment", text.getStrComment)
      if(text.hasNCaseState)
        appendTextStruct(buffer, "CaseState", text.getNCaseState.toString)

      if(text.hasNXieChaState)
        appendTextStruct(buffer, "XieChaFlag", text.getNCaseState.toString)
      if(text.hasNCancelFlag)
        appendTextStruct(buffer, "CancelFlag", text.getNCancelFlag.toString)

      appendTextStruct(buffer, "XieChaDate", text.getStrXieChaDate)
      appendTextStruct(buffer, "XieChaRequestUnitName", text.getStrXieChaRequestUnitName)
      appendTextStruct(buffer, "XieChaRequestUnitCode", text.getStrXieChaRequestUnitCode)

      //FPT5.0新增
      appendTextStruct(buffer, "XKID", protoCase.getStrSurveyId) //现场勘验编号
      appendTextStruct(buffer, "AJID", protoCase.getStrJingZongCaseId) //警综案事件编号
      appendTextStruct(buffer, "ExtractorIDCard", text.getStrExtractorIdCard) //提取人身份证号码
      appendTextStruct(buffer, "ExtractorContactNO", text.getStrExtractorTel ) //提取人电话
      appendTextStruct(buffer, "CaseIncidentNo", protoCase.getStrJingZongCaseId) //案事件编号
      appendTextStruct(buffer, "ExtractDateTime", text.getStrExtractDate) // 提取时间日期时间类型
      appendTextStruct(buffer, "CaseSource", protoCase.getStrCaseSource.toString) //案件来源
      gafisCase.pstText_Data = buffer.toArray
      gafisCase.nTextItemCount = gafisCase.pstText_Data.length.asInstanceOf[Short]
    }
    gafisCase
  }

  /**
   * convert GCASEINFOSTRUCT object to protobuf object
   * @param gCase
   * @return
   */
  def convertGCASEINFOSTRUCT2Protobuf(gCase: GCASEINFOSTRUCT): Case = {
    val caseInfo = Case.newBuilder()
    caseInfo.setNCaseFingerCount(gCase.nFingerCount)
    caseInfo.setStrCaseID(gCase.szCaseID)
    val text = caseInfo.getTextBuilder
    val admData = caseInfo.getAdmDataBuilder
    text.setBPersonKilled(gCase.bHasPersonKilled == 49)
    text.setNCancelFlag(gCase.pstText_Data.length)
    gCase.pstText_Data.foreach{ item=>
      val bytes = if (item.bIsPointer == 1) item.stData.textContent else item.stData.bnData
      if (bytes != null){
        val textContent = new String(bytes,AncientConstants.GBK_ENCODING).trim
        if(textContent.length > 0){
          item szItemName match{
            case "CaseClass1Code" =>
              text.setStrCaseType1(textContent)
            case "CaseClass2Code" =>
              text.setStrCaseType2(textContent)
            case "CaseClass3Code" =>
              text.setStrCaseType3(textContent)
            case "SuspiciousArea1Code" =>
              text.setStrSuspArea1Code(textContent)
            case "SuspiciousArea2Code" =>
              text.setStrSuspArea2Code(textContent)
            case "SuspiciousArea3Code" =>
              text.setStrSuspArea3Code(textContent)
            case "CaseOccurDate" =>
              text.setStrCaseOccurDate(textContent)
            case "CaseOccurPlaceCode" =>
              text.setStrCaseOccurPlaceCode(textContent)
            case "CaseOccurPlaceTail" =>
              text.setStrCaseOccurPlace(textContent)
            case "SuperviseLevel" =>
              text.setNSuperviseLevel(Integer.parseInt(textContent))
            case "ExtractUnitCode" =>
              text.setStrExtractUnitCode(textContent)
            case "ExtractUnitNameTail" =>
              text.setStrExtractUnitName(textContent)
            case "Extractor1" =>
              text.setStrExtractor(textContent)
            case "ExtractDate" =>
              text.setStrExtractDate(textContent)
            case "IllicitMoney" =>
              text.setStrMoneyLost(textContent)
            case "Premium" =>
              text.setStrPremium(textContent)
            case "HasPersonKilled" =>
              text.setBPersonKilled("1".equals(textContent))
            case "Comment" =>
              text.setStrComment(textContent)
            case "CaseState" =>
              text.setNCaseState(Integer.parseInt(textContent))
            case "XieChaFlag" =>
              text.setNXieChaState(Integer.parseInt(textContent))
            case "CancelFlag" =>
              text.setNCancelFlag(Integer.parseInt(textContent))
            case "XieChaDate" =>
              text.setStrXieChaDate(textContent)
            case "XieChaRequestUnitName" =>
              text.setStrXieChaRequestUnitName(textContent)
            case "XieChaRequestUnitCode" =>
              text.setStrXieChaRequestUnitCode(textContent)
            case "CreatorUnitCode" =>
              caseInfo.getAdmDataBuilder.setCreateUnitCode(textContent)
            case "UpdatorUnitCode" =>
              caseInfo.getAdmDataBuilder.setUpdateUnitCode(textContent)
            case "CreateUserName" =>
              caseInfo.getAdmDataBuilder.setCreator(textContent)
            case "UpdateUserName" =>
              caseInfo.getAdmDataBuilder.setUpdator(textContent)
              //FPT5.0新增
            case "ExtractorIDCard" =>
              text.setStrExtractorIdCard(textContent) //提取人身份证号码
            case "ExtractorContactNO" =>
              text.setStrExtractorTel(textContent) //提取人电话
            case "CaseIncidentNo" =>
              caseInfo.setStrJingZongCaseId(textContent) //案事件编号
            case "ExtractDateTime" =>
              text.setStrExtractDate(textContent) //提取时间
            case "XKID" =>
              caseInfo.setStrSurveyId(textContent) //现场勘验编号
            case "AJID" =>
              caseInfo.setStrJingZongCaseId(textContent) //案事件编号
            case "CaseSource" =>
              caseInfo.setStrCaseSource(textContent.toInt) //案件来源
             //hall7抓6新增
            case "IsBroken" =>
              caseInfo.setNBrokenStatus(textContent.toInt) //是否破案
            case "IsLTBroken" =>
              caseInfo.setNThanStateLt(textContent.toInt) //是否LT破案/正查比中状态
            case "UpdateUserName" =>
              admData.setCreator(textContent) //
            case "UpdatorUnitCode" =>
              admData.setCreateUnitCode(textContent) //
            case other =>
              warn("{} not mapped", other)
          }
        }
      }
    }
    if(gCase.nFingerIDLen > 0){
      gCase.pstFingerID_Data.foreach{ f=>
        caseInfo.addStrFingerID(f.szKey)
      }
    }
    if(gCase.nPalmIDLen > 0){
      gCase.pstPalmID_Data.foreach{ f=>
        caseInfo.addStrPalmID(f.szKey)
      }
    }
    //操作信息
    admData.setCreateDatetime(DateConverter.convertAFISDateTime2String(gCase.tCreateDateTime))
    admData.setUpdateDatetime(DateConverter.convertAFISDateTime2String(gCase.tUpdateDateTime))
    //原始创建人信息
    val extractInfo = gCase.pstExtraInfo_Data
    if(extractInfo != null){
      admData.setCreator(extractInfo.szOrgScanner)
      admData.setCreateUnitCode(extractInfo.szOrgScanUnitCode)
    }
    caseInfo.setStrMisConnectCaseId(gCase.szMISCaseID)  //警务平台编号

    //数据校验
    DictCodeConverter.convertCaseInfoText6to7(caseInfo.getTextBuilder)

    caseInfo.build()
  }

  /**
    * 去掉6.2案件号头字母A
    *
    * @param caseId
    * @return
    */
  def dropCaseNoHeadLetter(caseId: String): String = {
    if (caseId.toUpperCase.startsWith(HallApiConstants.LPCARDNO_HEAD_LETTER))
      caseId.toUpperCase.drop(1)
    else caseId.toUpperCase
  }

  /**
    * 添加6.2案件号头字母A
    *
    * @param caseId
    * @return
    */
  def appendCaseNoHeadLetter(caseId: String): String = {
    if (!caseId.toUpperCase.startsWith(HallApiConstants.LPCARDNO_HEAD_LETTER)) {
      var newCaseId = HallApiConstants.LPCARDNO_HEAD_LETTER.concat(caseId.toUpperCase)
      newCaseId
    }else {
      caseId.toUpperCase
    }
  }
}

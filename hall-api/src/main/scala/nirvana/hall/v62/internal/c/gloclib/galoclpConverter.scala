package nirvana.hall.v62.internal.c.gloclib

import com.google.protobuf.{ByteString, ProtocolStringList}
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.galoclp.{GCASEINFOSTRUCT, GLPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISMICSTRUCT, GATEXTITEMSTRUCT}
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.{Case, ImageType, LPCard}
import nirvana.hall.v62.services.DictCodeConverter

import scala.collection.JavaConversions._
import scala.collection.mutable

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
    if(gCard.stAdmData.szCaseID != null){
      text.setStrCaseId(gCard.stAdmData.szCaseID)
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
          case other =>
        }
      }
    }
    val mic = card.getBlobBuilder
    gCard.pstMIC_Data.foreach{ item =>
      //特征
      if(item.nMntLen > 0)
        mic.setStMntBytes(ByteString.copyFrom(item.pstMnt_Data))
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
    val admData = card.getAdmDataBuilder
    val stAdmData = gCard.stAdmData
    admData.setCreator(stAdmData.szCUserName)
    admData.setUpdator(stAdmData.szMUserName)
    admData.setCreateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tCDateTime))
    admData.setUpdateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tMDateTime))
    //数据字典校验
    DictCodeConverter.convertLPCardText6to7(card.getTextBuilder)
    //seqNo如果没有，截取指纹编号后两位
    val seqNo = card.getText.getStrSeq
    if(seqNo == null && seqNo.length == 0){
      card.getTextBuilder.setStrSeq(card.getStrCardID.substring(card.getStrCardID.length - 2))
    }
    card.build()
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
    gafisCase.nItemFlag = (1 + 4 + 16).asInstanceOf[Byte]
    //GAFIS里面没有'A',这里去掉前缀
    gafisCase.szCaseID = protoCase.getStrCaseID
    if(gafisCase.szCaseID.charAt(0) == 'A')
      gafisCase.szCaseID = gafisCase.szCaseID.substring(1)

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
      appendTextStruct(buffer, "ExtractDate", text.getStrExtractDate)
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
    text.setBPersonKilled(gCase.bHasPersonKilled == 48)
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
    val admData = caseInfo.getAdmDataBuilder
    admData.setCreateDatetime(DateConverter.convertAFISDateTime2String(gCase.tCreateDateTime))
    admData.setUpdateDatetime(DateConverter.convertAFISDateTime2String(gCase.tUpdateDateTime))

    //数据校验
    DictCodeConverter.convertCaseInfoText6to7(caseInfo.getTextBuilder)

    caseInfo.build()
  }
}

package nirvana.hall.v62.internal.c.gloclib

import com.google.protobuf.ByteString
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.{GAFISMICSTRUCT, GATEXTITEMSTRUCT}
import nirvana.hall.protocol.v62.FPTProto
import nirvana.hall.protocol.v62.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter.appendTextStruct

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-14
 */
object galoctpConverter {
  /**
   * convert protobuf object to gafis' TPCard
   * @param card protobuf object
   * @return gafis TPCard
   * @see FPTBatchUpdater.cpp #812
   */
  def convertProtoBuf2GTPCARDINFOSTRUCT(card: TPCard): GTPCARDINFOSTRUCT={
    val data = new GTPCARDINFOSTRUCT
    data.szCardID = card.getStrCardID
    data.stAdmData.szMISPersonID = card.getStrPersonID
    data.stAdmData.szPersonID = card.getStrPersonID

    if(card.hasText) {
      val text = card.getText

      val buffer = mutable.Buffer[GATEXTITEMSTRUCT]()

      //text information
      appendTextStruct(buffer, "Name",text.getStrName)
      appendTextStruct(buffer, "Alias",text.getStrAliasName)

      if(text.hasNSex)
        appendTextStruct(buffer, "SexCode",text.getNSex.toString)
      appendTextStruct(buffer, "BirthDate",text.getStrBirthDate)
      appendTextStruct(buffer, "ShenFenID",text.getStrIdentityNum)
      appendTextStruct(buffer, "HuKouPlaceCode",text.getStrBirthAddrCode)
      appendTextStruct(buffer, "HuKouPlaceTail",text.getStrBirthAddr)
      appendTextStruct(buffer, "AddressCode",text.getStrAddrCode)
      appendTextStruct(buffer, "AddressTail",text.getStrAddr)
      appendTextStruct(buffer, "PersonClassCode",text.getStrPersonType)
      appendTextStruct(buffer, "CaseClass1Code",text.getStrCaseType1)
      appendTextStruct(buffer, "CaseClass2Code",text.getStrCaseType2)
      appendTextStruct(buffer, "CaseClass3Code",text.getStrCaseType3)
      appendTextStruct(buffer, "PrinterUnitCode",text.getStrPrintUnitCode)
      appendTextStruct(buffer, "PrinterUnitNameTail",text.getStrPrintUnitName)
      appendTextStruct(buffer, "PrinterName",text.getStrPrinter)
      appendTextStruct(buffer, "PrintDate",text.getStrPrintDate)
      appendTextStruct(buffer, "Comment",text.getStrComment)
      appendTextStruct(buffer, "Nationality",text.getStrNation)
      appendTextStruct(buffer, "RaceCode",text.getStrRace)
      appendTextStruct(buffer, "CertificateCode",text.getStrCertifID)
      appendTextStruct(buffer, "CertificateType",text.getStrCertifType)
      if(text.hasBHasCriminalRecord)
        appendTextStruct(buffer, "IsCriminalRecord",if(text.getBHasCriminalRecord) "1" else "0")
      appendTextStruct(buffer, "CriminalRecordDesc",text.getStrCriminalRecordDesc)

      /*
      PARSETEXT_BEGIN(STR_PREMIUM",text.get)
      PARSETEXT_BEGIN(STR_XIECHAFLAG",text.get)
      PARSETEXT_BEGIN(STR_XIECHAREQUESTUNITNAME",text.get)
      PARSETEXT_BEGIN(STR_XIECHAREQUESTUNITCODE",text.get)
      PARSETEXT_BEGIN(STR_XIECHALEVEL",text.get)
      PARSETEXT_BEGIN(STR_XIECHAFORWHAT",text.get)
      PARSETEXT_BEGIN(STR_RELPERSONNO",text.get)
      PARSETEXT_BEGIN(STR_RELCASENO",text.get)
      PARSETEXT_BEGIN(STR_XIECHATIMELIMIT",text.get)
      PARSETEXT_BEGIN(STR_XIECHADATE",text.get)
      PARSETEXT_BEGIN(STR_XIECHAREQUESTCOMMENT",text.get)
      PARSETEXT_BEGIN(STR_XIECHACONTACTER",text.get)
      PARSETEXT_BEGIN(STR_XIECHATELNO",text.get)
      PARSETEXT_BEGIN(STR_SHENPIBY",text.get)
      */

      data.pstText_Data = buffer.toArray
      data.nTextItemCount = data.pstText_Data.length.asInstanceOf[Byte]
    }

    //mic
    val mics = card.getBlobList.map{blob=>
      val mic = new GAFISMICSTRUCT
      var flag = 0
      if(blob.hasStMnt){
        mic.pstMnt_Data = blob.getStMntBytes.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length

        flag |= glocdef.GAMIC_ITEMFLAG_MNT
      }
      if(blob.hasStImage){
        val imgType = blob.getStImageBytes.byteAt(9) //see GAFISIMAGEHEADSTRUCT.bIsCompressed
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

      //TODO 纹线数据？
      mic.nItemData = blob.getFgp.getNumber.asInstanceOf[Byte] //指位信息

      mic.nItemFlag = flag.asInstanceOf[Byte] //传送的特征类型 ,特征+图像 , 1 2 4 8

      blob.getType match{
        case FPTProto.ImageType.IMAGETYPE_FINGER =>
          if(blob.getBPlain)
            mic.nItemType = glocdef.GAMIC_ITEMTYPE_TPLAIN.asInstanceOf[Byte]
          else
            mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]

          //指位信息
          if(blob.hasFgp){
            mic.nItemData = blob.getFgp.getNumber.asInstanceOf[Byte]
          }
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
      mic.bIsLatent = 0 //是否位现场数据

      mic
    }

    data.pstMIC_Data = mics.toArray
    data.nMicItemCount = mics.size.asInstanceOf[Byte]

    data
  }

  /**
   * convert gafis' tpcard object to protobuf
   * @param data
   * @return
   */
  def convertGTPCARDINFOSTRUCT2ProtoBuf(data: GTPCARDINFOSTRUCT): TPCard = {
    val card = TPCard.newBuilder()
    card.setStrCardID(data.szCardID)
    if(data.stAdmData.szPersonID != null)
      card.setStrPersonID(data.stAdmData.szPersonID)

    val text = card.getTextBuilder
    data.pstText_Data.foreach{ item =>
      val bytes = if (item.bIsPointer == 1) item.stData.textContent else item.stData.bnData
      if (bytes != null){
        val textContent = new String(bytes, AncientConstants.GBK_ENCODING).trim
        if(textContent.length > 0){
          item szItemName match{
            case "Name" =>
              text.setStrName(textContent)
            case "Alias" =>
              text.setStrAliasName(textContent)
            case "SexCode" =>
              text.setNSex(Integer.parseInt(textContent))
            case "BirthDate" =>
              text.setStrBirthDate(textContent)
            case "ShenFenID" =>
              text.setStrIdentityNum(textContent)
            case "HuKouPlaceCode" =>
              text.setStrBirthAddrCode(textContent)
            case "HuKouPlaceTail" =>
              text.setStrBirthAddr(textContent)
            case "AddressCode" =>
              text.setStrAddrCode(textContent)
            case "AddressTail" =>
              text.setStrAddr(textContent)
            case "PersonClassCode" =>
              text.setStrPersonType(textContent)
            case "CaseClass1Code" =>
              text.setStrCaseType1(textContent)
            case "CaseClass2Code" =>
              text.setStrCaseType2(textContent)
            case "CaseClass3Code" =>
              text.setStrCaseType3(textContent)
            case "PrinterUnitCode" =>
              text.setStrPrintUnitCode(textContent)
            case "PrinterUnitNameTail" =>
              text.setStrPrintUnitName(textContent)
            case "PrinterName" =>
              text.setStrPrinter(textContent)
            case "PrintDate" =>
              text.setStrPrintDate(textContent)
            case "Comment" =>
              text.setStrComment(textContent)
            case "Nationality" =>
              text.setStrNation(textContent)
            case "RaceCode" =>
              text.setStrRace(textContent)
            case "CertificateCode" =>
              text.setStrCertifID(textContent)
            case "CertificateType" =>
              text.setStrCertifType(textContent)
            case "IsCriminalRecord" =>
              text.setBHasCriminalRecord("1".equals(textContent))
            case "CriminalRecordDesc" =>
              text.setStrCriminalRecordDesc(textContent)
            case other =>
          }
        }
      }
    }

    data.pstMIC_Data.foreach{ mic =>
      val data = card.addBlobBuilder()
      if(mic.pstMnt_Data != null)
        data.setStMntBytes(ByteString.copyFrom(mic.pstMnt_Data))
      if(mic.pstCpr_Data != null)
        data.setStImageBytes(ByteString.copyFrom(mic.pstCpr_Data))
      data.setFgp(FingerFgp.valueOf(mic.nItemData))
      mic.nItemType match {
        case glocdef.GAIMG_IMAGETYPE_FINGER =>
          data.setType(ImageType.IMAGETYPE_FINGER)
        case glocdef.GAMIC_ITEMTYPE_PLAINFINGER =>
          //四联指,不确定
          data.setType(ImageType.IMAGETYPE_UNKNOWN)
        case glocdef.GAMIC_ITEMTYPE_TPLAIN =>
          data.setType(ImageType.IMAGETYPE_FINGER)
          data.setBPlain(true)
        case glocdef.GAMIC_ITEMTYPE_FACE =>
          data.setType(ImageType.IMAGETYPE_FACE)
        case glocdef.GAMIC_ITEMTYPE_DATA =>
          data.setType(ImageType.IMAGETYPE_CARDIMG)
        case glocdef.GAMIC_ITEMTYPE_PALM =>
          data.setType(ImageType.IMAGETYPE_PALM)
        case glocdef.GAMIC_ITEMTYPE_VOICE =>
          data.setType(ImageType.IMAGETYPE_VOICE)
        case other =>
      }
    }

    card.build()
  }

}

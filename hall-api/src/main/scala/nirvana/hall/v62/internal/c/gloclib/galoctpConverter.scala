package nirvana.hall.v62.internal.c.gloclib

import java.io.ByteArrayOutputStream

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef._
import nirvana.hall.protocol.api.FPTProto
import nirvana.hall.protocol.api.FPTProto.TPCard.TPCardBlob
import nirvana.hall.protocol.api.FPTProto._
import nirvana.hall.v62.internal.c.FgpConverter
import nirvana.hall.v62.internal.c.gloclib.galoclpConverter.appendTextStruct
import nirvana.hall.v62.services.DictCodeConverter

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-14
 */
object galoctpConverter extends LoggerSupport{
  /**
   * convert protobuf object to gafis' TPCard
    *
    * @param card protobuf object
   * @return gafis TPCard
   * @see FPTBatchUpdater.cpp #812
   */
  def convertProtoBuf2GTPCARDINFOSTRUCT(card: TPCard): GTPCARDINFOSTRUCT={
    val data = new GTPCARDINFOSTRUCT
    data.szCardID = card.getStrCardID
    data.stAdmData.szMISPersonID = card.getStrMisPersonID //人员编号
//    data.stAdmData.szPersonID = card.getStrPersonID//重卡组号

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
      appendTextStruct(buffer, "HuKouPlaceCode",text.getStrHuKouPlaceCode)
      appendTextStruct(buffer, "HuKouPlaceTail",text.getStrHuKouPlaceTail)
      appendTextStruct(buffer, "AddressCode",text.getStrAddrCode)
      appendTextStruct(buffer, "AddressTail",text.getStrAddr)
      appendTextStruct(buffer, "PersonClassCode",text.getStrPersonType)
      appendTextStruct(buffer, "CaseClass1Code",text.getStrCaseType1)
      appendTextStruct(buffer, "CaseClass2Code",text.getStrCaseType2)
      appendTextStruct(buffer, "CaseClass3Code",text.getStrCaseType3)
      appendTextStruct(buffer, "PrinterUnitCode",text.getStrPrintUnitCode)
      appendTextStruct(buffer, "PrinterUnitNameTail",text.getStrPrintUnitName)
      appendTextStruct(buffer, "PrinterName",text.getStrPrinter)
      appendTextStruct(buffer, "PrintDate",text.getStrPrintDate.substring(0,8))
      appendTextStruct(buffer, "Comment",text.getStrComment)
      appendTextStruct(buffer, "Nationality",text.getStrNation)
      appendTextStruct(buffer, "RaceCode",text.getStrRace)
      appendTextStruct(buffer, "CertificateCode",text.getStrCertifID)
      appendTextStruct(buffer, "CertificateType",text.getStrCertifType)
      //FPT5.0新增
      appendTextStruct(buffer, "srcSysCaseRelatePersonNO",card.getStrMisPersonID)  //原始系统_案事件相关人员编号
      appendTextStruct(buffer, "policeIntegratedPersonNO",card.getStrJingZongPersonId) //警综人员编号
      appendTextStruct(buffer, "caseRelatePersonNO",card.getStrCasePersonID) //案事件相关人员编号
      //采集原因代码
      val captureInfoReasonCode = card.getCaptureInfoReasonCode.split(",")
      captureInfoReasonCode.foreach{
        code =>
          if (captureInfoReasonCode.indexOf(code) == 0){
            appendTextStruct(buffer, "personClassCode",code)
          } else{
            appendTextStruct(buffer, "personClassCode"+(captureInfoReasonCode.indexOf(code)+1),code)
          }
      }
      appendTextStruct(buffer, "printerID",text.getStrPrinterIdCardNo) //捺印人员身份证号码
      appendTextStruct(buffer, "printerPhone",text.getStrPrinterPhone) //捺印人员联系电话
      appendTextStruct(buffer, "PrintDateTime",text.getStrPrintDate) //新增FPT5.0捺印时间
      //TODO 指掌纹缺失情况代码
      //TODO 掌纹三角点位置类型代码
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

    //mic TODO 人像7.0存在有头和没头数据，暂时不处理人像
    val mics = card.getBlobList.filter(_.getType != ImageType.IMAGETYPE_FACE).map{blob=>
      val mic = new GAFISMICSTRUCT
      var flag = 0
      if(blob.hasStMnt){
        mic.pstMnt_Data = blob.getStMntBytes.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length

        flag |= glocdef.GAMIC_ITEMFLAG_MNT
      }
      if(blob.hasStImage){
        val img = new GAFISIMAGESTRUCT().fromStreamReader(blob.getStImageBytes.newInput())
//        val imgType = blob.getStImageBytes.byteAt(9) //see GAFISIMAGEHEADSTRUCT.bIsCompressed
        val imgType = img.stHead.bIsCompressed
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
      if(blob.hasStBin){
          mic.pstBin_Data = blob.getStBinBytes.toByteArray
          mic.nBinLen = mic.pstBin_Data.length
          flag |= glocdef.GAMIC_ITEMFLAG_BIN
      }
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
        case FPTProto.ImageType.IMAGETYPE_FACE => //人像
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_FACE.asInstanceOf[Byte]
          blob.getFacefgp match {
            case FaceFgp.FACE_FRONT => mic.nItemData = 1
            case FaceFgp.FACE_LEFT => mic.nItemData = 2
            case FaceFgp.FACE_RIGHT => mic.nItemData = 3
          }

          val sMagic = new Array[Byte](4)
          val imageBytes = blob.getStImageBytes.toByteArray
          System.arraycopy(imageBytes, 4, sMagic,0, 4)
          if("BLOB".equals(new String(sMagic))){//gafis7
            val headData = new Array[Byte](64)
            System.arraycopy(imageBytes, 64, headData,0, 64)
            val gafis7Head= new GAFIS7LOB_IMGHEADSTRUCT
            gafis7Head.fromByteArray(headData)
            val gafis6Head = convertHead7to6(gafis7Head)
            val data = new ByteArrayOutputStream()
            data.write(gafis6Head.toByteArray())
            data.write(imageBytes, 128, imageBytes.length-128)

            mic.pstCpr_Data = data.toByteArray
            mic.nCprLen = mic.pstCpr_Data.length
            mic.pstImg_Data = null
            mic.nImgLen=0
          }
        case FPTProto.ImageType.IMAGETYPE_CARDIMG =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_DATA.asInstanceOf[Byte]
        case FPTProto.ImageType.IMAGETYPE_PALM =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_PALM.asInstanceOf[Byte]
          mic.nItemData = FgpConverter.convertPalmFgp2GTPIO_ITEMINDEX(blob.getPalmfgp)
        case FPTProto.ImageType.IMAGETYPE_VOICE =>
          mic.nItemType = glocdef.GAMIC_ITEMTYPE_VOICE.asInstanceOf[Byte]
        case other =>
          error("mic type {} not supported for {}",other,data.szCardID)
//          new UnsupportedOperationException("item type "+other+" not supported ")
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
    *
    * @param data
   * @return
   */
  def convertGTPCARDINFOSTRUCT2ProtoBuf(data: GTPCARDINFOSTRUCT): TPCard = {
    val card = TPCard.newBuilder()
    card.setStrCardID(data.szCardID)
    card.setStrPersonID(data.stAdmData.szMISPersonID)
    if(data.stAdmData.szPersonID!= null){ //赋值重卡号
      card.setStrMisPersonID(data.stAdmData.szPersonID)
    }
    val text = card.getTextBuilder
    val admData = card.getAdmDataBuilder
    //采集原因代码
    val array = scala.collection.mutable.ArrayBuffer[String]()
    data.pstText_Data.foreach{ item =>
      val bytes = if (item.bIsPointer == 1) item.stData.textContent else item.stData.bnData
      if (bytes != null){
        val textContent = new String(bytes, AncientConstants.GBK_ENCODING).trim
        text.setStrComment("")  //增加备注初始值，用于南京性别匹配同步，未匹配则在case时直接赋值
        if(textContent.length > 0){
          item szItemName match{
            case "Name" =>
              text.setStrName(textContent)
            case "Alias" =>
              text.setStrAliasName(textContent)
            case "SexCode" =>
              if(textContent.matches("\\d")) {
                if(textContent.equals("1") || textContent.equals("2"))
                  text.setNSex(Integer.parseInt(textContent))
                else {
                  text.setNSex(0)
                  text.setStrComment(text.getStrComment + "性别：" +textContent)
                }
              }else{
                text.setNSex(0)
                text.setStrComment(text.getStrComment + "性别："+ textContent)
              }
            case "BirthDate" =>
              if(textContent.matches("\\d{8}"))
                text.setStrBirthDate(textContent)
            case "ShenFenID" =>
              text.setStrIdentityNum(textContent)
            case "HuKouPlaceCode" =>
              text.setStrHuKouPlaceCode(textContent)
            case "HuKouPlaceTail" =>
              text.setStrHuKouPlaceTail(textContent)
            case "AddressCode" =>
              text.setStrAddrCode(textContent)
            case "AddressTail" =>
              text.setStrAddr(textContent)
//            case "PersonType" =>        //移动到下方赋值
//              text.setStrPersonType(data.stAdmData.szPersonType)
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
              if(textContent.matches("\\d{8}"))
                text.setStrPrintDate(textContent+"000000")
            case "Comment" =>
              text.setStrComment(textContent + text.getStrComment())
            case "Nationality" =>
              text.setStrNation(textContent)
            case "RaceCode" =>
              text.setStrRace(textContent)
            case "CertificateCode" =>
              text.setStrCertifID(textContent)
            case "CertificateType" =>
              text.setStrCertifType(textContent)
            case "IsCriminalRecord" =>
              text.setBHasCriminalRecord("49".equals(textContent))  //南京前科标识转换
            case "CriminalRecordDesc" =>
              text.setStrCriminalRecordDesc(textContent)
            case "CreatorUnitCode" =>
              admData.setCreateUnitCode(textContent)
            case "UpdatorUnitCode" =>
              admData.setUpdateUnitCode(textContent)
            //FPT5.0新增
//            case "SrcSysCaseRelatePersonNO" =>      //移动到下方赋值
//              card.setStrMisPersonID(textContent)  //原始系统_案事件相关人员编号
            case "PoliceIntegratedPersonNO" =>
              card.setStrJingZongPersonId(textContent) //警综人员编号
            case "CaseRelatePersonNO" =>
              card.setStrCasePersonID(textContent) //案事件相关人员编号
            case "PersonClassCode" =>
              text.setStrPersonClassCode(textContent) //人员类别代码
              array += textContent
            case "PersonClassCode2" =>
              array += textContent
            case "PersonClassCode3" =>
              array += textContent
            case "PersonClassCode4" =>
              array += textContent
            case "PersonClassCode5" =>
              array += textContent
            case "PrinterID" =>
              text.setStrPrinterIdCardNo(textContent) //捺印人员身份证号码
            case "PrinterPhone" =>
             text.setStrPrinterPhone(textContent) //捺印人员联系电话
            case "PrintDateTime" =>
              text.setStrPrintDate(textContent) //真对FPT5.0捺印日期时间新增字段
            case "MicbUpdatorUserName" =>
              text.setStrMicUpdatorUsername(textContent) //特征更改用户
            case "MicbUpdatorUnitCode" =>
              text.setStrMicUpdatorUnitcode(textContent) //特征更改单位
            case "MISConnectPersonID" =>
              text.setStrPsisNo(textContent)  //警务平台编号
            case "NamePinYin" =>
              text.setStrSpellName(textContent) //姓名拼音
            case other =>
              warn("{} not mapped", other)
          }
        }
      }
    }
    card.setCaptureInfoReasonCode(array.mkString(","))
    //判断是否有图像数据
    if(data.nMicItemCount > 0){
      data.pstMIC_Data.foreach{ mic =>
        val micDataBuilder = TPCardBlob.newBuilder() //card.addBlobBuilder()
        //特征
        if(mic.pstMnt_Data != null && mic.pstMnt_Data.length > 64)
          micDataBuilder.setStMntBytes(ByteString.copyFrom(mic.pstMnt_Data))
        //压缩图像
        if(mic.pstCpr_Data != null && mic.pstCpr_Data.length > 64)
          micDataBuilder.setStImageBytes(ByteString.copyFrom(mic.pstCpr_Data))
        //原图
        if(mic.pstImg_Data != null && mic.pstImg_Data.length > 64)
          micDataBuilder.setStOriginalImageBytes(ByteString.copyFrom(mic.pstImg_Data))
        //纹线
        if(mic.pstBin_Data != null && mic.pstBin_Data.length > 64)
          micDataBuilder.setStBinBytes(ByteString.copyFrom(mic.pstBin_Data))
        //图像类型
        mic.nItemType match {
          case glocdef.GAIMG_IMAGETYPE_FINGER =>
            micDataBuilder.setType(ImageType.IMAGETYPE_FINGER)
            micDataBuilder.setFgp(FingerFgp.valueOf(mic.nItemData))
          case glocdef.GAMIC_ITEMTYPE_PLAINFINGER =>
            //四联指,不确定
            micDataBuilder.setType(ImageType.IMAGETYPE_UNKNOWN)
          case glocdef.GAMIC_ITEMTYPE_TPLAIN =>
            micDataBuilder.setType(ImageType.IMAGETYPE_FINGER)
            micDataBuilder.setBPlain(true)
            micDataBuilder.setFgp(FingerFgp.valueOf(mic.nItemData))
          case glocdef.GAMIC_ITEMTYPE_FACE =>
            micDataBuilder.setType(ImageType.IMAGETYPE_FACE)
            mic.nItemData match {
              case 1 => micDataBuilder.setFacefgp(FaceFgp.FACE_FRONT)
              case 2 => micDataBuilder.setFacefgp(FaceFgp.FACE_LEFT)
              case 3 => micDataBuilder.setFacefgp(FaceFgp.FACE_RIGHT)
              case other => micDataBuilder.setFacefgp(FaceFgp.FACE_UNKNOWN)
            }
          case glocdef.GAMIC_ITEMTYPE_DATA =>
            micDataBuilder.setType(ImageType.IMAGETYPE_CARDIMG)
            micDataBuilder.setCardimgfgp(CARDIMG.valueOf(mic.nItemData))
          case glocdef.GAMIC_ITEMTYPE_PALM =>
            micDataBuilder.setType(ImageType.IMAGETYPE_PALM)
            micDataBuilder.setPalmfgp(FgpConverter.convertGTPIO_ITEMINDEX2PalmFgp(mic.nItemData))
          case glocdef.GAMIC_ITEMTYPE_VOICE =>
            micDataBuilder.setType(ImageType.IMAGETYPE_VOICE)
          case glocdef.GAMIC_ITEMTYPE_SIGNATURE =>
            micDataBuilder.setType(ImageType.IMAGETYPE_SIGNATURE)
            micDataBuilder.setSigcprfgp(SignatureCpr.valueOf(mic.nItemData))
          case other =>
            logger.error("mic type {} not supported for {}",other,data.szCardID)
            micDataBuilder.setType(ImageType.IMAGETYPE_UNKNOWN)
        }
        //过滤掉image type为0的数据
        if(micDataBuilder.getType !=  ImageType.IMAGETYPE_UNKNOWN)
          card.addBlob(micDataBuilder)
      }
    }

    //操作信息
    val stAdmData = data.stAdmData
    admData.setCreateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tCDateTime))
    admData.setUpdateDatetime(DateConverter.convertAFISDateTime2String(stAdmData.tMDateTime))
    admData.setCreator(stAdmData.szCUserName)
    admData.setUpdator(stAdmData.szMUserName)
    admData.setStrTlDate(DateConverter.convertAFISDateTime2String(stAdmData.tSubmitTLDate))
    admData.setStrTtDate(DateConverter.convertAFISDateTime2String(stAdmData.tSubmitTTDate))
    admData.setNTtCount(stAdmData.nAccuTTCount)
    admData.setNTlCount(stAdmData.nAccuTLCount)
    admData.setStrTtUser(stAdmData.szTTUserName)
    admData.setStrTlUser(stAdmData.szTLUserName)
    admData.setNEditCount(stAdmData.nEditCount)
    text.setStrPersonType(stAdmData.szPersonType)    //人员类型

    //数据校验和转换
    DictCodeConverter.convertTPCardText6to7(card.getTextBuilder)
    //人员编号长度,中文校验
    if(card.getStrPersonID.length > 25 || !card.getStrPersonID.matches("[a-zA-z0-9]*")){
      card.getTextBuilder.setStrComment( card.getText.getStrComment + s"(人员编号:${card.getStrPersonID})")
      card.setStrPersonID("")
    }

    card.build()
  }

  def convertHead7to6(gafis7Head: GAFIS7LOB_IMGHEADSTRUCT): GAFISIMAGEHEADSTRUCT = {
    val gafis6Head = new GAFISIMAGEHEADSTRUCT

    gafis6Head.nWidth = gafis7Head.nWidth.toShort
    gafis6Head.nHeight = gafis7Head.nHeight.toShort
    gafis6Head.nCompressMethod = gafis7Head.nCompressMethod
    gafis6Head.nImgSize = gafis7Head.nDataSize
    gafis6Head.nResolution = gafis7Head.nResolution
    gafis6Head.bIsCompressed = 0
    if(gafis7Head.nCompressMethod != 0) {
      gafis6Head.bIsCompressed = 1
      gafis6Head.nCaptureMethod = convertCprMethod7to6(gafis7Head.nCompressMethod.toInt).toByte
    }
    gafis6Head
  }

  def convertCprMethod7to6(g7Method: Int): Int={
    g7Method match {
      case GAFIS7_CPRMETHOD.CPRCODE_NOCOMPRESS =>
        glocdef.GAIMG_CPRMETHOD_DEFAULT
      case GAFIS7_CPRMETHOD.CPRCODE_XGW =>
        glocdef.GAIMG_CPRMETHOD_XGW
      case GAFIS7_CPRMETHOD.CPRCODE_XGW_EZW =>
        glocdef.GAIMG_CPRMETHOD_XGW_EZW
      case GAFIS7_CPRMETHOD.CPRCODE_USERDEFINED =>
        glocdef.GAIMG_CPRMETHOD_XGW
      case GAFIS7_CPRMETHOD.CPRCODE_JP2000 =>
        glocdef.GAIMG_CPRMETHOD_JPG
      case GAFIS7_CPRMETHOD.CPRCODE_JP2000LS=>
        glocdef.GAIMG_CPRMETHOD_JPG
      case GAFIS7_CPRMETHOD.CPRCODE_JPEGB=>
        glocdef.GAIMG_CPRMETHOD_JPG
      case GAFIS7_CPRMETHOD.CPRCODE_JPEGL=>
        glocdef.GAIMG_CPRMETHOD_JPG
      case other =>
        glocdef.GAIMG_CPRMETHOD_DEFAULT
    }
  }
}

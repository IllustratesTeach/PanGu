package nirvana.hall.api.webservice


import java.io.FileInputStream

import com.google.protobuf.ByteString
import nirvana.hall.api.services.{QueryService, TPCardService}
import nirvana.hall.api.webservice.util.FPTFileHandler
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.v62.BaseV62TestCase
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisNormalqueryQueryque
import org.junit.Test

import scala.collection.mutable

/**
  * Created by yuchen on 2016/12/7.
  */
class FPTFileHandlerTest extends BaseV62TestCase{
  @Test
  def test_getTenprintFinger: Unit ={  //A1200002018881996110060.fpt  //44200481222242352823638.fpt
    val fptFile = FPTFileHandler.FPTFileParse(new FileInputStream("E:\\A1200002018881996110060.fpt"))
    //val imageStruts = FPTFileHandler.getImage(fptFile)

    var personId = ""

    fptFile match {
      case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
      case Right(fpt4) =>
        val featureType = ParseFeatureTypeEnum(fpt4.tpCount.toInt,fpt4.lpCount.toInt)
        fpt4.logic02Recs.foreach { tp =>
          val personId = tp.personId

          tp.fingers.foreach { tData =>
            if (tData.imgData != null && tData.imgData.length > 0)
              if (tData.imgData != null && tData.imgData.length > 0) {
                val tBuffer = FPTFileHandler.fingerDataToGafisImage(tData)
                //解压
                val s = FPTFileHandler.callHallImageDecompressionImage(tBuffer)
                //提特征
                val mnt = FPTFileHandler.extractorFeature(s,fgpParesEnum(tData.fgp),featureType)
              }
          }
        }
    }


    //assert(imageStruts.seq.iterator.hasNext,"获得图像错误")
  }

  /**
    * 将解析出的指位翻译成系统中的枚举类型
    */
  def fgpParesEnum(fgp:String): FingerPosition ={
    fgp match {
      case "01" =>
        FingerPosition.FINGER_R_THUMB
      case "02" =>
        FingerPosition.FINGER_R_INDEX
      case "03" =>
        FingerPosition.FINGER_R_MIDDLE
      case "04" =>
        FingerPosition.FINGER_R_RING
      case "05" =>
        FingerPosition.FINGER_R_LITTLE
      case "06" =>
        FingerPosition.FINGER_L_THUMB
      case "07" =>
        FingerPosition.FINGER_L_INDEX
      case "08" =>
        FingerPosition.FINGER_L_MIDDLE
      case "09" =>
        FingerPosition.FINGER_L_RING
      case "10" =>
        FingerPosition.FINGER_L_LITTLE
      case other =>
        FingerPosition.FINGER_UNDET
    }
  }

  /**
    *
    * @param tpCount
    * @param lpCount
    * @return
    */
  def ParseFeatureTypeEnum(tpCount:Integer,lpCount:Integer): FeatureType ={
    var featureType:FeatureType = null
    if(tpCount>0){
      featureType = FeatureType.FingerTemplate
    }else if(lpCount>0){
      featureType = FeatureType.FingerLatent
    }
    featureType
  }



  @Test
  def test_getSearchTask(): Unit ={
    try{

      val taskFpt = FPTFile.parseFromInputStream(new FileInputStream("E:\\44202450222242352022397.fpt"))

      //解析比对任务FPT
      val taskControlID = taskFpt.right.get.sid


      taskFpt match {
        case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
        case Right(fpt4) =>
          val tpCardService = getService[TPCardService]
          val queryService = getService[QueryService]
          if(fpt4.logic02Recs.length>0){
            if(fpt4.logic02Recs(0).head.dataType.equals("02")){
              var tPCard:TPCard = null
              tPCard = TPFPT2ProtoBuffer(fpt4)
              tpCardService.addTPCard(tPCard)
              // TODO 发比对任务
              queryService.addMatchTask(null)
            }
          }else if(fpt4.logic03Recs.length>0){
            if(fpt4.logic03Recs(0).head.dataType.equals("03")){
              // TODO 现场处理

            }
          }else{
            throw new Exception("接收FPT逻辑描述记录类型不正确")
          }
      }
    }catch{
      case e:Exception=> println("测试出现异常:" + e.getMessage)
    }
  }


  /**
    * 根据接口获得的FPT文件,构建TPCard的ProtoBuffer的对象
    * 在构建过程中,需要解压图片获得原图，提取特征
    * @param fpt4
    * @return
    */
  def TPFPT2ProtoBuffer(fpt4:FPT4File):TPCard ={
    val tpCard = TPCard.newBuilder()
    val textBuilder = tpCard.getTextBuilder
    tpCard.setStrCardID(fpt4.sid)
    fpt4.logic02Recs.foreach { tp =>
      textBuilder.setStrName(tp.personName)
      textBuilder.setStrAliasName (tp.alias)
      textBuilder.setNSex(tp.gender.toInt)
      textBuilder.setStrBirthDate (tp.birthday)
      textBuilder.setStrIdentityNum (tp.idCardNo)
      textBuilder.setStrBirthAddrCode (tp.addressDetail)
      textBuilder.setStrBirthAddr (tp.address)
      textBuilder.setStrAddrCode (tp.doorDetail)
      textBuilder.setStrAddr (tp.door)
      textBuilder.setStrPersonType (tp.category)
      textBuilder.setStrCaseType1 (tp.caseClass1Code)
      textBuilder.setStrCaseType2 (tp.caseClass2Code)
      textBuilder.setStrCaseType3 (tp.caseClass3Code)
      textBuilder.setStrPrintUnitCode (tp.gatherUnitCode)
      textBuilder.setStrPrintUnitName (tp.gatherName)
      textBuilder.setStrPrinter (tp.gatherName)
      textBuilder.setStrPrintDate (tp.gatherDate)
      textBuilder.setStrComment (tp.remark)
      textBuilder.setStrNation (tp.nation)
      textBuilder.setStrRace (tp.nativeplace)
      textBuilder.setStrCertifType (tp.certificateType)
      textBuilder.setStrCertifID (tp.certificateNo)
      //textBuilder.setBHasCriminalRecord(if StringUtils.isEmpty(tp.isCriminal) false else true)
      textBuilder.setStrCriminalRecordDesc (tp.criminalInfo)
      textBuilder.setStrPremium (tp.assistUnitName)
      //textBuilder.setNXieChaFlag (tp.isAssist.toInt)
      textBuilder.setStrXieChaRequestUnitName (tp.assistUnitName)
      textBuilder.setStrXieChaRequestUnitCode (tp.assistUnitCode)
      //textBuilder.setNXieChaLevel (tp.assistLevel.toInt)
      textBuilder.setStrXieChaForWhat (tp.assistPurpose)
      textBuilder.setStrRelPersonNo (tp.relatedPersonId)
      textBuilder.setStrRelCaseNo (tp.relatedCaseId)
      textBuilder.setStrXieChaTimeLimit (tp.assistTimeLimit)
      textBuilder.setStrXieChaDate (tp.assistDate)
      textBuilder.setStrXieChaRequestComment (tp.assistAskingInfo)
      textBuilder.setStrXieChaContacter (tp.contact)
      textBuilder.setStrXieChaTelNo (tp.contactPhone)
      textBuilder.setStrShenPiBy (tp.approver)
      tp.fingers.foreach { tData =>
        if (tData.imgData != null && tData.imgData.length > 0) {
          val tBuffer = FPTFileHandler.fingerDataToGafisImage(tData)
          //图像解压
          val s = FPTFileHandler.callHallImageDecompressionImage(tBuffer)
          //提取特征
          val mnt = FPTFileHandler.extractorFeature(s,fgpParesEnum(tData.fgp),ParseFeatureTypeEnum(fpt4.tpCount.toInt,fpt4.lpCount.toInt))
          val blobBuilder = tpCard.addBlobBuilder()
          mnt.foreach { blob =>
            blobBuilder.setStMntBytes(ByteString.copyFrom(blob.toByteArray()))
          }
        }
      }
      textBuilder.build()
    }
    tpCard.build()
  }
}

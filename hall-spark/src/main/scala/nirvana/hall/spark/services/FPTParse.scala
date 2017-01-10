package nirvana.hall.spark.services

import java.io.{ByteArrayInputStream, File}

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code.FPTFingerData
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.internal.{GafisPartitionRecordsDakuSaver, GafisPartitionRecordsSaver}
import org.apache.commons.io.FileUtils

/**
  * Created by wangjue on 2017/1/10.
  */
class FPTParse {

  def parse() : Unit = {
    val data = FileUtils.readFileToByteArray(new File(""))
    val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)
    fpt match {
      case Left(fpt3) =>
        val tpCounts = fpt3.tpCount
        var tpCount = 0
        if (tpCounts!=null && !"".equals(tpCounts))
          tpCount = tpCounts.toInt
        val lpCounts = fpt3.lpCount
        var lpCount = 0
        if (lpCounts!=null && !"".equals(lpCounts))
          lpCount = lpCounts.toInt
        if (tpCount > 0) {
          assert(tpCount == fpt3.logic3Recs.length)
          fpt3.logic3Recs.foreach { tp =>
            val fingerCount = tp.sendFingerCount.toInt
            assert(fingerCount == tp.fingers.length)
            personId = tp.personId
            assert(personId != null, "person id is null")
            val person = FptPropertiesConverter.fpt3ToPersonConvert(tp)
            tp.fingers.foreach { tData =>
              if (tData.imgData != null && tData.imgData.length > 0) {
                val tBuffer = createImageEvent(filePath, personId, tData, list)
                if (tBuffer != null)
                  buffer += tBuffer
              }
            }
          }
        }
        if (lpCount > 0) { //process latent FPT
          fpt3.logic2Recs.foreach { lp =>
            val caseId = lp.caseId
            assert(caseId != null && !"".equals(caseId), "case id is null")
            val latentCase = FptPropertiesConverter.fpt3ToLatentCaseConvert(lp)

            lp.fingers.foreach{ lData=>
              var seqNo = lData.fingerNo.toInt.toString
              var cardId = lData.fingerId
              if (cardId == null || "".equals(cardId)) {
                if (seqNo != null && !"".equals(seqNo)) {
                  if (seqNo.toInt < 10)
                    seqNo = "0" + seqNo
                  cardId = caseId + seqNo
                } else
                  cardId = null
              }
              assert(cardId != null && !"".equals(cardId), "card id is null")
              val latentFinger = FptPropertiesConverter.fpt3ToLatentFingerConvert(lData,filePath,caseId,cardId.trim,seqNo)
              if (lData != null && lData.featureCount.toInt > 0) {
                val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(lData)
                val feature = createImageLatentEvent(disp)
                val GFSFEATURE = fpt4code.FPTFingerLDataToGafisImage(lData)
                GFSFEATURE.bnData = feature
                GFSFEATURE.stHead.nImgSize = feature.length
                val latentFingerFeature = FptPropertiesConverter.fptToLatentFingerFeatureConvert(cardId,GFSFEATURE.toByteArray(),lData.extractMethod)
                latentFinger.LatentFingerFeatures =  latentFingerFeature :: Nil
              }
              if (latentCase.latentFingers == null)
                latentCase.latentFingers = latentFinger :: Nil
              else
                latentCase.latentFingers = latentCase.latentFingers ::: latentFinger :: Nil
            }
            GafisPartitionRecordsDakuSaver.saveLatent(latentCase)
          }
        }
      case Right(fpt4) =>
        /*if (fpt4.fileLength.toInt != fpt4.getDataSize) {
          println("fileLength="+fpt4.fileLength.toInt+"|dataSize="+fpt4.getDataSize+"|filePath="+filePath)
        }
        assert(fpt4.fileLength.toInt == fpt4.getDataSize,"fpt4 fileLength != dataSize")*/
        val tpCounts = fpt4.tpCount
        var tpCount = 0
        if (tpCounts!=null && !"".equals(tpCounts))
          tpCount = tpCounts.toInt
        val lpCounts = fpt4.lpCount
        var lpCount = 0
        if (lpCounts!=null && !"".equals(lpCounts))
          lpCount = lpCounts.toInt
        if (tpCount > 0) {
          assert(tpCount == fpt4.logic02Recs.length)
          fpt4.logic02Recs.foreach { tp =>
            val fingerCount = tp.sendFingerCount.toInt
            assert(fingerCount == tp.fingers.length)
            personId = tp.personId
            assert(personId != null, "person id is null")
            //save person base information
            val hasPerson = GafisPartitionRecordsDakuSaver.queryPersonById(personId)
            if (hasPerson.isEmpty) {
              val person = FptPropertiesConverter.fpt4ToPersonConvert(tp, filePath)
              GafisPartitionRecordsDakuSaver.savePersonInfo(person)
            }
            var list : List[Array[Int]] = List()
            if (reExtract) //reset finger mnt
              GafisPartitionRecordsDakuSaver.deleteTemplateFingerMntOrBin(personId)
            else
              list = GafisPartitionRecordsDakuSaver.queryFingerFgpAndFgpCaseByPersonId(personId)
            tp.fingers.foreach { tData =>
              if (tData.imgData != null && tData.imgData.length > 0) {
                val tBuffer = createImageEvent(filePath, personId, tData, list)
                if (tBuffer != null)
                  buffer += tBuffer
              }
            }
          }
        }
        if (lpCount > 0) { //process latent FPT
          fpt4.logic03Recs.foreach { lp =>
            val caseId = lp.caseId
            assert(caseId != null && !"".equals(caseId), "case id is null")
            val latentCase = FptPropertiesConverter.fpt4ToLatentCaseConvert(lp)

            lp.fingers.foreach{ lData=>
              var seqNo = lData.fingerNo.toInt.toString
              var cardId = lData.fingerId
              if (cardId == null || "".equals(cardId)) {
                if (seqNo != null && !"".equals(seqNo)) {
                  if (seqNo.toInt < 10)
                    seqNo = "0" + seqNo
                  cardId = caseId + seqNo
                } else
                  cardId = null
              }
              assert(cardId != null && !"".equals(cardId), "card id is null")
              val latentFinger = FptPropertiesConverter.fpt4ToLatentFingerConvert(lData,filePath,caseId,cardId.trim,seqNo)
              if (lData != null && lData.featureCount.toInt > 0) {
                val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(lData)
                val feature = createImageLatentEvent(disp)
                val GFSFEATURE = fpt4code.FPTFingerLDataToGafisImage(lData)
                GFSFEATURE.bnData = feature
                GFSFEATURE.stHead.nImgSize = feature.length
                val latentFingerFeature = FptPropertiesConverter.fptToLatentFingerFeatureConvert(cardId,GFSFEATURE.toByteArray(),lData.extractMethod)
                latentFinger.LatentFingerFeatures =  latentFingerFeature :: Nil
              }
              if (latentCase.latentFingers == null)
                latentCase.latentFingers = latentFinger :: Nil
              else
                latentCase.latentFingers = latentCase.latentFingers ::: latentFinger :: Nil
            }
            GafisPartitionRecordsDakuSaver.saveLatent(latentCase)
          }
        }
    }
  }

  private lazy val decoder = new FirmDecoderImpl(".",null)
  private lazy val extractor = new FeatureExtractorImpl
  def buildViewTemplate(tData : FPTFingerData) : Unit = {
    SparkFunctions.loadImageJNI()
    val compressImage = fpt4code.FPTFingerDataToGafisImage(tData)
    val decompressImage = decoder.decode(compressImage)
    val (mnt, bin) = extractor.extractByGAFISIMG(decompressImage, getFingerPosition(tData.fgp.toInt), FeatureType.FingerTemplate)


  }


  def buildViewCase() : Unit = {

  }

  case class Template(personId : String, compressMethod : String, groupId : String, fgpCase : String, fgp : String, fingerData : Array[Byte]) extends Serializable {
    var data : Array[Any] = _
  }
  case class Case(caseId : String, seq : String, cardId : String, fingerId : String,fingerData : Array[Byte]) extends Serializable {
    var data : Array[Any] = _
  }

  protected def getFingerPosition(fgp : Int) : FingerPosition = {
    val normalPosition = (fgp-1) % 10
    normalPosition match {
      case 0 => FingerPosition.FINGER_R_THUMB
      case 1 => FingerPosition.FINGER_R_INDEX
      case 2 => FingerPosition.FINGER_R_MIDDLE
      case 3 => FingerPosition.FINGER_R_RING
      case 4 => FingerPosition.FINGER_R_LITTLE
      case 5 => FingerPosition.FINGER_L_THUMB
      case 6 => FingerPosition.FINGER_L_INDEX
      case 7 => FingerPosition.FINGER_L_MIDDLE
      case 8 => FingerPosition.FINGER_L_RING
      case 9 => FingerPosition.FINGER_L_LITTLE
      case _ => FingerPosition.FINGER_UNDET
    }
  }

}

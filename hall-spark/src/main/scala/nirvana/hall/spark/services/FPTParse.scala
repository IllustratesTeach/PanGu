package nirvana.hall.spark.services

import java.io.{ByteArrayInputStream, File}
import java.nio.ByteOrder

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code.FPTFingerData
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.extract.FeatureDisplayProto.{FeatureDisplayRequest, FeatureDisplayResponse}
import org.apache.commons.io.{FileUtils}

/**
  * Created by wangjue on 2017/1/10.
  */
class FPTParse {

  def parse(fptPath : String, url : String) : (List[Template],List[Case]) = {

    val data = FileUtils.readFileToByteArray(new File(fptPath))
    val fpt = FPTFile.parseFromInputStream(new ByteArrayInputStream(data), AncientConstants.GBK_ENCODING)
    var listTemplate : List[Template] = List()
    var listCase = List[Case]()
    var personId: String = null
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
            //val person = FptPropertiesConverter.fpt3ToPersonConvert(tp)
            tp.fingers.foreach { tData =>
              if (tData.imgData != null && tData.imgData.length > 0) {
                val compressMethod = tData.imgCompressMethod
                var fgpCase = "0"
                if (tData.fgp.toInt > 10) fgpCase = "1"
                val bmpData = buildViewTemplate(personId,tData,url)
                val template = new Template(personId,compressMethod,fgpCase,tData.fgp,bmpData)
                listTemplate :: template :: Nil
              }
            }
          }
        }
        if (lpCount > 0) { //process latent FPT
          fpt3.logic2Recs.foreach { lp =>
            val caseId = lp.caseId
            assert(caseId != null && !"".equals(caseId), "case id is null")

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
            }
          }
        }
      case Right(fpt4) =>
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
            if (tp != null) {
              /*val fingerCount = tp.sendFingerCount.toInt
            assert(fingerCount == tp.fingers.length)*/
              personId = tp.personId
              assert(personId != null, "person id is null")
              //val person = FptPropertiesConverter.fpt4ToPersonConvert(tp)
              var flag = true
              tp.fingers.foreach { tData =>
                if (tData.imgData != null && tData.imgData.length > 0 && flag) {
                  val compressMethod = tData.imgCompressMethod
                  var fgpCase = "0"
                  if (tData.fgp.toInt > 10) fgpCase = "1"
                  val bmpData = buildViewTemplate(personId, tData, url)
                  val template = new Template(personId, compressMethod, fgpCase, tData.fgp, bmpData)
                  listTemplate = template :: listTemplate
                }
                //flag = false
              }
            }
          }
        }
        if (lpCount > 0) { //process latent FPT
          fpt4.logic03Recs.foreach { lp =>
            val caseId = lp.caseId
            assert(caseId != null && !"".equals(caseId), "case id is null")

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
              if (lData != null && lData.featureCount.toInt > 0) {
              }
            }
          }
        }
    }
    (listTemplate,listCase)
  }

  private lazy val decoder = new FirmDecoderImpl("support",null)
  private lazy val extractor = new FeatureExtractorImpl
  def buildViewTemplate(personId : String,tData : FPTFingerData, url : String) : Array[Byte] = {
    SparkFunctions.loadImageJNI()
    val compressImage = fpt4code.FPTFingerDataToGafisImage(tData)
    val decompressImage = decoder.decode(compressImage)
    SparkFunctions.loadExtractorJNI()
    val (mnt, bin) = extractor.extractByGAFISIMG(decompressImage, getFingerPosition(tData.fgp.toInt), FeatureType.FingerTemplate)
    val fingerImage = decompressImage.toByteArray()
    /**获取真实特征**/
    /*val fingerMnt = mnt.bnData
    val mntDispBytes = (new MNTDISPSTRUCT).toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    NativeExtractor.GAFIS_MntStdToMntDisp(fingerMnt, mntDispBytes, 1)
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.fromByteArray(mntDispBytes, byteOrder=ByteOrder.LITTLE_ENDIAN)*/

    val display = displayImageByRPC(fingerImage,mnt.toByteArray(),url)
    display
  }


  def buildViewCase() : Unit = {

  }

  def displayImageByRPC(imgData : Array[Byte], mntData : Array[Byte], url : String): Array[Byte] = {
    val rpcHttpClient = SparkFunctions.httpClient
    val request = FeatureDisplayRequest.newBuilder()
    request.setDecompressImageData(ByteString.copyFrom(imgData))
    request.setMntDispData(ByteString.copyFrom(mntData))
    val baseResponse = rpcHttpClient.call(url,FeatureDisplayRequest.cmd,request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        if (baseResponse.hasExtension(FeatureDisplayResponse.cmd)) {
          val response = baseResponse.getExtension(FeatureDisplayResponse.cmd)
          response.getDisplayData.toByteArray
        } else {
          throw new IllegalAccessException("response haven't FeatureDisplayResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("draw feature fail!,server message:%s".format(baseResponse.getMsg))
    }
  }


  case class Template(personId : String, compressMethod : String, fgpCase : String, fgp : String, fingerData : Array[Byte]) extends Serializable {
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

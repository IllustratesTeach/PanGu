package nirvana.hall.spark.services

import java.awt.{BasicStroke, Color}
import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, FileOutputStream}
import java.nio.ByteOrder
import javax.imageio.ImageIO

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.fpt4code.FPTFingerData
import nirvana.hall.c.services.gfpt4lib.{FPTFile, fpt4code}
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import org.apache.commons.io.FileUtils

import scala.collection.immutable.Range

/**
  * Created by wangjue on 2017/1/10.
  */
class FPTParse {

  def parse(fptPath : String) : (List[Template],List[Case]) = {

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
                val bmpData = buildViewTemplate(personId,tData)
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
            val fingerCount = tp.sendFingerCount.toInt
            assert(fingerCount == tp.fingers.length)
            personId = tp.personId
            assert(personId != null, "person id is null")
            //val person = FptPropertiesConverter.fpt4ToPersonConvert(tp)
            tp.fingers.foreach { tData =>
              if (tData.imgData != null && tData.imgData.length > 0) {
                val compressMethod = tData.imgCompressMethod
                var fgpCase = "0"
                if (tData.fgp.toInt > 10) fgpCase = "1"
                val bmpData = buildViewTemplate(personId,tData)
                val template = new Template(personId,compressMethod,fgpCase,tData.fgp,bmpData)
                listTemplate = template :: listTemplate
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

  private lazy val decoder = new FirmDecoderImpl(".",null)
  private lazy val extractor = new FeatureExtractorImpl
  def buildViewTemplate(personId : String,tData : FPTFingerData) : Array[Byte] = {
    SparkFunctions.loadImageJNI()
    val compressImage = fpt4code.FPTFingerDataToGafisImage(tData)
    val decompressImage = decoder.decode(compressImage)
    SparkFunctions.loadExtractorJNI()
    val (mnt, bin) = extractor.extractByGAFISIMG(decompressImage, getFingerPosition(tData.fgp.toInt), FeatureType.FingerTemplate)
    val fingerImage = decompressImage.toByteArray()
    /**获取真实特征**/
    val fingerMnt = mnt.bnData
    val mntDispBytes = (new MNTDISPSTRUCT).toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    NativeExtractor.GAFIS_MntStdToMntDisp(fingerMnt, mntDispBytes, 1)
    val mntDisp = new MNTDISPSTRUCT
    mntDisp.fromByteArray(mntDispBytes, byteOrder=ByteOrder.LITTLE_ENDIAN)

    val image = ImageIO.read(new ByteArrayInputStream(fingerImage))

    val baseImg = new BufferedImage(mntDisp.nWidth,mntDisp.nHeight,BufferedImage.TYPE_INT_RGB)
    val graphics = baseImg.createGraphics()
    graphics.drawImage(image,0,0,mntDisp.nWidth,mntDisp.nHeight,null)

    graphics.setStroke(new BasicStroke(3.0f))
    graphics.setColor(new Color(255,0,0))

    val centerPoint = mntDisp.stFg.upcore //中心
    val cpX = centerPoint.x               //中心位置X
    val cpY = centerPoint.y               //中心位置Y
    val nRadius = centerPoint.nRadius     //中心半径
    graphics.drawOval(cpX,cpY,nRadius*2,nRadius*2)

    val mntCnt = mntDisp.stCm.nMntCnt
    val mntDetail = mntDisp.stCm.mnt
    val mntLength = 10                          //特征长度

    var fgpCase = "0"
    if (tData.fgp.toInt > 10) fgpCase = "1"

    (0 until mntCnt).foreach{ m=>
      val stMnt = mntDetail(m)
      val mntReliability = stMnt.nReliability   //特征可信度
      val mntX = stMnt.x                        //特征位置X
      val mntY = stMnt.y                        //特征位置Y
      val mntZ = stMnt.z                        //特征角度[-90, 270)
      val coord = mntCoord(mntX,mntY,mntZ,mntLength)
      println(mntZ+"_"+fgpCase+"_"+tData.fgp.toInt)
      println(coord(0).toInt,coord(1).toInt,coord(2).toInt,coord(3).toInt)
      mntReliability match {
        case 0 => graphics.setColor(new Color(255,255,0))
        case 1 => graphics.setColor(new Color(255,0,0))
      }
      graphics.drawLine(coord(0).toInt,coord(1).toInt,coord(2).toInt,coord(3).toInt)
      graphics.drawOval(mntX,mntY,5,5)
      //graphics.drawOval(coord(2).toInt,coord(3).toInt,5,5)
    }

    val out = new ByteArrayOutputStream()
    /*var fgpCase = "0"
    if (tData.fgp.toInt > 10) fgpCase = "1"
    if (!new File("D:\\ftp\\cc\\"+personId).exists()) new File("D:\\ftp\\cc\\"+personId).mkdirs()
    ImageIO.write(image,"bmp",new File("D:\\ftp\\cc\\"+personId+File.separator+"1_"+fgpCase+"_"+tData.fgp.toInt+".bmp"))*/
    ImageIO.write(baseImg,"bmp",out)
    out.toByteArray
  }

  def mntCoord(x1: Double, y1 : Double, z : Double, len : Double) : Array[Double] = {
    var angle : Double = z
    if (angle >= 0 && angle <= 90) angle = angle
    else if (angle > 90 && angle <= 180) angle = 180-angle
    else if (angle > 180 && angle <= 270) angle = 270-angle
    else if (angle >= -90 && angle < 0) angle = Math.abs(angle)
    else throw new IllegalArgumentException("unknown angle :"+angle)

    angle = Math.toRadians(angle)
    var x2 = len * Math.cos(angle)
    var y2 = len * Math.sin(angle)
    if (z >= 0 && z <= 90) {x2 = x1 + x2;y2 = y1 + y2;}
    else if (z > 90 && z <= 180) {x2 = x1+x2;y2 = y1 - y2;}
    else if (z > 180 && z <= 270) {x2 = x1-x2;y2 = y1-2;}
    else if (z >= -90 && z < 0) {x2 = x1 + x2;y2 = y1-y2;}
    Array(x1,y1,x2,y2)
  }


  def buildViewCase() : Unit = {

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

package nirvana.hall.spark.services

import java.awt.{BasicStroke, Color, Font, Graphics2D, RenderingHints}
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
            var flag = true
            tp.fingers.foreach { tData =>
              if (tData.imgData != null && tData.imgData.length > 0 && flag) {
                val compressMethod = tData.imgCompressMethod
                var fgpCase = "0"
                if (tData.fgp.toInt > 10) fgpCase = "1"
                val bmpData = buildViewTemplate(personId,tData)
                val template = new Template(personId,compressMethod,fgpCase,tData.fgp,bmpData)
                listTemplate = template :: listTemplate
              }
              //flag = false
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

    val showCenterPoint = true
    val showSubCenterPoint = true
    val showRightTriangle = true
    val showLeftTriangle = true
    val showMnt = true
    FileUtils.writeByteArrayToFile(new File("E:\\temp\\display.img"),fingerImage)
    FileUtils.writeByteArrayToFile(new File("E:\\temp\\display.mnt"),mntDisp.toByteArray())



    val image = ImageIO.read(new ByteArrayInputStream(fingerImage))
    val baseImg = new BufferedImage(mntDisp.nWidth,mntDisp.nHeight,BufferedImage.TYPE_INT_RGB)
    val graphics = baseImg.createGraphics()
    graphics.drawImage(image,0,0,mntDisp.nWidth,mntDisp.nHeight,null)
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON)
    graphics.setStroke(new BasicStroke(2.0f))

    drawCenterPoint(mntDisp)(graphics)
    drawSubCenterPoint(mntDisp)(graphics)
    drawLeftTriangle(mntDisp)(graphics)
    drawRightTriangle(mntDisp)(graphics)
    drawMnt(mntDisp)(graphics)

    val out = new ByteArrayOutputStream()
    ImageIO.write(baseImg,"bmp",out)
    out.toByteArray
  }

  //中心
  def  drawCenterPoint(mntDisp : MNTDISPSTRUCT)(graphics : Graphics2D) : Unit = {
    val centerPoint = mntDisp.stFg.upcore
    val cpX = centerPoint.x //中心位置X
    val cpY = centerPoint.y //中心位置Y
    val cpZ = centerPoint.z
    val nRadius = centerPoint.nRadius //中心半径
    val nzVarRange = centerPoint.nzVarRange //方向范围
    drawOval(cpX - nRadius, cpY - nRadius, nRadius * 2, nRadius * 2)(graphics)
    val coordCenter = mntCoord(cpX, cpY, cpZ, 45)
    drawLine(cpX, cpY, coordCenter(0).toInt, coordCenter(1).toInt)(graphics)(Color.GREEN)
    val coordLeft = mntCoord(cpX, cpY, cpZ-nzVarRange, 35)
    drawLine(cpX, cpY, coordLeft(0).toInt, coordLeft(1).toInt)(graphics)(new Color(105,105,105))
    val coordRight = mntCoord(cpX, cpY, cpZ+nzVarRange, 35)
    drawLine(cpX, cpY, coordRight(0).toInt, coordRight(1).toInt)(graphics)(new Color(105,105,105))
  }
  //副中心
  def drawSubCenterPoint(mntDisp : MNTDISPSTRUCT)(graphics : Graphics2D) : Unit = {
    val subCenterPoint = mntDisp.stFg.lowcore
    val scpX = subCenterPoint.x
    val scpY = subCenterPoint.y
    val scpRadius = subCenterPoint.nRadius
    drawOval(scpX - scpRadius, scpY - scpRadius, scpRadius * 2, scpRadius * 2)(graphics)
  }
  //左三角
  def drawLeftTriangle(mntDisp : MNTDISPSTRUCT)(graphics : Graphics2D) : Unit = {
    val leftTriangle = mntDisp.stFg.ldelta
    val llX = leftTriangle.x
    val llY = leftTriangle.y
    val llRadius = leftTriangle.nRadius
    drawOval(llX - llRadius, llY - llRadius, llRadius * 2, llRadius * 2)(graphics)(new Color(255, 110, 180))
    val labc = innerTriangle(llX, llY, llRadius, 0)
    drawLine(labc(0).toInt, labc(1).toInt, labc(2).toInt, labc(3).toInt)(graphics)(new Color(255, 20, 147))
    drawLine(labc(0).toInt, labc(1).toInt, labc(4).toInt, labc(5).toInt)(graphics)(new Color(255, 20, 147))
    drawLine(labc(2).toInt, labc(3).toInt, labc(4).toInt, labc(5).toInt)(graphics)(new Color(255, 20, 147))
  }
  //右三角
  def drawRightTriangle(mntDisp : MNTDISPSTRUCT)(graphics : Graphics2D) : Unit = {
    val rightTriangle = mntDisp.stFg.rdelta
    val rlX = rightTriangle.x
    val rlY = rightTriangle.y
    val rlRadius = rightTriangle.nRadius
    drawOval(rlX - rlRadius, rlY - rlRadius, rlRadius * 2, rlRadius * 2)(graphics)
    val rabc = innerTriangle(rlX, rlY, rlRadius, 1)
    drawLine(rabc(0).toInt, rabc(1).toInt, rabc(2).toInt, rabc(3).toInt)(graphics)(new Color(205, 0, 0))
    drawLine(rabc(0).toInt, rabc(1).toInt, rabc(4).toInt, rabc(5).toInt)(graphics)(new Color(205, 0, 0))
    drawLine(rabc(2).toInt, rabc(3).toInt, rabc(4).toInt, rabc(5).toInt)(graphics)(new Color(205, 0, 0))
  }
  //特征
  def drawMnt(mntDisp : MNTDISPSTRUCT)(graphics : Graphics2D) : Unit = {
    val mntCnt = mntDisp.stCm.nMntCnt
    val mntDetail = mntDisp.stCm.mnt
    val mntLength = 15 //特征长度
    (0 until mntCnt).foreach { m =>
      val stMnt = mntDetail(m)
      val mntReliability = stMnt.nReliability //特征可信度
    val mntX = stMnt.x //特征位置X
    val mntY = stMnt.y //特征位置Y
    val mntZ = stMnt.z //特征角度[-90, 270)
    val coord = mntCoord(mntX, mntY, mntZ, mntLength)
      val color: Color = mntReliability match {
        case 0 => Color.BLUE
        case 1 => Color.YELLOW
      }
      drawOval(mntX - 1, mntY - 1, 2, 2)(graphics)(color)
      graphics.drawLine(mntX, mntY, coord(0).toInt, coord(1).toInt)
      /*graphics.setColor(new Color(0, 0, 255))
      graphics.setFont(new Font("隶书", Font.BOLD, 12))
      graphics.drawString(m.toString,mntX+6,mntY+6)*/
    }
  }


  def drawOval(x1 : Int, y1 : Int, width : Int, height : Int)(graphics : Graphics2D)(implicit color : Color = Color.RED) : Unit = {
    graphics.setColor(color)
    graphics.drawOval(x1,y1,width,height)
  }
  def drawLine(x1 : Int, y1 : Int, x2 : Int, y2 : Int)(graphics : Graphics2D)(implicit color : Color = Color.RED) : Unit = {
    graphics.setColor(color)
    graphics.drawLine(x1,y1,x2,y2)
  }

  def mntCoord(x1: Double, y1 : Double, zz : Double, len : Double) : Array[Double] = {
    var z = zz - 90
    if (zz >= -90 && zz < 0) z = zz + 270
    var angle : Double = z
    angle = Math.toRadians(angle)
    var x2 = len * Math.cos(angle)
    var y2 = len * Math.sin(angle)
    if (z >= 0 && z <= 90) {x2 = x1+x2;y2 = y1-y2;}
    else if (z > 90 && z <= 180) {x2 = x1+x2;y2 = y1-y2;}
    else if (z > 180 && z <= 270) {x2 = x1+x2;y2 = y1-y2;}
    else if (z >= -90 && z < 0) {x2 = x1+x2;y2 = y1-y2;}
    Array(x2,y2)
  }

  def innerTriangle(x : Double, y : Double, r : Double, l : Int) : Array[Double] = {
    val ax = r
    val ay = 0
    val by = r*Math.sqrt(3)/2
    val bx = Math.sqrt(Math.pow(r,2)-Math.pow(by,2))
    val cx = bx
    val cy = by
    l match {
      case 0 => Array(x+ax,y+ay,x-bx,y+by,x-cx,y-cy)
      case 1 => Array(x-ax,y+ay,x+bx,y+by,x+cx,y-cy)
    }
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

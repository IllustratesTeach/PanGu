package nirvana.hall.spark.internal

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{FingerPosition}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions._
import nirvana.hall.spark.services.{ImageProvider, SparkFunctions}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.{IOUtils}

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

class GafisDatabaseImageWJWProvider extends ImageProvider{
  private lazy implicit val dataSource = GafisPartitionRecordsWJWUpdate.dataSource
  private lazy val extractor = new FeatureExtractorImpl
  private lazy val wsqDecode = new ImageEncoderImpl(new FirmDecoderImpl("support",null))
  val querySql = "select t.GATHER_DATA,t.FGP,t.IDCARDINFO_ID from GUIZHOU_IDCARDFINGERINFO t WHERE t.pk_id = ?"

  private val FILL_WIDTH = 512
  private val FILL_HEIGHT = 512

  def requestImage(parameter:NirvanaSparkConfig,message:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]().toSeq}
  override def requestImageByFID(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]()}

  def reportException(e: Throwable,personId:String,fgp:Integer,pkId:String,parameter:NirvanaSparkConfig) = {
    e.printStackTrace(System.err)
    val event = StreamEvent(pkId, personId,  FeatureType.FingerTemplate, getFingerPosition(fgp),"","","")
    reportError(parameter, RequestRemoteFileError(event, e.toString))
    null
  }

  def requestImageByBMP(parameter:NirvanaSparkConfig,pkId:String): Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)] = {
    //val pkId = "41C8114C5A4A15A5E0538201A8C004E4"

    try {
      JdbcDatabase.queryFirst(querySql){ps=>
        ps.setString(1, pkId)
      }{rs=>
        var fgp = -1
        var idCard_id = ""
        var bmp = Array.emptyByteArray
        try {
          val data = rs.getBinaryStream("GATHER_DATA")
          fgp = rs.getInt("FGP")
          if (fgp!=null && fgp!=0)
            fgp -= 10
          idCard_id = rs.getString("IDCARDINFO_ID")

          bmp = IOUtils.toByteArray(data)
          IOUtils.closeQuietly(data)
        }  catch{
          case NonFatal(e)=>
            reportException(e,idCard_id,fgp,pkId,parameter)
        }
        extractFeatureEvent(parameter,pkId,idCard_id,fgp, bmp)
      }
    } catch{
      case NonFatal(e)=>
        reportException(e,"",-1,pkId,parameter)
    }
  }

  private def extractFeatureEvent(parameter:NirvanaSparkConfig,pkId : String ,idCard_id: String, fgp : Integer ,imgData: Array[Byte]):(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(pkId,idCard_id, FeatureType.FingerTemplate, getFingerPosition(fgp.toInt),"","","")
    val featureTryVersion = if (parameter.isNewFeature) ExtractProto.NewFeatureTry.V2 else ExtractProto.NewFeatureTry.V1
    try {
      SparkFunctions.loadExtractorJNI()
      val templateFinger = new TemplateFingerConvert()
      templateFinger.gatherData = imagePadding(imgData)
      val mntData = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(templateFinger.gatherData), FingerPosition.FINGER_L_THUMB, event.featureType, featureTryVersion)
      val img = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(templateFinger.gatherData))
      SparkFunctions.loadImageJNI()
      val wsqImg = wsqDecode.encodeWSQ(img)
      //TODO 定义图像类型，次处最好在压缩WSQ时判断类型再赋值
      wsqImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte //图像类型 1：指纹

      templateFinger.gatherData = wsqImg.toByteArray()
      (event, templateFinger, new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1), new GAFISIMAGESTRUCT().fromByteArray(mntData.get._2))
    } catch {
      case NonFatal(e) =>
        reportException(e,idCard_id,fgp,pkId,parameter)
    }
  }



  def imagePadding(imgData : Array[Byte]) : Array[Byte] = {
    val image = ImageIO.read(new ByteArrayInputStream(imgData))
    val paddingImage = new BufferedImage(FILL_WIDTH, FILL_HEIGHT, BufferedImage.TYPE_BYTE_GRAY)
    val g = paddingImage.createGraphics()
    g.fillRect(0, 0, FILL_WIDTH, FILL_HEIGHT)
    val width = (FILL_WIDTH-image.getWidth)/2
    val height = (FILL_HEIGHT-image.getHeight)/2
    g.drawImage(image, width, height, image.getWidth, image.getHeight, null)
    g.dispose()
    val paddingImageByteArray = new ByteArrayOutputStream()
    ImageIO.write(paddingImage, "bmp", paddingImageByteArray)
    paddingImageByteArray.toByteArray
  }

}

package nirvana.hall.spark.internal

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import javax.imageio.ImageIO

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions._
import nirvana.hall.spark.services.{ImageProvider, SparkFunctions, SysProperties}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.{IOUtils}

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

class GafisDatabaseImageIdCardProvider extends ImageProvider{
  private lazy implicit val dataSource = GafisPartitionRecordsIdCardUpdate.dataSource
  private lazy val extractorServer = SysProperties.getPropertyOption("extractor.server").get
  private lazy val directExtract = SysProperties.getBoolean("extractor.direct",defaultValue = false)
  private lazy val extractor = new FeatureExtractorImpl
  private lazy val wsqDecode = new ImageEncoderImpl(new FirmDecoderImpl("support",null))
  private val querySql = "select t.gather_data,t.fgp,t.idcardinfo_id,c.idcardno,c.name from GUIZHOU_IDCARDFINGERINFO t LEFT JOIN GUIZHOU_IDCARDINFO c ON t.idcardinfo_id = c.pk_id WHERE t.pk_id = ?"

  private val FILL_WIDTH = 512
  private val FILL_HEIGHT = 512


  override def requestImage(parameter:NirvanaSparkConfig,message:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]().toSeq}
  override def requestData(parameter: NirvanaSparkConfig, message: String): Seq[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]()}

  def reportException(e: Throwable,personId:String,fgp:Integer,pkId:String,parameter:NirvanaSparkConfig,errorType:String) :Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)] = {
    e.printStackTrace(System.err)
    val event = StreamEvent(pkId, personId,  FeatureType.FingerTemplate, getFingerPosition(fgp),"","","")
    reportError(parameter, RequestRemoteFileError(event, e.toString,errorType))
    Some(event, new TemplateFingerConvert(), new GAFISIMAGESTRUCT(), new GAFISIMAGESTRUCT())
  }

  override def requestImageByBMP(parameter:NirvanaSparkConfig,pkId:String): Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)] = {
    //val pkId = "52019700000000000000000000001949"
    var fgp = -1
    var idCard_id = ""
    var idCardNO = ""
    var name = ""
    var bmp = Array.emptyByteArray
    try {
      JdbcDatabase.queryFirst(querySql){ps=>
        ps.setString(1, pkId)
      }{rs=>

        try {
          val data = rs.getBinaryStream("GATHER_DATA")
          fgp = rs.getInt("FGP")
          if (fgp!=null && fgp!=0)
            fgp -= 10
          idCard_id = rs.getString("IDCARDINFO_ID")
          idCardNO = rs.getString("IDCARDNO")
          name = rs.getString("NAME")
          bmp = IOUtils.toByteArray(data)
          IOUtils.closeQuietly(data)

        }  catch{
          case NonFatal(e)=>
            reportException(e,idCard_id,fgp,pkId,parameter,"R").get
        }

      }
      Some(extractFeatureEvent(parameter,pkId,idCard_id,fgp, bmp, idCardNO, name))

    } catch{
      case NonFatal(e)=>
        reportException(e,"",-1,pkId,parameter,"R")
    }

  }
  private def extractFeatureEvent(parameter:NirvanaSparkConfig,pkId : String ,idCardID: String, fgp : Integer ,imgData: Array[Byte], idCardNO : String, name : String):(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(pkId,idCardID, FeatureType.FingerTemplate, getFingerPosition(fgp.toInt),"","","")
    val featureTryVersion = if (parameter.isNewFeature) ExtractProto.NewFeatureTry.V2 else ExtractProto.NewFeatureTry.V1
    try {
      if (directExtract) {
        SparkFunctions.loadExtractorJNI()
        val templateFinger = new TemplateFingerConvert()
        templateFinger.gatherData = imagePadding(parameter, imgData, idCardID, idCardNO, name, fgp, pkId)
        templateFinger.idCardID = idCardID
        templateFinger.idCardNO = idCardNO
        templateFinger.name = name
        val mntData = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(templateFinger.gatherData), FingerPosition.FINGER_L_THUMB, event.featureType, featureTryVersion)

        val img = extractor.readByteArrayAsGAFISIMAGE(new ByteArrayInputStream(templateFinger.gatherData))
        SparkFunctions.loadImageJNI()
        val wsqImg = wsqDecode.encodeWSQ(img)
        //TODO 定义图像类型，次处最好在压缩WSQ时判断类型再赋值
        wsqImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte //图像类型 1：指纹

        templateFinger.gatherData = wsqImg.toByteArray()
        (event, templateFinger, new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1), new GAFISIMAGESTRUCT())
      } else {
        val templateFinger = new TemplateFingerConvert()
        templateFinger.gatherData = imagePadding(parameter, imgData, idCardID, idCardNO, name, fgp, pkId)
        templateFinger.idCardID = idCardID
        templateFinger.idCardNO = idCardNO
        templateFinger.name = name
        val rpcHttpClient = SparkFunctions.httpClient
        val request = ExtractRequest.newBuilder()
        request.setImgData(ByteString.copyFrom(templateFinger.gatherData))
        request.setFeatureTry(featureTryVersion)
        request.setMntType(event.featureType)
        request.setPosition(event.position)
        val baseResponse = rpcHttpClient.call(extractorServer, ExtractRequest.cmd, request.build())
        baseResponse.getStatus match {
          case CommandStatus.OK =>
            if (baseResponse.hasExtension(ExtractResponse.cmd)) {
              val response = baseResponse.getExtension(ExtractResponse.cmd)
              val mntData = response.getMntData
              val binData = response.getBinData
              val gafisMnt = new GAFISIMAGESTRUCT
              val is = mntData.newInput()
              gafisMnt.fromStreamReader(is)
              val gafisBin = new GAFISIMAGESTRUCT
              /*val bin = binData.newInput()
              gafisBin.fromStreamReader(bin)*/

              (event,templateFinger, gafisMnt, gafisBin)
            } else {
              throw new IllegalAccessException("response haven't ExtractResponse")
            }
          case CommandStatus.FAIL =>
            throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
        }
      }
    } catch {
      case NonFatal(e) =>
        var result = e.getMessage
        if (result ==null) result = ""
        else {
          if (result.length > 60)
            result = result.substring(0,60)
        }
        val updateResult = GafisPartitionRecordsIdCardUpdate.updateExtractLogInfo(pkId, "2", result)
        if (updateResult == 0)
          GafisPartitionRecordsIdCardUpdate.addExtractLogInfo(pkId, idCardNO, name, "2", result)

        reportException(e,idCardID,fgp,pkId,parameter,"E").get
    }
  }


  /**
    * 填充图像，从FILL_WIDTH, FILL_HEIGHT写入图片
    * @param parameter
    * @param imgData
    * @param idCardID
    * @param idCardNO
    * @param name
    * @param fgp
    * @param pkId
    * @return
    */
  def imagePadding(parameter:NirvanaSparkConfig, imgData : Array[Byte], idCardID : String, idCardNO : String, name : String, fgp : Integer, pkId : String) : Array[Byte] = {
    try {
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
      val updateResult = GafisPartitionRecordsIdCardUpdate.updateRestoreLogInfo(pkId, "1", "")
      if (updateResult == 0)
        GafisPartitionRecordsIdCardUpdate.addRestoreLogInfo(pkId, idCardNO, name, "1", "")

      paddingImageByteArray.toByteArray

    } catch {
      case e: Throwable =>
        //e.printStackTrace()
        var result = e.getMessage
        if (result ==null) result = ""
        else {
          if (result.length > 80)
            result = result.substring(0,80)
        }
        val updateResult = GafisPartitionRecordsIdCardUpdate.updateRestoreLogInfo(pkId, "2", result)
        if (updateResult == 0)
          GafisPartitionRecordsIdCardUpdate.addRestoreLogInfo(pkId, idCardNO, name, "2", result)

        reportException(e,idCardID,fgp,pkId,parameter,"P")
        null
    }
  }

}

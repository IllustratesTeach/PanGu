package nirvana.hall.spark.internal

import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, File, InputStream}
import javax.imageio.ImageIO

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions._
import nirvana.hall.spark.services.{ImageProvider, SparkFunctions, SysProperties}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.{FileUtils, IOUtils}

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

class GafisDatabaseImageWJWProvider extends ImageProvider{
  private lazy implicit val dataSource = GafisPartitionRecordsIdCardUpdate.dataSource
  private lazy val extractorServer = SysProperties.getPropertyOption("extractor.server").get
  private lazy val directExtract = SysProperties.getBoolean("extractor.direct",defaultValue = false)
  private lazy val extractor = new FeatureExtractorImpl
  val querySql = "select t.GATHER_DATA,t.FGP,t.IDCARDINFO_ID from GUIZHOU_IDCARDFINGERINFO t WHERE t.pk_id = ?"

  def requestImage(parameter:NirvanaSparkConfig,message:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] = {ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]().toSeq}

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
        try {
          val data = rs.getBinaryStream("GATHER_DATA")
          fgp = rs.getInt("FGP")
          if (fgp!=null && fgp!=0)
            fgp -= 10
          idCard_id = rs.getString("IDCARDINFO_ID")

          val bmp = IOUtils.toByteArray(data)
          IOUtils.closeQuietly(data)
          extractFeatureEvent(parameter,pkId,idCard_id,fgp, bmp)

        }  catch{
          case NonFatal(e)=>
            reportException(e,idCard_id,fgp,pkId,parameter)
        }

      }


    } catch{
      case NonFatal(e)=>
        reportException(e,"",-1,pkId,parameter)
    }

  }
  private def extractFeatureEvent(parameter:NirvanaSparkConfig,pkId : String ,idCard_id: String, fgp : Integer ,imgData: Array[Byte]):(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(pkId,idCard_id, FeatureType.FingerTemplate, getFingerPosition(fgp.toInt),"","","")
    val featureTryVersion = if (parameter.isNewFeature) ExtractProto.NewFeatureTry.V2 else ExtractProto.NewFeatureTry.V1
    FileUtils.writeStringToFile(new File("/tmp/curPkid.txt"), pkId+"\r\n",true)
    try {
      if (directExtract) {
        SparkFunctions.loadExtractorJNI()
        val templateFinger = new TemplateFingerConvert()
        templateFinger.gatherData = imagePadding(imgData)
        val mntData = extractor.extractByGAFISIMGBinary(new ByteArrayInputStream(templateFinger.gatherData), FingerPosition.FINGER_L_THUMB, event.featureType, featureTryVersion)
        (event, templateFinger, new GAFISIMAGESTRUCT().fromByteArray(mntData.get._1), new GAFISIMAGESTRUCT().fromByteArray(mntData.get._2))
      } else {
        val templateFinger = new TemplateFingerConvert()
        templateFinger.gatherData = imagePadding(imgData)
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
        reportException(e,idCard_id,fgp,pkId,parameter)
    }
  }



  def imagePadding(imgData : Array[Byte]) : Array[Byte] = {
    val image = ImageIO.read(new ByteArrayInputStream(imgData))
    val paddingImage = new BufferedImage(640,640,BufferedImage.TYPE_BYTE_GRAY)
    val g = paddingImage.createGraphics()
    g.fillRect(0,0,640,640)
    g.drawImage(image,0,0,image.getWidth,image.getHeight,null)
    g.dispose()
    val paddingImageByteArray = new ByteArrayOutputStream()
    ImageIO.write(paddingImage,"jpg",paddingImageByteArray)
    paddingImageByteArray.toByteArray
  }

}

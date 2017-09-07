package nirvana.hall.spark.services

import java.io.{File, ByteArrayInputStream}

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.{ExtractResponse, ExtractRequest}
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import org.apache.commons.io.FileUtils
import scala.util.control.NonFatal

/**
 * extract feature service
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object ExtractFeatureService {
  private lazy val extractor = new FeatureExtractorImpl
  private lazy val directExtract = SysProperties.getBoolean("extractor.direct",defaultValue = false)
  private lazy val converterExtract = SysProperties.getBoolean("extractor.converter",defaultValue = false)
  private lazy val extractorServer = SysProperties.getPropertyOption("extractor.server").get
  private lazy val extractBinSupport = SysProperties.getBoolean("extractor.bin.support",defaultValue = false)
  case class ExtractError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "E|"+message
  }
  def requestExtract(parameter:NirvanaSparkConfig,event:StreamEvent, originalImg : GAFISIMAGESTRUCT, fingerImg : TemplateFingerConvert):Option[(StreamEvent,TemplateFingerConvert,GAFISIMAGESTRUCT,GAFISIMAGESTRUCT)]= {
    if (event.personId != null && event.personId.length > 0) {
      try {
        val featureTryVersion = if (parameter.isNewFeature) ExtractProto.NewFeatureTry.V2 else ExtractProto.NewFeatureTry.V1
        //解压后的图头长度定义为0，会导致转原图为空
        if (originalImg.stHead.nSize == 0) originalImg.stHead.nSize = 64
        if(directExtract) {
          SparkFunctions.loadExtractorJNI()
          if (converterExtract) {
            val is = new ByteArrayInputStream(originalImg.toByteArray())
            val newFeature = extractor.ConvertMntOldToNew(is)
            val mnt = new GAFISIMAGESTRUCT().fromByteArray(newFeature.get)
            Some(event, fingerImg,mnt,null)
          } else {
            val (mnt, bin) = extractor.extractByGAFISIMG(originalImg, event.position, event.featureType, featureTryVersion)
            Some(event, fingerImg,mnt, bin)
          }
        }else {
          val rpcHttpClient = SparkFunctions.httpClient
          val request = ExtractRequest.newBuilder()
          request.setImgData(ByteString.copyFrom(originalImg.toByteArray()))
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
                val bin = binData.newInput()
                gafisBin.fromStreamReader(bin)
                Some((event, fingerImg, gafisMnt, gafisBin))
              } else {
                throw new IllegalAccessException("response haven't ExtractResponse")
              }
            case CommandStatus.FAIL =>
              throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
          }
        }
      } catch {
        case NonFatal(e) =>
          e.printStackTrace(System.err)
          SparkFunctions.reportError(parameter, ExtractError(event, e.toString))
          None
      }
    } else if (event.caseId != null && event.caseId.length > 0){ //Latent type
      Some((event, null,null,null))
    } else {
      SparkFunctions.reportError(parameter, ExtractError(event, "personId and caseId are null!"))
      None
    }
  }
}

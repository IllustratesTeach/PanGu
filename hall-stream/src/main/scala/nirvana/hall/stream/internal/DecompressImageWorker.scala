package nirvana.hall.stream.internal

import com.google.protobuf.ByteString
import com.lmax.disruptor.WorkHandler
import monad.support.services.LoggerSupport
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.stream.internal.StreamServiceObject.StreamEvent
import nirvana.hall.stream.services.{DecompressService, ExtractService, FeatureSaverService}
import nirvana.hall.c.services.AncientData._

/**
 * decompress image handler
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
class DecompressImageWorker(decompressService: DecompressService)
  extends WorkHandler[StreamEvent]
  with LoggerSupport {
  override def onEvent(t: StreamEvent): Unit = {
    if(t.img == null){
      throw new IllegalArgumentException("image parameter is null!")
    }
    if(t.img.stHead.bIsCompressed == 1){//need decompress
      val requestBuilder = FirmImageDecompressRequest.newBuilder()
      val output = ByteString.newOutput(t.img.getDataSize)
      t.img.writeToStreamWriter(output)
      requestBuilder.setCprData(output.toByteString)
      try {
        decompressService.decompress(requestBuilder.build()) match {
          case Some(originImageData) =>
            t.originalImgData = originImageData
          case other =>
            throw new IllegalAccessException("fail to decompress image for event " + t)
        }
      }catch{
        case e:Throwable=>
          val msg = "fail to decompress for data [%s]".format(t.id)
          info(msg)
          throw new RuntimeException(msg,e)
      }
    }else{
      t.originalImgData = t.img
    }
  }
}
/**
 * extract feature worker
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
class ExtractFeatureWorker(extractService: ExtractService) extends WorkHandler[StreamEvent]{
  override def onEvent(t: StreamEvent): Unit = {
    if(t.originalImgData != null){
      val featureOpt = extractService.extract(t.originalImgData,t.position,t.feature)
      featureOpt match{
        case Some(feature) =>
          t.featureData = feature
        case other =>
          throw new IllegalAccessException("fail to extract feature")
      }
    }
  }
}
/**
 * save data worker
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
class FeatureSaverWorker(saver:FeatureSaverService) extends WorkHandler[StreamEvent]{
  override def onEvent(t: StreamEvent): Unit = {
    if(t.featureData != null)
      saver.save(t.id,t.featureData)
  }
}

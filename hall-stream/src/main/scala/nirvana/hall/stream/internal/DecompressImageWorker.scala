package nirvana.hall.stream.internal

import com.lmax.disruptor.WorkHandler
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.stream.internal.StreamServiceObject.StreamEvent
import nirvana.hall.stream.services.{DecompressService, ExtractService, FeatureSaverService}

/**
 * decompress image handler
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
class DecompressImageWorker(decompressService: DecompressService) extends WorkHandler[StreamEvent]{
  override def onEvent(t: StreamEvent): Unit = {
    if(t.img == null){
      throw new IllegalArgumentException("image parameter is null!")
    }
    if(t.imgIsCompressed){//need decompress
      val requestBuilder = FirmImageDecompressRequest.newBuilder()
      requestBuilder.setCprData(t.img)
      requestBuilder.setCode(t.compressFirmCode)
      decompressService.decompress(requestBuilder.build()) match{
        case Some(originImageData) =>
          t.originalImgData = originImageData
        case other=>
          throw new IllegalAccessException("fail to decompress image for event "+t)
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

package nirvana.hall.api

import nirvana.hall.api.internal.FeatureExtractorImpl
import nirvana.hall.api.internal.fpt.fpttransform.FPTTransformer
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import nirvana.hall.support.internal.RpcHttpClientImpl
import nirvana.hall.support.services.RpcHttpClient
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by yuchen on 2018/5/12.
  * 加载-FPT4.0 转换为FPT5.0功能时使用
  */
object LocalApiFPTTransformModule {
  def bind(binder: ServiceBinder): Unit ={
    binder.bind(classOf[RpcHttpClient],classOf[RpcHttpClientImpl]).withId("RpcHttpClient")
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[FPTTransformer])
  }
}

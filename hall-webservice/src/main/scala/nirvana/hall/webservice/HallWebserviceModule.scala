package nirvana.hall.webservice


import nirvana.hall.api.internal.FeatureExtractorImpl
import nirvana.hall.api.internal.fpt.{FPT5ServiceImpl, FPTServiceImpl}
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services.fpt.{FPT5Service, FPTService}
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceModule {

  def bind(binder: ServiceBinder) {
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[FPT5Service], classOf[FPT5ServiceImpl])
  }
}

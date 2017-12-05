package nirvana.hall.api

import nirvana.hall.api.internal._
import nirvana.hall.api.internal.fpt.{FPT5ServiceImpl, FPTServiceImpl}
import nirvana.hall.api.internal.remote._
import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.{FPT5Service, FPTService}
import nirvana.hall.api.services.remote._
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.services.Core

/**
 * local api service module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-01
 */
object LocalApiServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[ProtobufRequestGlobal], classOf[ProtobufRequestGlobalImpl]).withMarker(classOf[Core])
    binder.bind(classOf[SystemService], classOf[SystemServiceImpl])
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    binder.bind(classOf[RequiresUserAdvisor], classOf[RequiresUserAdvisorImpl])

    //图像和特征处理相关Service
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[FPT5Service], classOf[FPT5ServiceImpl])

    //远程服务类
    binder.bind(classOf[TPCardRemoteService], classOf[TPCardRemoteServiceImpl])
    binder.bind(classOf[QueryRemoteService], classOf[QueryRemoteServiceImpl])
    binder.bind(classOf[LPCardRemoteService], classOf[LPCardRemoteServiceImpl])
    binder.bind(classOf[LPPalmRemoteService], classOf[LPPalmRemoteServiceImpl])
    binder.bind(classOf[CaseInfoRemoteService], classOf[CaseInfoRemoteServiceImpl])
  }
}

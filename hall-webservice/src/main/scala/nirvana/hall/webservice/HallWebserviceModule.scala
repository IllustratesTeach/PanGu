package nirvana.hall.webservice

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.api.internal.FeatureExtractorImpl
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.{TaskHandlerServiceImpl, TenPrinterExportServiceImpl}
import nirvana.hall.webservice.internal.bjwcsy.WsFingerServiceImpl
import nirvana.hall.webservice.internal.haixin.{StrategyServiceImpl, SyncCronSendQueryServiceImpl, WsHaiXinFingerServiceImpl}
import nirvana.hall.webservice.internal.xingzhuan._
import nirvana.hall.webservice.internal.xingzhuan.{FetchLPCardExportServiceImpl, LatentCronServiceImpl}
import nirvana.hall.webservice.services.{TaskHandlerService, TenPrinterExportService}
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import nirvana.hall.webservice.services.haixin.{StrategyService, SyncCronSendQueryService, WsHaiXinFingerService}
import nirvana.hall.webservice.services.xingzhuan._
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.ioc.annotations.Symbol

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceModule {
  def buildHallWebserviceConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-webservice.xml")
    XmlLoader.parseXML[HallWebserviceConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/webservice/webservice.xsd")))
  }

  def bind(binder: ServiceBinder) {
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[StrategyService], classOf[StrategyServiceImpl])//对接海鑫综采平台时使用

    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl])//.withSimpleId()
    binder.bind(classOf[WsHaiXinFingerService],classOf[WsHaiXinFingerServiceImpl]).withSimpleId()
    binder.bind(classOf[WsSendQueryService],classOf[WsSendQueryServiceImpl])//.withSimpleId()
    binder.bind(classOf[TenPrinterExportService],classOf[TenPrinterExportServiceImpl])
    binder.bind(classOf[SendQueryService],classOf[SendQueryServiceImpl])
    binder.bind(classOf[TaskHandlerService],classOf[TaskHandlerServiceImpl])
    binder.bind(classOf[FetchFPTService],classOf[FetchFPTServiceImpl])
    binder.bind(classOf[HallDatasourceService],classOf[HallDatasourceServiceImpl])

    binder.bind(classOf[FetchLPCardExportService],classOf[FetchLPCardExportServiceImpl])

    //    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
//    binder.bind(classOf[SendMatchResultService], classOf[SendMatchResultService]).eagerLoad()
    binder.bind(classOf[xingzhuanTaskCronService],classOf[xingzhuanTaskCronServiceImpl])//.eagerLoad()
    binder.bind(classOf[SendCheckinService], classOf[SendCheckinServiceImpl])//.eagerLoad()
    binder.bind(classOf[UploadCheckinService], classOf[UploadCheckinServiceImpl])//.eagerLoad()
    //============================================================================//
    binder.bind(classOf[TenPrinterCronService],classOf[TenPrinterCronServiceImpl])//.eagerLoad()
    binder.bind(classOf[LatentCronService],classOf[LatentCronServiceImpl])//.eagerLoad()
    binder.bind(classOf[AssistcheckService],classOf[AssistcheckServiceImpl])//.eagerLoad()
    binder.bind(classOf[LocalCheckinService],classOf[LocalCheckinServiceImpl])//.eagerLoad()
    binder.bind(classOf[SyncSyCronService],classOf[SyncSyCronServiceImpl])//.eagerLoad()
    //========海鑫综采平台对接===================//
    binder.bind(classOf[SyncCronSendQueryService],classOf[SyncCronSendQueryServiceImpl]).eagerLoad()
  }
}

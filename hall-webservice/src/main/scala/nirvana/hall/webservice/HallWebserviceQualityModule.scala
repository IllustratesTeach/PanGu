package nirvana.hall.webservice

import nirvana.hall.api.internal.FeatureExtractorImpl
import nirvana.hall.api.internal.fpt.FPT5ServiceImpl
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import nirvana.hall.webservice.internal.quality.{StrategyServiceImpl, WsQualityScoreServiceImpl}
import nirvana.hall.webservice.internal.survey.gafis62.SurveyTableMaintenanceService
import nirvana.hall.webservice.services.quality.{StrategyService, WsQualityScoreService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceQualityModule {

  def bind(binder: ServiceBinder) {
    binder.bind(classOf[WsQualityScoreService], classOf[WsQualityScoreServiceImpl])
    binder.bind(classOf[StrategyService], classOf[StrategyServiceImpl])
    binder.bind(classOf[SurveyTableMaintenanceService],classOf[SurveyTableMaintenanceService])
  }
}

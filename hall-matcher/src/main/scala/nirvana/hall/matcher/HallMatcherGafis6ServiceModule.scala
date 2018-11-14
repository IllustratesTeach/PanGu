package nirvana.hall.matcher

import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.internal.adapter.common.SyncDataServiceImpl
import nirvana.hall.matcher.internal.adapter.gafis6.sync._
import nirvana.hall.matcher.internal.adapter.gafis6.{GetMatchTaskServiceImpl, MatchTaskCronServiceImpl, PutMatchProgressServiceImpl, PutMatchResultServiceImpl}
import nirvana.hall.matcher.service._
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherGafis6ServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[PersonFetcher], classOf[PersonFetcherImpl])
    binder.bind(classOf[CaseFetcher], classOf[CaseFetcherImpl])
    binder.bind(classOf[TemplateFingerFetcher], classOf[TemplateFingerFetcherImpl])
    binder.bind(classOf[TemplatePalmFetcher], classOf[TemplatePalmFetcherImpl])
    binder.bind(classOf[LatentFingerFetcher], classOf[LatentFingerFetcherImpl])
    binder.bind(classOf[LatentPalmFetcher], classOf[LatentPalmFetcherImpl])
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
    //特征转换service
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])

    binder.bind(classOf[MatchTaskCronService], classOf[MatchTaskCronServiceImpl]).eagerLoad()
  }
}

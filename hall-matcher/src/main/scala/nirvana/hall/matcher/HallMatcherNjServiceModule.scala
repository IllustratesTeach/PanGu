package nirvana.hall.matcher

import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.internal.adapter.common._
import nirvana.hall.matcher.internal.adapter.common.sync.{TemplateFingerFetcherImpl, TemplatePalmFetcherImpl}
import nirvana.hall.matcher.internal.adapter.nj.GetMatchTaskServiceNjImpl
import nirvana.hall.matcher.internal.adapter.nj.sync.{CaseFetcherImpl, LatentFingerFetcherImpl, LatentPalmFetcherImpl, PersonFetcherImpl}
import nirvana.hall.matcher.service._
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/5/6.
 */
object HallMatcherNjServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[PersonFetcher], classOf[PersonFetcherImpl])
    binder.bind(classOf[CaseFetcher], classOf[CaseFetcherImpl])
    binder.bind(classOf[TemplateFingerFetcher], classOf[TemplateFingerFetcherImpl])
    binder.bind(classOf[TemplatePalmFetcher], classOf[TemplatePalmFetcherImpl])
    binder.bind(classOf[LatentFingerFetcher], classOf[LatentFingerFetcherImpl])
    binder.bind(classOf[LatentPalmFetcher], classOf[LatentPalmFetcherImpl])
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceNjImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
    binder.bind(classOf[AutoCheckService], classOf[AutoCheckServiceImpl])

    binder.bind(classOf[MatchTaskCronService], classOf[MatchTaskCronServiceImpl]).eagerLoad()
    //特征转换service
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
  }
}

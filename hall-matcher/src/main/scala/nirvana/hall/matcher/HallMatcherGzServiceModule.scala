package nirvana.hall.matcher

import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.internal.adapter.common._
import nirvana.hall.matcher.internal.adapter.common.sync.{LatentFingerFetcherImpl, LatentPalmFetcherImpl, TemplateFingerFetcherImpl, TemplatePalmFetcherImpl}
import nirvana.hall.matcher.internal.adapter.gz.sync._
import nirvana.hall.matcher.internal.adapter.gz.{AutoExtractFeatureServiceImpl, GetMatchTaskServiceGzImpl}
import nirvana.hall.matcher.service._
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/3/25.
 */
object HallMatcherGzServiceModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[PersonFetcher], classOf[PersonFetcherImpl])
    binder.bind(classOf[CaseFetcher], classOf[CaseFetcherImpl])
    binder.bind(classOf[TemplateFingerFetcher], classOf[TemplateFingerFetcherImpl])
    binder.bind(classOf[TemplatePalmFetcher], classOf[TemplatePalmFetcherImpl])
    binder.bind(classOf[LatentFingerFetcher], classOf[LatentFingerFetcherImpl])
    binder.bind(classOf[LatentPalmFetcher], classOf[LatentPalmFetcherImpl])
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceGzImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
    binder.bind(classOf[AutoCheckService], classOf[AutoCheckServiceImpl])

    binder.bind(classOf[MatchTaskCronService], classOf[MatchTaskCronServiceImpl]).eagerLoad()
    //特征转换service
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    //自动重提特征
    binder.bind(classOf[AutoExtractFeatureService], classOf[AutoExtractFeatureServiceImpl]).eagerLoad()
  }

}

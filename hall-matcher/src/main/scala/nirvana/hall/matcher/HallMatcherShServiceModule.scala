package nirvana.hall.matcher

import nirvana.hall.extractor.internal.FeatureExtractorImpl
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.internal.adapter.sh.{PutMatchProgressServiceImpl, PutMatchResultServiceImpl, GetMatchTaskServiceImpl, SyncDataServiceImpl}
import nirvana.hall.matcher.service.{PutMatchProgressService, PutMatchResultService, GetMatchTaskService, SyncDataService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
 * Created by songpeng on 16/5/6.
 */
object HallMatcherShServiceModule {
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncDataService], classOf[SyncDataServiceImpl])
    binder.bind(classOf[GetMatchTaskService], classOf[GetMatchTaskServiceImpl])
    binder.bind(classOf[PutMatchResultService], classOf[PutMatchResultServiceImpl])
    binder.bind(classOf[PutMatchProgressService], classOf[PutMatchProgressServiceImpl])
    //特征转换service
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
  }
}

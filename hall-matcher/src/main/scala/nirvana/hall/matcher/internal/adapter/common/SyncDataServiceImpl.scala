package nirvana.hall.matcher.internal.adapter.common

import monad.support.services.LoggerSupport
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service._
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/29.
 */
class SyncDataServiceImpl(hallMatcherConfig: HallMatcherConfig,
                          personFetcher: PersonFetcher,
                          templateFingerFetcher: TemplateFingerFetcher,
                          templatePalmFetcher: TemplatePalmFetcher,
                          caseFetcher: CaseFetcher,
                          latentFingerFetcher: LatentFingerFetcher,
                          latentPalmFetcher: LatentPalmFetcher,
                          featureExtractor: FeatureExtractor) extends SyncDataService with LoggerSupport{
  /**
   * 同步数据
   * @param syncDataRequest
   * @return
   */
  override def syncData(syncDataRequest: SyncDataRequest): SyncDataResponse = {
    val responseBuilder = SyncDataResponse.newBuilder
    responseBuilder.setSyncDataType(syncDataRequest.getSyncDataType)
    val size = syncDataRequest.getSize
    val timestamp = syncDataRequest.getTimestamp
    val syncDataType = syncDataRequest.getSyncDataType
    info("fetching data {} timestamp:{} size:{}", syncDataType,timestamp,size)
    val fetcher = syncDataType match {
      case SyncDataType.PERSON => personFetcher
      case SyncDataType.CASE => caseFetcher
      case SyncDataType.TEMPLATE_FINGER => templateFingerFetcher
      case SyncDataType.TEMPLATE_PALM => templatePalmFetcher
      case SyncDataType.LATENT_FINGER => latentFingerFetcher
      case SyncDataType.LATENT_PALM => latentPalmFetcher
      case other => null
    }

    if(fetcher != null)
      fetcher.doFetch(responseBuilder, size, timestamp)
    info("{} data fetched with timestamp:{}",responseBuilder.getSyncDataCount,timestamp)
    responseBuilder.build()
  }
}

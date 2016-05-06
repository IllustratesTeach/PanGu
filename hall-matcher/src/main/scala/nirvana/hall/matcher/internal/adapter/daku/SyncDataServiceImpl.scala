package nirvana.hall.matcher.internal.adapter.daku

import javax.sql.DataSource

import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.daku.sync._
import nirvana.hall.matcher.service.SyncDataService
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/29.
 */
class SyncDataServiceImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource) extends SyncDataService{
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
    val fetcher = syncDataType match {
      case SyncDataType.PERSON => new PersonFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.TEMPLATE_FINGER => new TemplateFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.LATENT_FINGER => new LatentFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.CASE => new CaseFetcher(hallMatcherConfig, dataSource)
      case other => null
    }
    if(fetcher != null)
      fetcher.doFetch(responseBuilder, size, timestamp)
    responseBuilder.build()
  }
}

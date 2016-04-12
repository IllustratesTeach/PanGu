package nirvana.hall.matcher.internal.adapter.gz

import javax.sql.DataSource

import nirvana.hall.matcher.service.SyncDataService
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/29.
 */
class SyncDataServiceImpl(dataSource: DataSource) extends SyncDataService{
  private implicit val ds = dataSource
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
//      case SyncDataType.PERSON => new sync.PersonFetcher()
      case SyncDataType.TEMPLATE_FINGER => new sync.TemplateFingerFetcher()
      case SyncDataType.LATENT_FINGER => new sync.LatentFingerFetcher()
//      case SyncDataType.CASE => new sync.CaseFetcher()
      case other => null
    }
    if(fetcher != null)
      fetcher.doFetch(responseBuilder, size, timestamp)
    responseBuilder.build()
  }
}

package nirvana.hall.matcher.internal.adapter.sh

import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.SyncDataService
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
 * Created by songpeng on 16/3/29.
 */
class SyncDataServiceImpl(hallMatcherConfig: HallMatcherConfig, dataSource: DataSource, featureExtractor: FeatureExtractor) extends SyncDataService with LoggerSupport{
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
      case SyncDataType.PERSON => new sync.PersonFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.CASE => new sync.CaseFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.TEMPLATE_FINGER => new sync.TemplateFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.TEMPLATE_PALM => new sync.TemplatePalmFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.LATENT_FINGER => new sync.LatentFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.LATENT_PALM => new sync.LatentPalmFetcher(hallMatcherConfig, dataSource)
      case other => null
    }
    if(fetcher != null)
      fetcher.doFetch(responseBuilder, size, timestamp)
    //捺印指纹老特征转新特征
    if(hallMatcherConfig.mnt.isNewFeature && syncDataType == SyncDataType.TEMPLATE_FINGER){
      val it = responseBuilder.getSyncDataBuilderList.iterator()
      while (it.hasNext){
        val syncData = it.next()
        if(syncData.getMinutiaType == MinutiaType.FINGER){
          val mnt = featureExtractor.ConvertMntOldToNew(syncData.getData.newInput()).get
          syncData.setData(ByteString.copyFrom(mnt))
        }
      }
    }
    info("{} data fetched with timestamp:{}",responseBuilder.getSyncDataCount,timestamp)
    responseBuilder.build()
  }
}

package nirvana.hall.matcher.internal.adapter.reset

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.SyncDataService
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto
import nirvana.protocol.SyncDataProto.SyncDataResponse
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType

class SyncDataServiceImpl(hallMatcherConfig: HallMatcherConfig) extends SyncDataService with LoggerSupport{
  /**
    * 同步数据
    *
    * @param syncDataRequest
    * @return
    */
  override def syncData(syncDataRequest: SyncDataProto.SyncDataRequest): SyncDataProto.SyncDataResponse = {
    val responseBuilder = SyncDataResponse.newBuilder
    responseBuilder.setSyncDataType(syncDataRequest.getSyncDataType)
    val timestamp = syncDataRequest.getTimestamp
    val syncDataType = syncDataRequest.getSyncDataType
    info("fetching data_type {} timestamp:{}", syncDataType,timestamp)
    val syncDataBuilder = responseBuilder.addSyncDataBuilder()
    syncDataBuilder.setObjectId(0)
    syncDataBuilder.setPos(1)
    syncDataBuilder.setOperationType(SyncData.OperationType.DEL)
    syncDataType match {
      case SyncDataType.PERSON =>
        syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
        syncDataBuilder.setData(ByteString.copyFrom("123456790", "UTF-8"))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.personSeq)
      case SyncDataType.CASE =>
        syncDataBuilder.setMinutiaType(MinutiaType.TEXT)
        syncDataBuilder.setData(ByteString.copyFrom("123456790", "UTF-8"))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.caseSeq)
      case SyncDataType.TEMPLATE_FINGER =>
        syncDataBuilder.setMinutiaType(MinutiaType.FINGER)
        syncDataBuilder.setData(ByteString.copyFrom(new Array[Byte](hallMatcherConfig.mnt.fingerTemplateSize)))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.fingerTemplateSeq)
      case SyncDataType.TEMPLATE_PALM =>
        syncDataBuilder.setMinutiaType(MinutiaType.PALM)
        syncDataBuilder.setData(ByteString.copyFrom(new Array[Byte](hallMatcherConfig.mnt.palmTemplateSize)))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.palmTemplateSeq)
      case SyncDataType.LATENT_FINGER =>
        syncDataBuilder.setMinutiaType(MinutiaType.FINGER)
        syncDataBuilder.setData(ByteString.copyFrom(new Array[Byte](hallMatcherConfig.mnt.fingerLatentSize)))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.fingerLatentSeq)
      case SyncDataType.LATENT_PALM =>
        syncDataBuilder.setMinutiaType(MinutiaType.PALM)
        syncDataBuilder.setData(ByteString.copyFrom(new Array[Byte](hallMatcherConfig.mnt.palmLatentSize)))
        syncDataBuilder.setTimestamp(hallMatcherConfig.resetSeq.palmLatentSeq)
      case other =>
    }
    info("reset {} with timestamp:{}",syncDataType, syncDataBuilder.getTimestamp)
    responseBuilder.build()
  }
}

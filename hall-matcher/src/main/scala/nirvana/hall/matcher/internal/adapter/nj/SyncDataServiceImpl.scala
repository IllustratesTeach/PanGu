package nirvana.hall.matcher.internal.adapter.nj

import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.internal.adapter.common.sync.{LatentFingerFetcher, LatentPalmFetcher, TemplateFingerFetcher, TemplatePalmFetcher}
import nirvana.hall.matcher.service.SyncDataService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.NirvanaTypeDefinition.SyncDataType
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.MinutiaType
import nirvana.protocol.SyncDataProto.{SyncDataRequest, SyncDataResponse}

/**
  * Created by songpeng on 16/3/29.
  */
class SyncDataServiceImpl(hallMatcherConfig: HallMatcherConfig,  implicit val dataSource: DataSource, featureExtractor: FeatureExtractor) extends SyncDataService with LoggerSupport{
  /**
    * 同步数据
    * @param syncDataRequest
    * @return
    */
  override def syncData(syncDataRequest: SyncDataRequest): SyncDataResponse = {
    val responseBuilder = SyncDataResponse.newBuilder
    responseBuilder.setSyncDataType(syncDataRequest.getSyncDataType)
    val size = syncDataRequest.getSize
    val timestamp = syncDataRequest.getTimestamp //当前读到的seq
    val syncDataType = syncDataRequest.getSyncDataType
    info("fetching data {} timestamp:{} size:{}", syncDataType,timestamp,size)
    val fetcher = syncDataType match {
      case SyncDataType.PERSON => new sync.PersonFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.CASE => new sync.CaseFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.TEMPLATE_FINGER => new TemplateFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.TEMPLATE_PALM => new TemplatePalmFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.LATENT_FINGER => new LatentFingerFetcher(hallMatcherConfig, dataSource)
      case SyncDataType.LATENT_PALM => new LatentPalmFetcher(hallMatcherConfig, dataSource)
      case other => null
    }
    if(fetcher != null)
    //从数据库读数据
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
    //通过案件表的上报状态判断是否同步现场数据
    if(syncDataType == SyncDataType.LATENT_FINGER || syncDataType == SyncDataType.LATENT_PALM){
      val responseBuilderNew = SyncDataResponse.newBuilder()
      var tableName = "GAFIS_CASE_FINGER"
      if(syncDataType == SyncDataType.LATENT_PALM) tableName = "GAFIS_CASE_PALM"
      val it = responseBuilder.getSyncDataBuilderList.iterator()
      while (it.hasNext) {
        val syncData = it.next()
        if(!getDataStatusBySid(syncData.getObjectId,tableName)){
          responseBuilderNew.addSyncData(syncData)
        }
      }
      responseBuilderNew.setSyncDataType(syncDataRequest.getSyncDataType)
      info("{} data fetched with timestamp:{}",responseBuilderNew.getSyncDataCount,timestamp)
      responseBuilderNew.build()
    }else{
      info("{} data fetched with timestamp:{}",responseBuilder.getSyncDataCount,timestamp)
      responseBuilder.build()
    }
  }

  /**
    * 通过SID查询上报状态,判断是否参与比对
    * @param sid
    * @param tableName
    * @return
    */
  def getDataStatusBySid(sid:Long,tableName:String): Boolean ={
    val FILTER_SQL: String = "select b.data_status from GAFIS_CASE b RIGHT JOIN " + tableName +" c ON b.CASE_ID = c.CASE_ID where c.SID = ?"
    var deleteFlag = false
    JdbcDatabase.queryWithPsSetter(FILTER_SQL){ps=>
      ps.setLong(1, sid)
    }{rs=>
      val dataStatus = rs.getInt(1)
      //上报状态为0,表示为未上报，不参与比对
      if(!rs.wasNull() && dataStatus == 0) deleteFlag = true
    }
    deleteFlag
  }
}

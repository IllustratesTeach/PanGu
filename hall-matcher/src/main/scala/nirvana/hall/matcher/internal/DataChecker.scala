package nirvana.hall.matcher.internal

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.HallMatcherConstants
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData
import nirvana.protocol.SyncDataProto.SyncDataResponse.SyncData.{MinutiaType, OperationType}

/**
 * Created by songpeng on 16/6/4.
 */
object DataChecker extends LoggerSupport{
  /**
   * 校验分库数据
   * @param hallMatcherConfig
   * @param syncData
   * @param isLatent
   * @return
   */
  def checkSyncData(hallMatcherConfig: HallMatcherConfig, syncData: SyncData, isLatent: Boolean): Boolean ={
    if(syncData.getObjectId <= 0){
      error("syncData objectId is 0")
      return false
    }
    /*数据长度校验*/
    if(syncData.getMinutiaType == MinutiaType.TEXT){
      return syncData.getData.size() > 0
    }else{
      if (syncData.getData.size() <=hallMatcherConfig.mnt.headerSize && syncData.getOperationType == OperationType.PUT) {
        error("data size too small %s for sid:%s minutia_type:%s isLatent:%s".format(
          syncData.getData.size(), syncData.getObjectId, syncData.getMinutiaType.toString, isLatent))
        return false
      }
      var dataSizeExpected:Int = 0
      syncData.getMinutiaType match {
        case MinutiaType.FINGER =>
          if(isLatent){
            dataSizeExpected = hallMatcherConfig.mnt.fingerLatentSize
          }else{
            dataSizeExpected = hallMatcherConfig.mnt.fingerTemplateSize
          }
        case MinutiaType.PALM=>
          if(isLatent){
            dataSizeExpected = hallMatcherConfig.mnt.palmLatentSize
          }else{
            dataSizeExpected = hallMatcherConfig.mnt.palmTemplateSize
          }
        case MinutiaType.RIDGE=>
          dataSizeExpected = DataConverter.readGAFISIMAGESTRUCTDataLength(syncData.getData)
        case MinutiaType.FACE=>
        case MinutiaType.TEXT=>
      }
      dataSizeExpected += hallMatcherConfig.mnt.headerSize
      if (syncData.getData.size != dataSizeExpected || dataSizeExpected <= HallMatcherConstants.HEADER_LENGTH) {
        error("MinutiaType:{} isLatent:{} sid:{}  dataSize:{} != expected:{}", syncData.getMinutiaType, isLatent, syncData.getObjectId,syncData.getData.size, dataSizeExpected)
        return false
      }
      true
    }
  }

  /**
   * 校验查询任务数据
   * @param hallMatcherConfig
   * @param matchTask
   */
  def checkMatchTask(hallMatcherConfig: HallMatcherConfig, matchTask: MatchTask): Boolean={
    //校验数据长度
//    matchTask.getMatchType match {
//      case MatchType.FINGER_TT =>
//
//      case MatchType.FINGER_TL =>
//
//      case MatchType.FINGER_LT =>
//
//      case MatchType.FINGER_LL =>
//
//    }

    false
  }
}

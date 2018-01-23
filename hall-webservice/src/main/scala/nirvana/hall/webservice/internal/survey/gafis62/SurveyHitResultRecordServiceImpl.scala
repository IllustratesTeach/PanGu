package nirvana.hall.webservice.internal.survey.gafis62

import javax.activation.DataHandler

import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage}
import nirvana.hall.c.services.gloclib.survey.SURVEYHITRESULTRECORD
import nirvana.hall.support.services.XmlLoader
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.survey.SurveyHitResultRecordService

/**
  * Created by songpeng on 2018/1/18.
  */
class SurveyHitResultRecordServiceImpl(v62Facade: V62Facade, hallWebserviceConfig: HallWebserviceConfig, fpt5Service: FPT5Service) extends SurveyHitResultRecordService{
  /**
    * 添加现勘比中信息
    * @param hitResult
    */
  override def addSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_ADD(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现堪比中信息
    * @param hitResult
    */
  override def updateSurveyHitResultRecord(hitResult: SURVEYHITRESULTRECORD): Unit = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_UPDATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, hitResult)
  }

  /**
    * 获取现勘比中信息
    * @param state 状态
    * @return
    */
  override def getSurveyHitResultRecordList(state: Byte, limit: Int): Seq[SURVEYHITRESULTRECORD] = {
    v62Facade.NET_GAFIS_SURVEYHITRESULTRECORD_GET_BY_STATE(V62Facade.DBID_SURVEY, V62Facade.TID_SURVEYHITRESULTRECORD, state)
  }

  /**
    * 根据hitResult获取FPT5比对关系
    * @param hitResult SURVEYHITRESULTRECORD
    * @return
    */
  override def getDataHandlerOfLtOrLlHitResultPackage(hitResult: SURVEYHITRESULTRECORD): Option[DataHandler] = {
    val fingerId = hitResult.szFingerID
    val hitFingerId = hitResult.szHitFingerID
    //TODO 完善接口信息
    hitResult.nQueryType match {
      case QueryConstants.QUERY_TYPE_LT =>
        val ltHitPkg = new LtHitResultPackage
        ltHitPkg.fingerPrintCardId = hitFingerId
        ltHitPkg.latentFingerCardId = fingerId
        ltHitPkg.fingerprintPackage = Array(fpt5Service.getFingerprintPackage(ltHitPkg.fingerPrintCardId))
        ltHitPkg.latentPackage = Array(fpt5Service.getLatentPackage(ltHitPkg.latentFingerCardId))

        val dataHandler = getZipDataHandlerOfString(XmlLoader.toXml(ltHitPkg,"GBK"), fingerId +"-"+ hitFingerId, hallWebserviceConfig.localHitResultPath)
        Option(dataHandler)
      case QueryConstants.QUERY_TYPE_LL =>
        val llHitPkg = new LlHitResultPackage
        llHitPkg.cardId = fingerId
        llHitPkg.resultCardId = hitFingerId

        val dataHandler = getZipDataHandlerOfString(XmlLoader.toXml(llHitPkg,"GBK"), fingerId +"-"+ hitFingerId, hallWebserviceConfig.localHitResultPath)
        Option(dataHandler)
      case other =>
        None
    }

  }
}

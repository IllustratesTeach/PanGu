package nirvana.hall.v70.ln.services

import javax.activation.DataHandler

import nirvana.hall.api.services.ExceptRelationService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.v70.services.service.GetPKIDService

/**
 * Created by songpeng on 16/9/21.
 */
class ExceptRelationServiceImpl(fptService: FPTService, getPKIDService: GetPKIDService) extends ExceptRelationService{
  /**
    * 导出比对关系
    *
    * @param queryid
    * @param ora_sid
    * @return
    */
  override def exportMatchRelation(queryid: String, ora_sid: String): DataHandler = ???

  /**
    * 获取查询的比对关系
    *
    * @param pkid
    * @return
    */
  override def getSearchMatchRelation(pkid: String, num: Int): GafisMatchInfo = ???
}

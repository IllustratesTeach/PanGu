package nirvana.hall.v62.internal.sync

import javax.sql.DataSource

import nirvana.hall.api.services.sync.FetchMatchRelationService
import nirvana.hall.protocol.fpt.MatchRelationProto.MatchSysInfo

/**
  * Created by win-20161010 on 2017/6/27.
  */
class FetchMatchRelationServiceImpl (implicit dataSource: DataSource) extends FetchMatchRelationService{


  /**
    * 获取比中关系pkid列表
    *
    * @param seq
    * @param size
    * @param dbId
    */
  override def fetchPkId(seq: Long, size: Int, dbId: Option[String]): Seq[(String, Long)] = ???

  /**
    * 验证读取策略
    *
    * @param matchSysInfo 比中信息
    * @param readStrategy
    * @return
    */
  override def validateByReadStrategy(matchSysInfo: MatchSysInfo, readStrategy: String): Boolean = {true}
}

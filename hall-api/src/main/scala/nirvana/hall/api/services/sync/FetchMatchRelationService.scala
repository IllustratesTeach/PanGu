package nirvana.hall.api.services.sync

import nirvana.hall.protocol.fpt.MatchRelationProto.MatchSysInfo

/**
  * Created by yuchen on 2017/6/27.
  */
trait FetchMatchRelationService {

  /**
    * 获取比中关系pkid列表
    * @param seq
    * @param size
    * @param dbId
    */
  def fetchPkId(seq: Long, size: Int, dbId: Option[String] = None): Seq[(String, Long)]

  /**
    * 验证读取策略
    * @param matchSysInfo 比中信息
    * @param readStrategy
    * @return
    */
  def validateByReadStrategy(matchSysInfo: MatchSysInfo, readStrategy: String): Boolean
}

package nirvana.hall.matcher.internal.adapter.gz

import javax.sql.DataSource

import nirvana.hall.matcher.service.PutMatchProgressService
import nirvana.hall.support.services.JdbcDatabase
import nirvana.protocol.MatchProgressProto.MatchProgressResponse.MatchProgressStatus
import nirvana.protocol.MatchProgressProto.{MatchProgressResponse, MatchProgressRequest}

/**
 * Created by songpeng on 16/4/12.
 */
class PutMatchProgressServiceImpl(implicit dataSource: DataSource) extends PutMatchProgressService{
  val UPDATE_PROGRESS_SQL = "update GAFIS_NORMALQUERY_QUERYQUE t set t.match_progress =? where t.ora_sid =?"
  /**
   * 更新比对进度
   * @param matchProgressRequest
   * @return
   */
  override def putMatchProgress(matchProgressRequest: MatchProgressRequest): MatchProgressResponse = {
    val matchProgressResponse = MatchProgressResponse.newBuilder()
    val oraSid = matchProgressRequest.getMatchId
    val progress = matchProgressRequest.getProgress

    JdbcDatabase.update(UPDATE_PROGRESS_SQL){ps=>
      ps.setInt(1, progress)
      ps.setLong(2, oraSid.toLong)
    }

    matchProgressResponse.setStatus(MatchProgressStatus.OK)
    matchProgressResponse.build()
  }
}

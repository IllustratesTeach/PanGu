package nirvana.hall.api.services

import nirvana.hall.api.annotations.RequiresUser
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * system service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-06
 */
trait SystemService {
  @Transactional
  @RequiresUser
  def deleteEntity(entity: String, id: Int)(implicit session: DBSession = AutoSpringDataSourceSession.apply())

  def findAllProtocol(): Seq[String]
}

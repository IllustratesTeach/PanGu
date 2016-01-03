package nirvana.hall.orm.services

import javax.persistence.EntityManager

/**
 * create entity manager instance
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-02
 */
trait EntityManagerCreator {
  /**
   * create EntityManager instance
   * @return EntityManager instance
   */
  def createEntityManager: EntityManager
}

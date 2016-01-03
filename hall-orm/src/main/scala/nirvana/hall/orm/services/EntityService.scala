package nirvana.hall.orm.services

import org.springframework.transaction.annotation.Transactional

import scala.reflect.ClassTag

/**
 * entity service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
trait EntityService {
  @Transactional
  def save[T](entity:T):T
  @Transactional
  def deleteById[T:ClassTag](id:Any)
  @Transactional
  def delete[T](entity:T)
}

package nirvana.hall.orm.internal

import javax.persistence.EntityManager

import nirvana.hall.orm.services.EntityService
import org.springframework.transaction.annotation.Transactional

import scala.reflect.{ClassTag,classTag}

/**
 * implement EntityService
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityServiceImpl(entityManager:EntityManager) extends EntityService{
  @Transactional
  override def save[T](entity: T): T = {
    entityManager.persist(entity)
    entity
  }

  @Transactional
  override def deleteById[T: ClassTag](id: Any): Unit = {
    val entity = entityManager.find(classTag[T].runtimeClass,id)
    entityManager.remove(entity)
  }

  @Transactional
  override def delete[T](entity: T): Unit = {
    entityManager.remove(entity)
  }
}

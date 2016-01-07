package nirvana.hall.orm.internal

import javax.persistence.EntityManager

import nirvana.hall.orm.services.{EntityService, Relation}
import org.springframework.transaction.annotation.Transactional

import scala.reflect.{ClassTag, classTag}

/**
 * implement EntityService
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityServiceImpl(entityManager:EntityManager) extends EntityService {
  @Transactional
  override def save[T](entity: T): T = {
    entityManager.persist(entity)
    entity
  }

  @Transactional
  override def deleteById[T: ClassTag](id: Any): Unit = {
    val entity = entityManager.find(classTag[T].runtimeClass, id)
    entityManager.remove(entity)
  }

  @Transactional
  override def delete[T](entity: T): Unit = {
    entityManager.remove(entity)
  }

  @Transactional
  override def deleteRelation[T](relation: Relation[T]): Int = {
    var fullQl = "delete from %s".format(relation.entityClazz.getSimpleName)
    relation.queryClause.foreach{fullQl += " where %s".format(_)}
    val query = entityManager.createQuery(fullQl)

    relation.queryParams.zipWithIndex.foreach {
      case (value, index) =>
        query.setParameter(index + 1, value)
    }
    query.executeUpdate()
  }

  @Transactional
  override def updateRelation[T](relation: Relation[T]): Int = {
    var fullQl = "update %s set".format(relation.entityClazz.getSimpleName)
    relation.updateQl.foreach{fullQl += " %s".format(_)}
    relation.queryClause.foreach{fullQl += " where %s".format(_)}

    val query = entityManager.createQuery(fullQl)

    var index = 1

    relation.queryParams.foreach { value =>
      query.setParameter(index, value)
      index += 1
    }
    relation.updateParams.foreach { value =>
      query.setParameter(index, value)
      index += 1
    }
    query.executeUpdate()
  }
}

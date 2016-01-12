package nirvana.hall.orm.internal

import javax.persistence.{EntityManager, Query}

import nirvana.hall.orm.services.{CriteriaRelation, EntityService, QuerySupport, Relation}
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConversions
import scala.collection.immutable.Stream
import scala.reflect.{ClassTag, classTag}

/**
 * implement EntityService
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityServiceImpl(entityManager:EntityManager) extends EntityService {
  private val logger = LoggerFactory getLogger getClass
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

    setQueryParameter(query,relation)

    query.executeUpdate()
  }

  @Transactional
  override def updateRelation[T](updateObject: QuerySupport[T]): Int = {
    updateObject match {
      case relation: Relation[T] =>
        var fullQl = "update %s set".format(relation.entityClazz.getSimpleName)
        relation.updateQl.foreach {
          fullQl += " %s".format(_)
        }
        relation.queryClause.foreach {
          fullQl += " where %s".format(_)
        }

        val query = entityManager.createQuery(fullQl)

        var index: Int = setQueryParameter(query, relation)

        relation.updateParams.foreach { value =>
          query.setParameter(index, value)
          index += 1
        }
        query.executeUpdate()
      case other =>
        throw new UnsupportedOperationException
    }
  }

  private def setQueryParameter[T](query:Query,relation: Relation[T]): Int = {
    var index = 1
    relation.queryParams.foreach { value =>
      query.setParameter(index, value)
      index += 1
    }
    index
  }

  /**
   * find some records by Relation
   * @param queryObj relation object
   * @tparam T type parameter
   * @return record stream
   */
  def find[T](queryObj:QuerySupport[T]):Stream[T]={
    val query = queryObj match {
      case relation: Relation[T] =>
        var fullQl = "from %s".format(relation.entityClazz.getSimpleName)
        relation.queryClause.foreach {
          fullQl += " where %s".format(_)
        }
        relation.orderBy.foreach {
          fullQl += " order by %s".format(_)
        }

        logger.debug("ql:{}", fullQl)
        val query = entityManager.createQuery(fullQl)

        setQueryParameter(query, relation)

        query
      case relation: CriteriaRelation[T] =>
        entityManager.createQuery(relation.query)
      case other=>
        throw new UnsupportedOperationException("%s unspported".format(other))
    }
    if (queryObj.offset > -1)
      query.setFirstResult(queryObj.offset)
    if (queryObj.limit > -1)
      query.setMaxResults(queryObj.limit)
    //convert as scala stream
    JavaConversions.asScalaBuffer[T](query.getResultList.asInstanceOf[java.util.List[T]]).toStream
  }
}

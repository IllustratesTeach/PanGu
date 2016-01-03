package nirvana.hall.orm.services

import javax.persistence._

import nirvana.hall.orm.{BaseOrmTestCase, ModelA}
import org.junit.{Assert, Test}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityManagerTest extends BaseOrmTestCase{
  @Test
  def test_save: Unit ={

    val transaction = getService[PlatformTransactionManager]
    val transactionDef = new DefaultTransactionDefinition()
    val status = transaction.getTransaction(transactionDef)
    val entityManager = getService[EntityManager]
    val modelA = new ModelA
    modelA.name = "xxx"
    entityManager.persist(modelA)
    transaction.commit(status)
    Assert.assertTrue(modelA.id>0)


    val results = entityManager.createQuery("from ModelA").getResultList
    Assert.assertEquals(1,results.size())
  }
}

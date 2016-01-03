package nirvana.hall.orm.internal

import nirvana.hall.orm.{ModelA, BaseOrmTestCase}
import nirvana.hall.orm.services.EntityService
import org.junit.Test

/**
 * implements EntityService
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class EntityServiceImplTest extends BaseOrmTestCase{
  @Test
  def test_save: Unit ={
    val entityService = getService[EntityService]
    val modelA = new ModelA
    entityService.save(modelA)
  }
}

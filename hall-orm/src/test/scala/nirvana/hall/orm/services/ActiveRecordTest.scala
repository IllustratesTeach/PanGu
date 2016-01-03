package nirvana.hall.orm.services

import nirvana.hall.orm.{ModelA, BaseOrmTestCase}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class ActiveRecordTest extends BaseOrmTestCase{
  @Test
  def test_save: Unit ={
    val modelA = new ModelA
    modelA.save()
    modelA.delete()
  }
}

package nirvana.hall.orm.services

import nirvana.hall.orm.{BaseOrmTestCase, ModelA}
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class ActiveRecordTest extends BaseOrmTestCase{
  @Test
  def test_save: Unit ={
    val modelA = new ModelA
    modelA.name = "asdf"
    modelA.save()

    var dsl = ModelA.find_by_name_and_id("adsf1",1).order(name="asc",name="desc")
    Assert.assertEquals("name asc,name desc",dsl.orderBy.get)
    //Assert.assertEquals(1,list.size)
    /*
    list = ModelA.find_by_name_and_id("asdf",modelA.id)
    Assert.assertEquals(1,list.size)
    Assert.assertEquals(0, ModelA.find_by_name("fdsa").size)
    */

    modelA.delete()
    //Assert.assertEquals(0, ModelA.find_by_name("asdf").size)
  }
}

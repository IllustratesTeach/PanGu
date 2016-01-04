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
  @Test
  def test_find: Unit ={
    val modelA = new ModelA
    modelA.name = "asdf"
    modelA.save()

    val result = ModelA.find(modelA.id).headOption
    Assert.assertTrue(result.isDefined)

    var size = ModelA.find_by(name="asdf").size
    Assert.assertEquals(1,size)

    size = ModelA.find_by_name("asdf").limit(10).size
    Assert.assertEquals(1,size)

    size = ModelA.where(name="asdf",id=modelA.id).size
    Assert.assertEquals(1,size)

    size = ModelA.where("from ModelA where name=?","asdf")
      .offset(0).limit(10).order(name="asc").size
    Assert.assertEquals(1,size)
  }
}

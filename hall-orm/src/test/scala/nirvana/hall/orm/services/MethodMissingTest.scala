package nirvana.hall.orm.services

import nirvana.hall.orm.ModelA
import org.apache.tapestry5.ioc.internal.services.PropertyAccessImpl
import org.apache.tapestry5.ioc.services.PropertyAccess
import org.junit.{Assert, Test}

/**
 * method missing test case
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-01
 */
class MethodMissingTest {
  private val propertyAccess  = new PropertyAccessImpl
  @Test
  def test_create: Unit ={
    val missing = new MethodMissing[ModelA] with PropertyAccessorSupport{
      override def propertyAccessor: PropertyAccess = propertyAccess
    }
    val modelA = missing.create(name="xxx").head
    Assert.assertEquals("xxx",modelA.name)

    val modelA1 = new ModelA with MethodMissing[ModelA] with PropertyAccessorSupport{
      override def propertyAccessor: PropertyAccess = propertyAccess
    }
    modelA1.a
  }
}


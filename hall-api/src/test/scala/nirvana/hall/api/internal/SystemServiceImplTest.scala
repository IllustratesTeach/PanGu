package nirvana.hall.api.internal

import org.apache.tapestry5.ioc.services.ClassNameLocator
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-06
 */
class SystemServiceImplTest extends BaseServiceTestSupport {
  @Test
  def test_find_all_protocol: Unit = {
    val classNameLocator = registry.getService(classOf[ClassNameLocator])

  }
}

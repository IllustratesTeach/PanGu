package nirvana.hall.stream.internal.adapter.bianjian

import nirvana.hall.stream.services.StreamService
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.junit.Test
import org.mockito.Mockito
import org.slf4j.LoggerFactory

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
class BianjianStreamTest {
  private val logger = LoggerFactory getLogger getClass
  @Test
  def test_stream: Unit ={
    val streamService = Mockito.mock(classOf[StreamService])
    val registry = Mockito.mock(classOf[RegistryShutdownHub])

    //config jdbc
    //System.setProperty()


    val dataSource = BianjianTestModule.buildDataSource(registry,logger)
    val bianjiangStream = new BianjianStream(dataSource,streamService)
    bianjiangStream.startStream()

  }
}

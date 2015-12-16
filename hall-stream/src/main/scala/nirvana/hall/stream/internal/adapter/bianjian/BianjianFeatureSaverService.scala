package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.stream.services.FeatureSaverService

/**
 * bianjian feature saver test
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
class BianjianFeatureSaverService(dataSource:DataSource) extends FeatureSaverService{
  /**
   * save feature data
   * @param id data id
   * @param feature feature data
   */
  override def save(id: Any, feature: ByteString): Unit = {
    throw new UnsupportedOperationException
  }
}

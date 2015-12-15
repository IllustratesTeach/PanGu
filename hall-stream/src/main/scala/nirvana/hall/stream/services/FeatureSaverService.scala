package nirvana.hall.stream.services

import com.google.protobuf.ByteString

/**
 * save result
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait FeatureSaverService {
  /**
   * save feature data
   * @param id data id
   * @param feature feature data
   */
  def save(id:Any,feature:ByteString)
}

package nirvana.hall.stream.services

/**
 * stream producer
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-15
 */
trait StreamProducer {
  /**
   * push event data by appropriate next data
   */
  def pushData(StreamService:StreamService)
}

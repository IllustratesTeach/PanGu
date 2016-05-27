package nirvana.hall.v62.proxy

import nirvana.hall.api.services.TPCardService

/**
  * 封装本地的服务
 *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-27
  */
trait LocalServiceFinder {
  def findTPCardService: TPCardService
}

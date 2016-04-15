package nirvana.hall.image.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.ImageCompressProto.ImageCompressRequest

/**
  * image encoder
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-15
  */
trait ImageEncoder {
  def encode(imageCompressRequest: ImageCompressRequest):GAFISIMAGESTRUCT
}

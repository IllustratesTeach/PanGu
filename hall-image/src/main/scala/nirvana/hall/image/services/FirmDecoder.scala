package nirvana.hall.image.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
 * firm decoder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
trait FirmDecoder{
  /**
   * decode compressed data using firm's algorithm
   * @return original image data
   */
  def decode(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT
}

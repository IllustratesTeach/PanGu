package nirvana.hall.image.services

import nirvana.hall.image.jni.OriginalImage

/**
 * firm decoder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
trait FirmDecoder{
  /**
   * decode compressed data using firm's algorithm
   * @param code firm code,such as "1300"
   * @param cpr_data compressed data
   * @param width image width
   * @param height image height
   * @return original image data
   */
  def decode(code:String,cpr_data:Array[Byte],width:Int,height:Int,dpi:Int): OriginalImage
}

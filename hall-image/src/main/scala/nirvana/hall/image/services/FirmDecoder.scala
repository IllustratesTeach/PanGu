package nirvana.hall.image.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
 * firm decoder
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
/**
  * 区分图像数据类型
  */
trait ImageDataType
/** 标记为fpt图像数据 **/
case object FPTImageDataType extends ImageDataType
/** 标记为原始图像数据 **/
case object RawImageDataType extends ImageDataType

trait FirmDecoder{
  /**
   * decode compressed data using firm's algorithm
   * @param gafisImg
   * @param imageDataType  FPTImageDataType: fpt图像数据转换为GAFISIMAGESTRUCT，统一都加了一个头信息，GFS1900压缩算法的数据本身已经包含头信息，而且进行了加密转换，需要先解密
   *                       RawImageDataType: 数据库原始图像数据
   * @return original image data
   */
  def decode(gafisImg:GAFISIMAGESTRUCT,imageDataType: ImageDataType=FPTImageDataType): GAFISIMAGESTRUCT

  def decodeFPTImage(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT
  def decodeRawImage(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT

}


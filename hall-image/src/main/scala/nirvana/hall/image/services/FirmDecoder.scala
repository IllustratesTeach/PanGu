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
   * (大库处理fpt数据转换为GAFISIMAGESTRUCT时统一都加了一个头信息，解压时统一去掉头信息再解压
   * 由于GFS1900压缩算法的数据本身已经包含头信息,如果是数据库存储的数据,若使用该方法解压的GFS压缩数据时就需要多加一个头信息
   * 这种情况推荐使用decodeByGFS)
 *
   * @return original image data
   */
  def decode(gafisImg:GAFISIMAGESTRUCT,imageDataType: ImageDataType=FPTImageDataType): GAFISIMAGESTRUCT

  def decodeFPTImage(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT
  def decodeRawImage(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT

  /**
    * 解压gfs压缩图
 *
    * @param gafisImg, 6.2数据库存储的数据格式
    * @return
    */
  @deprecated(message = "已经过期,请使用decodeRawImage方法进行代替")
  def decodeByGFS(gafisImg: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT
}


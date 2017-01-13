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

  /**
    * 解压gfs压缩图
    * @param gafisImg, 6.2数据库存储的数据格式
    * @return
    */
  def decodeByGFS(gafisImg: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT
}

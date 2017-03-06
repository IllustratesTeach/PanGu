package nirvana.hall.api.services.remote

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
  * Created by songpeng on 2017/1/23.
  * 远程请求hall-image处理图像数据
  */
trait HallImageRemoteService {

  /**
    * 解压图像
    * @param gafisImage
    * @return
    */
  def decodeGafisImage(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT

  /**
    * 将图像数据压缩为wsq
    * @param gafisImage
    * @return
    */
  def encodeGafisImage2Wsq(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT

  /**
    * 将图像数据压缩为GFS压缩图（xgw压缩）
    * @param gafisImage
    * @return
    */
  def encodeGafisImage2GFS(gafisImage: GAFISIMAGESTRUCT): GAFISIMAGESTRUCT

}

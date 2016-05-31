package nirvana.hall.spark.services

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.NirvanaSparkConfig
import SparkFunctions.{StreamError, StreamEvent}

/**
  * 基础的图像提供
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
trait ImageProvider {
  /**
    * 请求图像数据
 *
    * @param parameter nirvana相关配置
    * @param message KAFKA中的消息
    * @return 消息序列
    */
  def requestImage(parameter:NirvanaSparkConfig,message:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)]

  case class RequestRemoteFileError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "R|"+message
  }
  //fpg to FingerPosition
  protected def getFingerPosition(fgp : Int) : FingerPosition = {
    val normalPosition = (fgp-1) % 10
    normalPosition match {
      case 0 => FingerPosition.FINGER_R_THUMB
      case 1 => FingerPosition.FINGER_R_INDEX
      case 2 => FingerPosition.FINGER_R_MIDDLE
      case 3 => FingerPosition.FINGER_R_RING
      case 4 => FingerPosition.FINGER_R_LITTLE
      case 5 => FingerPosition.FINGER_L_THUMB
      case 6 => FingerPosition.FINGER_L_INDEX
      case 7 => FingerPosition.FINGER_L_MIDDLE
      case 8 => FingerPosition.FINGER_L_RING
      case 9 => FingerPosition.FINGER_L_LITTLE
      case _ => FingerPosition.FINGER_UNDET
    }
  }
}

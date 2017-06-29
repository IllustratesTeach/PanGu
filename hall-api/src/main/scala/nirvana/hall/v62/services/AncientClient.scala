package nirvana.hall.v62.services

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.AncientData
import nirvana.hall.v62.internal.NoneResponse
import org.jboss.netty.buffer.ChannelBuffer

import scala.concurrent.Promise
import scala.reflect._

/**
 * ancient client.
 * direct call v6.2 application server.
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
trait AncientClient {
  def executeInChannel[T](action:ChannelOperator=>T): T
}
/**
 * channel operator trait.
 * use it to write message and receive message
 */
trait ChannelOperator{
  /**
    * 得到服务器信息
    *
    * @return
    */
  def getServerInfo:String
  /**
   * write message to channel
    *
    * @param data data written
   * @tparam R return type
   * @return server return message object
   */
  def writeMessage[R <: AncientData :ClassTag](data:Any*): R={
    data.foreach{
      case el:AncientData =>
        /*
        val buffer = ChannelBuffers.buffer(el.getDataSize)
        val bytes = el.writeToChannelBuffer(buffer).array()
        val count = connection.write(bytes,0,bytes.length)
        if(count != bytes.length){
          throw new IllegalAccessException("fail to write byte array")
        }
        */
        writeAncientData[el.type,NoneResponse](el)
      case el:Array[Byte] =>
        writeByteArray[NoneResponse](el)
      case other=>
        throw new IllegalArgumentException("data unsupported "+other)
    }
    receive[R]()
  }
  def writeAncientData[T <:AncientData,R <: AncientData :ClassTag](target:T): R={
    val bytes = target.toByteArray(AncientConstants.GBK_ENCODING)
    writeByteArray(bytes)
  }
  def writePromise(len:Int,promise:Promise[ChannelBuffer]): Unit ={
    throw new UnsupportedOperationException
  }


  /**
   * write byte array to channel
    *
    * @param data data be sent
   * @param offset byte array offset
   * @param length data length
   * @tparam R return type
   * @return data from server
   */
  def writeByteArray[R <: AncientData :ClassTag](data:Array[Byte],offset:Int,length:Int): R
  def writeByteArray[R <: AncientData :ClassTag](data:Array[Byte]): R = {
    writeByteArray[R](data,0,data.length)
  }
  def receive[R <: AncientData :ClassTag](): R ={
    classTag[R] match{
      case t if t == classTag[Nothing] =>
        null.asInstanceOf[R]
      case t if t == classTag[NoneResponse] =>
        new NoneResponse().asInstanceOf[R]
      case _ =>
        val dataInstance = classTag[R].runtimeClass.newInstance().asInstanceOf[R]
        receive(dataInstance)
      /*
      val buffer = receiveByteArray(dataInstance.getDataSize)
      dataInstance.fromChannelBuffer(buffer).asInstanceOf[R]
      */
    }
  }
  def receive[R <:AncientData](target:R):R ={
    val dataLength = target.getDataSize
    target.fromStreamReader(receiveByteArray(dataLength))
  };
  def receiveByteArray(len:Int): ChannelBuffer
}


package nirvana.hall.v62.services

import org.jboss.netty.buffer.ChannelBuffer

/**
 * ancient client.
 * direct call v6.2 application server.
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
   * write message to channel
   * @param data data written
   * @param manifest class reflection
   * @tparam R return type
   * @return server return message object
   */
  def writeMessage[R <: AncientData](data:Any*)(implicit manifest: Manifest[R]): R

  /**
   * write byte array to channel
   * @param data data be sent
   * @param offset byte array offset
   * @param length data length
   * @param manifest class reflection
   * @tparam R return type
   * @return data from server
   */
  def writeByteArray[R <: AncientData](data:Array[Byte],offset:Int,length:Int)(implicit manifest: Manifest[R]): R
  def writeByteArray[R <: AncientData](data:Array[Byte])(implicit manifest: Manifest[R]): R
  def receive[R <: AncientData]()(implicit manifest: Manifest[R]): R
  def receiveByteArray(len:Int): ChannelBuffer
}


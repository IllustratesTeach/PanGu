package nirvana.hall.v62.internal

import java.net.InetAddress

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.{ChannelOperator, AncientClient, AncientData}
import org.jboss.netty.buffer.ChannelBuffers
import org.xsocket.connection.{IBlockingConnection, BlockingConnection}

/**
 * ancient client based on xsocket
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class XSocketAncientClient(host:String,port:Int) extends AncientClient with LoggerSupport{
  def executeInChannel[T](action:ChannelOperator=>T): T={
    val connection = new BlockingConnection(InetAddress.getByName(host),port,10*1000)
    connection.setReadTimeoutMillis(5 * 1000)
    val channelHolder = new XSocketChannelOperator(connection)
    try{
      action(channelHolder)
    }finally{
      connection.close()
    }
  }
  class XSocketChannelOperator(connection:IBlockingConnection) extends ChannelOperator{
    /**
     * write message to channel
     * @param data data written
     * @param manifest class reflection
     * @tparam R return type
     * @return server return message object
     */
    override def writeMessage[R <: AncientData](data: Any*)(implicit manifest: Manifest[R]): R = {
      data.foreach{
        case el:AncientData =>
          val buffer = ChannelBuffers.buffer(el.getDataSize).array()
          connection.write(buffer,0,buffer.length)
        case el:Array[Byte] =>
          connection.write(el,0,el.length)
        case other=>
          throw new IllegalArgumentException("unspported data "+other)
      }
      receive[R]()
    }

    override def receiveByteArray(len: Int): Array[Byte] = {
      connection.readBytesByLength(len)
    }

    /**
     * write byte array to channel
     * @param data data be sent
     * @param offset byte array offset
     * @param length data length
     * @param manifest class reflection
     * @tparam R return type
     * @return data from server
     */
    override def writeByteArray[R <: AncientData](data: Array[Byte], offset: Int, length: Int)(implicit manifest: Manifest[R]): R = {
      connection.write(data,offset,length)
      receive[R]()
    }

    override def writeByteArray[R <: AncientData](data: Array[Byte])(implicit manifest: Manifest[R]): R = {
      writeByteArray(data,0,data.length)
    }

    override def receive[R <: AncientData]()(implicit manifest: Manifest[R]): R = {
      val dataInstance = manifest.runtimeClass.newInstance().asInstanceOf[AncientData]
      val bytes = receiveByteArray(dataInstance.getDataSize)
      dataInstance.fromChannelBuffer(ChannelBuffers.wrappedBuffer(bytes))

      dataInstance.asInstanceOf[R]
    }
  }
}
